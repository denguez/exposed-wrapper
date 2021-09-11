package reifydb

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID as SQLEntityID
import org.jetbrains.exposed.sql.Op.TRUE

typealias Entity = IntEntity

typealias EntityID = SQLEntityID<Int>

abstract class DAO<T : Entity>(table: Table) : IntEntityClass<T>(table) {

    suspend open fun create(block: T.() -> Unit): T = transaction { new(block) }

    suspend open fun readById(id: Int): T? = transaction { findById(id) }
    suspend open fun readOne(where: WhereExpression): T? = transaction { find(where).firstOrNull() }
    suspend open fun read(where: WhereExpression = { TRUE }) = transaction { find(where) }.toList()

    suspend open fun update(id: Int, block: T.() -> Unit) = transaction {
        findById(id)?.apply(block)
    }
    suspend open fun update(entity: T, block: T.() -> Unit) = transaction { entity.apply(block) }
    suspend open fun update(where: WhereExpression, block: T.() -> Unit) = transaction {
        find(where).forEach { it.apply(block) }
    }

    suspend open fun delete(id: Int) = transaction { findById(id)?.delete() }
    suspend open fun delete(entity: T) = transaction { entity.delete() }
    suspend open fun delete(where: WhereExpression) = transaction {
        find(where).forEach { it.delete() }
    }

    suspend open fun <R : Comparable<R>> readSorted(
            selector: (T) -> R?,
            where: WhereExpression = { TRUE }
    ) = transaction { find(where).sortedBy(selector) }.toList()
}
