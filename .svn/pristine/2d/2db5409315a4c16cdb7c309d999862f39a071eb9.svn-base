package com.serene.job.reader;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Vector;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.provider.sftp.SftpFileSystemConfigBuilder;

import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.Session;
import com.serene.job.common.BaseService;
import com.serene.job.common.BatchJobContext;
import com.serene.job.common.Constants;
import com.serene.job.dao.IntermidiateTableDao;
import com.serene.job.dao.JobSchedulerMetadataDao;
import com.serene.job.model.IntermediateTable;
import com.serene.job.model.IntermediateTablePrimaryKey;
import com.serene.job.model.JobSchedulerMetadata;
import com.serene.job.service.FTPUploadService;
import com.serene.job.service.GPGFileEncryptionService;
import com.serene.job.service.SystemPropertyService;

@Service
@Lazy(true)
@Scope("prototype")
public class JetPayItemReader implements ItemReader<Map<String,Object>> ,StepExecutionListener {
	
	/*
	 * @author: Lavy Toteza
	 */
	
	private Logger log = LoggerFactory.getLogger(JetPayItemReader.class);
	
	private Queue<Map<String,Object>> items ;
	
	@Resource
	private BatchJobContext  batchJobContext ;
	
	@Resource
	private FTPUploadService ftpUploadService ;
	
	@Autowired
	private SystemPropertyService systemPropertyService;
	
	@Resource
	private GPGFileEncryptionService gpgFileEncryptionService ;
	
	@Resource
	private JobSchedulerMetadataDao jobSchedulerMetadataDao ;
	
	@Resource
	private IntermidiateTableDao intermidiateTableDao ; 
	
	//private Session session = null;
	
	@Override
	public void beforeStep(StepExecution stepExecution) {
		// TODO Auto-generated method stub
		log.debug("Inside beforeStep method of JetPayItemReader");
		
		items = new LinkedBlockingQueue<Map<String,Object>>();
		
		String localFilePath = null;
		String downloadedFilePath = System.getProperty("java.io.tmpdir");
		if ( !(downloadedFilePath.endsWith("/") || downloadedFilePath.endsWith("\\")) )
			downloadedFilePath = downloadedFilePath + System.getProperty("file.separator");
		
		String passphraseFile = "/opt/apache-tomcat-8.0.18_int/temp/pass.txt";
		String processedFileName = null;
		String MERCHANT_ID = "YMCA07021001";//systemPropertyService.getPropertyByKeyName(Constants.JETPAY_MERCHANT_ID);
		
		// get sftp details from system prop 
		JobSchedulerMetadata jobSchedulerMetadata = batchJobContext.getJobSchedulerMetadata();
		String hostName = jobSchedulerMetadata.getFromQuery();
	    String username = jobSchedulerMetadata.getFromObjectName();
	    log.debug("params: hostName "+hostName + " username: "+username);
        try {
        	//session = ftpUploadService.createConnection(hostName,22,username,null,createDefaultOptions());
			//log.error("DEBUG - SFTP Connection was successful... ");

			//if(session!=null){
				String SFTPWORKINGDIR = "/"+jobSchedulerMetadata.getFromDataSource()+"/";
				String lstFileNames = jobSchedulerMetadata.getInterfaceLastPoolTime();
				log.debug("DEBUG - filenames found as... "+lstFileNames);
				if(lstFileNames!=null && !"".equals(lstFileNames)){
					List<String> files = Arrays.asList(lstFileNames.split(","));
					//localFilePath = ftpUploadService.DownloadFilesFromSFTP(session,SFTPWORKINGDIR);
					processedFileName = files.get(0);
					
					String localFile = downloadedFilePath + processedFileName +".RSP";
					log.debug("DEBUG - local file found as... "+localFile);
					String remoteFile = SFTPWORKINGDIR + (new File(processedFileName+".RSP")).getName();
					log.debug("DEBUG - remote file found as... "+remoteFile);
					localFilePath = ftpUploadService.DownloadFileFromSFTPToLocal(localFile, remoteFile, hostName, username);
					log.debug("DEBUG - downloaded file path: "+localFilePath);
				}
			//}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("Error while reading file on JetPayItemReader:- ",e);
		} 
        
        if(localFilePath!=null){
        	String result = gpgFileEncryptionService.GPGFileDecrypt(localFilePath, passphraseFile);
        	
        	log.debug("DEBUG - Decrypted file contents "+result);
        	
        	try {
				List<String> lstFileContent = Arrays.asList(result.split("\n"));
				if(lstFileContent.size()>0){
					boolean validFile = false;
					for(String fileContent: lstFileContent){
						log.debug("DEBUG - file contents line by line "+fileContent);
						List<String> fileData = Arrays.asList(fileContent.split(","));
						if(fileData.size()>1){
							log.debug("DEBUG - fileData.get(0) "+fileData.get(0) + " fileData.get(2) " + fileData.get(2) + " MERCHANT_ID "+ MERCHANT_ID);
							// validate file with header
							if("FH".equalsIgnoreCase(fileData.get(0).trim()) && fileData.get(2).trim().equalsIgnoreCase(MERCHANT_ID.trim())){
								validFile = true;
								log.debug("DEBUG - Inside validate file");
							}
							log.debug("DEBUG - validFile "+validFile);
							// process credit card record
							if(validFile && "DR".equalsIgnoreCase(fileData.get(0).trim())){
								log.debug("DEBUG - Inside DR block ");
								//for(Object str: fileData){
									Map<String,Object> obj = new HashMap<String,Object>();
									log.debug("DEBUG - fileData " +fileData);
									obj.put("AUTHORIZATION_CODE", fileData.get(12));
									obj.put("RESPONSE_CODE", fileData.get(13));
									
									String refNo = fileData.get(16);
									if(refNo!=null){
										List refNoSplit = Arrays.asList(refNo.trim().split("_"));
										if(refNoSplit.size()==2){
											String invoiceId = refNoSplit.get(0).toString();
											String paymentId = refNoSplit.get(1).toString();
											
											obj.put("paymentId", paymentId);
											obj.put("invoiceId", invoiceId);
										}
									}
									log.debug("DEBUG - Inside DR block " +obj);
									
									items.add(obj);
								//}
							}
							// process ACH record
							if(validFile && "CH".equalsIgnoreCase(fileData.get(0).trim())){
								log.debug("DEBUG - Inside CH block ");
								
								Map<String,Object> obj = new HashMap<String,Object>();
								log.debug("DEBUG - fileData " +fileData);
								obj.put("AUTHORIZATION_CODE", "ACH");
								obj.put("RESPONSE_CODE", fileData.get(10));
								
								String refNo = fileData.get(12);
								if(refNo!=null){
									List refNoSplit = Arrays.asList(refNo.trim().split("_"));
									if(refNoSplit.size()==2){
										String invoiceId = refNoSplit.get(0).toString();
										String paymentId = refNoSplit.get(1).toString();
										
										obj.put("paymentId", paymentId);
										obj.put("invoiceId", invoiceId);
									}
								}
								log.debug("DEBUG - Inside CH block " +obj);
								
								items.add(obj);
							}
				    		
						}
					}
				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				log.error("Error while processing jetpay response file on JetPayItemReader:- ",e1);
			}
        	
        	try {
				// update batch job metadata table remove filename
				if(processedFileName!=null){
					log.debug("DEBUG - processedFileName "+processedFileName);
					String lstFileNames = jobSchedulerMetadata.getInterfaceLastPoolTime();
					log.debug("DEBUG - lstFileNames "+lstFileNames);

					List<String> lstFileNamesStr = new LinkedList<String>(Arrays.asList(lstFileNames.split(",")));
					log.debug("DEBUG - lstFileNamesStr "+lstFileNamesStr);
					lstFileNamesStr.remove(processedFileName);
					log.debug("DEBUG - lstFileNamesStr2 "+lstFileNamesStr);
					if(lstFileNamesStr!=null && lstFileNamesStr.size()>0)
						jobSchedulerMetadata.setInterfaceLastPoolTime(StringUtils.join(lstFileNamesStr,','));
					else
						jobSchedulerMetadata.setInterfaceLastPoolTime("");
					jobSchedulerMetadataDao.save(jobSchedulerMetadata);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error("Error while updating jobmetadata table on JetPayItemReader:- ",e);
			}
        }
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		// TODO Auto-generated method stub
		log.error("Inside afterStep method of JetPayItemReader");
		
		//cleanup resource
		items.clear();

		return null;
	}

	@Override
	public Map<String, Object> read() throws Exception,
			UnexpectedInputException, ParseException,
			NonTransientResourceException {
		// TODO Auto-generated method stub
		log.error("Inside read method of JetPayItemReader "+items);
		if(items!=null && items.size()>0){
			return items.poll();
		}
		
		return null;
	}

}
