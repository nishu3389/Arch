package com.raykellyfitness.ui.tipsandtricks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.raykellyfitness.R
import com.raykellyfitness.base.*
import com.raykellyfitness.databinding.FragmentTipsAndTricksBinding
import com.raykellyfitness.model.response.MasterResponse
import com.raykellyfitness.ui.activity.HomeActivity
import com.raykellyfitness.util.Constant.POST_TYPE_BLOG
import com.raykellyfitness.util.Constant.POST_TYPE_EXERCISE
import com.raykellyfitness.util.Constant.POST_TYPE_MEAL
import com.raykellyfitness.util.Constant.POST_TYPE_MOTIVATION
import com.raykellyfitness.util.Constant.POST_TYPE_TIPS
import com.raykellyfitness.util.ParcelKeys
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class TipsAndTricksFragment : BaseFragment() {

    var type: String = ""
    var fromNotification = false
    var fromNotificationList = false
    var day = ""

    var adapter = TipsAdapterOuter(type, R.layout.row_tips_outer) { model, modelOuter ->
        navigate(R.id.TipsDetailFragment,
                 Pair(ParcelKeys.PK_POST_ID, model.id),
                 Pair(ParcelKeys.PK_POST_TYPE, type),
                 Pair(ParcelKeys.PK_POST_DAY, modelOuter.day))
    }

    var mealList = arrayListOf<ResponseTipsOuterItem>()
    val mClickHandler: ClickHandler by lazy { ClickHandler() }
    lateinit var mViewModel: TipsAndTricksViewModel
    lateinit var mBinding: FragmentTipsAndTricksBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        if (!::mBinding.isInitialized) {
            mBinding = FragmentTipsAndTricksBinding.inflate(inflater, container, false).apply {
                clickHandler = ClickHandler()
                viewModel = mViewModel
            }
            initWork()
            otherWork()
        }

        return mBinding.root
    }

    private fun otherWork() {
        setupRecycler()
        callInitialApis()
    }

    private fun setupRecycler() {
        mBinding.recyclerOuterMeal.adapter = adapter
    }

    fun callInitialApis() {
        mBinding.recyclerOuterMeal.visibility = View.INVISIBLE
        mViewModel.callgetPostsApi(type).observe(viewLifecycleOwner, Observer { handleTipsOrMealResponse(it) })
    }

    private fun handleTipsOrMealResponse(it: MasterResponse<ResponseTipsOuter>?) {

        mealList.clear()
        mealList = it?.data as ArrayList<ResponseTipsOuterItem>

        if (type.equals(POST_TYPE_BLOG)) {
            Collections.reverse(mealList[0].data_list)
        }

        if (type.equals(POST_TYPE_BLOG) || type.equals(POST_TYPE_EXERCISE)) {
            adapter = TipsAdapterOuter(type, R.layout.row_tips_outer) { model, modelOuter ->
                navigate(R.id.TipsDetailFragment,
                         Pair("id", model.id),
                         Pair("type", type),
                         Pair("typeName", modelOuter.day))
            }
            mBinding.recyclerOuterMeal.adapter = adapter
        }

        if (fromNotification && !mealList.isEmptyy()) mealList.get(0).open = true
        else if (fromNotificationList && !mealList.isEmptyy()) {
            try {
                mealList.filter {
                    it.day?.equals(day, true)
                }?.get(0)?.open = true
            } catch (e: Exception) {
            }
        }

        adapter.setNewItems(mealList)
        adapter.addClickEventWithView(R.id.card, mClickHandler::mealClicked)
        GlobalScope.launch(Dispatchers.Main) {
            delay(100)
            hideShowViews()
        }
    }

    private fun hideShowViews() {
        if (mealList != null && mealList.size > 0) {
            mBinding.tvNoData.gone()
            mBinding.recyclerOuterMeal.visible()
        } else {
            mBinding.tvNoData.visible()
            mBinding.recyclerOuterMeal.gone()
        }
    }

    private fun initWork() {
        (activity as HomeActivity).showLogo(true)
        if (type.isEmptyy()) {
            type = arguments?.getString("type")!!

            if (arguments != null && requireArguments().containsKey(ParcelKeys.PK_FROM) && requireArguments().getString(
                    ParcelKeys.PK_FROM).equals(ParcelKeys.PK_FROM_NOTIFICATION)
            ) fromNotification = true
            else if (arguments != null && requireArguments().containsKey(ParcelKeys.PK_FROM) && requireArguments().getString(
                    ParcelKeys.PK_FROM).equals(ParcelKeys.PK_FROM_NOTIFICATION_LIST)
            ) {
                fromNotificationList = true
                day = requireArguments().getString(ParcelKeys.PK_POST_DAY, "")!!
            }

            when (type) {
                POST_TYPE_MEAL -> {
                    (activity as HomeActivity).setTitle(getString(R.string.meal))
                    //                   mBinding.recyclerExercise.visibility = View.GONE
                    mBinding.recyclerOuterMeal.visibility = View.VISIBLE
                }
                POST_TYPE_TIPS -> {
                    (activity as HomeActivity).setTitle(getString(R.string.tips_and_tricks))
                    //                   mBinding.recyclerExercise.visibility = View.GONE
                    mBinding.recyclerOuterMeal.visibility = View.VISIBLE
                }
                POST_TYPE_EXERCISE -> {
                    (activity as HomeActivity).setTitle(getString(R.string.exercise))
                    //                   mBinding.recyclerExercise.visibility = View.VISIBLE
                    mBinding.recyclerOuterMeal.visibility = View.GONE
                }
                POST_TYPE_MOTIVATION -> {
                    (activity as HomeActivity).setTitle(getString(R.string.motivation))
                    //                   mBinding.recyclerExercise.visibility = View.VISIBLE
                    mBinding.recyclerOuterMeal.visibility = View.GONE
                }
                POST_TYPE_BLOG -> {
                    (activity as HomeActivity).setTitle(getString(R.string.blogs))
                    //                   mBinding.recyclerExercise.visibility = View.VISIBLE
                    mBinding.recyclerOuterMeal.visibility = View.GONE
                }
            }
        }
    }

    private fun setupViewModel() {
        mViewModel =
            ViewModelProviders.of(this, MyViewModelProvider(commonCallbacks as AsyncViewController))
                .get(TipsAndTricksViewModel::class.java)
    }

    inner class ClickHandler {
        fun mealClicked(position: Int, model: ResponseTipsOuterItem) {
            if (!model.open) {
                mealList.forEach {
                    it.open = false
                }
                model.open = true
                adapter.notifyDataSetChanged()
            } else {
                mealList.forEach {
                    it.open = false
                }
                adapter.notifyDataSetChanged()
            }
        }
    }

}
