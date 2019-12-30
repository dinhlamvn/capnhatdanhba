package com.adomino.ddsdb.di

import android.app.Application
import android.content.Context
import com.adomino.ddsdb.App
import com.adomino.ddsdb.helper.contact.ContactTask
import com.adomino.ddsdb.helper.contact.Contacts
import com.adomino.ddsdb.helper.resource.DrawableProvider
import com.adomino.ddsdb.helper.resource.ResourceProvider
import com.adomino.ddsdb.helper.resource.StringProvider
import com.adomino.ddsdb.ui.main.MainActivityModule
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