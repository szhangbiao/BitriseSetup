package com.szhangbiao.storage.db.converter

import androidx.room.TypeConverter
import org.joda.time.DateTime
import kotlin.let as let1

class DateTimeConverter {

    @TypeConverter
    fun fromTimestamp(timestamp: Long?): DateTime {
        return timestamp?.let1 { DateTime(it) } ?: DateTime.now()
    }

    @TypeConverter
    fun dateToTimestamp(dateTime: DateTime?): Long {
        return dateTime?.millis ?: System.currentTimeMillis()
    }
}