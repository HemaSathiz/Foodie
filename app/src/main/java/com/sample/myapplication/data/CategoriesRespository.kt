package com.sample.myapplication.data

import com.sample.myapplication.data.network.CategoriesRemoteDataSource
import com.sample.myapplication.model.Result
import com.sample.myapplication.model.network.CategoriesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CategoriesRespository@Inject constructor(
    private val movieRemoteDataSource: CategoriesRemoteDataSource
) {

    suspend fun fetchTrendingMovies(): Flow<Result<CategoriesResponse>?> {
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