package com.raykellyfitness.ui.notifications

import com.raykellyfitness.base.BaseRecyclerAdapter
import com.raykellyfitness.base.changeTimeFormat
import com.raykellyfitness.base.push
import com.raykellyfitness.databinding.RowNotificationBinding
import com.raykellyfitness.model.request.Notification
import com.raykellyfitness.util.Constant
import com.raykellyfitness.util.Util

class NotificationsAdapter(
    override val layoutId: Int,
    private val onClickListener: (model: Notification) -> Unit
) : BaseRecyclerAdapter<RowNotificationBinding, Notification>() {

    override fun bind(holder: ViewHolder, item: Notification, position: Int) {
        holder.binding.model = item
        val convertUtcToLocal = Util.convertUtcToLocal(item.created)
        holder.binding.tvDate.text = Util.getTimeAgo(convertUtcToLocal!!, Constant.API_DATE_FORMAT)
        holder.itemView.push()?.setOnClickListener { onClickListener.invoke(item) }
    }

}