package com.gitee.android.http

import androidx.lifecycle.LiveData
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean

/**
 * A Retrofit adapter that converts the Call into a LiveData of BaseResponse.
 * <p>
 * https://github.com/android/architecture-components-samples/blob/master/GithubBrowserSample
 */
class LiveDataCallAdapter<R>(private val responseType: Type) :
    CallAdapter<R, LiveData<ApiResponse<R>>> {

    override fun responseType(): Type = responseType

    override fun adapt(call: Call<R>): LiveData<ApiResponse<R>> {
        return object : LiveData<ApiResponse<R>>() {
            var started = AtomicBoolean(false)

            override fun onActive() {
                super.onActive()
                if (started.compareAndSet(false, true)) {
                    call.enqueue(object : Callback<R> {
                        override fun onResponse(call: Call<R>, response: Response<R>) {
                            postValue(ApiResponse(response))
                        }

                        override fun onFailure(call: Call<R>, throwable: Throwable) {
                            postValue(ApiResponse(throwable))
                        }
                    })
                }
            }
        }
    }
}

//class LiveDataCallAdapter<T>(
//    private val responseType: Type,
//    var creator: (Int, String, Any?) -> Any
//) :
//    CallAdapter<T, LiveData<T>> {
//    override fun adapt(call: Call<T>): LiveData<T> {
//        return object : LiveData<T>() {
//            private val started = AtomicBoolean(false)
//            override fun onActive() {
//                super.onActive()
//                if (started.compareAndSet(false, true)) {//确保执行一次
//                    call.enqueue(object : Callback<T> {
//                        override fun onFailure(call: Call<T>, t: Throwable) {
//                            val value = creator(-1, t.message ?: "", null) as T
//                            postValue(value)
//                        }
//
//                        override fun onResponse(
//                            call: Call<T>,
//                            response: Response<T>
//                        ) {
//                            postValue(response.body())
//                        }
//                    })
//                }
//            }
//        }
//    }
//
//    override fun responseType() = responseType
//}