package bank;
import bank.exceptions.AmountException;

import java.util.Objects;

public class Transfer extends Transaction implements CalculateBill{
    private String sender;                          //Attribute für die Name von Sender
    private String recipient;                       //Attribüte für die Name von Empfänger




    /*
     *
     * Getter und Setter for Attributes
     * um jede Attributes von außen zuzugriffen bzw, um zu setzen oder zu kriegen
     * set = zu setzen, get = zu kriegen
     *
     **/

    /**
     *
     * @return
     */

    public String getSender() {
        return sender;
    }

    /**
     *
     * @param sender
     */
    public void setSender(String sender) {
        this.sender = sender;
    }

    /**
     *
     * @return
     */
    public String getRecipient() {
        return recipient;
    }

    /**
     *
     * @param recipient
     */

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
    @Override
    public void setAmount(double amount) throws AmountException {
        if(amount<0) {
             throw new AmountException("Amount of Payment can not be negative!");
        }else
            this.amount=amount;
    }


    /**
     *
     * @param date1
     * @param amount1
     * @param desc
     */
    public Transfer (String date1 , double amount1, String desc){
        super(date1,amount1,desc);
    }

    /**
     *
     * @param date2
     * @param amount2
     * @param desc1
     * @param send
     * @param rec
     */
    public Transfer (String date2, double amount2, String desc1, String send, String rec){
        this(date2,amount2,desc1);
        this.sender=send;
        this.recipient=rec;
    }

    /**
     *
     * @param other
     */
    public Transfer(Transfer other){
        this(other.date, other.amount, other.description, other.sender, other.recipient);
    }


    /**
     *
     * @return
     */
    @Override
    public double calculate(){
        return getAmount();
    }

    /**
     * function to write attributes of the class
     * @return a String of attributes
     */
    @Override
    public String toString(){
        return super.toString()+
                "\n Sender: " +sender+
                "\n Recipient: "+recipient+"\n";
    }

    /**
     * function to compare current Variable and an Object
     * @param obj other objekt to compare with
     * @return whether the result of compare true or false
     */
    @Override
    public boolean equals (Object obj){
        if(this==obj)
            return true;
        if(obj instanceof Transfer transfer){
            if(super.equals(transfer) && Objects.equals(this.sender, transfer.sender) && Objects.equals(this.recipient, transfer.recipient))
                return true;
            else
                return false;
        }else
            return false;
    }
}