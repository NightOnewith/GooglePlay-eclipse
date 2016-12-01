package com.itheima.googleplay.protocol;

import com.itheima.googleplay.domian.AppInfo;
import com.itheima.googleplay.http.HttpHelper;
import com.itheima.googleplay.tools.FileUtils;
import com.lidroid.xutils.util.IOUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yin on 2016/11/26.
 */
public class HomeProtocol {
    public List<AppInfo> load(int index) {
        //加载本地数据
        String json = loadLocal(index);
        if (json == null) {
            //请求服务器
            json = loadServer(index);
            if (json != null) {
                saveLocal(json, index);
            }
        }
        if (json != null) {
            //解析json
            return paserJson(json);
        } else {
            return null;
        }
    }

    private String loadServer(int index) {
        HttpHelper.HttpResult httpResult = HttpHelper.get(HttpHelper.URL + "home" + "?index=" + index); //http://127.0.0.1:8090/home?index=index
        String json = httpResult.getString();
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

    //加载本地数据
    private String loadLocal(int index) {
        //如果发现文件已过期，就不要再去复用缓存了
        File dir = FileUtils.getCacheDir();//获取缓存所在文件夹
        File file = new File(dir, "home_" + index);
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            Long outOfDate = Long.parseLong(br.readLine());
            if (System.currentTimeMillis() > outOfDate) { //过期
                return null;
            } else {
                String str = null;
                StringWriter sw = new StringWriter();
                while ((str = br.readLine()) != null) {
                    sw.write(str);
                }
                return sw.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 1.把整个json文件写到一个本地文件中
    // 2.把每条数据都摘取出来存到数据库中
    public List<AppInfo> paserJson(String json) {
        List<AppInfo> appInfos = new ArrayList<AppInfo>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("list");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                long id = jsonObj.getLong("id");
                String name = jsonObj.getString("name");
                String packageName = jsonObj.getString("packageName");
                String iconUrl = jsonObj.getString("iconUrl");
                float stars = Float.parseFloat(jsonObj.getString("stars"));
                long size = jsonObj.getLong("size");
                String downloadUrl = jsonObj.getString("downloadUrl");
                String des = jsonObj.getString("des");
                AppInfo info = new AppInfo(id, name, packageName, iconUrl, stars, size, downloadUrl, des);
                appInfos.add(info);
            }
            return appInfos;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
