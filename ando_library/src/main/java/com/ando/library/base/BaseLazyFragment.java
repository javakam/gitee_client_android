package com.ando.library.base;

import androidx.fragment.app.FragmentTransaction;

/**
 * Title:BaseLazyFragment
 * <p>
 * Description:
 * </p>
 *
 * @author javakam
 * @date 2019/3/17 13:24
 */
public abstract class BaseLazyFragment extends BaseFragment implements IBaseLazyInterface {

    protected boolean isDataInitiated;      //Fragment 是否已加载过数据


    /**
     * After
     * <pre>
     *     @Override
     *     public void onActivityCreated(Bundle savedInstanceState) {
     *         super.onActivityCreated(savedInstanceState);
     *          initData() ;
     *     }
     * </pre>
     * {@link com.ando.library.base.BaseFragment#onActivityCreated}
     */
    @Override
    public void initData() {
        prepareFetchData();
    }

    /**
     * @deprecated Use {@link FragmentTransaction#setMaxLifecycle}
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (this.isVisibleToUser) {
            prepareFetchData();
        }
    }

    @Override
    public void prepareFetchData() {
        prepareFetchData(false);
    }

    @Override
    public void prepareFetchData(boolean forceUpdate) {
        //强制更新
        //(!isDataInitiated || forceUpdate) -> 执行刷新数据的两个条件:1.未初始化数据;2.强制刷新
        if (forceUpdate && isActivityCreated && isAdded()) {
            initLazyData();
            isDataInitiated = true;
            return;
        }

//        Logger.d("123","prepareFetchData isActivityCreated " + isActivityCreated + "   isVisibleToUser  " + isVisibleToUser + "    isAdded()   " + isAdded()
//                + " isResumed() " + isResumed() + "  isVisible()   " + isVisible());

        //正常懒加载
        if (!isDataInitiated && isActivityCreated && isVisibleToUser) {
            //在该方法中初始化 Fragment 的数据<p>
            //initLayout -> initViews -> onActivityCreated -> initData -> prepareFetchData -> initLazyData
            initLazyData();
            isDataInitiated = true;
        }
    }


}