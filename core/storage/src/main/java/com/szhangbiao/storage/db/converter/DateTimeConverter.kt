package com.szhangbiao.storage.db.converter

import androidx.room.TypeConverter
import org.joda.time.DateTime

class DateTimeConverter {

    @TypeConverter
    fun fromTimestamp(timestamp: Long?): DateTime {
        return timestamp?.let { DateTime(it) } ?: DateTime.now()
    }

    @TypeConverter
    fun dateToTimestamp(dateTime: DateTime?): Long {
        return dateTime?.millis ?: System.currentTimeMillis()
    }
}
