package facade;

import BusinessLogic.*;
import Util.Truncate;

import java.util.*;

public class Facade {

    // This class only has the skeleton of the methods used by the test.
    // You must fill in this class with your own code. You can (and should) create more classes
    // that implement the functionalities listed in the Facade and in the Test Cases.
    String EOF = System.lineSeparator();

    private ArrayList<Item> listOfItems;
    private ArrayList<Transaction> transactionHistory;
    private ArrayList<Employee> staff;

    public Facade(){
        this.listOfItems = new ArrayList<>();
        this.transactionHistory = new ArrayList<>();
        this.staff = new ArrayList<>();
    }

    public String createItem(String itemID, String itemName, double unitPrice){
        if (itemID.isBlank() || this.containsItem(itemID) || itemName.isBlank() || unitPrice <= 0) {
            return "Invalid data for item.";
        } else {
            Item newItem = new Item(itemID, itemName, unitPrice);
            this.listOfItems.add(newItem);
            return "Item " + newItem.getItemID() + " was registered successfully.";
        }
    }

    public String printItem(String itemID) {
        String message = "";
        if (this.containsItem(itemID)) {
            for (Item currentItem : listOfItems) {
                if (itemID.equals(currentItem.getItemID())) {
                    message = currentItem.toString();
                }
            }
        } else {
            message = "Item " + itemID + " was not registered yet.";
        }
        return message;
    }

    public String removeItem(String itemID) {
        if (this.containsItem(itemID)) {
            this.listOfItems.remove(this.retrieveItem(itemID));
            return "Item " + itemID + " was successfully removed.";
        } else {
            return "Item " + itemID + " could not be removed.";
        }
    }

    public boolean containsItem(String itemID) {
        boolean exists = false;
        for (Item currentItem : this.listOfItems) {
            if (itemID.equals(currentItem.getItemID())) {
                exists = true;
                break;
            }
        }
        return exists;
    }

    public double buyItem(String itemID, int amount) {
        if (this.containsItem(itemID)){
            Item tempItem = this.retrieveItem(itemID);

            double unitPrice = tempItem.getPricePerUnit();
            Transaction transaction = new Transaction(itemID, unitPrice, amount);

            this.transactionHistory.add(transaction);

            return transaction.getPurchasePrice();
        } else {
            return -1.0;
        }
    }

    public String reviewItem(String itemID, String reviewComment, int reviewGrade) {
        String message = "";
        if (!this.containsItem(itemID)) {
            message = "Item " + itemID + " was not registered yet.";

        } else if (reviewGrade < 0 || reviewGrade > 5) {
            message = "Grade values must be between 1 and 5.";

        } else {
            Review newReview = new Review(reviewGrade, reviewComment);
            Item tempItem = this.retrieveItem(itemID);
            tempItem.addReviewToItem(newReview);
            message = "Your item review was registered successfully.";

        }
        return message;
    }

    public String reviewItem(String itemID, int reviewGrade) {
        String message = "";
        if (!this.containsItem(itemID)) {
            message = "Item " + itemID + " was not registered yet.";

        } else if (reviewGrade < 0 || reviewGrade > 5) {
            message = "Grade values must be between 1 and 5.";

        } else {
            Review newReview = new Review(reviewGrade);
            Item tempItem = this.retrieveItem(itemID);
            tempItem.addReviewToItem(newReview);
            message = "Your item review was registered successfully.";
        }
        return message;
    }

    public String getItemCommentsPrinted(String itemID) {
        Item tempItem = this.retrieveItem(itemID);
        ArrayList <String> listOfComments = tempItem.retrieveListOfComments();
        String message = "";
        for (String comment : listOfComments){
            message += comment;
        }
        return "Written comments for item " + tempItem.getItemID() + ":" + EOF +
                message;

    }

    public List<String> getItemComments(String itemID) {
        Item tempItem = retrieveItem(itemID);

        return tempItem.retrieveListOfComments();
    }

    public double getItemMeanGrade(String itemID) {
        Item tempItem = retrieveItem(itemID);
        if (tempItem.checkIfItemHasReview()){
            return tempItem.getMeanGrade();
        } else {
            return 0.0;
        }
    }

    public int getNumberOfReviews(String itemID) {
        ArrayList<Review> amountOfComments = new ArrayList<>();
        int numOfReviews;

        Item tempItem = retrieveItem(itemID);

        amountOfComments = tempItem.getListOfReviews();
        if (amountOfComments.isEmpty()) {
            numOfReviews = 0;
        } else {
            numOfReviews = amountOfComments.size();
        }
        return numOfReviews;
    }

    public String getPrintedItemReview(String itemID, int reviewNumber) {
        Item tempItem = retrieveItem(itemID);
        String message = "";

        if (!(this.containsItem(itemID))) {
            message = "Item " + itemID + " was not registered yet.";
        } else if (!(tempItem.checkIfItemHasReview())) {
            message = "Item " + tempItem.getItemName() + " has not been reviewed yet.";

        } else if (tempItem.getListOfReviews().size() < reviewNumber || reviewNumber < 1) {
            message = "Invalid review number. Choose between 1 and " + tempItem.getListOfReviews().size() + ".";

        } else if (tempItem.checkIfItemHasReview()) {
            Review tempReview = this.retrieveReview(itemID, reviewNumber - 1);
            message = tempReview.toString();
        }
        return message;
    }

    public String getPrintedReviews(String itemID) {
        String message = "";
        String message2 = "";

        if (!(this.containsItem(itemID))) {
            message = "Item " + itemID + " was not registered yet.";
        } else {
            Item tempItem = retrieveItem(itemID);
            if (tempItem.checkIfItemHasReview()) {
                message2 = tempItem.printReviewsForItem();
                message = "Review(s) for " + tempItem + System.lineSeparator() + message2;


            } else {
                message = "Review(s) for " + tempItem + System.lineSeparator() + "The item " + tempItem.getItemName() + " has not been reviewed yet.";
            }
        }
        return message;
    }

    public String printMostReviewedItems() {
        String message = "";

        List<String> itemsMostReviewed = this.getMostReviewedItems();

        if (listOfItems.isEmpty()) {
            message = "No items registered yet.";
        }else if (!this.reviewsWereMade()){
            message = "No items were reviewed yet.";
        } else  {
            for (int i = 0; i < itemsMostReviewed.size(); i++) {
                String itemID = itemsMostReviewed.get(i);

                Item tempItem = retrieveItem(itemID);
                message += tempItem.toString() + System.lineSeparator();
            }
            Item tempItem = retrieveItem(itemsMostReviewed.get(0));

            message = "Most reviews: " + tempItem.getSizeOfReviewList() + " review(s) each." + System.lineSeparator() + message;
        }
        return message;
    }

    public List<String> getMostReviewedItems() {
        ArrayList<String> listOfMostReviewedItems = new ArrayList<>();
        String message = "";
        int mostReviewedItem = 0;

        for (Item item : listOfItems) {
            if (item.checkIfItemHasReview()){
                if (item.getSizeOfReviewList() > mostReviewedItem) {
                    mostReviewedItem = item.getSizeOfReviewList();

                    message = item.getItemID();
                    listOfMostReviewedItems.clear();
                    listOfMostReviewedItems.add(message);

                } else if (item.getSizeOfReviewList() == mostReviewedItem) {
                    message = item.getItemID();
                    listOfMostReviewedItems.add(message);
                }
            }
        }
        return listOfMostReviewedItems;
    }

    public List<String> getLeastReviewedItems() {
        ArrayList<String> listWithLeastReviewedItems = new ArrayList<>();
        String message = "";
        int leastReviewedItem = 0;

        for (Item item : listOfItems) {
            if (item.checkIfItemHasReview()) {
                leastReviewedItem = item.getSizeOfReviewList();
                break;
            }
        }
        for (Item currentItem : listOfItems) {
            if (currentItem.checkIfItemHasReview()) {
                if (currentItem.getSizeOfReviewList() < leastReviewedItem) {
                    leastReviewedItem = currentItem.getSizeOfReviewList();

                    message = currentItem.getItemID();
                    listWithLeastReviewedItems.clear();
                    listWithLeastReviewedItems.add(message);

                } else if (currentItem.getSizeOfReviewList() == leastReviewedItem) {
                    message = currentItem.getItemID();
                    listWithLeastReviewedItems.add(message);
                }
            }
        }
        return listWithLeastReviewedItems;
    }

    public String printLeastReviewedItems() {
        String message = "";
        List<String> leastReviewedItems = getLeastReviewedItems();

        if (listOfItems.isEmpty()) {
            message = "No items registered yet.";
        } else if (!this.reviewsWereMade()){
            message = "No items were reviewed yet.";
        } else {
            for (int i = 0; i < leastReviewedItems.size(); i++) {
                String itemID = leastReviewedItems.get(i);

                Item tempItem = retrieveItem(itemID);
                message += tempItem.toString() + System.lineSeparator();
            }
            Item tempItem = retrieveItem(leastReviewedItems.get(0));

            message = "Least reviews: " + tempItem.getSizeOfReviewList() + " review(s) each." + System.lineSeparator() + message;
        }
        return message;
    }

    public double getTotalProfit() {
        if (this.transactionHistory.isEmpty()){
            return 0;
        } else {
            double totalProfit = 0;
            for (Transaction transaction : this.transactionHistory){
                totalProfit = totalProfit + transaction.getPurchasePrice();
            }
            return totalProfit;
        }
    }

    public String printItemTransactions(String itemID) {

        String message = "";
        if (!this.containsItem(itemID)){
            message = "Item " + itemID + " was not registered yet.";

        } else if (!this.itemHasTransactions(itemID)){
            Item item = retrieveItem(itemID);
            message = "Transactions for item: " + item.toString() + EOF +
            "No transactions have been registered for item " + itemID + " yet.";

        } else {
            Item item = retrieveItem(itemID);
            message = "Transactions for item: " + item.toString() + EOF;

            //concatenating the message to all the transactions that have the same itemID as the one passed
            for (Transaction currentTransaction : this.transactionHistory){
                if (currentTransaction.getItemID().equals(itemID)){
                    message += currentTransaction.toString() + EOF;
                }
            }
        }

        return message;
    }

    public int getTotalUnitsSold() {
        if (this.transactionHistory.isEmpty()){
            return 0;
        } else {
            int totalUnitsSold = 0;
            //summing to the totalUnitsSold all the amounts from the transactions that have the same itemID as the one passed
            for (Transaction transaction : this.transactionHistory){
                totalUnitsSold = totalUnitsSold + transaction.getAmount();
            }
            return totalUnitsSold;
        }
    }

    public int getTotalTransactions() {
        if (this.transactionHistory.isEmpty()){
            return 0;
        } else {
            return this.transactionHistory.size();
        }
    }

    public double getProfit(String itemID) {
        double profit = 0.0;
        if(this.containsItem(itemID) && this.itemHasTransactions(itemID)){
            //summing to the profit all the purchase price from the transactions that have the same itemID as the one passed
            for (Transaction transaction : this.transactionHistory){
                if (transaction.getItemID().equals(itemID)){
                    profit = profit + transaction.getPurchasePrice();
                }
            }
        }
        return profit;
    }

    public int getUnitsSolds(String itemID) {
        int unitsSold = 0;
        if (this.containsItem(itemID) && this.itemHasTransactions(itemID)){
            for (Transaction transaction : this.transactionHistory){
                if (transaction.getItemID().equals(itemID)){
                    unitsSold = unitsSold + transaction.getAmount();
                }
            }
        }
        return unitsSold;
    }

    public String printAllTransactions() {
        String message = "";
        if (this.transactionHistory.isEmpty()){
            message = "All purchases made: " + EOF +
                      "Total profit: 0.00 SEK" + EOF +
                      "Total items sold: 0 units" + EOF +
                      "Total purchases made: 0 transactions" + EOF +
                      "------------------------------------" + EOF +
                      "------------------------------------" + EOF;
        } else {
            message = "All purchases made: " + EOF +
                      "Total profit: " + String.format("%.2f",this.getTotalProfit()) + " SEK" + EOF +
                      "Total items sold: " + this.getTotalUnitsSold() + " units" + EOF +
                      "Total purchases made: " + this.getTotalTransactions() + " transactions" + EOF +
                      "------------------------------------" + EOF;

            //concatenating the message with the transaction.toString() for every transaction in the transaction history
            for(Transaction transaction : this.transactionHistory){
                message += transaction.toString() + EOF;
            }
            message += "------------------------------------" + EOF;
        }
        return message;

    }

    public String printWorseReviewedItems() {
        List<String> worstGradedItems = this.getWorseReviewedItems();
        String message = "";

        if (this.listOfItems.isEmpty()) {
            message = "No items registered yet.";
        } else if (!worstGradedItems.isEmpty()) {
            for (String itemID : worstGradedItems) {
                Item tempItem = this.retrieveItem(itemID);
                message += tempItem.toString() + EOF;
            }
            Item tempItem = this.retrieveItem(worstGradedItems.get(0));
            message = "Items with worst mean reviews:" + EOF +
                    "Grade: " + tempItem.getMeanGrade() + EOF + message;
        } else {
            message = "No items were reviewed yet.";
            }
        return message;
    }


    public String printBestReviewedItems() {
        List<String> bestGradedItems = this.getBestReviewedItems();
        String message = "";

        if (this.listOfItems.isEmpty()) {
            message = "No items registered yet.";
        } else if (bestGradedItems.isEmpty()) {
            message = "No items were reviewed yet.";

        } else {
            for (String itemID : bestGradedItems) {
                Item tempItem = this.retrieveItem(itemID);
                message += tempItem.toString() + EOF;
            }
            Item tempItem = this.retrieveItem(bestGradedItems.get(0));
            message = "Items with best mean reviews:" + EOF +
                    "Grade: " + tempItem.getMeanGrade() + EOF + message;
        }
        return message;
    }


    public List<String> getWorseReviewedItems() {
        List <String> worstGradedItems = new ArrayList<>();
        double worstGrade = 0.0;

        if (!this.listOfItems.isEmpty() && this.reviewsWereMade()){
            //return as best grade the first item that has a review
            for (Item currentItem : listOfItems){
                if (currentItem.checkIfItemHasReview()){
                    worstGrade = currentItem.getMeanGrade();
                    break;
                }
            }

            //compare and (may) change and the best review and add to the list
            for (Item currentItem : this.listOfItems){
                if (currentItem.checkIfItemHasReview() && currentItem.getMeanGrade() < worstGrade){
                    worstGrade = currentItem.getMeanGrade();
                    worstGradedItems.clear();
                    worstGradedItems.add(currentItem.getItemID());

                } else if (currentItem.getMeanGrade() == worstGrade){
                    worstGradedItems.add(currentItem.getItemID());
                }
            }
        }
        return worstGradedItems;
    }

    public List<String> getBestReviewedItems() {
        List <String> bestGradedItems = new ArrayList<>();
        double bestGrade = 0.0;

        if (!this.listOfItems.isEmpty() && this.reviewsWereMade()){
            //return as best grade the first item that has a review
            for (Item currentItem : listOfItems){
                if (currentItem.checkIfItemHasReview()){
                    bestGrade = currentItem.getMeanGrade();
                    break;
                }
            }

            //compare and (may) change and the best review and add to the list
            for (Item currentItem : this.listOfItems){
                if (currentItem.getMeanGrade() > bestGrade){
                    bestGrade = currentItem.getMeanGrade();
                    bestGradedItems.clear();
                    bestGradedItems.add(currentItem.getItemID());

                } else if (currentItem.getMeanGrade() == bestGrade){
                    bestGradedItems.add(currentItem.getItemID());
                }
            }
        }
        return bestGradedItems;
    }


    public String printAllReviews() {
        String message = "";
        if (this.listOfItems.isEmpty()){
            message = "No items registered yet.";
        } else if (this.reviewsWereMade()){
            message = "All registered reviews:" + EOF +
                    "------------------------------------" + EOF;
            for (Item currentItem : this.listOfItems ){
                if (currentItem.checkIfItemHasReview()){
                    message += currentItem.printAllReviews()  +
                            "------------------------------------" + EOF;
                }
            }
        } else {
            message = "No items were reviewed yet.";
        }
        return message;
    }

    public String updateItemName(String itemID, String newName) {
        if (this.containsItem(itemID)) {
            if (!newName.isBlank()) {
                Item item = this.retrieveItem(itemID);
                item.setItemName(newName);
                return "Item " + item.getItemID() + " was updated successfully.";
            } else {
                return "Invalid data for item.";
            }
        } else {
            return "Item " + itemID + " was not registered yet.";
        }
    }

    public String updateItemPrice(String itemID, double newPrice) {
        if (this.containsItem(itemID)) {
            if (newPrice > 0) {
                Item item = this.retrieveItem(itemID);
                item.setPricePerUnit(newPrice);
                return "Item " + item.getItemID() + " was updated successfully.";
            } else {
                return "Invalid data for item.";
            }
        } else {
            return "Item " + itemID + " was not registered yet.";
        }
    }

    public String printAllItems() {
        if (this.listOfItems.size() < 1){
            return "No items registered yet.";
        } else {
            return "All registered items:" + System.lineSeparator() + this.toStringAllItems();
        }
    }

    public String printMostProfitableItems() {
        String message = "";
        if (this.listOfItems.isEmpty()){
            message = "No items registered yet.";

        } else if (this.transactionHistory.isEmpty()){
            message = "No items were bought yet.";

        } else {

            ArrayList<Item> mostProfitableItems = new ArrayList<>();
            double maxProfit = 0.0;

            for (Item item : this.listOfItems){
                if (this.getProfit(item.getItemID()) > maxProfit){

                    mostProfitableItems.clear();
                    mostProfitableItems.add(item);
                    maxProfit = this.getProfit(item.getItemID());

                } else if (this.getProfit(item.getItemID()) == maxProfit){
                    mostProfitableItems.add(item);
                }
            }

            message = "Most profitable items: " + EOF +
                      "Total profit: " + String.format("%.2f", maxProfit) + " SEK" + EOF;

            for (Item item : mostProfitableItems){
                message += item.toString() + EOF;
            }
        }

        return message;
    }

    public String createEmployee(String employeeID, String employeeName, double grossSalary) throws Exception {
        Employee newEmployee = new Employee(employeeID, employeeName, grossSalary);
        this.staff.add(newEmployee);
        return "Employee " + employeeID + " was registered successfully.";
    }

    public String printEmployee(String employeeID) throws Exception {
        Employee currentEmployee = this.retrieveEmployee(employeeID);
        return currentEmployee.toString();
    }

    public String createEmployee(String employeeID, String employeeName, double grossSalary, String degree) throws Exception {
        Employee newManager = new Manager(employeeID, employeeName, grossSalary, degree);
        this.staff.add(newManager);
        return "Employee " + employeeID + " was registered successfully.";
    }

    public String createEmployee(String employeeID, String employeeName, double grossSalary, int gpa) throws Exception {
        Intern newIntern = new Intern(employeeID, employeeName, grossSalary, gpa);
        this.staff.add(newIntern);
        return "Employee " + employeeID + " was registered successfully.";
    }

    public double getNetSalary(String employeeID) throws Exception {
        Employee currentEmployee = this.retrieveEmployee(employeeID);
        return currentEmployee.getNetSalary();
    }

    public String createEmployee(String employeeID, String employeeName, double grossSalary, String degree, String dept) throws Exception {
       Employee newDirector = new Director(employeeID, employeeName, grossSalary, degree, dept);
        this.staff.add(newDirector);
        return "Employee " + employeeID + " was registered successfully.";
    }

    public String removeEmployee(String empID) throws Exception {
        Employee tempEmployee = this.retrieveEmployee(empID);
        this.staff.remove(tempEmployee);
        return "Employee " + tempEmployee.getEmployeeID() + " was successfully removed.";
    }

    public String printAllEmployees() throws Exception {
        String message = "";
        if (staff.isEmpty()){
            throw new Exception("No employees registered yet.");
        }

        message = "All registered employees:" + EOF;
        for (Employee currentEmployee : this.staff) {
            message += currentEmployee.toString() + EOF;
        }

        return message;
    }

    public double getTotalNetSalary() throws Exception {
        double totalNetSalary = 0.0;
        if (staff.isEmpty()){
            throw new Exception("No employees registered yet.");
        }

        for (Employee currentEmployee : this.staff){
            totalNetSalary = totalNetSalary + currentEmployee.getNetSalary();
        }
        return Truncate.truncateValue(totalNetSalary, 2);
    }

    public String printSortedEmployees() throws Exception {
        if (this.staff.isEmpty()){
            throw new Exception("No employees registered yet.");
        }

        List<Employee> sortedEmployeeList = new ArrayList<>();

        sortedEmployeeList.addAll(this.staff);

        Collections.sort(sortedEmployeeList);

        String message = "Employees sorted by gross salary (ascending order):" + EOF;
        for (Employee currentEmployee : sortedEmployeeList){
            message += currentEmployee.toString() + EOF;
        }

        return message;
    }

    public String updateEmployeeName(String empID, String newName) throws Exception {
        Employee tempEmployee = this.retrieveEmployee(empID);
        tempEmployee.setEmployeeName(newName);
        return "Employee " + empID + " was updated successfully";
    }

    public String updateInternGPA(String empID, int newGPA) throws Exception {
        if (newGPA < 0 || newGPA > 10) {
            throw new Exception(newGPA + " outside range. Must be between 0-10.");
        }

        Intern tempIntern = this.retrieveIntern(empID);
        tempIntern.setGpa(newGPA);

        return "Employee " + empID + " was updated successfully";
    }

    public String updateManagerDegree(String empID, String newDegree) throws Exception {
        Manager tempManager = this.retrieveManager(empID);
        tempManager.setDegree(newDegree);

        return "Employee " + empID + " was updated successfully";
    }

    public String updateDirectorDept(String empID, String newDepartment) throws Exception {
        Director tempDirector = this.retrieveDirector(empID);
        tempDirector.setDepartment(newDepartment);

        return "Employee " + empID + " was updated successfully";
    }

    public String updateGrossSalary(String empID, double newSalary) throws Exception {
        Employee tempEmployee = this.retrieveEmployee(empID);
        tempEmployee.setSalary(newSalary);
        return "Employee " + empID + " was updated successfully";
    }

    public Map<String, Integer> mapEachDegree() throws Exception {

        if (this.staff.isEmpty()){
            throw new Exception("No employees registered yet.");
        }

        Map<String, Integer> mapOfDegrees = new HashMap<>();

        int totalBSc = 0;
        int totalMSc = 0;
        int totalPhD = 0;

        for (Employee currentEmployee : this.staff){
            if (currentEmployee instanceof Manager){
                Manager currentManager = (Manager) currentEmployee;
                if (currentManager.getDegree().equals("BSc")){
                    totalBSc = totalBSc + 1;
                } else if (currentManager.getDegree().equals("MSc")){
                    totalMSc = totalMSc + 1;
                } else {
                    totalPhD = totalPhD + 1;
                }
            }
        }

        if (totalBSc > 0){
            mapOfDegrees.put("BSc", totalBSc);
        }

        if (totalMSc > 0){
            mapOfDegrees.put("MSc", totalMSc);
        }

        if (totalPhD > 0){
            mapOfDegrees.put("PhD", totalPhD);
        }

        return mapOfDegrees;

    }

    public String promoteToManager(String empID, String degree) throws Exception {
        Employee beforePromotionEmployee = this.retrieveEmployee(empID);

        String name = beforePromotionEmployee.getEmployeeName();
        double salary = beforePromotionEmployee.getRawSalary();

        Employee afterPromotionEmployee = new Manager(empID, name, salary, degree);

        this.staff.remove(beforePromotionEmployee);

        this.staff.add(afterPromotionEmployee);

        return empID + " promoted successfully to Manager.";
    }

    public String promoteToDirector(String empID, String degree, String department) throws Exception {
        Employee beforePromotionEmployee = this.retrieveEmployee(empID);

        String name = beforePromotionEmployee.getEmployeeName();
        double salary = beforePromotionEmployee.getRawSalary();

        Employee afterPromotionEmployee = new Director(empID, name, salary, degree, department);


        this.staff.remove(beforePromotionEmployee);

        this.staff.add(afterPromotionEmployee);

        return empID + " promoted successfully to Director.";
    }

    public String promoteToIntern(String empID, int gpa) throws Exception {
        Employee beforePromotionEmployee = this.retrieveEmployee(empID);

        String name = beforePromotionEmployee.getEmployeeName();
        double salary = beforePromotionEmployee.getRawSalary();

        Employee afterPromotionEmployee = new Intern(empID, name, salary, gpa);


        this.staff.remove(beforePromotionEmployee);

        this.staff.add(afterPromotionEmployee);

        return empID + " promoted successfully to Intern.";
    }


    private String toStringAllItems() {
        String message = "";
        for (Item item : this.listOfItems) {
            message += item.toString() + System.lineSeparator();

        }
        return message;
    }

    private Item retrieveItem(String itemID){
        Item item = null;

        for (Item listOfItem : this.listOfItems) {
            if (listOfItem.getItemID().equals(itemID)) {
                item = listOfItem;
            }
        }
        return item;
    }

    private Review retrieveReview(String itemID, int reviewNumber){
        Item tempItem = this.retrieveItem(itemID);
        Review tempReview = tempItem.retrieveItemReview(reviewNumber);
        return tempReview;
    }

    private boolean itemHasTransactions(String itemID){
        boolean answer = false;
        for (Transaction currentTransaction : this.transactionHistory){
            if (itemID.equals(currentTransaction.getItemID())){
                answer = true;
                break;
            }
        }
        return answer;
    }

    private boolean reviewsWereMade(){
        boolean answer = false;
        for (Item currentItem : this.listOfItems){
            if (currentItem.checkIfItemHasReview()){
                answer = true;
            }
        }
        return answer;
    }

    private Employee retrieveEmployee(String employeeID) throws Exception {
        if (this.staff.isEmpty()){
            throw new Exception("Employee " + employeeID + " was not registered yet.");
        }

        Employee currentEmployee = null;
        for (Employee employee : this.staff) {
            if (employee.getEmployeeID().equals(employeeID)) {
                currentEmployee = employee;
                break;
            }
        }

        if (currentEmployee == null){
            throw new Exception("Employee " + employeeID + " was not registered yet.");
        }

        return currentEmployee;
    }

    private Intern retrieveIntern(String empID) throws Exception{
        Employee tempEmployee = this.retrieveEmployee(empID);
        if (tempEmployee instanceof Intern) {
            return (Intern) tempEmployee;
        } else {
            throw new Exception("Invalid employee ID.");
        }
    }

    private Manager retrieveManager(String empID) throws Exception{
        Employee tempEmployee = this.retrieveEmployee(empID);
        if (tempEmployee instanceof Manager) {
            return (Manager) tempEmployee;
        } else {
            throw new Exception("Invalid employee ID.");
        }
    }

    private Director retrieveDirector(String empID) throws Exception{
        Employee tempEmployee = this.retrieveEmployee(empID);
        if (tempEmployee instanceof Director) {
            return (Director) tempEmployee;
        } else {
            throw new Exception("Invalid employee ID.");
        }
    }


}