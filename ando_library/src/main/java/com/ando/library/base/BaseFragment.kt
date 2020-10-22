package com.ando.library.base

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 * Title:BaseFragment
 *
 * Description:
 *
 * @author changbao
 * @date 2019/3/17 13:24
 */
abstract class BaseFragment : Fragment(), IBaseInterface {

    //注:不要命名为 activity 会和 Fragment.getActivity 冲突
    protected var baseActivity: BaseActivity? = null
        private set
    protected var rootView: View? = null
        private set

    //
    protected var isActivityCreated = false//Activity 是否已创建
    protected var isVisibleToUser = false //Fragment 是否对用户可见
    protected var isHiddenToUser = false //hidden

    override fun onAttach(context: Context) {
        super.onAttach(context)
        baseActivity = context as? BaseActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = if (getLayoutId() > 0) {
            inflater.inflate(getLayoutId(), container, false)
        } else {
            getLayoutView()
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
        baseActivity = null
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