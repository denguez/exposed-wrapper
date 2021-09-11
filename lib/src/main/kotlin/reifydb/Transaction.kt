package reifydb

import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.transactions.transaction as sqlTransaction

suspend fun <T> transaction(block: () -> T) = 
    withContext(Dispatchers.IO) { 
        sqlTransaction { block() } 
    }