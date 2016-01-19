package com.ymca;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class TestingXMl {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try{
			String xml = "<bookstore><book category='cooking'><title lang='en'>Everyday Italian</title><author>Giada De Laurentiis</author><year>2005</year><price>30.00</price></book></bookstore>";			
			InputSource source = new InputSource(new StringReader(xml));
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(source);
			XPathFactory xpathFactory = XPathFactory.newInstance();
			XPath xpath = xpathFactory.newXPath();
			XPathExpression expr = xpath.compile("/bookstore/book/title");		
			NodeList nl = (NodeList) expr.evaluate(document, XPathConstants.NODESET);
			for (int i = 0; i < nl.getLength(); i++){
			    Node currentItem = nl.item(i);
			    String key = currentItem.getAttributes().getNamedItem("lang").getNodeValue();
			    System.out.println(key);
			}		
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		

	}
	
	
}
