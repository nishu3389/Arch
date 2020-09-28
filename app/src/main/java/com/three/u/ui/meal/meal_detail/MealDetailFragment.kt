package com.three.u.ui.meal.meal_detail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.three.u.R
import com.three.u.base.AsyncViewController
import com.three.u.base.BaseFragment
import com.three.u.base.MyViewModelProvider
import com.three.u.base.set
import com.three.u.databinding.FragmentMealDetailBinding
import com.three.u.databinding.MealDetailSliderBinding
import com.three.u.ui.activity.HomeActivity
import com.three.u.ui.meal.ResponseMealInner
import com.three.u.ui.meal.ResponseMealOuter


class MealDetailFragment : BaseFragment() {

    lateinit var mAdapter: SliderAdapter
    val mClickHandler: MealDetailFragment.ClickHandler by lazy { ClickHandler() }
    lateinit var mViewModel: MealDetailViewModel
    lateinit var mBinding: FragmentMealDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentMealDetailBinding.inflate(inflater, container, false).apply {
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
        setupViewPager(
            arrayListOf(
                ResponseMealInner(
                    "Pumpkin soup 1",
                    type = "image",
                    week = "Week 01",
                    title = "Pumpkin Soup 1",
                    url = "http://lorempixel.com/800/400/"
                ),
                ResponseMealInner(
                    "Pumpkin soup 2",
                    type = "video",
                    week = "Week 02",
                    title = "Pumpkin Soup 2",
                    url = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
                ),
                ResponseMealInner(
                    "Pumpkin soup 3",
                    type = "image",
                    week = "Week 03",
                    title = "Pumpkin Soup 3",
                    url = "http://lorempixel.com/820/420/"
                ),
                ResponseMealInner(
                    "Pumpkin soup 4",
                    type = "image",
                    week = "Week 04",
                    title = "Pumpkin Soup 4",
                    url = "http://lorempixel.com/850/450/"
                )
            )
        )
        manageClicks()
        callInitialApis()
    }



    fun callInitialApis() {
        mViewModel.model = ResponseMealInner(
            "Pumpkin soup 1",
            type = "image",
            week = "Week 01",
            title = "Pumpkin Soup 1",
            url = "http://lorempixel.com/800/400/"
        )
    }

    private fun initWork() {
        (activity as HomeActivity).showToolbar(false)
    }

    override fun onDetach() {
        super.onDetach()
        (activity as HomeActivity).showToolbar(true)
    }

    private fun manageClicks() {

    }


    private fun setupViewModel() {
        mViewModel = ViewModelProviders.of(
            this,
            MyViewModelProvider(commonCallbacks as AsyncViewController)
        ).get(MealDetailViewModel::class.java)
    }

    inner class ClickHandler  {
        fun mealClicked(position: Int, model: ResponseMealOuter){

        }

        fun back(){
            goBack(0)
        }

    }

    private fun setupViewPager(listOfFiles: ArrayList<ResponseMealInner>) {
        mAdapter = SliderAdapter(listOfFiles)
        mBinding.viewPager.adapter = mAdapter
    }

    class SliderAdapter(private var sliderViews: ArrayList<ResponseMealInner>?) : PagerAdapter() {

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            var inflater = container.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val binding: MealDetailSliderBinding = DataBindingUtil.inflate(inflater, R.layout.meal_detail_slider, container, false)
            binding.imgThumb.set(container.context, sliderViews?.get(position)?.url)

            (container as ViewPager).addView(binding.root, 0)
            return binding.root
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object`
        }

        override fun getCount(): Int {
            return sliderViews?.size ?: 0
        }

    }

}
