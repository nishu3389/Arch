package com.raykellyfitness.ui.health

import com.raykellyfitness.R
import com.raykellyfitness.base.BaseRecyclerAdapter
import com.raykellyfitness.base.changeTimeFormat
import com.raykellyfitness.databinding.RowBloodPressureListBinding
import com.raykellyfitness.databinding.RowBloodSugarListBinding
import com.raykellyfitness.databinding.RowWeightListBinding
import com.raykellyfitness.model.request.ResponseAddBloodPressureItem
import com.raykellyfitness.model.request.ResponseAddBloodSugarItem
import com.raykellyfitness.model.request.ResponseAddWeightItem
import com.raykellyfitness.util.Constant
import com.raykellyfitness.util.Util
import com.raykellyfitness.util.UtilJava

class WeightListAdapter(override val layoutId: Int) : BaseRecyclerAdapter<RowWeightListBinding, ResponseAddWeightItem>() {

    override fun bind(holder: ViewHolder, item: ResponseAddWeightItem, position: Int) {
        holder.binding.model = item

        val convertUtcToLocal = Util.convertUtcToLocal(item.created_at)
        holder.binding.tvDate.text = convertUtcToLocal?.changeTimeFormat(Constant.API_DATE_FORMAT, "dd MMM, yyyy")
    }

}

class BloodSugarListAdapter(override val layoutId: Int) : BaseRecyclerAdapter<RowBloodSugarListBinding, ResponseAddBloodSugarItem>() {

    override fun bind(holder: ViewHolder, item: ResponseAddBloodSugarItem, position: Int) {
        holder.binding.model = item

        if(item.blood_sugar_postprandial.toFloat()<=0)
            holder.binding.tvHeight.text = Constant.NA
        else
            holder.binding.tvHeight.text = "${item.blood_sugar_postprandial} mmol/L"

        val convertUtcToLocal = Util.convertUtcToLocal(item.created_at)
        holder.binding.tvDate.text = convertUtcToLocal?.changeTimeFormat(Constant.API_DATE_FORMAT, Constant.DATE_FORMAT)

    }

}

class BloodPressureListAdapter(override val layoutId: Int) : BaseRecyclerAdapter<RowBloodPressureListBinding, ResponseAddBloodPressureItem>() {

    override fun bind(holder: ViewHolder, item: ResponseAddBloodPressureItem, position: Int) {
        holder.binding.model = item
        
        val convertUtcToLocal = Util.convertUtcToLocal(item.created_at)
        holder.binding.tvDate.text = convertUtcToLocal?.changeTimeFormat(Constant.API_DATE_FORMAT, "dd MMM, yyyy")
    }

}