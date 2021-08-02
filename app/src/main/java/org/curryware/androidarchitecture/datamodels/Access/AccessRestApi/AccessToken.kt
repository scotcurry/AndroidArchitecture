package org.curryware.androidarchitecture.datamodels.Access.AccessRestApi

data class AccessToken(

        val scope: String,
        val access_token: String,
        val token_type: String,
        val expires_in: Int,
        val refresh_token: String
)
