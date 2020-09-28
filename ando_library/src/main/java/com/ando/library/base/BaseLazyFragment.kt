package com.ando.library.base

/**
 * Title:BaseLazyFragment
 *
 *
 * Description:
 *
 *
 * @author javakam
 * @date 2019/3/17 13:24
 */
abstract class BaseLazyFragment : BaseFragment(), IBaseLazyInterface {
    protected var isDataInitiated //Fragment 是否已加载过数据
            = false

    /**
     * After
     * <pre>
     * @Override
     * public void onActivityCreated(Bundle savedInstanceState) {
     * super.onActivityCreated(savedInstanceState);
     * initData() ;
     * }
    </pre> *
     * [com.ando.library.base.BaseFragment.onActivityCreated]
     */
    override fun initData() {
        prepareFetchData()
    }

    @Deprecated("Use {@link FragmentTransaction#setMaxLifecycle}")
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (this.isVisibleToUser) {
            prepareFetchData()
        }
    }

    override fun prepareFetchData() {
        prepareFetchData(false)
    }

    override fun prepareFetchData(forceUpdate: Boolean) {
        //强制更新
        //(!isDataInitiated || forceUpdate) -> 执行刷新数据的两个条件:1.未初始化数据;2.强制刷新
        if (forceUpdate && isActivityCreated && isAdded) {
            initLazyData()
            isDataInitiated = true
            return
        }

//        Logger.d("123","prepareFetchData isActivityCreated " + isActivityCreated + "   isVisibleToUser  " + isVisibleToUser + "    isAdded()   " + isAdded()
//                + " isResumed() " + isResumed() + "  isVisible()   " + isVisible());

        //正常懒加载
        if (!isDataInitiated && isActivityCreated && isVisibleToUser) {
            //在该方法中初始化 Fragment 的数据<p>
            //initLayout -> initViews -> onActivityCreated -> initData -> prepareFetchData -> initLazyData
            initLazyData()
            isDataInitiated = true
        }
    }
}