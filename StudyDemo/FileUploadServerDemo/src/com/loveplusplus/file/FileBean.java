package com.loveplusplus.file;
/** 
 * 类说明 
 * @author  程辉 
 * @version V1.0  创建时间：2013-1-25 下午1:47:06 
 */
public class FileBean {
	private String fileName;//文件名
	private String fileType;//文件类型，既后缀
	private String fileContent;//文件的内容
	private String fileLevel;//1代表多媒体附件标示，2代表选择自行处理时的多媒体附件标示
	private String fileUrl;//本地文件路径  用于离线保存时候使用
	
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	
	public String getFileContent() {
		return fileContent;
	}
	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}
	public String getFileLevel() {
		return fileLevel;
	}
	public void setFileLevel(String fileLevel) {
		this.fileLevel = fileLevel;
	}
}
