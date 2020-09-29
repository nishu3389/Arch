package com.three.u.ui.tipsandtricks

import com.three.u.R
import com.three.u.base.BaseRecyclerAdapter
import com.three.u.base.changeTimeFormat
import com.three.u.base.push
import com.three.u.databinding.*

class TipsAdapterOuter(
    override val layoutId: Int,
    private val onClickListener: (position : Int, model: ResponseTipsInner) -> Unit) : BaseRecyclerAdapter<RowTipsOuterBinding, ResponseTipsOuter>() {

    override fun bind(holder: ViewHolder, item: ResponseTipsOuter, position: Int) {
        holder.binding.model = item
        holder.binding.tvDate.text = item.created_at.changeTimeFormat("yyyy-MM-dd","dd MMM, yyyy")

        setInnerData(holder)
    }

    private fun setInnerData(holder: ViewHolder) {
        var adapterInner  = TipsAdapterInner(R.layout.row_tips_inner, onClickListener)
        holder.binding.recyclerInner.adapter = adapterInner
        var innerList = arrayListOf(
            ResponseTipsInner("Pumpkin soup 1"),
            ResponseTipsInner("Pumpkin soup 2"),
            ResponseTipsInner("Pumpkin soup 3"),
            ResponseTipsInner("Pumpkin soup 4")
        )
        adapterInner.setNewItems(innerList)

//        adapterInner.addClickEventWithView(R.id.meal_inner, mClickHandler::mealClicked)

    }



}

class TipsAdapterInner(override val layoutId: Int, private val onClickListener: (position : Int, model: ResponseTipsInner) -> Unit) : BaseRecyclerAdapter<RowTipsInnerBinding, ResponseTipsInner>() {

    override fun bind(holder: ViewHolder, item: ResponseTipsInner, position: Int) {
        holder.binding.model = item
        holder.binding.mealInner.push()?.setOnClickListener {
            onClickListener.invoke(position,item)
        }
    }

}