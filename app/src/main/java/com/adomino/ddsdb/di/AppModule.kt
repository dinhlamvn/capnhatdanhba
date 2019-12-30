package com.adomino.ddsdb.di

import android.app.Application
import android.content.Context
import android.vn.lcd.App
import android.vn.lcd.helper.contact.ContactTask
import android.vn.lcd.helper.contact.Contacts
import android.vn.lcd.helper.resource.DrawableProvider
import android.vn.lcd.helper.resource.ResourceProvider
import android.vn.lcd.helper.resource.StringProvider
import android.vn.lcd.ui.main.MainActivityModule
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [AppModule.Binding::class,
  MainActivityModule::class])
class AppModule {

  @Module
  interface Binding {
    @Binds
    fun bindApplication(app: App): Application
  }

  @Provides
  @Singleton
  fun provideContext(application: Application): Context {
    return application.applicationContext
  }

  @Provides
  @Singleton
  fun provideContactTask(context: Context): ContactTask {
    return Contacts(context.contentResolver)
  }

  @Provides
  @Singleton
  fun provideStringProvider(context: Context): ResourceProvider.StringResourceProvider {
    return StringProvider(context)
  }

  @Provides
  @Singleton
  fun provideDrawableProvider(context: Context): ResourceProvider.DrawableResourceProvider {
    return DrawableProvider(context)
  }
}