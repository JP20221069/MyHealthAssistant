package com.jpp.myhealthassistant.utils

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import androidx.core.database.getDoubleOrNull
import androidx.core.database.getFloatOrNull
import androidx.core.database.getIntOrNull
import androidx.core.database.getStringOrNull
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

class DBTools()
{
    fun deleteDatabase(context: Context) {
        val databaseName = "master.db"
        val dbFile = context.getDatabasePath(databaseName)

        if (dbFile.exists()) {
            dbFile.delete()
            println("Database $databaseName deleted successfully.")
        } else {
            println("Database $databaseName does not exist.")
        }
    }
     fun copyDatabase(context: Context) {
        val databaseName = "master.db"
        val dbPath = context.getDatabasePath(databaseName).absolutePath

        val databaseFile = context.getDatabasePath(databaseName)
        if (!databaseFile.exists()) {
            databaseFile.parentFile?.mkdirs()

            try {
                val inputStream: InputStream = context.assets.open(databaseName)
                val outputStream: OutputStream = FileOutputStream(dbPath)

                val buffer = ByteArray(1024)
                var length: Int

                while (inputStream.read(buffer).also { length = it } > 0) {
                    outputStream.write(buffer, 0, length)
                }

                outputStream.flush()
                outputStream.close()
                inputStream.close()

                println("Database copied to: $dbPath")
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
    
    @SuppressLint("Range")
    fun getInt(cursor: Cursor, col_name:String):Int?
    {
        return cursor.getIntOrNull(cursor.getColumnIndex(col_name))
    }

    @SuppressLint("Range")
    fun getString(cursor: Cursor, col_name:String):String?
    {
        return cursor.getStringOrNull(cursor.getColumnIndex(col_name))
    }

    @SuppressLint("Range")
    fun getFloat(cursor: Cursor, col_name:String):Float?
    {
        return cursor.getFloatOrNull(cursor.getColumnIndex(col_name))
    }

    @SuppressLint("Range")
    fun getDouble(cursor: Cursor, col_name:String):Double?
    {
        return cursor.getDoubleOrNull(cursor.getColumnIndex(col_name))
    }

    
}