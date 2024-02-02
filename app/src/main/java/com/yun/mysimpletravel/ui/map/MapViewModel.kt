package com.yun.mysimpletravel.ui.map

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yun.mysimpletravel.base.BaseViewModel
import com.yun.mysimpletravel.data.model.jejuhub.map.JejuHubCategoryModel
import com.yun.mysimpletravel.data.model.jejuhub.map.JejuHubMapList
import com.yun.mysimpletravel.data.model.jejuhub.map.JejuHubMapModel
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

    private val _mapItems = MutableLiveData<JejuHubMapModel>()
    val mapItems: LiveData<JejuHubMapModel> get() = _mapItems

    private val _category = MutableLiveData<List<JejuHubCategoryModel>>()
    val category: LiveData<List<JejuHubCategoryModel>> get() = _category


    init {

        carsharingWithSocar()
//        carsharing()
//
//        pharmacy()


        jejuHubCategory()
    }

    private fun jejuHubCategory(){
        val temp: ArrayList<JejuHubCategoryModel> = arrayListOf()
        temp.add(JejuHubCategoryModel(temp.size, "렌트카",true))
        temp.add(JejuHubCategoryModel(temp.size, "약국",false))
        temp.add(JejuHubCategoryModel(temp.size, "병원",false))
        temp.add(JejuHubCategoryModel(temp.size, "기념품",false))

        _category.postValue(temp)
    }

    /**
     * 카셰어링 소카
     */
    fun carsharingWithSocar(page: Int = 1, tempArray: List<JejuHubMapList> = emptyList()) {
        viewModelScope.launch(Dispatchers.IO) {
            jejuHubRepositoryImpl.carsharingWithSocar() { data, throwable ->
                if (throwable != null) {
                    throwable.printStackTrace()
                    return@carsharingWithSocar
                }
//                _carsharingWithSocar.postValue(data)
                data?.let {
                    val temp = (it.list ?: emptyList()) + tempArray
                    if (it.hasMore) {
                        carsharingWithSocar(page + 1, temp)
                    } else {
                        val currentData = _mapItems.value
                        val updatedData =
                            currentData?.copy(list = currentData.list.orEmpty() + temp) ?: it.copy(
                                list = temp
                            )
                        _mapItems.postValue(updatedData)
                    }
                }
            }
        }
    }

    /**
     * 카셰어링
     */
    fun carsharing(page: Int = 1, tempArray: List<JejuHubMapList> = emptyList()) {
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
//                        _mapItems.postValue(it.copy(list = temp))
                        val currentData = _mapItems.value
                        val updatedData = currentData?.copy(list = currentData.list.orEmpty() + temp) ?: it.copy(list = temp)
                        _mapItems.postValue(updatedData)
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