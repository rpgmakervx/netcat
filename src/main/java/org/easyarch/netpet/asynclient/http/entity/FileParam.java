package org.easyarch.netpet.asynclient.http.entity;

import org.easyarch.netpet.web.mvc.entity.UploadFile;

/**
 * Created by xingtianyu on 17-4-7
 * 下午4:21
 * description:
 */

public class FileParam {

    private String paramName;

    private UploadFile file;

    public FileParam(String paramName, UploadFile file) {
        this.paramName = paramName;
        this.file = file;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public UploadFile getFile() {
        return file;
    }

    public void setFile(UploadFile file) {
        this.file = file;
    }
}
