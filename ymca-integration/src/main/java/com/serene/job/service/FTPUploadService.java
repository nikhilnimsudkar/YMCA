package com.serene.job.service;

import java.util.Vector;

import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemOptions;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.Session;

public interface FTPUploadService {
	
	public void uploadToFTPServer(String strFile);
	
	public Session createConnection(String hostname, int port, String username, char password[], FileSystemOptions fileSystemOptions)
            throws FileSystemException;
	
	public String DownloadFilesFromSFTP(Session session, String SFTPWORKINGDIR);
	public String DownloadFileFromSFTPToLocal(String localFilePath, String remoteFilePath, Session session);
	public String DownloadFileFromSFTPToLocal(String localFilePath, String remoteFilePath, String hostName, String userName);
}
