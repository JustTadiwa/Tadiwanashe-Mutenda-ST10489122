# Tadiwanashe-Mutenda-ST10489122

## Classes and Methods

### Main Class
- `main()` - Application entry point
- `loadTestData()` - Preloads test messages into array
- `sendMessages()` - Handles message creation and sending
- `messageManagement()` - Part 3 features menu
- Array search and manipulation methods

### Login Class
- `checkUserName()` - Validates username format
- `checkPasswordComplexity()` - Validates password requirements
- `checkCellPhone()` - Validates phone number format
- `registerUser()` - Handles user registration
- `loginUser()` - Authenticates users
- `getFullName()` - Returns user's full name

### Message Class
- Constructor - Creates message objects with auto-generated ID and Hash
- `createMessageHash()` - Generates unique message hash
- `printMessage()` - Formats message details for display
- `storeMessage()` - Saves message to JSON file
- Getter methods for all properties

## Technical Implementation

### Arrays
- Uses basic arrays as taught in Learning Unit 6
- No ArrayLists or advanced collections
- Manual array manipulation for all operations

### String Manipulation
- Message ID: "Msg" + number + "of" + total (with substring for length limit)
- Message Hash: First 2 chars of ID + ":" + message number + ":" + first word + last word

### GUI Implementation
- All user interaction via JOptionPane dialogs
- No console input/output (System.out/Scanner)

### Data Persistence
- Manual JSON string creation without external libraries
- File writing using FileWriter

## Unit Testing
Comprehensive test coverage including:
- User registration and login scenarios
- Message validation and creation
- Array population and manipulation
- Search and delete functionality
- Edge cases and error conditions

## How to Run

### Prerequisites
- Java JDK 8 or higher
- IntelliJ IDEA (recommended) or any Java IDE

### Running the Application
1. Open the project in IntelliJ
2. Run `Main.java`
3. Follow the registration and login process
4. Use the menu system to access all features

### Running Tests
1. Right-click on any test file (*Test.java)
2. Select "Run Tests"
3. Or use the green play buttons next to individual test methods

## Test Data
The application includes 5 preloaded test messages:
1. "+2784557896" - "Did you get the cake?"
2. "+27338884567" - "Where are you? You are late! I have asked you to be on time."
3. "+2733484567" - "Yohoooo, I am at your gate."
4. "0808844597" - "It is dinner time !!"
5. "+27338884567" - "Ok, I am leaving without you."

## Academic Compliance
This implementation strictly uses only programming concepts and techniques taught in:
- Joyce Farrell's "Java Programming" textbook
- PROG5121 Learning Units 1-6
- No external libraries beyond JUnit for testing
- No advanced Java features beyond course curriculum

## Developer
Tadiwanashe
Student Number: ST10489122
PROG5121 Programming 1A
The Independent Institute of Education

## Submission
This project represents the complete POE submission for PROG5121, incorporating feedback from Part 2 and fully implementing all Part 3 requirements.
