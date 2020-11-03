package com.raykellyfitness.ui.tipsandtricks.tips_detail

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.raykellyfitness.R
import com.raykellyfitness.base.*
import com.raykellyfitness.databinding.FragmentTipsDetailBinding
import com.raykellyfitness.databinding.MealDetailSliderBinding
import com.raykellyfitness.networking.Api
import com.raykellyfitness.ui.activity.HomeActivity
import com.raykellyfitness.ui.tipsandtricks.Media
import com.raykellyfitness.util.Constant.IMAGE
import com.raykellyfitness.util.Constant.POST_TYPE_BLOG
import com.raykellyfitness.util.Constant.URL
import com.raykellyfitness.util.ParcelKeys
import com.raykellyfitness.util.ParcelKeys.PK_POST_ID

class TipsDetailFragment : BaseFragment() {

    var type : String = ""
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
        callInitialApis()
    }

    private fun callInitialApis() {
        mBinding.mainLayout.visibility = View.INVISIBLE
        mViewModel.callTipsDetailApi(requireArguments().getString(PK_POST_ID)!!, type).observe(
            viewLifecycleOwner,
            Observer {
                mViewModel.model = it.data
                mBinding.tvDesc.setHtml(it.data!!.description)
                mBinding.tvDate.text = it.data!!.date.changeTimeFormat(getString(R.string.from_date), getString(R.string.to_date))

                if(type.equals(POST_TYPE_BLOG)){
                    mBinding.tvTitle.gone()
                    mBinding.tvWeek.text = mViewModel.model?.title
                    mBinding.tvDate.gone()
                }else{
                    mBinding.tvTitle.visible()
                    mBinding.tvTitle.text = mViewModel.model?.title
                    mBinding.tvWeek.text = mViewModel.type
                }

                setSlider(it.data?.media)
                mBinding.mainLayout.visibility = View.VISIBLE
            })
    }

    override fun onApiRequestFailed(apiUrl: String, errCode: Int, errorMessage: String): Boolean {
        commonCallbacks?.showAlertDialog(
            errorMessage,
            DialogInterface.OnClickListener { _, _ ->
                activity?.onBackPressed()
            })
        return true
    }

    private fun setSlider(media: List<Media>?) {
        if(!media.isEmptyy() && media?.size!!>1)
            mBinding.viewShadow.visible()
        else
            mBinding.viewShadow.gone()

        mAdapter = SliderAdapter(mBinding,media, this)
        mBinding.viewPager.adapter = mAdapter
    }

    private fun initWork() {
        (activity as HomeActivity).showToolbar(false)

        if(type.isEmptyy()){
            type = arguments?.getString(ParcelKeys.PK_POST_TYPE)!!


            if(!type.equals(POST_TYPE_BLOG))
            mViewModel.type = arguments?.getString(ParcelKeys.PK_POST_DAY)!!
        }

    }

    override fun onDetach() {
        super.onDetach()
        (activity as HomeActivity).showToolbar(true)
    }

    private fun setupViewModel() {
        mViewModel = ViewModelProviders.of(
            this,
            MyViewModelProvider(commonCallbacks as AsyncViewController)
        ).get(TipsDetailViewModel::class.java)
    }

    inner class ClickHandler  {
        fun back(){
            goBack(0)
        }
    }

    class SliderAdapter(var mBinding: FragmentTipsDetailBinding, private var sliderViews: List<Media>?, var fragment: TipsDetailFragment) : PagerAdapter() {

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            var inflater = container.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val binding: MealDetailSliderBinding = DataBindingUtil.inflate(inflater, R.layout.meal_detail_slider, container, false)

            setImageAndClickWork(container.context, binding, position)

            (container as ViewPager).addView(binding.root, 0)
            return binding.root
        }

        private fun setImageAndClickWork(
            binding1: Context,
            binding: MealDetailSliderBinding,
            position: Int
        ) {
            val model = sliderViews?.get(position)

          /*  val myOptions = RequestOptions().centerCrop().override(300,300)

            Glide.with(binding1)
                .asBitmap()
                .placeholder(R.drawable.placeholder)
                .apply(myOptions)
                .load(model?.url)
                .into(binding.imgThumb)*/

            binding.imgThumb.set(binding1, model?.url)

            when(model?.media_type){
                IMAGE -> binding.imgPlay.gone()
                "" -> binding.imgPlay.gone()
                else -> binding.imgPlay.visible()
            }

            binding.imgThumb?.setOnClickListener {
                clickWork(model)
            }

            binding.imgPlay?.setOnClickListener {
                clickWork(model)
            }

        }

        fun clickWork(model: Media?) {
            if(model?.url.isEmptyy() || !URLUtil.isValidUrl(model?.url))
                return

            if(model?.media_type.equals(IMAGE))
                fragment.showImageDialog(model?.url!!)

            else
                fragment.startActivity(
                    Intent(fragment.context, VideoPlayerActivity::class.java).putExtra(
                        URL,
                        model?.url
                    )
                )
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
