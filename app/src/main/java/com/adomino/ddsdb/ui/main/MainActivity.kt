package com.adomino.ddsdb.ui.main

import android.Manifest
import android.view.MenuItem
import androidx.viewpager2.widget.ViewPager2
import com.adomino.ddsdb.R
import com.adomino.ddsdb.base.BaseActivity
import com.adomino.ddsdb.common.bindView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

class MainActivity : BaseActivity() {

  private val btnExecute: FloatingActionButton by bindView(R.id.fbtExecute)

  private val bottomView: BottomNavigationView by bindView(R.id.bnvFunction)

  private val viewPager: ViewPager2 by bindView(R.id.viewPager)

  override fun viewId(): Int {
    return R.layout.activity_main
  }

  override fun onInitUI() {
    val pageAdapter: ViewPagerAdapter = ViewPagerAdapter(this)
    viewPager.adapter = pageAdapter

    viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
      private var lastBottomItemView: MenuItem? = null

      override fun onPageSelected(position: Int) {
        lastBottomItemView?.let { viewItem ->
          viewItem.isChecked = false
        }
        val bottomItemView = bottomView.menu.getItem(position)
        bottomItemView.isChecked = true
        lastBottomItemView = bottomItemView
      }
    })

    bottomView.setOnNavigationItemSelectedListener { itemView ->
      when (itemView.itemId) {
        R.id.itConvert -> viewPager.setCurrentItem(0, true)
        R.id.itFindSame -> viewPager.setCurrentItem(1, true)
        R.id.itConvert2 -> viewPager.setCurrentItem(2, true)
        R.id.itDelete -> viewPager.setCurrentItem(3, true)
      }
      return@setOnNavigationItemSelectedListener true
    }
  }

  override fun onStart() {
    super.onStart()
    Dexter.withActivity(this)
        .withPermissions(
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS
        )
        .withListener(object : MultiplePermissionsListener {
          override fun onPermissionsChecked(report: MultiplePermissionsReport) {}
          override fun onPermissionRationaleShouldBeShown(
            permissions: List<PermissionRequest>,
            token: PermissionToken
          ) {
          }
        })
        .check()
  }
}