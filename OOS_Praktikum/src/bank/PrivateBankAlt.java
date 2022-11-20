package bank;

import bank.exceptions.*;
import java.util.*;

public class PrivateBankAlt implements Bank {

    private String name;
    private double incomingInterest;
    private double OutgoingInterest;
    private Map<String, List<Transaction>> accountsToTransactions = new HashMap<>();


    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param incomingInterest
     * @throws IncomingException
     */
    public void setIncomingInterest(double incomingInterest) throws IncomingException {
        if(incomingInterest > 0 && incomingInterest < 1) {
            this.incomingInterest = incomingInterest;
        }else throw new IncomingException("Value of Incoming Interest must be between 0 and 1");
    }

    /**
     *
     * @return
     */
    public double getIncomingInterest() {
        return incomingInterest;
    }

    /**
     *
     * @param outgoingInterest
     * @throws OutgoingException
     */
    public void setOutgoingInterest(double outgoingInterest) throws OutgoingException {
        if(outgoingInterest > 0 && outgoingInterest < 1) {
            OutgoingInterest = outgoingInterest;
        }else throw new OutgoingException("Value of Incoming Interest must be between 0 and 1");
    }

    /**
     *
     * @return
     */
    public double getOutgoingInterest() {
        return OutgoingInterest;
    }

    /**
     *
     * @param name
     * @param incomingInterest
     * @param outgoingInterest
     * @throws IncomingException
     * @throws OutgoingException
     */
    public PrivateBankAlt(String name, double incomingInterest, double outgoingInterest) throws IncomingException, OutgoingException {
        this.name=name;
        setIncomingInterest(incomingInterest);
        setOutgoingInterest(outgoingInterest);
    }

    /**
     *
     * @param other
     * @throws IncomingException
     * @throws OutgoingException
     */
    public PrivateBankAlt(PrivateBankAlt other) throws IncomingException, OutgoingException {
        this(other.name,other.incomingInterest,other.OutgoingInterest);
    }

    /**
     * function to write attributes of the class
     * @return a String of attributes
     */
    @Override
    public String toString(){
        String res = "Name :"+this.name+
                "\nIncoming interest: "+this.incomingInterest+
                "\nOutgoing interest: "+this.OutgoingInterest;
        Set<String> keys = accountsToTransactions.keySet();
        for(String key:keys){
            res+="\n"+key+"=> \n";
            List<Transaction> Lists = accountsToTransactions.get(key);
            res+="[\n";
            for(Transaction transaction:Lists)
                res+=transaction;
            res+="]\n";
        }
        return res;
    }

    /**
     * function to compare current Variable and an Object
     * @param obj other objekt to compare with
     * @return whether the result of compare true or false
     */
    @Override
    public boolean equals(Object obj){
        if(this==obj)
            return true;
        if(obj instanceof PrivateBankAlt privateBankAlt){
            if(this.name==privateBankAlt.name && this.incomingInterest==privateBankAlt.incomingInterest&&this.OutgoingInterest==privateBankAlt.OutgoingInterest){
                return true;
            }else
                return false;
        }else
            return false;
    }

    /**
     *
     * @param account the account to be added
     * @throws AccountAlreadyExistsException
     */
    @Override
    public void createAccount(String account) throws AccountAlreadyExistsException{
        if(accountsToTransactions.containsKey(account))
            throw new AccountAlreadyExistsException("account with the name "+account+" in the bank"+name+" already exists!");
        else {
            accountsToTransactions.put(account, List.of());
            System.out.println("account with the name " + account + " has succesfully created in the bank " + name);
        }
    }

    /**
     *
     * @param account      the account to be added
     * @param transactions a list of already existing transactions which should be added to the newly created account
     * @throws AccountAlreadyExistsException
     * @throws TransactionAlreadyExistException
     * @throws TransactionAttributeException
     * @throws OutgoingException
     * @throws IncomingException
     */
    @Override
    public void createAccount(String account, List<Transaction> transactions)
            throws AccountAlreadyExistsException, TransactionAlreadyExistException, TransactionAttributeException, OutgoingException, IncomingException {
        if (accountsToTransactions.containsKey(account)) {
            throw new AccountAlreadyExistsException("account with the name " + account + " in the bank" + name + " already exists!");
        } else {
            for (Transaction tr : transactions) {
                if (accountsToTransactions.containsKey(account) && accountsToTransactions.get(account).contains(transactions)) {
                    throw new TransactionAlreadyExistException("duplicate transaction can not be added to the account!");
                } else {
                    if (tr instanceof Payment payment) {
                        if (payment.getIncomingInterest()<0||payment.getIncomingInterest()>1||payment.getOutgoingInterest()<0||payment.getOutgoingInterest()>1) {
                            throw new TransactionAttributeException("Transaction attribute fails!");
                        }else{
                            payment.setOutgoingInterest(this.OutgoingInterest);
                            payment.setIncomingInterest(this.incomingInterest);
                        }
                    }

                }
            }
            accountsToTransactions.put(account, transactions);
            System.out.println("account with the name " + account + " and its transaction has succesfully created in the bank " + name);
        }
    }

    /**
     *
     * @param account     the account to which the transaction is added
     * @param transaction the transaction which should be added to the specified account
     * @throws TransactionAlreadyExistException
     * @throws AccountDoesNotExistException
     * @throws TransactionAttributeException
     * @throws OutgoingException
     * @throws IncomingException
     */
    @Override
    public void addTransaction(String account, Transaction transaction)
            throws TransactionAlreadyExistException, AccountDoesNotExistException, TransactionAttributeException, OutgoingException, IncomingException {
        if(!accountsToTransactions.containsKey(account)){
            throw new AccountDoesNotExistException("account with the name "+account+" in the bank"+name+" does not exist!");
        }else {
            if(accountsToTransactions.get(account).contains(transaction)){
                throw new TransactionAlreadyExistException("this transaction is already exist in the account "+account);
            }else{
                if(transaction instanceof Payment payment) {
                    if (payment.getIncomingInterest()<0||payment.getIncomingInterest()>1||payment.getOutgoingInterest()<0||payment.getOutgoingInterest()>1) {
                        throw new TransactionAttributeException("Transaction attribute fails!");
                    }else{
                        payment.setOutgoingInterest(this.OutgoingInterest);
                        payment.setIncomingInterest(this.incomingInterest);
                    }
                }
                List<Transaction> Lists = new ArrayList<>(accountsToTransactions.get(account));
                Lists.add(transaction);
                accountsToTransactions.put(account,Lists);
                System.out.println("Transaction successfully added");

            }
        }
    }

    /**
     *
     * @param account     the account from which the transaction is removed
     * @param transaction the transaction which is removed from the specified account
     * @throws AccountDoesNotExistException
     * @throws TransactionDoesNotExistException
     */
    @Override
    public void removeTransaction(String account, Transaction transaction)
            throws AccountDoesNotExistException, TransactionDoesNotExistException{
        if(!accountsToTransactions.containsKey(account)){
            throw new AccountDoesNotExistException("account with the name "+account+" in the bank"+name+" does not exist!");
        }else {
            if(!accountsToTransactions.get(account).contains(transaction)){
                throw new TransactionDoesNotExistException("this transaction does not exist in the account "+account);
            }else{
                List<Transaction> Lists = new ArrayList<>(accountsToTransactions.get(account));
                Lists.remove(transaction);
                accountsToTransactions.put(account,Lists);
                System.out.println("Transaction successfully removed");
            }
        }
    }

    /**
     *
     * @param account     the account from which the transaction is checked
     * @param transaction the transaction to search/look for
     * @return
     */
    @Override
    public boolean containsTransaction(String account, Transaction transaction){
        if(accountsToTransactions.containsKey(account)){
            if(accountsToTransactions.get(account).contains(transaction))
                return true;
            else
                return false;
        }else
            return false;
    }

    /**
     *
     * @param account the selected account
     * @return
     */
    @Override
    public double getAccountBalance(String account){

        double balance=0;
        List<Transaction> Lists = accountsToTransactions.get(account);
        for(Transaction tr:Lists){
            if(tr instanceof Transfer transfer && transfer.getSender()==account)
                balance-=tr.calculate();
            else
                balance += tr.calculate();
        }
        return balance;
    }

    /**
     *
     * @param account the selected account
     * @return
     */
    @Override
    public List<Transaction> getTransactions(String account){
        List<Transaction> transaction = accountsToTransactions.get(account);
        for(Transaction List : transaction){
            List.toString();
        }
        return transaction;
    }

    /**
     *
     * @param account the selected account
     * @param asc     selects if the transaction list is sorted in ascending or descending order
     * @return
     */
    @Override
    public List<Transaction> getTransactionsSorted(String account, boolean asc){
        List<Transaction> sorted = new ArrayList<>(accountsToTransactions.get(account));
        if(asc)
            sorted.sort(Comparator.comparing(Transaction::calculate));
        else
            sorted.sort(Comparator.comparing(Transaction::calculate).reversed());
        return sorted;
    }

    /**
     *
     * @param account  the selected account
     * @param positive selects if positive or negative transactions are listed
     * @return
     */
    @Override
    public List<Transaction> getTransactionsByType(String account, boolean positive){
        List<Transaction> type = new ArrayList<>();
        List<Transaction> Lists = accountsToTransactions.get(account);
        for(Transaction tr : Lists){
            if(positive && tr.calculate()>=0){
                type.add(tr);
            }else if(!positive && tr.calculate()<0){
                type.add(tr);
            }
        }
        return type;
    }
}

