package com.yun.mysimpletravel.ui.map

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yun.mysimpletravel.base.BaseViewModel
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

    fun carsharingWithSocar(){
        viewModelScope.launch {
            jejuHubRepositoryImpl.carsharingWithSocar(object : JejuHubRepositoryImpl.GetDataCallBack<CarsharingModel>{
                override fun onSuccess(data: CarsharingModel) {
                    _carsharingWithSocar.postValue(data)
                    Log.d("lys","carsharingWithSocar > $data")
                }

                override fun onFailure(throwable: Throwable) {
                    throwable.printStackTrace()
                }
            })
        }
    }

    fun carsharing(){
        viewModelScope.launch {
            jejuHubRepositoryImpl.carsharing(object : JejuHubRepositoryImpl.GetDataCallBack<CarsharingModel>{
                override fun onSuccess(data: CarsharingModel) {
                    _carsharing.postValue(data)
                }

                override fun onFailure(throwable: Throwable) {
                    throwable.printStackTrace()
                }
            })
        }
    }

}