package com.careydevelopment.clw;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class SearsCjHandler extends BaseHandler {
	
	//private static final Logger LOGGER = LoggerFactory.
	
	private static final String VENDOR_NAME = "Sears";

	private Product product = new Product();
	private String content;
	private String category;
	private List<String> categoryList = new ArrayList<String>();
	private int counter = 0;

	
	public SearsCjHandler() {
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
				//System.err.println(content);
				categoryList.add(content);
			}
			product.setKeywords(content);
		}*/
		
		//System.err.println(qName + "--" + content);
		
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
			for (String cat : categoryList) {
				System.err.println(cat);
			}
			
			System.err.println("There are " + addedProducts.size() + " products");
			
			//ImageFileShrinker shrinker = new ImageFileShrinker();
			
			int count = 0;
			for (String key : addedProducts.keySet()) {
				product = addedProducts.get(key);
				
				count++;
				
				System.err.println(count + " Writing " + product.getName() + product.getPrice() + " " + product.getAdvertiserCategory());
				
				//pef.writeProduct(product);						
				//writeToDatabase("Sears", "sears");
				/*if (count>6000) {
					break;
				}*/
			}
			//LOGGER.debug(counter);
			//pef.close();
		} else if (qName.equals("product")) {
			//if (counter<3) {
			String newCategory = translateCategory(product.getAdvertiserCategory(),product.getName(),product);
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
	
	
	private String translateCategory(String category,String name,Product product) {
		String newCategory = null;
		
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
		
		if (name.toLowerCase().indexOf("improvement") > -1) {
			return null;
		}
		
		Float price = new Float(product.getPrice());
		if (price > 500 || price < 200) {
			return null;
		}
		 
		
		/*if (category.indexOf("Electronics") > -1 && name.toLowerCase().indexOf("desktop pc") > -1) {
			return CareyCategories.PC_DESKTOPS;
		}
		
		
		if (category.indexOf("Cooking Appliances") > -1 && name.toLowerCase().indexOf("microwave") > -1) {
			return CareyCategories.MICROWAVES;
		}*/
		
		
		/*if (category.indexOf("Top Freezer Refrigerators") > -1) {
			//product.setAttMap(attMap);
			Map<String,String> attMap = new HashMap<String,String>();
			//attMap.put(CareyAttributes.LABEL_REFRIGERATOR_CONFIG_TYPE, CareyAttributes.REFRIG_FREEZER_ON_TOP);
			//product.setAttMap(attMap);
			return CareyCategories.REFRIGERATORS_FREEZER_ON_TOP;
		}

		if (category.indexOf("Single Door Bottom Freezer Refrigerators") > -1) {
			//product.setAttMap(attMap);
			Map<String,String> attMap = new HashMap<String,String>();
			//attMap.put(CareyAttributes.LABEL_REFRIGERATOR_CONFIG_TYPE, CareyAttributes.REFRIG_FREEZER_ON_BOT);
			//product.setAttMap(attMap);
			return CareyCategories.REFRIGERATORS_FREEZER_ON_BOT;
		}
		
		if (category.indexOf("French Door Refrigerators") > -1) {
			//product.setAttMap(attMap);
			//Map<String,String> attMap = new HashMap<String,String>();
			//attMap.put(CareyAttributes.LABEL_REFRIGERATOR_CONFIG_TYPE, CareyAttributes.REFRIG_FRENCH_DOOR);
			//product.setAttMap(attMap);
			return CareyCategories.REFRIGERATORS_FRENCH;
		}
		
		if (category.indexOf("Side-by-Side Refrigerators") > -1) {
			//product.setAttMap(attMap);
			//Map<String,String> attMap = new HashMap<String,String>();
			//attMap.put(CareyAttributes.LABEL_REFRIGERATOR_CONFIG_TYPE, CareyAttributes.REFRIG_SIDE_BY_SIDE);
			//product.setAttMap(attMap);
			return CareyCategories.REFRIGERATORS_SIDE;
		}
	
		
		if (category.indexOf("Wine Cellars") > -1) {
			return CareyCategories.WINE_CELLARS;
		}
		
		if (category.indexOf("Electric Dryers") > -1) {
			return CareyCategories.DRYERS;
		}

		if (category.indexOf("Front Load Washers") > -1) {
			return CareyCategories.WASHERS;
		}

		if (category.indexOf("Electric Ranges") > -1) {
			//Map<String,String> attMap = new HashMap<String,String>();
			//attMap.put(CareyAttributes.LABEL_RANGE_FUEL_TYPE, CareyAttributes.RANGE_FUEL_ELECTRIC);
			//product.setAttMap(attMap);
			return CareyCategories.RANGES_ELECTRIC;
		}
		
		if (category.indexOf("Gas Ranges") > -1) {
			//Map<String,String> attMap = new HashMap<String,String>();
			//attMap.put(CareyAttributes.LABEL_RANGE_FUEL_TYPE, CareyAttributes.RANGE_FUEL_GAS);
			//product.setAttMap(attMap);
			return CareyCategories.RANGES_GAS;
		}
		*/
		
		
		/*if (category.indexOf("Electronics") > -1 && name.toLowerCase().indexOf("led tv") > -1 && name.toLowerCase().indexOf("antenna") == -1 && name.toLowerCase().indexOf("mount") == -1 && name.toLowerCase().indexOf("adapter") == -1) {
			return CareyCategories.TVS;
		}
		
		if (category.indexOf("Electronics") > -1 && name.toLowerCase().indexOf("hdtv") > -1 && name.toLowerCase().indexOf("antenna") == -1 && name.toLowerCase().indexOf("mount") == -1 && name.toLowerCase().indexOf("adapter") == -1) {
			return CareyCategories.TVS;
		}*/
		
		
		
		//if (category.indexOf("-Projectors") > -1) {
		//	return CareyCategories.PROJECTORS;
		//}
		
		
		/*if ((category.indexOf("Electronics") > -1 || category.indexOf("Other") > -1) && name.toLowerCase().indexOf("battery") == -1 && name.toLowerCase().indexOf("notebook") > -1 && (name.toLowerCase().indexOf("acer") > -1 || name.toLowerCase().indexOf("lenovo") > -1 || name.toLowerCase().indexOf("dell") > -1 || name.toLowerCase().indexOf("toshiba") > -1 || name.toLowerCase().indexOf("hp ") > -1)){
			return CareyCategories.PC_LAPTOPS;
		}*/
		
		/*if (category.indexOf("Electronics") > -1 && name.toLowerCase().indexOf("macbook") > -1) {
			return CareyCategories.MAC_LAPTOPS;
		}*/
		
		/*if ((category.indexOf("Electronics") > -1 || category.indexOf("Other") > -1) && name.toLowerCase().indexOf("battery") == -1 && name.toLowerCase().indexOf("touchscreen") > -1 && (name.toLowerCase().indexOf("acer") > -1 || name.toLowerCase().indexOf("lenovo") > -1 || name.toLowerCase().indexOf("dell") > -1 || name.toLowerCase().indexOf("toshiba") > -1 || name.toLowerCase().indexOf("hp ") > -1)) {
			return CareyCategories.PC_LAPTOPS;
		}
		
		if ((category.indexOf("Electronics") > -1 || category.indexOf("Other") > -1) && name.toLowerCase().indexOf("battery") == -1 && name.toLowerCase().indexOf("laptop") > -1 && (name.toLowerCase().indexOf("acer") > -1 || name.toLowerCase().indexOf("lenovo") > -1 || name.toLowerCase().indexOf("dell") > -1 || name.toLowerCase().indexOf("toshiba") > -1 || name.toLowerCase().indexOf("hp ") > -1)) {
			return CareyCategories.PC_LAPTOPS;
		}
		
		if ((category.indexOf("Electronics") > -1 || category.indexOf("Other") > -1) && name.toLowerCase().indexOf("battery") == -1 && name.toLowerCase().indexOf("chromebook") > -1 && (name.toLowerCase().indexOf("acer") > -1 || name.toLowerCase().indexOf("lenovo") > -1 || name.toLowerCase().indexOf("dell") > -1 || name.toLowerCase().indexOf("toshiba") > -1 || name.toLowerCase().indexOf("hp ") > -1)) {
			return CareyCategories.PC_LAPTOPS;
		}*/
		
		
		/*
		if (category.indexOf("Tower Only PCs") > -1) {
			return CareyCategories.PC_DESKTOPS;
		}
		*/
		
		
		/*if (category.indexOf("Women's Apparel") > -1 && name.toLowerCase().indexOf("bootcut") > -1) {
			//Map<String,String> attMap = new HashMap<String,String>();
			//attMap.put(CareyAttributes.LABEL_WOMENS_JEAN_TYPE, CareyAttributes.BOOTCUT_JEANS);
			//product.setAttMap(attMap);
			return CareyCategories.WOMEN_JEANS_BOOTCUT;
		}
		
		if (category.indexOf("Women's Apparel") > -1 && name.toLowerCase().indexOf("skinny jean") > -1) {
			//Map<String,String> attMap = new HashMap<String,String>();
			//attMap.put(CareyAttributes.LABEL_WOMENS_JEAN_TYPE, CareyAttributes.SKINNY_JEANS);
			//product.setAttMap(attMap);
			return CareyCategories.WOMEN_JEANS_SKINNY;
		}
		
		if (category.indexOf("Men's Apparel") > -1 && name.toLowerCase().indexOf("skinny jean") > -1) {
			//Map<String,String> attMap = new HashMap<String,String>();
			//attMap.put(CareyAttributes.LABEL_MENS_JEAN_TYPE, CareyAttributes.MENS_SKINNY_JEANS);
			//product.setAttMap(attMap);
			return CareyCategories.MEN_SKINNY_JEANS;
		}
		
		if (category.indexOf("Men's Apparel") > -1 && name.toLowerCase().indexOf("straight jean") > -1) {
			//Map<String,String> attMap = new HashMap<String,String>();
			//attMap.put(CareyAttributes.LABEL_MENS_JEAN_TYPE, CareyAttributes.MENS_STRAIGHT_JEANS);
			//product.setAttMap(attMap);
			return CareyCategories.MEN_STRAIGHT_JEANS;
		}
		
		if (category.indexOf("Men's Apparel") > -1 && name.toLowerCase().indexOf("khaki") > -1) {
			//Map<String,String> attMap = new HashMap<String,String>();
			//attMap.put(CareyAttributes.LABEL_MENS_PANTS_TYPE, CareyAttributes.CASUAL_PANTS);
			//product.setAttMap(attMap);
			return CareyCategories.MEN_CASUAL_PANTS;
		}
		
	
		
		if (category.indexOf("Men's Apparel") > -1 && name.toLowerCase().indexOf("polo") > -1 && name.toLowerCase().indexOf("shirt") > -1) {
			//Map<String,String> attMap = new HashMap<String,String>();
			//attMap.put(CareyAttributes.LABEL_MENS_SHIRT_TYPE, CareyAttributes.POLO);
			//product.setAttMap(attMap);
			return CareyCategories.MEN_POLO_SHIRTS;
		}
		
		if (category.indexOf("Men's Apparel") > -1 && name.toLowerCase().indexOf("henley") > -1) {
			//Map<String,String> attMap = new HashMap<String,String>();
			//attMap.put(CareyAttributes.LABEL_MENS_SHIRT_TYPE, CareyAttributes.HENLEY);
			//product.setAttMap(attMap);
			return CareyCategories.MEN_HENLEY_SHIRTS;
		}
		
		if (category.indexOf("Men's Apparel") > -1 && name.toLowerCase().indexOf("dress shirt") > -1) {
			//Map<String,String> attMap = new HashMap<String,String>();
			//attMap.put(CareyAttributes.LABEL_MENS_SHIRT_TYPE, CareyAttributes.DRESS_SHIRTS);
			//product.setAttMap(attMap);
			return CareyCategories.MEN_DRESS_SHIRTS;
		}*/
		
		
		/*if (category.indexOf("Comforters") > -1 && name.toLowerCase().indexOf(" set") > -1) {
			return CareyCategories.COMFORTER;
		}*/
		
		
		
		/*
		if (category.indexOf("Bedroom Furniture-Dressers&Chests") > -1) {
			return CareyCategories.DRESSERS;
		}
		
		if (category.indexOf("Bedroom Furniture-Nightstands") > -1) {
			return CareyCategories.NIGHTSTANDS;
		}
		
		if (category.indexOf("Dining Sets&Collections") > -1 && price > 100) {
			return CareyCategories.DINING_ROOM_SETS;
		}
		
		if (category.indexOf("Entertainment Centers") > -1 && price > 50) {
			return CareyCategories.ENTERTAINMENT_CENTERS;
		}
		
		if (category.indexOf("Living Room Chairs") > -1) {
			return CareyCategories.LIVING_ROOM_CHAIRS;
		}
		
		if (category.indexOf("Coffee&End Tables") > -1 && name.toLowerCase().indexOf("coffee") > -1) {
			return CareyCategories.COFFEE_TABLES;
		}
		
		if (category.indexOf("Coffee&End Tables") > -1 && name.toLowerCase().indexOf("end ") > -1) {
			return CareyCategories.END_TABLES;
		}
		
		
		if (category.indexOf("-Home Theater Systems") > -1) {
			return CareyCategories.HOME_THEATER;
		}
		
		if (category.indexOf("LCD TVs") > -1) {
			return CareyCategories.TVS;
		}
		*/
		
		
		
		
		
		
		/*if (category.indexOf("Dinnerware Sets") > -1) {
		return CareyCategories.DINNERWARE;
	}*/
		
		/*if (category.indexOf("Slow Cookers") > -1) {
		return CareyCategories.CROCKPOTS;
	}
	
	if (category.indexOf("Toaster Ovens") > -1) {
		return CareyCategories.TOASTER_OVENS;
	}
	
	if (category.indexOf("-Armoires") > -1) {
		return CareyCategories.ARMOIRES;
	}*/

		//too many tshirts
		/*if (category.indexOf("Men's Regular Shirts") > -1 && name.toLowerCase().indexOf("t-shirt") > -1) {
			Map<String,String> attMap = new HashMap<String,String>();
			attMap.put(CareyAttributes.LABEL_MENS_SHIRT_TYPE, CareyAttributes.TSHIRT);
			product.setAttMap(attMap);
			return CareyCategories.MEN_SHIRTS;
		}*/

		/*if (category.indexOf("Coffee&Espresso Makers") > -1 && name.toLowerCase().indexOf("coffee maker") > -1) {
		return CareyCategories.COFFEE_MACHINES;
	}*/

		/*if (category.indexOf("Blenders&Accessories") > -1 && name.toLowerCase().indexOf("blender") > -1) {
		return CareyCategories.BLENDERS;
	}

	if (category.indexOf("Food Processors") > -1) {
		return CareyCategories.FOOD_PROCESSORS;
	}*/

		//LCD TVs
	
		if (category.indexOf("All Laptops") > -1) {
			System.err.println("Adding " + name);
			return "Laptops";
		}
		
		return newCategory;
	}
	
	
}

