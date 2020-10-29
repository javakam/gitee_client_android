package com.ando.library.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/**
 * Title: BaseViewModel
 * <p>
 * Description:
 * </p>
 * @author javakam
 * @date 2020/10/29  9:48
 */
abstract class BaseViewModel :ViewModel(){

    open fun launch(block: suspend () -> Unit, error: suspend (Throwable) -> Unit) =
        viewModelScope.launch {
            try {
                block()
            } catch (e: Throwable) {
                error(e)
            }
        }
}