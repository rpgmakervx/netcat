package org.easyarch.netcat.web.mvc.action.filter.impl;

import org.easyarch.netcat.web.http.request.impl.HttpHandlerRequest;
import org.easyarch.netcat.web.http.response.impl.HttpHandlerResponse;
import org.easyarch.netcat.web.mvc.action.filter.HttpFilter;

/**
 * Created by xingtianyu on 17-3-16
 * 上午11:42
 * description:处理静态页面的过滤器
 * 当请求在webView+prefix+viewName+"."+suffix 找不到，或请求uri的前缀是WEB-INF时.静态页面过滤器不处理该请求。
 */
@Deprecated
public class StaticFilter implements HttpFilter {

    @Override
    public boolean before(HttpHandlerRequest request, HttpHandlerResponse response) {
//        HandlerContext context = request.getContext();
//        String webView = context.getWebView();
//        String uri = request.getRequestURI();
//        Pattern cachedPattern = Pattern.compile(CACHEPATTERN);
//
//        StringBuffer resourcePath = new StringBuffer();
//        resourcePath.append(webView).append(uri);
//        int point = uri.lastIndexOf(".");
//        String suffix = "";
//        String prefix = "";
//        String filename = "";
//        if (!uri.equals("/")) {
//            String[] segement = uri.split("/");
//            prefix = "/" + segement[1] + "/";
//        }
//        if (point != -1) {
//            suffix = uri.substring(point, uri.length());
//            filename = uri.substring(uri.lastIndexOf(File.separator), point);
//        } else {
//            filename = uri.substring(uri.lastIndexOf(File.separator), uri.length());
//        }
//        if (HandlerContext.WEB_INF.equals(prefix)
//                || !FileKits.exists(resourcePath.toString())) {
//            return true;
//        }
//        if (cachedPattern.matcher(suffix).matches()) {
//            checkStrongCache(request, response);
//            boolean cached = checkNagoCache(request, response, suffix, resourcePath.toString());
//            if (cached) {
//                response.write();
//            }
//            response.write(FileKits.read(resourcePath.toString())
//                    , HttpHeaderValue.getContentType(suffix));
//        } else if (suffix.endsWith(DOCX) || suffix.endsWith(DOC)) {
//            response.download(FileKits.read(resourcePath.toString()), filename, HttpHeaderValue.DOC);
//        } else if (suffix.endsWith(XLS) || suffix.endsWith(XLSX)) {
//            response.download(FileKits.read(resourcePath.toString()), filename, HttpHeaderValue.XLS);
//        } else if (suffix.endsWith(PDF)) {
//            response.download(FileKits.read(resourcePath.toString()), filename, HttpHeaderValue.PDF);
//        } else {
//            response.write(FileKits.read(resourcePath.toString()));
//        }
        return false;
    }

    @Override
    public void after(HttpHandlerRequest request, HttpHandlerResponse response) {

    }

//    private void checkStrongCache(HttpHandlerRequest request, HttpHandlerResponse response) {
//        if (!request.getContext().isStrongCache()) {
//            return;
//        }
//        response.setHeader(HttpHeaderName.CACHE_CONTROL,
//                HttpHeaderValue.MAXAGE + String.valueOf(request.getContext().getMaxAge()));
//    }
//
//    private boolean checkNagoCache(HttpHandlerRequest request, HttpHandlerResponse response, String type, String resourcePath) {
//        if (!request.getContext().isNegoCache()) {
//            return false;
//        }
//        String ifNoneMatch = request.getHeader(HttpHeaderName.IF_NONE_MATCH);
//        String etag = FileKits.md5(resourcePath);
//        String lastModify = TimeKits.getGMTTime(FileKits.getLastModifyTime(resourcePath));
//        String lastModifySince = request.getHeader(HttpHeaderName.IF_MODIFIED_SINCE);
//        if (etag.equals(ifNoneMatch)) {
//            if (lastModify.equals(lastModifySince)) {
//                response.setStatus(HttpStatus.NOT_MODIFIED);
//                return true;
//            }
//        }
//        response.setHeader(HttpHeaderName.LAST_MODIFIED, lastModify);
//        response.setHeader(HttpHeaderName.ETAG, etag);
//        return false;
//    }

}
