package com.sample.myapplication.ui.recepies

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.myapplication.data.RecepiesRepository
import com.sample.myapplication.model.Result
import com.sample.myapplication.model.network.CategoriesResponse
import com.sample.myapplication.model.network.RecepiesResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@InternalCoroutinesApi
@HiltViewModel
class RecepiesViewModel @Inject constructor(private val movieRepository: RecepiesRepository) :
    ViewModel() {

    private val _movieList = MutableLiveData<Result<RecepiesResponse>>()
    private val _categoryList = MutableLiveData<Result<CategoriesResponse>>()

    val categoryList = _categoryList
    val movieList = _movieList

    init {
        fetchMovies()
        fetchCategories()
    }

    @InternalCoroutinesApi
    private fun fetchMovies() {
        viewModelScope.launch {
            movieRepository.fetchTrendingMovies().collect {
                _movieList.value = it
            }
        }
    }

    @InternalCoroutinesApi
    private fun fetchCategories() {
        viewModelScope.launch {
            movieRepository.fetchCategories().collect {
                categoryList.value = it
            }
        }
    }
}
