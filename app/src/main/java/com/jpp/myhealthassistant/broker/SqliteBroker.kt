package com.jpp.myhealthassistant.broker

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteOpenHelper
import com.jpp.myhealthassistant.helper.DBHelper
import com.jpp.myhealthassistant.model.User

class SqliteBroker(context: Context)
{
    private val databaseHelper = DBHelper(context);
    fun executeScalar(query: String,arguments:Array<String>?=null): Any? {
        val db = databaseHelper.readableDatabase;
        db.use { database ->
            database.rawQuery(query, arguments).use { cursor ->
                return if (cursor.moveToFirst()) {
                    when (cursor.getType(0)) {
                        Cursor.FIELD_TYPE_INTEGER -> cursor.getInt(0)
                        Cursor.FIELD_TYPE_FLOAT -> cursor.getFloat(0)
                        Cursor.FIELD_TYPE_STRING -> cursor.getString(0)
                        Cursor.FIELD_TYPE_BLOB -> cursor.getBlob(0)
                        else -> null
                    }
                } else {
                    null
                }
            }
        }
    }

    fun executeNonQuery(query: String,arguments:Array<String>?=null) {
        val db = databaseHelper.writableDatabase;
        db.use { database ->
            database.execSQL(query)
        }
    }

    fun executeQuery(query: String,arguments:Array<String>?=null): Cursor? {
        val db = databaseHelper.readableDatabase
        return db.rawQuery(query, arguments)
    }
}