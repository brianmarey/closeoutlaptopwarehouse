package com.careydevelopment.clw;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Product implements Comparable<Product> {
	
	private String name = "";
	private String programName = "";
	private String programUrl = "";
	private String catalogName = "";
	private String lastUpdated = "";
	private String keywords = "";
	private String description = "";
	private String sku = "";
	private String manufacturer = "";
	private String vendor = "";
	private String upc = "";
	private String price = "";
	private String buyUrl = "";
	private String manufacturerId = "";
	private String impressionUrl = "";
	private String advertiserCategory = "";
	private String condition = "";
	private String inStock = "";
	private String retailPrice =  "";
	private String imageUrl = "";
	private String sizes = "";
	private String colors = "";
	private String artist = "";
	private String height = "";
	private String width = "";
	private String length = "";
	private String weight = "";
	private String lowestOfferPrice = "";
	private String offersUrl = "";
	
	private List<String> variantFrontUrls = new ArrayList<String>();
	private List<String> variantBackUrls = new ArrayList<String>();
	private List<String> swatchUrls = new ArrayList<String>();	
	
	private Map<String,String> attMap = new HashMap<String,String>();
	
	
	public String getSizes() {
		return sizes;
	}
	public void setSizes(String sizes) {
		this.sizes = sizes;
	}
	public String getColors() {
		return colors;
	}
	public void setColors(String colors) {
		this.colors = colors;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getRetailPrice() {
		return retailPrice;
	}
	public void setRetailPrice(String retailPrice) {
		this.retailPrice = retailPrice;
	}
	public String getInStock() {
		return inStock;
	}
	public void setInStock(String inStock) {
		this.inStock = inStock;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProgramName() {
		return programName;
	}
	public void setProgramName(String programName) {
		this.programName = programName;
	}
	public String getProgramUrl() {
		return programUrl;
	}
	public void setProgramUrl(String programUrl) {
		this.programUrl = programUrl;
	}
	public String getCatalogName() {
		return catalogName;
	}
	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}
	public String getLastUpdated() {
		return lastUpdated;
	}
	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public String getVendor() {
		return vendor;
	}
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	public String getUpc() {
		return upc;
	}
	public void setUpc(String upc) {
		this.upc = upc;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		/*System.err.println("setting price to " + price);
		try {
			throw new Exception ("HITIE");
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		this.price = price;
	}
	public String getBuyUrl() {
		return buyUrl;
	}
	public void setBuyUrl(String buyUrl) {
		this.buyUrl = buyUrl;
	}
	public String getManufacturerId() {
		return manufacturerId;
	}
	public void setManufacturerId(String manufacturerId) {
		this.manufacturerId = manufacturerId;
	}
	public String getImpressionUrl() {
		return impressionUrl;
	}
	public void setImpressionUrl(String impressionUrl) {
		this.impressionUrl = impressionUrl;
	}
	public String getAdvertiserCategory() {
		return advertiserCategory;
	}
	public void setAdvertiserCategory(String advertiserCategory) {
		this.advertiserCategory = advertiserCategory;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public List<String> getVariantFrontUrls() {
		return variantFrontUrls;
	}
	public void setVariantFrontUrls(List<String> variantFrontUrls) {
		this.variantFrontUrls = variantFrontUrls;
	}
	public List<String> getVariantBackUrls() {
		return variantBackUrls;
	}
	public void setVariantBackUrls(List<String> variantBackUrls) {
		this.variantBackUrls = variantBackUrls;
	}
	public List<String> getSwatchUrls() {
		return swatchUrls;
	}
	public void setSwatchUrls(List<String> swatchUrls) {
		this.swatchUrls = swatchUrls;
	}
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getLowestOfferPrice() {
		return lowestOfferPrice;
	}
	public void setLowestOfferPrice(String lowestOfferPrice) {
		this.lowestOfferPrice = lowestOfferPrice;
	}
	public String getOffersUrl() {
		return offersUrl;
	}
	public void setOffersUrl(String offersUrl) {
		this.offersUrl = offersUrl;
	}
	public Map<String, String> getAttMap() {
		return attMap;
	}
	public void setAttMap(Map<String, String> attMap) {
		this.attMap = attMap;
	}
	
	
	public int compareTo(Product compareProduct) {
		Float comparePrice = new Float(((Product) compareProduct).getPrice()); 
		Float myPrice = new Float(price);
		
		//ascending order
		float val =  myPrice - comparePrice;
		
		if (val < 0) return -1;
		else if (val > 0) return 1;
		else return 0;
	}
	
}

