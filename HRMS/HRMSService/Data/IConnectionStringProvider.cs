namespace Data
{
    /// <summary>
    ///     Provides methods to get connection string for database.
    /// </summary>
    public interface IConnectionStringProvider
    {
        /// <summary>
        ///     Gets the database name.
        /// </summary>
        string Database { get; }

        /// <summary>
        ///     Gets connection string.
        /// </summary>
        /// <returns>Connection string.</returns>
        string Get();
    }
}