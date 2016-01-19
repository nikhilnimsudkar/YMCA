package com.ymca.web.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.ResourceBundle;

public class propReaderForCurrentMemShip {
	
	public static  HashMap<String,String> loadPropInfo()
	{
		// TODO Auto-generated method stub
		HashMap<String,String> getPropr=new HashMap<String,String>();
		ResourceBundle rb = ResourceBundle.getBundle("app");
		Enumeration <String> keys = rb.getKeys();
		while (keys.hasMoreElements())
		{
			String key = keys.nextElement();
			String value = rb.getString(key);
			getPropr.put(key, value);
			/*//System.out.println(key + ": " + value);*/
		}
		
		
		
		return getPropr;
	}

}
