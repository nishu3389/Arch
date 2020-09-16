package com.three.u.item_detail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.three.u.BindingAdapters.loadImage
import com.three.u.R
import com.three.u.base.push
import com.three.u.base.set
import com.three.u.browse_items.IViewPagerClick
import com.three.u.common.IViewPagerClickListener
import com.three.u.model.request.ListOfFile
import com.three.u.model.response.AdvModel

class ItemImagesAdapter(
    private val mContext: Context,
    private val mediaList: List<AdvModel>?,
    val layoutResId: Int,
    val iViewPagerClick: IViewPagerClickListener?
) : PagerAdapter()
{
    override fun instantiateItem(collection: ViewGroup, position: Int): Any
    {
        val mediaData = mediaList?.get(position)
        val inflater = LayoutInflater.from(mContext)
        val layout = inflater.inflate(layoutResId, collection, false) as ImageView

        val itemImage =layout.findViewById<View>(R.id.imv_item) as ImageView
        itemImage.set(mContext,mediaData?.advertisementImage)
        itemImage?.setOnClickListener{
            iViewPagerClick?.viewPagerItemClicked(mediaData)
        }
        collection.addView(layout)
        return layout
    }
    override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
        collection.removeView(view as View)
    }
    override fun getCount(): Int {
        return mediaList?.size?:0
    }
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

}