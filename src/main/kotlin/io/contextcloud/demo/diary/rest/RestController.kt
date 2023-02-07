package io.contextcloud.demo.diary.rest

import io.contextcloud.demo.diary.model.DiaryEntry
import io.contextcloud.demo.diary.service.DiaryEntryProcessor
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class RestController(@Autowired val processor: DiaryEntryProcessor) {

    companion object {
        val log: Logger = LoggerFactory.getLogger(this::class.java)
    }

    @GetMapping("/entries")
    fun getTopDiaryPage(): List<DiaryEntry> = processor.fetchDiaryEntries().also{
        log.info("Boom!!!")
    }

    @PostMapping("/entry")
    fun createEntry(@RequestBody entry: CreateDiaryEntryRequest) =
        processor.createDiaryEntry(entry.text)
}

data class CreateDiaryEntryRequest(val text: String)