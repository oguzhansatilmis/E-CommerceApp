package com.android.ecommerceapp.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.ecommerceapp.model.SelectedItems
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(): ViewModel() {

    private var _selectedItem = MutableLiveData<ArrayList<SelectedItems>>()
    val selectedItem: LiveData<ArrayList<SelectedItems>> = _selectedItem

    fun setSelectedItem(value: ArrayList<SelectedItems>) {
        _selectedItem.value = value
    }
}


