package com.gitee.android.http.rx

import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Title:TransformerHelper
 *
 * Description:
 *
 * @author javakam
 * @date 2019/11/12 16:11
 */
object TransformerHelper {

    private val TRANSFORMER: FlowableTransformer<Any, Any> =
        FlowableTransformer<Any, Any> { upstream: Flowable<Any> ->
            upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }

    /**
     * io线程-主线程
     */
    fun <T> io2main(): FlowableTransformer<T, T> {
        return TRANSFORMER as FlowableTransformer<T, T>
    }

    /**
     * 将服务器返回的Response<T>流装换成T流
     */
    //    public static <T> FlowableTransformer<BaseResponse<T>, T> handleResponse(Class<?> clz) {
    //        return flowable -> flowable.flatMap((Function<BaseResponse<T>, Publisher<T>>) t -> {
    //            if (t == null || (t instanceof List && ((List) t).size() == 0)) {
    //                return Flowable.error(new Exception("返回的数据实体为空"));// 自定义异常
    //            } else {
    //                if (t.data != null) {
    //                    if ((t.data instanceof String) && TextUtils.isEmpty((String) t.data)) {
    //                        return Flowable.error(new Throwable(t.status));
    //                    }
    //                    return Flowable.just(t.data);
    //                }
    //                return Flowable.error(new Throwable("未获取到有效数据"));
    //            }
    //        });
    //    }

}