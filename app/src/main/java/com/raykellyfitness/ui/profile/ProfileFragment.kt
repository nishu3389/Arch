package com.raykellyfitness.ui.profile

import android.os.Bundle
import android.view.*
import androidx.lifecycle.ViewModelProviders
import com.raykellyfitness.R
import com.raykellyfitness.ui.activity.HomeActivity
import com.raykellyfitness.base.*
import com.raykellyfitness.databinding .FragmentProfileShowBinding

class ProfileFragment : BaseFragment() {

    lateinit var mViewModel: ProfileViewModel
    lateinit var mBinding: FragmentProfileShowBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = FragmentProfileShowBinding.inflate(inflater, container, false).apply {
            viewModel = mViewModel
        }
        return mBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as HomeActivity).showLogoAndTitle(getString(R.string.profile))
    }

    private fun setupViewModel() {
        mViewModel = ViewModelProviders.of(this, MyViewModelProvider(commonCallbacks as AsyncViewController)).get(ProfileViewModel::class.java)

    }


}
