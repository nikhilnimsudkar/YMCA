package com.serene.job.service;

import java.io.IOException;

public interface GPGFileEncryptionService {
	
	public void GPGFileEncrypt(String inFile, String outFile) throws IOException;
	public String GPGFileDecrypt(String file, String passphraseFile);
}
