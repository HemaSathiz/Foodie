package com.sample.myapplication.ui.recepies

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.myapplication.data.CategoriesRespository
import com.sample.myapplication.model.Result
import com.sample.myapplication.model.network.CategoriesResponse
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@InternalCoroutinesApi
class CategoriesViewModel @Inject constructor(private val movieRepository: CategoriesRespository) :
    ViewModel() {

    private val _movieList = MutableLiveData<Result<CategoriesResponse>>()
    val movieList = _movieList

    init {
        fetchMovies()
    }

    @InternalCoroutinesApi
    private fun fetchMovies() {
        viewModelScope.launch {
            movieRepository.fetchTrendingMovies().collect {
                _movieList.value = it
            }
        }
    }
}
