package com.szhangbiao.storage.preference

interface ILocalStorage {

    val deviceSerialNumber: String

    var userId: Int

    var lastBuyBackEmail: String

    var userName: String

    var initialSetupTime: Long

    var lastUserSession: Long

    fun getDarkModeEnabled(): Boolean

    fun toggleDarkMode()

    fun clearData()
}
