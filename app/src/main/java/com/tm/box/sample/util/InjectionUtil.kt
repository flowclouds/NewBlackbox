package com.tm.box.sample.util

import com.tm.box.sample.data.AppsRepository
import com.tm.box.sample.view.apps.AppsFactory
import com.tm.box.sample.view.list.ListFactory

object InjectionUtil {

    private val appsRepository = AppsRepository()

    fun getAppsFactory() : AppsFactory {
        return AppsFactory(appsRepository)
    }

    fun getListFactory(): ListFactory {
        return ListFactory(appsRepository)
    }
}