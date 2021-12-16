package BusinessLogic;

import Util.Truncate;

import java.util.Objects;

public class Transaction {
    //more than 4 items the following will get 3% discount
    final double DISCOUNT = 0.3;

    final private String itemID;
    final private int amount;
    final private double purchasePrice;

    public Transaction(String itemID, double unitPrice, int amount){
        this.itemID = itemID;
        this.amount = amount;

        double purchasePrice = this.calculatePurchasePrice(unitPrice, amount);
        purchasePrice = Truncate.truncateValue(purchasePrice, 2);

        this.purchasePrice = purchasePrice;
    }

    public String getItemID(){
        return this.itemID;
    }

    public int getAmount(){
        return this.amount;
    }

    public double getPurchasePrice(){
        return this.purchasePrice;
    }

    @Override
    public String toString(){
        return this.getItemID() + ": " + this.getAmount() + " item(s). " + String.format("%.2f", this.purchasePrice) + " SEK";
    }

    @Override
    public boolean equals(Object anotherObject){
        if (anotherObject == null){
            return false;
        }
        if (this == anotherObject){
            return true;
        }
        if (anotherObject instanceof Transaction){
            Transaction anotherTransaction = (Transaction) anotherObject;
            return (this.itemID.equals(anotherTransaction.getItemID()) &&
                    this.amount == anotherTransaction.getAmount() &&
                    this.purchasePrice == anotherTransaction.getPurchasePrice());
        } else {
            return false;
        }

    }

    @Override
    public int hashCode(){
        int hash = Objects.hash(this.itemID, this.amount, this.purchasePrice);
        return hash;
    }

    private double calculatePurchasePrice(double unitPrice, int amount){
        if (amount <= 4) {
            return unitPrice * amount;
        } else {
            return ((unitPrice * 4) + ( ( unitPrice * (amount - 4) ) * (1 - DISCOUNT) ) );
        }
    }

}
