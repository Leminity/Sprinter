package com.tistory.leminity.sprinter.framework

import android.util.Log

private var TAG = "com.tistory.leminity.sprinter";

class SLog {

    companion object {
        fun d(message: String) = d(null, message)
        fun d(tag: String?, message: String) = if (tag == null) Log.d(TAG, message) else Log.d(tag, message)
    }

}