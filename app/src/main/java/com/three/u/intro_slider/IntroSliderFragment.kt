package com.three.u.intro_slider

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.three.u.R
import com.three.u.base.*
import com.three.u.databinding.IntroSliderBinding
import com.three.u.model.request.RequestChangePassword
import kotlin.collections.ArrayList


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

        setupViewPager(
            arrayListOf(
                SliderModel(R.drawable.onboarding_1, getString(R.string.slider_1_msg_1), getString(R.string.slider_1_msg_2)),
                SliderModel(
                    R.drawable.onboarding_2, getString(R.string.slider_2_msg_1), getString(
                        R.string.slider_2_msg_2
                    )
                ),
                SliderModel(
                    R.drawable.onboarding_3, getString(R.string.slider_3_msg_1), getString(
                        R.string.slider_3_msg_2
                    )
                ),
                SliderModel(
                    R.drawable.onboarding_4, getString(R.string.slider_4_msg_1), getString(
                        R.string.slider_4_msg_2
                    )
                )
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


    private fun setupViewPager(listOfFiles: ArrayList<SliderModel>) {
        mAdapter = ViewsSliderAdapter(listOfFiles)
        mBinding.viewPager2.adapter = mAdapter
    }

    class ViewsSliderAdapter(private var images: ArrayList<SliderModel>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        @NonNull
        override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.row_slider, parent, false)
            return SliderViewHolder(view)
        }

        override fun onBindViewHolder(@NonNull holder: RecyclerView.ViewHolder, position: Int) {
            holder.itemView.findViewById<ImageView>(R.id.imv_item)
                .set(holder.itemView.context, images?.get(position)?.image)
            holder.itemView.findViewById<TextView>(R.id.tv_1)
                .setText(images?.get(position)?.message1)
            holder.itemView.findViewById<TextView>(R.id.tv_2)
                .setText(images?.get(position)?.message2)
        }

        override fun getItemCount(): Int {
            return images?.size ?: 0
        }

        inner class SliderViewHolder(view: View?) :
            RecyclerView.ViewHolder(view!!) {
            var imageView: TextView? = null
        }

    }

}
