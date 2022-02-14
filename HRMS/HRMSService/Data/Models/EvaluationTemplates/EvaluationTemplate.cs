using System;
using System.Collections.Generic;
using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;
namespace Data.EvaluationTemplates
{
   
    [BsonIgnoreExtraElements]
    [CollectionName("evaluationtemplates")]
    public class EvaluationTemplate
    {
        [BsonElement("_id")]
        public ObjectId EvaluationTemplateId { get; set; }
        [BsonElement("criteria")]
        public string Criteria { get; set; }
        [BsonElement("basis")]
        public string Basis { get; set; }
        [BsonElement("expectation")]
        public string Expectation { get; set; }
        [BsonElement("customfieldId")]
        public string CustomfieldId { get; set; }
    }
}