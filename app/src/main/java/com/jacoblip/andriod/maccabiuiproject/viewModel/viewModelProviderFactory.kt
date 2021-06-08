package com.jacoblip.andriod.maccabiuiproject.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jacoblip.andriod.maccabiuiproject.repository.MaccabiRepository

class viewModelProviderFactory(
    val repository: MaccabiRepository,
    val context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(repository,context) as T
    }
}