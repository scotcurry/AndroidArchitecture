package org.curryware.androidarchitecture.datamodels.Access.AccessRestApi

data class AccessUser(

    val Resources: List<Resource>,
    val itemsPerPage: Int,
    val schemas: List<String>,
    val startIndex: Int,
    val totalResults: Int
)