# D0017D Practice Exam 1: Luleå Airport Parking lot

## Assignment description
Your task is to implement a long term parking lot management system at an airport. The program is supposed to handle information about entrance/exit dates, cost of parking and parking history.

### Menu
At the start, the program should print a menu of options that can be selected by the user. It should be possible to exit the program by pressing **_q_**
```
----------------------------------
# LULEA AIRPORT PARKING LOT
----------------------------------
1. Drive in
2. Drive out
3. Check parking
4. Print parking history (by arrival date)
5. Print parking history (by registration number)
q. End program
> Enter your option:
```

### Driving in
In order to drive in, the user is required to provide the registration number of the vehicle, as well as current date. In order to simplify the task, **assume that there are 12 months in a year and 30 days per month**. The maximum parking time is **one month or 30 days**. The date should be provided in current format: **YYYY-MM-DD**. There are no constraints on the registration number besides from them being between 3 and 8 characters long. The user should also be prompted with an option to charge the vehicle during parking (for additional payment, see next section).

```
> Enter your option: 1
> Enter registration number: PXT083
> Current date: 2022-08-24
> Charge electric vehicle: Yes

Car PXT083 entered at 2022-08-24
```

### Driving out 
Once a car has entered the parking lot, it should be possible to drive out by providing
the date of departure. A car is referenced by the registration number which was provided in previous step. The cost of parking is 120kr per day for the first 10 days, 50kr per day for days 11-30. If the vehicle has been charged during parking, an additional charge of 250kr is imposed. The user should be presented with a receipt once the necessary details are provided. Make sure to implement validations (for example that the car can not exit the parking earlier than the entrance date etc.)
```
> Enter your option: 2
> Enter registration number: PXT083
> Current date: 2022-08-26

B###################################
# RECEIPT PARKING #
###################################
# Reg IN OUT #
# PXT083 2022/08/24 2022/08/26 #
# #
# Number of days: 3 days #
# Charge: Yes #
# Cost: 510 kr #
###################################
```

### Checking the parking
It should be possible to check whether a vehicle is currently parked. If the car is parked, the user should be able to see the entrance date.
```
> Enter your option: 3
> Enter registration number: PXT083
Car PXT083 is not parked at the moment

> Enter your option: 3
> Enter registration number: DWW96B
Car DWW96B is currently parked since 2022-08-01
```

### Parking history
It should be possible to print parking history. The user should be able to see all previously parked cars and following information:
* Registration number
* Entrance date
* Exit date (if exited)
* Charging used (Yes/No)
* Parking cost (if exited)
The program should have two options to present this information: sorted by entrance date and sorted by registration number. **Important: if you do not have time, you will still get points if you manage to present the correct unsorted data!**
```
> Enter your option: 4

Parking history sorted by entrance date

Registration  Entered       Exited       Charging used       Parking cost
DWW96B        2022-08-01                 Yes
PXT083        2022-08-24    2022-08-26   Yes                 510kr
APT93G        2022-08-24                 No
EWR054        2022-08-25    2022-09-01   Yes                 1090kr

> Enter your option: 5

Parking history sorted by registration number

Registration  Entered       Exited       Charging used       Parking cost
APT93G        2022-08-24                 No
DWW96B        2022-08-01                 Yes
EWR054        2022-08-25    2022-09-01   Yes                 1090kr
PXT083        2022-08-24    2022-08-26   Yes                 510kr
```

## Instructions
The program should have the necessary error handling (ways of validating the input, such as checking that the validity of the date and registration number etc.). The program should under no circumstances crash when receiving incorrect input (this will result in deduction of points). It is allowed to store additional information, other than provided in the assignment description. You should create appropriate data structures to store the information. The programs that compile with the compilation error will not be graded (it should be possible to run the program).

## Tips
* Do one functionality/method/option at a time. It is possible to pass the assignment by not finalizing all the parts! Leave the hardest parts till the end. For example, First get the program working for correct/valid inputs. Then update the program to handle error conditions, such as checking if a number is entered for price.
* Write down requirements for each function as comment on top of the function, this will help you not to forget a requirement


## Assumptions
If you notice that some information is missing from the assignment description, you are allowed to make own assumptions as long as it does not change the condition and basis of the assignment.

## Requirements
* **_Please do not forget to include your name, LTU username on the top of the file as a author_**
* You are free to use either replit or any IDE of your choice.
* Do not use packages (there should not be any code, beginning with package).
* Follow the course’s coding conventions and formatting conventions (method comments, usage of constants, indentation etc.).
* Use methods in your solution.
* It is not allowed to use any global variables (only global Scanner objects are allowed).
* It is not allowed to use built in functionality (such as ArrayLists etc.), in other words, same rules as your assignments. In other words, only import statement you will have is ```import java.util.Scanner; ```. **It is ok to use Random library as well.**
* The assignment is to be solved independently! It is allowed to get inspiration from the Internet, but you are to solve the task by your own! Always provide a source for your information that you get from external sources. **_NOTE! It is not allowed to copy entire code sections!_** You are, however, allowed to copy the code, that you have written yourself.
  
## Submission
You must submit a single java file to canvas.
* If you are using your own IDE, such as eclipse, make sure your filename is called ```Main.java``` and class name is called ```Main```. Upload this single file Main.java to canvas. 
* You can upload multiple times to Canvas.
