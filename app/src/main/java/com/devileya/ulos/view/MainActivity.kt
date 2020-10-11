package com.devileya.ulos.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.devileya.ulos.R
import com.devileya.ulos.utils.CustomProgress
import com.devileya.ulos.view_model.BaseViewModel.Companion.showError
import com.devileya.ulos.view_model.BaseViewModel.Companion.showLoading

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val customProgress = CustomProgress(this)

        showLoading.observe(this, Observer {
            if (it) customProgress.showLoading() else customProgress.hideLoading()
        })

        showError.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
    }
}