package cn.itguy.allshare;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import cn.itguy.allshare.platform.qq.QQContent;
import cn.itguy.allshare.platform.qq.QQShare;
import cn.itguy.allshare.platform.wx.WXContent;
import cn.itguy.allshare.platform.wx.WXShare;

/**
 * 分享sdk入口
 *
 * Created by yelongfei490 on 2016/11/25.
 */
public class AllShare {

    private static String wxId;
    private static String qqId;
    private static String weiboId;

    public static void init(Context context) {
        Resources res = context.getResources();
        String packageName = context.getPackageName();
        wxId = context.getString(res.getIdentifier("all_share_wx_app_id", "string", packageName));
        qqId = context.getString(res.getIdentifier("all_share_qq_app_id", "string", packageName)).replace("tencent", "");
        weiboId = context.getString(res.getIdentifier("all_share_wb_app_id", "string", packageName));

        Log.i("AllShare", "Got wxId: " + wxId + " qqId: " + qqId + " weiboId: " + weiboId);
    }

    public static String getWxId() {
        return wxId;
    }

    public static String getQqId() {
        return qqId;
    }

    public static String getWeiboId() {
        return weiboId;
    }

    public static void share(Context context, Content content, Callback callback) {
        Share share = getShare(context, content);
        if (share != null) {
            share.share(content, callback);
        }
    }

    private static Share getShare(Context context, Content content) {
        if (content != null) {
            if (content instanceof QQContent) {
                return new QQShare(context);
            } else if (content instanceof WXContent) {
                return new WXShare(context);
            }
        }
        return null;
    }

}
