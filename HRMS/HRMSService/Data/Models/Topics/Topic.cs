using System;
using System.Collections.Generic;
using Data.Models.Contacts;
using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;
namespace Data.Topics
{
    [BsonIgnoreExtraElements]
    [CollectionName("topics")]
    public class Topic
    {
        [BsonElement("_id")]
        public ObjectId TopicId { get; set; }
        
        [BsonElement("topicName")]
        public string TopicName { get; set; }
        
        [BsonElement("evaluationtemplateName")]
        public string EvaluationtemplateName { get; set; }
        
        [BsonElement("contactIdentites")]
        public Contact[] ContactIdentites { get; set; }
    }
}