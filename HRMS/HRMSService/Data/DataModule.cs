﻿using Autofac;

namespace Data
{
    /// <inheritdoc />
    public sealed class DataModule: Module
    {
        /// <inheritdoc />
        protected override void Load(ContainerBuilder builder)
        {
            builder.RegisterType<DatabaseContext>().As<IDatabaseContext>().InstancePerDependency();
        }
    }
}