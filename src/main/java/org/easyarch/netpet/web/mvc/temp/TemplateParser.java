package org.easyarch.netpet.web.mvc.temp;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.easyarch.netpet.kits.file.FileKits;
import org.easyarch.netpet.web.context.HandlerContext;

import java.io.*;
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
////        File file = new File(context.getWebView()+context.getViewPrefix());
//        String errorPagePath = context.getWebView()+context.getViewPrefix()
//                +context.getErrorPage()
//                +Const.POINT
//                +HandlerContext.DEFAULT_SUFFIX;
//        File errorPage = new File(errorPagePath);
//        if (!errorPage.exists()){
//            hasErrorPage = false;
//        }
////        if (!file.exists()){
////        }
//        File file = new File(HandlerContext.DEFAULT_RESOURCE);
//        configuration.setDirectoryForTemplateLoading(file);
    }
    public TemplateParser(String path) throws IOException {
        this.params = new HashMap();
//        File file = new File(path);
//        if (!file.exists()){
//            file = new File(HandlerContext.DEFAULT_RESOURCE);
//        }
//        configuration.setDirectoryForTemplateLoading(file);
    }

    public void addParam(String name,Object value){
        this.params.put(name,value);
    }

    public void addParam(Map<String,Object> params){
        this.params.putAll(params);
    }

    /**
     * error page再prefix中不存在时。读取默认的error.html
     * @param tmpPath 模板路径
     * @return 返回模板内容
     */
    public String getTemplate(String tmpPath) throws Exception {
        System.out.println("template path:"+tmpPath);
        StringWriter writer = new StringWriter();
        byte[] bytes = FileKits.readx(tmpPath);
        BufferedReader reader = new BufferedReader(new StringReader(new String(bytes)));
        temp = new Template(null,reader,null);
        temp.process(this.params,writer);
        return writer.toString();
    }
    public String getTemplate(InputStream tmpStream) throws Exception {
        StringWriter writer = new StringWriter();
        BufferedReader reader = new BufferedReader(new InputStreamReader(tmpStream));
        temp = new Template(null,reader,null);
        temp.process(this.params,writer);
        return writer.toString();
    }
}
