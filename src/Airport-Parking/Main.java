import java.util.Scanner;

/**
 * Airport Parking Lot Management System
 * 
 * This program manages a parking lot at an airport. It allows users to:
 * - Drive in and register a car
 * - Drive out and calculate parking costs
 * - Check if a car is currently parked
 * - View parking history sorted by date or registration number
 * 
 * Author: [Your Name], LTU Username: [Your LTU Username]
 */
public class Main {
    private static final int MAX_PARKING_DAYS = 30;
    private static final int FIRST_10_DAYS_COST = 120;
    private static final int AFTER_10_DAYS_COST = 50;
    private static final int CHARGING_COST = 250;

    private static final int MAX_CARS = 100;
    private static Car[] parkingHistory = new Car[MAX_CARS];
    private static int carCount = 0;

    private static Scanner scanner = new Scanner(System.in);

    /**
     * Main method to run the program.
     * 
     * Requirements:
     * - Continuously display a menu to the user.
     * - Allow the user to select options for parking management.
     * - Exit the program when the user selects "q".
     * 
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        while (true) {
            printMenu();
            String option = scanner.nextLine().trim();

            switch (option) {
                case "1":
                    driveIn();
                    break;
                case "2":
                    driveOut();
                    break;
                case "3":
                    checkParking();
                    break;
                case "4":
                    printParkingHistoryByDate();
                    break;
                case "5":
                    printParkingHistoryByRegistration();
                    break;
                case "q":
                    System.out.println("Exiting program...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    /**
     * Prints the main menu options.
     * 
     * Requirements:
     * - Display all available options for the user.
     * - Prompt the user to enter their choice.
     */
    private static void printMenu() {
        System.out.println("----------------------------------");
        System.out.println("# LULEA AIRPORT PARKING LOT");
        System.out.println("----------------------------------");
        System.out.println("1. Drive in");
        System.out.println("2. Drive out");
        System.out.println("3. Check parking");
        System.out.println("4. Print parking history (by arrival date)");
        System.out.println("5. Print parking history (by registration number)");
        System.out.println("q. End program");
        System.out.print("> Enter your option: ");
    }

    /**
     * Handles the "Drive in" functionality.
     * 
     * Requirements:
     * - Check if the parking lot is full.
     * - Validate the registration number and date format.
     * - Allow the user to specify if the car is charging.
     * - Add the car to the parking history.
     */
    private static void driveIn() {
        if (carCount >= MAX_CARS) {
            System.out.println("Parking lot is full.");
            return;
        }

        System.out.print("> Enter registration number: ");
        String regNumber = scanner.nextLine().trim();
        if (!isValidRegistrationNumber(regNumber)) {
            System.out.println("Invalid registration number. It must be 3-8 characters long.");
            return;
        }

        System.out.print("> Current date (YYYY-MM-DD): ");
        String date = scanner.nextLine().trim();
        if (!isValidDate(date)) {
            System.out.println("Invalid date format.");
            return;
        }

        System.out.print("> Charge electric vehicle (Yes/No): ");
        String chargeInput = scanner.nextLine().trim().toLowerCase();
        boolean isCharging = chargeInput.equals("yes");

        parkingHistory[carCount++] = new Car(regNumber, date, isCharging);
        System.out.println("Car " + regNumber + " entered at " + date);
    }

    /**
     * Handles the "Drive out" functionality.
     * 
     * Requirements:
     * - Validate the registration number and exit date.
     * - Ensure the car is currently parked.
     * - Calculate the parking cost based on the number of days and charging status.
     * - Mark the car as exited.
     */
    private static void driveOut() {
        System.out.print("> Enter registration number: ");
        String regNumber = scanner.nextLine().trim();

        Car car = findCarByRegistration(regNumber);
        if (car == null || car.getExitDate() != null) {
            System.out.println("Car is not currently parked.");
            return;
        }

        System.out.print("> Current date (YYYY-MM-DD): ");
        String exitDate = scanner.nextLine().trim();
        if (!isValidDate(exitDate) || !isExitDateValid(car.getEntryDate(), exitDate)) {
            System.out.println("Invalid exit date.");
            return;
        }

        car.setExitDate(exitDate);
        int daysParked = calculateDays(car.getEntryDate(), exitDate);
        int cost = calculateCost(daysParked, car.isCharging());

        System.out.println("###################################");
        System.out.println("# RECEIPT PARKING #");
        System.out.println("###################################");
        System.out.println("# Reg IN OUT #");
        System.out.println("# " + car.getRegistrationNumber() + " " + car.getEntryDate() + " " + exitDate + " #");
        System.out.println("# #");
        System.out.println("# Number of days: " + daysParked + " days #");
        System.out.println("# Charge: " + (car.isCharging() ? "Yes" : "No") + " #");
        System.out.println("# Cost: " + cost + " kr #");
        System.out.println("###################################");
    }

    /**
     * Checks if a car is currently parked.
     * 
     * Requirements:
     * - Validate the registration number.
     * - Display whether the car is currently parked or not.
     */
    private static void checkParking() {
        System.out.print("> Enter registration number: ");
        String regNumber = scanner.nextLine().trim();

        Car car = findCarByRegistration(regNumber);
        if (car == null || car.getExitDate() != null) {
            System.out.println("Car " + regNumber + " is not parked at the moment.");
        } else {
            System.out.println("Car " + regNumber + " is currently parked since " + car.getEntryDate());
        }
    }

    /**
     * Prints parking history sorted by entry date.
     * 
     * Requirements:
     * - Sort the parking history by entry date.
     * - Display the sorted parking history.
     */
    private static void printParkingHistoryByDate() {
        System.out.println("Parking history sorted by entrance date");
        System.out.println("Registration  Entered       Exited       Charging used       Parking cost");
        sortHistoryByDate();
        printHistory();
    }

    /**
     * Prints parking history sorted by registration number.
     * 
     * Requirements:
     * - Sort the parking history by registration number.
     * - Display the sorted parking history.
     */
    private static void printParkingHistoryByRegistration() {
        System.out.println("Parking history sorted by registration number");
        System.out.println("Registration  Entered       Exited       Charging used       Parking cost");
        sortHistoryByRegistration();
        printHistory();
    }

    /**
     * Validates a registration number.
     * 
     * Requirements:
     * - Ensure the registration number is between 3 and 8 characters long.
     * 
     * @param regNumber The registration number to validate.
     * @return True if valid, false otherwise.
     */
    private static boolean isValidRegistrationNumber(String regNumber) {
        return regNumber.length() >= 3 && regNumber.length() <= 8;
    }

    /**
     * Validates a date string in the format YYYY-MM-DD.
     * 
     * Requirements:
     * - Ensure the date matches the format YYYY-MM-DD.
     * 
     * @param date The date string to validate.
     * @return True if valid, false otherwise.
     */
    private static boolean isValidDate(String date) {
        return date.matches("\\d{4}-\\d{2}-\\d{2}");
    }

    /**
     * Validates if the exit date is within the allowed parking period.
     * 
     * Requirements:
     * - Ensure the exit date is not earlier than the entry date.
     * - Ensure the parking duration does not exceed the maximum allowed days.
     * 
     * @param entryDate The entry date.
     * @param exitDate The exit date.
     * @return True if valid, false otherwise.
     */
    private static boolean isExitDateValid(String entryDate, String exitDate) {
        int daysParked = calculateDays(entryDate, exitDate);
        return daysParked >= 0 && daysParked <= MAX_PARKING_DAYS;
    }

    /**
     * Calculates the number of days between two dates.
     * 
     * Requirements:
     * - Parse the dates and calculate the difference in days.
     * 
     * @param startDate The start date.
     * @param endDate The end date.
     * @return The number of days between the dates.
     */
    private static int calculateDays(String startDate, String endDate) {
        String[] start = startDate.split("-");
        String[] end = endDate.split("-");

        int startYear = Integer.parseInt(start[0]);
        int startMonth = Integer.parseInt(start[1]);
        int startDay = Integer.parseInt(start[2]);

        int endYear = Integer.parseInt(end[0]);
        int endMonth = Integer.parseInt(end[1]);
        int endDay = Integer.parseInt(end[2]);

        int startTotalDays = startYear * 360 + startMonth * 30 + startDay;
        int endTotalDays = endYear * 360 + endMonth * 30 + endDay;

        return endTotalDays - startTotalDays;
    }

    /**
     * Calculates the parking cost based on the number of days and charging status.
     * 
     * Requirements:
     * - Calculate the cost for the first 10 days and subsequent days.
     * - Add an additional cost if the car was charged.
     * 
     * @param days The number of days parked.
     * @param isCharging Whether the car was charged.
     * @return The total parking cost.
     */
    private static int calculateCost(int days, boolean isCharging) {
        int cost = 0;
        for (int i = 1; i <= days; i++) {
            if (i <= 10) {
                cost += FIRST_10_DAYS_COST;
            } else {
                cost += AFTER_10_DAYS_COST;
            }
        }
        if (isCharging) {
            cost += CHARGING_COST;
        }
        return cost;
    }

    /**
     * Finds a car by its registration number.
     * 
     * Requirements:
     * - Search the parking history for a car with the given registration number.
     * 
     * @param regNumber The registration number to search for.
     * @return The car object if found, null otherwise.
     */
    private static Car findCarByRegistration(String regNumber) {
        for (int i = 0; i < carCount; i++) {
            if (parkingHistory[i].getRegistrationNumber().equals(regNumber)) {
                return parkingHistory[i];
            }
        }
        return null;
    }

    /**
     * Sorts the parking history by entry date.
     * 
     * Requirements:
     * - Sort the parking history array in ascending order of entry date.
     */
    private static void sortHistoryByDate() {
        for (int i = 0; i < carCount - 1; i++) {
            for (int j = 0; j < carCount - i - 1; j++) {
                if (parkingHistory[j].getEntryDate().compareTo(parkingHistory[j + 1].getEntryDate()) > 0) {
                    Car temp = parkingHistory[j];
                    parkingHistory[j] = parkingHistory[j + 1];
                    parkingHistory[j + 1] = temp;
                }
            }
        }
    }

    /**
     * Sorts the parking history by registration number.
     * 
     * Requirements:
     * - Sort the parking history array in ascending order of registration number.
     */
    private static void sortHistoryByRegistration() {
        for (int i = 0; i < carCount - 1; i++) {
            for (int j = 0; j < carCount - i - 1; j++) {
                if (parkingHistory[j].getRegistrationNumber().compareTo(parkingHistory[j + 1].getRegistrationNumber()) > 0) {
                    Car temp = parkingHistory[j];
                    parkingHistory[j] = parkingHistory[j + 1];
                    parkingHistory[j + 1] = temp;
                }
            }
        }
    }

    /**
     * Prints the parking history.
     * 
     * Requirements:
     * - Display the parking history in a formatted table.
     * - Include registration number, entry date, exit date, charging status, and cost.
     */
    private static void printHistory() {
        for (int i = 0; i < carCount; i++) {
            Car car = parkingHistory[i];
            System.out.printf("%-13s %-12s %-12s %-16s %-12s%n",
                    car.getRegistrationNumber(),
                    car.getEntryDate(),
                    car.getExitDate() == null ? "" : car.getExitDate(),
                    car.isCharging() ? "Yes" : "No",
                    car.getExitDate() == null ? "" : calculateCost(calculateDays(car.getEntryDate(), car.getExitDate()), car.isCharging()) + "kr");
        }
    }
}

/**
 * Represents a car in the parking lot.
 */
class Car {
    private String registrationNumber;
    private String entryDate;
    private String exitDate;
    private boolean isCharging;

    /**
     * Constructor for the Car class.
     * 
     * @param registrationNumber The car's registration number.
     * @param entryDate The date the car entered the parking lot.
     * @param isCharging Whether the car is being charged.
     */
    public Car(String registrationNumber, String entryDate, boolean isCharging) {
        this.registrationNumber = registrationNumber;
        this.entryDate = entryDate;
        this.isCharging = isCharging;
    }

    /**
     * Gets the car's registration number.
     * 
     * @return The registration number.
     */
    public String getRegistrationNumber() {
        return registrationNumber;
    }

    /**
     * Gets the car's entry date.
     * 
     * @return The entry date.
     */
    public String getEntryDate() {
        return entryDate;
    }

    /**
     * Gets the car's exit date.
     * 
     * @return The exit date.
     */
    public String getExitDate() {
        return exitDate;
    }

    /**
     * Sets the car's exit date.
     * 
     * @param exitDate The exit date.
     */
    public void setExitDate(String exitDate) {
        this.exitDate = exitDate;
    }

    /**
     * Checks if the car is being charged.
     * 
     * @return True if charging, false otherwise.
     */
    public boolean isCharging() {
        return isCharging;
    }
}