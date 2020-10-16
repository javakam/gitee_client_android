package com.gitee.android.common

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.ando.library.base.BaseFragment
import com.gitee.android.R

/**
 * 切换 底部Fragment
 * <p>
 * 注意 ID 必须为 :  transaction.add(R.id.main_container, fragment)
 */
fun switchFragment(
    manager: FragmentManager,
    fragments: MutableList<BaseFragment>,
    index: Int
) {
    if (fragments.isNullOrEmpty()) return
    val transaction: FragmentTransaction = manager.beginTransaction()
    var fragment: Fragment
    var i = 0
    val j: Int = fragments.size
    if (index >= j) {
        return
    }
    while (i < j) {
        if (i == index) {
            i++
            continue
        }
        fragment = fragments[i]
        if (fragment.isAdded) {
            transaction.hide(fragment)
        }
        i++
    }
    fragment = fragments[index]
    if (fragment.isAdded) {
        transaction.show(fragment)
    } else {
        transaction.add(R.id.main_container, fragment)
    }

    //简单的渐变动画
    //transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

    transaction.commitAllowingStateLoss()
    //Fixed Bug : java.lang.IllegalStateException: Fragment already added: BlogListFragment{2a95f57f (2206a881-37b5-4861-89c6-c584505baff7) id=0x7f0800ad}
    //https://stackoverflow.com/questions/19441932/java-lang-illegalstateexception-fragment-already-added
    //manager.executePendingTransactions()
}