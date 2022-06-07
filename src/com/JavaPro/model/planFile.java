package com.JavaPro.model;

import java.io.File;

/**
 * 文件实体类
 * @author 周文瑞 20373804
 */
public class planFile {
    private String fName;
    private File file;
    private byte[] content;

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
