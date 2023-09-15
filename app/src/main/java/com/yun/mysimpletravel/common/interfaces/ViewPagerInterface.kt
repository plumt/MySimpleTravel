package com.yun.mysimpletravel.common.interfaces

interface ViewPagerInterface {
    fun onPageSelected(position: Int)
    fun onReselected(position: Int)
    fun moveScreen(position: Int)
}