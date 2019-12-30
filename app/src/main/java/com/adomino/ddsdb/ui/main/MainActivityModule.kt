package com.adomino.ddsdb.ui.main

import android.vn.lcd.ui.listcontact.di.ListContactFragmentModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [ListContactFragmentModule::class])
abstract class MainActivityModule {

  @ContributesAndroidInjector(
    modules = [
      Provision::class
    ]
  )
  abstract fun activity(): MainActivity

  @Module
  interface Provision
}