import Bank.Transaction;
import Bank.Payment;
import Bank.Transfer;

public class main {

    public static void main(String[] args){
        Transfer transfer1 = new Transfer("02.08.2022",520,"this is Transfer","Sender is me","recipient also me");
        Transfer transfer1copy = new Transfer(transfer1);

        System.out.println(transfer1.equals(transfer1copy));

        transfer1copy.setAmount(200);

        System.out.println(transfer1.toString());
        System.out.println(transfer1copy.toString());

        transfer1.setAmount(-200);
        System.out.println(transfer1.toString());


        Payment payment1 = new Payment("01.02.2021",1200,"this is deposit",0.15,0.1);
        Payment payment1copy = new Payment(payment1);
        System.out.println(payment1.equals(payment1copy));

        payment1.setAmount(1500);
        System.out.println(payment1.toString());
        System.out.println(payment1copy.toString());
    }

}
