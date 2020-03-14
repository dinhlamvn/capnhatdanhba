package com.adomino.ddsdb.ui.listcontact

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.adomino.ddsdb.R
import com.adomino.ddsdb.base.BaseFragment
import com.adomino.ddsdb.base.FragmentArgs
import com.adomino.ddsdb.common.Constant
import com.adomino.ddsdb.common.bindView
import com.adomino.ddsdb.common.model.LoadingUiModel
import com.adomino.ddsdb.common.viewholder.LoadingViewHolder
import com.adomino.ddsdb.di.scope.FragmentScope
import com.adomino.ddsdb.helper.resource.ResourceProvider
import com.adomino.ddsdb.recyclerview.XAdapter
import com.adomino.ddsdb.ui.listcontact.uimodel.ContactUiModel
import com.adomino.ddsdb.ui.listcontact.viewholder.EmptyResultViewHolder
import com.adomino.ddsdb.ui.listcontact.viewholder.ListContactViewHolder
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

  private val swipeRefreshLayout: SwipeRefreshLayout by bindView(R.id.swipeRefreshLayout)

  private val adapter: XAdapter = XAdapter.create { viewGroup: ViewGroup, viewType: Int ->
    when (viewType) {
      0 -> {
        ListContactViewHolder.create(viewGroup)
      }
      1 -> {
        LayoutInflater.from(requireContext())
            .inflate(R.layout.result_empty_notify_text, viewGroup, false)
            .run {
              EmptyResultViewHolder(this)
            }
      }
      else -> {
        LoadingViewHolder.create(viewGroup)
      }
    }
  }

  private val loadingUiModel by lazy {
    LoadingUiModel(1111, requireContext().getString(R.string.loading_list_contact))
  }

  override fun layout(): Int {
    return R.layout.fragment_list_contact
  }

  override fun onInitUI(view: View) {
    swipeRefreshLayout.setOnRefreshListener {
      adapter.submitChange(loadingUiModel)
      viewModel.loadContactList()
    }

    recyclerView.adapter = adapter
    recyclerView.addItemDecoration(
        DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
    )
    adapter.submitChange(loadingUiModel)

    viewModel.contactList.observe(viewLifecycleOwner, Observer { list ->
      swipeRefreshLayout.isRefreshing = false
      if (list.isNotEmpty()) {
        val models = list.map {
          ContactUiModel(
              id = it.contactInfo.id,
              contactUpdateInfo = it
          )
        }
        adapter.submitChange(models)
      } else {
        adapter.submitChange(loadingUiModel)
      }
    })
  }
}