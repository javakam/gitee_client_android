package com.ando.library.base;

public interface IBaseLazyInterface extends IBaseInterface {

    /**
     * (!isDataInitiated || forceUpdate) -> 执行刷新数据的两个条件:1.未初始化数据;2.强制刷新
     */
    default void prepareFetchData() {
    }

    /**
     * (!isDataInitiated || forceUpdate) -> 执行刷新数据的两个条件:1.未初始化数据;2.强制刷新
     */
    default void prepareFetchData(boolean forceUpdate) {
    }

    void initLazyData();

}