﻿using System;
using System.Collections.Generic;
 using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;

namespace Data.Models.Users
{
    [BsonIgnoreExtraElements]
    [CollectionName("users")]
    public  class User
    {
        [BsonElement("_id")]
        public ObjectId Id { get; set; }

        [BsonElement("name")]
        public string Name { get; set; }

        [BsonElement("mobileNumber")]
        public string MobileNumber { get; set; }

        [BsonElement("isdCode")]
        public string IsdCode { get; set; }

        [BsonElement("emailAddress")]
        public string EmailAddress { get; set; }

        [BsonElement("gender")]
        public string Gender { get; set; }

        [BsonElement("password")]
        public string Password { get; set; }
        
        [BsonElement("creationDate")]
        public DateTime CreationDate { get; set; }
        
        [BsonElement("dateOfBirth")]
        public DateTime? DateOfBirth { get; set; }
        
        [BsonElement("isActive")]
        public bool IsActive { get; set; }
        
 
    }
}