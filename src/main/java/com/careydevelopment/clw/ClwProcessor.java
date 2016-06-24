package com.careydevelopment.clw;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClwProcessor {

	public static void main(String[] args) {
		List<Product> products = new ArrayList<Product>();
		
		FileProcessor fp = new FileProcessor("c:/users/bcarey/Downloads/Sears-Sears_Marketplace_Product_Catalog_2.xml", new SearsCjHandler());
		fp.processParse();
		products.addAll(fp.getProducts());
		
		fp = new FileProcessor("c:/users/bcarey/Downloads/OfficeDepot-Product_Catalog.xml", new OfficeDepotCjHandler());
		fp.processParse();
		products.addAll(fp.getProducts());
		
		Collections.sort(products);
		
		System.err.println(products.size());
		
		/*for (Product p : products) {
			System.err.println(p.getName() + " " + p.getPrice() + " " + p.getRetailPrice());
		}*/
		
		int count = 0;
		BufferedWriter writer = null;
		int fileCount = 1;
		String fileName = "./outputfile" + fileCount + ".txt";

		try {
			writer = new BufferedWriter(new FileWriter(fileName));
			for (Product product : products) {
				String html = getHtmlOutput(product);
				//System.err.println(html);
				//System.err.println("\n\n\n\n");
				writer.write(html);
				writer.write(System.getProperty("line.separator"));
				writer.write(System.getProperty("line.separator"));
				count++;
				if (count == 30) {
					writer.close();
					fileCount++;
					if (fileCount > 9) break;
					fileName =  "./outputfile" + fileCount + ".txt";
					writer = new BufferedWriter(new FileWriter(fileName));
					count = 0;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (Exception e){}
		}
	}

	
	private static String getHtmlOutput(Product product) {
		StringBuilder builder = new StringBuilder();
		
		builder.append("<div class=\"cell-4 fx shop-item\" data-animate=\"fadeInUp\">");
		builder.append("<div class=\"item-box\">");
		builder.append("<h3 class=\"item-title clw-title\"><a href=\"");
		builder.append(product.getBuyUrl());
		builder.append("\" target=\"_blank\">");
		builder.append(product.getName());
		builder.append("</a></h3>");
		builder.append("<div class=\"item-img clw-img\">");
		builder.append("<a href=\"");
		builder.append(product.getBuyUrl());
		builder.append("\" target=\"_blank\"><img alt=\"\" src=\"");
		builder.append(product.getImageUrl());
		builder.append("\"></a>");
		builder.append("</div>");
		builder.append("<div class=\"item-details\">");
        builder.append("<p>");
        //builder.append(product.getDescription());
        builder.append("</p>");
        builder.append("<div class=\"right\">");
        builder.append("<div class=\"item-rating\"></div>");
        
        Float retailPrice = new Float(product.getRetailPrice());
        Float price = new Float(product.getPrice());
        builder.append("<div class=\"item-price\">");
        if (price < retailPrice) {
        	builder.append("<s>$");
        	builder.append(product.getRetailPrice());
            builder.append("</s> ");
        }
        builder.append("$");
        builder.append(product.getPrice());
        builder.append("</div>");
        builder.append("</div>");
        builder.append("<div class=\"left clw-company\">");
        builder.append(product.getVendor());
        builder.append("</div></div></div></div>");

		return builder.toString();
	}
}
