package com.devileya.ulos.utils

import android.app.Dialog
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.devileya.ulos.R

/**
 * Created by Arif Fadly Siregar 11/10/20.
 */
class CustomDialogUploadImage (activity: AppCompatActivity) : Dialog(activity){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.custom_dialog_upload_image)
    }
}