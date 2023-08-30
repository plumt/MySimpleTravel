package com.yun.mysimpletravel.util

import android.content.Context
import android.content.SharedPreferences

object PreferenceUtil {
    private const val PREFERENCES_NAME = "MySimpleTravel"
    private const val DEFAULT_VALUE_STRING = ""
    private const val DEFAULT_VALUE_BOOLEAN = false
    private const val DEFAULT_VALUE_INT = -1
    private const val DEFAULT_VALUE_LONG = -1L
    private const val DEFAULT_VALUE_FLOAT = -1f
    open fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    fun setString(context: Context, key: String?, value: String?) {
        val prefs = getPreferences(context)
        val editor = prefs.edit()
        editor.putString(key, value)
        editor.commit()
    }

    fun setBoolean(context: Context, key: String?, value: Boolean) {
        val prefs = getPreferences(context)
        val editor = prefs.edit()
        editor.putBoolean(key, value)
        editor.commit()
    }

    fun setInt(context: Context, key: String?, value: Int) {
        val prefs = getPreferences(context)
        val editor = prefs.edit()
        editor.putInt(key, value)
        editor.commit()
    }

    fun getString(context: Context, key: String?): String? {
        val prefs = getPreferences(context)
        return prefs.getString(key, DEFAULT_VALUE_STRING)
    }

    fun getBoolean(context: Context, key: String?): Boolean? {
        val prefs = getPreferences(context)
        return prefs.getBoolean(key, false)
    }

    fun getInt(context: Context, key: String?): Int? {
        val prefs = getPreferences(context)
        return prefs.getInt(key, DEFAULT_VALUE_INT)
    }

    fun getAll(context: Context): MutableCollection<out Any?> {
        val prefs = getPreferences(context)
        return prefs.all.keys
    }

    fun clearData(context: Context, keys: ArrayList<String>) {
        val prefs = getPreferences(context)
        keys.forEach { key ->
            prefs.edit().remove(key).apply()
        }
    }
}