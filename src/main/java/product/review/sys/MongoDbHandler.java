package product.review.sys;

import java.util.ArrayList;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoDbHandler {
	
	private final String MONGO_IP = "localhost";
	private final int MONGO_PORT = 27017; 
	private final String MONGO_DATABASE_NAME = "review";
	
	private final String MONGO_USER_COLLECTION_NAME = "user";
	private final String MONGO_PRODUCT_COLLECTION_NAME = "product";
	private final String MONGO_REVIEW_COLLECTION_NAME = "review";
	
	private static MongoDbHandler instance;
	private MongoClient mongoClient;
	private MongoDatabase database;
	
	private ArrayList<User> userList;
	private MongoCollection<Document> userCollection;
	
	private ArrayList<Product> productList;
	private MongoCollection<Document> productCollection;
	
	private MongoCollection<Document> reviewCollection;
	
	
	// Constructor to make class singleton
	private MongoDbHandler() {
		this.userList = new ArrayList<>();	
		this.productList = new ArrayList<>();
	}
	
	/*
	 * Parse and save already stored document for users.
	 */
	private void parseOldUserFromMongo() throws Exception {
		
		for (Document cur : this.userCollection.find()) {
	
			// Get user data from cursor
			User user = new User(cur.getString(User.KEY_USER_NAME), cur.getString(User.KEY_USER_ID));
			
			// Add retrieve data inside the user list
			this.userList.add(user);
		}	
	}
	
	/*
	 * Parse and save already stored document for products.
	 */
	private void parseOldProductsFromMongo() throws Exception {
		
		for (Document cur : this.productCollection.find()) {
			
			// Get product data from cursor
			Product product = new Product(cur.getString(Product.KEY_PRODUCT_NAME), cur.getString(Product.KEY_PRODUCT_ID));
	
			// Add data inside the product list
			this.productList.add(product);
		}	
	}
	
	/*
	 * Creates connection with mongo db and read all the collections.
	 */
	@SuppressWarnings("deprecation")
	public void init() throws Exception {
		
		// Hide the mongo log messages
		Logger.hideMongoDbLogs();
		
		// Get the connection with running mongo db server	
		this.mongoClient = new MongoClient(MONGO_IP, MONGO_PORT);
		
		// Get the database name from mongo db server
		this.database = this.mongoClient.getDatabase(MONGO_DATABASE_NAME);	
		
		// Get the user collection from database
		this.userCollection = this.database.getCollection(MONGO_USER_COLLECTION_NAME);
		
		// Get all the documents in case there are any
		if (this.userCollection.count() > 0)
			parseOldUserFromMongo();
		
		// Get the product collection from database
		this.productCollection = this.database.getCollection(MONGO_PRODUCT_COLLECTION_NAME);
		
		// Get all the previous documents from collection
		if (this.productCollection.count() > 0)
			parseOldProductsFromMongo();
		
		// Get the review collection from database
		this.reviewCollection = this.database.getCollection(MONGO_REVIEW_COLLECTION_NAME);
	}
	
	/*
	 * Find if provided user name is unique.
	 */
	public boolean checkUsernameUniqness(final String un) {
		
		for (User user : this.userList) {
		
			// Check if user name exists or not
			if (user.getUserName().equalsIgnoreCase(un)) {
				return false;
			}
		}
		return true;
	}
	
	/*
	 * Find if provided product name is unique.
	 */
	public boolean checkProductNameUniqueness(final String pn) {
		
		for (Product product : this.productList) {
			
			// Check if product name exists or not
			if (product.getName().equalsIgnoreCase(pn)) {
				return false;
			}
		}
		return true;
	}
	
	/*
	 * Prints out all the user list on to the screen
	 */
	public void printUserList() {
		
		if (this.userList.size() == 0) {
			
			Logger.error("No users found inside database.");
			return;
		}
		else {
			
			int count = 1;
			for (User user : this.userList) {
				Logger.output(count + ">> " + user.toString());
				count++;
			}
		}
	}
	
	/*
	 * Prints out all the product list on to the screen.
	 */
	public void printProductList() {
		
		if (this.productList.size() == 0) {
			
			Logger.error("No products found inside database.");
			return;
		}
		else {
			
			int count = 1;
			for (Product product : this.productList) {
				Logger.output(count + ">> " + product.toString());
				count++;
			}
		}
	}
	
	/*
	 * Print all the review for specific product.
	 */
	public void printReviewListForSingleProduct(final String productName) {
		
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put(Review.KEY_PRODUCT_NAME, productName);
		
		for (Document cur : this.reviewCollection.find(whereQuery)) {
			
			Logger.output(
					"{"
						+ "product_name : " + productName + ", "
						+ "user_name : " + cur.getString(Review.KEY_USER_NAME) + " , "
						+ "rating : " + cur.getString(Review.KEY_PRODUCT_RATING) + ", "
						+ "review : " + cur.getString(Review.KEY_PRODUCT_REVIEW) + 
					"}");
		}
	}
	
	/*
	 * Print reviews for all the products.
	 */
	public void printAllProductReviews() {
		
		for (Document cur : this.reviewCollection.find()) {
			
			Logger.output(
					"{"
						+ "product_name : " + cur.getString(Review.KEY_PRODUCT_NAME) + ", "
						+ "user_name : " + cur.getString(Review.KEY_USER_NAME) + " , "
						+ "rating : " + cur.getString(Review.KEY_PRODUCT_RATING) + ", "
						+ "review : " + cur.getString(Review.KEY_PRODUCT_REVIEW) + 
					"}");
		}
	}
	
	/*
	 * Returns mongo db document for user.
	 */
	private Document getUserDocument(final String un) {
		
		// Create new user
		User user = new User(un);
		
		// Save the new user inside the user list
		this.userList.add(user);
		
		// Return mongo db document 
		return new Document(User.KEY_USER_NAME, user.getUserName())
				.append(User.KEY_USER_ID, user.getUserId());
	}
	
	/*
	 * Returns mongo db document for product.
	 */
	private Document getProductDocument(final String pn) {
		
		// Create new product
		Product product = new Product(pn);
		
		// Save new product inside the product list
		this.productList.add(product);
		
		// Return mongo db document
		return new Document(Product.KEY_PRODUCT_NAME, product.getName())
				.append(Product.KEY_PRODUCT_ID, product.getUid());
	}
	
	/*
	 * Returns mongo db document for review.
	 */
	private Document getReviewDocument(final Review review) {
		
		return new Document(Review.KEY_PRODUCT_NAME, review.getProductName())
				.append(Review.KEY_USER_NAME, review.getUserName())
				.append(Review.KEY_PRODUCT_RATING, review.getRating())
				.append(Review.KEY_PRODUCT_REVIEW, review.getReview());
	}
	
	/*
	 * Check for uniqueness of user name, if unique, store
	 * 		 it inside mongo db. 
	 */
	public boolean isUsernameUnique(final String un) {
		
		if (this.userList.size() > 0) {
			
			if (checkUsernameUniqness(un)) {
				
				// Insert the document inside user collection
				this.userCollection.insertOne(getUserDocument(un));
				return true;
			}
			else return false;
		}
		else {
			
			// Insert the document inside user collection
			this.userCollection.insertOne(getUserDocument(un));
			return true;
		}
	}
	
	/*
	 * Check for uniqueness of product name, if unique, store
	 * 		 it inside mongo db. 
	 */
	public boolean isProductNameUnique(final String pn) {
		
		if (this.productList.size() > 0) {
			
			if (checkProductNameUniqueness(pn)) {
				
				// Insert the document inside user collection
				this.productCollection.insertOne(getProductDocument(pn));
				return true;
			}
			else return false;
			
		}
		else {
			
			// Insert the document inside user collection
			this.productCollection.insertOne(getProductDocument(pn));
			return true;
		}
	}
	
	/*
	 * Saves review inside the mongo db.
	 */
	public void storeReviewForProduct(Review review) {
		this.reviewCollection.insertOne(getReviewDocument(review));
	}
	
	/*
	 * Get instance of the singleton class
	 */
	public static MongoDbHandler getInstance() {
		if (instance == null) 
			instance = new MongoDbHandler();
		return instance;
	}

}
