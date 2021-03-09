package org.curryware.androidarchitecture.ui.sdk

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.curryware.androidarchitecture.repository.Repository

// The ViewModelFactory is required because we are going to pass the repository when we try to
// build the ViewModel.  There is no way to initialize the ViewModel with parameters if you just
// use the ViewModelProviders.of syntax.
class SDKViewModelFactory(private val repository: Repository)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SDKViewModel(repository) as T
    }
}