package com.careydevelopment.clw;

import java.io.File;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class ProcessSears {

	private static final String OUTPUT_FILE = "./output/brianmcarey/sears2.xml";
	private static final String INPUT_FILE = "c:/users/bcarey/Downloads/Sears-Sears_Marketplace_Product_Catalog_2.xml";
	
	public static void main(String args[]) {
		//FileGrabber.grabFile(INPUT_FILE,FileGrabber.SEARS2);
		ProcessSears pa = new ProcessSears();
		pa.processParse();
		//Cleaner cleaner = new Cleaner("sears",DatabaseHelper.getDatabase(DatabaseHelper.BRIANMCAREY),OUTPUT_FILE);
		//cleaner.removeItemsNoLongerAvailable();
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
	
	private void processParse() {
		try {
			
			File file = new File(INPUT_FILE);

			SAXParserFactory parserFactor = SAXParserFactory.newInstance();
			SAXParser parser = parserFactor.newSAXParser();
			SearsCjHandler handler = new SearsCjHandler();

			
			//System.err.println("about to parse");
			parser.parse(file, handler); 
			
			List<Product> products = handler.getProducts();
			
			for (Product p : products) {
				System.err.println(p.getName() + " " + p.getPrice() + " " + p.getRetailPrice());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
