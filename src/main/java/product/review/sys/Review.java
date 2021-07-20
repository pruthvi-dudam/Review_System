package product.review.sys;

public class Review {
	
	public static final String KEY_PRODUCT_NAME = "productName";
	public static final String KEY_USER_NAME = "userName";
	public static final String KEY_PRODUCT_REVIEW = "productReview";
	public static final String KEY_PRODUCT_RATING = "productRating";
	
	private String productName;
	private String rating;
	private String review;
	private String userName;
	
	// Constructor
	public Review() {
		this.productName = "";
		this.rating = "";
		this.review = "";
	}
	
	// Setter
	public void setProductName(String pn) {
		this.productName = pn;
	}
	
	// Setter
	public void setUserName(String un) {
		this.userName = un;
	}
	
	// Setter
	public void setRating(String rating) {
		this.rating = rating;
	}
	
	// Setter
	public void setReview(String review) {
		this.review = review;
	}
	
	// Getter
	public String getProductName() {
		return this.productName;
	}
	
	// Getter
	public String getUserName() {
		return this.userName;
	}
	
	// Getter
	public String getRating() {
		return this.rating;
	}
	
	// Getter
	public String getReview() {
		return this.review;
	}
	
	/*
	 * Handles the process of writing product review.
	 */
	public static void writeProductReview() {
		
		final String userMes = "Enter user name ?";
		final String productMes = "Enter product name ?";
		final String ratingMes = "Enter product rating(value between 1 to 5) ?";
		final String reviewMes = "Enter product review ?";
		
		String username = "";
		String productName  = "";
		String rating = "";
		String review = "";
		
		// Ask for user name
		Logger.output(userMes);
		username = Logger.input().trim();
		
		// Check if user name already exists
		if (MongoDbHandler.getInstance().checkUsernameUniqness(username)) {
			Logger.error("User name does not exist inside database.");
			return;
		}
		
		// Ask for product name
		Logger.output(productMes);
		productName = Logger.input().trim();
		
		// Check if product name already exists
		if (MongoDbHandler.getInstance().checkProductNameUniqueness(productName)) {
			Logger.error("Product name does not exist inside database");
			return;
		}
		
		// Ask for rating
		Logger.output(ratingMes);
		rating = Logger.input().trim();
		
		// Check if user has provided valid rating input
		if (Integer.parseInt(rating) <= 0 && Integer.parseInt(rating) > 5) {
			Logger.output("Rating value must be between 1 and 5 ");
			return;
		}
		
		// Ask for user review
		Logger.output(reviewMes);
		review = Logger.input().trim();
		
		// Store review object inside the db
		Review reviewObj = new Review();
		reviewObj.setProductName(productName);
		reviewObj.setUserName(username);
		reviewObj.setRating(rating);
		reviewObj.setReview(review);
		
		MongoDbHandler.getInstance().storeReviewForProduct(reviewObj);
		Logger.output("Review stored successfully.");
	}
	
	/*
	 * Handles the case of getting the reviews for specific product.
	 */
	public static void getProductReview() {
		
		final String mes = "Enter product name ?";
		Logger.output(mes);
		String input = Logger.input().trim();
		
		// Check if product name already exists
		if (MongoDbHandler.getInstance().checkProductNameUniqueness(input)) {
			Logger.error("Product name does not exist inside database");
			return;
		}
		else {
			MongoDbHandler.getInstance().printReviewListForSingleProduct(input);
		}
	}
	
	
}
