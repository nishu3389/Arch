package com.architecture.util

import android.content.Context
import android.util.Patterns
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.ObservableField
import com.architecture.R
import com.architecture.base.*
import com.architecture.model.request.*
import java.util.regex.Pattern


class Validator {

    companion object {

        fun showCustomToast(msg: String) {
            val inflater = MainApplication.get()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val layout = inflater.inflate(R.layout.layout_toast_custom, null, false)
            val tv = layout.findViewById(R.id.txtvw) as TextView
            tv.text = msg
            val toast = Toast(MainApplication.get())
            toast.duration = Toast.LENGTH_SHORT
            toast.view = layout
            toast.setGravity(Gravity.CENTER, 0, 100)
            toast.show()
        }


        fun isNameValid(otp: String?, errMsgHolder: ObservableField<String>): Boolean {
            if (otp?.isEmptyy() == true) {
                errMsgHolder.set(MainApplication.get().getString(R.string.err_title_missing))
                errMsgHolder.get()?.showWarning()
                return false
            } else errMsgHolder.set("")

            return true
        }


        fun isAddress(address: String?, errMsgHolder: ObservableField<String>): Boolean {
            if (address?.isEmptyy() == true) {
                errMsgHolder.set(MainApplication.get().getString(R.string.err_address_missing))
                errMsgHolder.get()?.showWarning()

                return false
            } else errMsgHolder.set("")

            return true
        }

        fun isSuburb(address: String?, errMsgHolder: ObservableField<String>): Boolean {
            if (address?.isEmpty() == true) {
                errMsgHolder.set(MainApplication.get().getString(R.string.err_suburb_missing))
                errMsgHolder.get()?.showWarning()

                return false
            } else errMsgHolder.set("")

            if (address?.equals("null")!!) {
                errMsgHolder.set(MainApplication.get().getString(R.string.err_suburb_missing))
                errMsgHolder.get()?.showWarning()

                return false
            } else errMsgHolder.set("")

            return true
        }

        fun isCity(address: Int?, errMsgHolder: ObservableField<String>): Boolean {
            if (address == null || address == 0) {
                errMsgHolder.set(MainApplication.get().getString(R.string.err_city_missing))
                errMsgHolder.get()?.showWarning()

                return false
            } else errMsgHolder.set("")

            return true
        }

        fun isState(address: Int?, errMsgHolder: ObservableField<String>): Boolean {
            if (address == null || address == 0) {
                errMsgHolder.set(MainApplication.get().getString(R.string.err_state_missing))
                errMsgHolder.get()?.showWarning()

                return false
            } else errMsgHolder.set("")

            return true
        }

        fun isCountry(address: Int?, errMsgHolder: ObservableField<String>): Boolean {
            if (address == null || address == 0) {
                errMsgHolder.set(MainApplication.get().getString(R.string.err_country_missing))
                errMsgHolder.get()?.showWarning()

                return false
            } else errMsgHolder.set("")


            return true
        }

        fun isPostcode(address: String?, errMsgHolder: ObservableField<String>): Boolean {
            if (address?.isEmpty() == true) {
                errMsgHolder.set(MainApplication.get().getString(R.string.err_postcode_missing))
                errMsgHolder.get()?.showWarning()

                return false
            } else errMsgHolder.set("")

            if (address?.equals("null")!!) {
                errMsgHolder.set(MainApplication.get().getString(R.string.err_postcode_missing))
                errMsgHolder.get()?.showWarning()

                return false
            } else errMsgHolder.set("")

            if (address?.length <= 3) {
                errMsgHolder.set(MainApplication.get().getString(R.string.err_postcode_length))
                errMsgHolder.get()?.showWarning()

                return false
            } else errMsgHolder.set("")



            return true
        }



        fun isGender(gender: String?, errMsgHolder: ObservableField<String>): Boolean {
            if (gender?.isEmpty() == true || gender.equals("0")) {
                errMsgHolder.set(MainApplication.get().getString(R.string.err_select_gender))
                errMsgHolder.get()?.showWarning()

                return false
            } else errMsgHolder.set("")


            return true
        }

        fun isOTPValid(otp: String?, errMsgHolder: ObservableField<String>): Boolean {
            if (otp?.isEmpty() == true) {
                errMsgHolder.set(MainApplication.get().getString(R.string.err_otp_missing))
                showCustomToast(errMsgHolder.get() ?: "")
                return false
            } else errMsgHolder.set("")


            return true
        }


        fun isPhoneValid(phone: String?, errMsgHolder: ObservableField<String>): Boolean {

            val diff = (phone!!.indexOf("-") + 1) - (phone.indexOf("+") + 1)
            val indexOfDash = (phone!!.indexOf("-"))
            val validExpression = "[+]\\d{1,3}[-]\\d{7,15}"

            if (phone?.isEmpty() == true) {
                errMsgHolder.set(MainApplication.get().getString(R.string.err_mobile_missing))
                errMsgHolder.get()?.showWarning()
                return false
            } else errMsgHolder.set("")


            if (!phone!!.hasPlusAndDash()) {
                errMsgHolder.set(MainApplication.get().getString(R.string.err_mobile_format))
                errMsgHolder.get()?.showWarning()
                return false
            } else errMsgHolder.set("")


            if ((phone!!.substring(indexOfDash).length < 7 || phone.substring(indexOfDash).length > 15) && (diff <= 1 || diff > 4)) {
                errMsgHolder.set(MainApplication.get().getString(R.string.err_mobile_invalid))
                errMsgHolder.get()?.showWarning()
                return false
            } else errMsgHolder.set("")


            if (phone!!.substring(indexOfDash).length < 7 || phone.substring(indexOfDash).length > 15) {
                errMsgHolder.set(MainApplication.get().getString(R.string.err_mobile_missing))
                errMsgHolder.get()?.showWarning()
                return false
            } else errMsgHolder.set("")


            if (diff <= 1 || diff > 4) {
                errMsgHolder.set(MainApplication.get().getString(R.string.err_country_code_length))
                errMsgHolder.get()?.showWarning()
                return false
            } else errMsgHolder.set("")


            if (!Pattern.compile(validExpression).matcher(phone).matches()) {
                errMsgHolder.set(MainApplication.get().getString(R.string.err_mobile_format))
                errMsgHolder.get()?.showWarning()
                return false
            } else errMsgHolder.set("")


            return true
        }

        fun isPhoneValid(phone: String?, tv: TextView): Boolean {

            val diff = (phone!!.indexOf("-") + 1) - (phone.indexOf("+") + 1)
            val indexOfDash = (phone!!.indexOf("-"))
            val validExpression = "[+]\\d{1,3}[-]\\d{7,15}"

            if (phone?.isEmpty() == true) {
                tv.text = MainApplication.get().getString(R.string.err_mobile_missing)
                tv.visible()
                return false
            }


            if (!phone!!.hasPlusAndDash()) {
                tv.text = MainApplication.get().getString(R.string.err_mobile_format)
                tv.visible()
                return false
            }


            if ((phone!!.substring(indexOfDash).length < 7 || phone.substring(indexOfDash).length > 15) && (diff <= 1 || diff > 4)) {
                tv.text = MainApplication.get().getString(R.string.err_mobile_invalid)
                tv.visible()
                return false
            }


            if (phone!!.substring(indexOfDash).length < 7 || phone.substring(indexOfDash).length > 15) {
                tv.text = MainApplication.get().getString(R.string.err_mobile_missing)
                tv.visible()
                return false
            }


            if (diff <= 1 || diff > 4) {
                tv.text = MainApplication.get().getString(R.string.err_country_code_length)
                tv.visible()
                return false
            }


            if (!Pattern.compile(validExpression).matcher(phone).matches()) {
                tv.text = MainApplication.get().getString(R.string.err_mobile_format)
                tv.visible()
                return false
            }


            return true
        }

        fun isEmailValid(email: String?, errMsgHolder: ObservableField<String>): Boolean {
            if (email?.isEmpty() == true) {
                errMsgHolder.set(MainApplication.get().getString(R.string.err_email_missing))
                errMsgHolder.get()?.showWarning()
                return false
            } else errMsgHolder.set("")

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                errMsgHolder.set(MainApplication.get().getString(R.string.err_email_invalid))
                errMsgHolder.get()?.showWarning()
                return false
            } else errMsgHolder.set("")

            return true
        }

        fun isEmailOrNumberValid(email: String?, errMsgHolder: ObservableField<String>): Boolean {
            if (email?.isEmpty() == true) {
                errMsgHolder.set(MainApplication.get().getString(R.string.err_email_missing))
                errMsgHolder.get()?.showWarning()
                return false
            } else errMsgHolder.set("")

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                errMsgHolder.set(
                    MainApplication.get().getString(R.string.err_email_or_phone_invalid)
                )
                errMsgHolder.get()?.showWarning()
                return false
            } else errMsgHolder.set("")

            return true
        }



        fun isPasswordValidLogin(
            password: String?,
            errMsgHolder: ObservableField<String>
        ): Boolean {
            if (password?.isEmptyy() == true) {
                errMsgHolder.set(MainApplication.get().getString(R.string.err_password_missing))
                errMsgHolder.get()?.showWarning()
                return false
            } else errMsgHolder.set("")



            return true
        }


        fun isConfirmPasswordValid(
            password: String?,
            errMsgHolder: ObservableField<String>
        ): Boolean {
            if (password?.isEmptyy() == true) {
                errMsgHolder.set(MainApplication.get().getString(R.string.err_cpassword_missing))
                errMsgHolder.get()?.showWarning()
                return false
            } else errMsgHolder.set("")

            if (password!!.length !in 6..20) {
                errMsgHolder.set(
                    MainApplication.get().getString(R.string.err_password_length_invalid)
                )
                errMsgHolder.get()?.showWarning()
                return false
            } else errMsgHolder.set("")

            return true
        }

        fun validateChangePasswordForm(
            data: RequestChangePassword,
            errOldPassword: ObservableField<String>,
            errNewPassword: ObservableField<String>,
            errConfirmPassword: ObservableField<String>
        ): Boolean {

            val context: Context = MainApplication.get().getContext()

            errOldPassword.set("")
            errNewPassword.set("")
            errConfirmPassword.set("")

            if (data.oldPassword.isEmpty()) {
                errOldPassword.set(context.getString(R.string.err_old_password_missing))
                errOldPassword.get()?.showWarning()
                return false
            }

            if (data.oldPassword.length < 6) {
                errOldPassword.set(context.getString(R.string.err_new_password_min_length))
                errOldPassword.get()?.showWarning()
                return false
            }

            if (data.newPassword.isEmpty()) {
                errNewPassword.set(context.getString(R.string.err_new_password_missing))
                errNewPassword.get()?.showWarning()
                return false
            }

            if (data.newPassword.length < 6) {
                errNewPassword.set(context.getString(R.string.err_new_password_min_length))
                errNewPassword.get()?.showWarning()
                return false
            }

            if (data.newPassword.length > 20) {
                errNewPassword.set(context.getString(R.string.err_new_password_max_length))
                errNewPassword.get()?.showWarning()
                return false
            }

            if (data.confirmPassword.isEmpty()) {
                errConfirmPassword.set(context.getString(R.string.err_confirm_password_missing))
                errConfirmPassword.get()?.showWarning()
                return false
            }

            if (data.newPassword.trim() != data.confirmPassword.trim()) {
                errConfirmPassword.set(context.getString(R.string.err_passwords_not_same))
                errConfirmPassword.get()?.showWarning()
                return false
            }

            /*if (data.oldPassword.trim() == data.newPassword.trim()){
                errConfirmPassword.set(context.getString(R.string.err_new_password_matches_new_password))
                return false
            }*/

            return true
        }

        fun validateResetPasswordForm(
            data: RequestResetPassword,
            errOTP: ObservableField<String>,
            errNewPassword: ObservableField<String>,
            errConfirmPassword: ObservableField<String>
        ): Boolean {

            val context: Context = MainApplication.get().getContext()

            errOTP.set("")
            errNewPassword.set("")
            errConfirmPassword.set("")

            if (data.otp.isEmpty()) {
                errOTP.set(context.getString(R.string.err_otp))
                errOTP.get()?.showWarning()
                return false
            }

            if (data.otp.length < 4) {
                errOTP.set(context.getString(R.string.err_otp_length))
                errOTP.get()?.showWarning()
                return false
            }


            if (data.newPassword.isEmpty()) {
                errNewPassword.set(context.getString(R.string.err_new_password_missing))
                errNewPassword.get()?.showWarning()
                return false
            }

            if (data.newPassword.length < 6) {
                errNewPassword.set(context.getString(R.string.err_new_password_min_length))
                errNewPassword.get()?.showWarning()
                return false
            }

            if (data.newPassword.length > 20) {
                errNewPassword.set(context.getString(R.string.err_new_password_max_length))
                errNewPassword.get()?.showWarning()
                return false
            }

            if (data.confirmPassword.isEmpty()) {
                errConfirmPassword.set(context.getString(R.string.err_confirm_password_missing))
                errConfirmPassword.get()?.showWarning()
                return false
            }

            if (data.newPassword.trim() != data.confirmPassword.trim()) {
                errConfirmPassword.set(context.getString(R.string.err_passwords_not_same))
                errConfirmPassword.get()?.showWarning()
                return false
            }

            return true
        }


        fun validatePasswordForm(
            data: RequestUserRegister,
            errPassword: ObservableField<String>,
            errConfirmPassword: ObservableField<String>
        ): Boolean {

            val context: Context = MainApplication.get().getContext()

            errPassword.set("")
            errConfirmPassword.set("")
            errConfirmPassword.set("")

            if (data.password?.isEmpty()!!) {
                errPassword.set(context.getString(R.string.err_password_missing))
                //showCustomToast(errOldPassword.get() ?: "")
                return false
            }

            if (data.confirmPassword.isEmpty()) {
                errConfirmPassword.set(context.getString(R.string.err_confirm_password_missing))
//                showCustomToast(errConfirmPassword.get() ?: "")
                return false
            }

            if (data.password!!.length < 3) {
                errPassword.set(context.getString(R.string.err_new_password_min_length))
//                showCustomToast(errPassword.get() ?: "")
                return false
            }

            if (data.password!!.length > 20) {
                errPassword.set(context.getString(R.string.err_new_password_max_length))
//                showCustomToast(errPassword.get() ?: "")
                return false
            }



            if (data.password!!.trim() != data.confirmPassword.trim()) {
                errConfirmPassword.set(context.getString(R.string.err_passwords_not_same))
//                showCustomToast(errConfirmPassword.get() ?: "")
                return false
            }


            return true
        }


    }

}