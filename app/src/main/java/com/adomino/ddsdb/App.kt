package com.adomino.ddsdb

import android.app.Application
import com.adomino.ddsdb.di.AppComponent
import com.adomino.ddsdb.di.AppModule
import com.adomino.ddsdb.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class App : Application(), HasAndroidInjector {

  @Inject
  lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

  private lateinit var appComponent: AppComponent

  override fun onCreate() {
    super.onCreate()
    appComponent = DaggerAppComponent
        .builder()
        .appModule(AppModule())
        .application(this)
        .build()
    appComponent.inject(this)
  }

  fun appComponent(): AppComponent {
    return this.appComponent
  }

  override fun androidInjector(): AndroidInjector<Any> {
    return dispatchingAndroidInjector
  }
}