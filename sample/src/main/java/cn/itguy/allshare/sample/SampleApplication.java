package cn.itguy.allshare.sample;

import android.app.Application;
import cn.itguy.allshare.AllShare;

/**
 * Created by yelongfei490 on 2016/11/28.
 */
public class SampleApplication extends Application {

    @Override public void onCreate() {
        super.onCreate();
        // 初始化Allshare
        AllShare.init(this);
    }
}
