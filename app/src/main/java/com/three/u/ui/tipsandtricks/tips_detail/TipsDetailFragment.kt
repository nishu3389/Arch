package com.three.u.ui.tipsandtricks.tips_detail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.three.u.R
import com.three.u.base.AsyncViewController
import com.three.u.base.BaseFragment
import com.three.u.base.MyViewModelProvider
import com.three.u.base.set
import com.three.u.databinding.FragmentMealDetailBinding
import com.three.u.databinding.FragmentTipsDetailBinding
import com.three.u.databinding.MealDetailSliderBinding
import com.three.u.ui.activity.HomeActivity
import com.three.u.ui.meal.ResponseMealInner
import com.three.u.ui.meal.ResponseMealOuter
import com.three.u.ui.tipsandtricks.Media
import com.three.u.ui.tipsandtricks.ResponseTipsDetail


class TipsDetailFragment : BaseFragment() {

    lateinit var mAdapter: SliderAdapter
    val mClickHandler: TipsDetailFragment.ClickHandler by lazy { ClickHandler() }
    lateinit var mViewModel: TipsDetailViewModel
    lateinit var mBinding: FragmentTipsDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentTipsDetailBinding.inflate(inflater, container, false).apply {
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
        manageClicks()
        callInitialApis()
    }

    fun callInitialApis() {
        mViewModel.callTipsDetailApi(requireArguments().getString("id")!!).observe(viewLifecycleOwner, Observer {
            mViewModel.model = it.data
            mBinding.invalidateAll()
            setSlider(it.data?.media)
            mBinding.mainLayout.visibility = View.VISIBLE
        })
    }

    private fun setSlider(media: List<Media>?) {
        mAdapter = SliderAdapter(media)
        mBinding.viewPager.adapter = mAdapter
    }

    private fun initWork() {
        (activity as HomeActivity).showToolbar(false)
        (activity as HomeActivity).setTitle(getString(R.string.tips_and_tricks))
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
        ).get(TipsDetailViewModel::class.java)
    }

    inner class ClickHandler  {
        fun mealClicked(position: Int, model: ResponseMealOuter){

        }

        fun back(){
            goBack(0)
        }

    }

    private fun setupViewPager(listOfFiles: ResponseTipsDetail) {
//        mAdapter = SliderAdapter(listOfFiles)
        mBinding.viewPager.adapter = mAdapter
    }

    class SliderAdapter(private var sliderViews: List<Media>?) : PagerAdapter() {

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
