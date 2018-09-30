package com.example.abdelazim.code_05_notifymetodolist.database;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.Date;

public class AppTypeConverter {

    @TypeConverter
    public static Long fromDateToLong(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static Date fromLongToDate(Long time) {
        return time == null ? null : new Date(time);
    }

    @TypeConverter
    public static String fromCalendarToString(Calendar calendar) {
        return calendar == null ? null : new Gson().toJson(calendar);
    }

    @TypeConverter
    public static Calendar fromJsonToCalendar(String json) {
        Type type = new TypeToken<Calendar>() {
        }.getType();
        return json == null ? null : (Calendar) new Gson().fromJson(json, type);
    }
}
