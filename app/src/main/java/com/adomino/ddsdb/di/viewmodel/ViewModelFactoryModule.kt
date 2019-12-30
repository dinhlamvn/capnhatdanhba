package com.adomino.ddsdb.di.viewmodel

import android.vn.lcd.ui.listcontact.ListContactViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelFactoryModule {
  @Binds
  @IntoMap
  @_root_ide_package_.com.adomino.ddsdb.di.viewmodel.ViewModelKey(ListContactViewModel::class)
  fun bindListContactViewModel(viewModel: ListContactViewModel): ViewModel

  @Binds
  fun bindViewModelFactory(factory: DaggerViewModelFactory): ViewModelProvider.Factory
}