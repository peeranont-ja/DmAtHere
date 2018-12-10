package com.kku.pharm.project.dmathere.data.local

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kku.pharm.project.dmathere.data.model.AlarmTimeInformationList


object PreferenceHelper {
    private lateinit var mPref: SharedPreferences
    private lateinit var mGson: Gson
    private const val FILE_NAME = "customer_pref_file"
    const val ALARM_TIME_INFORMATION_LIST = "alarm_time_information_list"

    fun initPreferenceHelper(context: Context) {
        mPref = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
        mGson = GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSz")
                .create()
    }

    fun clear() {
        mPref.edit().clear().apply()
    }


    var alarmTimeInformationList: AlarmTimeInformationList?
        get() {
            return mGson.fromJson(
                    mPref.getString(ALARM_TIME_INFORMATION_LIST, null),
                    AlarmTimeInformationList::class.java)
        }
        set(inputData) = mPref.edit()
                .putString(
                        ALARM_TIME_INFORMATION_LIST,
                        mGson.toJson(inputData))
                .apply()
}