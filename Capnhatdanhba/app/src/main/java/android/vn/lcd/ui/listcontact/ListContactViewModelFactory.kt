package android.vn.lcd.ui.listcontact

import android.content.ContentResolver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class ListContactViewModelFactory(private val resolver: ContentResolver): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListContactViewModel::class.java)) {
            return ListContactViewModel(resolver) as T
        }
        throw IllegalArgumentException("View model not found.")
    }
}