package com.gitee.android.http

import androidx.lifecycle.LiveData
import retrofit2.CallAdapter
import retrofit2.CallAdapter.Factory
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * https://github.com/android/architecture-components-samples/blob/master/GithubBrowserSample
 */
class LiveDataCallAdapterFactory : Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != LiveData::class.java) return null
        val observableType = getParameterUpperBound(0, returnType as ParameterizedType)
        val rawObservableType = getRawType(observableType)
        require(rawObservableType == ApiResponse::class.java) { "type must be a resource" }
        require(observableType is ParameterizedType) { "resource must be parameterized" }
        val bodyType = getParameterUpperBound(0, observableType)
        return LiveDataCallAdapter<Any>(bodyType)
    }
}

//class LiveDataCallAdapterFactory(var creator: (Int, String, Any?) -> Any) : Factory() {
//
//    override fun get(
//        returnType: Type,
//        annotations: Array<Annotation>,
//        retrofit: Retrofit
//    ): CallAdapter<*, *>? {
//        if (getRawType(returnType) != LiveData::class.java) return null
//        val observableType = getParameterUpperBound(0, returnType as ParameterizedType)
//
//        val rawType = getRawType(observableType)
//        if (rawType != creator(0, "", null).javaClass) {
//            throw IllegalArgumentException("type must be ApiResponse")
//        }
//        if (observableType !is ParameterizedType) {
//            throw IllegalArgumentException("resource must be parameterized")
//        }
//        return LiveDataCallAdapter<Any>(observableType, creator)
//    }
//}
