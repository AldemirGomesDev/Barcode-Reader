package com.aldemir.barcodereader.util

import android.util.Log
import com.aldemir.barcodereader.BuildConfig

class LogUtils {
    companion object {
        private const val TAG = "logcat"

        fun debug(tag: String = TAG, message: String) {
            if (BuildConfig.DEBUG) {
                Log.d(tag, message)
            }
        }

        fun verbose(tag: String = TAG, message: String) {
            if (BuildConfig.DEBUG) {
                Log.v(tag, message)
            }
        }

        fun warning(tag: String = TAG, message: String) {
            if (BuildConfig.DEBUG) {
                Log.w(tag, message)
            }
        }

        fun info(tag: String = TAG, message: String) {
            if (BuildConfig.DEBUG) {
                Log.i(tag, message)
            }
        }

        fun error(tag: String = TAG, message: String) {
            Log.e(tag, message)
        }
    }
}