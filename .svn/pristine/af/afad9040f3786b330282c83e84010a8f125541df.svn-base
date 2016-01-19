package com.ymca.web.controller;

import java.io.StringBufferInputStream;
import java.io.StringReader;

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

/**
 * This is a sample application that demonstrates
 * how to use the Jakarta HttpClient API.
 *
 * This application sends an XML document
 * to a remote web server using HTTP POST
 *
 * @author Sean C. Sullivan
 * @author Ortwin Gl�ck
 * @author Oleg Kalnichevski
 */
public class ProcessPaymentByTokenId  extends BaseController {

    /**
     *
     * Usage:
     * java PostXML http://mywebserver:80/ c:\foo.xml
     *
     * @param args command line arguments
     * Argument 0 is a URL to a web server
     * Argument 1 is a local filename
     *
     */
    public static void main(String[] args) throws Exception {

        // Get target URL
        String strURL = "https://test1.jetpay.com/jetpay";     
        
        // Prepare HTTP post
        HttpPost post = new HttpPost(strURL);        
        StringBuffer inputXMLStrBuffer = new StringBuffer("<JetPay>");
        inputXMLStrBuffer.append("<TransactionID>"+RandomStringUtils.randomAlphanumeric(18)+"</TransactionID>");	
        inputXMLStrBuffer.append("<TransactionType>AUTHONLY</TransactionType>");       
        inputXMLStrBuffer.append("<Token>KKLJJMLIKNJIKHKOIBOBOBCL</Token>");
        inputXMLStrBuffer.append("<TotalAmount>808</TotalAmount>");
        inputXMLStrBuffer.append("<TerminalID>TESTTERMINAL</TerminalID>");
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
        try {

        	HttpResponse httpResponse = httpclient.execute(post);
        	
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

            String transactionID = xpath.evaluate("/JetPayResponse/TransactionID", document);
            String actionCode = xpath.evaluate("/JetPayResponse/ActionCode", document);
            String responseText = xpath.evaluate("/JetPayResponse/ResponseText", document);

            System.out.println("transactionID=" + transactionID);
            System.out.println("actionCode=" + actionCode);
            System.out.println("responseText=" + responseText);
            System.out.println("____");


        } catch(Exception e){
        	log.error("Error  ",e);
        	
        } finally {
            // Release current connection to the connection pool 
            // once you are done
            post.releaseConnection();
        }
    }
}