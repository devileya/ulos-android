package com.devileya.ulos.view

import android.graphics.Bitmap
import android.graphics.text.LineBreaker.JUSTIFICATION_MODE_INTER_WORD
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.devileya.ulos.R
import kotlinx.android.synthetic.main.fragment_ulos_detail.*


class UlosDetailFragment : Fragment() {
//    ['Bintang Maratur', 'Mangiring', 'Ragi Hotang', 'Sibolang', "Suri-Suri"]

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ulos_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            tv_name.text = it.get("name").toString()
            iv_ulos_photo.setImageBitmap(it.get("image") as Bitmap)
            when (it.get("id")) {
                0 -> {
                    tv_desc.text = getString(R.string.ulos_bintang_maratur_desc)
                    tv_cultural_background.text = getString(R.string.ulos_bintang_maratur_cb)
                    tv_function.text = getString(R.string.ulos_bintang_maratur_function)
                }
                1 -> {
                    tv_desc.text = getString(R.string.ulos_mangiring_desc)
                    tv_cultural_background.text = getString(R.string.ulos_mangiring_cb)
                    tv_function.text = getString(R.string.ulos_mangiring_function)
                }
                2 -> {
                    tv_desc.text = getString(R.string.ulos_ragi_hotang_desc)
                    tv_cultural_background.text = getString(R.string.ulos_ragi_hotang_cb)
                    tv_function.text = getString(R.string.ulos_ragi_hotang_function)
                }
                3 -> {
                    tv_desc.text = getString(R.string.ulos_sibolang_desc)
                    tv_cultural_background.text = getString(R.string.ulos_sibolang_cb)
                    tv_function.text = getString(R.string.ulos_sibolang_function)
                }
                4 -> {
                    tv_desc.text = getString(R.string.ulos_suri_suri_desc)
                    tv_cultural_background.text = getString(R.string.ulos_suri_suri_cb)
                    tv_function.text = getString(R.string.ulos_suri_suri_function)
                }
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                tv_desc.justificationMode = JUSTIFICATION_MODE_INTER_WORD
            }
        }
    }
}