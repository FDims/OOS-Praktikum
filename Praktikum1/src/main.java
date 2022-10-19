import Bank.Transfer;
import Bank.Payment;


public class main {
    public static void main(String[] args){

        //Transfer Beispiele
        Transfer T1=new Transfer("25.08.2022",-2500,"waere falsch");
        T1.printObject();

        Transfer T2 = new Transfer("25.08.2022",2500,"Beschreibung");
        T2.printObject();

        Transfer T3=new Transfer("12.07.2022",200,"Bonus","Beispiel Sender", "Beispiel Empfänger");
        T2.printObject();

        Transfer T4 = new Transfer(T3);
        T3.printObject();



        //Payment Beispiele
        Payment P1 = new Payment("23.09.2022",1000,"Miete");
        P1.printObject();

        Payment P2 = new Payment("23.01.2022",250.5,"Beschreibung",0.2,2);
        P2.printObject();

        Payment P3 = new Payment("23.01.2022",250.5,"Einzahlung",0.2,0.5);
        P3.printObject();

        Payment P4 = new Payment(P3);
        P4.printObject();


    }
}
