package reifydb

import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Op.TRUE

abstract class DAO(val table: Table, val db: Database? = null) {
    suspend open fun create(statement: CreateExpression) =
            transaction(db) { table.insert(statement) }

    suspend open fun read(where: WhereExpression) = 
            transaction(db) { table.select(where).toList() }

    suspend open fun readOne(where: WhereExpression) =
            transaction(db) { table.select(where).firstOrNull() }

    suspend open fun update(where: WhereExpression, block: UpdateExpression) =
            transaction(db) { table.update(where, body = block) }

    suspend open fun delete(where: WhereExpression) =
            transaction(db) { table.deleteWhere(op = where) }
}

abstract class IDDAO<I : Comparable<I>, T : IDEntity<I>>(
        table: IDTable<I>,
        val db: Database? = null
) : EntityClass<I, T>(table) {

    suspend open fun create(block: T.() -> Unit): T = 
            transaction(db) { new(block) }

    suspend open fun read(where: WhereExpression = { TRUE }) = 
            transaction(db) { find(where).toList() }

    suspend open fun readOne(where: WhereExpression): T? = 
            transaction(db) { find(where).firstOrNull() }

    suspend open fun update(entity: T, block: T.() -> Unit) = 
            transaction(db) { entity.apply(block) }

    suspend open fun update(where: WhereExpression, block: T.() -> Unit) = 
            transaction(db) { find(where).forEach { it.apply(block) } }

    suspend open fun delete(entity: T) = 
            transaction(db) { entity.delete() }

    suspend open fun delete(where: WhereExpression) = 
            transaction(db) { find(where).forEach { it.delete() } }

    suspend open fun <R : Comparable<R>> readSorted(
            selector: (T) -> R?,
            where: WhereExpression = { TRUE }
    ) = transaction(db) { find(where).sortedBy(selector).toList() }
}

abstract class IntIDDAO<T : IntIDEntity>(
    table: IntIDTable, 
    val db: Database? = null
) : IntEntityClass<T>(table) {

    suspend open fun create(block: T.() -> Unit): T = 
            transaction(db) { new(block) }

    suspend open fun read(where: WhereExpression = { TRUE }) = 
            transaction(db) { find(where).toList() }

    suspend open fun readOne(where: WhereExpression): T? = 
            transaction(db) { find(where).firstOrNull() }

    suspend open fun update(entity: T, block: T.() -> Unit) = 
            transaction(db) { entity.apply(block) }

    suspend open fun update(where: WhereExpression, block: T.() -> Unit) = 
            transaction(db) { find(where).forEach { it.apply(block) } }

    suspend open fun delete(entity: T) = 
            transaction(db) { entity.delete() }

    suspend open fun delete(where: WhereExpression) = 
            transaction(db) { find(where).forEach { it.delete() } }

    suspend open fun <R : Comparable<R>> readSorted(
            selector: (T) -> R?,
            where: WhereExpression = { TRUE }
    ) = transaction(db) { find(where).sortedBy(selector).toList() }
}
