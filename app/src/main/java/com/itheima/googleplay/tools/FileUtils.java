package com.itheima.googleplay.tools;

import android.os.Environment;

import java.io.File;

/**
 * Created by yin on 2016/11/28.
 */
public class FileUtils {
    public static final String CACHE = "cache";
    public static final String ICON = "icon";
    public static final String ROOT = "GooglePlay";

    public static File getDir(String str) {
        StringBuilder path = new StringBuilder();

        if (isSDAvailable()) {
            path.append(Environment.getExternalStorageDirectory().getAbsolutePath());//得到当前sd卡的根路径 /mnt/sdcard
            path.append(File.separator);//  /mnt/sdcard/
            path.append(ROOT);// /mnt/sdcard/GooglePlay
            path.append(File.separator);// /mnt/sdcard/GooglePlay
            path.append(str);// /mnt/sdcard/GooglePlay/cache
        } else {
            File filesDir = UiUtils.getContext().getCacheDir();//得到cache
            path.append(filesDir.getAbsolutePath());//  /data/data/com.itheima.googleplay/cache
            path.append(File.separator);//  /data/data/com.itheima.googleplay/cache/
            path.append(str);//  /data/data/com.itheima.googleplay/cache/cache
        }
        File file = new File(path.toString());
        if (!file.exists() || !file.isDirectory()) {
            file.mkdirs();//新建文件夹
        }
        return file;
    }

    /**
     * 判断是否是SD卡
     *
     * @return
     */
    private static boolean isSDAvailable() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取缓存路径
     *
     * @return
     */
    public static File getCacheDir() {
        return getDir(CACHE);// /mnt/sdcard/GooglePlay/cache
    }

    /**
     * 获取图片缓存路径
     */
    public static File getIconDir() {
        return getDir(ICON);
    }
}
