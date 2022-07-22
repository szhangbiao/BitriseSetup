package com.szhangbiao.storage.db.converter

import org.joda.time.DateTime
import org.joda.time.Seconds
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.util.Calendar

class DateTimeConverterTest {

    private var converter: DateTimeConverter? = null

    @Before
    fun setUp() {
        converter = DateTimeConverter()
    }

    @After
    fun tearDown() {
        converter = null
    }

    @Test
    fun testFromTimestamp() {
        val convertNow = converter?.fromTimestamp(null)
        Assert.assertEquals(Seconds.secondsBetween(DateTime.now(), convertNow).seconds, 0)
        val timestamp: Long = System.currentTimeMillis()
        val nowTime = DateTime(timestamp)
        val now = converter?.fromTimestamp(timestamp)
        Assert.assertEquals(now, nowTime)
    }

    @Test
    fun testDateToTimestamp() {
        // test non parameter
        Assert.assertEquals(System.currentTimeMillis(), converter?.dateToTimestamp(null))
        val year = 2022
        val monthOfYear = 7
        val dayOfMonth = 21
        val hourOfDay = 17
        val minuteOfHour = 48
        val dateTime = DateTime(year, monthOfYear, dayOfMonth, hourOfDay, minuteOfHour)
        val calendar: Calendar = Calendar.getInstance()
        calendar.clear()
        calendar.set(year, monthOfYear - 1, dayOfMonth, hourOfDay, minuteOfHour)
        Assert.assertEquals(converter?.dateToTimestamp(dateTime), calendar.timeInMillis)
    }
}
