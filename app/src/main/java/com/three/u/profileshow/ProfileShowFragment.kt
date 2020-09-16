package com.three.u.profileshow

import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.three.u.R
import com.three.u.activity.MainBoardActivity
import com.three.u.base.*
import com.three.u.databinding.FragmentProfileShowBinding
import com.three.u.model.request.ResponseCountryStateCity
import com.three.u.model.response.MasterResponse
import com.three.u.model.response.ResponseGetProfile
import com.three.u.model.response.ResponseLogin
import com.three.u.util.Prefs

class ProfileShowFragment : BaseFragment() {

    var model : ResponseGetProfile? = null
    lateinit var mViewModel: ProfileShowViewModel
    lateinit var mBinding: FragmentProfileShowBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentProfileShowBinding.inflate(inflater, container, false).apply {
            clickHandler = ClickHandler()
            viewModel = mViewModel
        }
        mViewModel.callGetUserProfileApi()
        return mBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        (activity as MainBoardActivity).unlockDrawer()
        setHasOptionsMenu(true)
        getIntentData()
    }

    private fun getIntentData() {
        (activity as MainBoardActivity).setTitle(context?.titleWithLogo(R.string.profile))
        mBinding.tvChangePassword.visibility = if(Prefs.get().isFromSocialLogin == true) View.GONE else View.VISIBLE
    }

    private fun setupViewModel() {
        mViewModel = ViewModelProviders.of(this, MyViewModelProvider(commonCallbacks as AsyncViewController)).get(ProfileShowViewModel::class.java)
        mViewModel.callGetUserProfileApi().observe(this, Observer {
            handleProfileResponse(it)
        })
    }

    private fun handleProfileResponse(data: MasterResponse<ResponseGetProfile>?) {
        if (data?.responseCode == 200) {

            model = data.data

            model?.isNotificationEnabled?.let {
                mBinding.btnSwitch.isChecked = model?.isNotificationEnabled!!
            }
            mBinding.edtContactNumber.setText(model?.contact)
            mBinding.name.text = model?.customerName
            mBinding.edtEmail.setText(model?.email)
            mBinding.edtAddress.setText(model?.addressLine)

            mBinding.edtSuburb.setText(model?.suburb)
            mBinding.edtPostcode.setText(model?.postcode)

            mBinding.edtAge.setText(""+model?.age)

            mBinding.edtGender.setGender(model?.gender)
            context?.let { mBinding.imageView.set(it,model?.logoProfilePic, R.drawable.pic_user) }

            var responseLogin:ResponseLogin
            responseLogin= Prefs.get().loginData!!
            responseLogin.logoProfilePic=model?.logoProfilePic
            responseLogin.customerName=model?.customerName
            responseLogin.email=model?.email


            Prefs.get().loginData=responseLogin

            (activity as MainBoardActivity).upadteNameImage()

            mViewModel.callGetCountryStateCityApi().observe(viewLifecycleOwner, Observer { handleCountryStateCityResponse(it,model) })

        }
    }

    private fun handleCountryStateCityResponse(it: MasterResponse<ResponseCountryStateCity>?, modelProfile:ResponseGetProfile?) {
        lateinit var listCity: java.util.ArrayList<ResponseCountryStateCity.City>
        lateinit var listState: java.util.ArrayList<ResponseCountryStateCity.State>
        lateinit var listCountries: java.util.ArrayList<ResponseCountryStateCity.Country>

        it?.data?.listCountry.let {
            listCountries = it as ArrayList<ResponseCountryStateCity.Country>
        }
        it?.data?.listState.let {
            listState = it as ArrayList<ResponseCountryStateCity.State>
        }
        it?.data?.listCity.let {
            listCity = it as ArrayList<ResponseCountryStateCity.City>
        }

        if(modelProfile?.countryId != null && modelProfile?.countryId != 0)
            mBinding.edtCountry.setText(listCountries.filter { it.id == modelProfile?.countryId }.get(0).name)

        if(modelProfile?.stateId != null && modelProfile?.stateId != 0)
            mBinding.edtState.setText(listState.filter { it.id == modelProfile?.stateId }.get(0).name)

        if(modelProfile?.cityId != null && modelProfile?.cityId != 0)
            mBinding.edtCity.setText(listCity.filter { it.id == modelProfile?.cityId }.get(0).name)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_profile, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.mi_settings) {
            findNavController().navigate(R.id.ProfileEditFragment)
            return true
        } else return super.onOptionsItemSelected(item)
    }

    inner class ClickHandler {

        fun onChanagePassword() {
            commonCallbacks?.hideKeyboard()
            findNavController().navigate(R.id.ChangePasswordFragment)
        }

        fun onClickNotification() {
            if(model?.isNotificationEnabled==null)
                model?.isNotificationEnabled = false

            mViewModel.requestEnableNotification.get()?.IsNotificationEnabled = if(model?.isNotificationEnabled!!) 0 else 1
            mViewModel.callEnableNotification()
        }
    }

}
