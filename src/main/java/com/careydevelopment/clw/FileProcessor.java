package com.careydevelopment.clw;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.helpers.DefaultHandler;

public class FileProcessor {

	//private static final String OUTPUT_FILE = "./output/brianmcarey/sears2.xml";
	//private static final String INPUT_FILE = "c:/users/bcarey/Downloads/Sears-Sears_Marketplace_Product_Catalog_2.xml";
	
	private String xmlFile;
	private BaseHandler handler;
	private List<Product> products;
	
	public static void main(String args[]) {
		//FileGrabber.grabFile(INPUT_FILE,FileGrabber.SEARS2);
		//FileProcessor pa = new FileProcessor();
		//pa.processParse();
	}
	
	public FileProcessor(String file, BaseHandler handler) {
		this.xmlFile = file;
		this.handler = handler;
	}
	
	
    public String stripNonValidXMLCharacters(String in) {
        StringBuffer out = new StringBuffer(); // Used to hold the output.
        char current; // Used to reference the current character.

        if (in == null || ("".equals(in))) return ""; // vacancy test.
        for (int i = 0; i < in.length(); i++) {
            current = in.charAt(i); // NOTE: No IndexOutOfBoundsException caught here; it should not happen.
            if ((current == 0x9) ||
                (current == 0xA) ||
                (current == 0xD) ||
                ((current >= 0x20) && (current <= 0xD7FF)) ||
                ((current >= 0xE000) && (current <= 0xFFFD)) ||
                ((current >= 0x10000) && (current <= 0x10FFFF)))
                out.append(current);
        }
        return out.toString();
    } 
	
	public void processParse() {
		try {
			
			File file = new File(xmlFile);

			SAXParserFactory parserFactor = SAXParserFactory.newInstance();
			SAXParser parser = parserFactor.newSAXParser();
			
			//System.err.println("about to parse");
			parser.parse(file, handler); 
			
			products = handler.getProducts();
			
			//Collections.sort(products);
			
			for (Product p : products) {
				System.err.println(p.getName() + " " + p.getPrice() + " " + p.getRetailPrice());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public List<Product> getProducts() {
		return products;
	}
}
