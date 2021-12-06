package reifydb

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SqlExpressionBuilder
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateStatement
import org.jetbrains.exposed.sql.transactions.transaction as sqlTransaction

typealias WhereExpression = SqlExpressionBuilder.() -> Op<Boolean>

typealias CreateExpression = Table.(InsertStatement<Number>) -> Unit
typealias UpdateExpression = Table.(UpdateStatement) -> Unit

suspend fun <T> transaction(db: Database? = null, block: () -> T) =
        withContext(Dispatchers.IO) { sqlTransaction(db) { block() } }
