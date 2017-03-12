package org.easyarch.netcat.mvc.action.handler.impl;

import org.easyarch.netcat.context.HandlerContext;
import org.easyarch.netcat.http.protocol.HttpHeaderName;
import org.easyarch.netcat.http.protocol.HttpHeaderValue;
import org.easyarch.netcat.http.protocol.HttpStatus;
import org.easyarch.netcat.http.request.impl.HttpHandlerRequest;
import org.easyarch.netcat.http.response.impl.HttpHandlerResponse;
import org.easyarch.netcat.kits.TimeKits;
import org.easyarch.netcat.kits.file.FileKits;
import org.easyarch.netcat.mvc.action.handler.HttpHandler;

import java.io.File;
import java.util.regex.Pattern;

import static org.easyarch.netcat.kits.file.FileFilter.*;

/**
 * Created by xingtianyu on 17-3-9
 * 下午3:53
 * description:处理静态资源，以及强缓存和协商缓存
 */

public class DefaultHttpHandler implements HttpHandler {

    /**
     * 资源是否被默认handler捕获
     */
    private boolean interrupt = false;

    @Override
    public void handle(HttpHandlerRequest request, HttpHandlerResponse response) throws Exception {
        HandlerContext context = request.getContext();
        String webView = context.getWebView();
        String uri = request.getRequestURI();
        Pattern cachedPattern = Pattern.compile(CACHEPATTERN);

        StringBuffer resourcePath = new StringBuffer();
        resourcePath.append(webView).append(uri);
        int point = uri.lastIndexOf(".");
        if (point == -1){
            System.out.println("不走默认");
            return ;
        }
        String suffix = uri.substring(point, uri.length());
        String filename = uri.substring(uri.lastIndexOf(File.separator),point);
        if (!FileKits.exists(resourcePath.toString())){
            System.out.println("不走默认");
            return ;
        }
        System.out.println("走默认");
        interrupt = true;
        if (cachedPattern.matcher(suffix).matches()){
            checkStrongCache(request,response);
            boolean cached = checkNagoCache(request,response,suffix,resourcePath.toString());
            if (cached){
                response.write();
                return;
            }
            response.write(FileKits.read(resourcePath.toString())
                    ,HttpHeaderValue.getContentType(suffix));
        }else if (suffix.endsWith(DOCX)||suffix.endsWith(DOC)){
            response.download(FileKits.read(resourcePath.toString()),filename,HttpHeaderValue.DOC);
        }else if (suffix.endsWith(XLS)||suffix.endsWith(XLSX)){
            response.download(FileKits.read(resourcePath.toString()),filename,HttpHeaderValue.XLS);
        }else if (suffix.endsWith(PDF)){
            response.download(FileKits.read(resourcePath.toString()),filename,HttpHeaderValue.PDF);
        }else{
            System.out.println("go to default");
            response.write(FileKits.read(resourcePath.toString()));
        }
    }

    private void checkStrongCache(HttpHandlerRequest request, HttpHandlerResponse response){
        if (!request.getContext().isStrongCache()){
            return ;
        }
        response.setHeader(HttpHeaderName.CACHE_CONTROL,
                HttpHeaderValue.MAXAGE+String.valueOf(request.getContext().getMaxAge()));
    }

    private boolean checkNagoCache(HttpHandlerRequest request, HttpHandlerResponse response,String type,String resourcePath){
        if (!request.getContext().isNegoCache()){
            return false;
        }
        String ifNoneMatch = request.getHeader(HttpHeaderName.IF_NONE_MATCH);
        String etag = FileKits.md5(resourcePath);
        String lastModify = TimeKits.getGMTTime(FileKits.getLastModifyTime(resourcePath));
        String lastModifySince = request.getHeader(HttpHeaderName.IF_MODIFIED_SINCE);
        if (etag.equals(ifNoneMatch)){
            if (lastModify.equals(lastModifySince)){
                response.setStatus(HttpStatus.NOT_MODIFIED);
                return true;
            }
        }
        response.setHeader(HttpHeaderName.LAST_MODIFIED,lastModify);
        response.setHeader(HttpHeaderName.ETAG,etag);
        return false;
    }

    public boolean isInterrupt(){
        return interrupt;
    }
}
