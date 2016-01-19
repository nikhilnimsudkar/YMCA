package com.serene.job.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.serene.job.common.Constants;
import com.serene.job.util.JobUtils;

@Service
public class GPGFileEncryptionServiceImpl implements GPGFileEncryptionService {
	
private static final Log log = LogFactory.getLog(GPGFileEncryptionServiceImpl.class);
	
	@Resource
	private JobUtils jobUtils;
	
	@Autowired
	private SystemPropertyService systemPropertyService;
	
	@Override
	public void GPGFileEncrypt(String inFile, String outFile) throws IOException {
		String keyToEncrypt = systemPropertyService.getPropertyByKeyName(Constants.JETPAY_KEY_FOR_FILE_ENCRYPTION);
		
		StringBuilder gpgCommand = new StringBuilder("gpg --recipient \"");
	    gpgCommand.append(keyToEncrypt).append("\" --output \"").append(outFile).append("\" --yes --encrypt \"");
	    gpgCommand.append(inFile).append("\"");

	    log.debug("DEBUG - ENCRYPT COMMAND: " + gpgCommand);
		
		//Process sProcess = Runtime.getRuntime().exec("./etc/profile.d/gnupg.sh &");
		//Process gpgProcess = Runtime.getRuntime().exec(gpgCommand.toString());
		ProcessBuilder processBuilder = new ProcessBuilder("/bin/sh", "-c", gpgCommand.toString());
		Process gpgProcess = processBuilder.start();
		
		BufferedReader gpgOutput = new BufferedReader(new InputStreamReader(gpgProcess.getInputStream()));
		BufferedReader gpgErrorOutput = new BufferedReader(new InputStreamReader(gpgProcess.getErrorStream()));
		BufferedWriter gpgInput = new BufferedWriter(new OutputStreamWriter(gpgProcess.getOutputStream()));
		
		String result = null;
		boolean executing = true;

		while(executing){
		    try{
		        if(gpgErrorOutput.ready()){
		            result = getStreamText(gpgErrorOutput);
		            log.debug("DEBUG - Result:- "+result);
		            break;
		        }else if(gpgOutput.ready()){
		            result = getStreamText(gpgOutput);
		        }

		        int exitValue = gpgProcess.exitValue();
		        log.info("EXIT: " + exitValue);

		        executing = false;
		    }catch(IllegalThreadStateException e){
		    	log.error("Not yet ready.  Stream status: " + gpgOutput.ready() + ", error: " + gpgErrorOutput.ready());

		        try {
		            Thread.sleep(100);
		        } catch (InterruptedException e1) {
		            log.error("This thread has insomnia: " + e1.getMessage());
		        }
		    }
		}
		
		log.error("DEBUG - Final Rresult: " + result);
	}
	
	public String GPGFileDecrypt(String file, String passphraseFile){
		log.error("DEBUG - filename to encrypt: " + file + "passphrase file: "+passphraseFile);
	    String result = null;
	    StringBuilder command = new StringBuilder("gpg --no-tty --passphrase-fd 0 --decrypt \"");
	    command.append(file).append("\" 0<\"").append(passphraseFile).append("\"");             
	    log.debug("DECRYPT COMMAND: " + command.toString());
	    log.error("DEBUG - DECRYPT COMMAND: " + command.toString());
	    try {

	        //Process gpgProcess = Runtime.getRuntime().exec(command.toString());
	    	ProcessBuilder processBuilder = new ProcessBuilder("/bin/sh", "-c", command.toString());
			Process gpgProcess = processBuilder.start();

	        BufferedReader gpgOutput = new BufferedReader(new InputStreamReader(gpgProcess.getInputStream()));
	        BufferedReader gpgErrorOutput = new BufferedReader(new InputStreamReader(gpgProcess.getErrorStream()));
	        BufferedWriter gpgInput = new BufferedWriter(new OutputStreamWriter(gpgProcess.getOutputStream()));

	        boolean executing = true;

	        while(executing){
	            try{
	            	log.error("gpgOutput : " +gpgOutput.ready());
	            	//log.error("gpgOutput1 : " +getStreamText(gpgOutput));
	            	
	                /*if(gpgErrorOutput.ready()){
	                    result = getStreamText(gpgErrorOutput);
	                    log.error("Error Output: " +result);
	                    break;
	                }else*/ if(gpgOutput.ready()){
	                    result = getStreamText(gpgOutput);
	                    log.error("Output: " +result);
	                }

	                int exitValue = gpgProcess.exitValue();
	                log.error("EXIT: " + exitValue);

	                executing = false;
	            }catch(IllegalThreadStateException e){
	            	log.error("Not yet ready.  Stream status: " + gpgOutput.ready() + ", error: " + gpgErrorOutput.ready());

	                try {
	                    Thread.sleep(100);
	                } catch (InterruptedException e1) {
	                	log.error("This thread has insomnia: " + e1.getMessage());
	                }
	            }
	        }
	    } catch (IOException e) {
	    	log.error("Unable to execute GPG decrypt command via command line: " + e.getMessage());
	    }
	    
	    log.error("Out of loop with result: " +result);
	    return result;
	}
	
	private String getStreamText(BufferedReader reader) throws IOException{
	    StringBuilder result = new StringBuilder();
	    try{
	        while(reader.ready()){
	            result.append(reader.readLine());
	            //log.error("DEBUG - reading the stream: " + reader.readLine());
	            if(reader.ready()){
	                result.append("\n");
	            }
	        }
	    }catch(IOException ioe){
	    	log.error("Error while reading the stream: " + ioe.getMessage());
	        throw ioe;
	    }
	    return result.toString();
	}
	
}
