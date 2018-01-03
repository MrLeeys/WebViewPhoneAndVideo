package com.leo.webview;

import android.os.Environment;

import java.io.File;

public class FileUtil {

    /*
     * 获取该应用的根目录
     */
    public static String getAppPath() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return "";
        }
        File sdRoot = Environment.getExternalStorageDirectory();
        File file = new File(sdRoot.getAbsolutePath() + "/tencent");
        if (!file.exists()) file.mkdir();
        return file.getAbsolutePath();
    }

    /*
    音频
    */
    public static String getAudioPath() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return "";
        }
        File file = new File(getAppPath() + "/audio");
        if (!file.exists()) file.mkdir();
        return file.getAbsolutePath();
    }

    /*
    图片
    */
    public static String getTmpPath() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return "";
        }
        File file = new File(getAppPath() + "/temp");
        if (!file.exists()) file.mkdir();
        return file.getAbsolutePath();
    }
}
