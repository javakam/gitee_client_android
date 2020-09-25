package com.ando.library.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.ando.toolkit.StringUtils;
import com.ando.toolkit.ext.ToastUtils;

/**
 * Title:BaseFragment
 * <p>
 * Description:
 * </p>
 *
 * @author javakam
 * @date 2019/3/17 13:24
 */
public abstract class BaseFragment extends Fragment implements IBaseInterface {

    protected BaseActivity activity;
    protected View rootView;
    //
    protected boolean isActivityCreated;    //Activity 是否已创建
    protected boolean isVisibleToUser;      //Fragment 是否对用户可见
    protected boolean isHidden;               //hidden

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (BaseActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getLayoutId() > 0) {
            rootView = inflater.inflate(getLayoutId(), container, false);
        } else {
            rootView = getLayoutView();
        }

        initView(savedInstanceState);
        initListener();
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isActivityCreated = true;
        initData();
    }

    /**
     * @deprecated Use {@link FragmentTransaction#setMaxLifecycle}
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        //L.i("setUserVisibleHint isVisibleToUser ===> " + isVisibleToUser);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        this.isHidden = hidden;
        //L.i("onHiddenChanged hidden ===> " + hidden);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        rootView = null;
        activity = null;
    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(cls, null, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(activity, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivity(Class<?> cls, Bundle bundle) {
        if (getActivity() != null) {
            Intent intent = new Intent();
            intent.setClass(activity, cls);
            if (bundle != null) {
                intent.putExtras(bundle);
            }
            startActivity(intent);
        }
    }

    public void shortToast(@StringRes int text) {
        ToastUtils.shortToast(StringUtils.noNull(getString(text)));
    }

    public void shortToast(String text) {
        ToastUtils.shortToast(StringUtils.noNull(text));
    }

    public void longToast(@StringRes int text) {
        ToastUtils.longToast(StringUtils.noNull(getString(text)));
    }

    public void longToast(String text) {
        ToastUtils.longToast(StringUtils.noNull(text));
    }

    public void controlBackPress() {
        //https://stackoverflow.com/questions/22552958/handling-back-press-when-using-fragments-in-android
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    // handle back button
                    // Toast.makeText(activity, "handle back button", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                    return true;
                }
                return false;
            }
        });
    }

    public void onBackPressed() {
    }

}