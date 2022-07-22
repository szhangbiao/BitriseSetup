package com.szhangbiao.storage.utils

import android.os.Build
import java.util.*
import java.util.concurrent.ThreadLocalRandom

object RandomUtil {
    fun randomInt(bound: Int): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ThreadLocalRandom.current().nextInt(bound)
        } else {
            Random().nextInt(bound)
        }
    }
}
