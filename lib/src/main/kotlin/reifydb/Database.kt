package reifydb

import org.jetbrains.exposed.sql.Database as Exposed
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction

class DatabaseConfig() {
    val driver = "org.postgresql.Driver"
    var url = ""
    var username = "root"
    var password = ""
    var logger = false
    internal val tables: MutableList<Table> = mutableListOf()

    fun tables(vararg tables: Table) = this.tables.addAll(tables)
}

fun Database(block: DatabaseConfig.() -> Unit) {
    val config = DatabaseConfig().apply(block)
    Exposed.connect(
            config.url,
            driver = config.driver,
            user = config.username,
            password = config.password,
    )
    transaction {
        val tables = config.tables.toTypedArray()
        if (tables.isNotEmpty()) SchemaUtils.create(*tables)
        if (config.logger) addLogger(StdOutSqlLogger)
    }
}


