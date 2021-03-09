package org.curryware.androidarchitecture.datamodels

import com.google.gson.annotations.SerializedName

data class UEMInfo(

    @SerializedName("ProductName")
    val productName: String,

    @SerializedName("ProductVersion")
    val productVersion: String,

    @SerializedName("Version")
    val version: Int,

    @SerializedName("CommitID")
    val commitId: String
)
