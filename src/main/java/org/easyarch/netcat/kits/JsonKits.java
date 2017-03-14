package org.easyarch.netcat.kits;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.easyarch.netcat.mvc.entity.Json;

import java.util.HashMap;
import java.util.Map;

/**
 * Description :
 * Created by xingtianyu on 17-2-27
 * 下午5:15
 * description:
 */

public class JsonKits {

    public static String toString(Map<String,Object> jsonMap){
        if (jsonMap ==null||jsonMap.size()== 0){
            return "";
        }
        JSONObject jsonObject = new JSONObject(jsonMap);
        return jsonObject.toJSONString();
    }

    public static Map<String,Object> toMap(String json){
        if (StringKits.isEmpty(json)){
            return null;
        }
        JSONObject object = JSON.parseObject(json);
        Map<String,Object> jsonMap = new HashMap<String,Object>();
        for (Map.Entry<String,Object> entry:object.entrySet()){
            jsonMap.put(entry.getKey(),entry.getValue());
        }
        return jsonMap;
    }

    public static Json toJson(String str){
        Json json = new Json(toMap(str));
        return json;
    }

    public static Json toJson(Map<String,Object> map){
        Json json = new Json(map);
        return json;
    }

    public static String toString(Json json){
        Map<String,Object> map = json.getJsonMap();
        return toString(map);
    }

    public static<T> byte[] serial(T bean){
        return JSONObject.toJSONBytes(bean, SerializerFeature.PrettyFormat);
    }

    public static<T> T deserial(byte[] json,Class<T> cls){
        return (T) JSONObject.parse(json, Feature.OrderedField);
    }
}
