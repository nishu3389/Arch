package com.raykellyfitness.ui.meal.meal_detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.raykellyfitness.R
import com.raykellyfitness.base.*
import com.raykellyfitness.databinding.FragmentMealDetailBinding
import com.raykellyfitness.databinding.FragmentTipsDetailBinding
import com.raykellyfitness.databinding.MealDetailSliderBinding
import com.raykellyfitness.ui.activity.HomeActivity
import com.raykellyfitness.ui.tipsandtricks.Media
import com.raykellyfitness.ui.tipsandtricks.tips_detail.VideoPlayerActivity


class MealDetailFragment : BaseFragment() {

    lateinit var mAdapter: SliderAdapter
    val mClickHandler: ClickHandler by lazy { ClickHandler() }
    lateinit var mViewModel: MealDetailViewModel
    lateinit var mBinding: FragmentMealDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
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
        callInitialApis()
    }

    private fun callInitialApis() {
        mBinding.mainLayout.visibility = View.INVISIBLE
        mViewModel.callTipsDetailApi(requireArguments().getString("id")!!).observe(viewLifecycleOwner, Observer {
            mViewModel.model = it.data
            mBinding.tvDesc.setText(Html.fromHtml(it.data!!.description))
            mBinding.tvDate.text = it.data!!.date.changeTimeFormat("yyyy-MM-dd hh:mm:ss","EEEE dd MMM, yyyy")
            mBinding.invalidateAll()
            setSlider(it.data?.media)
            mBinding.mainLayout.visibility = View.VISIBLE
        })
    }

    private fun setSlider(media: List<Media>?) {
        mAdapter = SliderAdapter(media,this)
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

    private fun setupViewModel() {
        mViewModel = ViewModelProviders.of(this, MyViewModelProvider(commonCallbacks as AsyncViewController)).get(MealDetailViewModel::class.java)
    }

    inner class ClickHandler  {
        fun back(){
            goBack(0)
        }
    }

    class SliderAdapter(private var sliderViews: List<Media>?, var fragment: MealDetailFragment) : PagerAdapter() {

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            var inflater = container.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val binding: MealDetailSliderBinding = DataBindingUtil.inflate(inflater, R.layout.meal_detail_slider, container, false)

            setImageAndClickWork(container.context,binding,position)

            (container as ViewPager).addView(binding.root, 0)
            return binding.root
        }

        private fun setImageAndClickWork(binding1: Context, binding: MealDetailSliderBinding, position: Int) {
            val model = sliderViews?.get(position)

            binding.imgThumb.set(binding1, model?.url)

            binding.imgThumb?.setOnClickListener {
                if(model?.media_type.equals("image")){
//                    fragment.showImageDialog(model?.url!!)
                    fragment.showImageDialog(model?.url!!)
                }
                else{
//                    fragment.startActivity(Intent(fragment.context,VideoPlayerActivity::class.java).putExtra("url",model?.url))
                    fragment.startActivity(
                        Intent(fragment.context,
                            VideoPlayerActivity::class.java).putExtra("url","http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4"))
                }
            }

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
