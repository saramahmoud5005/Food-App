package com.example.test_food_app.database

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.util.jar.Attributes

@TypeConverters
class MealTypeConverter {
    @TypeConverter
    fun fromAnyToString(attribute: Any?):String{

        if(attribute==null) return ""

        return attribute as String
    }

    @TypeConverter
    fun fromAnyToString(attribute: String?):Any{

        if(attribute==null) return ""

        return attribute
    }
}