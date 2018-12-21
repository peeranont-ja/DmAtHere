package com.kku.pharm.project.dmathere.data.local

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kku.pharm.project.dmathere.data.model.AlarmTimeInformation

object PreferenceHelper {
    private lateinit var mPref: SharedPreferences
    private lateinit var mGson: Gson
    private const val FILE_NAME = "customer_pref_file"
    private const val ALARM_TIME_MORNING_INFORMATION = "alarm_time_morning_information"
    private const val ALARM_TIME_AFTERNOON_INFORMATION = "alarm_time_afternoon_information"
    private const val ALARM_TIME_EVENING_INFORMATION = "alarm_time_evening_information"
    private const val ALARM_TIME_NIGHT_INFORMATION = "alarm_time_night_information"

    fun initPreferenceHelper(context: Context) {
        mPref = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
        mGson = GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSz")
                .create()
    }

    fun clear() {
        mPref.edit().clear().apply()
    }


    var alarmTimeMorningInfo: AlarmTimeInformation?
        get() {
            return mGson.fromJson(
                    mPref.getString(ALARM_TIME_MORNING_INFORMATION, null),
                    AlarmTimeInformation::class.java)
        }
        set(inputData) = mPref.edit()
                .putString(
                        ALARM_TIME_MORNING_INFORMATION,
                        mGson.toJson(inputData))
                .apply()

    var alarmTimeAfternoonInfo: AlarmTimeInformation?
        get() {
            return mGson.fromJson(
                    mPref.getString(ALARM_TIME_AFTERNOON_INFORMATION, null),
                    AlarmTimeInformation::class.java)
        }
        set(inputData) = mPref.edit()
                .putString(
                        ALARM_TIME_AFTERNOON_INFORMATION,
                        mGson.toJson(inputData))
                .apply()

    var alarmTimeEveningInfo: AlarmTimeInformation?
        get() {
            return mGson.fromJson(
                    mPref.getString(ALARM_TIME_EVENING_INFORMATION, null),
                    AlarmTimeInformation::class.java)
        }
        set(inputData) = mPref.edit()
                .putString(
                        ALARM_TIME_EVENING_INFORMATION,
                        mGson.toJson(inputData))
                .apply()

    var alarmTimeNightInfo: AlarmTimeInformation?
        get() {
            return mGson.fromJson(
                    mPref.getString(ALARM_TIME_NIGHT_INFORMATION, null),
                    AlarmTimeInformation::class.java)
        }
        set(inputData) = mPref.edit()
                .putString(
                        ALARM_TIME_NIGHT_INFORMATION,
                        mGson.toJson(inputData))
                .apply()
}