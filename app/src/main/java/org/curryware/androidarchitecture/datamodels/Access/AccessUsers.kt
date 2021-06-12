package org.curryware.androidarchitecture.datamodels.Access

data class AccessUsers(
    val Resources: List<Resource>,
    val itemsPerPage: Int,
    val schemas: List<String>,
    val startIndex: Int,
    val totalResults: Int
)