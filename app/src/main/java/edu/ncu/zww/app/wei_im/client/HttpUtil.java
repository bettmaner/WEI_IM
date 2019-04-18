package edu.ncu.zww.app.wei_im.client;

import java.util.List;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class HttpUtil {

    // URL地址原因http://www.cnblogs.com/kode/p/4568145.html
    private static final String URI = "http://10.0.2.2:8080/";
    private static final String queryContactUrl = "http://192.168.56.1:8080/queryContact";

    public static void sendOkHttpRequest(String url, Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(callback);
    }

    // 获取查询联系人的url
    public static String getQueryContactUrl(String account,Integer type) {
        return queryContactUrl+"?account="+account+"&&type="+String.valueOf(type);
    }

}
