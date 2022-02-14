using System;
using System.Collections.Generic;
using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;
namespace Data.Assements
{   
    [BsonIgnoreExtraElements]
    [CollectionName("assements")]
    public class Assement
    {
        [BsonElement("_id")]
        public ObjectId AsserId { get; set; }
        [BsonElement("asserName")]
        public string AsserName { get; set; }
        [BsonElement("asserDepartment")]
        public string AsserDepartment { get; set; }
        
    }
}