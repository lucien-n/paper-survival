package me.scaffus.survival.database

class DatabaseCredentials(
    private val host: String,
    val user: String,
    val password: String,
    val databaseName: String,
    private val port: Int
) {
    fun toUri(): String {
        val sb = StringBuilder()
        sb.append("jdbc:mysql://")
            .append(host)
            .append(":")
            .append(port)
            .append("/")
            .append(databaseName)
        return sb.toString()
    }
    // jdbc:mysql://u19411_GlmfiGMvrP:izORXgo7o%5E2xh6ZP!!S.Eg7D@sql-01.fra02.mine-hoster.de:3306/s19411_survival
}