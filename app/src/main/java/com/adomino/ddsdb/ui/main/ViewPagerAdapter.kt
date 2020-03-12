package com.adomino.ddsdb.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.adomino.ddsdb.common.ViewPagerFragmentFactory

class ViewPagerAdapter(
  fragmentActivity: FragmentActivity,
  val factory: ViewPagerFragmentFactory
) : FragmentStateAdapter(fragmentActivity) {
  override fun getItemCount(): Int {
    return factory.size()
  }

  override fun createFragment(position: Int): Fragment {
    return factory.getFragment(position)
  }

  fun getFragmentAtPosition(position: Int): Fragment {
    require(position < factory.size()) { "Position greater than size." }
    return factory.getFragment(position)
  }
}