package com.ando.library.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.ando.toolkit.StringUtils.noNull
import com.ando.toolkit.ext.ToastUtils

/**
 * Title:BaseFragment
 *
 * Description:
 *
 * @author changbao
 * @date 2019/3/17 13:24
 */
abstract class BaseFragment : Fragment(), IBaseInterface {

    protected var activity: BaseActivity? = null
    protected var rootView: View? = null

    //
    protected var isActivityCreated = false//Activity 是否已创建
    protected var isVisibleToUser = false //Fragment 是否对用户可见
    protected var isHiddenToUser = false //hidden

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as? BaseActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = if (layoutId > 0) {
            inflater.inflate(layoutId, container, false)
        } else {
            layoutView
        }
        initView(savedInstanceState)
        initListener()
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isActivityCreated = true
        initData()
    }

    @Deprecated("Use {@link FragmentTransaction#setMaxLifecycle}")
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        this.isVisibleToUser = isVisibleToUser
        //L.i("setUserVisibleHint isVisibleToUser ===> " + isVisibleToUser);
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        isHiddenToUser = hidden
        //L.i("onHiddenChanged hidden ===> " + hidden);
    }

    override fun onDetach() {
        super.onDetach()
        rootView = null
        activity = null
    }

    //https://stackoverflow.com/questions/22552958/handling-back-press-when-using-fragments-in-android
    fun controlBackPress() =
        view?.let { it ->
            it.isFocusableInTouchMode = true
            it.requestFocus()
            it.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
                if (event.action == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    // handle back button
                    // Toast.makeText(activity, "handle back button", Toast.LENGTH_SHORT).show();
                    onBackPressed()
                    return@OnKeyListener true
                }
                false
            })
        }

    fun onBackPressed() {}

}