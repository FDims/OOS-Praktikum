package Bank;

public class Payment {

    private String date;                    //Attribute für die Datum
    private double amount;                  //Attribute für den Betrag von Ein-/Auszahlung (kann negative sein)
    private String description;             //Attribute für Beschreibung
    private double incomingInterest;        //Attribute für Zinsen der Einzahluing
    private double outgoingInterest;        //Attribute für Zinsen der Auszahlung
    private boolean status = true;                         //Attribute für den Status der Überweisung



    /*
     *
     * Getter und Setter for Attributes
     * um jede Attributes von außen zuzugriffen bzw, um zu setzen oder zu kriegen
     * set = zu setzen, get = zu kriegen
     *
     **/
    public String getDate(){
        return this.date;
    }

    public void setDate(String date1) {
        this.date = date1;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount1) {
        this.amount = amount1;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String desc) {
        this.description = desc;
    }

    public double getIncomingInterest() {
        return incomingInterest;
    }

    public void setIncomingInterest(double incomingInterest) {
        if(incomingInterest < 0 || incomingInterest > 1) {                                                          //Überprüfung die Zahl von Zinsen der Einzahlung
            System.out.println("Payment error, value of incoming interest must be in percent between 0 and 1!");    //Error Message
            status = false;
        }else {
            this.incomingInterest = incomingInterest;
            status=true;
        }
    }

    public double getOutgoingInterest() {
        return outgoingInterest;
    }

    public void setOutgoingInterest(double outgoingInterest) {
        if(outgoingInterest < 0 || outgoingInterest > 1) {                                                       //Überprüfung die Zahl von Zinsen der Auszahlung
            System.out.println("Payment error, value of outgoing interest must be in percent between 0 and 1!"); //Error Message
            status=false;
        }else {
            this.outgoingInterest = outgoingInterest;
            status=true;
        }
    }



    /*
     *
     * Konstruktor
     * die Variable mit dem Datentyp der Klasse zu konstruieren
     * in dem die Parametern als Attributes genommen werden
     *
     */
    public Payment (String date1 , double amount1, String desc){
        this.date = date1;
        this.amount=amount1;
        this.description=desc;
    }
    public Payment (String date2, double amount2, String desc1, double in, double out){
        this(date2,amount2,desc1);
        setIncomingInterest(in);
        setOutgoingInterest(out);
    }

    /*
     *
     * Copy-Konstruktor
     * Konstruktor, in dem Attributes von anderen Variable mit gleicen Typ
     * von einer neuen Variable übernommen werden.
     *
     */
    public Payment (Payment other){
        this(other.date,other.amount, other.description, other.incomingInterest, other.outgoingInterest);
    }

    public void printObject(){
        if(status)
            System.out.println(
                "Date of payment: " +date+
                        "\n Amount of payment: " +amount+
                        "\n Description: " +description+
                        "\n Incoming interest: " +incomingInterest+
                        "\n Outgoing interest: "+outgoingInterest+"\n"
            );
        else
            System.out.println("Transaction is failed !"+"\n");
    }

}
