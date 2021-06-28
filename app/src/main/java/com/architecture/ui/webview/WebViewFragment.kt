package com.architecture.ui.webview

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Parcelable
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*

import androidx.navigation.fragment.findNavController
import com.architecture.R
import com.architecture.base.*
import com.architecture.databinding.WebviewFragmentBinding
import com.architecture.ui.image_picker.ImagePickerActivity
import com.architecture.util.ParcelKeys
import com.architecture.util.Prefs
import com.architecture.util.permission.DeviceRuntimePermission
import com.architecture.util.permission.IPermissionGranted
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class WebViewFragment : BaseFragment(), IPermissionGranted {

    private var mUploadMessage: ValueCallback<Uri?>? = null
    private var mCapturedImageURI: Uri? = null
    var mFilePathCallback: ValueCallback<Array<Uri?>>? = null
    private var mCameraPhotoPath: String? = null
    private var INPUT_FILE_REQUEST_CODE = 1
    private var FILECHOOSER_RESULTCODE = 1

    private var url: kotlin.String? = null
    lateinit var mBinding: WebviewFragmentBinding
    lateinit var mViewModel: WebviewViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
    }


    private fun setupViewModel() {
        mViewModel =
            androidx.lifecycle.ViewModelProvider(this, MyViewModelProvider(commonCallbacks as AsyncViewController))
                .get(WebviewViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = WebviewFragmentBinding.inflate(inflater, container, false).apply {
            viewModel = mViewModel
            clickHandler = ClickHandler()
        }
        return mBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initWork()
        otherWork()
    }

    /*----------If you want to navigate back on the webpages on backpressed-----------*/
    /* override fun handlingBackPress(): Boolean {

         if(mBinding.webView.canGoBack())
             mBinding.webView.goBack()
         else
             goBack()

         return true
     }*/

    private fun initWork() {
        arguments.let {
            url = it?.getString("url")
        }

        setHasOptionsMenu(false)
    }


    private fun otherWork() {

        mBinding.webView.getSettings().setJavaScriptEnabled(true);
        mBinding.webView.getSettings().setPluginState(WebSettings.PluginState.OFF);
        mBinding.webView.getSettings().setLoadWithOverviewMode(true);
        mBinding.webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mBinding.webView.getSettings().setUseWideViewPort(true);
        mBinding.webView.getSettings()
            .setUserAgentString("Android Mozilla/5.0 AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30");
        mBinding.webView.getSettings().setAllowFileAccess(true);
        mBinding.webView.getSettings().setAllowFileAccess(true);
        mBinding.webView.getSettings().setAllowContentAccess(true);
        mBinding.webView.getSettings().supportZoom();

        loadUrl(url)

        mBinding.webView.setWebViewClient(object : WebViewClient() {

            override fun shouldInterceptRequest(view: WebView?, request: WebResourceRequest?): WebResourceResponse? {
                return super.shouldInterceptRequest(view, request)
//                navigationWork(view, request?.url.toString())
            }

            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
//                navigationWork(request.url.toString())
                return false
            }

        })

        mBinding.webView.setWebChromeClient(ChromeClient())

    }

    fun loadUrl(url: String?) {
        url.let {
            mBinding.webView.postDelayed({ url?.let { it1 -> mBinding.webView.loadUrl(it1) } }, 500)
        }
    }

    fun reloadFragment(url: String?) {
        findNavController().popBackStack()
        navigate(R.id.WebViewFragment, Pair("url", url))
    }


    inner class ClickHandler {


    }

    override fun permissionGranted(requestCode: Int) {
        when (requestCode) {

            DeviceRuntimePermission.REQUEST_CAMERA_N_STORAGE_PERMISSION -> {
                launchGalleryIntent()
                /* ImagePickerActivity.showImagePickerOptions(requireActivity(),
                     requireContext().getString(R.string.stripe_upload_document_title),
                     object : ImagePickerActivity.PickerOptionListener {
                         override fun onTakeCameraSelected() {
                             launchCameraIntent()
                         }

                         override fun onChooseGallerySelected() {
                             launchGalleryIntent()
                         }

                     })*/
            }
        }
    }

    private fun launchGalleryIntent() {
        Prefs.get().shouldCheckSecurity = false
        val intent = Intent(requireActivity(), ImagePickerActivity::class.java)
        intent.putExtra(
            ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION,
            ImagePickerActivity.REQUEST_GALLERY_IMAGE
        )
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, false)
//        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1) // 16x9, 1x1, 3:4, 3:2
//        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1)
        startActivityForResult(intent, ParcelKeys.REQUEST_PICK_IMAGE)
    }

    private fun launchCameraIntent() {
        Prefs.get().shouldCheckSecurity = false
        val intent = Intent(requireActivity(), ImagePickerActivity::class.java)
        intent.putExtra(
            ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION,
            ImagePickerActivity.REQUEST_IMAGE_CAPTURE
        )

        // setting aspect ratio
        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, false)
//        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1) // 16x9, 1x1, 3:4, 3:2
//        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1)

        // setting maximum bitmap width and height
        // setting maximum bitmap width and height
        intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true)
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 620)
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 620)

        startActivityForResult(intent, ParcelKeys.REQUEST_PICK_IMAGE)
    }


    override fun permissionDenied(requestCode: Int) {
        when (requestCode) {

            DeviceRuntimePermission.REQUEST_CAMERA_N_STORAGE_PERMISSION -> (activity as BaseActivity).checkAndAskPermission(
                DeviceRuntimePermission(
                    DeviceRuntimePermission.REQUEST_CAMERA_N_STORAGE_PERMISSION,
                    null
                )
            )
        }
    }

    fun onClickImage() {
        (activity as BaseActivity).setPermissionGranted(this)
        permissionDenied(DeviceRuntimePermission.REQUEST_CAMERA_N_STORAGE_PERMISSION)
    }

    inner class ChromeClient : WebChromeClient() {

        override fun onReceivedTitle(view: WebView?, title: String?) {
            super.onReceivedTitle(view, title)
            navigationWork(view, view?.url)
        }

        override fun onPermissionRequest(request: PermissionRequest?) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                request?.grant(request.resources)
            }
        }

        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            mBinding.progressBar.setProgress(newProgress)
            mBinding.progressBar.setVisibility(if (newProgress == 100) View.GONE else View.VISIBLE)
        }

        @Throws(IOException::class)
        private fun createImageFile(): File? {
            // Create an image file name
            val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val imageFileName = "JPEG_" + timeStamp + "_"
            val storageDir: File = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES
            )
            return File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",  /* suffix */
                storageDir /* directory */
            )
        }

        // For Android 5.0
        override fun onShowFileChooser(
            webView: WebView?,
            filePath: ValueCallback<Array<Uri?>>?,
            fileChooserParams: FileChooserParams?
        ): Boolean {
            // Double check that we don't have any existing callbacks
            /* if (mFilePathCallback != null) {
                 mFilePathCallback!!.onReceiveValue(null)
             }*/
            mFilePathCallback = filePath

            onClickImage()
            return true

        }

        // openFileChooser for Android 3.0+
        fun openFileChooser(uploadMsg: ValueCallback<Uri?>?, acceptType: String?) {

            mUploadMessage = uploadMsg
            // Create AndroidExampleFolder at sdcard
            // Create AndroidExampleFolder at sdcard
            val imageStorageDir = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "AndroidExampleFolder"
            )
            if (!imageStorageDir.exists()) {
                // Create AndroidExampleFolder at sdcard
                imageStorageDir.mkdirs()
            }

            // Create camera captured image file path and name
            val file = File(
                imageStorageDir.name + File.separator.toString() + "IMG_" + System.currentTimeMillis()
                    .toString() + ".jpg"
            )
            mCapturedImageURI = Uri.fromFile(file)

            // Camera capture image intent
            val captureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI)
            val i = Intent(Intent.ACTION_GET_CONTENT)
            i.addCategory(Intent.CATEGORY_OPENABLE)
            i.setType("image/*")

            // Create file chooser intent
            val chooserIntent: Intent = Intent.createChooser(i, "Image Chooser")

            // Set camera intent to file chooser
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf<Parcelable>(captureIntent))

            // On select image call onActivityResult method of activity
            startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE)
        }

        // openFileChooser for Android < 3.0
        fun openFileChooser(uploadMsg: ValueCallback<Uri?>) {
            openFileChooser(uploadMsg, "")
        }

        //openFileChooser for other Android versions
        fun openFileChooser(uploadMsg: ValueCallback<Uri?>, acceptType: String?, capture: String?) {
            openFileChooser(uploadMsg, acceptType)
        }
    }

    private fun navigationWork(view: WebView? ,url: String?) {

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK) {
            var results: Array<Uri?>? = null
            val uri = data!!.getParcelableExtra<Uri>("path")
//            val path = PathUtil.getPath(requireContext(), uri)
//            lifecycleScope.launch {
//                val compressedImageFile = context?.let { Compressor.compress(it, File(path)) }
//                results = arrayOf(compressedImageFile?.toUri())
            results = arrayOf(uri)
            mFilePathCallback!!.onReceiveValue(results)
            mFilePathCallback = null
//            }
        } else if (resultCode == RESULT_CANCELED) {
            // this is important, call the callback with null parameter
            mFilePathCallback?.onReceiveValue(null)
        }


      /*---------------Code to pick or capture an image when requested by webview/webpage---------------*/

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (*//*requestCode != INPUT_FILE_REQUEST_CODE || *//*mFilePathCallback == null) {
                super.onActivityResult(requestCode, resultCode, data)
                return
            }
            var results: Array<Uri?>? = null

            // Check that the response is a good one
            if (resultCode == Activity.RESULT_OK) {
                if (data == null) {
                    // If there is not data, then we may have taken a photo
                    if (mCameraPhotoPath != null) {
                        results = arrayOf(Uri.parse(mCameraPhotoPath))
                    }
                } else {
                    val dataString = data.dataString
                    if (dataString != null) {
                        results = arrayOf(Uri.parse(dataString))
                    }
                }
            }
            mFilePathCallback!!.onReceiveValue(results)
            mFilePathCallback = null
        } else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            if (*//*requestCode != FILECHOOSER_RESULTCODE || *//*mUploadMessage == null) {
                super.onActivityResult(requestCode, resultCode, data)
                return
            }
            if (requestCode == FILECHOOSER_RESULTCODE) {
                if (null == mUploadMessage) {
                    return
                }
                var result: Uri? = null
                try {
                    result = if (resultCode != RESULT_OK) {
                        null
                    } else {
                        // retrieve from the private variable if the intent is null
                        if (data == null) mCapturedImageURI else data.data
                    }
                } catch (e: Exception) {
                    Toast.makeText(getApplicationContext(), "activity :$e", Toast.LENGTH_LONG).show()
                }
                mUploadMessage!!.onReceiveValue(result)
                mUploadMessage = null
            }
        }*/
        return
    }



}