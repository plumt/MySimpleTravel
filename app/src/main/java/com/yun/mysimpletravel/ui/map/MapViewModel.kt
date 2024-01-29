package com.yun.mysimpletravel.ui.map

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yun.mysimpletravel.base.BaseViewModel
import com.yun.mysimpletravel.data.model.travel.carsharing.CarsharingList
import com.yun.mysimpletravel.data.model.travel.carsharing.CarsharingModel
import com.yun.mysimpletravel.data.repository.jejuhub.JejuHubRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    application: Application,
    private val jejuHubRepositoryImpl: JejuHubRepositoryImpl,
) : BaseViewModel(application) {

    private val _isLoading = MutableLiveData<Boolean>()
    override val isLoading: LiveData<Boolean> get() = _isLoading

    private val _carsharingWithSocar = MutableLiveData<CarsharingModel>()
    val carsharingWithSocar: LiveData<CarsharingModel> get() = _carsharingWithSocar

    private val _carsharing = MutableLiveData<CarsharingModel>()
    val carsharing: LiveData<CarsharingModel> get() = _carsharing

    init {
        carsharingWithSocar()
        carsharing()
    }

    /**
     * 카셰어링 소카
     */
//    fun carsharingWithSocar() {
//        viewModelScope.launch {
//            jejuHubRepositoryImpl.carsharingWithSocar(callBack = object :
//                JejuHubRepositoryImpl.GetDataCallBack<CarsharingModel> {
//                override fun onSuccess(data: CarsharingModel) {
//                    _carsharingWithSocar.postValue(data)
//                    Log.d("lys", "carsharingWithSocar > $data")
//                }
//
//                override fun onFailure(throwable: Throwable) {
//                    throwable.printStackTrace()
//                }
//            })
//        }
//    }
    fun carsharingWithSocar() {
        viewModelScope.launch {
            jejuHubRepositoryImpl.carsharingWithSocar(){ data, throwable ->
                if(throwable != null){
                    throwable.printStackTrace()
                    return@carsharingWithSocar
                }
                _carsharingWithSocar.postValue(data)
            }
        }
    }

    /**
     * 카셰어링
     */
//    fun carsharing(page: Int = 1, tempArray: List<CarsharingList> = arrayListOf()){
//        viewModelScope.launch {
//            jejuHubRepositoryImpl.carsharing(page.toString(),object : JejuHubRepositoryImpl.GetDataCallBack<CarsharingModel>{
//                override fun onSuccess(data: CarsharingModel) {
//                    val temp = (data.list ?: arrayListOf()).plus(tempArray)
//                    if(data.hasMore){
//                        carsharing(page+1, temp)
//                    } else {
//                        _carsharing.postValue(data.apply {
//                            list = temp
//                        })
//                    }
//                }
//
//                override fun onFailure(throwable: Throwable) {
//                    throwable.printStackTrace()
//                }
//            })
//        }
//    }

    fun carsharing(page: Int = 1, tempArray: List<CarsharingList> = emptyList()) {
        viewModelScope.launch {
            jejuHubRepositoryImpl.carsharing(page.toString()){ data, throwable ->
//                throwable?.printStackTrace()
                if(throwable != null){
                    throwable.printStackTrace()
                    return@carsharing
                }
                data?.let {
                    val temp = (it.list ?: emptyList()) + tempArray
                    if (it.hasMore) {
                        carsharing(page + 1, temp)
                    } else {
                        _carsharing.postValue(it.copy(list = temp))
                    }
                }
            }
        }
    }




}