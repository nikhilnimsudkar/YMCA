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
import org.springframework.http.HttpMethod;
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
public class ProcessAchTransaction extends BaseController {

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
        // New ACH Transaction
        HttpPost post = new HttpPost(strURL);    
        
        StringBuffer inputXMLStrBuffer = new StringBuffer("<JetPay>");
        inputXMLStrBuffer.append("<TransactionType>CHECK</TransactionType>");
        inputXMLStrBuffer.append("<TerminalID>TESTMERCHANT</TerminalID>");
        inputXMLStrBuffer.append("<TransactionID>"+RandomStringUtils.randomAlphanumeric(18)+"</TransactionID>");
        inputXMLStrBuffer.append("<CardName>Mickey Mouse</CardName>");
        inputXMLStrBuffer.append("<TotalAmount>1399</TotalAmount>");
        //inputXMLStrBuffer.append("<FeeAmount>100</FeeAmount>");
        inputXMLStrBuffer.append("<ACH Type=\"SAVINGS\" SEC=\"PPD\">");
        inputXMLStrBuffer.append("<AccountNumber>123411</AccountNumber>");
        inputXMLStrBuffer.append("<ABA>122000496</ABA>");
        inputXMLStrBuffer.append("<CheckNumber>15</CheckNumber>");
        inputXMLStrBuffer.append("</ACH>");
        inputXMLStrBuffer.append("</JetPay>");
        
        
        //Token Based Process
        /*StringBuffer inputXMLStrBufferToken = new StringBuffer("<JetPay>");
        inputXMLStrBufferToken.append("<TransactionID>"+RandomStringUtils.randomAlphanumeric(18)+"</TransactionID>");
        inputXMLStrBufferToken.append("<TransactionType> CHECK </TransactionType>");
        inputXMLStrBufferToken.append("<TerminalID> TESTTERMINAL </TerminalID>");
        inputXMLStrBufferToken.append("<Origin> INTERNET </Origin>");
        inputXMLStrBufferToken.append("<TotalAmount>599500</TotalAmount>");
        inputXMLStrBufferToken.append("<CardName> Fred Furtz </CardName>");
        inputXMLStrBufferToken.append("<ACH>");
        inputXMLStrBufferToken.append("<AccountNumber Tokenize='true'> 1982597 </AccountNumber>");
        inputXMLStrBufferToken.append("<ABA > 061120767 </ABA>");
        inputXMLStrBufferToken.append("<CheckNumber > 1234 </CheckNumber>");
        inputXMLStrBufferToken.append("</ACH>");
        inputXMLStrBufferToken.append("</JetPay>");*/
        
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

            //System.out.println("transactionID=" + transactionID);
            //System.out.println("actionCode=" + actionCode);
            //System.out.println("responseText=" + responseText);


        } catch(Exception e){
        	log.error("Error  ",e);
        	
        } finally {
            // Release current connection to the connection pool 
            // once you are done
            post.releaseConnection();
        }
    }
}