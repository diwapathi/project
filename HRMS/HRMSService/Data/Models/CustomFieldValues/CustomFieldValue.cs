using System;
using System.Collections.Generic;
using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;
namespace Data.CustomFieldValues
{
    [BsonIgnoreExtraElements]
    [CollectionName("customFieldValues")]
    public class CustomFieldValue
    {
        [BsonElement("_id")]
        public ObjectId custFieldValId { get; set; }
        
        [BsonElement("custRemark ")]
        public string custRemark  { get; set; }
    }
}