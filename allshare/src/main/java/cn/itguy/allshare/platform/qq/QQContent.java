package cn.itguy.allshare.platform.qq;

import android.os.Parcel;
import cn.itguy.allshare.Content;

/**
 * Created by yelongfei490 on 2016/11/25.
 */

public class QQContent extends Content {

    private boolean qzone;
    private String title;
    private String targetUrl;
    private String summary;
    private String imageUrl;

    public boolean isQzone() {
        return qzone;
    }

    public void setQzone(boolean qzone) {
        this.qzone = qzone;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeByte(this.qzone ? (byte) 1 : (byte) 0);
        dest.writeString(this.title);
        dest.writeString(this.targetUrl);
        dest.writeString(this.summary);
        dest.writeString(this.imageUrl);
    }

    public QQContent() {
    }

    protected QQContent(Parcel in) {
        super(in);
        this.qzone = in.readByte() != 0;
        this.title = in.readString();
        this.targetUrl = in.readString();
        this.summary = in.readString();
        this.imageUrl = in.readString();
    }

    public static final Creator<QQContent> CREATOR = new Creator<QQContent>() {
        @Override public QQContent createFromParcel(Parcel source) {
            return new QQContent(source);
        }

        @Override public QQContent[] newArray(int size) {
            return new QQContent[size];
        }
    };
}
