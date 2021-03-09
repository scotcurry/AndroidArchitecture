package org.curryware.androidarchitecture.ui.sdk

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import org.curryware.androidarchitecture.datamodels.UEMInfo
import org.curryware.androidarchitecture.repository.Repository
import org.curryware.androidarchitecture.workers.RetroFitInstance
import retrofit2.Response

// This constructor can use a SavedStateHandle object.
// The repository class is the layer of abstraction between the workers and this class.  The reason
// for this is in the case of getting the UEM information, we could get it from a database, or a
// REST API call.  We would call different Repository methods rather than coding them here.
class SDKViewModel(
    private val repository: Repository
) : ViewModel() {

    // Saved State Model allows for getting some information from the state of the fragment.
    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }

    private val _osVersion = MutableLiveData<Int>().apply {

    }
    val text: LiveData<String> = _text


    val uemInfoResponse: MutableLiveData<Response<UEMInfo>> = MutableLiveData()
    fun getUEMInfo() {

        viewModelScope.launch {
            val uemVersion = repository.getUEMInfo()
            uemInfoResponse.value = uemVersion
        }
    }
}