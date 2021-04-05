package org.curryware.androidarchitecture.ui.sdk

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.crittercism.app.Crittercism
import org.curryware.androidarchitecture.R
import org.curryware.androidarchitecture.repository.Repository

class SDKFragment : Fragment() {

    private val TAG: String = "SDKFragment"
    // This is the new way of handling viewModels.  There will be examples that call the
    // ViewModelProvider like below.  Not the way to go moving forward.
    private lateinit var repository: Repository
    private lateinit var sdkViewModel: SDKViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val rootSdkFragment = inflater.inflate(R.layout.fragment_sdk, container, false)
        val textView: TextView = rootSdkFragment.findViewById(R.id.text_sdk)
        val textOsVersion: TextView = rootSdkFragment.findViewById(R.id.textView_osVersionLabel)
        val buttonRetrofit = rootSdkFragment.findViewById<Button>(R.id.button_config_retrofit)

        buttonRetrofit.setOnClickListener {
            makeRetrofitCall()
        }

        repository = Repository()
        val viewModelFactory = SDKViewModelFactory(repository)
        sdkViewModel = ViewModelProvider(this, viewModelFactory)
                .get(SDKViewModel::class.java)

        val osObserver = Observer<String> { newOSVersion ->
            textOsVersion.text = newOSVersion
        }
        sdkViewModel.osVersionName.observe(viewLifecycleOwner, osObserver)

        Crittercism.leaveBreadcrumb("Created SDK Fragment")
        return rootSdkFragment
    }

    private fun makeRetrofitCall() {

        // val repository = Repository()
        // val viewModelFactory = SDKViewModelFactory(repository)
        // val sdkViewModel = ViewModelProvider(this, viewModelFactory).get(SDKViewModel::class.java)
        // Now that we actually have a ViewModel, we need to do som "stuff" to populate the UI.
        // sdkViewModel.getUEMInfo()
        Log.i(TAG, "Getting Ready To Make Network Calls")
        Crittercism.leaveBreadcrumb("Hitting Init Retrofit Button")

        sdkViewModel.uemInfoResponse.observe(viewLifecycleOwner, { response ->
            if (response.isSuccessful) {
                Log.d(TAG, "Product Version: "+ response.body()?.productVersion!!)
                Log.d(TAG, "Product Name: " + response.body()?.productName!!)
            } else {
                Log.e(TAG, response.message())
            }
        })

        sdkViewModel.getAccessToken()
        sdkViewModel.accessTokenInfo.observe(viewLifecycleOwner, { response ->
            if (response.isSuccessful) {
                Log.d(TAG, "Scope: " + response.body()?.scope!!)
            } else {
                Log.e(TAG, response.message())
            }
        })
    }
}