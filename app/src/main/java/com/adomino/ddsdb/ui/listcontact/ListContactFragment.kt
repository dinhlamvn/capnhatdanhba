package com.adomino.ddsdb.ui.listcontact

import android.Manifest
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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
import com.adomino.ddsdb.helper.router.Router
import com.adomino.ddsdb.recyclerview.XAdapter
import com.adomino.ddsdb.recyclerview.XModel
import com.adomino.ddsdb.recyclerview.XViewHolder
import com.adomino.ddsdb.ui.listcontact.uimodel.ContactUiModel
import com.adomino.ddsdb.ui.listcontact.uimodel.ContactUpdateFailUiModel
import com.adomino.ddsdb.ui.listcontact.uimodel.ContactUpdateUIModel
import com.adomino.ddsdb.ui.listcontact.uimodel.EmptyResultUiModel
import com.adomino.ddsdb.ui.listcontact.viewholder.ContactItemViewHolder
import com.adomino.ddsdb.ui.listcontact.viewholder.EmptyResultViewHolder
import com.adomino.ddsdb.ui.listcontact.viewholder.UpdateFailViewHolder
import com.adomino.ddsdb.ui.listcontact.viewholder.UpdateLoadingViewHolder
import com.adomino.ddsdb.util.UIHelper
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import javax.inject.Inject
import kotlin.system.exitProcess

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

  @Inject
  lateinit var router: Router

  private val recyclerView: RecyclerView by bindView(R.id.recyclerView)

  private val swipeRefreshLayout: SwipeRefreshLayout by bindView(R.id.swipeRefreshLayout)

  fun shareContact() {
    UIHelper.showToast(requireContext(), "OK", Toast.LENGTH_SHORT)
  }

  private val adapter: XAdapter = XAdapter.create { viewGroup: ViewGroup, model: XModel ->
    when (model) {
      is ContactUiModel -> {
        XViewHolder.create(ContactItemViewHolder::class.java, viewGroup, R.layout.contact_item_view)
      }
      is EmptyResultUiModel -> {
        XViewHolder.create(
            EmptyResultViewHolder::class.java, viewGroup, R.layout.result_empty_notify_text
        )
      }
      is ContactUpdateUIModel -> {
        XViewHolder.create(UpdateLoadingViewHolder::class.java, viewGroup, R.layout.loading)
      }
      is ContactUpdateFailUiModel -> {
        XViewHolder.create(
            UpdateFailViewHolder::class.java, viewGroup, R.layout.contact_update_fail_view
        )
      }
      else -> {
        XViewHolder.create(LoadingViewHolder::class.java, viewGroup, R.layout.loading)
      }
    }
  }

  private val loadingUiModel by lazy {
    LoadingUiModel("loading", requireContext().getString(R.string.loading_list_contact))
  }

  override fun onStart() {
    super.onStart()
    Dexter.withActivity(requireActivity())
        .withPermissions(
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS,
            Manifest.permission.CALL_PHONE
        )
        .withListener(object : MultiplePermissionsListener {
          override fun onPermissionsChecked(report: MultiplePermissionsReport) {
            if (report.areAllPermissionsGranted()) {
              viewModel.syncContactsToDatabase()
            } else {
              val alertDialog = AlertDialog.Builder(requireContext())
                  .setTitle("Permission required.")
                  .setMessage("Please allow app's permission to access your contact.")
                  .setPositiveButton("OK") { _, _ ->

                  }
                  .setNegativeButton("Cancel") { _, _ ->
                    exitProcess(-1)
                  }
                  .create()
              alertDialog.show()
            }
          }

          override fun onPermissionRationaleShouldBeShown(
            permissions: List<PermissionRequest>,
            token: PermissionToken
          ) {
            token.continuePermissionRequest()
          }
        })
        .check()
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
              contactUpdateInfo = it,
              actionCall = View.OnClickListener { _ ->
                router.startCall(requireContext(), it.contactInfo.phoneNumber)
              },
              actionItemClick = View.OnClickListener {
                UIHelper.showToast(requireContext(), "On Item Click")
              }
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