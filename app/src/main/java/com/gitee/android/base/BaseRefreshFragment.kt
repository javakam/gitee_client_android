package com.gitee.android.base

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ando.library.base.BaseLazyFragment
import com.ando.library.views.loader.LoadState
import com.ando.library.views.loader.Loader.OnReloadListener
import com.ando.toolkit.log.L.i
import com.gitee.android.R
import com.gitee.android.common.defaultPageConfig
import com.gitee.android.view.LoaderView
import com.scwang.smart.refresh.layout.api.RefreshLayout
import java.io.Serializable

/**
 * Title: BaseRefreshFragment
 *
 *
 * Description:
 * onLoadMore 传入false表示刷新失败
 * mRefreshLayout.finishRefresh(2000,false) or mRefreshLayout.finishLoadMore(2000,false);
 *
 * @author javakam
 * @date 2019/11/18  15:27
 */
abstract class BaseRefreshFragment<E : Serializable?> : BaseLazyFragment(), IRefreshCallBack<E>,
    IPageLazyRefresh {

    //Common View
    protected var rootView: View? = null
    protected var mLoaderView: LoaderView? = null
    protected var mRefreshLayout: RefreshLayout? = null
    protected var mRecycler: RecyclerView? = null

    //todo 2019-11-18 16:00:59 此处耦合 BaseQuickAdapter
    protected var mAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder?>? = null

    //
    protected var mData: List<E>? = ArrayList<E>()

    //配置文件
    private var mPageConfig: PageConfig? = null

    //刷新
    protected var mPullUpNum = 1
    protected var mPullUpNumLast = 1

    //刷新手势动作
    protected var isPullDown = true

    private val config: PageConfig = mPageConfig ?: defaultPageConfig

    /**
     * 懒加载会在 initViews 之前执行,所以要优先初始化 PageConfig
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mPageConfig = initConfig()
    }

    override fun initConfig(): PageConfig? {
        return defaultPageConfig
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rootView = view
        if (config.haveLoader) {
            mLoaderView = rootView?.findViewById(R.id.stateLayout)
            mLoaderView?.setOnReloadListener(object : OnReloadListener {
                override fun onReload() {
                    retry()
                }
            })
        }
        initRefresh()

        //初始化 RecyclerView (列表数据情况)
        if (config.haveRecyclerView) {
            mRecycler = rootView?.findViewById(R.id.refreshRecycler)
            if (mRecycler != null) {
                if (!config.usedVLayout) {
                    mRecycler?.setHasFixedSize(true)
                    mRecycler?.itemAnimator = null
                    mRecycler?.layoutManager =
                        LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                }
                initRecyclerView(mRecycler)
            }
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            initImmersionBar()
        }
    }

    /**
     * 沉浸式状态栏
     */
    protected fun initImmersionBar() {
//        ImmersionBar.with(this)
//                .titleBar(view)
//                .keyboardEnable(false)
//                .statusBarDarkFont(false)
//                .init();
    }

    fun initRefresh() {
        // EApplication 中也有配置
        mRefreshLayout = rootView?.findViewById<View>(R.id.refreshLayout) as RefreshLayout
        val enableRefresh = config.enableRefresh
        val enableLoadMore = config.enableLoadMore
        if (config.haveRecyclerView) {
            mRefreshLayout?.setEnableRefresh(enableRefresh)
            mRefreshLayout?.setEnableLoadMore(enableLoadMore)
        }
        if (enableRefresh) {
            mRefreshLayout?.setOnRefreshListener(this)
        }
        mRefreshLayout?.setEnableLoadMore(enableLoadMore)
        if (enableLoadMore) {
            mRefreshLayout?.setEnableFooterFollowWhenNoMoreData(false) // setEnableFooterFollowWhenLoadFinished 设置是否在没有更多数据之后 Footer 跟随内容
            mRefreshLayout?.setOnLoadMoreListener(this)
        }
        if (enableRefresh || enableLoadMore) {
            initRefresh(mRefreshLayout)
        }
    }

    override fun prepareFetchData(forceUpdate: Boolean) {
        if (config.enableLazyLoading) {
            super.prepareFetchData(forceUpdate)
        } else {
            initLazyData()
        }
    }

    fun initRecyclerView(recyclerView: RecyclerView?) {}
    fun addData(data: List<E>?) {}
    fun replaceData(data: List<E>?) {}
    fun clearData() {}
    abstract fun refresh()
    abstract fun more()
    fun retry() {
        mPullUpNumLast = 1
        mPullUpNum = 1
        refresh()
        //prepareFetchData(true);
        //or
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        isPullDown = true
        if (config.usedVLayout) {
            clearData()
        }
        retry()
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        isPullDown = false
        //列表为空时,不开启加载更多
        if (mData.isNullOrEmpty()) {
            initLazyData()
            return
        }
        mPullUpNumLast = mPullUpNum
        ++mPullUpNum
        more()
    }

    override fun onRefreshSuccess(data: List<E>?) {
        //L.i(getClass().getSimpleName() + "  onRefreshSuccess  " + (ListUtils.isEmpty(data) ? data : data.size()));
        mData = data
        mPullUpNumLast = 1
        mPullUpNum = 1
        if (mData.isNullOrEmpty()) {
            if (config.haveLoader && mLoaderView != null) {
                mLoaderView?.setLoadState(LoadState.EMPTY)
            }
            if (config.enableLoadMore) {
                mRefreshLayout?.setEnableLoadMore(false)
            }
            mRefreshLayout?.finishRefresh()
            if (config.enableLoadMore) {
                mRefreshLayout?.finishRefreshWithNoMoreData() //将不会再次触发加载更多事件
            }
            //mRefreshLayout.finishRefresh(false);
            i("loadData: onRefreshSuccess")
        } else {
            if (config.usedVLayout) {
                i("loadData: usedVLayout " + config.usedVLayout + "  " + mData?.size)
                replaceData(data)
            } else {
                if (config.haveRecyclerView && mAdapter != null) {
                    //todo 2020年10月28日 16:22:30
                    // mAdapter.replaceData(data)
                }
            }
            if (config.haveLoader && mLoaderView != null) {
                mLoaderView?.setLoadState(LoadState.SUCCESS)
            }
            mRefreshLayout?.setEnableLoadMore(config.enableLoadMore)
            mRefreshLayout?.finishRefresh()
            if (config.enableLoadMore) {
                mRefreshLayout?.finishLoadMore()
                mRefreshLayout?.resetNoMoreData() // setNoMoreData(false);
            }
        }
    }

    override fun onLoadMoreSuccess(data: List<E>?) {
        if (data.isNullOrEmpty()) {
            mPullUpNum = mPullUpNumLast
            mRefreshLayout?.finishLoadMoreWithNoMoreData() //"数据全部加载完毕" ,将不会再次触发加载更多事件
        } else {
            //todo
//            mData.addAll(data)

            //mPullUpNum++;
            mPullUpNumLast = mPullUpNum
            if (config.usedVLayout) {
                addData(data)
            } else {
                if (config.haveRecyclerView && mAdapter != null) {
                    //todo
//                    mAdapter.addData(data)
                }
            }
            mRefreshLayout?.finishLoadMore()
            i("loadMoreData: mData=" + mData?.size + "  " + data?.size)
        }
    }

    override fun onRefreshFailed(errMsg: String?) {
        i(javaClass.simpleName + "  onRefreshFailed  " + errMsg)
        //下拉加载更多(仿 Bilibili)
        //mPullUpNum = mPullUpNumLast;
        mPullUpNumLast = 1
        mPullUpNum = 1
        mRefreshLayout?.finishRefresh(false)
        if (config.haveLoader && mLoaderView != null) {
            mLoaderView?.setLoadState(LoadState.ERROR)
        }
        if (config.enableLoadMore) {
            mRefreshLayout?.setEnableLoadMore(false)
        }
    }

    override fun onLoadMoreFailed(errMsg: String?) {
        i("loadMoreData: onLoadMoreFailed $errMsg")
        mPullUpNum = mPullUpNumLast
        //传入false表示加载更多失败
        mRefreshLayout?.finishLoadMore(false)
        //mRefreshLayout.finishLoadMore(0, false, false);
    }

}