package com.adomino.ddsdb.ui.listcontact.di

import android.vn.lcd.ui.listcontact.ListContactFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ListContactFragmentModule {

  @ContributesAndroidInjector(
    modules = [Provision::class]
  )
  @ListContactScope
  fun inject(): ListContactFragment

  @Module
  interface Provision
}