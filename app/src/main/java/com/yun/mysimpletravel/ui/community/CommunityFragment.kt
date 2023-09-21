package com.yun.mysimpletravel.ui.community

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.behavior.HideBottomViewOnScrollBehavior
import com.yun.mysimpletravel.BR
import com.yun.mysimpletravel.R
import com.yun.mysimpletravel.base.BaseFragment
import com.yun.mysimpletravel.base.BaseRecyclerAdapter
import com.yun.mysimpletravel.common.constants.CommunityConstants
import com.yun.mysimpletravel.common.manager.NavigationManager
import com.yun.mysimpletravel.data.model.community.CommunityDataModel
import com.yun.mysimpletravel.databinding.FragmentCommunityBinding
import com.yun.mysimpletravel.databinding.ItemCommunityBinding
import com.yun.mysimpletravel.ui.popup.community.CommunityCreatePopup
import com.yun.mysimpletravel.util.Util
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommunityFragment : BaseFragment<FragmentCommunityBinding, CommunityViewModel>(){
    override val viewModel: CommunityViewModel by viewModels()
    override fun setVariable(): Int = BR.community
    override fun getResourceId(): Int = R.layout.fragment_community
    override fun isLoading(): LiveData<Boolean>? = viewModel.isLoading
    override fun isOnBackEvent(): Boolean = true
    override fun onBackEvent() {}

    private lateinit var navigationManager: NavigationManager
    private lateinit var communityCreatePopup: CommunityCreatePopup

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()

        navigationManager = NavigationManager(requireActivity(), view)
        communityCreatePopup = CommunityCreatePopup(requireActivity(), communityCreateInterface)

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

        binding.fab.setOnClickListener(clickListener)
    }

    private val communityCreateInterface = object : CommunityCreatePopup.CommunityCreateInterface {
        override fun onButtonClick(result: Boolean) {
            if (result) {
                viewModel.setData(clear = true)
                Toast.makeText(requireActivity(), "저장", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireActivity(), "취소", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun init() {
        Util.delayedHandler(100) {
            viewModel.setData(clear = true)
        }
    }

    private val clickListener = View.OnClickListener { v ->
        when (v) {
            binding.fab -> {
                moveCreateScreen()
            }
        }
    }

    private fun moveCreateScreen() {
        communityCreatePopup.showPopup()
//        navigationManager.movingScreen(R.id.action_homeFragment_to_communityCreateFragment,
//            NavigationConstants.Type.ENTER
//        )
    }

    /**
     * 화면 최상단으로 스크롤
     */
    private fun topScroll() {
        ((binding.appBarLayout.layoutParams as? CoordinatorLayout.LayoutParams)?.behavior as? AppBarLayout.Behavior)?.topAndBottomOffset =
            0
        ((binding.fab.layoutParams as CoordinatorLayout.LayoutParams).behavior as HideBottomViewOnScrollBehavior).slideUp(
            binding.fab
        )
        binding.rvCommunity.smoothScrollToPosition(0)
    }
}