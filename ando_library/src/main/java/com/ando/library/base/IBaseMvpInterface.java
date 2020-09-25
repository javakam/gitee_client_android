package com.ando.library.base;

public interface IBaseMvpInterface extends IBaseInterface {

    default void initMvp() {
    }

    void initPresenter();
}
