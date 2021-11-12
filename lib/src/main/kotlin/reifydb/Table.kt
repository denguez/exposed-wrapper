package reifydb

import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table as SQLTable
import org.jetbrains.exposed.sql.`java-time`.date as sqlDate
import org.jetbrains.exposed.sql.select

abstract class Table(name: String = "") : SQLTable(name) {
    fun <T> primaryKey(column: Column<T>) = PrimaryKey(column)
    fun string(name: String) = varchar(name, 255)
    fun date(name: String) = sqlDate(name)
}

abstract class IntIDTable(name: String = "") : IntIdTable(name)

abstract class IDTable<ID : Comparable<ID>>(name: String = "") : IdTable<ID>(name) {
    fun <T> primaryKey(column: Column<T>) = PrimaryKey(column)
    fun string(name: String) = varchar(name, 255)
    fun date(name: String) = sqlDate(name)

    fun join(other: IDTable<ID>, where: WhereExpression) = run {
        innerJoin(other).slice(columns).select(where).withDistinct()
    }
}