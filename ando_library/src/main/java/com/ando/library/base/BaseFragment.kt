package com.ando.library.base

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.ando.toolkit.ext.otherwise
import com.ando.toolkit.ext.yes

/**
 * Title:BaseFragment
 *
 * Description:
 *
 * @author javakam
 * @date 2019/3/17 13:24
 */
abstract class BaseFragment : Fragment() {

    //注:不要命名为 activity 会和 Fragment.getActivity 冲突
    protected lateinit var baseActivity: BaseActivity
        private set

    //
    var isActivityCreated = false//Activity 是否已创建
    var isVisibleToUser = false //Fragment 是否对用户可见
    var isHiddenToUser = false //hidden

    override fun onAttach(context: Context) {
        super.onAttach(context)
        baseActivity = context as BaseActivity
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isActivityCreated = true
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

abstract class BaseMvcFragment : BaseFragment(), IBaseInterface {

    protected lateinit var rootView: View
        private set

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = (getLayoutId() > 0).yes {
            inflater.inflate(getLayoutId(), container, false)
        }.otherwise {
            getLayoutView()!!
        }
        initView(savedInstanceState)
        initListener()
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initData()
    }

}

abstract class BaseMvvmFragment<T : ViewDataBinding> : BaseFragment() {

    abstract val layoutId: Int
    lateinit var binding: T
    abstract fun initView(root: View, savedInstanceState: Bundle?)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        binding.lifecycleOwner = this
        binding.executePendingBindings()
        initView(binding.root,savedInstanceState)
        return binding.root
    }

//    override fun onViewCreated(root: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(root, savedInstanceState)
//        initView(root,savedInstanceState)
//    }


}