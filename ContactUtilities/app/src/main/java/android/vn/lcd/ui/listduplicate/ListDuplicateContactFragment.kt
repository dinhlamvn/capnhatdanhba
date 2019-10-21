package android.vn.lcd.ui.listduplicate

import android.os.Bundle
import android.view.View
import android.vn.lcd.base.LoadingBaseFragment
import android.vn.lcd.customview.LoadingPercentDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.adomino.ddsdb.R
import kotlinx.android.synthetic.main.fragment_list_contact.rvContactList
import kotlinx.android.synthetic.main.fragment_list_duplicate_contact.*

class ListDuplicateContactFragment : LoadingBaseFragment() {

    private val listContactViewModelFactory: ListDuplicateContactViewModelFactory
            by lazy(LazyThreadSafetyMode.NONE) {
        ListDuplicateContactViewModelFactory(requireContext().contentResolver)
    }

    private val listDuplicateContactViewModel: ListDuplicateContactViewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(this, listContactViewModelFactory).get(ListDuplicateContactViewModel::class.java)
    }

    private val loadingPercentDialog by lazy {
        LoadingPercentDialog.newInstance()
    }

    private val contactListAdapter: ListDuplicateContactAdapter by lazy(LazyThreadSafetyMode.NONE) {
        ListDuplicateContactAdapter(ListDuplicateContactAdapter.OnContactChecked { contactInfo, isChecked ->
            if (isChecked) {
                listDuplicateContactViewModel.removeContactFromRemoveList(contactInfo)
            } else {
                listDuplicateContactViewModel.addContactToRemove(contactInfo)
            }
        })
    }

    override fun getLayoutResource(): Int {
        return R.layout.fragment_list_duplicate_contact
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvContactList.adapter = contactListAdapter
        rvContactList.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))

        listDuplicateContactViewModel.contactList.observe(this, Observer { contactList ->
            activity?.runOnUiThread {
                contactListAdapter.setDataList(contactList)
            }
            if (contactList.isNotEmpty()) {
                rvContactList.visibility = View.VISIBLE
            } else {
                rvContactList.visibility = View.GONE
            }
        })

        listDuplicateContactViewModel.showLoading.observe(this, Observer { info ->
            if (info.isShow) {
                if (info.message.isNotEmpty()) {
                    displayLoading(message = info.message)
                } else {
                    displayLoading()
                }
            } else {
                dismissLoading()
            }
        })

        listDuplicateContactViewModel.percentLoading.observe(this, Observer { info ->
            if (info.isShow) {
                if (loadingPercentDialog.isShowing) {
                    loadingPercentDialog.doUpdate(info.percent, info.total)
                } else {
                    fragmentManager?.let { manager ->
                        loadingPercentDialog.show(manager)
                    }
                }
            } else {
                if (loadingPercentDialog.showsDialog) {
                    loadingPercentDialog.dismissAllowingStateLoss()
                }
            }
        })

        listDuplicateContactViewModel.loadContactList()

        srlLayout.setOnRefreshListener {
            srlLayout.isRefreshing = true
            listDuplicateContactViewModel.refreshList()
            srlLayout.isRefreshing = false
        }
    }

    fun removeDuplicatePhoneNumber() {
        listDuplicateContactViewModel.removeContactList()
    }
}