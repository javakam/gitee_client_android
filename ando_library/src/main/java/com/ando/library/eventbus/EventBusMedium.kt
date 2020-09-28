package com.ando.library.eventbus

import android.os.Parcel
import android.os.Parcelable
import java.util.*

/**
 * EventBus 传输介质
 *
 * @author majavakam
 * @date 2019-05-25 14:18:11
 */
class EventBusMedium : Parcelable {
    var id: Int
    var what = 0
    var obj: Any? = null
    var obj1: Any? = null
    var obj2: Any? = null
    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o == null || javaClass != o.javaClass) {
            return false
        }
        val that = o as EventBusMedium
        return id == that.id && what == that.what &&
                obj == that.obj &&
                obj1 == that.obj1 &&
                obj2 == that.obj2
    }

    override fun hashCode(): Int {
        return Objects.hash(id, what, obj, obj1, obj2)
    }

    constructor(id: Int) {
        this.id = id
    }

    constructor(id: Int, what: Int) {
        this.id = id
        this.what = what
    }

    constructor(id: Int, what: Int, obj: Any?) {
        this.id = id
        this.what = what
        this.obj = obj
    }

    constructor(id: Int, what: Int, obj: Any?, obj1: Any?, obj2: Any?) {
        this.id = id
        this.what = what
        this.obj = obj
        this.obj1 = obj1
        this.obj2 = obj2
    }

    protected constructor(`in`: Parcel) {
        id = `in`.readInt()
        what = `in`.readInt()
        obj = `in`.readParcelable(Any::class.java.classLoader)
        obj1 = `in`.readParcelable(Any::class.java.classLoader)
        obj2 = `in`.readParcelable(Any::class.java.classLoader)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(id)
        dest.writeInt(what)
        dest.writeParcelable(obj as Parcelable?, flags)
        dest.writeParcelable(obj1 as Parcelable?, flags)
        dest.writeParcelable(obj2 as Parcelable?, flags)
    }

    class Builder {
        private var id = 0
        private var what = 0
        private var obj: Any? = null
        private var obj1: Any? = null
        private var obj2: Any? = null

        constructor() {}
        constructor(id: Int) {
            this.id = id
        }

        fun setId(id: Int): Builder {
            this.id = id
            return this
        }

        fun setWhat(what: Int): Builder {
            this.what = what
            return this
        }

        fun setObj(obj: Any?): Builder {
            this.obj = obj
            return this
        }

        fun setObj1(obj1: Any?): Builder {
            this.obj1 = obj1
            return this
        }

        fun setObj2(obj2: Any?): Builder {
            this.obj2 = obj2
            return this
        }

        fun build(): EventBusMedium {
            return EventBusMedium(id, what, obj, obj1, obj2)
        }
    }

    companion object {
        val CREATOR: Parcelable.Creator<EventBusMedium> =
            object : Parcelable.Creator<EventBusMedium?> {
                override fun createFromParcel(source: Parcel): EventBusMedium? {
                    return EventBusMedium(source)
                }

                override fun newArray(size: Int): Array<EventBusMedium?> {
                    return arrayOfNulls(size)
                }
            }
    }
}