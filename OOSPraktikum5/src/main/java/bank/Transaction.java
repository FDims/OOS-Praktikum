package bank;

import bank.exceptions.AmountException;

import java.util.Objects;

public abstract class Transaction implements CalculateBill{
    protected String date;
    protected double amount;
    protected String description;

    /**
     *
     * @param amount
     */
    public void setAmount(double amount) throws AmountException {
        if(this instanceof Transfer transfer){
            if(amount<0) {
                throw new AmountException("Amount of Payment can not be negative!");
            }else
                this.amount=amount;
        }else
            this.amount = amount;
    }

    /**
     *
     * @return
     */
    public double getAmount() {
        return amount;
    }

    /**
     *
     * @param date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     *
     * @return
     */
    public String getDate() {
        return date;
    }

    /**
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param datum
     * @param Betrag
     * @param desc
     */
    Transaction(String datum,double Betrag, String desc){
        this.date=datum;
        this.amount=Betrag;
        this.description=desc;
    }

    /**
     * function to write attributes of the class
     * @return a String of attributes
     */
    public String toString(){
        return " Date of Transaction: " +date+
                "\n Amount of Transaction: " + calculate() +
                "\n Description: " +description;
    }

    /**
     * function to compare current Variable and an Object
     * @param obj other objekt to compare with
     * @return whether the result of compare true or false
     */
    @Override
    public boolean equals(Object obj){
        if(this==obj)
            return true;
        if(obj instanceof Transaction transaction){
            if(Objects.equals(this.date, transaction.date) && this.amount==transaction.amount
                    && Objects.equals(this.description, transaction.description))
                return true;
            else
                return false;
        }else
            return false;
    }
}
