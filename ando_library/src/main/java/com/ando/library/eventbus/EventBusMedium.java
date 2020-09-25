package com.ando.library.eventbus;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

/**
 * EventBus 传输介质
 *
 * @author majavakam
 * @date 2019-05-25 14:18:11
 */
public class EventBusMedium implements Parcelable {
    private int id;
    private int what;
    private Object obj;
    private Object obj1;
    private Object obj2;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EventBusMedium that = (EventBusMedium) o;
        return id == that.id &&
                what == that.what &&
                Objects.equals(obj, that.obj) &&
                Objects.equals(obj1, that.obj1) &&
                Objects.equals(obj2, that.obj2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, what, obj, obj1, obj2);
    }

    public EventBusMedium(int id) {
        this.id = id;
    }

    public EventBusMedium(int id, int what) {
        this.id = id;
        this.what = what;
    }

    public EventBusMedium(int id, int what, Object obj) {
        this.id = id;
        this.what = what;
        this.obj = obj;
    }

    public EventBusMedium(int id, int what, Object obj, Object obj1, Object obj2) {
        this.id = id;
        this.what = what;
        this.obj = obj;
        this.obj1 = obj1;
        this.obj2 = obj2;
    }

    protected EventBusMedium(Parcel in) {
        this.id = in.readInt();
        this.what = in.readInt();
        this.obj = in.readParcelable(Object.class.getClassLoader());
        this.obj1 = in.readParcelable(Object.class.getClassLoader());
        this.obj2 = in.readParcelable(Object.class.getClassLoader());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWhat() {
        return what;
    }

    public void setWhat(int what) {
        this.what = what;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public Object getObj1() {
        return obj1;
    }

    public void setObj1(Object obj1) {
        this.obj1 = obj1;
    }

    public Object getObj2() {
        return obj2;
    }

    public void setObj2(Object obj2) {
        this.obj2 = obj2;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.what);
        dest.writeParcelable((Parcelable) this.obj, flags);
        dest.writeParcelable((Parcelable) this.obj1, flags);
        dest.writeParcelable((Parcelable) this.obj2, flags);
    }

    public static final Creator<EventBusMedium> CREATOR = new Creator<EventBusMedium>() {
        @Override
        public EventBusMedium createFromParcel(Parcel source) {
            return new EventBusMedium(source);
        }

        @Override
        public EventBusMedium[] newArray(int size) {
            return new EventBusMedium[size];
        }
    };

    public static class Builder {
        private int id;
        private int what;
        private Object obj;
        private Object obj1;
        private Object obj2;

        public Builder() {
        }

        public Builder(int id) {
            this.id = id;
        }

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setWhat(int what) {
            this.what = what;
            return this;
        }

        public Builder setObj(Object obj) {
            this.obj = obj;
            return this;
        }

        public Builder setObj1(Object obj1) {
            this.obj1 = obj1;
            return this;
        }

        public Builder setObj2(Object obj2) {
            this.obj2 = obj2;
            return this;
        }

        public EventBusMedium build() {
            return new EventBusMedium(id, what, obj, obj1, obj2);
        }
    }
}
