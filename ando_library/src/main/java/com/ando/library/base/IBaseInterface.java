package com.ando.library.base;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

public interface IBaseInterface {

    void initView(@Nullable Bundle savedInstanceState);

    default void initListener() {
    }

    default void initData() {
    }

    default View getLayoutView() {
        return null;
    }

    int getLayoutId();

}