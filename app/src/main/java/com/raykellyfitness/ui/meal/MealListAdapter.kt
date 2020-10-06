package com.raykellyfitness.ui.meal

import com.raykellyfitness.R
import com.raykellyfitness.base.BaseRecyclerAdapter
import com.raykellyfitness.base.changeTimeFormat
import com.raykellyfitness.base.push
import com.raykellyfitness.databinding.RowMealInnerBinding
import com.raykellyfitness.databinding.RowMealOuterBinding

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