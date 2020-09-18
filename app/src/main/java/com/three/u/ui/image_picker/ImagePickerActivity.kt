package com.three.u.ui.image_picker

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import com.three.u.R
import com.three.u.base.BaseActivity
import com.three.u.util.PathUtil
import com.yalantis.ucrop.UCrop
import id.zelory.compressor.Compressor
import kotlinx.coroutines.launch
import java.io.File

class ImagePickerActivity : BaseActivity() {
    private var lockAspectRatio = false
    private var setBitmapMaxWidthHeight = false
    private var ASPECT_RATIO_X = 16
    private var ASPECT_RATIO_Y = 9
    private var bitmapMaxWidth = 620
    private var bitmapMaxHeight = 620
    private var IMAGE_COMPRESSION = 80

    interface PickerOptionListener {
        fun onTakeCameraSelected()
        fun onChooseGallerySelected()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_picker)
        val intent = intent
            ?: //Toast.makeText(getApplicationContext(), getString(R.string.toast_image_intent_null), Toast.LENGTH_LONG).show();
            return
        ASPECT_RATIO_X =
            intent.getIntExtra(INTENT_ASPECT_RATIO_X, ASPECT_RATIO_X)
        ASPECT_RATIO_Y =
            intent.getIntExtra(INTENT_ASPECT_RATIO_Y, ASPECT_RATIO_Y)
        IMAGE_COMPRESSION = intent.getIntExtra(
            INTENT_IMAGE_COMPRESSION_QUALITY,
            IMAGE_COMPRESSION
        )
        lockAspectRatio =
            intent.getBooleanExtra(INTENT_LOCK_ASPECT_RATIO, false)
        setBitmapMaxWidthHeight = intent.getBooleanExtra(
            INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT,
            false
        )
        bitmapMaxWidth = intent.getIntExtra(
            INTENT_BITMAP_MAX_WIDTH,
            bitmapMaxWidth
        )
        bitmapMaxHeight = intent.getIntExtra(
            INTENT_BITMAP_MAX_HEIGHT,
            bitmapMaxHeight
        )
        val requestCode =
            intent.getIntExtra(INTENT_IMAGE_PICKER_OPTION, -1)
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            takeCameraImage()
        } else {
            chooseImageFromGallery()
        }
    }

    override fun requestLogout() {

    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_IMAGE_CAPTURE -> if (resultCode == Activity.RESULT_OK) {
                cropImage(getCacheImagePath(fileName))
            } else {
                setResultCancelled()
            }
            REQUEST_GALLERY_IMAGE -> if (resultCode == Activity.RESULT_OK) {
                val imageUri = data!!.data
                cropImage(imageUri)
            } else {
                setResultCancelled()
            }
            UCrop.REQUEST_CROP -> if (resultCode == Activity.RESULT_OK) {
                handleUCropResult(data)
            } else {
                setResultCancelled()
            }
            UCrop.RESULT_ERROR -> {
                val cropError = UCrop.getError(data!!)
                Log.e(TAG, "Crop error: $cropError")
                setResultCancelled()
            }
            else -> setResultCancelled()
        }
    }

    private fun takeCameraImage() {
        fileName = System.currentTimeMillis().toString() + ".png"
        val takePictureIntent =
            Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        takePictureIntent.putExtra(
            MediaStore.EXTRA_OUTPUT,
            getCacheImagePath(fileName)
        )
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(
                takePictureIntent,
                REQUEST_IMAGE_CAPTURE
            )
        }
    }

    private fun chooseImageFromGallery() {
        val pickPhoto = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        startActivityForResult(pickPhoto, REQUEST_GALLERY_IMAGE)
    }

    private fun cropImage(sourceUri: Uri?) {
        val destinationUri = Uri.fromFile(
            File(
                cacheDir,
                queryName(contentResolver, sourceUri)
            )
        )
        val options = UCrop.Options()
        options.setStatusBarColor( ContextCompat.getColor(this,R.color.blue))
        options.setToolbarColor(ContextCompat.getColor(this,R.color.blue))
        options.setToolbarTitle("Crop")
        options.setToolbarWidgetColor(ContextCompat.getColor(this,R.color.white))

        options.setShowCropGrid(true)
        options.setHideBottomControls(true)
        options.setCompressionQuality(IMAGE_COMPRESSION)
        // applying UI theme
//j

        options.setActiveWidgetColor(
            ContextCompat.getColor(
                this,
                R.color.blue
            )
        )
        if (lockAspectRatio) options.withAspectRatio(
            ASPECT_RATIO_X.toFloat(),
            ASPECT_RATIO_Y.toFloat()
        )
        if (setBitmapMaxWidthHeight) options.withMaxResultSize(bitmapMaxWidth, bitmapMaxHeight)
        UCrop.of(sourceUri!!, destinationUri)
            .withOptions(options)
            .start(this)
    }

    private fun handleUCropResult(data: Intent?) {
        if (data == null) {
            setResultCancelled()
            return
        }
        val resultUri = UCrop.getOutput(data)
        setResultOk(resultUri)
    }

    private fun setResultOk(imagePath: Uri?) {
        val intent = Intent()

        lifecycleScope.launch {
            val compressedImageFile = Compressor.compress(this@ImagePickerActivity, File(PathUtil.getPath(this@ImagePickerActivity, imagePath)))
            intent.putExtra("path", compressedImageFile?.toUri())
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

    }

    private fun setResultCancelled() {
        val intent = Intent()
        setResult(Activity.RESULT_CANCELED, intent)
        finish()
    }

    private fun getCacheImagePath(fileName: String?): Uri {
        val path = File(externalCacheDir, "camera")
        if (!path.exists()) path.mkdirs()
        val image = File(path, fileName)
        return FileProvider.getUriForFile(
            this@ImagePickerActivity,
            "$packageName.provider",
            image
        )
    }

    companion object {
        private val TAG = ImagePickerActivity::class.java.simpleName
        const val INTENT_IMAGE_PICKER_OPTION = "image_picker_option"
        const val INTENT_ASPECT_RATIO_X = "aspect_ratio_x"
        const val INTENT_ASPECT_RATIO_Y = "aspect_ratio_Y"
        const val INTENT_LOCK_ASPECT_RATIO = "lock_aspect_ratio"
        const val INTENT_IMAGE_COMPRESSION_QUALITY = "compression_quality"
        const val INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT = "set_bitmap_max_width_height"
        const val INTENT_BITMAP_MAX_WIDTH = "max_width"
        const val INTENT_BITMAP_MAX_HEIGHT = "max_height"
        const val REQUEST_IMAGE_CAPTURE = 0
        const val REQUEST_GALLERY_IMAGE = 1
        var fileName: String? = null

        fun showImagePickerOptions(context: Context, listener: PickerOptionListener) { // setup the alert builder
            val builder = AlertDialog.Builder(context)
            builder.setTitle(context.getString(R.string.lbl_set_profile_photo))
            // add a list
            val animals = arrayOf(
                context.getString(R.string.lbl_take_camera_picture),
                context.getString(R.string.lbl_choose_from_gallery)
            )
            builder.setItems(
                animals
            ) { dialog: DialogInterface?, which: Int ->
                when (which) {
                    0 -> listener.onTakeCameraSelected()
                    1 -> listener.onChooseGallerySelected()
                }
            }
            // create and show the alert dialog
            val dialog = builder.create()
            dialog.show()
        }

        fun showImagePickerOptions(context: Context, title:String, listener: PickerOptionListener) { // setup the alert builder
            val builder = AlertDialog.Builder(context)
            builder.setTitle(title)
            // add a list
            val animals = arrayOf(
                context.getString(R.string.lbl_take_camera_picture),
                context.getString(R.string.lbl_choose_from_gallery)
            )
            builder.setItems(
                animals
            ) { dialog: DialogInterface?, which: Int ->
                when (which) {
                    0 -> listener.onTakeCameraSelected()
                    1 -> listener.onChooseGallerySelected()
                }
            }
            // create and show the alert dialog
            val dialog = builder.create()
            dialog.show()
        }

        private fun queryName(
            resolver: ContentResolver,
            uri: Uri?
        ): String {
            val returnCursor = resolver.query(uri!!, null, null, null, null)!!
            val nameIndex =
                returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            returnCursor.moveToFirst()
            val name = returnCursor.getString(nameIndex)
            returnCursor.close()
            return name
        }

        /**
         * Calling this will delete the images from cache directory
         * useful to clear some memory
         */
        fun clearCache(context: Context) {
            val path = File(context.externalCacheDir, "camera")
            if (path.exists() && path.isDirectory) {
                for (child in path.listFiles()) {
                    child.delete()
                }
            }
        }
    }
}