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
        holder.binding.tvDate.text = item.created_at.changeTimeFormat("yyyy-MM-dd","dd MMM, yyyy")

        setInnerData(holder)
    }

    private fun setInnerData(holder: ViewHolder) {
        var adapterInner  = MealInnerAdapter(R.layout.row_meal_inner, onClickListener)
        holder.binding.recyclerInner.adapter = adapterInner
        var innerList = arrayListOf(
            ResponseMealInner("Pumpkin soup 1"),
            ResponseMealInner("Pumpkin soup 2"),
            ResponseMealInner("Pumpkin soup 3"),
            ResponseMealInner("Pumpkin soup 4")
        )
        adapterInner.setNewItems(innerList)

//        adapterInner.addClickEventWithView(R.id.meal_inner, mClickHandler::mealClicked)

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