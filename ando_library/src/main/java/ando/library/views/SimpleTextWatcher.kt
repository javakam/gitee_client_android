package ando.library.views

import android.text.Editable
import android.text.TextWatcher

/**
 * Title: SimpleTextWatcher
 *
 * Description:
 *      Simple implementation of the [TextWatcher] interface with stub
 * implementations of each method. Extend this if you do not intend to override
 * every method of [TextWatcher].
 *
 * @author javakam
 * @date 2018/11/2 9:54
 */
class SimpleTextWatcher : TextWatcher {
    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        // This space for rent
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        // This space for rent
    }

    override fun afterTextChanged(s: Editable) {
        // This space for rent
    }
}