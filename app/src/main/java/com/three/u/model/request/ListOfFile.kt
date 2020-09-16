package com.three.u.model.request

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ListOfFile : Serializable {

    constructor(fileURL : String, fileName: String){
        this.fileURL = fileURL
        this.fileName = fileName
    }

    @SerializedName("fileURL")
    var fileURL: String? = ""
    @SerializedName("fileName")
    var fileName: String? = ""
}