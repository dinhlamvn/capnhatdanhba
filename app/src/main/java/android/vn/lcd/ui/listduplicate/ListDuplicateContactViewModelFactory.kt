package android.vn.lcd.ui.listduplicate

import android.content.ContentResolver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ListDuplicateContactViewModelFactory(private val resolver: ContentResolver) : ViewModelProvider.Factory {

  @Suppress("UNCHECKED_CAST")
  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(ListDuplicateContactViewModel::class.java)) {
      return ListDuplicateContactViewModel(resolver) as T
    }
    throw IllegalArgumentException("View model not found.")
  }
}