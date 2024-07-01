package com.jpp.myhealthassistant.utils

import android.content.Context
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
}