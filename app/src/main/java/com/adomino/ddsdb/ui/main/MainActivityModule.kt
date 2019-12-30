package com.adomino.ddsdb.ui.main

import com.adomino.ddsdb.ui.listcontact.di.ListContactFragmentModule
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