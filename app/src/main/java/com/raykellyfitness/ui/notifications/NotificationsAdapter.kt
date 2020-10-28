package com.raykellyfitness.ui.notifications

import com.raykellyfitness.base.BaseRecyclerAdapter
import com.raykellyfitness.base.changeTimeFormat
import com.raykellyfitness.base.push
import com.raykellyfitness.databinding.RowNotificationBinding
import com.raykellyfitness.model.request.Notification

class NotificationsAdapter(
    override val layoutId: Int,
    private val onClickListener: (model: Notification) -> Unit
) : BaseRecyclerAdapter<RowNotificationBinding, Notification>() {

    override fun bind(holder: ViewHolder, item: Notification, position: Int) {
        holder.binding.model = item
        holder.binding.tvDate.text = item.created.changeTimeFormat("yyyy-MM-dd hh:mm:ss", "EEEE dd MMM, yyyy")
        holder.itemView.push()?.setOnClickListener { onClickListener.invoke(item) }
    }

}
