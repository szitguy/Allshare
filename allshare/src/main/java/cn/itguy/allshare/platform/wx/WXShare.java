package cn.itguy.allshare.platform.wx;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import cn.itguy.allshare.Callback;
import cn.itguy.allshare.Constants;
import cn.itguy.allshare.Content;
import cn.itguy.allshare.Share;
import java.lang.ref.WeakReference;

/**
 * Created by yelongfei490 on 2016/11/25.
 */

public class WXShare extends ResultReceiver implements Share {

    private WeakReference<Context> contextWeakRef;
    private Callback callback;

    public WXShare(Context context) {
        super(null);
        contextWeakRef = new WeakReference<>(context);
    }

    @Override public void share(Content content, Callback callback) {
        this.callback = callback;
        Context context = contextWeakRef.get();
        if (context != null) {
            context.startActivity(new Intent(context, WXEntryActivity.class)
                    .putExtra(Constants.KEY_SHARE_CONTENT, content)
                    .putExtra(Constants.KEY_SHARE_LISTENER, this));
        }
    }

    @Override protected void onReceiveResult(int resultCode, Bundle resultData) {
        if (resultCode == Share.RESULT_SUCCESS) {
            if (callback != null) {
                callback.onSuccess();
            }
        } else if (resultCode == Share.RESULT_CANCELD) {
            if (callback != null) {
                callback.onCanceled();
            }
        } else {
            if (callback != null) {
                callback.onFailed();
            }
        }
    }
}
