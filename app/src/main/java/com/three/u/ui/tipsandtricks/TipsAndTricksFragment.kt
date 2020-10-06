package com.three.u.ui.tipsandtricks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.three.u.R
import com.three.u.base.AsyncViewController
import com.three.u.base.BaseFragment
import com.three.u.base.MyViewModelProvider
import com.three.u.databinding.FragmentTipsAndTricksBinding
import com.three.u.ui.activity.HomeActivity
import kotlinx.coroutines.*

class TipsAndTricksFragment : BaseFragment() {

    var adapter = TipsAdapterOuter(R.layout.row_tips_outer, onClickListener = { position, model ->
        run {
            navigate(R.id.TipsDetailFragment, Pair("id", model.id))
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun otherWork() {
        setupRecycler()
        callInitialApis()
    }

    private fun setupRecycler() {
        mBinding.recyclerOuterMeal.adapter = adapter
        mViewModel.callgetPostsApi().observe(viewLifecycleOwner, Observer {
            mealList.clear()
            mealList = it.data as ArrayList<ResponseTipsOuterItem>
            adapter.setNewItems(mealList)
            adapter.addClickEventWithView(R.id.card, mClickHandler::mealClicked)
            GlobalScope.launch(Dispatchers.Main) {
                delay(100)
                mBinding.recyclerOuterMeal.visibility = View.VISIBLE
            }
        })
    }


    fun callInitialApis() {

    }

    private fun initWork() {
        (activity as HomeActivity).showLogo(true)
        (activity as HomeActivity).setTitle(getString(R.string.tips_and_tricks))
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
