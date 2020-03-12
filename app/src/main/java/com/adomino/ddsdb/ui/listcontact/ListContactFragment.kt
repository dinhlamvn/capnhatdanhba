package com.adomino.ddsdb.ui.listcontact

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.adomino.ddsdb.R
import com.adomino.ddsdb.base.BaseFragment
import com.adomino.ddsdb.base.FragmentArgs
import com.adomino.ddsdb.common.Constant
import com.adomino.ddsdb.common.bindView
import com.adomino.ddsdb.di.scope.FragmentScope
import com.adomino.ddsdb.helper.resource.ResourceProvider
import com.adomino.ddsdb.recyclerview.XAdapter
import javax.inject.Inject

@FragmentScope
class ListContactFragment : BaseFragment() {

  companion object {
    fun create(args: FragmentArgs? = null): ListContactFragment {
      return ListContactFragment().apply {
        arguments = Bundle().apply {
          putParcelable(Constant.FRAGMENT_ARGS, args)
        }
      }
    }
  }

  @Inject
  lateinit var stringProvider: ResourceProvider.StringResourceProvider

  @Inject
  lateinit var factory: ViewModelProvider.Factory

  @Inject
  lateinit var viewModel: ListContactViewModel

  private val recyclerView: RecyclerView by bindView(R.id.recyclerView)

  private val adapter: XAdapter = XAdapter.create()

  override fun layout(): Int {
    return R.layout.fragment_list_contact
  }

  override fun onInitUI(view: View) {
    adapter.setViewHolderFactory { viewGroup: ViewGroup, viewType: Int ->
      if (viewType == 0) {
        ListContactViewHolder(
            LayoutInflater.from(requireContext())
                .inflate(R.layout.contact_item_view, viewGroup, false)
        )
      } else {
        EmptyResultViewHolder(
            LayoutInflater.from(requireContext())
                .inflate(R.layout.result_empty_notify_text, viewGroup, false)
        )
      }
    }
    recyclerView.adapter = adapter

    viewModel.contactList.observe(viewLifecycleOwner, Observer { list ->
      if (list.isNotEmpty()) {
        val models = list.map {
          ContactUiModel(contactUpdateInfo = it)
        }
        adapter.submitChange(models)
      } else {
        adapter.submitChange(listOf(ContactEmptyResult()))
      }
    })
  }
}