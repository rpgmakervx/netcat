package org.easyarch.netpet.web.context;


import org.easyarch.netpet.kits.file.FileKits;
import org.easyarch.netpet.web.http.session.HttpSession;

import java.io.File;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Description :
 * Created by xingtianyu on 17-2-28
 * 上午10:51
 * description:
 */

public class HandlerContext {

    static {
        System.setProperty("netcat.home",System.getProperty("user.dir")+ File.separator);
    }

    public static final String DEFAULT_CONTEXT = File.separator;
    public static final String DEFAULT_SUFFIX = "html";

    public static final String DEFAULT_ERRORPAGE = "error";

    public static final String WEB_INF = "/WEB-INF/";

    private static final int DEFAULT_PORT = 8080;

    public static final String DEFAULT_RESOURCE = System.getProperty("netcat.home");

    private static Map<String,Object> globalAttr = new ConcurrentHashMap<>();

    private int remotePort = DEFAULT_PORT;

    private int maxAge = 3600;

    private long maxFileUpload = FileKits.ONE_MB * 128;

    private int sessionAge = Integer.MAX_VALUE;

    private boolean negoCache = false;
    private boolean strongCache = true;


    private String contextPath = "/";

    private String errorPage = "error";

    private String notFound = "error";

    private String serverError = "error";

    /**
     * web资源路径
     */
    private String webView;
    /**
     * 视图资源路径前缀
     */
    private String viewPrefix = WEB_INF;
    /**
     * 视图资源文件后缀（默认html）
     */
    private String viewSuffix = DEFAULT_SUFFIX;

    private SessionHolder sessionHolder;

    public HandlerContext(){
        URL resourceUrl = HandlerContext.class.getResource("/");
        if (resourceUrl == null){
            webView = DEFAULT_RESOURCE;
        }else {
            webView = resourceUrl.getPath();
        }
        sessionHolder = new SessionHolder();
    }

    public int getRemotePort() {
        return remotePort;
    }

    public void setRemotePort(int remotePort) {
        if (remotePort < 1){
            remotePort = DEFAULT_PORT;
        }
        this.remotePort = remotePort;
    }

    public String getContextPath(){
        return contextPath;
    }

    public String getWebView(){
        return webView;
    }

    public void setWebView(String root){
        this.webView = root;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public String getViewPrefix() {
        return viewPrefix;
    }

    public void setViewPrefix(String viewPrefix) {
        this.viewPrefix = viewPrefix;
    }

    public String getViewSuffix() {
        return viewSuffix;
    }

    public void setViewSuffix(String viewSuffix) {
        this.viewSuffix = viewSuffix;
    }

    public HttpSession getSession(String sessionId){
        return sessionHolder.getSession(sessionId);
    }

    public void addSession(String sessionId, HttpSession session){
        sessionHolder.addSession(sessionId,session);
    }

    public String getErrorPage() {
        return errorPage;
    }

    public void setErrorPage(String errorPage) {
        this.errorPage = errorPage;
    }

    public String getNotFound() {
        return notFound;
    }

    public String getServerError() {
        return serverError;
    }

    public void setServerError(String serverError) {
        this.serverError = serverError;
    }

    public void setNotFound(String notFound) {
        this.notFound = notFound;
    }

    public int getMaxAge(){
        return maxAge;
    }

    public void setMaxAge(int maxAge){
        this.maxAge = maxAge;
    }

    public int getSessionAge() {
        return sessionAge;
    }

    public void setSessionAge(int sessionAge) {
        this.sessionAge = sessionAge;
    }

    public boolean isNegoCache() {
        return negoCache;
    }

    public void setNegoCache(boolean negoCache) {
        this.negoCache = negoCache;
    }

    public boolean isStrongCache() {
        return strongCache;
    }

    public void setStrongCache(boolean strongCache) {
        this.strongCache = strongCache;
    }

    public long getMaxFileUpload() {
        return maxFileUpload;
    }

    public void setMaxFileUpload(long maxFileUpload) {
        this.maxFileUpload = maxFileUpload;
    }

    public void globalConfig(String name, String value){
        globalAttr.put(name,value);
    }

    public Object globalConfig(String name){
        return globalAttr.get(name);
    }
}
