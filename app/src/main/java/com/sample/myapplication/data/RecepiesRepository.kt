package com.sample.myapplication.data

import com.sample.myapplication.data.network.RecepiesRemoteDataSource
import com.sample.myapplication.model.Result
import com.sample.myapplication.model.network.CategoriesResponse
import com.sample.myapplication.model.network.RecepiesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RecepiesRepository@Inject constructor(
    private val movieRemoteDataSource: RecepiesRemoteDataSource
) {

    suspend fun fetchTrendingMovies(): Flow<Result<RecepiesResponse>?> {
        return flow {
            emit(Result.loading())
            val result = movieRemoteDataSource.fetchRecepies()

            if (result.status == Result.Status.SUCCESS) {
                result.data?.recipes?.let { it ->
                /*    movieDao.deleteAll(it)
                    movieDao.insertAll(it)*/
                }
            }
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun fetchCategories(): Flow<Result<CategoriesResponse>?> {
        return flow {
            emit(Result.loading())
            val result = movieRemoteDataSource.fetchCategories()

            if (result.status == Result.Status.SUCCESS) {
                result.data?.categories?.let { it ->
                    /*    movieDao.deleteAll(it)
                        movieDao.insertAll(it)*/
                }
            }
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

  /*  private fun fetchTrendingMoviesCached(): Result<TrendingMovieResponse>? =
        movieDao.getAll()?.let {
            Result.success(TrendingMovieResponse(it))
        }

    suspend fun fetchMovie(id: Int): Flow<Result<MovieDesc>> {
        return flow {
            emit(Result.loading())
            emit(movieRemoteDataSource.fetchMovie(id))
        }.flowOn(Dispatchers.IO)
    }*/
}
