package org.easyarch.netcat.mvc.action.filter.impl;

import org.easyarch.netcat.context.HandlerContext;
import org.easyarch.netcat.http.protocol.HttpHeaderName;
import org.easyarch.netcat.http.protocol.HttpHeaderValue;
import org.easyarch.netcat.http.protocol.HttpStatus;
import org.easyarch.netcat.http.request.impl.HttpHandlerRequest;
import org.easyarch.netcat.http.response.impl.HttpHandlerResponse;
import org.easyarch.netcat.kits.TimeKits;
import org.easyarch.netcat.kits.file.FileKits;
import org.easyarch.netcat.mvc.action.filter.HttpFilter;

import java.io.File;
import java.util.regex.Pattern;

import static org.easyarch.netcat.kits.file.FileFilter.*;

/**
 * Created by xingtianyu on 17-3-16
 * 上午11:42
 * description:
 */

public class StaticFilter implements HttpFilter {

    @Override
    public boolean before(HttpHandlerRequest request, HttpHandlerResponse response) {
        HandlerContext context = request.getContext();
        String webView = context.getWebView();
        String uri = request.getRequestURI();
        Pattern cachedPattern = Pattern.compile(CACHEPATTERN);

        StringBuffer resourcePath = new StringBuffer();
        resourcePath.append(webView).append(uri);
        int point = uri.lastIndexOf(".");
        if (point == -1){
            return true;
        }
        String suffix = uri.substring(point, uri.length());
        String filename = uri.substring(uri.lastIndexOf(File.separator),point);
        if (!FileKits.exists(resourcePath.toString())){
            return true;
        }
        if (cachedPattern.matcher(suffix).matches()){
            checkStrongCache(request,response);
            boolean cached = checkNagoCache(request,response,suffix,resourcePath.toString());
            if (cached){
                response.write();
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
            response.write(FileKits.read(resourcePath.toString()));
        }
        return false;
    }

    @Override
    public void after(HttpHandlerRequest request, HttpHandlerResponse response) {

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
}
