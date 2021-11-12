package reifydb

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.id.EntityID

typealias ID<T> = EntityID<T>

typealias IntID = EntityID<Int>

abstract class Entity<T : Comparable<T>>(id: ID<T>) : Entity<T>(id)

abstract class IDEntity(id: IntID) : IntEntity(id)
