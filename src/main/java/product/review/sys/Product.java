package product.review.sys;

import java.util.UUID;

public class Product {
	
	public static final String KEY_PRODUCT_ID = "productId";
	public static final String KEY_PRODUCT_NAME = "productName";
	
	private String name;
	private String uid;
	
	// Constructor
	public Product() {
		this.name = "";
		this.uid = "";
	}
	
	// Constructor
	public Product(String name) {
		this.name = name;
		this.uid = UUID.randomUUID().toString();
	}
	
	// Constructor
	public Product(String name, String uid) {
		this.name = name;
		this.uid = uid;
	}

	// Getter and Setter for Product name
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
	
	// Getter and Setter for Product id
	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUid() {
		return this.uid;
	}
	
	/*
	 *  Converts product object to string.
	 */
	public String toString() {
		return "{product_name : " + this.name + ", product_id : " + this.uid + "}";
	}
	
	/*
	 * Handles the process of creating new product.
	 */
	public static void createNewProduct() {
		
		final String mes = "Enter unique product name ?";
		Logger.output(mes); // prints mes on console
		
		String input = Logger.input().trim(); // reads in user input
		
		if (input.length() > 0) {
			
			// Check if provided product name is unique
			if (MongoDbHandler.getInstance().isProductNameUnique(input)) {
				Logger.output("Product stored successfully.");
				return;
			}
			else {
				Logger.output("Product already exists in database.");
				return;
			}
		}
		else {
			Logger.output("Invalid product name, try again");
		}
	}

}
