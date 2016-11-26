package com.itheima.googleplay.protocol;

import com.itheima.googleplay.http.HttpHelper;

/**
 * Created by yin on 2016/11/26.
 */
public class HomeProtocol {
    public void load(int index){
        //请求服务器
        String json = loadLocal(index);
        if(json == null){
            json = loadServer(index);
            if(json != null){
                saveLocal(json, index);
            }
        }
        if(json != null){
            //解析json
            paserJson(json);
        }
    }

    private String loadLocal(int index) {
        return null;
    }

    private String loadServer(int index) {
        HttpHelper.HttpResult httpResult = HttpHelper.get(HttpHelper.URL+"home"+"?index="+index); //http://127.0.0.1:8090/home?index=index
        String json = httpResult.getString();
        System.out.println(json);
        return json;
    }

    private void saveLocal(String json, int index) {

    }

    private void paserJson(String json) {

    }
}
