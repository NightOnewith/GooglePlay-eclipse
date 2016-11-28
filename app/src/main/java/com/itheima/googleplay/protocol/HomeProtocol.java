package com.itheima.googleplay.protocol;

import com.itheima.googleplay.http.HttpHelper;
import com.itheima.googleplay.tools.FileUtils;
import com.itheima.googleplay.tools.UiUtils;
import com.lidroid.xutils.util.IOUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by yin on 2016/11/26.
 */
public class HomeProtocol {
    public void load(int index) {
        //请求服务器
        String json = loadLocal(index);
        if (json == null) {
            json = loadServer(index);
            if (json != null) {
                saveLocal(json, index);
            }
        }
        if (json != null) {
            //解析json
            paserJson(json);
        }
    }

    //加载本地数据
    private String loadLocal(int index) {
        return null;
    }

    private String loadServer(int index) {
        HttpHelper.HttpResult httpResult = HttpHelper.get(HttpHelper.URL + "home" + "?index=" + index); //http://127.0.0.1:8090/home?index=index
        String json = httpResult.getString();
        System.out.println(json);
        return json;
    }

    //保存数据到本地
    private void saveLocal(String json, int index) {
        BufferedWriter bw = null;
        try {
            File dir = FileUtils.getCacheDir();
            //在第一行写个过期时间
            File file = new File(dir, "home_" + index);// /mnt/sdcard/GooglePlay/cache/home_0
            FileWriter fw = new FileWriter(file);
            bw = new BufferedWriter(fw);
            bw.write(System.currentTimeMillis() + 1000 * 100 + "");
            bw.newLine();//换行
            bw.write(json);//把整个json文件保存起来
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(bw);
        }
    }

    private void paserJson(String json) {

    }
}
