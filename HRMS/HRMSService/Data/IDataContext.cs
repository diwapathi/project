﻿using MongoDB.Driver;
using MongoDB.Driver.GridFS;

namespace Data
{
    /// <summary>
    ///     Represents database context.
    /// </summary>
    public interface IDatabaseContext
    {
        /// <summary>
        ///     Gets the database.
        /// </summary>
        /// <value>
        ///     The database.
        /// </value>
        IMongoDatabase Database { get; }

        /// <summary>
        ///     Gets the file system.
        /// </summary>
        /// <value>
        ///     The file system.
        /// </value>
        IGridFSBucket FileSystem { get; }

        /// <summary>
        ///     Gets the collection.
        /// </summary>
        /// <typeparam name="T">Type of entity.</typeparam>
        /// <returns>The data context.</returns>
        IMongoCollection<T> GetCollection<T>();

        /// <summary>
        ///     Gets the collection.
        /// </summary>
        /// <typeparam name="T">Type of entity.</typeparam>
        /// <returns>The data context.</returns>
        IMongoCollection<T> GetCollection<T>(string name);

        IMongoDatabase GetDatabase(string name);

        void SetDatabase(string name);
    }
}