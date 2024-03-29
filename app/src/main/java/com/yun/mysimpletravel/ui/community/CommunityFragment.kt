package com.yun.mysimpletravel.ui.community

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.contains
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.yun.mysimpletravel.BR
import com.yun.mysimpletravel.R
import com.yun.mysimpletravel.base.BaseFragment
import com.yun.mysimpletravel.base.BaseRecyclerAdapter
import com.yun.mysimpletravel.base.OnSingleClickListener
import com.yun.mysimpletravel.common.constants.AuthConstants.Info.SNS_ID
import com.yun.mysimpletravel.common.constants.CommunityConstants
import com.yun.mysimpletravel.common.manager.NavigationManager
import com.yun.mysimpletravel.data.model.community.CommunityDataModel
import com.yun.mysimpletravel.databinding.FragmentCommunityBinding
import com.yun.mysimpletravel.databinding.ItemCommunityBinding
import com.yun.mysimpletravel.ui.popup.community.CommunityCreatePopup
import com.yun.mysimpletravel.util.FirebaseUtil
import com.yun.mysimpletravel.util.PreferenceUtil
import com.yun.mysimpletravel.util.Util
import com.yun.mysimpletravel.util.Util.dpToPx
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CommunityFragment : BaseFragment<FragmentCommunityBinding, CommunityViewModel>() {
    override val viewModel: CommunityViewModel by viewModels()
    override fun setVariable(): Int = BR.community
    override fun getResourceId(): Int = R.layout.fragment_community
    override fun isLoading(): LiveData<Boolean>? = viewModel.isLoading
    override fun isOnBackEvent(): Boolean = true
    override fun onBackEvent() {}

    private lateinit var navigationManager: NavigationManager
    private lateinit var communityCreatePopup: CommunityCreatePopup

    @Inject
    lateinit var sPrefs: PreferenceUtil
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init(view)


        clickListenerSetting()

        observes()

        binding.rvCommunity.run {
            adapter =
                object : BaseRecyclerAdapter.Create<CommunityDataModel.RS, ItemCommunityBinding>(
                    layoutResId = R.layout.item_community,
                    bindingVariableId = BR.itemCommunity,
                    bindingListener = BR.communityListener
                ) {
                    override fun onItemClick(item: CommunityDataModel.RS, view: View) {
                        val index = viewModel.communityList.value!!.indexOf(item)
                        when (view.tag.toString()) {
                            CommunityConstants.ViewTag.DETAIL -> {
                                item.fullSize = !item.fullSize
                                if (index > -1) binding.rvCommunity.adapter!!.notifyItemChanged(
                                    index
                                )
                            }

                            CommunityConstants.ViewTag.LIKE -> {
                                val userId = sPrefs.getString(requireActivity(), SNS_ID) ?: ""

                                when {
                                    item.likes == null -> {
                                        val arrayList = arrayListOf(userId)
                                        item.likes = arrayList
                                        item.like = true
                                    }

                                    item.likes!!.contains(userId) -> {
                                        item.likes!!.remove(userId)
                                        item.like = false
                                    }

                                    !item.likes!!.contains(userId) -> {
                                        item.likes!!.add(userId)
                                        item.like = true
                                        // "item.likes" 목록에 "userId"가 포함되어 있지 않음
                                        // 여기에 처리할 코드를 추가하세요.
                                    }
                                }

                                if (index > -1) binding.rvCommunity.adapter!!.notifyItemChanged(
                                    index
                                )

                                FirebaseUtil.communityLikeUpdate(
                                    item.writer,
                                    item.timestamp,
                                    item.likes!!
                                )
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
                    if (viewModel.communityList.sizes() > 0 && !recyclerView.canScrollVertically(1) && !viewModel.isLoading.value!! && !viewModel.isLimit) {
                        viewModel.setData(clear = false)
                    }
                }
            })

            addItemDecoration(BottomMarginItemDecoration(dpToPx("10", this), dpToPx("60", this)))
            setHasFixedSize(false)
            itemAnimator = null
        }

        binding.refreshLayout.setOnRefreshListener {
            if (viewModel.setData(clear = true)) {
                binding.refreshLayout.isRefreshing = false
            }
        }


    }

    private val communityCreateInterface = object : CommunityCreatePopup.CommunityCreateInterface {
        override fun onButtonClick(message: String, base64: String) {
//            viewModel.setData(clear = true)
            viewModel.writeCommunity(message, base64)
        }
    }

    private fun init(view: View) {

        navigationManager = NavigationManager(view)
        communityCreatePopup =
            CommunityCreatePopup(requireActivity(), this, communityCreateInterface)


        Util.delayedHandler(100) {
//            viewModel.setData(clear = true)
        }
    }

    private val onSingleClickListener = object : OnSingleClickListener() {
        override fun onSingleClick(v: View) {
            when (v) {
                binding.ivCommunityWrite -> {
                    moveCreateScreen()
                }
            }
        }
    }

    private fun clickListenerSetting() {
        binding.ivCommunityWrite.setOnClickListener(onSingleClickListener)
    }

    private fun observes() {

        sharedVM.bottomNavDoubleTab.observe(viewLifecycleOwner) { doubleTab ->
            if (doubleTab) {
                topScroll()
                sharedVM.bottomNavDoubleTabEvent()
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
//        ((binding.fab.layoutParams as CoordinatorLayout.LayoutParams).behavior as HideBottomViewOnScrollBehavior).slideUp(
//            binding.fab
//        )
        binding.rvCommunity.smoothScrollToPosition(0)
    }

    inner class BottomMarginItemDecoration(private val margin: Int, private val marginBottom: Int) :
        RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            val position = parent.getChildAdapterPosition(view)
            val itemCount = state.itemCount

            // 마지막 아이템인 경우만 아래쪽 마진 추가
            if (position == itemCount - 1) {
                outRect.bottom = marginBottom
            } else {
                outRect.bottom = margin
            }
        }
    }
}