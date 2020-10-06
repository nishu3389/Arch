package com.three.u.ui.meal

import com.three.u.R
import com.three.u.base.BaseRecyclerAdapter
import com.three.u.base.changeTimeFormat
import com.three.u.base.push
import com.three.u.databinding.*

class MealOuterAdapter(
    override val layoutId: Int,
    private val onClickListener: (position : Int, model: ResponseMealInner) -> Unit) : BaseRecyclerAdapter<RowMealOuterBinding, ResponseMealOuter>() {

    override fun bind(holder: ViewHolder, item: ResponseMealOuter, position: Int) {
        holder.binding.model = item
        holder.binding.tvDate.text = item.date.changeTimeFormat("yyyy-MM-dd","EEEE dd MMM, yyyy")

        setInnerData(holder, item)
    }

    private fun setInnerData(holder: ViewHolder, item:ResponseMealOuter) {
        var adapterInner  = MealInnerAdapter(R.layout.row_meal_inner, onClickListener)
        holder.binding.recyclerInner.adapter = adapterInner
        adapterInner.setNewItems(item.data)
    }



}

class MealInnerAdapter(override val layoutId: Int, private val onClickListener: (position : Int, model: ResponseMealInner) -> Unit) : BaseRecyclerAdapter<RowMealInnerBinding, ResponseMealInner>() {

    override fun bind(holder: ViewHolder, item: ResponseMealInner, position: Int) {
        holder.binding.model = item
        holder.binding.mealInner.push()?.setOnClickListener {
            onClickListener.invoke(position,item)
        }
    }

}