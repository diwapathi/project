﻿using System.Reflection;

using MongoDB.Driver;
using MongoDB.Driver.GridFS;

using Serilog;

namespace Data
{
    internal sealed class DatabaseContext: IDatabaseContext
    {
        private readonly ILogger _logger;

        private MongoClient _mongoClient;

        public DatabaseContext(ILogger logger, IConnectionStringProvider connectionStringProvider)
        {
            _logger = logger;

            _mongoClient = new MongoClient(connectionStringProvider.Get());
            Database = _mongoClient.GetDatabase(connectionStringProvider.Database);
        }

        public IMongoDatabase GetDatabase(string name)
        {
            return _mongoClient.GetDatabase(name);
        }

        public void SetDatabase(string name)
        {
            Database = _mongoClient.GetDatabase(name);
        }

        /// <inheritdoc />
        public IMongoDatabase Database { get; set; }

        public IGridFSBucket FileSystem => new GridFSBucket(Database);

        /// <inheritdoc />
        public IMongoCollection<T> GetCollection<T>()
        {
            var type = typeof(T);
            var attribute = type.GetCustomAttribute(typeof(CollectionNameAttribute)) as CollectionNameAttribute;
            var collectionName = attribute?.Name ?? typeof(T).Name;
            _logger.Verbose($"Accessing {collectionName} collection");
            return Database.GetCollection<T>(collectionName);
        }

        public IMongoCollection<T> GetCollection<T>(string name)
        {
            var collectionName = name;
            _logger.Verbose($"Accessing {collectionName} collection");
            return Database.GetCollection<T>(collectionName);
        }
    }
}