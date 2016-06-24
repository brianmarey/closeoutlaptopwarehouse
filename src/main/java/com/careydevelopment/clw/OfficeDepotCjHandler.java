package com.careydevelopment.clw;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class OfficeDepotCjHandler extends BaseHandler {
	
	//private static final Logger LOGGER = Logger.getLogger(OfficeDepotCjHandler.class);
	
	private static final String VENDOR_NAME = "Office Depot";

	private Product product;
	private String content;
	private String category;
	private List<String> categoryList = new ArrayList<String>();
	private int counter = 0;
	
	public OfficeDepotCjHandler() {
	}
	
	protected String removeSpecialChar(String s, String toRemove, String toReplace) {
		int start = 0;
		while (start > -1) {
			start = s.indexOf(toRemove);
			if (start>-1) {
				String sub1 = s.substring(0,start);
				String sub2 = s.substring(start + toRemove.length(), s.length());
				s = sub1 + toReplace + sub2;
			}
		}
		
		return s;
	}
	
	protected String getRidOfCrap(String s) {
		StringBuilder sb = new StringBuilder();
		
		for (int i=0;i<s.length();i++) {
			int val = (int)s.charAt(i);
			if (val !=9) {
				sb.append(s.charAt(i));
			}
		}
		
		return sb.toString();
	}
	
		
	public void characters(char[] ch, int start, int length) throws SAXException {
		if (content == null) content = String.copyValueOf(ch, start, length).trim();
		else content +=  String.copyValueOf(ch, start, length).trim();
	}
	
	protected String stripChevrons(String input) {
		String output = "";
		int start = 0;
		
		while (input.indexOf(">",start) > -1) {
			//System.err.println(start + " " + input.indexOf(">",start));
			output = output + input.substring(start,input.indexOf(">",start));
			output = output + "&gt;";
			start = input.indexOf(">",start) + 1;
		}
		
		//if (output.indexOf("_")>-1) System.err.println("output is " + output);
		return output;
	}
	
	public void startElement(String uri, String localName, String qName, 
			Attributes attributes) throws SAXException {
			
		if (qName.equals("product")) {
			//System.err.println("creating new product");
			product = new Product();
		}
	}
		
	public void endElement(String uri, String localName, String qName) 
				throws SAXException {
		/*if (qName.equals("keywords")) {
			if (!categoryList.contains(content)) {
				System.err.println(content);
				categoryList.add(content);
			}
		}*/
		
		if (qName.equals("name")) {
			product.setName(content);
		} else if (qName.equals("sku")) {
			product.setSku(content);
		} else if (qName.equals("imageurl")) {
			product.setImageUrl(content);
		} else if (qName.equals("description")) {
			product.setDescription(content);
		} else if (qName.equals("manufacturer")) {
			product.setManufacturer(content);
		} else if (qName.equals("upc")) {
			product.setUpc(content);
		} else if (qName.equals("saleprice")) {
			product.setPrice(content);
		} else if (qName.equals("price")) {
			product.setRetailPrice(content);
		} else if (qName.equals("buyurl")) {
			product.setBuyUrl(content);
		} else if (qName.equals("manufacturerid")) {
			product.setManufacturerId(content);
		} else if (qName.equals("programname")) {
			product.setProgramName(content);
		} else if (qName.equals("programurl")) {
			product.setProgramUrl(content);
		} else if (qName.equals("catalogname")) {
			product.setCatalogName(content);
		} else if (qName.equals("lastupdated")) {
			product.setLastUpdated(content);
		} else if (qName.equals("instock")) {
			if (content!= null && content.equals("yes")) {
				product.setInStock("instock");
			} else {
				product.setInStock("outofstock");
			}
		} else if (qName.equals("condition")) {
			product.setCondition(content);
		} else if (qName.equals("keywords")) {
			product.setKeywords(content);
		}  
		else if (qName.equals("advertisercategory")) {
			product.setAdvertiserCategory(content);
			//System.err.println("Setting advertiser cat to " + content + " for " + product.getName());
			category = content;
			
			if (!categoryList.contains(content)) {
				categoryList.add(content);
			}
			
		} else if (qName.equals("impressionurl")) {
			product.setImpressionUrl(content);
		} else if (qName.equals("catalog")) {
			
			for (String key : addedProducts.keySet()) {
				product = addedProducts.get(key);
				System.err.println("Writing " + product.getName() + product.getAdvertiserCategory() + product.getBuyUrl());
				//setAdditionalInfo();
				
				//pef.writeProduct(product);						
				//writeToDatabase("Office Depot", "officedepot");
			}
			//LOGGER.debug(counter);
			//pef.close();
			
			/*for (String cat : categoryList) {
				System.err.println(cat);
			}*/
		} else if (qName.equals("product")) {
			//if (counter<3) {
			String newCategory = translateCategory(product.getAdvertiserCategory(),product.getName());
			if (newCategory == null) {
				//System.err.println("couldn't translate " + product.getName() + " >" + product.getAdvertiserCategory());
			} else {
				Set<String> ads = addedProducts.keySet();
				if (!ads.contains(product.getName())) {
					product.setAdvertiserCategory(newCategory);
					product.setVendor(VENDOR_NAME);
					//setAdditionalInfo();

					addedProducts.put(product.getName(), product);
					counter++;
				}
			}
			//}
		}
		
		content = null;
	}
		
	
	private String translateCategory(String category,String name) {
		String newCategory = null;
		
		Float price = new Float(product.getPrice());
		if (price > 500 || price < 200) {
			return null;
		}
		
		if (name.toLowerCase().indexOf("refurbished") > -1) {
			return null;
		}
		
		if (name.toLowerCase().indexOf("tablet") > -1) {
			return null;
		}
		
		if (name.toLowerCase().indexOf("android") > -1) {
			return null;
		}

		if (name.toLowerCase().indexOf("briefcase") > -1) {
			return null;
		}
		
		if (name.toLowerCase().indexOf("backpack") > -1) {
			return null;
		}
		
		if (category != null) {
			if (category.indexOf(">Laptop Computers") > -1) {
				System.err.println("Adding " + name);
				return "Laptops";
			}
		}
		
		return newCategory;
	}

}
