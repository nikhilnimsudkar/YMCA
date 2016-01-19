package com.ymca.web.controller;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.ymca.model.JetPayPayment;

public class ProcessACHPaymentByTokenId  extends BaseController {
	
	
	/**
	 * @param args
	 * @throws UnsupportedEncodingException 
	 */
	public static void main(String[] args) throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
		
		String strURL = "https://test1.jetpay.com/jetpay";         
		// New ACH Transaction
		HttpPost post = new HttpPost(strURL);    

		StringBuffer inputXMLStrBuffer = new StringBuffer("<JetPay>");
		inputXMLStrBuffer.append("<TransactionID>"+RandomStringUtils.randomAlphanumeric(18)+"</TransactionID>");
		inputXMLStrBuffer.append("<TransactionType> CHECK </TransactionType>");
		inputXMLStrBuffer.append("<TerminalID> TESTTERMINAL </TerminalID>");
		inputXMLStrBuffer.append("<Origin> INTERNET </Origin>");
		inputXMLStrBuffer.append("<TotalAmount> 100 </TotalAmount>");
		//inputXMLStrBuffer.append("<FeeAmount>100</FeeAmount>");
		inputXMLStrBuffer.append("<CardName> Kate Thompson </CardName>");
		inputXMLStrBuffer.append("<Token> KKHIHCOIKJJHHCKCKLHMJKJK </Token>");
		inputXMLStrBuffer.append("<ACH>");
		inputXMLStrBuffer.append("<CheckNumber> 1234 </CheckNumber>");
		inputXMLStrBuffer.append("</ACH>");
		inputXMLStrBuffer.append("</JetPay>");
		// Request content will be retrieved directly
		// from the input stream
		// Per default, the request content needs to be buffered
		// in order to determine its length.
		// Request body buffering can be avoided when
		// content length is explicitly specified
		post.setEntity(new StringEntity(inputXMLStrBuffer.toString()));
		//, inputXMLStrBuffer.toString().length())

		// Specify content type and encoding
		// If content encoding is not explicitly specified
		// ISO-8859-1 is assumed
		post.addHeader("Content-type", "text/xml; charset=ISO-8859-1");

		// Get HTTP client
		HttpClient httpclient = HttpClients.createDefault();

		// Execute request
		JetPayPayment jetPayPayment =  new JetPayPayment();
		try {

			HttpResponse httpResponse =  httpclient.execute(post);
			

			// Display status code
			//System.out.println("Response status code: " + result);

			// Display response
			//System.out.println("Response body: ");
			//System.out.println(post.getResponseBodyAsString());
					
			
			String xml = EntityUtils.toString(httpResponse.getEntity());           
			InputSource source = new InputSource(new StringReader(xml));

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(source);

			XPathFactory xpathFactory = XPathFactory.newInstance();
			XPath xpath = xpathFactory.newXPath();

			//System.out.println(xpath.evaluate("/JetPayResponse/TransactionID", document));
			//System.out.println(xpath.evaluate("/JetPayResponse/ActionCode", document));
			//System.out.println(xpath.evaluate("/JetPayResponse/ResponseText", document));
            
		} catch(Exception e){
			log.error("Error  ",e);
			
		} finally {
			// Release current connection to the connection pool 
			// once you are done
			post.releaseConnection();
		}

	}

}
