package com.yun.mysimpletravel.ui.map

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yun.mysimpletravel.base.BaseViewModel
import com.yun.mysimpletravel.data.model.jejuhub.carsharing.CarsharingList
import com.yun.mysimpletravel.data.model.jejuhub.carsharing.CarsharingModel
import com.yun.mysimpletravel.data.repository.jejuhub.JejuHubRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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

        pharmacy()
    }

    /**
     * 카셰어링 소카
     */
    fun carsharingWithSocar() {
        viewModelScope.launch(Dispatchers.IO) {
            jejuHubRepositoryImpl.carsharingWithSocar() { data, throwable ->
                if (throwable != null) {
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
    fun carsharing(page: Int = 1, tempArray: List<CarsharingList> = emptyList()) {
        viewModelScope.launch(Dispatchers.IO) {
            jejuHubRepositoryImpl.carsharing(page.toString()) { data, throwable ->
                if (throwable != null) {
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


    fun pharmacy() {
        viewModelScope.launch(Dispatchers.IO) {
            jejuHubRepositoryImpl.pharmacy("1") { data, throwable ->
                Log.d("lys", "pharmacy > $data")
            }
        }
    }


}