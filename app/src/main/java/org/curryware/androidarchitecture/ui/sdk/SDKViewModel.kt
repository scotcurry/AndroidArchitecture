package org.curryware.androidarchitecture.ui.sdk

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.curryware.androidarchitecture.BuildConfig
import org.curryware.androidarchitecture.datamodels.AccessToken
import org.curryware.androidarchitecture.datamodels.UEMInfo
import org.curryware.androidarchitecture.repository.Repository
import retrofit2.Response
import java.util.*
import kotlin.collections.HashMap

// This constructor can use a SavedStateHandle object.
// The repository class is the layer of abstraction between the workers and this class.  The reason
// for this is in the case of getting the UEM information, we could get it from a database, or a
// REST API call.  We would call different Repository methods rather than coding them here.
class SDKViewModel(
    private val repository: Repository
) : ViewModel() {

    private val TAG: String = "SDKViewModel"

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
    fun getAccessToken() {

        val clientID: String = "Zero_Day_Token"
        val clientToken: String = "CTOpIVfg7sMJiNtSzdMZTHDMXXNGluv8mTo0NMgoF6PGXtqR"
        val stringToConvert: String = ("$clientID:$clientToken")

        viewModelScope.launch {

            val getTokenHeaderMap = HashMap<String, String>()
            getTokenHeaderMap["Authorization"] = buildBasicAccessHeader()
            getTokenHeaderMap["Content-Type"] = "application/json"
            getTokenHeaderMap["Accept"] = "application/json"
            val access_token_payload = async { repository.getAccessToken(getTokenHeaderMap) }.await()

            if (access_token_payload.isSuccessful) {
                val accessToken = access_token_payload.body()?.access_token!!
                Log.i(TAG, "Access Token: $accessToken")
                val headerMap = HashMap<String, String>()
                headerMap["Authorization"] = "HZN $accessToken"
                headerMap["Content-Type"] = "application/json"
                headerMap["Accept"] = "application/json"
                for((key, value) in headerMap) {
                    Log.i(TAG, "Key: $key - Value: $value")
                }
                val getAccessUsers = async { repository.getAccessUsers(headerMap) }.await()
                if (getAccessUsers.isSuccessful) {
                    Log.i(TAG, "Got Some Users")
                } else {
                    val returnCode = getAccessUsers.code().toString()
                    val debug = getAccessUsers.message()
                    val more_debug = getAccessUsers.errorBody()
                    Log.e(TAG, "Had an Issue - Return: $returnCode, Message: $debug")
                }

            }
            accessTokenInfo.value = access_token_payload
        }
    }

    private fun buildBasicAccessHeader(): String {

        val clientID = "Zero_Day_Token"
        val clientToken = "CTOpIVfg7sMJiNtSzdMZTHDMXXNGluv8mTo0NMgoF6PGXtqR"

        val stringToConvert = "$clientID:$clientToken"
        val base64String: String = Base64.getEncoder().encodeToString(stringToConvert.toByteArray())
        val authString = "Basic $base64String"
        Log.d(TAG, authString)
        return authString
    }
}