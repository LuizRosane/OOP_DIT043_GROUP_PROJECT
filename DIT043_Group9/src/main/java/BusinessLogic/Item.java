package BusinessLogic;

import Util.Truncate;

import java.util.ArrayList;
import java.util.Objects;

public class Item {

    String EOF = System.lineSeparator();

    private ArrayList<Review> listOfReviews; // Robert
    private String itemName;
    final private String itemID;
    private double pricePerUnit;


    public Item(String itemID, String itemName, double pricePerUnit){
        this.itemID = itemID;
        this.itemName = itemName;
        this.listOfReviews = new ArrayList<>();   // Robert

        //making sure that the number won't be rounded
        pricePerUnit = Truncate.truncateValue(pricePerUnit, 2);

        this.pricePerUnit = pricePerUnit;
        }

    public String getItemID() {
        return this.itemID;
    }

    public String getItemName(){
        return this.itemName;
    }

    public double getPricePerUnit() {
        return this.pricePerUnit;
    }

    public void setItemName(String itemName){
        if (!itemName.isBlank()){
            this.itemName = itemName;
        }
    }

    public void setPricePerUnit(double pricePerUnit) {
        if (pricePerUnit > 0){
            pricePerUnit = Truncate.truncateValue(pricePerUnit, 2);
            this.pricePerUnit = pricePerUnit;
        }
    }

    @Override
    public String toString(){
        return this.itemID + ": " + this.itemName + ". " + String.format("%.2f", this.pricePerUnit) + " SEK";
    }

    @Override
    public boolean equals(Object anotherObject){
        if (anotherObject == null){
            return false;
        } else if (this == anotherObject){
            return true;
        }
        else if(anotherObject instanceof Item) {
            Item anotherItem = (Item) anotherObject;
            return (this.itemID.equals(anotherItem.getItemID()) &&
                    this.itemName.equals(anotherItem.getItemName()) &&
                    this.pricePerUnit == anotherItem.getPricePerUnit());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.itemID, this.itemName, this.pricePerUnit);
    }

    public ArrayList<Review> getListOfReviews() {
        return this.listOfReviews;
    }

    public void addReviewToItem (Review newReview) {
        listOfReviews.add(newReview);
    }

    public ArrayList<String> retrieveListOfComments (){
        ArrayList<String> listOfComments = new ArrayList<>();
        for (Review currentReview : this.listOfReviews) {
            if (currentReview.getReviewComment() != null && !(currentReview.getReviewComment().isBlank())){
                listOfComments.add(currentReview.getReviewComment());
            }
        }
        return listOfComments;
    }

    public boolean checkIfItemHasReview() {
        return !listOfReviews.isEmpty();
    }



   public double getMeanGrade(){
        double sum = 0;
        for (int i = 0; i < listOfReviews.size(); i++){
            sum = sum + this.retrieveItemReview(i).getReviewGrade();
        }
        double mean = sum / listOfReviews.size();
        mean = Truncate.truncateValue(mean, 1);

        return mean;

   }


    public Review retrieveItemReview(int reviewNumber){
        return this.listOfReviews.get(reviewNumber);

    }

    public String printReviewsForItem() {
        String message = "";
        for (Review review : this.listOfReviews) {
            message  += review.toString() + EOF;

        } return message;
    }

    public int getSizeOfReviewList() {

        return listOfReviews.size();
    }

    public String printAllReviews(){
        String message = "Review(s) for " + this.toString() + EOF;

        for (Review currentReview : this.listOfReviews){
            message += currentReview.toString() + EOF;
        }

        return message;
    }


}

