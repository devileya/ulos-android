package com.devileya.ulos.view

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.ablanco.imageprovider.ImageProvider
import com.ablanco.imageprovider.ImageSource
import com.devileya.ulos.R
import com.devileya.ulos.utils.CustomDialogUploadImage
import com.devileya.ulos.view_model.UlosViewModel
import kotlinx.android.synthetic.main.custom_dialog_upload_image.*
import kotlinx.android.synthetic.main.fragment_select_image.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import timber.log.Timber
import java.io.ByteArrayOutputStream

const val PERMISSION_REQUEST_CODE = 200

class SelectImageFragment : Fragment() {

    private val viewModel by sharedViewModel<UlosViewModel>()

    private val imageProvider: ImageProvider by lazy { ImageProvider(requireActivity()) }
    private var image: Bitmap? = null
    private var scaledImage: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_select_image, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission()
        observeData()

        iv_ulos_photo.setOnClickListener { imageUploadDialog() }

        btn_submit.setOnClickListener {
            if (scaledImage == null)
                Toast.makeText(context, "Please add an image first!", Toast.LENGTH_SHORT).show()
            else
                viewModel.postUlosPrediction(scaledImage.toString())
        }
    }

    private fun imageUploadDialog() {
        try {
            val dialogUploadImage = CustomDialogUploadImage(activity as AppCompatActivity)
            dialogUploadImage.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialogUploadImage.show()
            dialogUploadImage.btn_camera.setOnClickListener {
                if (checkPermission()) {
                    getImage(ImageSource.CAMERA)
                } else {
                    requestPermission()
                }
                dialogUploadImage.dismiss()
            }
            dialogUploadImage.btn_gallery.setOnClickListener {
                getImage(ImageSource.GALLERY)
                dialogUploadImage.dismiss()
            }
        } catch (e: Exception) {
            Timber.e("error upload image dialog ${e.message}")
        }
    }

    private fun getImage(imageSource: ImageSource) {
        try {
            imageProvider.getImage(imageSource) {
                it?.let {
                    image = it
                    scaledImage = encodeToBase64(Bitmap.createScaledBitmap(it, 180, 180, true))
                    iv_ulos_photo.setImageBitmap(image)
                }
            }
        } catch (e: Exception) {
            Timber.e("error get image ${e.message}")
        }
    }

    private fun encodeToBase64(image: Bitmap): String {
        val byteArray = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 50, byteArray)
        val b = byteArray.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }

    private fun decodeBase64(input: String): Bitmap {
        val decodedBytes = Base64.decode(input, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    }

    private fun goToUlosDetail(id: Int, name: String) {
        image?.let {
            findNavController().navigate(
                SelectImageFragmentDirections.goToUlosDetailFragment(
                    id,
                    name,
                    it
                )
            )
        }
    }

    private fun observeData() {
        viewModel.ulosData.observe(viewLifecycleOwner, Observer {
            goToUlosDetail(it.id, it.name)
        })


    }

    private fun checkPermission() =
        ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(), arrayOf(Manifest.permission.CAMERA),
            PERMISSION_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(
                    requireContext(),
                    "Permission Granted",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Permission Denied",
                    Toast.LENGTH_SHORT
                ).show()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(
                            requireContext(),
                            Manifest.permission.CAMERA
                        )
                        != PackageManager.PERMISSION_GRANTED
                    ) {
                        showMessageOKCancel(
                            DialogInterface.OnClickListener { _, _ ->
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    requestPermission()
                                }
                            })
                    }
                }
            }
        }
    }

    private fun showMessageOKCancel(okListener: DialogInterface.OnClickListener) {
        AlertDialog.Builder(requireContext())
            .setMessage("You need to allow access permissions")
            .setPositiveButton("OK", okListener)
            .setNegativeButton("Cancel", null)
            .create()
            .show()
    }
}