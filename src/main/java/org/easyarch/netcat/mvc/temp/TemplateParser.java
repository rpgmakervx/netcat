package org.easyarch.netcat.mvc.temp;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.easyarch.netcat.context.HandlerContext;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xingtianyu on 17-3-13
 * 上午11:16
 * description:
 */

public class TemplateParser {

    private static Configuration configuration;

    private Map<String,Object> params;

    private HandlerContext context;

    private Template temp;
    static {
        configuration = new Configuration(Configuration.VERSION_2_3_22);
    }

    public TemplateParser(HandlerContext context){
        try {
            this.context = context;
            this.params = new HashMap();
            configuration.setDirectoryForTemplateLoading(new File(context.getWebView()+context.getViewPrefix()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addParam(String name,Object value){
        this.params.put(name,value);
    }

    public void addParam(Map<String,Object> params){
        this.params.putAll(params);
    }

    public String getTemplate(String tmpName){
        try {
            temp = configuration.getTemplate(tmpName);
            StringWriter writer = new StringWriter();
            temp.process(this.params,writer);
            return writer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
