import bank.*;
import bank.exceptions.*;

import java.io.IOException;
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
        System.out.println(payment1copy.toString());

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
        }*/

        PrivateBank deutscheBank = new PrivateBank("Deutsche Bank",0.3 , 0.25, "Deutsche Bank");
        try {
            deutscheBank.createAccount("Molziles", List.of(
                    new Payment("12.03.2008", 321, "Payment"),
                    new Payment("23.09.1897",  -2500,"Payment", 0.8, 0.5),
                    new OutgoingTransfer("03.03.2000",  80,"OutgoingTransfer to Elixir", "Molziles", "Elixir")
            ));
        } catch (AccountAlreadyExistsException e) {
            System.out.println(e);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            deutscheBank.createAccount("Elixir", List.of(
                    new Payment("22.06.1998",  435,"Payment", 0., 0.),
                    new IncomingTransfer("03.03.2000",  80,"IncomingTransfer from Molziles to Elixir", "Molziles", "Elixir"),
                    new Payment("05.08.2022",  -118,"Payment", 0., 0.),
                    new OutgoingTransfer("15.04.1990",  185,"OutgoingTransfer to Hagen", "Elixir", "Hagen"),
                    new OutgoingTransfer("30.07.2020",  1890,"OutgoingTransfer to Hagen", "Elixir", "Hagen")
            ));
        } catch (AccountAlreadyExistsException | IOException e) {
            System.out.println(e);
        }

        try {
            deutscheBank.createAccount("Hagen");
        } catch (AccountAlreadyExistsException | IOException e) {
            System.out.println(e);
        }

        try {
            deutscheBank.removeTransaction("Hagen", new Payment("19.01.2011",  -789,"Payment", 0.9, 0.25));
        } catch (TransactionDoesNotExistException|AccountDoesNotExistException e) {
            System.out.println(e);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            deutscheBank.addTransaction("Molziles", new Payment("19.01.2011",  -789,"Payment", 0.9, 0.25));
        } catch (TransactionAlreadyExistException | AccountDoesNotExistException | IOException e) {
            System.out.println(e);
        }

        try {
            deutscheBank.addTransaction("Molziles", new Payment("19.01.2011",  -789,"Payment", 0.9, 0.25));
        } catch (TransactionAlreadyExistException|AccountDoesNotExistException e) {
            System.out.println(e);

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("\n" + deutscheBank);

        PrivateBank sparkasse = new PrivateBank("Sparkasse", 0.05, 0.11, "Sparkasse");
        try {
            sparkasse.addTransaction("Hagen", new Payment("19.01.2011",  -789,"Payment", 0.9, 0.25));
        } catch (AccountDoesNotExistException e) {
            System.out.println(e);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            sparkasse.createAccount("Rehman");
        } catch (AccountAlreadyExistsException | IOException e) {
            System.out.println(e);
        }

        System.out.println("\n" + sparkasse);


        PrivateBank aachenerBank = new PrivateBank("Aachener Bank", 0.26 ,0.11,"Aachen" );
        System.out.println(aachenerBank);

    }

}
