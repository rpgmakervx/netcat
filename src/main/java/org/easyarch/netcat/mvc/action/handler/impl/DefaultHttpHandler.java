package org.easyarch.netcat.mvc.action.handler.impl;

import org.easyarch.netcat.context.HandlerContext;
import org.easyarch.netcat.http.protocol.HttpHeaderValue;
import org.easyarch.netcat.http.request.impl.HttpHandlerRequest;
import org.easyarch.netcat.http.response.impl.HttpHandlerResponse;
import org.easyarch.netcat.kits.file.FileKits;
import org.easyarch.netcat.mvc.action.handler.HttpHandler;

import java.io.File;
import java.util.regex.Pattern;

import static org.easyarch.netcat.kits.file.FileFilter.*;

/**
 * Created by xingtianyu on 17-3-9
 * 下午3:53
 * description:
 */

public class DefaultHttpHandler implements HttpHandler {

    @Override
    public void handle(HttpHandlerRequest request, HttpHandlerResponse response) throws Exception {
        HandlerContext context = request.getContext();
        String webView = context.getWebView();
        String uri = request.getRequestURI();
        Pattern pattern = Pattern.compile(IMAGEPATTERN);

        StringBuffer resourcePath = new StringBuffer();
        resourcePath.append(webView).append(uri);

        String suffix = uri.substring(uri.lastIndexOf(".") + 1, uri.length());
        int point = uri.lastIndexOf(".");
        if (point == -1){
            return ;
        }
        String filename = uri.substring(uri.lastIndexOf(File.separator),point);
        if (!FileKits.exists(resourcePath.toString())){
            return ;
        }
        if (suffix.endsWith(HTML)){
            response.html(resourcePath.toString());
        }else if (suffix.endsWith(CSS)){
            response.write(FileKits.read(resourcePath.toString()),HttpHeaderValue.CSS);
        }else if (suffix.endsWith(JS)){
            response.write(FileKits.read(resourcePath.toString()),HttpHeaderValue.JS);
        }else if (suffix.endsWith(JSON)){
            response.json(FileKits.read(resourcePath.toString()));
        }else if (pattern.matcher(suffix).matches()){
            response.image(FileKits.read(resourcePath.toString()));
        }else if (suffix.endsWith(EOT)||suffix.endsWith(TTF)){
            response.write(FileKits.read(resourcePath.toString()),HttpHeaderValue.TTF_EOT);
        }else if (suffix.endsWith(WOFF)){
            response.write(FileKits.read(resourcePath.toString()),HttpHeaderValue.WOFF);
        }else if (suffix.endsWith(SVG)){
            response.write(FileKits.read(resourcePath.toString()),HttpHeaderValue.SVG);
        }else if (suffix.endsWith(DOCX)||suffix.endsWith(DOC)){
            response.download(FileKits.read(resourcePath.toString()),filename,HttpHeaderValue.DOC);
        }else if (suffix.endsWith(XLS)||suffix.endsWith(XLSX)){
            response.download(FileKits.read(resourcePath.toString()),filename,HttpHeaderValue.XLS);
        }else if (suffix.endsWith(PDF)){
            response.download(FileKits.read(resourcePath.toString()),filename,HttpHeaderValue.PDF);
        }else{
            response.write(FileKits.read(resourcePath.toString()));
        }
    }
}
