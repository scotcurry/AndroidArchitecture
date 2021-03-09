package org.curryware.androidarchitecture.ui.sdk

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import org.curryware.androidarchitecture.R
import org.curryware.androidarchitecture.repository.Repository

class SDKFragment : Fragment() {

    private val TAG: String = "SDKFragment"
    // This is the new way of handling viewModels.  There will be examples that call the
    // ViewModelProvider like below.  Not the way to go moving forward.
    // private lateinit var sdkViewModel: SDKViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // sdkViewModel = ViewModelProvider(this).get(SDKViewModel::class.java)
        val rootSdkFragment = inflater.inflate(R.layout.fragment_sdk, container, false)
        val textView: TextView = rootSdkFragment.findViewById(R.id.text_sdk)


        // Still need to research the exact reason I'm passing this repository around.  I think it
        // has something to do with dependencies, but need to figure this out.
        val repository = Repository()
        val viewModelFactory = SDKViewModelFactory(repository)
        val sdkViewModel = ViewModelProvider(this, viewModelFactory).get(SDKViewModel::class.java)

        // Now that we actually have a ViewModel, we need to do som "stuff" to populate the UI.
        sdkViewModel.getUEMInfo()

        sdkViewModel.text.observe(viewLifecycleOwner, {
            textView.text = it
        })

        sdkViewModel.uemInfoResponse.observe(viewLifecycleOwner, { response ->
            if (response.isSuccessful) {
                Log.d(TAG, "Product Version: "+ response.body()?.productVersion!!)
                Log.d(TAG, "Product Name: " + response.body()?.productName!!)
            } else {
                Log.e(TAG, response.message())
            }
        })
        return rootSdkFragment
    }
}