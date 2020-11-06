package com.gitee.android.utils

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigator
import java.util.*

@Navigator.Name("fragment")
class FixFragmentNavigator(context: Context, manager: FragmentManager, containerId: Int) :
    FragmentNavigator(context, manager, containerId) {

    private val mContext = context
    private val mManager = manager
    private val mContainerId = containerId

    override fun navigate(
        destination: Destination,
        args: Bundle?,
        navOptions: NavOptions?,
        navigatorExtras: Navigator.Extras?
    ): NavDestination? {
        if (mManager.isStateSaved) {
            Log.i(
                "123",
                "Ignoring navigate() call: FragmentManager has already" + " saved its state"
            )
            return null
        }
        var className = destination.className
        if (className[0] == '.') {
            className = mContext.packageName + className
        }

        val ft = mManager.beginTransaction()
        var enterAnim = navOptions?.enterAnim ?: -1
        var exitAnim = navOptions?.exitAnim ?: -1
        var popEnterAnim = navOptions?.popEnterAnim ?: -1
        var popExitAnim = navOptions?.popExitAnim ?: -1
        if (enterAnim != -1 || exitAnim != -1 || popEnterAnim != -1 || popExitAnim != -1) {
            enterAnim = if (enterAnim != -1) enterAnim else 0
            exitAnim = if (exitAnim != -1) exitAnim else 0
            popEnterAnim = if (popEnterAnim != -1) popEnterAnim else 0
            popExitAnim = if (popExitAnim != -1) popExitAnim else 0
            ft.setCustomAnimations(enterAnim, exitAnim, popEnterAnim, popExitAnim)
        }

        val fragment = mManager.primaryNavigationFragment
        if (fragment != null) {
            ft.hide(fragment)
        }
        var frag: Fragment?
        val tag = destination.id.toString()
        frag = mManager.findFragmentByTag(tag)
        if (frag != null) {
            ft.show(frag)
        } else {
            frag = mManager.fragmentFactory.instantiate(mContext.classLoader, className)
            frag.arguments = args
            ft.add(mContainerId, frag, tag)
        }
        ft.setPrimaryNavigationFragment(frag)

        @IdRes val destId = destination.id
        //通过反射的方式获取 mBackStack
        val field = FragmentNavigator::class.java.getDeclaredField("mBackStack")
        field.isAccessible = true
        val mBackStack: ArrayDeque<Int> = field.get(this) as ArrayDeque<Int>

        val initialNavigation = mBackStack.isEmpty()
        // TODO Build first class singleTop behavior for fragments
        val isSingleTopReplacement = (navOptions != null && !initialNavigation
                && navOptions.shouldLaunchSingleTop()
                && mBackStack.peekLast() == destId)

        val isAdded: Boolean
        when {
            initialNavigation -> {
                isAdded = true
            }
            isSingleTopReplacement -> {
                // Single Top means we only want one instance on the back stack
                if (mBackStack.size > 1) {
                    // If the Fragment to be replaced is on the FragmentManager's
                    // back stack, a simple replace() isn't enough so we
                    // remove it from the back stack and put our replacement
                    // on the back stack in its place
                    mManager.popBackStack(
                        generateBackStackName(mBackStack.size, mBackStack.peekLast() ?: 0),
                        FragmentManager.POP_BACK_STACK_INCLUSIVE
                    )
                    ft.addToBackStack(generateBackStackName(mBackStack.size, destId))
                }
                isAdded = false
            }
            else -> {
                ft.addToBackStack(generateBackStackName(mBackStack.size + 1, destId))
                isAdded = true
            }
        }
        if (navigatorExtras is Extras) {
            val extras = navigatorExtras as Extras?
            for ((key, value) in extras!!.sharedElements) {
                ft.addSharedElement(key, value)
            }
        }
        ft.setReorderingAllowed(true)
        //ft.commit()
        ft.commitAllowingStateLoss()

        // The commit succeeded, update our view of the world
        return if (isAdded) {
            mBackStack.add(destId)
            destination
        } else null
    }


    /**
     * 在父类是 private的  直接定义一个方法即可
     */
    private fun generateBackStackName(backIndex: Int, destId: Int): String {
        return "$backIndex-$destId"
    }

}