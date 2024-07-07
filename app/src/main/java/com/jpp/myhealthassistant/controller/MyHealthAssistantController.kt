package com.jpp.myhealthassistant.controller

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.widget.Toast
import com.jpp.myhealthassistant.broker.SqliteBroker
import com.jpp.myhealthassistant.helper.DBHelper
import com.jpp.myhealthassistant.model.Measurement
import com.jpp.myhealthassistant.model.Scale
import com.jpp.myhealthassistant.model.User
import com.jpp.myhealthassistant.utils.DBTools
import com.jpp.myhealthassistant.utils.ToastHandler
import com.jpp.myhealthassistant.model.Unit;

class MyHealthAssistantController(context: Context)
{
    val context = context;
    val DBBroker:SqliteBroker = SqliteBroker(context)
    val dbt:DBTools = DBTools();
    val th:ToastHandler = ToastHandler(context);
    fun loginUser(u: User):User?
    {
        try {

            if (chkLoggedIn(u) == false) {
                DBBroker.executeNonQuery("UPDATE USER SET LOGGED_IN=1 WHERE USERNAME='" + u.Username + "';")
                return getUserByUsername(u.Username);
            }
            else
            {
                th.showToast("Error: User already logged in.",Toast.LENGTH_SHORT);
                return null;
            }
        }
        catch(e:Exception)
        {
            th.showToast("Error: "+e.message,Toast.LENGTH_SHORT);
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
            th.showToast("Error: "+e.message,Toast.LENGTH_SHORT);
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
            th.showToast("Error: "+e.message,Toast.LENGTH_SHORT);
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
            th.showToast("Error: "+e.message,Toast.LENGTH_SHORT);
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
            th.showToast("Error: "+e.message,Toast.LENGTH_SHORT);
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
            th.showToast("Error: "+e.message,Toast.LENGTH_SHORT);
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
            th.showToast("Error: "+e.message,Toast.LENGTH_SHORT);
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
            th.showToast("Error: "+e.message,Toast.LENGTH_SHORT);
        }
    }

    fun insertMeasurement(m: Measurement)
    {
        try
        {
            DBBroker.executeNonQuery("INSERT INTO USER_MEASUREMENTS(USER_ID,VALUE,SCALE,POSITIONAL) VALUES("+m.User?.ID+","+m.Value+","+m.Scale?.ID+","+m.Positional+");")
            if(m.Positional==1)
            {
                val id = DBBroker.executeScalar("SELECT ID FROM USER_MEASUREMENTS ORDER BY ID DESC LIMIT 1")
                DBBroker.executeNonQuery("INSERT INTO MEASUREMENT_LOCATION(MEASUREMENT_ID,LOCATION,REFERRED,PRECISE) VALUES("+id+","+m.Location?.value+",0,0);")
            }
        }
        catch(e:Exception)
        {
            th.showToast("Error: "+e.message,Toast.LENGTH_SHORT);
        }
    }

    fun getScaleByID(id:Int): Scale?
    {
        try {


            var ret = Scale();
            var cursor: Cursor? = DBBroker.executeQuery("SELECT * FROM SCALE WHERE ID=" + id);
            while (cursor?.moveToNext()!!) {
                ret.ID=dbt.getInt(cursor,"ID");
                ret.Name=dbt.getString(cursor,"NAME")!!;
                ret.Unit=getUnitByID(dbt.getInt(cursor,"UNIT")!!);
                ret.Step=dbt.getFloat(cursor,"STEP");
                ret.Description=dbt.getString(cursor,"DESCRIPTION");
                ret.Categoric=dbt.getInt(cursor,"CATEGORIC")!!;
                ret.MaxValue=dbt.getFloat(cursor,"MAX_VALUE");
                ret.MinValue=dbt.getFloat(cursor,"MIN_VALUE");
            }
            return ret;
        }
        catch(e:Exception)
        {
            th.showToast("Error: "+e.message,Toast.LENGTH_SHORT);
        }
        return null;
    }

    fun getScaleByName(name:String): Scale?
    {
        try {


            var ret = Scale();
            var cursor: Cursor? = DBBroker.executeQuery("SELECT * FROM SCALE WHERE NAME='" +name+"';");
            while (cursor?.moveToNext()!!) {
                ret.ID=dbt.getInt(cursor,"ID");
                ret.Name=dbt.getString(cursor,"NAME")!!;
                ret.Unit=getUnitByID(dbt.getInt(cursor,"UNIT")!!);
                ret.Step=dbt.getFloat(cursor,"STEP");
                ret.Description=dbt.getString(cursor,"DESCRIPTION");
                ret.Categoric=dbt.getInt(cursor,"CATEGORIC")!!;
                ret.MaxValue=dbt.getFloat(cursor,"MAX_VALUE");
                ret.MinValue=dbt.getFloat(cursor,"MIN_VALUE");
            }
            return ret;
        }
        catch(e:Exception)
        {
            th.showToast("Error: "+e.message,Toast.LENGTH_SHORT);
        }
        return null;
    }

    fun getUnitByID(id:Int):Unit?
    {
        try {


            var ret = Unit();
            var cursor: Cursor? = DBBroker.executeQuery("SELECT * FROM MEASUREMENT_UNIT WHERE ID=" + id);
            while (cursor?.moveToNext()!!) {
                ret.ID=dbt.getInt(cursor,"ID")!!;
                ret.Acronym=dbt.getString(cursor,"ACRONYM")!!;
                ret.Fullname=dbt.getString(cursor,"FULLNAME")!!;
                ret.Description=dbt.getString(cursor,"DESCRIPTION");
            }
            return ret;
        }
        catch(e:Exception)
        {
            th.showToast("Error: "+e.message,Toast.LENGTH_SHORT);
        }
        return null;
    }
}