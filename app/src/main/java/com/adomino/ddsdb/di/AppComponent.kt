package com.adomino.ddsdb.di

import android.vn.lcd.App
import android.vn.lcd.di.viewmodel.ViewModelFactoryModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Component(
  modules = [
    AndroidInjectionModule::class,
    AppModule::class,
    ViewModelFactoryModule::class
  ]
)
@Singleton
interface AppComponent {
  fun inject(app: App)

  @Component.Builder
  interface Builder {
    fun build(): AppComponent

    @BindsInstance
    fun appModule(appModule: AppModule): Builder

    @BindsInstance
    fun application(app: App): Builder
  }
}