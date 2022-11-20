import bank.*;
import bank.exceptions.*;

import java.util.List;


public class main {

    public static void main(String[] args) throws TransactionAlreadyExistException, AccountAlreadyExistsException, TransactionAttributeException, IncomingException, OutgoingException {
        /*Transfer transfer1 = new Transfer("02.08.2022",520,"this is Transfer","Sender is me","recipient also me");
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
        System.out.println(payment1copy.toString());*/

        System.out.println("Bank Konto erstellen:\n");
        PrivateBankAlt privateBankAlt = new PrivateBankAlt("altBank1",0.15,0.1);



        privateBankAlt.createAccount("Antonio", List.of(
                new Transfer("02.08.2022",520,"this is Transfer","Antonio","Jessica"),
                new Payment("01.02.2021",1200,"this is deposit",0.15,0.1),
                new Payment("01.02.2021",-1100,"this is deposit",0.12,0.3),
                new Transfer("02.08.2022",950,"this is Transfer","Alexa","Antonio")
        ));

        privateBankAlt.createAccount("Alexa", List.of(
                new Transfer("02.08.2022",560,"this is Transfer","Alexa","Jessica"),
                new Payment("01.02.2021",1255,"this is deposit",0.15,0.1),
                new Payment("01.02.2021",-1350,"this is deposit",0.12,0.3),
                new Transfer("02.08.2022",520,"this is Transfer","Antonio","Alexa")
        ));


        System.out.println("\n\nBalance Funktionen testen:\n");
        System.out.println(privateBankAlt.getTransactions("Alexa"));
        System.out.println(privateBankAlt.getAccountBalance("Alexa"));

        System.out.println("\n\nsorted Funktionen testen:\n");

        System.out.println(privateBankAlt.getTransactionsSorted("Alexa",true));
        System.out.println(privateBankAlt.getTransactionsSorted("Alexa",false));
        System.out.println(privateBankAlt.getTransactionsByType("Alexa",true));
        System.out.println(privateBankAlt.getTransactionsByType("Alexa",false));

        System.out.println("\n\ncontains Funktionen testen:\n");
        System.out.println(privateBankAlt.containsTransaction("Alexa",new Payment("01.02.2021",-1350,"this is deposit",0.12,0.3)));
        System.out.println(privateBankAlt.containsTransaction("Alexa",new Payment("03.05.2021",-1550,"this is deposit",0.12,0.3)));
        System.out.println(privateBankAlt.containsTransaction("Sarah",new Payment("01.02.2021",-1350,"this is deposit",0.12,0.3)));


        System.out.println("\n\nEquals Funktion testen:\n");
        PrivateBankAlt privateBankAlt2=new PrivateBankAlt("bank1", 0.15,0.1);
        privateBankAlt2.createAccount("Antonio", List.of(
                new Transfer("02.08.2022",520,"this is Transfer","Antonio","Jessica"),
                new Payment("01.02.2021",1200,"this is deposit",0.15,0.1),
                new Payment("01.02.2021",-1100,"this is deposit",0.12,0.3),
                new Transfer("02.08.2022",450,"this is Transfer","Alexa","Antonio")
        ));
        privateBankAlt2.createAccount("Alexa", List.of(
                new Transfer("02.08.2022",560,"this is Transfer","Alexa","Jessica"),
                new Payment("01.02.2021",1255,"this is deposit",0.15,0.1),
                new Payment("01.02.2021",-1350,"this is deposit",0.12,0.3),
                new Transfer("02.08.2022",520,"this is Transfer","Antonio","Alexa")
        ));

        PrivateBankAlt privateBankAlt3=new PrivateBankAlt("bank1", 0.15,0.1);
        privateBankAlt3.createAccount("Antonio", List.of(
                new Transfer("02.08.2022",520,"this is Transfer","Antonio","Jessica"),
                new Payment("01.02.2021",1200,"this is deposit",0.15,0.1)

        ));
        privateBankAlt3.createAccount("Alexa", List.of(
                new Transfer("02.08.2022",560,"this is Transfer","Alexa","Jessica"),
                new Transfer("02.08.2022",520,"this is Transfer","Antonio","Alexa")
        ));

        System.out.println(privateBankAlt.equals(privateBankAlt2));
        System.out.println(privateBankAlt.equals(privateBankAlt3));

        Payment pay1 = new Payment("01.02.2021",1200,"this is deposit",0.15,0.1);
        Payment pay2 = new Payment("01.02.2021",1200,"this is deposit",0.15,0.1);
        System.out.println(pay1.equals(pay2));


        System.out.println("\n\nException testen:\n");
        try{
            privateBankAlt.createAccount("Alexa");
        }catch (AccountAlreadyExistsException fail){
            System.out.println(fail);
        }

        try{
            privateBankAlt.createAccount("Alexa",List.of(new Transfer("02.08.2022",520,"this is Transfer","Antonio","Alexa")));
        }catch (AccountAlreadyExistsException | TransactionAlreadyExistException | TransactionAttributeException fail){
            System.out.println(fail);
        }

        try{
            privateBankAlt.createAccount("Alexa",List.of(new Transfer("02.08.2022",520,"this is Transfer","Antonio","Alexa")));
        }catch (AccountAlreadyExistsException | TransactionAlreadyExistException | TransactionAttributeException fail){
            System.out.println(fail);
        }

        try{
            privateBankAlt.createAccount("Anderson",List.of(new Payment("01.02.2021",-1350,"this is deposit",1.5,0.3)));
        }catch (AccountAlreadyExistsException | TransactionAlreadyExistException | TransactionAttributeException | IncomingException | OutgoingException fail){
            System.out.println(fail);
        }
        try{
            privateBankAlt.createAccount("Anderson",List.of(new Payment("01.02.2021",-1350,"this is deposit",0.5,0.3),new Payment("01.02.2021",-1350,"this is deposit",0.5,0.3)));
        }catch (AccountAlreadyExistsException | TransactionAlreadyExistException | TransactionAttributeException | IncomingException | OutgoingException fail){
            System.out.println(fail);
        }

        try{
            privateBankAlt.addTransaction("Michael", new Transfer("02.08.2022",520,"this is Transfer","Antonio","Alexa"));
        }catch (TransactionAlreadyExistException | AccountDoesNotExistException | TransactionAttributeException fail){
            System.out.println(fail);
        }
        try{
            privateBankAlt.addTransaction("Alexa", new Transfer("02.08.2022",520,"this is Transfer","Antonio","Alexa"));
        }catch (TransactionAlreadyExistException | AccountDoesNotExistException | TransactionAttributeException fail){
            System.out.println(fail);
        }

        try{
            privateBankAlt.addTransaction("Alexa", new Payment("01.02.2021",1350,"this is deposit",1.5,0.3));
        }catch (TransactionAlreadyExistException | AccountDoesNotExistException | TransactionAttributeException | IncomingException | OutgoingException fail){
            System.out.println(fail);
        }

        try{
            privateBankAlt.removeTransaction("Michael",new Payment("01.02.2021",1350,"this is deposit",1.5,0.3));
        } catch (AccountDoesNotExistException|TransactionDoesNotExistException | IncomingException | OutgoingException fail) {
            System.out.println(fail);
        }
        try{
            privateBankAlt.removeTransaction("Alexa",new Payment("01.01.2021",-1350,"this is deposit",0.12,0.3));
        } catch (AccountDoesNotExistException|TransactionDoesNotExistException fail) {
            System.out.println(fail);
        }

        try{
            privateBankAlt.removeTransaction("michael",new Payment("01.02.2021",-1350,"this is deposit",0.12,0.3));
        } catch (AccountDoesNotExistException|TransactionDoesNotExistException fail) {
            System.out.println(fail);
        }



    }

}
