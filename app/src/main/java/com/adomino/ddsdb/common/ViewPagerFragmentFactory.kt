package com.adomino.ddsdb.common

import androidx.fragment.app.Fragment

interface ViewPagerFragmentFactory {
  fun getFragment(position: Int): Fragment
  fun size(): Int
}