package io.contextcloud.demo.diary.dao

import io.contextcloud.demo.diary.DatabaseInit
import io.contextcloud.demo.diary.dao.DiaryEntryTable.createdTs
import io.contextcloud.demo.diary.dao.DiaryEntryTable.message
import io.contextcloud.demo.diary.model.DiaryEntry
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.Instant

object DiaryEntryTable: Table() {
    val id = long("id").autoIncrement()
    val createdTs = long("created")
    val message = varchar("message", 250)
}

@Component
class DiaryEntryDao(@Autowired val dbInit: DatabaseInit) {

    fun loadEntities(maxEntries: Int): List<DiaryEntry> =
        transaction {
            DiaryEntryTable
                .selectAll()
                .orderBy(createdTs to SortOrder.DESC)
                .limit(maxEntries)
                .map { it.toDiaryEntry() }
        }

    fun createEntity(msg: String) {
        transaction {
            DiaryEntryTable.insert {
                it[createdTs] = Instant.now().toEpochMilli()
                it[message] = msg
            }
        }
    }
}

fun ResultRow.toDiaryEntry() = DiaryEntry(this[createdTs], null, this[message])