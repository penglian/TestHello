package com.example.network.network.manager;

import com.example.network.BuildConfig;
import com.example.network.network.BaseApiEntity;
import com.example.network.network.gson.DoubleDefault0Adapter;
import com.example.network.network.gson.IntegerDefault0Adapter;
import com.example.network.network.gson.LongDefault0Adapter;
import com.example.network.network.okhttp.CacheInterceptor;
import com.example.network.network.okhttp.HeaderInterceptor;
import com.example.network.network.okhttp.TrustManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络管理类
 * Created by pl on 2018/4/26 0026.
 */

public class NetworkManager {
    private static volatile NetworkManager networkManager;
    private static Gson gson;
    /**
     * 获取单例
     *
     * @return
     */
    public static NetworkManager getInstance() {
        if (networkManager == null) {
            synchronized (NetworkManager.class) {
                if (networkManager == null) {
                    networkManager = new NetworkManager();
                }
            }
        }
        return networkManager;
    }


    /**
     * 执行网络请求
     */
    public <T> T doRequest(final BaseApiEntity baseApiEntity, Class<T> clazz) {
        //手动创建一个OkHttpClient并设置超时时间缓存等设置
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        //SSL证书
        builder.sslSocketFactory(TrustManager.getUnsafeOkHttpClient())
                .hostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        if (baseApiEntity.isBombServer()) {
            builder.addInterceptor(new HeaderInterceptor());
        }
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        }

        builder.addNetworkInterceptor(new CacheInterceptor(baseApiEntity.getMaxAge(), baseApiEntity.getMaxStale()))  //设置Cache拦截器
                .addInterceptor(new CacheInterceptor(baseApiEntity.getMaxAge(), baseApiEntity.getMaxStale()))
                .cache(baseApiEntity.getCache())
                //time out
                .connectTimeout(baseApiEntity.getTimeOutConnection(), TimeUnit.SECONDS)
                .readTimeout(baseApiEntity.getTimeOutRead(), TimeUnit.SECONDS)
                .writeTimeout(baseApiEntity.getTimeOutRead(), TimeUnit.SECONDS)
                //失败重连
                .retryOnConnectionFailure(true);


        /*创建retrofit对象*/
        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create(buildGson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseApiEntity.getBaseUrl())
                .build();
        return retrofit.create(clazz);
    }

    /**
     * 处理json串异常的情况
     * @return
     */
    private Gson buildGson() {
        if (gson == null) {
            gson = new GsonBuilder()
                    .registerTypeAdapter(Integer.class, new IntegerDefault0Adapter())
                    .registerTypeAdapter(int.class, new IntegerDefault0Adapter())
                    .registerTypeAdapter(Double.class, new DoubleDefault0Adapter())
                    .registerTypeAdapter(double.class, new DoubleDefault0Adapter())
                    .registerTypeAdapter(Long.class, new LongDefault0Adapter())
                    .registerTypeAdapter(long.class, new LongDefault0Adapter())
                    .create();
        }
        return gson;
    }

}
