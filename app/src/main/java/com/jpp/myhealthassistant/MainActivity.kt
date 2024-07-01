package com.jpp.myhealthassistant

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.jpp.myhealthassistant.utils.DBTools

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var d:DBTools = DBTools();
        d.copyDatabase(this)
        setContentView(R.layout.activity_main)
    }

}
