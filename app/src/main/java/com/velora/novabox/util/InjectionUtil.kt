package com.velora.novabox.util

import com.velora.novabox.data.AppsRepository
import com.velora.novabox.data.FakeLocationRepository
import com.velora.novabox.data.GmsRepository

import com.velora.novabox.view.apps.AppsFactory
import com.velora.novabox.view.fake.FakeLocationFactory
import com.velora.novabox.view.gms.GmsFactory
import com.velora.novabox.view.list.ListFactory


/**
 *
 * @Description:
 * @Author: wukaicheng
 * @CreateDate: 2021/4/29 22:38
 */
object InjectionUtil {

    private val appsRepository = AppsRepository()

    private val gmsRepository = GmsRepository()

    private val fakeLocationRepository = FakeLocationRepository()

    fun getAppsFactory() : AppsFactory {
        return AppsFactory(appsRepository)
    }

    fun getListFactory(): ListFactory {
        return ListFactory(appsRepository)
    }


    fun getGmsFactory():GmsFactory{
        return GmsFactory(gmsRepository)
    }

    fun getFakeLocationFactory():FakeLocationFactory{
        return FakeLocationFactory(fakeLocationRepository)
    }

    fun preloadInstalledApps() {
        appsRepository.previewInstallList()
    }
}
