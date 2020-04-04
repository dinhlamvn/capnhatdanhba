package com.adomino.ddsdb.ui.listcontact.di

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.adomino.ddsdb.di.scope.FragmentScope
import com.adomino.ddsdb.di.viewmodel.DaggerViewModelFactory
import com.adomino.ddsdb.ui.listcontact.ListContactFragment
import com.adomino.ddsdb.ui.listcontact.ListContactViewModel
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
interface ListContactFragmentModule {

  @ContributesAndroidInjector(
      modules = [Binding::class, Provision::class]
  )
  @FragmentScope
  fun inject(): ListContactFragment

  @Module
  interface Binding {
    fun bindFactory(factory: DaggerViewModelFactory): ViewModelProvider.Factory
  }

  @Module
  object Provision {
  }
}