package com.yun.mysimpletravel.ui.community

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import com.yun.mysimpletravel.base.BaseViewModel
import com.yun.mysimpletravel.base.ListLiveData
import com.yun.mysimpletravel.common.constants.AuthConstants.Info.SNS_ID
import com.yun.mysimpletravel.common.constants.FirebaseConstants.Community.IMG
import com.yun.mysimpletravel.common.constants.FirebaseConstants.Community.LIKES
import com.yun.mysimpletravel.common.constants.FirebaseConstants.Community.MESSAGE
import com.yun.mysimpletravel.common.constants.FirebaseConstants.Community.TIMESTAMP
import com.yun.mysimpletravel.common.constants.FirebaseConstants.Community.WRITER
import com.yun.mysimpletravel.data.model.community.CommunityDataModel
import com.yun.mysimpletravel.util.FirebaseUtil
import com.yun.mysimpletravel.util.PreferenceUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CommunityViewModel @Inject constructor(
    application: Application,
    private val sPrefs: PreferenceUtil
) :
    BaseViewModel(application) {

    private val _isLoading = MutableLiveData<Boolean>(false)
    override val isLoading: LiveData<Boolean> get() = _isLoading

    private val _communityList = ListLiveData<CommunityDataModel.RS>()
    val communityList: ListLiveData<CommunityDataModel.RS> get() = _communityList

    // 가져올 수 있는 모든 게시글을 가져왔는지 체크
    var isLimit = false

    // 마지막에 가져온 게시글
    var lastItem: DocumentSnapshot? = null

    var itemTotalSize = 0

    init {

    }

    //TODO 지금 파이어베이스로 하고 있지만, 그냥 일반 서버 형태로 전부 전환하는게 맞을 듯
    fun setData(clear: Boolean = false): Boolean {
        setLoading(true)
        if (clear) {
            // 초기화
            lastItem = null
            isLimit = false
            _communityList.clear(true)
            itemTotalSize = 0
        }

        val profile =
            "https://k.kakaocdn.net/dn/bog87f/btss3F1JVQw/18XAftomGevqjvJ4h2t09k/img_640x640.jpg"
        val img = "https://t1.daumcdn.net/cfile/tistory/2463694C53D0A5D806"
//        val img = "https://k.kakaocdn.net/dn/bog87f/btss3F1JVQw/18XAftomGevqjvJ4h2t09k/img_640x640.jpg"


        FirebaseUtil.selectCommunity(lastItem) {

            Log.d("lys", "selectCommunity isSuccessful > ${it.isSuccessful}")
            if (it.isSuccessful && it.result.documents.size > 0) {
                val userId = sPrefs.getString(mContext, SNS_ID)
                val arrayList = arrayListOf<CommunityDataModel.RS>()
                it.result.documents.forEachIndexed { index, snap ->
                    val timestamp = snap[TIMESTAMP] as Timestamp
                    val likes = snap[LIKES] as ArrayList<String>
                    val message = snap[MESSAGE] as String
                    val imgUrl = snap[IMG] as String
                    val writer = snap[WRITER] as String
                    arrayList.add(
                        CommunityDataModel.RS(
                            itemTotalSize++,
                            imgUrl,
                            likes,
                            likes.contains(userId),
                            message,
                            timestamp,
                            writer,
                            fullSize = false
                        )
                    )

                    lastItem = snap
                }
                _communityList.addAll(arrayList)
                Log.d("lys", "communityList > ${communityList.value}")
                setLoading(false)
            } else {
                Log.d("lys", "selectCommunity error > ${it.isSuccessful}  ${it.result.documents}")
                isLimit = true
                setLoading(false)
            }
        }


//        val list = arrayListOf<CommunityDataModel.RS>()
//        for (i in 0..8) {
//            list.add(
//                CommunityDataModel.RS(
//                    _communityList.sizes() + list.size,
//                    UserInfoDataModel("test", "email","testNm", profile, "",""),
//                    "testContentstestContentstestContentstestContentstestContentstestContentstestContentstestContentstestContentstestContentstestContentstestContentstestContentstestContentstestContentstestContents",
//                    img,
//                    false
//                )
//            )
//        }
//        Util.delayedHandler(1000) {
//            _communityList.addAll(list)
//            setLoading(false)
//        }


        return true
    }

    fun writeCommunity(message: String, base64: String) {
        val userId = sPrefs.getString(mContext, SNS_ID) ?: return
        FirebaseUtil.writeCommunity(userId, message, base64) { isSuccess ->
            if (isSuccess) setData(true)
        }
    }

    fun setLoading(loading: Boolean) {
        _isLoading.value = loading
    }

    fun test() {
        Log.d("lys", "test")
    }
}