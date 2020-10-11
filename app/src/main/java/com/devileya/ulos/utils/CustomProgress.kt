package com.devileya.ulos.utils

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import com.devileya.ulos.R

/**
 * Created by Arif Fadly Siregar 29/08/20.
 */
class CustomProgress(ctx: Context) : Dialog(ctx) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.custom_progress_bar)
        window?.setBackgroundDrawableResource(android.R.color.transparent)
    }

    fun showLoading(cancelable: Boolean = false) {
        try {
            this.setCancelable(cancelable)
            this.setCanceledOnTouchOutside(cancelable)
            this.show()
        } catch (e: Exception) {}
    }

    fun hideLoading() {
        try {
            this.dismiss()
        } catch (e: Exception) {}
    }
}