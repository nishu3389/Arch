package com.raykellyfitness.ui.reset_password
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.raykellyfitness.base.AsyncViewController
import com.raykellyfitness.base.BaseViewModel
import com.raykellyfitness.model.request.RequestResetPassword
import com.raykellyfitness.model.response.MasterResponse
import com.raykellyfitness.model.response.ResponseLogin
import com.raykellyfitness.util.Validator
import com.raykellyfitness.networking.Api

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