package com.ando.library.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ando.toolkit.ext.otherwise
import com.ando.toolkit.ext.yes

/**
 * Title:BaseLazyFragment
 *
 * Description:
 *
 * @author changbao
 * @date 2019/3/17 13:24
 */

class LazyFragmentController(private val fragment: BaseFragment) : IBaseLazyInterface {

    var isDataInitiated = false //是否已加载过数据

    @Deprecated("Use {@link FragmentTransaction#setMaxLifecycle}")
    fun setUserVisibleHint(isVisibleToUser: Boolean) {
        if (fragment.isVisibleToUser) {
            prepareFetchData(false)
        }
    }

    override fun prepareFetchData(forceUpdate: Boolean) {
        //强制更新
        //(!isDataInitiated || forceUpdate) -> 执行刷新数据的两个条件:1.未初始化数据;2.强制刷新
        if (forceUpdate && fragment.isActivityCreated && fragment.isAdded) {
            initLazyData()
            isDataInitiated = true
            return
        }

//      L.d("123","prepareFetchData isActivityCreated " + isActivityCreated + "   isVisibleToUser  " + isVisibleToUser + "    isAdded()   " + isAdded()
//              + " isResumed() " + isResumed() + "  isVisible()   " + isVisible());

        //正常懒加载
        if (!isDataInitiated && fragment.isActivityCreated && fragment.isVisibleToUser) {
            //在该方法中初始化 Fragment 的数据
            //initLayout -> initViews -> onActivityCreated -> initData -> prepareFetchData -> initLazyData
            initLazyData()
            isDataInitiated = true
        }
    }

    override fun initLazyData() {
    }
}

abstract class BaseLazyFragment : BaseFragment(), IBaseLazyInterface {

    private lateinit var controller: LazyFragmentController
    override fun onAttach(context: Context) {
        super.onAttach(context)
        controller = LazyFragmentController(this)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        controller.setUserVisibleHint(isVisibleToUser)
    }

    override fun prepareFetchData() {
        prepareFetchData(false)
    }

    override fun prepareFetchData(forceUpdate: Boolean) {
        controller.prepareFetchData(forceUpdate)
    }

}

abstract class BaseMvcLazyFragment : BaseMvcFragment(), IBaseLazyInterface {

    /**
     * <pre>
     * @Override
     * public void onActivityCreated(Bundle savedInstanceState) {
     *      super.onActivityCreated(savedInstanceState);
     *      initData() ;
     * }
     * </pre>
     * [com.ando.library.base.BaseFragment.onActivityCreated]
     */
    override fun initData() {
        prepareFetchData(false)
    }

    private lateinit var controller: LazyFragmentController
    override fun onAttach(context: Context) {
        super.onAttach(context)
        controller = LazyFragmentController(this)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        controller.setUserVisibleHint(isVisibleToUser)
    }

    override fun prepareFetchData() {
        prepareFetchData(false)
    }

    override fun prepareFetchData(forceUpdate: Boolean) {
        controller.prepareFetchData(forceUpdate)
    }

}
