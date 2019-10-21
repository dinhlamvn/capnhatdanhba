package android.vn.lcd.ui.listcontact

import android.os.Bundle
import android.view.View
import android.vn.lcd.base.BaseFragment
import android.vn.lcd.base.LoadingBaseFragment
import android.vn.lcd.ui.main.MainActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.adomino.ddsdb.R
import kotlinx.android.synthetic.main.fragment_list_contact.*
import kotlinx.android.synthetic.main.fragment_list_contact.rvContactList
import kotlinx.android.synthetic.main.fragment_list_contact.srlLayout
import kotlinx.android.synthetic.main.fragment_list_duplicate_contact.*
import java.util.*

class ListContactFragment : LoadingBaseFragment() {

    private val listContactViewModelFactory: ListContactViewModelFactory by lazy(LazyThreadSafetyMode.NONE) {
        ListContactViewModelFactory(requireContext().contentResolver)
    }

    private val listContactViewModel: ListContactViewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(this, listContactViewModelFactory).get(ListContactViewModel::class.java)
    }

    private val contactListAdapter: ListContactAdapter by lazy(LazyThreadSafetyMode.NONE) {
        ListContactAdapter()
    }

    override fun getLayoutResource(): Int {
        return R.layout.fragment_list_contact
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

        listContactViewModel.showLoading.observe(this, Observer { info ->
            if (info.isShow) {
                if (info.message.isNotEmpty()) {
                    displayLoading(info.message)
                } else {
                    displayLoading()
                }
            } else {
                dismissLoading()
            }
        })

        srlLayout.setOnRefreshListener {
            srlLayout.isRefreshing = true
            listContactViewModel.refreshList()
            srlLayout.isRefreshing = false
        }
    }

    fun executeUpdateContact() {
        listContactViewModel.updateContact()
    }
}