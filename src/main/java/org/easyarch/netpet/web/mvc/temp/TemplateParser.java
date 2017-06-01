package org.easyarch.netpet.web.mvc.temp;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.easyarch.netpet.kits.file.FileKits;
import org.easyarch.netpet.web.context.HandlerContext;
import org.easyarch.netpet.web.http.session.HttpSession;

import java.io.*;
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

    private String sessionId;

    private boolean hasErrorPage = false;
    static {
        configuration = new Configuration(Configuration.VERSION_2_3_22);
    }

    public TemplateParser(HandlerContext context,String sessionId) throws IOException {
        this.context = context;
        this.params = new ConcurrentHashMap<>();
        this.sessionId = sessionId;

    }

    public void addParam(String name,Object value){
        this.params.put(name,value);
    }

    public void addParam(Map<String,Object> params){
        this.params.putAll(params);
    }

    private void fillWithSession(){
        System.out.println("模板中的session："+context.getSessions());
        System.out.println("模板中的sessionId："+sessionId);
        HttpSession session = context.getSession(sessionId);
        if (session != null){
            this.params.putAll(session.getAll());
            System.out.println("模板中的键值对："+params);
        }
    }

    /**
     * error page再prefix中不存在时。读取默认的error.html
     * @param tmpPath 模板路径
     * @return 返回模板内容
     */
    public String getTemplate(String tmpPath) throws Exception {
        System.out.println("template path:"+tmpPath);
        fillWithSession();
        StringWriter writer = new StringWriter();
        byte[] bytes = FileKits.readx(tmpPath);
        BufferedReader reader = new BufferedReader(new StringReader(new String(bytes)));
        temp = new Template(null,reader,null);
        temp.process(this.params,writer);
        return writer.toString();
    }
    public String getTemplate(InputStream tmpStream) throws Exception {
        fillWithSession();
        StringWriter writer = new StringWriter();
        BufferedReader reader = new BufferedReader(new InputStreamReader(tmpStream));
        temp = new Template(null,reader,null);
        temp.process(this.params,writer);
        return writer.toString();
    }
}
