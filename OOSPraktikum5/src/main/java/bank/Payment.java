package bank;
import bank.exceptions.IncomingException;
import bank.exceptions.OutgoingException;


public class Payment extends Transaction implements CalculateBill{

    private double incomingInterest;        //Attribute für Zinsen der Einzahluing
    private double outgoingInterest;        //Attribute für Zinsen der Auszahlung



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
    public double getIncomingInterest() {
        return incomingInterest;
    }

    /**
     *
     * @param incomingInterest
     */
    public void setIncomingInterest(double incomingInterest) throws IncomingException{
        if(incomingInterest < 0 || incomingInterest > 1) {                                                          //Überprüfung die Zahl von Zinsen der Einzahlung
            throw new IncomingException("Value of Incoming Interest must be between 0 and 1");    //Error Message
        }else {
            this.incomingInterest = incomingInterest;
        }
    }

    /**
     *
     * @return
     */
    public double getOutgoingInterest() {
        return outgoingInterest;
    }

    /**
     *
     * @param outgoingInterest
     */
    public void setOutgoingInterest(double outgoingInterest) throws OutgoingException{
        if(outgoingInterest < 0 || outgoingInterest > 1) {                                                       //Überprüfung die Zahl von Zinsen der Auszahlung
            throw new OutgoingException("Value of Incoming Interest must be between 0 and 1");
        }else {
            this.outgoingInterest = outgoingInterest;
        }
    }


    /**
     *
     * @param date1
     * @param amount1
     * @param desc
     */
    public Payment (String date1 , double amount1, String desc){
        super(date1,amount1,desc);
    }
    public Payment (String date2, double amount2, String desc1, double in, double out) throws IncomingException, OutgoingException {
        this(date2,amount2,desc1);
        setIncomingInterest(in);
        setOutgoingInterest(out);
    }

    /**
     *
     * @param other
     */
    public Payment (Payment other) throws IncomingException, OutgoingException {
        this(other.date,other.amount, other.description, other.incomingInterest, other.outgoingInterest);
    }



    /**
     *
     * @return
     */
    @Override
    public double calculate(){
        double amount=getAmount();
        double zinsen=0;
        if(amount>0){
            zinsen=getIncomingInterest();
            return amount-(amount*zinsen);
        }else{
            zinsen=getOutgoingInterest();
            return amount+(amount*zinsen);
        }
    }

    /**
     * function to write attributes of the class
     * @return a String of attributes
     */
    @Override
    public String toString(){
        return super.toString()+
                "\n Incoming interest: " +incomingInterest+
                "\n Outgoing interest: "+outgoingInterest+"\n";
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
        if(obj instanceof Payment payment){
            if(super.equals(payment) && this.incomingInterest==payment.incomingInterest && this.outgoingInterest== payment.outgoingInterest)
                return true;
            else
                return false;
        }else
            return false;
    }

}
