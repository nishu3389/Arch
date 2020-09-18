package com.three.u.ui.reset_password
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.three.u.base.AsyncViewController
import com.three.u.base.BaseViewModel
import com.three.u.model.request.RequestResetPassword
import com.three.u.model.response.MasterResponse
import com.three.u.model.response.ResponseLogin
import com.three.u.util.Validator
import com.three.u.networking.Api

class ResetPasswordViewModel(controller: AsyncViewController) : BaseViewModel(controller) {
    var isNumber : Boolean = false
    val request = ObservableField<RequestResetPassword>()
    val responseForgotPassword = MutableLiveData<MasterResponse<ResponseLogin>>()

    val errOTP = ObservableField<String>("")
    val errNewPassword = ObservableField<String>("")
    val errConfirmPassword = ObservableField<String>("")


    fun validateInput(): Boolean {

        val data = request.get() ?: return false

        if (!Validator.validateResetPasswordForm(data!!, errOTP, errNewPassword, errConfirmPassword))
            return false
        return true
    }

    fun callResetPasswordApi() {
        baseRepo.restClient.callApi(Api.RESET_PASSWORD, request.get(), responseForgotPassword)
    }
}