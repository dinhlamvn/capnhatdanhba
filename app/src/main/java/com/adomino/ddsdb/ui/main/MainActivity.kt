package com.adomino.ddsdb.ui.main

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBar
import com.adomino.ddsdb.R
import com.adomino.ddsdb.base.BaseActivity
import com.adomino.ddsdb.ui.listcontact.ListContactFragment
import com.adomino.ddsdb.ui.main.MainViewListener.CurrentPage
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

class MainActivity : BaseActivity(), View.OnClickListener, MainViewListener {

  private val viewListener: MainViewListener = this

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    findViewById<View>(R.id.fbtExecute).setOnClickListener(this)
    val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bnvFunction)
    bottomNavigationView.setOnNavigationItemSelectedListener { item ->
      item.isChecked = true
      if (item.itemId == R.id.itConvert) {
        currentPage = CurrentPage.CHANGE_HEAD_NUMBER_PAGE
        ListContactFragment.newInstance().attachTo(this,
          R.id.container)
      } else if (item.itemId == R.id.itFindSame) {
        currentPage = CurrentPage.REMOVE_DUPLICATE_PAGE
      }
      true
    }
  }

  override fun onStart() {
    super.onStart()
    Dexter.withActivity(this)
      .withPermissions(
        Manifest.permission.READ_CONTACTS,
        Manifest.permission.WRITE_CONTACTS
      ).withListener(object : MultiplePermissionsListener {
        override fun onPermissionsChecked(report: MultiplePermissionsReport) {}
        override fun onPermissionRationaleShouldBeShown(
          permissions: List<PermissionRequest>,
          token: PermissionToken
        ) {
        }
      }).check()
  }

  override fun onClick(v: View) {
    when (currentPage) {
      CurrentPage.CHANGE_HEAD_NUMBER_PAGE -> {
        viewListener.executeUpdateContactList()
      }
      CurrentPage.REMOVE_DUPLICATE_PAGE -> {
        viewListener.executeRemoveContactList()
      }
    }
  }

  override fun onActionBarConfiguration(actionBar: ActionBar) {}
  override fun viewMainId(): Int {
    return R.id.container
  }

  override fun executeUpdateContactList() {}
  override fun executeRemoveContactList() {}

  companion object {
    private var currentPage = CurrentPage.CHANGE_HEAD_NUMBER_PAGE
  }
}