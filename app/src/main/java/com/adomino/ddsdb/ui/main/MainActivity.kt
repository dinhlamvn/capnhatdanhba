package com.adomino.ddsdb.ui.main

import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.adomino.ddsdb.R
import com.adomino.ddsdb.base.BaseActivity
import com.adomino.ddsdb.common.ViewPagerFragmentFactory
import com.adomino.ddsdb.common.bindView
import com.adomino.ddsdb.database.repository.ContactRepository
import com.adomino.ddsdb.interactor.local.LocalService
import com.adomino.ddsdb.ui.listcontact.ListContactFragment
import com.adomino.ddsdb.util.UIHelper
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import javax.inject.Inject

class MainActivity : BaseActivity(),
    BottomNavigationView.OnNavigationItemSelectedListener,
    BottomNavigationView.OnNavigationItemReselectedListener {

  private val btnMenu: FloatingActionButton by bindView(R.id.fbtMenu)

  private val bottomView: BottomNavigationView by bindView(R.id.bnvFunction)

  private val viewPager: ViewPager2 by bindView(R.id.viewPager)

  @Inject
  lateinit var repository: ContactRepository

  @Inject
  lateinit var contactService: LocalService.Contact

  private val viewPagerAdapter = ViewPagerAdapter(
      this,
      object : ViewPagerFragmentFactory {

        val listContactFragment = ListContactFragment.create()

        override fun getFragment(position: Int): Fragment {
          return when (position) {
            0 -> listContactFragment
            1 -> ListContactFragment.create()
            3 -> ListContactFragment.create()
            else -> ListContactFragment.create()
          }
        }

        override fun size(): Int {
          return 4
        }
      }
  )

  override fun onNavigationItemSelected(item: MenuItem): Boolean {
    val position = getBottomNavigationItemPosition(item);
    viewPager.setCurrentItem(position, true)
    return true
  }

  override fun onNavigationItemReselected(item: MenuItem) {
    val position = getBottomNavigationItemPosition(item)
    val fragment = viewPagerAdapter.getFragmentAtPosition(position)
  }

  private fun getBottomNavigationItemPosition(item: MenuItem): Int {
    return when (item.itemId) {
      R.id.itConvert -> 0
      R.id.itFindSame -> 1
      R.id.itConvert2 -> 2
      else -> 3
    }
  }

  private val pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
    private var lastBottomItemView: MenuItem? = null

    override fun onPageSelected(position: Int) {
      lastBottomItemView?.let { viewItem ->
        viewItem.isChecked = false
      }
      val bottomItemView = bottomView.menu.getItem(position)
      bottomItemView.isChecked = true
      lastBottomItemView = bottomItemView
    }
  }

  override fun viewId(): Int {
    return R.layout.activity_main
  }

  override fun onInitUI() {
    viewPager.adapter = viewPagerAdapter
    viewPager.registerOnPageChangeCallback(pageChangeCallback)
    bottomView.setOnNavigationItemSelectedListener(this)

    btnMenu.setOnClickListener {
      showPopupMenu()
    }
  }

  private fun showPopupMenu() {
    UIHelper.showPopupMenu(this, btnMenu, R.menu.contact_menu) { menuItem ->
      when (menuItem.itemId) {
        R.id.itemUpdate -> {
          val fragmentListContact = viewPagerAdapter.getFragmentAtPosition(0) as ListContactFragment
          fragmentListContact.viewModel.updateContact()
        }
        R.id.itemAddContact -> {

        }
        R.id.itemShareContact -> {
          val fragmentListContact = viewPagerAdapter.getFragmentAtPosition(0) as ListContactFragment
          fragmentListContact.shareContact()
        }
      }
      true
    }
  }
}