package net.vivatcreative.core.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import java.util.HashMap;
import java.util.Map;

public class JsonUtil {

    private static final Gson gson = new Gson();

    public static <E> E fromJSON(String json, Class<E> holderClass){
        return gson.fromJson(json, holderClass);
    }

    public static String toJSON(Object o){
        return gson.toJson(o);
    }

    public static class JSONBuilder {
        private Map<String, Object> map = new HashMap<>();
        public JSONBuilder put(String key, Object v){
            map.put(key, v);
            return this;
        }
        public String build(){
            StringBuilder builder = new StringBuilder("{");
            map.forEach((key, value) -> builder.append(String.format("\"%s\":\"%s\",", key, value)));
            builder.setCharAt(builder.length()-1, '}');
            return builder.toString();
        }
    }

    public static String getString(String json, String key){
        int startIndex = json.indexOf(key) + key.length() + 1;
        return json.substring(startIndex, startIndex + json.substring(startIndex).indexOf(','));
    }
}
