package com.yun.mysimpletravel.ui.home.community

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.yun.mysimpletravel.R
import com.yun.mysimpletravel.BR
import com.yun.mysimpletravel.base.BaseFragment
import com.yun.mysimpletravel.base.BaseRecyclerAdapter
import com.yun.mysimpletravel.data.model.community.CommunityDataModel
import com.yun.mysimpletravel.databinding.FragmentCommunityBinding
import com.yun.mysimpletravel.databinding.ItemCommunityBinding
import com.yun.mysimpletravel.ui.home.HomeViewModel
import com.yun.mysimpletravel.util.Util.delayedHandler
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommunityFragment : BaseFragment<FragmentCommunityBinding, CommunityViewModel>() {
    override val viewModel: CommunityViewModel by viewModels()
    override fun setVariable(): Int = BR.community
    override fun getResourceId(): Int = R.layout.fragment_community
    override fun isLoading(): LiveData<Boolean>? = viewModel.isLoading
    override fun isOnBackEvent(): Boolean = true
    override fun onBackEvent() {}

    private val homeViewModel: HomeViewModel by viewModels(
        ownerProducer = { requireParentFragment() }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvCommunity.run {
            adapter =
                object : BaseRecyclerAdapter.Create<CommunityDataModel.RS, ItemCommunityBinding>(
                    layoutResId = R.layout.item_community,
                    bindingVariableId = BR.itemCommunity,
                    bindingListener = BR.communityListener
                ) {
                    override fun onItemClick(item: CommunityDataModel.RS, view: View) {

                        when (view.tag.toString()) {
                            "detail" -> {
                                view.findViewById<TextView>(R.id.tv_contents).maxLines = Int.MAX_VALUE
                            }
                            "like" -> {
                                Toast.makeText(requireActivity(), "*좋아요", Toast.LENGTH_SHORT).show()
                            }
                            "comment" -> {
                                Toast.makeText(requireActivity(), "*댓글", Toast.LENGTH_SHORT).show()
                            }

                            else -> {
                                Toast.makeText(requireActivity(), "???", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                    override fun onItemLongClick(item: CommunityDataModel.RS, view: View): Boolean =
                        true
                }

            addOnScrollListener(object : RecyclerView.OnScrollListener(){
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    if(viewModel.communityList.sizes() > 0 &&!recyclerView.canScrollVertically(1) && !viewModel.isLoading.value!!){
                        viewModel.setLoading(true)
                        Toast.makeText(requireActivity(),"바닥",Toast.LENGTH_SHORT).show()
                        viewModel.setData()
                    }
                }
            })
        }

        binding.refreshLayout.setOnRefreshListener {
            viewModel.setLoading(true)
            if(viewModel.setData(true)){
                binding.refreshLayout.isRefreshing = false
            }
        }

    }
}