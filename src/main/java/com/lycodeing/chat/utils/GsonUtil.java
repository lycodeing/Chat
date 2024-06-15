package com.lycodeing.chat.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 单例模式
 *
 * @author xiaotianyu
 */
public class GsonUtil {

    private static final Gson GSON = new GsonBuilder().create();

    //私有构造函数，防止实例化
    private GsonUtil() {

    }

    public static String toJson(Object object) {
        return GSON.toJson(object);
    }

    public static <T> T fromJson(String json, Class<T> classOfT) {
        return GSON.fromJson(json, classOfT);
    }


}
