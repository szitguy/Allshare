package cn.itguy.allshare;

import android.util.Log;

/**
 * 日志工具类
 *
 * Created by yelongfei490 on 16/8/31.
 */
public class LogUtils {

    private static String tag = "AllShare";

    private static boolean debug = true;

    public static void e(String msg, Object... values) {
        if (debug) {
            if (values != null && values.length > 0) {
                Log.e(tag, String.format(msg, values));
            } else {
                Log.e(tag, String.valueOf(msg));
            }
        }
    }

    public static void e(boolean forceLog, String msg, Object... values) {
        if (forceLog) {
            if (values != null && values.length > 0) {
                Log.e(tag, String.format(msg, values));
            } else {
                Log.e(tag, String.valueOf(msg));
            }
        }
    }

    public static void v(String msg, Object... values) {
        if (debug) {
            if (values != null && values.length > 0) {
                Log.v(tag, String.format(msg, values));
            } else {
                Log.v(tag, String.valueOf(msg));
            }
        }
    }

    public static void v(boolean forceLog, String msg, Object... values) {
        if (forceLog) {
            if (values != null && values.length > 0) {
                Log.v(tag, String.format(msg, values));
            } else {
                Log.v(tag, String.valueOf(msg));
            }
        }
    }

    public static void i(String msg, Object... values) {
        if (debug) {
            if (values != null && values.length > 0) {
                Log.i(tag, String.format(msg, values));
            } else {
                Log.i(tag, String.valueOf(msg));
            }
        }
    }

    public static void i(boolean forceLog, String msg, Object... values) {
        if (forceLog) {
            if (values != null && values.length > 0) {
                Log.i(tag, String.format(msg, values));
            } else {
                Log.i(tag, String.valueOf(msg));
            }
        }
    }

    public static void w(String msg, Object... values) {
        if (debug) {
            if (values != null && values.length > 0) {
                Log.w(tag, String.format(msg, values));
            } else {
                Log.w(tag, String.valueOf(msg));
            }
        }
    }

    public static void w(boolean forceLog, String msg, Object... values) {
        if (forceLog) {
            if (values != null && values.length > 0) {
                Log.w(tag, String.format(msg, values));
            } else {
                Log.w(tag, String.valueOf(msg));
            }
        }
    }

    public static void d(String msg, Object... values) {
        if (debug) {
            if (values != null && values.length > 0) {
                Log.d(tag, String.format(msg, values));
            } else {
                Log.d(tag, String.valueOf(msg));
            }
        }
    }

    public static void d(boolean forceLog, String msg, Object... values) {
        if (forceLog) {
            if (values != null && values.length > 0) {
                Log.d(tag, String.format(msg, values));
            } else {
                Log.d(tag, String.valueOf(msg));
            }
        }
    }

    public static void setTag(String tag) {
        LogUtils.tag = tag;
    }

    public static void setDebug(boolean debug) {
        LogUtils.debug = debug;
    }

}
