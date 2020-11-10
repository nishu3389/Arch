package com.raykellyfitness.ui.tipsandtricks

import com.raykellyfitness.R
import com.raykellyfitness.base.*
import com.raykellyfitness.databinding.RowTipsInnerBinding
import com.raykellyfitness.databinding.RowTipsOuterBinding
import com.raykellyfitness.networking.Api
import com.raykellyfitness.util.Constant.POST_TYPE_BLOG
import com.raykellyfitness.util.Constant.POST_TYPE_EXERCISE

class TipsAdapterOuter(
    val type : String,
    override val layoutId: Int,
    private val onClickListener: (model: ResponseTipsInner, item: ResponseTipsOuterItem) -> Unit
) : BaseRecyclerAdapter<RowTipsOuterBinding, ResponseTipsOuterItem>() {

    override fun bind(holder: ViewHolder, item: ResponseTipsOuterItem, position: Int) {

        var date = item.date.changeTimeFormat("yyyy-MM-dd hh:mm:ss", "EEEE dd MMM, yyyy")

        if(type.equals(POST_TYPE_BLOG,true) || type.equals(POST_TYPE_EXERCISE,true)){
            item.open = true
            holder.binding.tvWeek.text = item.day
            holder.binding.card.gone()
            holder.binding.tvDate.visible()
            holder.binding.tvDate2.gone()
        }else if(type.equals(POST_TYPE_EXERCISE,true)){
            holder.binding.tvWeek.text = item.day_title?:item.day
            holder.binding.tvDate2.text = date
            holder.binding.tvDate.gone()
            holder.binding.tvDate2.visible()
        }else{
            holder.binding.tvWeek.text = item.day
            holder.binding.tvDate.visible()
            holder.binding.tvDate2.gone()
        }

        holder.binding.model = item
        holder.binding.tvDate.text = date

        setInnerData(holder, item)
    }

    private fun setInnerData(holder: ViewHolder, item: ResponseTipsOuterItem) {
        var adapterInner = TipsAdapterInner(R.layout.row_tips_inner, onClickListener = onClickListener, modelOuter = item)
        holder.binding.recyclerInner.adapter = adapterInner
        adapterInner.setNewItems(item.data_list)
    }

}

class TipsAdapterInner(
    override val layoutId: Int,
    private val onClickListener: (model: ResponseTipsInner, modelOuter: ResponseTipsOuterItem) -> Unit,
    var modelOuter: ResponseTipsOuterItem
) : BaseRecyclerAdapter<RowTipsInnerBinding, ResponseTipsInner>() {

    override fun bind(holder: ViewHolder, item: ResponseTipsInner, position: Int) {
        holder.binding.model = item
        holder.binding.imgMeal.set(holder.itemView.context,item.media_url)
        holder.binding.mealInner.push()?.setOnClickListener {
            onClickListener.invoke(item, modelOuter)
        }
    }

}