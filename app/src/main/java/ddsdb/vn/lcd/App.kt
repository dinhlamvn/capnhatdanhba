package ddsdb.vn.lcd

import android.app.Application
import android.vn.lcd.di.AppModule
import ddsdb.vn.lcd.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class App : Application(), HasAndroidInjector {

  @Inject
  lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

  override fun onCreate() {
    super.onCreate()
    _root_ide_package_.ddsdb.vn.lcd.di.DaggerAppComponent
      .builder()
      .appModule(AppModule())
      .application(this)
      .build()
      .inject(this)
  }

  override fun androidInjector(): AndroidInjector<Any> {
    return dispatchingAndroidInjector
  }
}