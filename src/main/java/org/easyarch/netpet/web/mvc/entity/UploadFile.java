package org.easyarch.netpet.web.mvc.entity;

import org.easyarch.netpet.kits.file.FileKits;

import java.io.File;

/**
 * Created by xingtianyu on 17-3-21
 * 下午3:48
 * description:
 */

public class UploadFile {

    private String fileName;

    private String path;

    private String contentType;

    private byte[] content = new byte[0];

    public UploadFile(String fileName, byte[] content,boolean isPath) {
        if (isPath){
            this.path = fileName;
            this.fileName = FileKits.getName(fileName);
        }else{
            this.fileName = fileName;
        }
        this.content = content;
    }
    public UploadFile(String fileName,boolean isPath) {
        if (isPath){
            this.path = fileName;
            this.fileName = FileKits.getName(fileName);
        }else{
            this.fileName = fileName;
        }
        this.fileName = fileName;
        this.content = FileKits.readx(this.path);
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public File getFile(){
        return new File(fileName);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "fileName='" + fileName + '\'' +
                ", contentType='" + contentType + '\'' +
                ", content length=" + content.length;
    }
}
