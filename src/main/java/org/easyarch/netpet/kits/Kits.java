package org.easyarch.netpet.kits;

import org.easyarch.netpet.kits.file.FileKits;
import org.easyarch.netpet.web.context.HandlerContext;

/**
 * Created by xingtianyu on 17-3-14
 * 下午4:45
 * description:
 */

public class Kits {

    /**
     * 检查视图是否存在
     * @param context 上下文
     * @param viewName 视图名
     * @return
     */
    public static boolean hasView(HandlerContext context, String viewName){
        StringBuffer path = new StringBuffer();
        path.append(context.getWebView())
                .append(context.getViewPrefix())
                .append(viewName)
                .append(context.getViewSuffix());
        return FileKits.exists(path.toString());
    }

    public static boolean hasResource(HandlerContext context,String uri){
        StringBuffer resourcePath = new StringBuffer();
        resourcePath.append(context.getWebView()).append(uri);
        String prefix = "";
        if (!uri.equals("/")) {
            String[] segement = uri.split("/");
            prefix = "/" + segement[1] + "/";
        }
        System.out.println("resourcePath.toString():"+resourcePath.toString());
        if (HandlerContext.WEB_INF.equals(prefix)) {
            return false;
        }
        if (!FileKits.exists(resourcePath.toString())
                ||FileKits.isDir(resourcePath.toString())){
            return false;
        }
        return true;
    }

    /**
     * 确保正确得到错误页面
     * 默认错误页面在jar包中
     * @param context 上下文
     * @return 得到错误页面路径
     */
    public static String getErrorView(HandlerContext context){
        if (hasView(context,context.getErrorPage())){
            return context.getErrorPage();
        }
        StringBuffer path = new StringBuffer();
        path.append(HandlerContext.DEFAULT_RESOURCE)
                .append(context.getViewPrefix())
                .append(HandlerContext.DEFAULT_ERRORPAGE)
                .append(context.getViewSuffix());
        return path.toString();
    }

}
