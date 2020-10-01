package com.three.u.ui.tipsandtricks

import com.three.u.R
import com.three.u.base.BaseRecyclerAdapter
import com.three.u.base.changeTimeFormat
import com.three.u.base.push
import com.three.u.databinding.*

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
        holder.binding.mealInner.push()?.setOnClickListener {
            onClickListener.invoke(position, item)
        }
    }

}