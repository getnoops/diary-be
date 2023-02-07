package io.contextcloud.demo.diary.service

import io.contextcloud.demo.diary.dao.DiaryEntryDao
import io.contextcloud.demo.diary.model.DiaryEntry
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.text.SimpleDateFormat

@Component
class DiaryEntryProcessor(@Autowired val diaryDao: DiaryEntryDao) {

    companion object {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy hh:mm")
    }

    fun createDiaryEntry(msg: String) = diaryDao.createEntity(msg.take(250))

    fun fetchDiaryEntries(limit: Int = 10) =
        diaryDao
            .loadEntities(limit)
            .map { DiaryEntry(dateFormat.format(it.dateTime.toLong()), it.text) }

}