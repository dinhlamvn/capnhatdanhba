package com.adomino.ddsdb.ui.listcontact

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adomino.ddsdb.R
import com.adomino.ddsdb.base.BaseFragment
import com.adomino.ddsdb.helper.resource.ResourceProvider
import com.adomino.ddsdb.ui.listcontact.di.ListContactScope
import java.util.*
import javax.inject.Inject

@ListContactScope
class ListContactFragment : BaseFragment() {

  lateinit var listContactViewModel: ListContactViewModel

  @Inject
  lateinit var stringProvider: ResourceProvider.StringResourceProvider

  @Inject
  lateinit var factory: ViewModelProvider.Factory

  private val contactListAdapter: ListContactAdapter by lazy(LazyThreadSafetyMode.NONE) {
    ListContactAdapter()
  }

  override fun getLayoutResource(): Int {
    return R.layout.fragment_list_contact
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    listContactViewModel = ViewModelProviders.of(this, factory).get(ListContactViewModel::class.java)

    val rvContactList = view.findViewById<RecyclerView>(R.id.rvContactList)

    rvContactList.adapter = contactListAdapter
    rvContactList.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))

    listContactViewModel.contactList.observe(this, Observer { contactList ->
      activity?.runOnUiThread {
        contactListAdapter.setDataList(contactList)
        val actionBarTitle = String.format(Locale.getDefault(),
          getString(R.string.action_bar_title_change_number), contactList.size)
        getBaseActivity().setActionBarTitle(actionBarTitle)
      }
    })

    getBaseActivity().setActionBarTitle(stringProvider.get(R.string.app_name))
  }

  companion object {
    fun newInstance(): ListContactFragment {
      val fragment = ListContactFragment()
      return fragment
    }
  }
}