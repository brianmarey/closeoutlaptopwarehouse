package com.careydevelopment.clw;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xml.sax.helpers.DefaultHandler;

public abstract class BaseHandler extends DefaultHandler {
	
	protected Map<String,Product> addedProducts = new HashMap<String,Product>();
	
	public List<Product> getProducts() {
		List<Product> products = new ArrayList<Product>();
		
		for (String key : addedProducts.keySet()) {
			Product p = addedProducts.get(key);
			products.add(p);
		}
		
		return products;
	}

}
