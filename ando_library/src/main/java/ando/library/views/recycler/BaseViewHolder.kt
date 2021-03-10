package ando.library.views.recycler

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.util.Linkify
import android.util.SparseArray
import android.view.View
import android.view.View.OnLongClickListener
import android.view.View.OnTouchListener
import android.view.animation.AlphaAnimation
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import java.util.*

/**
 * # BaseViewHolder
 *
 * github Joan Zapata
 */
open class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val views: SparseArray<View> = SparseArray()
    val childClickViewIds: HashSet<Int> = HashSet()
    val itemChildLongClickViewIds: HashSet<Int> = HashSet()
    var convertView: View = view

    /**
     * Package private field to retain the associated user object and detect a change
     *
     * set: Should be called during convert
     * get: Retrieves the last converted object on this view.
     */
    var associatedObject: Any? = null

    /**
     * 相对位置
     *
     * <pre>
     * 获取 DelegateAdapter 里数据的相对位置
     *      在 DelegateAdapter 里有 findOffsetPosition(int absolutePosition) 方法，传入整个页面的绝对位置，获取相对位置。
     * eg :  int offsetPosition = delegateAdapter.findOffsetPosition(1);
     *      或者用
     * public static abstract class Adapter<VH extends RecyclerView.VirtualViewHolder> extends RecyclerView.Adapter<VH> {
     *      public abstract LayoutHelper onCreateLayoutHelper();
     *          protected void onBindViewHolderWithOffset(VH holder, int position, int offsetTotal) {
     *          }
     * }
     * 中的 onBindViewHolderWithOffset() 方法代替传统的 onBindViewHolder() 方法，其中的 position 参数也是相对位置,offsetTotal 为绝对位置。
     * </pre>
     */
    var relativePosition = -1

    /**
     * 绝对位置
     */
    var absolutePosition = -1
    fun setText(viewId: Int, value: CharSequence?): BaseViewHolder {
        val view = getView<TextView>(viewId)
        view.text = value
        return this
    }

    fun setText(viewId: Int, @StringRes strId: Int): BaseViewHolder {
        val view = getView<TextView>(viewId)
        view.setText(strId)
        return this
    }

    fun setImageResource(viewId: Int, @DrawableRes imageResId: Int): BaseViewHolder {
        val view = getView<ImageView>(viewId)
        view.setImageResource(imageResId)
        return this
    }

    fun setBackgroundColor(viewId: Int, color: Int): BaseViewHolder {
        val view = getView<View>(viewId)
        view.setBackgroundColor(color)
        return this
    }

    fun setBackgroundRes(viewId: Int, @DrawableRes backgroundRes: Int): BaseViewHolder {
        val view = getView<View>(viewId)
        view.setBackgroundResource(backgroundRes)
        return this
    }

    fun setTextColor(viewId: Int, textColor: Int): BaseViewHolder {
        val view = getView<TextView>(viewId)
        view.setTextColor(textColor)
        return this
    }

    fun setImageDrawable(viewId: Int, drawable: Drawable?): BaseViewHolder {
        val view = getView<ImageView>(viewId)
        view.setImageDrawable(drawable)
        return this
    }

    fun setImageBitmap(viewId: Int, bitmap: Bitmap?): BaseViewHolder {
        val view = getView<ImageView>(viewId)
        view.setImageBitmap(bitmap)
        return this
    }

    @SuppressLint("ObsoleteSdkInt")
    fun setAlpha(viewId: Int, value: Float): BaseViewHolder {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getView<View>(viewId).alpha = value
        } else {
            // Pre-honeycomb hack to set Alpha value
            val alpha = AlphaAnimation(value, value)
            alpha.duration = 0
            alpha.fillAfter = true
            getView<View>(viewId).startAnimation(alpha)
        }
        return this
    }

    fun setVisible(viewId: Int, visible: Boolean): BaseViewHolder {
        val view = getView<View>(viewId)
        view.visibility = if (visible) View.VISIBLE else View.GONE
        return this
    }

    fun linkify(viewId: Int): BaseViewHolder {
        val view = getView<TextView>(viewId)
        Linkify.addLinks(view, Linkify.ALL)
        return this
    }

    fun setTypeface(viewId: Int, typeface: Typeface?): BaseViewHolder {
        val view = getView<TextView>(viewId)
        view.typeface = typeface
        view.paintFlags = view.paintFlags or Paint.SUBPIXEL_TEXT_FLAG
        return this
    }

    fun setTypeface(typeface: Typeface?, vararg viewIds: Int): BaseViewHolder {
        for (viewId in viewIds) {
            val view = getView<TextView>(viewId)
            view.typeface = typeface
            view.paintFlags = view.paintFlags or Paint.SUBPIXEL_TEXT_FLAG
        }
        return this
    }

    fun setProgress(viewId: Int, progress: Int): BaseViewHolder {
        val view = getView<ProgressBar>(viewId)
        view.progress = progress
        return this
    }

    fun setProgress(viewId: Int, progress: Int, max: Int): BaseViewHolder {
        val view = getView<ProgressBar>(viewId)
        view.max = max
        view.progress = progress
        return this
    }

    fun setMax(viewId: Int, max: Int): BaseViewHolder {
        val view = getView<ProgressBar>(viewId)
        view.max = max
        return this
    }

    fun setRating(viewId: Int, rating: Float): BaseViewHolder {
        val view = getView<RatingBar>(viewId)
        view.rating = rating
        return this
    }

    fun setRating(viewId: Int, rating: Float, max: Int): BaseViewHolder {
        val view = getView<RatingBar>(viewId)
        view.max = max
        view.rating = rating
        return this
    }

    fun addOnClickListener(viewId: Int): BaseViewHolder {
        childClickViewIds.add(viewId)
        return this
    }

    fun addOnLongClickListener(viewId: Int): BaseViewHolder {
        itemChildLongClickViewIds.add(viewId)
        return this
    }

    fun setOnTouchListener(viewId: Int, listener: OnTouchListener?): BaseViewHolder {
        val view = getView<View>(viewId)
        view.setOnTouchListener(listener)
        return this
    }

    fun setOnLongClickListener(viewId: Int, listener: OnLongClickListener?): BaseViewHolder {
        val view = getView<View>(viewId)
        view.setOnLongClickListener(listener)
        return this
    }

    fun setOnItemClickListener(
        viewId: Int,
        listener: AdapterView.OnItemClickListener?
    ): BaseViewHolder {
        val view = getView<AdapterView<*>>(viewId)
        view.onItemClickListener = listener
        return this
    }

    fun setOnItemLongClickListener(
        viewId: Int,
        listener: AdapterView.OnItemLongClickListener?
    ): BaseViewHolder {
        val view = getView<AdapterView<*>>(viewId)
        view.onItemLongClickListener = listener
        return this
    }

    fun setOnItemSelectedClickListener(
        viewId: Int,
        listener: OnItemSelectedListener?
    ): BaseViewHolder {
        val view = getView<AdapterView<*>>(viewId)
        view.onItemSelectedListener = listener
        return this
    }

    fun setOnCheckedChangeListener(
        viewId: Int,
        listener: CompoundButton.OnCheckedChangeListener?
    ): BaseViewHolder {
        val view = getView<CompoundButton>(viewId)
        view.setOnCheckedChangeListener(listener)
        return this
    }

    fun setTag(viewId: Int, tag: Any?): BaseViewHolder {
        val view = getView<View>(viewId)
        view.tag = tag
        return this
    }

    fun setTag(viewId: Int, key: Int, tag: Any?): BaseViewHolder {
        val view = getView<View>(viewId)
        view.setTag(key, tag)
        return this
    }

    fun setChecked(viewId: Int, checked: Boolean): BaseViewHolder {
        val view = getView<View>(viewId)
        // View unable cast to Checkable
        if (view is CompoundButton) {
            view.isChecked = checked
        } else if (view is CheckedTextView) {
            view.isChecked = checked
        }
        return this
    }

    fun setAdapter(viewId: Int, adapter: Adapter?): BaseViewHolder {
        val view = getView<AdapterView<Adapter>>(viewId)
        view.adapter = adapter
        return this
    }

    fun <T : View?> getView(viewId: Int): T {
        var view = views[viewId]
        if (view == null) {
            view = convertView.findViewById(viewId)
            views.put(viewId, view)
        }
        @Suppress("UNCHECKED_CAST")
        return view as T
    }

}