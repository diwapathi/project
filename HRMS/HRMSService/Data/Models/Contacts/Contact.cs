using System;
using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;

namespace Data.Models.Contacts
{
    [BsonIgnoreExtraElements]
    [CollectionName("contacts")]
    public class Contact
    {
        [BsonElement("_id")]
        public ObjectId Id { get; set; }

        [BsonElement("empName")]
        public string EmpName { get; set; }
        
        [BsonElement("empDesignation")]
        public string EmpDesignation { get; set; }

        [BsonElement("emDoj")]
        public DateTime EmDoj { get; set; }

        [BsonElement("empPbplExp")]
        public string EmpPbplExp { get; set; }

        [BsonElement("empTotalExp")]
        public string EmpTotalExp { get; set; }
        
        [BsonElement("empPastCompany")]
        public string EmpPastCompany { get; set; }
        
        [BsonElement("empDepartmentUnit")]
        public string EmpDepartmentUnit { get; set; }
        
        [BsonElement("empAuditFaced")]
        public string EmpAuditFaced { get; set; }
     
        [BsonElement("empCode")]
        public string EmpCode { get; set; }
        
        [BsonElement("creationDate")]
        public DateTime CreationDate { get; set; }
        
       
    }
}