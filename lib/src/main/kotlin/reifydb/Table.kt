package reifydb

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SqlExpressionBuilder
import org.jetbrains.exposed.sql.`java-time`.date as sqlDate
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column

typealias WhereExpression = SqlExpressionBuilder.() -> Op<Boolean>

abstract class Table<ID: Comparable<ID>>(name: String = ""): IdTable<ID>(name) {
    fun<T> primaryKey(column: Column<T>) = PrimaryKey(column) 
    fun string(name: String) = varchar(name, 255)
    fun date(name: String) = sqlDate(name)

    fun join(other: Table<ID>, where: WhereExpression) = run {
        innerJoin(other).slice(columns).select(where).withDistinct()
    }
}

abstract class IDTable(name: String = "") : Table<Int>(name)
