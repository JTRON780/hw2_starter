# hw2_starter

# CS520 Spring 2026 - Homework 2

## Overview and Goal
In this assignment, you will work with an existing implementation of an Expense Tracker application. This application applies the MVC architecture pattern.
Rather than building brand-new user features, your task is to understand, analyze, and restructure the system using sound software engineering principles.

Treat this as a realistic on-boarding task: you are joining a team with shipped software, and your responsibility is to improve understandability, modularity, extensibility readiness, and testability while preserving current behavior. 

## Getting Started
1. Clone the repository: `git clone https://github.com/CS520-Spring2026/hw2_starter`
2. Read this `README.md` file.
3. Build, test, and run the application using the commands below.
4. Explore source code in `src/` and tests in `test/`.

We'll use the ant build tool (`https://ant.apache.org/manual/installlist.html`) to build and run the application.

## Optional Working Files
You may draft your answers in local markdown files (for example under `docs/`) while working, but these files are optional and are not required for grading.
All written graded content must appear in `HW1_answers.pdf`.

## Build and Run

The Expense Tracker application has the following structure:
- `bin/`: Contains the generated class files
- `jdoc/`: Contains the generated javadoc folders/files
- `lib/`: Contains the third party software jar files
- `src/`: Contains the Java folders and source files
- `test/`: Contains the JUnit test suite source files
- `build.xml`: Is the ant build tool input file
- `build/`: Contains the ant build tool output files
- `coverage_report/`: Contains the test coverage report

The build requirements are:
- JDK 21+: Generate API doc (javadoc), compile (javac), run (java)
- Ant 1.10.15+: Build and run the application and test suite(s)

From the root directory (containing the build.xml file):

1. Build app: `ant compile`

2. Run the app: `java -cp bin ExpenseTrackerApp`

3. Build and run tests: `ant test` (See the build/TEST-*.txt files for more details.)

4. Generate the test coverage report: `ant coverage.report (See the coverage_report/index.html for more details.)

5. Generate Javadoc: `ant document`

6. Perform linting `ant lint`

7. Clean generated artifacts (e.g., class files, javadoc files): `ant clean`

# Architecture

The Expense Tracker application applies the MVC architecture pattern as follows:
* model package: Contains the data model represented as a list of transactions
* view package: Contains the visualizations of the model and supports user interactions
* controller package: Contains the application logic to process the user interactions

# Features

* **Add Transaction:**
  Enter a valid amount and category, then click **Add Transaction**.
  The valid transaction appears in the list, and the total cost updates automatically.

* **Input Validation:**
  Validates adding and exporting functionalities. The transaction amount must be greater than 0 and less than or equal to 1000. The category must be one of: food, travel, bills, entertainment, or other. The exported filename must be valid and end with `.csv`.

* **Delete Transaction:**
  Select a valid transaction from the list.
  In the 'Edit' menu, select the 'Delete' menu item.
  The valid transaction disappears from the list, and the total cost updates automatically.

* **Save the Transaction List:**
  In the 'File' menu, select the 'Save As...' menu item
  In the Save dialog, first select a valid output file and then click the 'Save' button

* **Open a Transaction List:**
  In the 'File' menu, select the "Open File..." menu item
  In the Open dialog, first select a valid input file and then click the 'Open' button

# Solution

## Part 1 Manual Review and Design Analysis [20 pts]

### Some examples of satisfying non-functional requirements
1. Usability: Enhancement of usability by the GUI. Generally using UI widgets in expected ways. 
2. Debuggability: The app is under version control. If a bug report is made about a particular version, it is possible to access that version and use debugging techniques to localize the cause of that bug in the version.
3. Understandability: Naming conventions are being used to improve program understanding.
4. Testability: There exists a unit test suite.
5. ...

### Other examples of violated non-functional requirements
1. Integrity or Security or Modularity: No data encapsulation is implemented to protect transaction data. 
2. Debuggability or Usability: The code generally lacks proper data error handling mechanisms. The InputValidation class now provides some data error handling.
3. Modularity: The code does not demonstrate a high level of modularity. in the ```ExpenseTrackerView.java```, there is a mix of view-related code and controller-related code. Could apply the MVC pattern as described in the MVC section.
4. Testability: The code often does not facilitate testing. In the ```ExpenseTrackerApp``` class, the main method directly creates instances of the ```ExpenseTrackerView``` and sets it as visible. This makes it difficult to write unit tests for the application logic, as it tightly couples the view and controller. Additionally the unit test suite does not seem adequate (i.e. too few test cases).
5. ...
