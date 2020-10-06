package com.three.u.ui.tipsandtricks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.three.u.R
import com.three.u.base.*
import com.three.u.databinding.FragmentTipsAndTricksBinding
import com.three.u.model.response.MasterResponse
import com.three.u.networking.Api
import com.three.u.networking.Api.POST_TYPE_EXERCISE
import com.three.u.networking.Api.POST_TYPE_MEAL
import com.three.u.networking.Api.POST_TYPE_TIPS
import com.three.u.ui.activity.HomeActivity
import kotlinx.coroutines.*

class TipsAndTricksFragment : BaseFragment() {

    var type: String = ""

    var adapter = TipsAdapterOuter(R.layout.row_tips_outer, onClickListener = { position, model ->
        run {
            navigate(R.id.TipsDetailFragment, Pair("id", model.id), Pair("type", type))
        }
    })


    var mealList = arrayListOf<ResponseTipsOuterItem>()
    val mClickHandler: ClickHandler by lazy { ClickHandler() }
    lateinit var mViewModel: TipsAndTricksViewModel
    lateinit var mBinding: FragmentTipsAndTricksBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
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
//        mBinding.recyclerExercise.visibility = View.INVISIBLE
        mBinding.recyclerOuterMeal.visibility = View.INVISIBLE
        mViewModel.callgetPostsApi(type).observe(viewLifecycleOwner, Observer { handleTipsOrMealResponse(it) })
    }


    private fun handleTipsOrMealResponse(it: MasterResponse<ResponseTipsOuter>?) {
        mealList.clear()
        mealList = it?.data as ArrayList<ResponseTipsOuterItem>
        hideShowViews()
        adapter.setNewItems(mealList)
        adapter.addClickEventWithView(R.id.card, mClickHandler::mealClicked)
        GlobalScope.launch(Dispatchers.Main) {
            delay(100)
            mBinding.recyclerOuterMeal.visibility = View.VISIBLE
        }
    }

    private fun hideShowViews() {
        if(mealList!=null && mealList.size>0){
            mBinding.tvNoData.gone()
            mBinding.recyclerOuterMeal.visible()
        }else{
            mBinding.tvNoData.visible()
            mBinding.recyclerOuterMeal.gone()
        }
    }

    private fun initWork() {
        (activity as HomeActivity).showLogo(true)
        if (type.isEmptyy()) {
            type = arguments?.getString("type")!!
            when (type) {
                POST_TYPE_MEAL -> {
                    (activity as HomeActivity).setTitle(getString(R.string.meal))
//                    mBinding.recyclerExercise.visibility = View.GONE
                    mBinding.recyclerOuterMeal.visibility = View.VISIBLE
                }
                POST_TYPE_TIPS -> {
                    (activity as HomeActivity).setTitle(getString(R.string.tips_and_tricks))
//                    mBinding.recyclerExercise.visibility = View.GONE
                    mBinding.recyclerOuterMeal.visibility = View.VISIBLE
                }
                POST_TYPE_EXERCISE -> {
                    (activity as HomeActivity).setTitle(getString(R.string.exercise))
//                    mBinding.recyclerExercise.visibility = View.VISIBLE
                    mBinding.recyclerOuterMeal.visibility = View.GONE
                }
            }
        }
    }

    private fun setupViewModel() {
        mViewModel = ViewModelProviders.of(this, MyViewModelProvider(commonCallbacks as AsyncViewController)).get(TipsAndTricksViewModel::class.java)
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
