Product: Kontek \
Title: Housing Loans \
Version: 1.0.0 \
Date: 2023-07-02 \
Author: Dmitrijs Arkašarins

# Revision history

| Version | Date       | Author              | Change           |
|---------|------------|---------------------|------------------|
| 1.0.0   | 02.07.2023 | Dmitrijs Arkašarins | Initial version. |

# General information

***Loan Calc*** application can be used for calculation of the cost from a housing loan. 
The application a console user interface where the user can specify the desired amount and the payback time in years. 
Fixed interest of 3.5% per year during the complete payback time is applied for housing loan by default, 
however can be changed anytime in properties. \
When selecting amount and payback time, the application generates a
monthly payback plan based on the series loan principle, i.e. you pay back an equal amount
each month and add the generated interest. The interest is calculated every month. \
More loan types and payback plan options coming soon.

# Usage

This chapter describes how to package, configure and start ***Loan Calc***.

## Package compilation

In the project folder build the app:

	mvn clean package 
    
Use -DskipTests to skip unit test stage.

This will compile and package the code into a .jar file and put it into the target folder.
Copy this file into a directory of your choice.

## Configuration

Below is an example of ***Loan Calc*** "application.properties" file.
Place it inside the "<installation root folder\>/config/application.properties".

```
### Loan interest rate for housing loan calculations
#loan.type.housing.interest-rate=3.5
#loan.payback-plan=monthly
```

Commented out values are used by default. Currently, the only payback plan available is "monthly".

## Starting the application

Go to the "<installation root folder\>" folder and start the ***Loan Calc*** application.

	java -jar LoanCalc-1.0-SNAPSHOT.war <argument>

### Arguments

Following arguments are available for the launch:

-   help - shows help information (default when no valid arguments passed)
-   housing - launches housing loan calculator

## Interaction

When user selects a loan type on launch, currently it being only housing loan available,
there are two parameters that should be specified in console prompt:

-   Loan amount - total loan to be paid (any currency), a positive decimal number;
-   Payback time - loan payback time in years, a positive integer.
