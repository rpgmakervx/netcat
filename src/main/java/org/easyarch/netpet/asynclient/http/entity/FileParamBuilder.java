package org.easyarch.netpet.asynclient.http.entity;

import org.easyarch.netpet.kits.file.FileKits;
import org.easyarch.netpet.web.mvc.entity.UploadFile;

import java.io.File;

/**
 * Created by xingtianyu on 17-4-7
 * 下午6:21
 * description:
 */

public class FileParamBuilder {

    private UploadFile file;

    private String paramName;


    public FileParamBuilder buildFileParam(String paramName,String path){
        return buildFileParam(paramName,new File(path).getPath(), FileKits.readx(path));
    }

    /**
     * 如果已经构造过fileParam，后续再次调用这个方法
     * @param paramName
     * @param content
     * @return
     */
    public FileParamBuilder buildFileParam(String paramName,String filePath,byte[] content){
        this.file = new UploadFile(filePath, content,true);
        this.file.setContentType("application/x-zip-compressed");
        this.paramName = paramName;
        return this;
    }

    public FileParam build(){
        if (this.file == null||paramName == null){
            throw new IllegalArgumentException("Please call method buildFileParam first,params were not ready");
        }
        return new FileParam(this.paramName,this.file);
    }
}
