package cn.itguy.allshare;

/**
 * Created by yelongfei490 on 2016/11/25.
 */

public interface Share {

    int RESULT_FAILED = 0;

    int RESULT_SUCCESS = 1;

    int RESULT_CANCELD = 2;

    void share(Content content, Callback callback);

}
