package com.three.u

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import com.three.u.util.RoundedCornersTransformation
import com.three.u.util.Util


object BindingAdapters {
    @JvmStatic
    @BindingAdapter(value = ["imageUrl", "defaultImage"], requireAll = false)
    fun loadImage(view: ImageView, url: String?, defaultImage: Int?) {
        if (url.isNullOrEmpty()) {
            if (defaultImage == 0 || defaultImage == null) {

                Picasso.get().load(R.drawable.ic_launcher_background).placeholder(R.drawable.placeholder).error(R.drawable.placeholder).into(view)
            } else {
                Picasso.get().load(defaultImage).placeholder(R.drawable.placeholder).error(R.drawable.placeholder).into(view)
            }
            return
        }
        Picasso.get().load(url).placeholder(R.drawable.placeholder).error(R.drawable.placeholder).into(view)
    }


    @JvmStatic
    @BindingAdapter(value = ["loadRoundedImage", "defaultImage"], requireAll = false)
    fun loadRoundedImage(view: ImageView, url: String?, defaultImage: Int?) {
        if (url.isNullOrEmpty()) {
            if (defaultImage == 0 || defaultImage == null) {
                Picasso.get().load(R.drawable.ic_launcher_background).into(object : Target {
                    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                    }

                    override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                    }

                    override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                        view.setImageBitmap(Util.getRoundedCornerBitmap(bitmap!!, 100))
                    }

                })
            } else {
                Picasso.get().load(defaultImage).into(view)
            }
            return
        }
        Picasso.get().load(url).into(view)
    }

    @JvmStatic
    @BindingAdapter(value = ["imageUrlNew", "defaultImage"], requireAll = false)
    fun loadImageNew(view: ImageView, url: String?, defaultImage: Int?) {

        val transformation = RoundedCornersTransformation(20, 0)

        if (url.isNullOrEmpty()) {
            Picasso.get().load(R.drawable.ic_launcher_background).transform(transformation).into(
                view
            )
        } else
            Picasso.get().load(url).transform(transformation).error(R.drawable.ic_launcher_background).placeholder(
                R.drawable.ic_launcher_background
            )
                .into(view)
    }

    @JvmStatic
    @BindingAdapter(value = ["imageUrlUpdate", "defaultImage"], requireAll = false)
    fun loadImageUpdate(view: ImageView, url: String?, defaultImage: Int?) {

        if (url.isNullOrEmpty()) {
            Picasso.get().load(R.drawable.ic_launcher_background)/*.transform(transformation)*/.into(
                view
            )
        } else
            Picasso.get().load(url).resize(400, 400).centerCrop().error(R.drawable.ic_launcher_background)
                .placeholder(R.drawable.ic_launcher_background).into(view)
    }

    @JvmStatic
    @BindingAdapter(value = ["roundedImageUrl", "defaultImage"], requireAll = false)
    fun loadImageAsRoundedCorners(view: ImageView, url: String?, defaultImage: Int?) {


        if (url.isNullOrEmpty()) {
            if (defaultImage == 0 || defaultImage == null) {
                Picasso.get().load(R.drawable.ic_launcher_background)/*.transform(transformation)*/.into(
                    view
                )
            } else {
                Picasso.get().load(defaultImage)/*.transform(transformation)*/.into(view)
            }
            return
        }
        Picasso.get().load(url).transform(RoundedCornersTransformation(12, 0)).into(view)
    }

    @JvmStatic
    @BindingAdapter(value = ["imageUrlList", "defaultImage"], requireAll = false)
    fun loadImageFomList(view: ImageView, urlList: String?, defaultImage: Int?) {
        val url: String
        val urlArr = urlList?.split(",")

        if (urlList?.isNotEmpty() == true && urlArr?.get(0)?.isNotBlank() == true) {
            url = urlArr[0]
            Picasso.get().load(url).into(view)

        } else if (defaultImage != null && defaultImage != 0) {
            Picasso.get().load(defaultImage).into(view)

        } else {
            Picasso.get().load(R.drawable.ic_launcher_background).into(view)
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["roundedImageUrlList", "defaultImage"], requireAll = false)
    fun loadRoundedImageFomList(view: ImageView, urlList: String?, defaultImage: Int?) {
        val url: String
        val urlArr = urlList?.split(",")


        val transformation = RoundedCornersTransformation(12, 0)


        if (urlList?.isNotEmpty() == true && urlArr?.get(0)?.isNotBlank() == true) {
            url = urlArr[0]

            var thumb = url.replace(
                url.split("/")[url.split("/").size - 1],
                ""
            ) + "thumb/" + url.split("/")[url.split("/").size - 1]

            Picasso.get().load(thumb).transform(transformation).resize(200, 200).centerCrop()
                .into(view)

        } else if (defaultImage != null && defaultImage != 0) {
            Picasso.get().load(defaultImage).transform(transformation).into(view)
        } else {
            Picasso.get().load(R.drawable.ic_launcher_background).transform(transformation).into(view)
        }
    }

    @JvmStatic
    @BindingAdapter("visibleErrIfListEmpty")
    fun visibleErrIfListEmpty(view: View, list: List<Any>?) {
        if (list.isNullOrEmpty()) {
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.GONE
        }
    }

    @JvmStatic
    @BindingAdapter("htmlText")
    fun loadHtmlText(view: TextView, text: String?) {
        if (text == null) return
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            view.text = Html.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY)
        } else {
            view.text = Html.fromHtml(text)
        }
    }

    @JvmStatic
    @BindingAdapter("dateFormated")
    fun loadDate(view: TextView, text: String?) {
        if (text == null) return

        view.text = Util.covertTimeToText(text)

    }


    @JvmStatic
    @BindingAdapter("dateFormatedOnly")
    fun loadDateOnly(view: TextView, text: String?) {
        if (text == null) return
        view.text = Util.convertDateOnly(text)

    }

    @JvmStatic
    @BindingAdapter("updateMessage")
    fun loadUpdateMessage(view: TextView, text: String?) {
        if (text == null) return
        if (text == "like") {
            view.text = Util.covertTimeToText(text)
        } else if (text == "comment") {
            view.text = Util.covertTimeToText(text)
        } else
            view.text = Util.covertTimeToText(text)

    }

}


