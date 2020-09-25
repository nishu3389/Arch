package com.three.u.ui.meal

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
import com.three.u.databinding.FragmentMealPlanBinding
import com.three.u.ui.activity.HomeActivity

class MealPlanFragment : BaseFragment() {

    var adapter = MealOuterAdapter(R.layout.row_meal_outer)
    var mealList = arrayListOf(ResponseMealOuter("Week 1"),ResponseMealOuter("Week 2"),ResponseMealOuter("Week 3"),ResponseMealOuter("Week 4"))
    val mClickHandler: MealPlanFragment.ClickHandler by lazy { ClickHandler() }
    lateinit var mViewModel: MealPlanViewModel
    lateinit var mBinding: FragmentMealPlanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = FragmentMealPlanBinding.inflate(inflater, container, false).apply {
            clickHandler = ClickHandler()
            viewModel = mViewModel

        }

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initWork()
        otherWork()

    }

    private fun otherWork() {
        setupRecycler()
        manageClicks()
        callInitialApis()
    }

    private fun setupRecycler() {
        mBinding.recyclerOuterMeal.adapter = adapter

        adapter.setNewItems(mealList)
        adapter.addClickEventWithView(R.id.card, mClickHandler::mealClicked)
    }



    fun callInitialApis() {

    }

    private fun initWork() {
        (activity as HomeActivity).showLogo(true)
        (activity as HomeActivity).setTitle(getString(R.string.meal))
    }

    private fun manageClicks() {

    }


    private fun setupViewModel() {
        mViewModel = ViewModelProviders.of(this, MyViewModelProvider(commonCallbacks as AsyncViewController)).get(MealPlanViewModel::class.java)
    }

    inner class ClickHandler  {
        fun mealClicked(position : Int, model : ResponseMealOuter){

            if(!model.isOpen){
                mealList.forEach {
                    it.isOpen = false
                }
                model.isOpen = true
                adapter.notifyDataSetChanged()
            }else{
                mealList.forEach {
                    it.isOpen = false
                }
                adapter.notifyDataSetChanged()
            }

        }

    }



}
