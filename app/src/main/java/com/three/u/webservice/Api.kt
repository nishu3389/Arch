package com.three.u.webservice

import com.google.gson.reflect.TypeToken
import com.three.u.model.request.*
import com.three.u.model.request.ResponseGetSalesProfile
import com.three.u.model.response.*
import com.three.u.model.response.ResponseGetProfile

object Api {

 // const val URL_NEW_WORK = "http://3uapitest.projectstatus.in/"
    const val URL_NEW_WORK = "http://3udemop2api.projectstatus.in/"
    const val TERMS = "http://3udemop2.projectstatus.in/termsofuse"

    const val URL_DEV = "http://api.projectstatus.in/"

    const val URL_UAT = "http://apiuat.projectstatus.in/"

    const val URL_WEB = "http://3usystem.projectstatus.in"

    //    const val TERMS = URL_DEV + "termsofuse"

    const val BASE_URL = URL_NEW_WORK

    const val UPDATE_TOKEN_TO_SERVER = "Account/UpdateDeviceToken"
    const val SIGNUP = "Account/SignUp"
    const val SEND_VERIFICATION_CODE = "Account/SendVerificationCode"
    const val LOGIN = "Account/CustomerLogin"
    const val SOCIAL_LOGIN = "Account/SocialLogin"
    const val LOGOUT = "Account/UserLogout"
    const val FORGOT_PASSWORD = "Account/ForgotPassword"
    const val RESET_PASSWORD = "Account/ResetPassword"
    const val VERIFY_OTP = "Account/VerifyOtp"

    const val REDEEMFREEBOX = "Customer/RedeemFreebox"

    const val ADDMYCHECKLIST_V1 = "Customer/AddMyCheckList_V1"

    const val SEARCHNEARBYDISTRIBUTOR = "Customer/SearchNearbyDistributor"
    const val SCAN_ADVERTISEMENT_QR_CODE = "Customer/ScanAdvertisementQRCode"

    const val SEARCHSERVICEDIRECTORY = "ServiceDirectory/SearchServiceDirectory"
    const val SERVICEDETAILS = "ServiceDirectory/ServiceDetails"
    const val RateReviewDirectory = "ServiceDirectory/RateReviewDirectory"
    const val BusinessCategoryList = "Common/BusinessCategoryList"
    const val COMMON_UPLOAD_IMAGE = "Common/UploadImage"

    const val SELL_ITEM_CATEGORY = "ItemForSell/SellItemCategory"
    const val ADD_SELL_ITEM = "ItemForSell/AddSellItem"
    const val UPDATE_SELL_ITEM = "ItemForSell/UpdateSellItem"
    const val GET_MY_SELL_ITEM = "ItemForSell/GetMySellItem"
    const val MY_SELL_ITEM_LIST = "ItemForSell/MySellItemList"
    const val DELETE_SELL_ITEM = "ItemForSell/DeleteSellItem"
    const val ITEM_DETAIL = "ItemForSell/ItemDetail"
    const val GET_PROMOTIONAL_ADVERTISEMENT_POPUP =
        "PromotionAdvertisement/GetPromotionalAdvertisementPopup"
    const val GET_PROMOTIONAL_ADVERTISEMENT_STRIP =
        "PromotionAdvertisement/GetPromotionalAdvertisementStrip"
    const val BROWSE_SELL_ITEM = "ItemForSell/BrowseSellItem"
    const val ContactUsQuery = "Account/ContactUsQuery"
    const val ChangePassword = "Account/ChangePassword"
    const val COURIER_COMPANIES = "Common/CourierCompanyList"

    /*------------------------------PHASE 2 Checklist---------------------------------*/
    const val GET_CUSTOMER_CHECKLIST = "Checklist/GetCustomerChecklist"
    const val NEW_ADD_CUSTOMER_CHECKLIST = "Checklist/CreateChecklist"
    const val NEW_EDIT_CUSTOMER_CHECKLIST = "Checklist/EditChecklist"
    const val NEW_UPDATE_CUSTOMER_CHECKLIST = "Checklist/UpdateChecklist"
    const val NEW_DELETE_CUSTOMER_CHECKLIST = "Checklist/RemoveChecklist"
    const val NEW_ADD_CHECKLIST = "Checklist/ImportCheckListItems"

    const val NEW_SHARE_CHECKLIST = "ShareChecklist/ShareChecklist"
    const val NEW_GET_SHARED_USERS = "ShareChecklist/ChecklistShareWithUsers"
    const val NEW_DELETE_SHARED_USER = "ShareChecklist/RemoveChecklistShareWithUser"

    const val GETMASTERCHECKLIST = "Customer/GetMasterCheckList"
    const val GETMYCHECKLIST = "Customer/GetMyChecklist"
    const val GET_MASTER_CHECKLIST = "Checklist/GetMasterCheckList"
    const val GET_CHECKLIST_ITEMS = "Checklist/GetCheckListItems"
    const val CHANGE_CHECKLIST_ITEM_STATUS = "Checklist/GetCheckListItems"
    const val SINGLE_CHECKBOX_API_CALL = "Checklist/ChangeChecklistItemStatus"
    const val MULTIPLE_CHECKBOX_API_CALL = "Checklist/ChangeChecklistItemStatusAll"
    const val SINGLE_TODO_API_CALL = "Checklist/ChangeToDoChecklistItemStatus_V1"
    const val VERIFY_SHARE_CHECKLIST = "ShareChecklist/VerifyShareChecklist"
    const val GET_BUDGET_DETAIL = "BudgetTracker/GetBudgetDetails"
    const val AddUpdateBudget = "BudgetTracker/AddUpdateBudget"
    const val AddBudgetExpenses = "BudgetTracker/AddBudgetExpenses"
    const val GetExpenseCategory = "BudgetTracker/GetExpenseCategory"
    /*------------------------------PHASE 2 Checklist---------------------------------*/

    /*------------------------------PHASE 2 Sales Profile---------------------------------*/
    const val GETCUSTOMERPROFILE = "customer/GetCustomerProfile"
    const val GET_SALES_PROFILE = "customer/GetSalesProfile"

    const val ENABLE_NOTIFICATION = "Customer/EnableNotification"
    const val UPDATE_PROFILE = "customer/UpdateProfile"
    const val UPDATE_SALES_PROFILE = "customer/UpdateSalesProfile"
    const val CountryStateCityLookup = "common/CountryStateCityLookup"
    const val ConciergeRequest = "ConciergeRequest/ConciergeRequest"
    /*------------------------------PHASE 2 Sales Profile---------------------------------*/

    /*------------------------------PHASE 2 Stripe Add Bank Account---------------------------------*/
    const val REMOVE_BANK_ACCOUNT = "Stripe/DeleteBankAccount"
    const val ADD_BANK_ACCOUNT = "Stripe/AddBankAccount"
    const val GET_BANK_SETUP_DETAIL = "Stripe/GetBankSetupDetail"
    const val PLACE_ORDER = "Order/PlaceOrder"
    /*------------------------------PHASE 2 Stripe Add Bank Account---------------------------------*/

    /*------------------------------PHASE 2 Sell an item---------------------------------*/
    const val DONATION_BODIES = "Common/DonationBodiesList"
    /*------------------------------PHASE 2 Sell an item---------------------------------*/

    /*------------------------------PHASE 2 MY ORDERS LISTING---------------------------------*/
    const val MY_ORDERS = "Order/MyOrders"
    const val MY_ORDER_DETAIL = "Order/MyOrdersDetail"
    const val CANCLE_ORDER = "Order/CancelOrder"
    /*------------------------------PHASE 2 MY ORDERS LISTING---------------------------------*/

    /*------------------------------PHASE 2 MY ITEM LISTING---------------------------------*/
    const val MARK_SELL_ITEM_OCCUPIED = "ItemForSell/MarkSellItemOccupied"
    const val MARK_SELL_ITEM_DELIVERED = "Order/UpdateOrderStatus"
    /*------------------------------PHASE 2 MY ITEM LISTING---------------------------------*/

    /*------------------------------PHASE 2 ADVERTISEMENT---------------------------------*/
    const val ADVERTISEMENTS = "PromotionAdvertisement/GetAllAdvertisements"
    const val ADVERTISEMENT_COUNT = "PromotionAdvertisement/UpdateAdvertisementAccess"
    /*------------------------------PHASE 2 ADVERTISEMENT---------------------------------*/

    fun getApiRequestType(url: String): ApiRequestType {

        val result = ApiRequestType()
        when (url) {
            DONATION_BODIES -> {
                result.responseType =
                    object : TypeToken<MasterResponse<List<ResponseDonationBodies>>>() {}.type
                result.url = BASE_URL + DONATION_BODIES
                result.requestType = RequestType.POST
                return result
            }
            MY_ORDERS -> {
                result.responseType = object : TypeToken<MasterResponse<List<MyOrder>>>() {}.type
                result.url = BASE_URL + MY_ORDERS
                result.requestType = RequestType.POST
                return result
            }
            MY_ORDER_DETAIL -> {
                result.responseType = object : TypeToken<MasterResponse<MyOrderDetail>>() {}.type
                result.url = BASE_URL + MY_ORDER_DETAIL
                result.requestType = RequestType.POST
                return result
            }
            CANCLE_ORDER -> {
                result.responseType = object : TypeToken<MasterResponse<Boolean>>() {}.type
                result.url = BASE_URL + CANCLE_ORDER
                result.requestType = RequestType.POST
                return result
            }
            ConciergeRequest -> {
                result.responseType = object : TypeToken<MasterResponse<Boolean>>() {}.type
                result.url = BASE_URL + ConciergeRequest
                result.requestType = RequestType.POST
                return result
            }
            ADD_BANK_ACCOUNT -> {
                result.responseType = object : TypeToken<MasterResponse<Boolean>>() {}.type
                result.url = BASE_URL + ADD_BANK_ACCOUNT
                result.requestType = RequestType.POST
                return result
            }
            REMOVE_BANK_ACCOUNT -> {
                result.responseType = object : TypeToken<MasterResponse<Boolean>>() {}.type
                result.url = BASE_URL + REMOVE_BANK_ACCOUNT
                result.requestType = RequestType.POST
                return result
            }
            GET_BANK_SETUP_DETAIL -> {
                result.responseType =
                    object : TypeToken<MasterResponse<ResponseGetBankSetupDetail>>() {}.type
                result.url = BASE_URL + GET_BANK_SETUP_DETAIL
                result.requestType = RequestType.POST
                return result
            }

            LOGIN -> {
                result.responseType = object : TypeToken<MasterResponse<ResponseLogin>>() {}.type
                result.url = BASE_URL + LOGIN
                result.requestType = RequestType.POST
                return result
            }
            VERIFY_OTP -> {
                result.responseType = object : TypeToken<MasterResponse<Boolean>>() {}.type
                result.url = BASE_URL + LOGIN
                result.requestType = RequestType.POST
                return result
            }
            SIGNUP -> {
                result.responseType = object : TypeToken<MasterResponse<Boolean>>() {}.type
                result.url = BASE_URL + SIGNUP
                result.requestType = RequestType.POST
                return result
            }
            SEND_VERIFICATION_CODE -> {
                result.responseType = object : TypeToken<MasterResponse<Boolean>>() {}.type
                result.url = BASE_URL + SEND_VERIFICATION_CODE
                result.requestType = RequestType.POST
                return result
            }
            GETCUSTOMERPROFILE -> {
                result.responseType =
                    object : TypeToken<MasterResponse<ResponseGetProfile>>() {}.type
                result.url = BASE_URL + GETCUSTOMERPROFILE
                result.requestType = RequestType.POST
                return result
            }
            GET_SALES_PROFILE -> {
                result.responseType =
                    object : TypeToken<MasterResponse<ResponseGetSalesProfile>>() {}.type
                result.url = BASE_URL + GET_SALES_PROFILE
                result.requestType = RequestType.POST
                return result
            }
            UPDATE_PROFILE -> {
                result.responseType =
                    object : TypeToken<MasterResponse<ResponseUpdateProfile>>() {}.type
                result.url = BASE_URL + UPDATE_PROFILE
                result.requestType = RequestType.POST
                return result
            }
            ENABLE_NOTIFICATION -> {
                result.responseType = object : TypeToken<MasterResponse<Boolean>>() {}.type
                result.url = BASE_URL + ENABLE_NOTIFICATION
                result.requestType = RequestType.POST
                return result
            }
            UPDATE_SALES_PROFILE -> {
                result.responseType =
                    object : TypeToken<MasterResponse<Boolean>>() {}.type
                result.url = BASE_URL + UPDATE_SALES_PROFILE
                result.requestType = RequestType.POST
                return result
            }

            CountryStateCityLookup -> {
                result.responseType =
                    object : TypeToken<MasterResponse<ResponseCountryStateCity>>() {}.type
                result.url = BASE_URL + CountryStateCityLookup
                result.requestType = RequestType.POST
                return result
            }

            REDEEMFREEBOX -> {
                result.responseType =
                    object : TypeToken<MasterResponse<ResponseRedeemFreebox>>() {}.type
                result.url = BASE_URL + REDEEMFREEBOX
                result.requestType = RequestType.POST
                return result
            }
            MY_SELL_ITEM_LIST -> {
                result.responseType =
                    object : TypeToken<MasterResponse<List<MySellItemItemResponse>>>() {}.type
                result.url = BASE_URL + MY_SELL_ITEM_LIST
                result.requestType = RequestType.POST
                return result
            }
            /* UPLOAD_IMAGE -> {
                 result.responseType =
                     object : TypeToken<MasterResponse<FileUploadResponse>>() {}.type
                 result.url = BASE_URL + UPLOAD_IMAGE
                 result.requestType = RequestType.POST
                 return result
             }*/

            SEARCHNEARBYDISTRIBUTOR -> {
                result.responseType = object :
                    TypeToken<MasterResponse<List<ResponseSearchNearbyDistributor>>>() {}.type
                result.url = BASE_URL + SEARCHNEARBYDISTRIBUTOR
                result.requestType = RequestType.POST
                return result
            }
            ADDMYCHECKLIST_V1 -> {
                result.responseType =
                    object : TypeToken<MasterResponse<Any>>() {}.type
                result.url = BASE_URL + ADDMYCHECKLIST_V1
                result.requestType = RequestType.POST
                return result
            }
            NEW_ADD_CUSTOMER_CHECKLIST -> {
                result.responseType =
                    object : TypeToken<MasterResponse<Any>>() {}.type
                result.url = BASE_URL + NEW_ADD_CUSTOMER_CHECKLIST
                result.requestType = RequestType.POST
                return result
            }
            NEW_UPDATE_CUSTOMER_CHECKLIST -> {
                result.responseType = object : TypeToken<MasterResponse<Any>>() {}.type
                result.url = BASE_URL + NEW_UPDATE_CUSTOMER_CHECKLIST
                result.requestType = RequestType.POST
                return result
            }
            NEW_DELETE_CUSTOMER_CHECKLIST -> {
                result.responseType = object : TypeToken<MasterResponse<Any>>() {}.type
                result.url = BASE_URL + NEW_DELETE_CUSTOMER_CHECKLIST
                result.requestType = RequestType.POST
                return result
            }
            NEW_ADD_CHECKLIST -> {
                result.responseType =
                    object : TypeToken<MasterResponse<Any>>() {}.type
                result.url = BASE_URL + NEW_ADD_CHECKLIST
                result.requestType = RequestType.POST
                return result
            }
            NEW_SHARE_CHECKLIST -> {
                result.responseType = object : TypeToken<MasterResponse<Boolean>>() {}.type
                result.url = BASE_URL + NEW_SHARE_CHECKLIST
                result.requestType = RequestType.POST
                return result
            }
            NEW_GET_SHARED_USERS -> {
                result.responseType = object :
                    TypeToken<MasterResponse<List<ResponseSharedUsers.ResponseSharedUsersItem>>>() {}.type
                result.url = BASE_URL + NEW_GET_SHARED_USERS
                result.requestType = RequestType.POST
                return result
            }
            NEW_DELETE_SHARED_USER -> {
                result.responseType = object : TypeToken<MasterResponse<Boolean>>() {}.type
                result.url = BASE_URL + NEW_DELETE_SHARED_USER
                result.requestType = RequestType.POST
                return result
            }
            GETMASTERCHECKLIST -> {
                result.responseType =
                    object : TypeToken<MasterResponse<List<MasterChecklistItems>>>() {}.type
                result.url = BASE_URL + GETMASTERCHECKLIST
                result.requestType = RequestType.POST
                return result
            }

            LOGOUT -> {
                result.responseType =
                    object : TypeToken<MasterResponse<Boolean>>() {}.type
                result.url = BASE_URL + LOGOUT
                result.requestType = RequestType.POST
                return result
            }

            FORGOT_PASSWORD -> {
                result.responseType =
                    object : TypeToken<MasterResponse<Boolean>>() {}.type
                result.url = BASE_URL + FORGOT_PASSWORD
                result.requestType = RequestType.POST
                return result
            }
            RESET_PASSWORD -> {
                result.responseType =
                    object : TypeToken<MasterResponse<Boolean>>() {}.type
                result.url = BASE_URL + RESET_PASSWORD
                result.requestType = RequestType.POST
                return result
            }

            /* CHANGECHECKLISTITEMSTATUS -> {
                 result.responseType = object : TypeToken<MasterResponse<Boolean>>() {}.type
                 result.url = BASE_URL + CHANGECHECKLISTITEMSTATUS
                 result.requestType = RequestType.POST
                 return result
             }*/
            SINGLE_CHECKBOX_API_CALL -> {
                result.responseType = object : TypeToken<MasterResponse<Any>>() {}.type
                result.url = BASE_URL + SINGLE_CHECKBOX_API_CALL
                result.requestType = RequestType.POST
                return result
            }
            MULTIPLE_CHECKBOX_API_CALL -> {
                result.responseType = object : TypeToken<MasterResponse<Any>>() {}.type
                result.url = BASE_URL + MULTIPLE_CHECKBOX_API_CALL
                result.requestType = RequestType.POST
                return result
            }
            /* ChangeToDoChecklistItemStatus -> {
                 result.responseType = object : TypeToken<MasterResponse<Boolean>>() {}.type
                 result.url = BASE_URL + ChangeToDoChecklistItemStatus
                 result.requestType = RequestType.POST
                 return result
             }*/
            SINGLE_TODO_API_CALL -> {
                result.responseType = object : TypeToken<MasterResponse<Any>>() {}.type
                result.url = BASE_URL + SINGLE_TODO_API_CALL
                result.requestType = RequestType.POST
                return result
            }
            VERIFY_SHARE_CHECKLIST -> {
                result.responseType = object : TypeToken<MasterResponse<Any>>() {}.type
                result.url = BASE_URL + VERIFY_SHARE_CHECKLIST
                result.requestType = RequestType.POST
                return result
            }
            GET_BUDGET_DETAIL -> {
                result.responseType =
                    object : TypeToken<MasterResponse<ResponseBudgetDetail>>() {}.type
                result.url = BASE_URL + GET_BUDGET_DETAIL
                result.requestType = RequestType.POST
                return result
            }
            AddUpdateBudget -> {
                result.responseType = object : TypeToken<MasterResponse<Any>>() {}.type
                result.url = BASE_URL + AddUpdateBudget
                result.requestType = RequestType.POST
                return result
            }
            AddBudgetExpenses -> {
                result.responseType = object : TypeToken<MasterResponse<Any>>() {}.type
                result.url = BASE_URL + AddBudgetExpenses
                result.requestType = RequestType.POST
                return result
            }
            GetExpenseCategory -> {
                result.responseType =
                    object : TypeToken<MasterResponse<ResponseExpenseCategory>>() {}.type
                result.url = BASE_URL + GetExpenseCategory
                result.requestType = RequestType.POST
                return result
            }
            SEARCHSERVICEDIRECTORY -> {
                result.responseType =
                    object :
                        TypeToken<MasterResponse<List<ResponseSearchServiceDirectory>>>() {}.type
                result.url = BASE_URL + SEARCHSERVICEDIRECTORY
                result.requestType = RequestType.POST
                return result
            }
            SERVICEDETAILS -> {
                result.responseType =
                    object : TypeToken<MasterResponse<ResponseServiceDetails>>() {}.type
                result.url = BASE_URL + SERVICEDETAILS
                result.requestType = RequestType.POST
                return result
            }
            SELL_ITEM_CATEGORY -> {

                result.responseType =
                    object : TypeToken<MasterResponse<List<SellItemCategoryResponse>>>() {}.type
                result.url = BASE_URL + SELL_ITEM_CATEGORY
                result.requestType = RequestType.POST
                return result
            }
            COMMON_UPLOAD_IMAGE -> {
                result.responseType =
                    object : TypeToken<MasterResponse<ListOfFile>>() {}.type
                result.url = BASE_URL + COMMON_UPLOAD_IMAGE
                result.requestType = RequestType.POST
                return result
            }
            ADD_SELL_ITEM -> {
                result.responseType =
                    object : TypeToken<MasterResponse<Any>>() {}.type
                result.url = BASE_URL + ADD_SELL_ITEM
                result.requestType = RequestType.POST
                return result
            }
            DELETE_SELL_ITEM -> {
                result.responseType =
                    object : TypeToken<MasterResponse<Any>>() {}.type
                result.url = BASE_URL + DELETE_SELL_ITEM
                result.requestType = RequestType.POST
                return result
            }
            ITEM_DETAIL -> {
                result.responseType =
                    object : TypeToken<MasterResponse<ResponseItemDetail>>() {}.type
                result.url = BASE_URL + ITEM_DETAIL
                result.requestType = RequestType.POST
                return result
            }
            GET_MY_SELL_ITEM -> {
                result.responseType =
                    object : TypeToken<MasterResponse<ResponseMySellItem>>() {}.type
                result.url = BASE_URL + GET_MY_SELL_ITEM
                result.requestType = RequestType.POST
                return result
            }


            UPDATE_SELL_ITEM -> {
                result.responseType =
                    object : TypeToken<MasterResponse<Any>>() {}.type
                result.url = BASE_URL + UPDATE_SELL_ITEM
                result.requestType = RequestType.POST
                return result
            }
            MARK_SELL_ITEM_OCCUPIED -> {
                result.responseType =
                    object : TypeToken<MasterResponse<Any>>() {}.type
                result.url = BASE_URL + MARK_SELL_ITEM_OCCUPIED
                result.requestType = RequestType.POST
                return result
            }
            MARK_SELL_ITEM_DELIVERED -> {
                result.responseType =
                    object : TypeToken<MasterResponse<Any>>() {}.type
                result.url = BASE_URL + MARK_SELL_ITEM_DELIVERED
                result.requestType = RequestType.POST
                return result
            }
            ADVERTISEMENTS -> {
                result.responseType =
                    object : TypeToken<MasterResponse<AdvlistResponse>>() {}.type
                result.url = BASE_URL + ADVERTISEMENTS
                result.requestType = RequestType.POST
                return result
            }
            ADVERTISEMENT_COUNT -> {
                result.responseType =
                    object : TypeToken<MasterResponse<Boolean>>() {}.type
                result.url = BASE_URL + ADVERTISEMENT_COUNT
                result.requestType = RequestType.POST
                return result
            }
            BROWSE_SELL_ITEM -> {
                result.responseType =
                    object : TypeToken<MasterResponse<List<ResponseBrowseListItem>>>() {}.type
                result.url = BASE_URL + BROWSE_SELL_ITEM
                result.requestType = RequestType.POST
                return result
            }

            RateReviewDirectory -> {
                result.responseType =
                    object : TypeToken<MasterResponse<ResponseRateReviewDirectry>>() {}.type
                result.url = BASE_URL + RateReviewDirectory
                result.requestType = RequestType.POST
                return result
            }
            BusinessCategoryList -> {
                result.responseType =
                    object : TypeToken<MasterResponse<List<ResonseBusinessCategoryList>>>() {}.type
                result.url = BASE_URL + BusinessCategoryList
                result.requestType = RequestType.POST
                return result
            }

            ChangePassword -> {
                result.responseType =
                    object : TypeToken<MasterResponse<Boolean>>() {}.type
                result.url = BASE_URL + ChangePassword
                result.requestType = RequestType.POST
                return result
            }
            GET_CUSTOMER_CHECKLIST -> {
                result.responseType =
                    object : TypeToken<MasterResponse<List<ResponseCustomerChecklist>>>() {}.type
//                result.responseType = object : TypeToken<MasterResponse<MyCheckListResponse>>() {}.type
                result.url = BASE_URL + GET_CUSTOMER_CHECKLIST
                result.requestType = RequestType.POST
                return result
            }
            COURIER_COMPANIES -> {
                result.responseType =
                    object : TypeToken<MasterResponse<List<ResponseCourierCompaniesItem>>>() {}.type
                result.url = BASE_URL + COURIER_COMPANIES
                result.requestType = RequestType.POST
                return result
            }
            GETMYCHECKLIST -> {
                result.responseType =
                    object : TypeToken<MasterResponse<MyCheckListResponse>>() {}.type
                result.url = BASE_URL + GETMYCHECKLIST
                result.requestType = RequestType.POST
                return result
            }

            ContactUsQuery -> {
                result.responseType =
                    object : TypeToken<MasterResponse<Boolean>>() {}.type
                result.url = BASE_URL + ContactUsQuery
                result.requestType = RequestType.POST
                return result
            }
            GET_PROMOTIONAL_ADVERTISEMENT_POPUP -> {
                result.responseType =
                    object : TypeToken<MasterResponse<ResponseAdvertsementPopup>>() {}.type
                result.url = BASE_URL + GET_PROMOTIONAL_ADVERTISEMENT_POPUP
                result.requestType = RequestType.POST
                return result
            }
            GET_PROMOTIONAL_ADVERTISEMENT_STRIP -> {
                result.responseType =
                    object : TypeToken<MasterResponse<ResponseAdvertsementPopup>>() {}.type
                result.url = BASE_URL + GET_PROMOTIONAL_ADVERTISEMENT_STRIP
                result.requestType = RequestType.POST
                return result
            }
            SCAN_ADVERTISEMENT_QR_CODE -> {
                result.responseType =
                    object : TypeToken<MasterResponse<ResponseScanAdvertisementQRCode>>() {}.type
                result.url = BASE_URL + SCAN_ADVERTISEMENT_QR_CODE
                result.requestType = RequestType.POST
                return result
            }
            SOCIAL_LOGIN -> {
                result.responseType = object : TypeToken<MasterResponse<ResponseLogin>>() {}.type
                result.url = BASE_URL + SOCIAL_LOGIN
                result.requestType = RequestType.POST
                return result
            }

        }
        throw IllegalStateException("API is not registered")
    }
    //a
}
