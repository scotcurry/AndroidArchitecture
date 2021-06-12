package org.curryware.androidarchitecture.datamodels.Access

data class Resource(
    val active: Boolean,
    val emails: List<Email>,
    val externalId: String,
    val groups: List<Group>,
    val id: String,
    val meta: Meta,
    val name: Name,
    val phoneNumbers: List<PhoneNumber>,
    val roles: List<Role>,
    val title: String,
    val enterprise10: UrnScimSchemasExtensionEnterprise10,
    val workspace10: UrnScimSchemasExtensionEnterprise10,
    val mfaWorkspace10: UrnScimSchemasExtensionWorkspaceMfa10,
    val tenantInfo: UrnScimSchemasExtensionWorkspaceTenantAwCurrywareEx110,
    val userName: String
)