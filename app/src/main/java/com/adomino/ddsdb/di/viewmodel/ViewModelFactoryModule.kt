package com.adomino.ddsdb.di.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.adomino.ddsdb.ui.listcontact.ListContactViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelFactoryModule {
  @Binds
  @IntoMap
  @ViewModelKey(ListContactViewModel::class)
  fun bindListContactViewModel(viewModel: ListContactViewModel): ViewModel

  @Binds
  fun bindViewModelFactory(factory: DaggerViewModelFactory): ViewModelProvider.Factory
}