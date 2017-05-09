package cn.itguy.allshare.sample;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import cn.itguy.allshare.AllShare;
import cn.itguy.allshare.Callback;
import cn.itguy.allshare.Content;
import cn.itguy.allshare.platform.qq.QQContent;
import cn.itguy.allshare.platform.wx.WXContent;
import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onWxShareButtonClick(View view) {
        WXContent content = new WXContent();
        content.setType(Content.Type.TEXT);
        //content.setType(Content.Type.LINK);
        content.setTimeline(false);
        //content.setTimeline(true);
        content.setTitle("AllShare Test");
        content.setDescription("This is a test");
        content.setText("AllShare Test");
        content.setLink("http://wx.qq.com");
        content.setThumb(
                BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
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
    }

    public void onQqShareButtonClick(View view) {
        // QQ share
        QQContent content = new QQContent();
        content.setType(Content.Type.LINK);
        content.setImageUrl(
                "http://imgsrc.baidu.com/forum/w%3D580/sign=2ddd144b78899e51788e3a1c72a6d990/42064f4a20a4462311d062d49b22720e0cf3d716.jpg");
        //content.setQzone(true);
        content.setTitle("AllShare Test");
        content.setTargetUrl("http://qq.com");
        content.setSummary("This is a test");
        AllShare.share(this, content, new ShareCallback(this));
    }
}
