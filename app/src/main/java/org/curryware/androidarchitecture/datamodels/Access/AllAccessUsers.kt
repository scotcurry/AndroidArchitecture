package org.curryware.androidarchitecture.datamodels.Access

import org.curryware.androidarchitecture.datamodels.Access.AccessRestApi.AccessUser

data class AllAccessUsers(

    val allUsers: List<AccessUser>
)