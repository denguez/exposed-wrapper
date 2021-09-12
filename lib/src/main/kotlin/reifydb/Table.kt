package reifydb

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SqlExpressionBuilder
import org.jetbrains.exposed.sql.`java-time`.date as sqlDate
import org.jetbrains.exposed.sql.select

typealias WhereExpression = SqlExpressionBuilder.() -> Op<Boolean>

abstract class Table : IntIdTable() {
    fun string(name: String) = varchar(name, 255)
    fun date(name: String) = sqlDate(name)

    fun join(other: Table, where: WhereExpression) = run {
        innerJoin(other).slice(columns).select(where).withDistinct()
    }
}
