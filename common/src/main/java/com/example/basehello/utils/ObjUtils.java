package com.example.basehello.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.lang.reflect.Type;

/**
 * desc:
 * created by pl at 2018/12/21
 */
public class ObjUtils {
    private static Gson gson = new Gson();
    private static JsonParser parser = new JsonParser();

    public static String obj2Json(Object obj){
        if(obj != null){
            return gson.toJson(obj);
        }
        return "";
    }


    public static Object json2Obj(String json,Class<?> cls){
        try{
            if(json != null && !"".equals(json.trim())){
                return gson.fromJson(json,cls);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static Object json2Obj(JsonElement json, Class<?> cls){
        try{
            if(json != null && !json.isJsonNull()){
                return gson.fromJson(json,cls);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    //    List<Person> ps = gson.fromJson(str, new TypeToken<List<Person>>(){}.getType());
    public static Object json2Obj(String json, Type type){
        try{
            if(json != null && !"".equals(json.trim())){
                return gson.fromJson(json,type);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static JsonObject json2JsonObj(String json){
        try {
            return parser.parse(json).getAsJsonObject();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static JsonArray json2JsonArray(String json){
        try {
            return parser.parse(json).getAsJsonArray();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
