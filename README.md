# About
Java application using Maven, that allows a user to register, view products and leave a review + rating for products. The data is stored in a MongoDB engine.

# How to run the Application

## Prerequisites
The following services must be installed:

1. [Java Development kit](https://www.oracle.com/java/technologies/javase-downloads.html)
2. [Java IDE](https://www.jetbrains.com/idea/)
2. [Maven java](https://maven.apache.org/download.cgi)
3. [MongoDB](https://docs.mongodb.com/manual/tutorial/install-mongodb-on-os-x/) (Using Homebrew Tap)
4. [MongoDB compass](https://www.mongodb.com/products/compass)

## Installation

Clone the repository
$ `git clone https://github.com/pruthvi-dudam/Review_System.git`

## Development Mode
### Run MongoDB

1. Open terminal and go to the directory where you installed the cloned repository file
2. Type $ `brew services start mongodb-community` 
3. Type $ `mongo`
4. Open MongoDB Compass and click on New Connection and click Connect
5. It can also be accessed through localhost: 27017

### Run the Java Application
1. Open another terminal window and go to the directory where you installed the cloned repository file
2. Type $ `mvn clean install`
3. Go to the target folder directory located inside the current directory - $ `cd target/`
4. Type $ `java -jar snapshot.jar`

### To test the Application and View Updated DB
1. After running the application, you will be presented with the following options:
    - Welcome to Simple Review System 
    - 1). Create new user 
    - 2). Show list of all users 
    - 3). Create new product 
    - 4). Show list of all products 
    - 5). Write product review 
    - 6). Get review+rating for specific product 
    - 7). Get review+rating for all products 
    - Enter input from 1 - 7, ctrl+c to exit. "
2. Input a few values in your terminal
3. To view the DB, open MongoDB Compass, refresh and view the updated data.
