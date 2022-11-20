import bank.*;
import bank.exceptions.*;

import java.util.List;


public class main {

    public static void main(String[] args) throws TransactionAlreadyExistException, AccountAlreadyExistsException, TransactionAttributeException {
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

        PrivateBank privateBank=new PrivateBank("bank1", 0.15,0.1);
        PrivateBankAlt privateBankAlt = new PrivateBankAlt("altBank1",0.12,0.14);

        privateBank.createAccount("Antonio", List.of(
                new Transfer("02.08.2022",520,"this is Transfer","Antonio","Jessica"),
                new Payment("01.02.2021",1200,"this is deposit",0.15,0.1),
                new Payment("01.02.2021",-1100,"this is deposit",0.12,0.3),
                new Transfer("02.08.2022",450,"this is Transfer","Alexa","Antonio")
        ));

        privateBankAlt.createAccount("Antonio", List.of(
                new Transfer("02.08.2022",520,"this is Transfer","Antonio","Jessica"),
                new Payment("01.02.2021",1200,"this is deposit",0.15,0.1),
                new Payment("01.02.2021",-1100,"this is deposit",0.12,0.3),
                new Transfer("02.08.2022",950,"this is Transfer","Alexa","Antonio")
        ));

        privateBank.createAccount("Alexa", List.of(
                new Transfer("02.08.2022",560,"this is Transfer","Alexa","Jessica"),
                new Payment("01.02.2021",1255,"this is deposit",0.15,0.1),
                new Payment("01.02.2021",-1350,"this is deposit",0.12,0.3),
                new Transfer("02.08.2022",520,"this is Transfer","Antonio","Alexa")
        ));
        privateBankAlt.createAccount("Alexa", List.of(
                new Transfer("02.08.2022",560,"this is Transfer","Alexa","Jessica"),
                new Payment("01.02.2021",1255,"this is deposit",0.15,0.1),
                new Payment("01.02.2021",-1350,"this is deposit",0.12,0.3),
                new Transfer("02.08.2022",520,"this is Transfer","Antonio","Alexa")
        ));

        System.out.println(privateBank);
        System.out.println(privateBankAlt);

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
        }catch (AccountAlreadyExistsException | TransactionAlreadyExistException | TransactionAttributeException fail){
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
        }catch (TransactionAlreadyExistException | AccountDoesNotExistException | TransactionAttributeException fail){
            System.out.println(fail);
        }

        try{
            privateBankAlt.removeTransaction("Michael",new Payment("01.02.2021",1350,"this is deposit",1.5,0.3));
        } catch (AccountDoesNotExistException|TransactionDoesNotExistException fail) {
            System.out.println(fail);
        }
        try{
            privateBankAlt.removeTransaction("Alexa",new Payment("01.01.2021",-1350,"this is deposit",0.12,0.3));
        } catch (AccountDoesNotExistException|TransactionDoesNotExistException fail) {
            System.out.println(fail);
        }

        System.out.println(privateBankAlt);
        try{
            privateBankAlt.removeTransaction("Alexa",new Payment("01.02.2021",-1350,"this is deposit",0.12,0.3));
        } catch (AccountDoesNotExistException|TransactionDoesNotExistException fail) {
            System.out.println(fail);
        }
        System.out.println(privateBankAlt);

        System.out.println(privateBankAlt.getTransactionsSorted("Alexa",true));
        System.out.println(privateBankAlt.getTransactionsSorted("Alexa",false));
        System.out.println(privateBankAlt.getTransactionsByType("Alexa",true));
        System.out.println(privateBankAlt.getTransactionsByType("Alexa",false));

        System.out.println(privateBankAlt.containsTransaction("Alexa",new Payment("01.02.2021",-1350,"this is deposit",0.12,0.3)));
        System.out.println(privateBankAlt.containsTransaction("Alexa",new Payment("03.05.2021",-1550,"this is deposit",0.12,0.3)));
        System.out.println(privateBankAlt.containsTransaction("Sarah",new Payment("01.02.2021",-1350,"this is deposit",0.12,0.3)));

        PrivateBank privateBank2=new PrivateBank("bank1", 0.15,0.1);
        privateBank2.createAccount("Antonio", List.of(
                new Transfer("02.08.2022",520,"this is Transfer","Antonio","Jessica"),
                new Payment("01.02.2021",1200,"this is deposit",0.15,0.1),
                new Payment("01.02.2021",-1100,"this is deposit",0.12,0.3),
                new Transfer("02.08.2022",450,"this is Transfer","Alexa","Antonio")
        ));
        privateBank2.createAccount("Alexa", List.of(
                new Transfer("02.08.2022",560,"this is Transfer","Alexa","Jessica"),
                new Payment("01.02.2021",1255,"this is deposit",0.15,0.1),
                new Payment("01.02.2021",-1350,"this is deposit",0.12,0.3),
                new Transfer("02.08.2022",520,"this is Transfer","Antonio","Alexa")
        ));

        PrivateBank privateBank3=new PrivateBank("bank1", 0.15,0.1);
        privateBank3.createAccount("Antonio", List.of(
                new Transfer("02.08.2022",520,"this is Transfer","Antonio","Jessica"),
                new Payment("01.02.2021",1200,"this is deposit",0.15,0.1)

        ));
        privateBank3.createAccount("Alexa", List.of(
                new Transfer("02.08.2022",560,"this is Transfer","Alexa","Jessica"),
                new Transfer("02.08.2022",520,"this is Transfer","Antonio","Alexa")
        ));

        System.out.println(privateBank.equals(privateBank2));
        System.out.println(privateBank.equals(privateBank3));

    }

}
