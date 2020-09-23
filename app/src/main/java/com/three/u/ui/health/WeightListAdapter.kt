package com.three.u.ui.health

import com.three.u.base.BaseRecyclerAdapter
import com.three.u.base.changeTimeFormat
import com.three.u.base.isEmptyy
import com.three.u.databinding.RowBloodPressureListBinding
import com.three.u.databinding.RowBloodSugarListBinding
import com.three.u.databinding.RowWeightListBinding
import com.three.u.model.request.ResponseAddBloodPressureItem
import com.three.u.model.request.ResponseAddBloodSugarItem
import com.three.u.model.request.ResponseAddWeightItem

class WeightListAdapter(override val layoutId: Int) : BaseRecyclerAdapter<RowWeightListBinding, ResponseAddWeightItem>() {

    override fun bind(holder: ViewHolder, item: ResponseAddWeightItem, position: Int) {
        holder.binding.model = item
        holder.binding.tvDate.text = item.created_at.changeTimeFormat("yyyy-MM-dd","dd MMM, yyyy")
    }

}

class BloodSugarListAdapter(override val layoutId: Int) : BaseRecyclerAdapter<RowBloodSugarListBinding, ResponseAddBloodSugarItem>() {

    override fun bind(holder: ViewHolder, item: ResponseAddBloodSugarItem, position: Int) {
        holder.binding.model = item
        holder.binding.tvDate.text = item.created_at.changeTimeFormat("yyyy-MM-dd","dd MMM, yyyy")
    }

}

class BloodPressureListAdapter(override val layoutId: Int) : BaseRecyclerAdapter<RowBloodPressureListBinding, ResponseAddBloodPressureItem>() {

    override fun bind(holder: ViewHolder, item: ResponseAddBloodPressureItem, position: Int) {
        holder.binding.model = item
        holder.binding.tvDate.text = item.created_at.changeTimeFormat("yyyy-MM-dd","dd MMM, yyyy")
    }

}
