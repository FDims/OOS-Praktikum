package Bank;

public class Transfer {
    private String date;                            //Attribute für die Datum
    private double amount;                          //Attribute für den Betrag der Überweisung
    private String description;                     //Attribute für die Beschreibung der Überweisung
    private String sender;                          //Attribute für die Name von Sender
    private String recipient;                       //Attribüte für die Name von Empfänger
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
        if(amount1 < 0) {                                                                               //Überprüfung des Anzahl der Überweisung
            System.out.println("Transfer error, the amount of transfer cannot be negative!");   //Error Message
            status = false;
        } else {
            this.amount = amount1;
            status = true;
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String desc) {
        this.description = desc;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }


    /*
    *
    * Konstruktor
    * die Variable mit dem Datentyp der Klasse zu konstruieren
    * in dem die Parametern als Attributes genommen werden
     *
    */
    public Transfer (String date1 , double amount1, String desc){
        this.date = date1;
        setAmount(amount1);
        this.description=desc;
    }
    public Transfer (String date2, double amount2, String desc1, String send, String rec){
        this(date2,amount2,desc1);
        this.sender = send;
        this.recipient=rec;
    }

    /*
    *
    * Copy-Konstruktor
    * Konstruktor, in dem Attributes von anderen Variable mit gleicen Typ
    * von einer neuen Variable übernommen werden.
    *
    */
    public Transfer(Transfer other){
        this(other.date, other.amount, other.description, other.sender, other.recipient);
    }

    public void printObject(){
        if(status)
            System.out.println(
                "Date of transfer: " +date+
                        "\n Amount of transfer: " +amount+
                        "\n Description: " +description+
                        "\n Sender: " +sender+
                        "\n Recipient: "+recipient+"\n"
                );
        else
            System.out.println("Transaction is failed !"+"\n");
    }

}
