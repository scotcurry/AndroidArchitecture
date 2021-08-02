package org.curryware.androidarchitecture.ui.sdk

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.curryware.androidarchitecture.BuildConfig
import org.curryware.androidarchitecture.datamodels.Access.AccessRestApi.AccessToken
import org.curryware.androidarchitecture.datamodels.UEMInfo
import org.curryware.androidarchitecture.datamodels.Access.AccessRestApi.AccessUser
import org.curryware.androidarchitecture.repository.AccessRepository
import retrofit2.Response

// This constructor can use a SavedStateHandle object.
// The repository class is the layer of abstraction between the workers and this class.  The reason
// for this is in the case of getting the UEM information, we could get it from a database, or a
// REST API call.  We would call different Repository methods rather than coding them here.
class SDKViewModel(
    private val repository: AccessRepository
) : ViewModel() {

    private val TAG: String = "SDKViewModel"

    // These are the values that are shown in the SDKFragment
    // The concept here is that the private methods are used to call populate the data for
    // each of the LiveData elements.  This makes it easier to test those components.

    // Saved State Model allows for getting some information from the state of the fragment.
    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    private val _osVersion = MutableLiveData<String>().apply {
        value = BuildConfig.VERSION_NAME
    }
    val osVersionName: MutableLiveData<String> = _osVersion

    val uemInfoResponse: MutableLiveData<Response<UEMInfo>> = MutableLiveData()
    fun getUEMInfo() {

        viewModelScope.launch {
            val uemVersion = async { repository.getUEMInfo()}.await()
            uemInfoResponse.value = uemVersion
        }
    }

    val accessTokenInfo: MutableLiveData<Response<AccessToken>> = MutableLiveData()
    val viewUsers: MutableLiveData<List<AccessUser>> = MutableLiveData()

    // TODO:  This needs to be updated.  There is not value to getting the Access Token.  Every
    // call is made needs to get the token, so this should be changed to just
    fun getAccessToken() {

        // This is the key documentation to how to work with calls that you need the results for.
        //  You first do the launch as the line below shows.  Once in this block you can run the
        // calls with the async....await() function.  This means before you run the next line wait
        // for this line to finish.
        // https://developer.android.com/kotlin/coroutines/coroutines-adv#start
        viewModelScope.launch {

            val accessTokenPayload = async { repository.getAccessToken() }.await()
            // var userList: List<ViewUser>? = null

            if (accessTokenPayload.isSuccessful) {
                val accessToken = accessTokenPayload.body()?.access_token
                Log.i(TAG, "Access Token: $accessToken")
                // val headerMap = HashMap<String, String>()
                // headerMap["Authorization"] = "HZN $accessToken"
                // headerMap["Content-Type"] = "application/json"
                // headerMap["Accept"] = "application/json"
                // for((key, value) in headerMap) {
                //    Log.i(TAG, "Key: $key - Value: $value")
                // }
                val getAccessUsers = async { repository.getAccessUsers(accessToken) }.await()
//                if (getAccessUsers.isSuccessful) {
//                    val allUsers = getAccessUsers.body()?.Resources
//                    val viewUsersToAdd = mutableListOf<AccessUser>()
//                    Log.i(TAG, "Got Some Users")
//                    if (allUsers != null) {
//                        for (currentUser: Resource in allUsers) {
//                            val userName = currentUser.userName
//                            val title = currentUser.title
//                            val emailAddress = currentUser.emails[0].value
//                            val firstName = currentUser.name.givenName
//                            val lastName = currentUser.name.familyName
//                            val fullName = "$firstName $lastName"
//                            val viewUser = AccessUser(userName, fullName, emailAddress, title)
//                            viewUsersToAdd.add(viewUser)
//                        }
//                        // userList = viewUsersToAdd
//                    }
//                } else {
//                    val returnCode = getAccessUsers.code().toString()
//                    val debug = getAccessUsers.message()
//                    val more_debug = getAccessUsers.errorBody()
//                    Log.e(TAG, "Had an Issue - Return: $returnCode, Message: $debug, Extended Error: $more_debug")
//                }

            }
            // viewUsers.value = userList
            accessTokenInfo.value = accessTokenPayload
        }
    }
}