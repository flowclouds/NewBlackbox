package com.tm.box.sample.view.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tm.box.sample.data.AppsRepository


@Suppress("UNCHECKED_CAST")
class ListFactory(private val appsRepository: AppsRepository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ListViewModel(appsRepository) as T
    }
}