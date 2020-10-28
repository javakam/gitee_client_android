package com.gitee.android.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/**
 * Title:
 * <p>
 * Description:
 * </p>
 * @author ChangBao
 * @date 2020/10/27  15:12
 */
abstract class BaseViewModel : ViewModel() {

    open fun launch(block: suspend () -> Unit, error: suspend (Throwable) -> Unit) =
        viewModelScope.launch {
            try {
                block()
            } catch (e: Throwable) {
                error(e)
            }
        }
}