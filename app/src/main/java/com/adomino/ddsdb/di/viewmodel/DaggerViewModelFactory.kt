package com.adomino.ddsdb.di.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class DaggerViewModelFactory @Inject constructor(
  private val viewModelMaps: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {

  @Suppress("UNCHECKED_CAST")
  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    val creator = viewModelMaps[modelClass] ?: viewModelMaps.asIterable().firstOrNull { vm ->
      modelClass.isAssignableFrom(vm.key)
    }?.value ?: throw IllegalArgumentException("unknown view model class $modelClass")
    return try {
      creator.get() as T
    } catch (e: Exception) {
      throw RuntimeException("can not create view model class $e")
    }
  }
}