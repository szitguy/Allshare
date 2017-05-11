package cn.itguy.allshare.sample;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;
import cn.itguy.allshare.AllShare;
import cn.itguy.allshare.Callback;
import cn.itguy.allshare.Content;
import cn.itguy.allshare.platform.qq.QQContent;
import cn.itguy.allshare.platform.wx.WXContent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {

    public static final String ALL_SHARE_GITHUB = "https://github.com/szitguy/Allshare";

    private static final String SHARE_ICON_NAME = "share.png";
    private static final File ICON_FILE = new File(SampleApplication.instance.getExternalFilesDir(
            Environment.DIRECTORY_PICTURES), SHARE_ICON_NAME);
    private static final String TITLE = "AllShare";
    private static final String DESC = "封装常用分享平台的sdk，统一并简化各分享sdk使用，提升分享功能的开发速度";

    CheckBox momentCheckBox;
    CheckBox qzoneCheckBox;

    private Bitmap icon;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        momentCheckBox = (CheckBox) findViewById(R.id.shareToMomentCheckBox);
        qzoneCheckBox = (CheckBox) findViewById(R.id.shareToQzoneCheckBox);

        icon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        if (!ICON_FILE.exists()) {
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(ICON_FILE);
                icon.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 微信分享文本
     * @param view
     */
    public void onWxShareTextButtonClick(View view) {
        WXContent content = new WXContent();
        content.setType(Content.Type.TEXT);
        content.setTimeline(momentCheckBox.isChecked());
        content.setText(String.format("%s --- %s", ALL_SHARE_GITHUB, TITLE));
        content.setDescription(DESC);
        AllShare.share(this, content, new ShareCallback(this));
    }

    /**
     * 微信分享链接
     * @param view
     */
    public void onWxShareLinkButtonClick(View view) {
        WXContent content = new WXContent();
        content.setType(Content.Type.LINK);
        content.setTimeline(momentCheckBox.isChecked());
        content.setTitle(TITLE);
        content.setDescription(DESC);
        content.setLink(ALL_SHARE_GITHUB);
        content.setThumb(icon);
        AllShare.share(this, content, new ShareCallback(this));
    }

    /**
     * 微信分享图片
     * @param view
     */
    public void onWxShareImageButtonClick(View view) {
        WXContent content = new WXContent();
        content.setType(Content.Type.IMAGE);
        content.setTimeline(momentCheckBox.isChecked());
        content.setImage(icon);
        content.setThumb(icon);
        AllShare.share(this, content, new ShareCallback(this));
    }

    /**
     * QQ分享链接
     * @param view
     */
    public void onQQShareLinkButtonClick(View view) {
        QQContent content = new QQContent();
        content.setType(Content.Type.LINK);
        content.setImageUrl(ICON_FILE.getAbsolutePath()); // 本地or网络图片，可选
        content.setQzone(qzoneCheckBox.isChecked());
        content.setTitle(TITLE);
        content.setTargetUrl(ALL_SHARE_GITHUB);
        content.setSummary(DESC); // 可选
        AllShare.share(this, content, new ShareCallback(this));
    }

    /**
     * QQ分享图片
     * @param view
     */
    public void onQQShareImageButtonClick(View view) {
        if (qzoneCheckBox.isChecked()) {
            Toast.makeText(this, "Qzone不支持图片分享", Toast.LENGTH_SHORT).show();
            return;
        }
        QQContent content = new QQContent();
        content.setType(Content.Type.IMAGE);
        content.setImageUrl(ICON_FILE.getAbsolutePath()); // 必须为本地图片
        AllShare.share(this, content, new ShareCallback(this));
    }

    private static class ShareCallback implements Callback {

        WeakReference<Context> contextWeakRef;

        public ShareCallback(Context context) {
            contextWeakRef = new WeakReference<>(context);
        }

        @Override public void onSuccess() {
            Context context = contextWeakRef.get();
            if (null != context) {

                Toast.makeText(context, "分享成功", Toast.LENGTH_SHORT).show();
            }
        }

        @Override public void onFailed() {
            Context context = contextWeakRef.get();
            if (null != context) {

                Toast.makeText(context, "分享失败", Toast.LENGTH_SHORT).show();
            }
        }

        @Override public void onCanceled() {
            Context context = contextWeakRef.get();
            if (null != context) {

                Toast.makeText(context, "取消分享", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
