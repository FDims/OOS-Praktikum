package Bank;


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
    public void setIncomingInterest(double incomingInterest) {
        if(incomingInterest < 0 || incomingInterest > 1) {                                                          //Überprüfung die Zahl von Zinsen der Einzahlung
            System.out.println("Payment error, value of incoming interest must be in percent between 0 and 1!");    //Error Message
            status = false;
        }else {
            this.incomingInterest = incomingInterest;
            status=true;
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
    public void setOutgoingInterest(double outgoingInterest) {
        if(outgoingInterest < 0 || outgoingInterest > 1) {                                                       //Überprüfung die Zahl von Zinsen der Auszahlung
            System.out.println("Payment error, value of outgoing interest must be in percent between 0 and 1!"); //Error Message
            status=false;
        }else {
            this.outgoingInterest = outgoingInterest;
            status=true;
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
    public Payment (String date2, double amount2, String desc1, double in, double out){
        this(date2,amount2,desc1);
        setIncomingInterest(in);
        setOutgoingInterest(out);
    }

    /**
     *
     * @param other
     */
    public Payment (Payment other){
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
     *
     * @return
     */
    @Override
    public String toString(){
        if(!status)
            return super.toString();
        else
        return super.toString()+
                "\n Incoming interest: " +incomingInterest+
                "\n Outgoing interest: "+outgoingInterest+"\n";
    }

    /**
     *
     * @param obj
     * @return
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
