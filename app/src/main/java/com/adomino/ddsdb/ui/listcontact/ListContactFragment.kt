package com.adomino.ddsdb.ui.listcontact

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import com.adomino.ddsdb.ui.listcontact.uimodel.ContactUpdateFailUiModel
import com.adomino.ddsdb.ui.listcontact.uimodel.ContactUpdateUIModel
import com.adomino.ddsdb.ui.listcontact.uimodel.EmptyResultUiModel
import com.adomino.ddsdb.ui.listcontact.viewholder.EmptyResultViewHolder
import com.adomino.ddsdb.ui.listcontact.viewholder.ListContactViewHolder
import com.adomino.ddsdb.ui.listcontact.viewholder.UpdateFailViewHolder
import com.adomino.ddsdb.ui.listcontact.viewholder.UpdateLoadingViewHolder
import com.adomino.ddsdb.util.UIHelper
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

  fun shareContact() {
    UIHelper.showToast(requireContext(), "OK", Toast.LENGTH_SHORT)
  }

  private val adapter: XAdapter = XAdapter.create { viewGroup: ViewGroup, viewType: Int ->
    when (viewType) {
      ContactUiModel.VIEW_TYPE -> {
        ListContactViewHolder.create(viewGroup)
      }
      EmptyResultUiModel.VIEW_TYPE -> {
        LayoutInflater.from(requireContext())
            .inflate(R.layout.result_empty_notify_text, viewGroup, false)
            .run {
              EmptyResultViewHolder(this)
            }
      }
      ContactUpdateUIModel.VIEW_TYPE -> {
        UpdateLoadingViewHolder.create(viewGroup)
      }
      ContactUpdateFailUiModel.VIEW_TYPE -> {
        UpdateFailViewHolder.create(viewGroup)
      }
      else -> {
        LoadingViewHolder.create(viewGroup)
      }
    }
  }

  private val loadingUiModel by lazy {
    LoadingUiModel("loading", requireContext().getString(R.string.loading_list_contact))
  }

  override fun layout(): Int {
    return R.layout.fragment_list_contact
  }

  override fun onInitUI(view: View) {
    swipeRefreshLayout.setOnRefreshListener {
      viewModel.loadContactList()
    }

    recyclerView.adapter = adapter
    recyclerView.addItemDecoration(
        DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
    )

    viewModel.loading.observe(viewLifecycleOwner, Observer { showLoading ->
      if (showLoading) {
        adapter.submitChange(loadingUiModel)
      }
    })

    viewModel.contactList.observe(viewLifecycleOwner, Observer { list ->
      swipeRefreshLayout.isRefreshing = false
      if (list.isNotEmpty()) {
        val models = list.map {
          ContactUiModel(
              id = "contact${it.contactInfo.id}",
              contactUpdateInfo = it
          )
        }
        adapter.submitChange(models)
      } else {
        adapter.submitChange(EmptyResultUiModel(id = "emptyResult"))
      }
    })

    viewModel.updateContact.observe(this, Observer { updateResult ->
      if (updateResult) {
        UIHelper.showToast(
            requireContext(),
            requireContext().getString(R.string.contact_update_head_number_success),
            Toast.LENGTH_SHORT
        )
        viewModel.loadContactList()
      } else {
        UIHelper.showToast(
            requireContext(), requireContext().getString(R.string.contact_update_head_number_fail),
            Toast.LENGTH_SHORT
        )
        adapter.submitChange(
            ContactUpdateFailUiModel(
                requireContext().getString(R.string.reload)
            ) {
              viewModel.loadContactList()
            }
        )
      }
    })

    viewModel.loadingUpdateContact.observe(this, Observer { showUpdateLoading ->
      if (showUpdateLoading) {
        adapter.submitChange(
            ContactUpdateUIModel(requireContext().getString(R.string.contact_updating_message))
        )
      }
    })
  }
}