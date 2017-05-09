package cn.itguy.allshare.platform.wx;

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
import com.tencent.mm.sdk.openapi.BaseReq;
import com.tencent.mm.sdk.openapi.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXImageObject;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.openapi.WXTextObject;
import com.tencent.mm.sdk.openapi.WXWebpageObject;

import static cn.itguy.allshare.Constants.KEY_SHARE_CONTENT;
import static cn.itguy.allshare.Constants.KEY_SHARE_LISTENER;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "WXEntryActivity";

    private IWXAPI wxapi;
    private WXContent shareContent;
    private ResultReceiver listener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        wxapi = WXAPIFactory.createWXAPI(this, AllShare.getWxId(), true);
        boolean registered = wxapi.registerApp(AllShare.getWxId());
        // 若是接收结果，处理完直接finish
        if (wxapi.handleIntent(intent, this)) {
            finish();
            return;
        }

        Bundle bundle;
        boolean paramError = false;
        if (!registered) {
            LogUtils.e("%s: api is null, finish.", TAG);
            paramError = true;
        } else if (null == intent || null == (bundle = intent.getExtras())) {
            LogUtils.e("%s: params is null, finish.", TAG);
            paramError = true;
        } else {
            bundle.setClassLoader(WXContent.class.getClassLoader());
            if (null == (shareContent = bundle.getParcelable(KEY_SHARE_CONTENT))) {
                LogUtils.e("%s: share content is null, finish.", TAG);
                paramError = true;
            } else if (null == (listener = bundle.getParcelable(KEY_SHARE_LISTENER))) {
                LogUtils.e("%s: result receiver is null.", TAG);
            }
        }
        if (paramError) {
            Toast.makeText(this, R.string.share_failed, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        share();
    }

    private void share() {
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        WXMediaMessage msg = new WXMediaMessage();
        msg.setThumbImage(shareContent.getThumb());
        msg.title = shareContent.getTitle();
        msg.description = shareContent.getDescription();
        switch (shareContent.getType()) {
            case Content.Type.TEXT:
                msg.mediaObject = new WXTextObject(shareContent.getText());
                req.transaction = buildTransaction("text");
                break;
            case Content.Type.IMAGE:
                msg.mediaObject = new WXImageObject(shareContent.getImage());
                req.transaction = buildTransaction("img");
                break;
            case Content.Type.LINK:
                msg.mediaObject = new WXWebpageObject(shareContent.getLink());
                req.transaction = buildTransaction("webpage");
                break;
        }
        req.message = msg;
        req.scene = shareContent.isTimeline() ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
        wxapi.sendReq(req);
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        wxapi.handleIntent(intent, this);
    }

    /**
     * Activity resume的次数
     */
    private int resumeCount;

    @Override protected void onResume() {
        super.onResume();
        // resume计数
        resumeCount++;
        // 当第二次resume时，要么是分享成功或分享失败或取消分享，必需finish，onResume在onNewIntent()后调用
        // 这个处理可以避免某些情况下（如跳转分享前，有两个微信可供选择的对话框），用户返回本页面导致"卡屏"问题
        if (resumeCount > 1) {
            finish();
        }
    }

    @Override
    public void onReq(BaseReq req) {
        LogUtils.e("%s: onReq: type=%s, transaction=%s.", TAG, req.getType(), req.transaction);
    }

    @Override
    public void onResp(BaseResp resp) {
        LogUtils.e("%s: Share resp: code=%s, msg=%s, transaction=%s.", TAG, resp.errCode, resp.errStr, resp.transaction);
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                LogUtils.e("%s: Share completed.", TAG);
                if (listener != null) {
                    listener.send(Share.RESULT_SUCCESS, null);
                }
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                LogUtils.e("%s: Share canceled.", TAG);
                if (listener != null) {
                    listener.send(Share.RESULT_FAILED, null);
                }
                break;
            default:
                if (listener != null) {
                    listener.send(Share.RESULT_FAILED, null);
                }
                break;
        }
        finish();
    }

}