package org.easyarch.netcat.web.mvc.temp;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.easyarch.netcat.web.context.HandlerContext;
import org.easyarch.netcat.web.http.Const;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by xingtianyu on 17-3-13
 * 上午11:16
 * description:
 */

public class TemplateParser {

    private static final String MODEL = "model";

    private static Configuration configuration;

    private Map<String,Object> params;

    private HandlerContext context;

    private Template temp;

    private boolean hasErrorPage = false;
    static {
        configuration = new Configuration(Configuration.VERSION_2_3_22);
    }

    public TemplateParser(HandlerContext context) throws IOException {
        this.context = context;
        this.params = new ConcurrentHashMap<>();
//        File file = new File(context.getWebView()+context.getViewPrefix());
        String errorPagePath = context.getWebView()+context.getViewPrefix()
                +context.getErrorPage()
                +Const.POINT
                +HandlerContext.DEFAULT_SUFFIX;
        File errorPage = new File(errorPagePath);
        if (!errorPage.exists()){
            hasErrorPage = false;
        }
//        if (!file.exists()){
//        }
        File file = new File(HandlerContext.DEFAULT_RESOURCE);
        configuration.setDirectoryForTemplateLoading(file);
    }
    public TemplateParser(String path) throws IOException {
        this.params = new HashMap();
        File file = new File(path);
        if (!file.exists()){
            file = new File(HandlerContext.DEFAULT_RESOURCE);
        }
        configuration.setDirectoryForTemplateLoading(file);
    }

    public void addParam(String name,Object value){
        this.params.put(name,value);
    }

    public void addParam(Map<String,Object> params){
        this.params.putAll(params);
    }

    /**
     * error page再prefix中不存在时。读取默认的error.html
     * @param tmpName
     * @return
     * @throws Exception
     */
    public String getTemplate(String tmpName) throws Exception {
        if (!hasErrorPage&&tmpName.contains(context.getErrorPage())){
            temp = configuration.getTemplate(
                    HandlerContext.DEFAULT_ERRORPAGE
                            +Const.POINT
                            +HandlerContext.DEFAULT_SUFFIX);
        }else{
            temp = configuration.getTemplate(context.getViewPrefix()+tmpName);
        }
        StringWriter writer = new StringWriter();
        temp.process(this.params,writer);
        return writer.toString();
    }
}
