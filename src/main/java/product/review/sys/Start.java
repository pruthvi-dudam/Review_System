package product.review.sys;

public class Start {
	
	/*
	 * Shows welcome message on to the console.
	 */
	public void welcomeMessage() {
		
		final String mes = 
				"\n Welcome to Simple Review System "
				+ "\n 1). Create new user "
				+ "\n 2). Show list of all users "
				+ "\n 3). Create new product "
				+ "\n 4). Show list of all products "
				+ "\n 5). Write product review "
				+ "\n 6). Get review+rating for specific product "
				+ "\n 7). Get review+rating for all products "
				+ "\n Enter input from 1 - 7, ctrl+c to exit. ";
		
		// Loop to get user input infinitely
		while (true) {
			
			// Print the welcome message
			Logger.output(mes);
			
			// Get the user input
			String input = Logger.input();
			
			Logger.output("Input is => " + input);
			
			if (input.equals("1")) {
				User.createNewUser();
			}
			else if (input.equals("2")) {
				MongoDbHandler.getInstance().printUserList();
			}
			else if (input.equals("3")) {
				Product.createNewProduct();
			}
			else if (input.equals("4")){
				MongoDbHandler.getInstance().printProductList();
			}
			else if (input.equals("5")){
				Review.writeProductReview();
			}
			else if (input.equals("6")) {
				Review.getProductReview();
			}
			else if (input.equals("7")) {
				MongoDbHandler.getInstance().printAllProductReviews();
			}
			else {
				Logger.output("Please provide valid input(1-7).");
			}
		}
	}
	
	/*
	 * Main/Driver function from where program execution starts
	 */
	public static void main(String[] args) {
		
		// Initialize mongo db
		try {
			MongoDbHandler.getInstance().init();
			new Start().welcomeMessage();
		}
		catch(Exception e) {
			Logger.error(e, "while creating mongo db client connection.");
			e.printStackTrace();
		}
	}




}
