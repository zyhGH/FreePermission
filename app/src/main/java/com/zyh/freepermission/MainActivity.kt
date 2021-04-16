package com.zyh.freepermission

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.zyh.annotation.Permission
import com.zyh.permission.FreePermission
import com.zyh.permission.PermissionUtils.READ_PHONE_PERMISSION

open class MainActivity : AppCompatActivity() {
    var tv: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FreePermission.bind(this)

        tv = findViewById(R.id.tv)
    }

    @Permission(R.id.tv, READ_PHONE_PERMISSION)
    fun onClick() {
        val intent = Intent(this, TestActivity::class.java)
        startActivity(intent)
    }
}