package com.raykellyfitness.ui.tipsandtricks

import com.raykellyfitness.R
import com.raykellyfitness.base.*
import com.raykellyfitness.databinding.RowTipsInnerBinding
import com.raykellyfitness.databinding.RowTipsOuterBinding
import com.raykellyfitness.networking.Api

class TipsAdapterOuter(
    val type : String,
    override val layoutId: Int,
    private val onClickListener: (model: ResponseTipsInner, item: ResponseTipsOuterItem) -> Unit
) : BaseRecyclerAdapter<RowTipsOuterBinding, ResponseTipsOuterItem>() {

    override fun bind(holder: ViewHolder, item: ResponseTipsOuterItem, position: Int) {
        if(type.equals(Api.POST_TYPE_BLOG,true)){
            item.open = true
            holder.binding.card.gone()
        }
        holder.binding.model = item
        holder.binding.tvDate.text = item.date.changeTimeFormat("yyyy-MM-dd hh:mm:ss", "EEEE dd MMM, yyyy")

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