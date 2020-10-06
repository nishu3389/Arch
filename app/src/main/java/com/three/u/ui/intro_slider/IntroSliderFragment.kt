package com.three.u.ui.intro_slider

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.three.u.R
import com.three.u.base.AsyncViewController
import com.three.u.base.BaseFragment
import com.three.u.base.MyViewModelProvider
import com.three.u.base.push
import com.three.u.databinding.IntroSliderBinding
import com.three.u.model.request.RequestChangePassword
import com.three.u.util.Prefs


class IntroSliderFragment : BaseFragment() {

    lateinit var mAdapter: ViewsSliderAdapter
    lateinit var mViewModel: IntroSliderViewModel
    lateinit var mBinding: IntroSliderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = IntroSliderBinding.inflate(inflater, container, false).apply {
            clickHandler = ClickHandler()
            viewModel = mViewModel
        }
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Prefs.get().isIntroShown = true
        setupViewPager(
            arrayListOf(
                R.layout.row_slider1,
                R.layout.row_slider2,
                R.layout.row_slider3,
                R.layout.row_slider4
            )
        )
    }


    private fun setupViewModel() {
        mViewModel =
            ViewModelProviders.of(this, MyViewModelProvider(commonCallbacks as AsyncViewController))
                .get(IntroSliderViewModel::class.java)
        mViewModel.requestChangePassword.set(RequestChangePassword())
    }


    inner class ClickHandler {

        fun onClickSubmit() {
            mViewModel.requestChangePassword()
        }
    }


    private fun setupViewPager(listOfFiles: ArrayList<Int>) {
        mAdapter = ViewsSliderAdapter(listOfFiles)
        mBinding.viewPager.adapter = mAdapter
        mBinding.tvNext?.push()?.setOnClickListener {

            var position = mBinding.viewPager.currentItem

            if (position == 3)
                findNavController().navigate(R.id.LoginFragment)
            else
                mBinding.viewPager.setCurrentItem(position + 1, true)
        }

        mBinding.tvSkip.push()?.setOnClickListener {
            findNavController().navigate(R.id.LoginFragment)
        }

    }

    class ViewsSliderAdapter(private var sliderViews: ArrayList<Int>?) : PagerAdapter() {

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            var inflater = container.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view: View = inflater.inflate(sliderViews?.get(position)!!, container, false)
            (container as ViewPager).addView(view, 0)
            return view
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
