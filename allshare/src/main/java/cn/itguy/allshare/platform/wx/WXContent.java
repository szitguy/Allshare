package cn.itguy.allshare.platform.wx;

import android.graphics.Bitmap;
import android.os.Parcel;
import cn.itguy.allshare.Content;

/**
 * Created by yelongfei490 on 2016/11/25.
 */

public class WXContent extends Content {

    private String title;
    private Bitmap thumb;
    private String description;
    private String text;
    private Bitmap image;
    private String link;
    private boolean timeline;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Bitmap getThumb() {
        return thumb;
    }

    public void setThumb(Bitmap thumb) {
        this.thumb = thumb;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public boolean isTimeline() {
        return timeline;
    }

    public void setTimeline(boolean timeline) {
        this.timeline = timeline;
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.title);
        dest.writeParcelable(this.thumb, flags);
        dest.writeString(this.description);
        dest.writeString(this.text);
        dest.writeParcelable(this.image, flags);
        dest.writeString(this.link);
        dest.writeByte(this.timeline ? (byte) 1 : (byte) 0);
    }

    public WXContent() {
    }

    protected WXContent(Parcel in) {
        super(in);
        this.title = in.readString();
        this.thumb = in.readParcelable(Bitmap.class.getClassLoader());
        this.description = in.readString();
        this.text = in.readString();
        this.image = in.readParcelable(Bitmap.class.getClassLoader());
        this.link = in.readString();
        this.timeline = in.readByte() != 0;
    }

    public static final Creator<WXContent> CREATOR = new Creator<WXContent>() {
        @Override public WXContent createFromParcel(Parcel source) {
            return new WXContent(source);
        }

        @Override public WXContent[] newArray(int size) {
            return new WXContent[size];
        }
    };
}
