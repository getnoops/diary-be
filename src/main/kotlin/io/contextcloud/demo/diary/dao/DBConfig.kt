package io.contextcloud.demo.diary

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.contextcloud.demo.diary.dao.DiaryEntryTable
import jakarta.annotation.PostConstruct
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class DBConnectionPoolConfig(
    @Autowired val db: DbConfigProperties
) {
    companion object {
        val log: Logger = LoggerFactory.getLogger(this::class.java)
        private lateinit var config: HikariConfig
        lateinit var dataSource: HikariDataSource
    }

    @PostConstruct
    fun postConstruct() {
        log.info("Initializing Hikari Pool Config...")
        kotlin.runCatching {
            config = HikariConfig().apply {
                jdbcUrl = db.url
                username = db.username
                password = db.password
                driverClassName = db.driver
                addDataSourceProperty("cachePrepStmts", "true")
                addDataSourceProperty("prepStmtCacheSize", "250")
                addDataSourceProperty("reWriteBatchedInserts", "true")
            }

            dataSource = HikariDataSource(config).apply {
                maximumPoolSize = db.poolSize
            }

            log.info("Hikari Pool is now initialized.")
        }.getOrElse {
            log.error("Could not create Hikari Pool", it)
        }
    }
}

@Component
class DatabaseInit(
    @Autowired val dbConnectionConfig: DBConnectionPoolConfig
) {
    companion object {
        val log: Logger = LoggerFactory.getLogger(this::class.java)
    }

    @PostConstruct
    fun postConstruct() {
        log.info("Connecting to DB and performing DDLs...")
        kotlin.runCatching {
            Database.connect(DBConnectionPoolConfig.dataSource)
            transaction {
                SchemaUtils.create(DiaryEntryTable)
            }
            log.info("DB connected. Tables created.")
        }.getOrElse {
            log.error("Could not connect to DB/perform DDLs", it)
        }
    }
}
