using System;

namespace Data
{
    [AttributeUsage(AttributeTargets.Class)]
    public sealed class CollectionNameAttribute: Attribute
    {
        public CollectionNameAttribute(string name)
        {
            Name = name;
        }

        public string Name { get; }
    }
}