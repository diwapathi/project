using System;
using System.Collections.Generic;
using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;
namespace Data.CustomFieldTemplates
{
    [BsonIgnoreExtraElements]
    [CollectionName("customFieldTemplates")]
    public class CustomFieldTemplate
    {
        [BsonElement("_id")]
        public ObjectId CustFieldTempId { get; set; }

        [BsonElement("CustomFields ")]
        public string[] customFields  { get; set; }
        
    }
}