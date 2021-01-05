package com.ctl.indicator.demo.base

/**
 * created by : chentl
 * Date: 2020/07/14
 */
inline fun <T, R> T.dowithTry(block: (T) -> R) {
    try {
        block(this)
    } catch (e: Throwable) {
        e.printStackTrace()
    }
}