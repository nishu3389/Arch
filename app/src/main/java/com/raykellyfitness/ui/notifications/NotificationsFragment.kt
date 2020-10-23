package com.raykellyfitness.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.raykellyfitness.R
import com.raykellyfitness.base.AsyncViewController
import com.raykellyfitness.base.BaseFragment
import com.raykellyfitness.base.MyViewModelProvider
import com.raykellyfitness.base.isEmptyy
import com.raykellyfitness.databinding.FragmentNotificationsBinding
import com.raykellyfitness.model.request.Notification
import com.raykellyfitness.model.request.ResponseNotifications
import com.raykellyfitness.ui.activity.HomeActivity

class NotificationsFragment : BaseFragment() {

    lateinit var mViewModel: NotificationsViewModel
    lateinit var mBinding: FragmentNotificationsBinding
    var onClickHandler = ClickHandler()


    var adapter = NotificationsAdapter(R.layout.row_notification) { model ->
        navigate(
            R.id.TipsDetailFragment,
            Pair("id", model.id),
            Pair("type", model.type),
            Pair("typeName", model.day)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (!::mBinding.isInitialized) {
            mBinding = FragmentNotificationsBinding.inflate(inflater, container, false).apply {
                clickHandler = onClickHandler
                viewModel = mViewModel
            }
            initWork()
            otherWork()
        }
        return mBinding.root
    }

    private fun otherWork() {
        setupRecycler()
        callInitialApi()
    }

    private fun initWork() {
        (activity as HomeActivity).showBack(false)
        (activity as HomeActivity).showLogo(true)
        (activity as HomeActivity).setTitle("Notifications")
    }

    private fun callInitialApi() {

        var list = ResponseNotifications()
        for(i in 0..10)
            list.add(Notification())

        handleResponse(list)

        mViewModel.callNotificationsApi().observe(viewLifecycleOwner, Observer {
                handleResponse(it.data)
        })
    }

    private fun handleResponse(data: ResponseNotifications?) {
        if(!data.isEmptyy(mBinding.tvNoData))
            adapter.setNewItems(data!!)
    }

    private fun setupRecycler() {
        mBinding.recycler.adapter = adapter
    }

    private fun setupClicks() {

    }

    private fun setupViewModel() {
        mViewModel = ViewModelProviders.of(this, MyViewModelProvider(commonCallbacks as AsyncViewController)).get(NotificationsViewModel::class.java)
    }

    inner class ClickHandler {

    }

}
