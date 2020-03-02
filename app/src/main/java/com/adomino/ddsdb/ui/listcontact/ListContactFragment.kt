package com.adomino.ddsdb.ui.listcontact

import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adomino.ddsdb.R
import com.adomino.ddsdb.base.BaseFragment
import com.adomino.ddsdb.di.scope.FragmentScope
import com.adomino.ddsdb.helper.resource.ResourceProvider
import com.adomino.ddsdb.recyclerview.XAdapter
import javax.inject.Inject

@FragmentScope
class ListContactFragment : BaseFragment() {

  @Inject
  lateinit var stringProvider: ResourceProvider.StringResourceProvider

  @Inject
  lateinit var factory: ViewModelProvider.Factory

  @Inject
  lateinit var viewModel: ListContactViewModel

  override fun layout(): Int {
    return R.layout.fragment_list_contact
  }

  override fun onInitUI(view: View) {
    val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
    recyclerView.layoutManager = LinearLayoutManager(requireContext())
    val adapter = XAdapter.create()
        .setViewHolderFactory { viewGroup, _ ->
          ListContactViewHolder(
              LayoutInflater.from(requireContext())
                  .inflate(android.R.layout.simple_list_item_1, viewGroup, false)
          )
        }
    recyclerView.adapter = adapter

    for (i in 0..10) {
      adapter.addModel(ContactUiModel(id = i, name = "Dinh $i"))
    }
  }
}