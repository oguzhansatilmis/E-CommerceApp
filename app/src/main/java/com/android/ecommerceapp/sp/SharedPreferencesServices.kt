package com.android.ecommerceapp.sp

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPreferencesServices@Inject constructor(@ApplicationContext context: Context) {

    @Inject
    lateinit var gson: Gson

    val sharedPreferences =
        context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)!!

    fun save(model: Any, key: SharedPreferencesKey) {
        val json = gson.toJson(model)

        sharedPreferences
            .edit()
            .putString(key.value, json)
            .apply()
    }

    inline fun <reified T> fetch(key: SharedPreferencesKey): T? {
        val json = sharedPreferences.getString(key.value, null) ?: return null
        return gson.fromJson(json, object : TypeToken<T>() {}.type)
    }


}