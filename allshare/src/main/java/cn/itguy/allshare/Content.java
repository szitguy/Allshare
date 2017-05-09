package cn.itguy.allshare;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yelongfei490 on 2016/11/25.
 */

public abstract class Content implements Parcelable {

    public interface Type {

        int TEXT = 0;

        int IMAGE = 1;

        int LINK = 2;
    }

    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.type);
    }

    public Content() {
    }

    protected Content(Parcel in) {
        this.type = in.readInt();
    }

}
