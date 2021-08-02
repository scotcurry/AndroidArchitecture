package org.curryware.androidarchitecture.datamodels.Access.AccessRestApi

data class UrnScimSchemasExtensionWorkspace10(
    val distinguishedName: String,
    val domain: String,
    val externalUserDisabled: Boolean,
    val internalUserType: String,
    val userPrincipalName: String,
    val userStatus: String,
    val userStoreUuid: String
)