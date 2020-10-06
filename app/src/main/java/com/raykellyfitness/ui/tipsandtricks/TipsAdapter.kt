package com.raykellyfitness.ui.tipsandtricks

import com.raykellyfitness.R
import com.raykellyfitness.base.BaseRecyclerAdapter
import com.raykellyfitness.base.changeTimeFormat
import com.raykellyfitness.base.push
import com.raykellyfitness.base.set
import com.raykellyfitness.databinding.RowTipsInnerBinding
import com.raykellyfitness.databinding.RowTipsOuterBinding

class TipsAdapterOuter(
    override val layoutId: Int,
    private val onClickListener: (position: Int, model: ResponseTipsInner) -> Unit
) : BaseRecyclerAdapter<RowTipsOuterBinding, ResponseTipsOuterItem>() {

    override fun bind(holder: ViewHolder, item: ResponseTipsOuterItem, position: Int) {
        holder.binding.model = item
        holder.binding.tvDate.text = item.date.changeTimeFormat("yyyy-MM-dd hh:mm:ss", "EEEE dd MMM, yyyy")

        setInnerData(holder, item)
    }

    private fun setInnerData(holder: ViewHolder, item: ResponseTipsOuterItem) {
        var adapterInner = TipsAdapterInner(R.layout.row_tips_inner, onClickListener)
        holder.binding.recyclerInner.adapter = adapterInner
        adapterInner.setNewItems(item.data_list)
    }

}

class TipsAdapterInner(
    override val layoutId: Int,
    private val onClickListener: (position: Int, model: ResponseTipsInner) -> Unit
) : BaseRecyclerAdapter<RowTipsInnerBinding, ResponseTipsInner>() {

    override fun bind(holder: ViewHolder, item: ResponseTipsInner, position: Int) {
        holder.binding.model = item
        holder.binding.imgMeal.set(holder.itemView.context,item.media_url)
        holder.binding.mealInner.push()?.setOnClickListener {
            onClickListener.invoke(position, item)
        }
    }

}