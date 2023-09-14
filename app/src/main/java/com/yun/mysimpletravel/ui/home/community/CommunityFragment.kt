package com.yun.mysimpletravel.ui.home.community

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.yun.mysimpletravel.BR
import com.yun.mysimpletravel.R
import com.yun.mysimpletravel.base.BaseFragment
import com.yun.mysimpletravel.base.BaseRecyclerAdapter
import com.yun.mysimpletravel.common.constants.CommunityConstants
import com.yun.mysimpletravel.common.constants.HomeConstants.Screen.COMMUNITY
import com.yun.mysimpletravel.data.model.community.CommunityDataModel
import com.yun.mysimpletravel.databinding.FragmentCommunityBinding
import com.yun.mysimpletravel.databinding.ItemCommunityBinding
import com.yun.mysimpletravel.ui.home.HomeViewModel
import com.yun.mysimpletravel.ui.home.ViewPagerCallback
import com.yun.mysimpletravel.util.Util
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommunityFragment : BaseFragment<FragmentCommunityBinding, CommunityViewModel>(),
    ViewPagerCallback {
    override val viewModel: CommunityViewModel by viewModels()
    override fun setVariable(): Int = BR.community
    override fun getResourceId(): Int = R.layout.fragment_community
    override fun isLoading(): LiveData<Boolean>? = viewModel.isLoading
    override fun isOnBackEvent(): Boolean = true
    override fun onBackEvent() {}

    private val homeViewModel: HomeViewModel by viewModels(
        ownerProducer = { requireParentFragment() }
    )

    override fun onPageSelected(position: Int) {
        if (position == COMMUNITY) {
            visibilityParentLayout(View.VISIBLE)
        } else {
            visibilityParentLayout(View.INVISIBLE)
        }
    }

    override fun onReselected() {
        if (isBindingInitialized) {
            binding.rvCommunity.smoothScrollToPosition(0)
        }
    }

    private fun visibilityParentLayout(visibility: Int) {
        if (isBindingInitialized) binding.layoutParent.visibility = visibility
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()

        binding.rvCommunity.run {
            adapter =
                object : BaseRecyclerAdapter.Create<CommunityDataModel.RS, ItemCommunityBinding>(
                    layoutResId = R.layout.item_community,
                    bindingVariableId = BR.itemCommunity,
                    bindingListener = BR.communityListener
                ) {
                    override fun onItemClick(item: CommunityDataModel.RS, view: View) {
                        when (view.tag.toString()) {
                            CommunityConstants.ViewTag.DETAIL -> {
                                item.fullSize = !item.fullSize
                                notifyItemChanged(item.id)
                            }

                            CommunityConstants.ViewTag.LIKE -> {
                                Toast.makeText(requireActivity(), "*좋아요", Toast.LENGTH_SHORT).show()
                            }

                            CommunityConstants.ViewTag.COMMENT -> {
                                Toast.makeText(requireActivity(), "*댓글", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                    override fun onItemLongClick(item: CommunityDataModel.RS, view: View): Boolean =
                        true
                }

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    if (viewModel.communityList.sizes() > 0 && !recyclerView.canScrollVertically(1) && !viewModel.isLoading.value!!) {
                        viewModel.setData(clear = false)
                    }
                }
            })
        }

        binding.refreshLayout.setOnRefreshListener {
            if (viewModel.setData(clear = true)) {
                binding.refreshLayout.isRefreshing = false
            }
        }
    }

    private fun init() {
        Util.delayedHandler(100) {
            viewModel.setData(clear = true)
        }
    }
}