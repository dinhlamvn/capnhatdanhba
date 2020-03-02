package com.adomino.ddsdb.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.adomino.ddsdb.ui.listcontact.ListContactFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(
    fragmentActivity
) {
  override fun getItemCount(): Int {
    return 4
  }

  override fun createFragment(position: Int): Fragment {
    return ListContactFragment()
  }
}