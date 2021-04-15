package com.zyh.freepermission

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.zyh.annotation.Permission
import com.zyh.freepermission.PermissionUtils.READ_PHONE_PERMISSION

open class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FreePermission.bind(this)
    }

    @Permission(R.id.tv, READ_PHONE_PERMISSION)
    fun onClick() {
        val intent = Intent(this, TestActivity::class.java)
        startActivity(intent)
    }
}