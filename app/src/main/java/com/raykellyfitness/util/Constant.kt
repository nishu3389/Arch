package com.raykellyfitness.util

object Constant {

    //const val AMZ_BASE_URL = "https://hookup-user-profile.s3.ap-south-1.amazonaws.com/"

    const val POST_TYPE_TIPS = "Tips & Tricks"
    const val POST_TYPE_MEAL = "Meal"
    const val POST_TYPE_EXERCISE = "Exercise"
    const val POST_TYPE_MOTIVATION = "Daily Motivation"
    const val POST_TYPE_BLOG = "blog"

    var SKU = "product_subscription"
    val NOTIFICATION_TYPE_SUBSCRIPTION: String? = "subscription"
    val NOTIFICATION_TYPE_POST: String? = "post"

    val ACTION_NAVIGATION: String? = "navigation"
    val ACTION_ORDER_PLACED: String? = "OrderPlaced"
    val ACTION_ORDER_CANCELLED: String? = "OrderCancelled"
    val ACTION_ITEM_DELIVERED: String? = "ItemDelivered"
    val ACTION_CHECKLIST_UPDATE: String? = "ChecklistUpdate"
    val ACTION_BOX_ALLOTMENT: String? = "BoxAllotment"
    val ACTION_CONCIERGE_PROCESSED: String? = "ConceirgeProcessed"
    val ACTION_BUDGET: String? = "ACTION_BUDGET"

    ///////////WEIGHT RANGE///////////////////////
    val WEIGHT_RANGE_MIN = 1
    val WEIGHT_RANGE_MAX = 500

    val HEIGHT_RANGE_MIN = 1
    val HEIGHT_RANGE_MAX = 250
    ///////////WEIGHT RANGE///////////////////////

    ///////////BP RANGE///////////////////////
    val DIASTOLIC_BP_RANGE_MIN = 1
    val DIASTOLIC_BP_RANGE_MAX = 250

    val SYSTOLIC_BP_RANGE_MIN = 1
    val SYSTOLIC_BP_RANGE_MAX = 250
    ///////////BP RANGE///////////////////////

    ///////////BS RANGE///////////////////////
    val FASTING_SUGAR_RANGE_MIN = 1
    val FASTING_SUGAR_RANGE_MAX = 30

    val POST_FASTING_SUGAR_RANGE_MIN = 1
    val POST_FASTING_SUGAR_RANGE_MAX = 30
    ///////////BS RANGE///////////////////////



    val ENTER_WEIGHT_RANGE: String? =
        "Please enter your weight between $WEIGHT_RANGE_MIN to $WEIGHT_RANGE_MAX KG"
    val ENTER_HEIGHT_RANGE: String? =
        "Please enter your height between $HEIGHT_RANGE_MIN to $HEIGHT_RANGE_MAX CM."


    val ENTER_DIASTOLIC_BP_RANGE: String? =
        "Please enter your Diastolic blood pressure level between $DIASTOLIC_BP_RANGE_MIN to " +
                "$DIASTOLIC_BP_RANGE_MAX mmHg."
    val ENTER_SYSTOLIC_BP_RANGE: String? =
        "Please enter your Systolic blood pressure between $SYSTOLIC_BP_RANGE_MIN to " +
                "$SYSTOLIC_BP_RANGE_MAX mmHg."


    val ENTER_FASTING_SUGAR_RANGE: String? =
        "Please enter your Fasting blood sugar level between $FASTING_SUGAR_RANGE_MIN to " +
                "$FASTING_SUGAR_RANGE_MAX mmol/L."
    val ENTER_POST_FASTING_SUGAR_RANGE: String? =
        "Please enter your Post Fasting blood sugar level between $POST_FASTING_SUGAR_RANGE_MIN to " +
                "$POST_FASTING_SUGAR_RANGE_MAX mmol/L."



    val ENTER_WEIGHT: String? = "Please enter your weight."
    val ENTER_HEIGHT: String? = "Please enter your height."
    val ENTER_DIASTOLIC_BP: String? = "Please enter your Diastolic blood pressure."
    val ENTER_SYSTOLIC_BP: String? = "Please enter your Systolic blood pressure."
    val ENTER_FASTING_SUGAR: String? = "Please enter your Fasting blood sugar level."
    val ENTER_POST_FASTING_SUGAR: String? = "Please enter your Post Fasting blood sugar level."

    val IMAGE: String? = "image"
    val VIDEO: String? = "video"
    val URL: String? = "url"
    val NO_RECORD_AVAILABLE: String? = "No records available"

    val MAIN_BOARD_ACTIVITY: String? = "com.raykellyfitness.ui.activity.MainBoardActivity"

    const val ITEM_PENDING = 1
    const val ITEM_ORDERED = 2
    const val ITEM_DELIVERED = 3
    const val ITEM_REMOVED = 4
    const val ITEM_OCCUPIED = 5

    const val ITEM_FOR_SELL = 1
    const val ITEM_FOR_GIVEAWAY = 2
    const val ITEM_FOR_DONATE = 3

    //    const val STRIPE_PUBLISHABLE_KEY: String = "pk_test_51H5nNGHagS9NaLqefyzH9pFJNFgmfVg7tHkL6XwFFJixCtOw5eRFe4nzl5xrwOcSe9h8Pk0jubSGcwnq4iEOAAZ100wcpwY6pe"
    const val STRIPE_PUBLISHABLE_KEY: String =
        "pk_test_51H8HZVHkhGxsTLKhGcgaEAlHEVIEbdkjFygPVQ5iNO8JG3Q75W4zFVFBX1l1Pv2N4swytx57z2YFRqtlcrujUsXc00NfJwPcWL"
    const val AMZ_BASE_URL = "https://hookup-post-images.s3.ap-south-1.amazonaws.com/"

    const val CODE_DIRECTORY: Int = 208
    const val CODE_PACKAGING: Int = 209
    const val CODE_UTILITIES: Int = 210

    const val PROFILE_NORMAL = 1
    const val PROFILE_SALES = 2
    const val KEY_PROFILE_TYPE = "key_profile_type"

    const val BEAN = "bean"
    const val TYPE = "type"

    const val REGISTER = "REGISTER"
    const val FORGOT_PASSWORD = "FORGOT_PASSWORD"

    const val DEVICE_TOKEN_FCM = "dummy"
    const val DUMMY_EMAIL = "test1@gmail.com"
    const val DUMMY_PASSWORD = "12345678"

    const val PHONE = ""
    const val COUNTRYCODE = ""

    const val REQC_PICK_IMAGE = 1112
    const val REQ_GOOGLE_SIGN_IN = 1113
    const val REQC_SHOW_IMAGE_CAROUSEL = 1115

    const val DISPLAY_DATE_FORMAT_1 = "dd-MM-yyyy hh:mm a"
    const val DISPLAY_DATE_FORMAT_2 = "dd-MM-yyyy"
    const val API_DATE_FORMAT_1 = "dd-MM-yyyy HH:mm a"
    const val API_DATE_FORMAT_2 = "dd MMM yyyy hh:mm:ss a"
    const val API_DATE_FORMAT_3 = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"


}


