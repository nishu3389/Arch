package com.raykellyfitness.networking

import java.lang.reflect.Type

class ApiRequestType {
    lateinit var responseType : Type
    lateinit var url : String
    lateinit var requestType : RequestType
}
