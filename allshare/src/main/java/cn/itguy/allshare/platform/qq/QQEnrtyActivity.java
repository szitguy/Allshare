package cn.itguy.allshare.platform.qq;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.widget.Toast;
import cn.itguy.allshare.AllShare;
import cn.itguy.allshare.Constants;
import cn.itguy.allshare.Content;
import cn.itguy.allshare.LogUtils;
import cn.itguy.allshare.R;
import cn.itguy.allshare.Share;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import java.util.ArrayList;

/**
 * Created by yelongfei490 on 16/5/23.
 */
public class QQEnrtyActivity extends Activity {

    public static final String TAG =  "QQEnrtyActivity";

    private QQContent shareContent;
    private ResultReceiver listener;
    private Tencent api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle;
        boolean paramError = false;
        Intent intent = getIntent();
        if (null == intent || null == (bundle = intent.getExtras())) {
            LogUtils.e("%s: params is null, finish.", TAG);
            paramError = true;
        } else {
            bundle.setClassLoader(QQContent.class.getClassLoader());
            if (null == (shareContent = bundle.getParcelable(Constants.KEY_SHARE_CONTENT))) {
                LogUtils.e("%s: share content is null, finish.", TAG);
                paramError = true;
            } else if (null == (listener = bundle.getParcelable(Constants.KEY_SHARE_LISTENER))) {
                LogUtils.e("%s: result receiver is null.", TAG);
            } else if (null == (api = Tencent.createInstance(AllShare.getQqId(), this))) {
                LogUtils.e("%s: api is null, finish.", TAG);
                paramError = true;
            }
        }
        if (paramError) {
            Toast.makeText(this, R.string.share_failed, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        if (shareContent.isQzone())
            doShareToQQZone();
        else
            doShareToQQ();
    }

    private void doShareToQQ() {
        int type = shareContent.getType();
        if (Content.Type.TEXT == type) {
            LogUtils.e("%s: Unsupported content, finish.", TAG);
            Toast.makeText(this, R.string.share_failed, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        Bundle params = new Bundle();
        switch (type) {
            case Content.Type.LINK:
                params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
                params.putString(QQShare.SHARE_TO_QQ_TITLE, shareContent.getTitle());
                params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, shareContent.getTargetUrl());
                params.putString(QQShare.SHARE_TO_QQ_SUMMARY, shareContent.getSummary());
                params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, shareContent.getImageUrl());
                break;
            case Content.Type.IMAGE:
                params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
                params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, shareContent.getImageUrl());
                break;
        }
        api.shareToQQ(QQEnrtyActivity.this, params, qqShareListener);
    }

    private void doShareToQQZone() {
        if (Content.Type.LINK != shareContent.getType()) {
            LogUtils.e("%s: Unsupported content, finish.", TAG);
            Toast.makeText(this, R.string.share_failed, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        Bundle params = new Bundle();
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, shareContent.getTitle());
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, shareContent.getSummary());
        ArrayList<String> imageUrls = new ArrayList<>();
        imageUrls.add(shareContent.getImageUrl());
        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imageUrls);
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, shareContent.getTargetUrl());

        api.shareToQzone(QQEnrtyActivity.this, params, qqShareListener);
    }

    IUiListener qqShareListener = new IUiListener() {

        @Override
        public void onCancel() {
            LogUtils.e("%s: Share canceled.", TAG);
            if (listener != null) {
                listener.send(Share.RESULT_CANCELD, null);
            }
            finish();
        }

        @Override
        public void onComplete(Object response) {
            LogUtils.e("%s: Share completed.", TAG);
            if (listener != null) {
                listener.send(Share.RESULT_SUCCESS, null);
            }
            finish();
        }

        @Override
        public void onError(UiError e) {
            LogUtils.e("%s: Share error: code=%s, msg=%s, detail=%s.", TAG, e.errorCode, e.errorMessage, e.errorDetail);
            if (listener != null) {
                listener.send(Share.RESULT_FAILED, null);
            }
            finish();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Tencent.onActivityResultData(requestCode, resultCode, data, qqShareListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (api != null) {
            api.releaseResource();
        }
    }
}
