package com.three.u.ui.profile

import android.os.Bundle
import android.view.*
import androidx.lifecycle.ViewModelProviders
import com.three.u.R
import com.three.u.ui.activity.HomeActivity
import com.three.u.base.*
import com.three.u.databinding.FragmentProfileShowBinding
import com.three.u.model.response.ResponseGetProfile

class ProfileFragment : BaseFragment() {

    var model : ResponseGetProfile? = null
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

        setHasOptionsMenu(true)
        getIntentData()
    }

    private fun getIntentData() {
        (activity as HomeActivity).setTitle(context?.titleWithLogo(R.string.profile))
    }

    private fun setupViewModel() {
        mViewModel = ViewModelProviders.of(this, MyViewModelProvider(commonCallbacks as AsyncViewController)).get(ProfileViewModel::class.java)

    }


}
