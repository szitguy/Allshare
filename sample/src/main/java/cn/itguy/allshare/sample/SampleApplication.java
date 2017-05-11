package cn.itguy.allshare.sample;

import android.app.Application;
import cn.itguy.allshare.AllShare;

/**
 * Created by yelongfei490 on 2016/11/28.
 */
public class SampleApplication extends Application {

    public static SampleApplication instance;

    @Override public void onCreate() {
        super.onCreate();
        instance = this;

        // 初始化Allshare
        AllShare.init(this);
    }

}
