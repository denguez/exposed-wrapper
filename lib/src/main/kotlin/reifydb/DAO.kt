package reifydb

import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.Op.TRUE

abstract class DAO(val table: Table) {
    suspend open fun create(statement: CreateExpression) = transaction { table.insert(statement) }

    suspend open fun read(where: WhereExpression) = transaction { table.select(where).toList() }
    suspend open fun readOne(where: WhereExpression) = transaction {
        table.select(where).firstOrNull()
    }

    suspend open fun update(where: WhereExpression, statement: UpdateExpression) = transaction {
        table.update(where, body = statement)
    }

    suspend open fun delete(where: WhereExpression) = transaction { table.deleteWhere(op = where) }
}

abstract class IDDAO<I : Comparable<I>, T : IDEntity<I>>(table: IDTable<I>) :
        EntityClass<I, T>(table) {
    suspend open fun create(block: T.() -> Unit): T = transaction { new(block) }

    suspend open fun read(where: WhereExpression = { TRUE }) = transaction { find(where).toList() }
    suspend open fun readOne(where: WhereExpression): T? = transaction { find(where).firstOrNull() }

    suspend open fun update(entity: T, block: T.() -> Unit) = transaction { entity.apply(block) }
    suspend open fun update(where: WhereExpression, block: T.() -> Unit) = transaction {
        find(where).forEach { it.apply(block) }
    }

    suspend open fun delete(entity: T) = transaction { entity.delete() }
    suspend open fun delete(where: WhereExpression) = transaction {
        find(where).forEach { it.delete() }
    }

    suspend open fun <R : Comparable<R>> readSorted(
            selector: (T) -> R?,
            where: WhereExpression = { TRUE }
    ) = transaction { find(where).sortedBy(selector) }.toList()
}

abstract class IntIDDAO<T : IntIDEntity>(table: IntIDTable) : IntEntityClass<T>(table) {
    suspend open fun create(block: T.() -> Unit): T = transaction { new(block) }

    suspend open fun read(where: WhereExpression = { TRUE }) = transaction { find(where).toList() }
    suspend open fun readOne(where: WhereExpression): T? = transaction { find(where).firstOrNull() }

    suspend open fun update(entity: T, block: T.() -> Unit) = transaction { entity.apply(block) }
    suspend open fun update(where: WhereExpression, block: T.() -> Unit) = transaction {
        find(where).forEach { it.apply(block) }
    }

    suspend open fun delete(entity: T) = transaction { entity.delete() }
    suspend open fun delete(where: WhereExpression) = transaction {
        find(where).forEach { it.delete() }
    }

    suspend open fun <R : Comparable<R>> readSorted(
            selector: (T) -> R?,
            where: WhereExpression = { TRUE }
    ) = transaction { find(where).sortedBy(selector) }.toList()
}
