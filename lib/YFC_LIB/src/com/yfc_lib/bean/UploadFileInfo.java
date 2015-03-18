package com.yfc_lib.bean;
import java.io.File;
import java.io.Serializable;

public class UploadFileInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String fileName;
	private File file;

	public UploadFileInfo() {
		super();
	}

	public UploadFileInfo(String fileName, File file) {
		super();
		this.fileName = fileName;
		this.file = file;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

}