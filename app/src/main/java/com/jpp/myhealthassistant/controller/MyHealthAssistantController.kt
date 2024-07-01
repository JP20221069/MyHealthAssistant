package com.jpp.myhealthassistant.controller

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.widget.Toast
import com.jpp.myhealthassistant.broker.SqliteBroker
import com.jpp.myhealthassistant.helper.DBHelper
import com.jpp.myhealthassistant.model.User

class MyHealthAssistantController(context: Context)
{
    val context = context;
    val DBBroker:SqliteBroker = SqliteBroker(context)
    fun loginUser(u: User):User?
    {
        try {

            if (chkLoggedIn(u) == false) {
                DBBroker.executeNonQuery("UPDATE USER SET LOGGED_IN=1 WHERE USERNAME='" + u.Username + "';")
                return getUserByUsername(u.Username);
            }
            else
            {
                Toast.makeText(context,"Error: User already logged in.",Toast.LENGTH_SHORT);
                return null;
            }
        }
        catch(e:Exception)
        {
            Toast.makeText(context,"Error: "+e.message,Toast.LENGTH_SHORT);
        }
        return null;
    }

    fun logoutUser(u: User)
    {
        try {

            if (chkLoggedIn(u) == true) {
                DBBroker.executeNonQuery("UPDATE USER SET LOGGED_IN=0 WHERE USERNAME='" + u.Username+ "';")
            }
        }
        catch(e:Exception)
        {
            Toast.makeText(context,"Error: "+e.message,Toast.LENGTH_SHORT);
        }
    }

    fun logoutUser(id: Int)
    {
        try {

            if (chkLoggedIn(id) == true) {
                DBBroker.executeNonQuery("UPDATE USER SET LOGGED_IN=0 WHERE ID='" + id + "';")
            }
        }
        catch(e:Exception)
        {
            Toast.makeText(context,"Error: "+e.message,Toast.LENGTH_SHORT);
        }
    }

    fun chkLoggedIn(u: User) : Boolean?
    {
        try {

            var ret = true;
            val loggedin =
                DBBroker.executeScalar("SELECT LOGGED_IN FROM USER WHERE USERNAME='" + u.Username + "';");
            if (loggedin is Int) {
                if (loggedin == 0) {
                    ret = false;
                } else {
                    ret = true;
                }
            }
            return ret;
        }
        catch(e:Exception)
        {
            Toast.makeText(context,"Error: "+e.message,Toast.LENGTH_SHORT);
        }
        return null;
    }

    fun chkLoggedIn(id: Int) : Boolean?
    {
        try {

            var ret = true;
            val loggedin =
                DBBroker.executeScalar("SELECT LOGGED_IN FROM USER WHERE ID="+id+";");
            if (loggedin is Int) {
                if (loggedin == 0) {
                    ret = false;
                } else {
                    ret = true;
                }
            }
            return ret;
        }
        catch(e:Exception)
        {
            Toast.makeText(context,"Error: "+e.message,Toast.LENGTH_SHORT);
        }
        return null;
    }

    @SuppressLint("Range")
    fun getUserByUsername(username:String):User?
    {
        try {


            var ret = User();
            var cursor: Cursor? =
                DBBroker.executeQuery("SELECT * FROM USER WHERE USERNAME='" + username+"';");
            while (cursor?.moveToNext()!!) {
                ret.Username = cursor?.getString(cursor?.getColumnIndex("USERNAME")!!)!!;
                ret.LoggedIn = when (cursor?.getInt(cursor?.getColumnIndex("LOGGED_IN")!!)) {
                    0 -> false;else -> true
                };
                ret.E_mail = cursor?.getString(cursor?.getColumnIndex("EMAIL")!!)!!;
                ret.ID = cursor?.getInt(cursor?.getColumnIndex("ID")!!)!!;
            }
            return ret;
        }
        catch(e:Exception)
        {
            Toast.makeText(context,"Error: "+e.message,Toast.LENGTH_SHORT);
        }
        return null;
    }
    @SuppressLint("Range")
    fun getUserByID(id:Int):User?
    {
        try {


            var ret = User();
            var cursor: Cursor? = DBBroker.executeQuery("SELECT * FROM USER WHERE ID=" + id);
            while (cursor?.moveToNext()!!) {
                ret.Username = cursor?.getString(cursor?.getColumnIndex("USERNAME")!!)!!;
                ret.LoggedIn = when (cursor?.getInt(cursor?.getColumnIndex("LOGGED_IN")!!)) {
                    0 -> false;else -> true
                };
                ret.E_mail = cursor?.getString(cursor?.getColumnIndex("EMAIL")!!)!!;
                ret.ID = cursor?.getInt(cursor?.getColumnIndex("ID")!!)!!;
            }
            return ret;
        }
        catch(e:Exception)
        {
            Toast.makeText(context,"Error: "+e.message,Toast.LENGTH_SHORT);
        }
        return null;
    }

    fun insertUser(u:User)
    {
        try {
            DBBroker.executeNonQuery("INSERT INTO USER(USERNAME,PASS,EMAIL,LOGGED_IN) VALUES('" + u.Username + "','" + u.Password + "','" + u.E_mail + "',0);");
        }
        catch(e:Exception)
        {
            Toast.makeText(context,"Error: "+e.message,Toast.LENGTH_SHORT);
        }
    }
}