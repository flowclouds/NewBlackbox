package com.tm.box.sample.view.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.tm.box.TmBox
import com.tm.box.sample.util.InjectionUtil
import com.tm.box.sample.view.list.ListViewModel

class WelcomeActivity : AppCompatActivity() {

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        jump()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        previewInstalledAppList()
        jump()
    }

    private fun jump() {
        MainActivity.start(this)
        finish()
    }

    private fun previewInstalledAppList(){
        val viewModel = ViewModelProvider(this,InjectionUtil.getListFactory()).get(ListViewModel::class.java)
        viewModel.previewInstalledList()
    }
}