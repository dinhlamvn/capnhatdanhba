package android.vn.lcd.ui.listcontact

import android.os.Bundle
import android.view.View
import android.vn.lcd.base.BaseFragment
import android.vn.lcd.helper.ContactHelper
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.adomino.ddsdb.R
import kotlinx.android.synthetic.main.fragment_list_contact.*
import kotlin.concurrent.timer

class ListContactFragment : BaseFragment() {

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
            }
        })

        listContactViewModel.showLoading.observe(this, Observer { info ->
            if (info.isShow) {
                if (info.title.isNotEmpty() && info.content.isNotEmpty()) {
                    showLoading(title = info.title, message = info.content)
                } else {
                    showLoading(title = info.title)
                }
            } else {
                dismissLoading()
            }
        })

        listContactViewModel.loadContactList()
    }

    fun executeUpdateContact() {
        listContactViewModel.updateContact()
    }

    fun loadListContact() {
        listContactViewModel.loadContactList()
    }
}