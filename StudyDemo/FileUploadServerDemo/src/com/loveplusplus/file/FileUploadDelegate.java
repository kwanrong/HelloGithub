package com.loveplusplus.file;

@javax.jws.WebService(targetNamespace = "http://file.loveplusplus.com/", serviceName = "FileUploadService", portName = "FileUploadPort", wsdlLocation = "WEB-INF/wsdl/FileUploadService.wsdl")
public class FileUploadDelegate {

	com.loveplusplus.file.FileUpload fileUpload = new com.loveplusplus.file.FileUpload();

	public String upload(String json) {
		return fileUpload.upload(json);
	}

}