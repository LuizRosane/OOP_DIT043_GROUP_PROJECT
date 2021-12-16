package Menus;

import Util.Input;
import facade.Facade;

public class MainMenu {

    private static String EOF = System.lineSeparator();

    public static Facade facade = new Facade();

    public static void mainMenu(){

        String EOF = System.lineSeparator();

        int answer = 0; //Declaring and also initializing (which is not obligatory) following good practice

        do {

            //display main menu
            System.out.println("Main Menu: Please choose among the options below." + EOF + EOF +
                    "0. Close system." + EOF +
                    "1. Open Item options." + EOF +
                    "2. Open Review options." + EOF +
                    "3. Open Transaction History options." + EOF +
                    "4. Open Employee options." + EOF);

            //read user's answer
            answer = Input.readInt("Type an option number: ");

            //main menu's commands
            switch (answer){
                case 0:
                    break;

                case 1:
                    itemMenu();
                    break;

                case 2:
                    reviewsOptionsMenu();
                    break;
                case 3:
                    transactionHistoryMenu();
                    break;
                case 4:
                    employeeMenu();
                    break;

                default:
                    System.out.println("Invalid menu option. Please type another option" + EOF);
                    break;

            }
        } while (answer != 0);

        Input.input.close();

    }

    private static void itemMenu(){
        int answer= 0;

        do{

            //display item menu
            System.out.println("Item options menu:" + EOF +
                    "0. Return to Main Menu." +EOF +
                    "1. Create an Item." + EOF +
                    "2. Remove an Item." + EOF +
                    "3. Print all registered Items." + EOF +
                    "4. Buy an Item." + EOF +
                    "5. Update an item’s name." + EOF +
                    "6. Update an item’s price." + EOF +
                    "7. Print a specific item." + EOF);

            //reads the user's input
            answer = Input.readInt("Type an option number: ");


            //item menu's commands
            switch (answer){
                case 0: break;

                case 1:
                    String itemId       = Input.readString("Type the new item's ID: ");
                    String itemName     = Input.readString("Type the new item's name: ");
                    double pricePerUnit = Input.readDouble("Add a price: ");
                    System.out.println(MainMenu.facade.createItem(itemId,itemName,pricePerUnit));
                    System.out.println();
                    break;

                case 2:
                    String itemIdToRemove = Input.readString("Type the item's ID from the item to be removed: ");
                    System.out.println(MainMenu.facade.removeItem(itemIdToRemove));
                    System.out.println();
                    break;

                case 3:
                    System.out.println(MainMenu.facade.printAllItems());
                    break;

                case 4:
                    String itemIdToBeBought       = Input.readString("Type item's ID that it will be bought: ");
                    int amount = Input.readInt("Type the amount of items: ");
                    System.out.println(MainMenu.facade.buyItem(itemIdToBeBought, amount));
                    System.out.println();
                    break;

                case 5:
                    String itemIdUpdateName = Input.readString("Type the item's ID from the item to be updated: ");
                    String itemNewName = Input.readString("Type new name: ");
                    System.out.println(MainMenu.facade.updateItemName(itemIdUpdateName, itemNewName));
                    System.out.println();
                    break;

                case 6:
                    double itemNewPrice = Input.readDouble("Type the item's ID from the item to be updated: ");
                    String itemIdUpdatePrice = Input.readString("Type new price: ");
                    System.out.println(MainMenu.facade.updateItemPrice(itemIdUpdatePrice, itemNewPrice));
                    System.out.println();
                    break;

                case 7:
                    String itemIdToBePrinted = Input.readString("Type the item's ID from the item to be printed: ");
                    System.out.println(MainMenu.facade.printItem(itemIdToBePrinted));
                    System.out.println();
                    break;

                default: System.out.println("Invalid menu option. Please type another option" + EOF);
                    break;
            }
        } while (answer !=0);

    }

    private static void reviewsOptionsMenu(){
        int answer = 0;

        do{

            //display reviews options menu
            System.out.println("Reviews options menu:" + EOF +
                    "0. Return to Main Menu." + EOF +
                    "1. Create a review for an Item." + EOF +
                    "2. Print a specific review of an Item." + EOF +
                    "3. Print all reviews of an Item." + EOF +
                    "4. Print mean grade of an Item" + EOF +
                    "5. Print all comments of an Item." + EOF +
                    "6. Print all registered reviews." + EOF +
                    "7. Print item(s) with most reviews." + EOF +
                    "8. Print item(s) with least reviews." + EOF +
                    "9. Print item(s) with best mean review grade." + EOF +
                    "10. Print item(s) with worst mean review grade." + EOF);

            //read user's answer
            answer = Input.readInt("Type an option number: ");



            //review option menu's commands
            switch (answer){
                case 0: break;

                case 1:
                    String itemID = Input.readString("Enter the ID of the item you wish to review: ");
                    String reviewComment = Input.readString("Enter review comment (optional): ");
                    int reviewGrade = Input.readInt("Input the review grade: ");
                    if (reviewComment.isBlank()) {
                        System.out.println(MainMenu.facade.reviewItem(itemID, reviewGrade));
                    } else {
                        System.out.println(MainMenu.facade.reviewItem(itemID, reviewComment, reviewGrade));
                    }
                    break;

                case 2:
                    String itemID2 = Input.readString("Enter the ID of the item review you wish to print: ");
                    int reviewNumber = Input.readInt("Enter the review number of the review you wish to print: ");
                    System.out.println(MainMenu.facade.getPrintedItemReview(itemID2, reviewNumber));
                    break;

                case 3:
                    String itemID3 = Input.readString("Enter the item ID to print all reviews of the item: ");
                    System.out.println(MainMenu.facade.getPrintedReviews(itemID3));
                    break;

                case 4:
                    String itemID4 = Input.readString("Enter item ID to print mean grade for that item: ");
                    if (MainMenu.facade.containsItem(itemID4)) {

                        if (MainMenu.facade.getItemMeanGrade(itemID4) > 0.0){
                            System.out.println(MainMenu.facade.getItemMeanGrade(itemID4));
                        } else {
                            System.out.println("Item " + itemID4 + " has not been reviewed yet.");
                        }
                    } else {
                        System.out.println("Item " + itemID4 + " was not registered yet.");
                    }
                    System.out.println();

                    break;

                case 5:
                    String itemID5 = Input.readString("Enter ID of the item to see the comments for that item: ");
                    System.out.println(MainMenu.facade.getItemCommentsPrinted(itemID5));
                    break;

                case 6:
                    System.out.println(MainMenu.facade.printAllReviews());
                    break;

                case 7:
                    System.out.println(MainMenu.facade.printMostReviewedItems());
                    break;

                case 8:
                    System.out.println(MainMenu.facade.printLeastReviewedItems());
                    break;

                case 9:
                    System.out.println(MainMenu.facade.printBestReviewedItems());
                    break;

                case 10:
                    System.out.println(MainMenu.facade.printWorseReviewedItems());
                    break;

                default: System.out.println("Invalid menu option. Please type another option" + EOF);
                    break;

            }
        }while (answer != 0);
    }

    private static void transactionHistoryMenu(){
        int answer = 0;

        do{

            //display transaction history menu
            System.out.println("Transaction History options menu:" + EOF +
                    "0. Return to Main Menu." + EOF +
                    "1. Print total profit from all item purchases" + EOF +
                    "2. Print total units sold from all item purchases" + EOF +
                    "3. Print the total number of item transactions made." + EOF +
                    "4. Print all transactions made" + EOF +
                    "5. Print the total profit of a specific item." + EOF +
                    "6. Print the number of units sold of a specific item." + EOF +
                    "7. Print all transactions of a specific item." + EOF +
                    "8. Print item with highest profit." + EOF);

            //read user's answer
            answer = Input.readInt("Type an option number: ");

            //transaction history menu's commands
            switch (answer){
                case 0: break;
                case 1:
                    System.out.println(MainMenu.facade.getTotalProfit());
                    break;

                case 2:
                    System.out.println(MainMenu.facade.getTotalUnitsSold());
                    break;

                case 3:
                    System.out.println(MainMenu.facade.getTotalTransactions());
                    break;

                case 4:
                    System.out.println(MainMenu.facade.printAllTransactions());
                    break;

                case 5:
                    String itemIdToGetProfit = Input.readString("Type the item's ID to show it's profit: ");
                    System.out.println(MainMenu.facade.getProfit(itemIdToGetProfit));
                    break;

                case 6:
                    String itemIdToGetTotalUnitsSold = Input.readString("Type the item's ID to show how many units were sold: ");
                    System.out.println(MainMenu.facade.getUnitsSolds(itemIdToGetTotalUnitsSold));
                    break;

                case 7:
                    String itemIdToBePrintedAllTransactions = Input.readString("Type the item's ID from the item to be print all it's transactions: ");
                    System.out.println(MainMenu.facade.printItemTransactions(itemIdToBePrintedAllTransactions));
                    break;

                case 8:
                    System.out.println(MainMenu.facade.printMostProfitableItems());
                    break;

                default: System.out.println("Invalid menu option. Please type another option" + EOF);
                    break;
            }
        } while (answer !=0);
    }

    private static void employeeMenu(){

            int answer= 0;

            do{

                //display item menu
                System.out.println("Employee options menu:" + EOF +
                        "0. Return to Main Menu." +EOF +
                        "1. Create an employee (Regular Employee)." + EOF +
                        "2. Create an employee (Manager)." + EOF +
                        "3. Create an employee (Director)." + EOF +
                        "4. Create an employee (Intern)." + EOF +
                        "5. Remove an employee." + EOF +
                        "6. Print specific employee." + EOF +
                        "7. Print all registered employees." + EOF +
                        "8. Print the total expense with net salary." + EOF +
                        "9. Print all employees sorted by gross salary." + EOF);

                //reads the user's input
                answer = Input.readInt("Type an option number: ");


                //item menu's commands
                switch (answer) {
                    case 0: break;

                    case 1:
                        String employeeId1          = Input.readString("Type the new employee's ID: ");
                        String employeeName1        = Input.readString("Type the regular employee's name: ");
                        double employeeGrossSalary1 = Input.readDouble("Add a gross salary: ");
                        try {
                            System.out.println(MainMenu.facade.createEmployee(employeeId1, employeeName1, employeeGrossSalary1));
                            System.out.println();
                        } catch (Exception exception) {
                            System.out.println(exception.getMessage());
                            System.out.println();
                        }
                        break;

                    case 2:
                        String employeeId2          = Input.readString("Type the new employee's ID: ");
                        String employeeName2        = Input.readString("Type the manager's name: ");
                        double employeeGrossSalary2 = Input.readDouble("Add a gross salary: ");
                        String degree1              = Input.readString("Add the manager's degree: ");
                        try {
                            System.out.println(MainMenu.facade.createEmployee(employeeId2, employeeName2, employeeGrossSalary2, degree1));
                            System.out.println();
                        } catch (Exception exception) {
                            System.out.println(exception.getMessage());
                            System.out.println();
                        }
                        break;

                    case 3:
                        String employeeId3          = Input.readString("Type the new employee's ID: ");
                        String employeeName3        = Input.readString("Type the director's name: ");
                        double employeeGrossSalary3 = Input.readDouble("Add a gross salary: ");
                        String degree2              = Input.readString("Add the director's degree: ");
                        String department           = Input.readString("Add the director's department: ");
                        try {
                            System.out.println(MainMenu.facade.createEmployee(employeeId3, employeeName3, employeeGrossSalary3, degree2, department));
                            System.out.println();
                        } catch (Exception exception) {
                            System.out.println(exception.getMessage());
                            System.out.println();
                        }
                        break;

                    case 4:
                        String employeeId4          = Input.readString("Type the new employee's ID: ");
                        String employeeName4        = Input.readString("Type the intern's name: ");
                        double employeeGrossSalary4 = Input.readDouble("Add a gross salary: ");
                        int gpa                     = Input.readInt   ("Add intern's GPA: ");
                        try {
                            System.out.println(MainMenu.facade.createEmployee(employeeId4, employeeName4, employeeGrossSalary4, gpa));
                            System.out.println();
                        }catch (Exception exception) {
                            System.out.println(exception.getMessage());
                            System.out.println();
                        }
                        break;

                    case 5:
                        String employeeIdToRemove = Input.readString("Type the employee ID from the employee you wish to remove: ");
                        try {
                            System.out.print(MainMenu.facade.removeEmployee(employeeIdToRemove));
                            System.out.println();
                        } catch (Exception exception) {
                            System.out.println(exception.getMessage());
                            System.out.println();
                        }
                        break;

                    case 6:
                        String employeeId6 = Input.readString("Enter the ID of the employee you wish to print: ");
                        try {
                            System.out.println(MainMenu.facade.printEmployee(employeeId6));
                            System.out.println();
                        } catch (Exception exception) {
                            System.out.println(exception.getMessage());
                            System.out.println();
                        }
                        break;

                    case 7:
                        try {
                            System.out.println(MainMenu.facade.printAllEmployees());
                            System.out.println();
                        } catch (Exception exception) {
                            System.out.println(exception.getMessage());
                            System.out.println();
                        }
                        break;

                    case 8:
                        try {
                            System.out.println(MainMenu.facade.getTotalNetSalary());
                            System.out.println();
                        } catch (Exception exception) {
                            System.out.println(exception.getMessage());
                            System.out.println();
                        }
                        break;

                    case 9:
                        try {
                            System.out.println(MainMenu.facade.printSortedEmployees());
                            System.out.println();
                        } catch (Exception exception) {
                            System.out.println(exception.getMessage());
                            System.out.println();
                        }
                }
            } while (answer != 0);
    }

}


