package org.curryware.androidarchitecture.datamodels

// This is a simple data class.  It holds all of the fields that are presented on the SDKViewModel
// page.
data class SDKDataModel(

    val lastUpdated: String,
    val osVersion: Int,
    val appBuildVersion: String,
    val loggedInUser: String,
    val apiVersion: String,
    val customSettings: String
)
