package bank;

import bank.exceptions.*;
import com.google.gson.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


public class PrivateBank implements Bank {
    private String name;
    private double incomingInterest;
    private double OutgoingInterest;
    private Map<String, List<Transaction>> accountsToTransactions = new HashMap<>();

    private String path;

    private String directoryName;


    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * @param incomingInterest
     * @throws IncomingException
     */
    public void setIncomingInterest(double incomingInterest) throws IncomingException {
        if (incomingInterest > 0 && incomingInterest < 1) {
            this.incomingInterest = incomingInterest;
        } else throw new IncomingException("Value of Incoming Interest must be between 0 and 1");
    }

    /**
     * @return
     */
    public double getIncomingInterest() {
        return incomingInterest;
    }

    /**
     * @param outgoingInterest
     * @throws OutgoingException
     */
    public void setOutgoingInterest(double outgoingInterest) throws OutgoingException {
        if (outgoingInterest > 0 && outgoingInterest < 1) {
            OutgoingInterest = outgoingInterest;
        } else throw new OutgoingException("Value of Incoming Interest must be between 0 and 1");
    }

    /**
     * @return
     */
    public double getOutgoingInterest() {
        return OutgoingInterest;
    }

    /**
     * @param DirName
     */
    public void setDirectoryName(String DirName) {
        this.directoryName = DirName;
    }

    /**
     * @return
     */
    public String getDirectoryName() {
        return directoryName;
    }

    /**
     * @param dirName
     */
    public void setPath(String dirName) {
        path = "Data/" + dirName;
    }

    /**
     * @return
     */
    public String getPath() {
        return path;
    }

    /**
     * @param name
     * @param incomingInterest
     * @param outgoingInterest
     * @throws IncomingException
     * @throws OutgoingException
     */
    public PrivateBank(String name, double incomingInterest, double outgoingInterest, String directoryName) throws IncomingException, OutgoingException {
        this.name = name;
        this.directoryName = directoryName;
        setIncomingInterest(incomingInterest);
        setOutgoingInterest(outgoingInterest);
        setPath(directoryName);
        try {
            Path path = Paths.get(this.path);
            if (Files.notExists(path)) {
                Files.createDirectories(path);
                System.out.println("\n new Directory " + this.name + " created succesfully");
            } else {
                readAccounts();
            }
        } catch (IOException | AccountAlreadyExistsException fail) {
            System.out.println("Failed to create directory");
        }
    }

    /**
     * @param other
     * @throws IncomingException
     * @throws OutgoingException
     */
    public PrivateBank(PrivateBank other) throws IncomingException, OutgoingException {
        this(other.name, other.incomingInterest, other.OutgoingInterest, other.directoryName);

    }


    /**
     * function to write attributes of the class
     *
     * @return a String of attributes
     */
    @Override
    public String toString() {
        String res = "Name :" + this.name +
                "\nIncoming interest: " + this.incomingInterest +
                "\nOutgoing interest: " + this.OutgoingInterest;
        Set<String> keys = accountsToTransactions.keySet();
        for (String key : keys) {
            res += "\n" + key + "=> \n";
            List<Transaction> Lists = accountsToTransactions.get(key);
            res += "[\n";
            for (Transaction transaction : Lists)
                res += transaction + "\n";
            res += "]\n";
        }
        return res;
    }

    /**
     * function to compare current Variable and an Object
     *
     * @param obj other objekt to compare with
     * @return whether the result of compare true or false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj instanceof PrivateBank privateBank) {
            if (this.name == privateBank.name && this.incomingInterest == privateBank.incomingInterest && this.OutgoingInterest == privateBank.OutgoingInterest) {
                return true;
            } else
                return false;
        } else
            return false;
    }

    /**
     * @param account the account to be added
     * @throws AccountAlreadyExistsException
     */
    @Override
    public void createAccount(String account) throws AccountAlreadyExistsException, IOException {
        if (accountsToTransactions.containsKey(account))
            throw new AccountAlreadyExistsException("account with the name " + account + " in the bank" + name + " already exists!");
        else {
            accountsToTransactions.put(account, List.of());
            writeAccount(account);
            System.out.println("account with the name " + account + " has succesfully created in the bank " + name);
        }
    }

    /**
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
            throws AccountAlreadyExistsException, TransactionAlreadyExistException, TransactionAttributeException, OutgoingException, IncomingException, IOException {
        if (accountsToTransactions.containsKey(account)) {
            throw new AccountAlreadyExistsException("account with the name " + account + " in the bank" + name + " already exists!");
        } else {
            for (Transaction tr : transactions) {
                if (accountsToTransactions.containsKey(account) && accountsToTransactions.get(account).contains(transactions)) {
                    throw new TransactionAlreadyExistException("duplicate transaction can not be added to the account!");
                } else {
                    if (tr instanceof Payment payment) {
                        if (payment.getIncomingInterest() < 0 || payment.getIncomingInterest() > 1 || payment.getOutgoingInterest() < 0 || payment.getOutgoingInterest() > 1) {
                            throw new TransactionAttributeException("Transaction attribute fails!");
                        } else {
                            payment.setOutgoingInterest(this.OutgoingInterest);
                            payment.setIncomingInterest(this.incomingInterest);
                        }
                    }

                }
            }
            accountsToTransactions.put(account, transactions);
            writeAccount(account);
            System.out.println("account with the name " + account + " and its transaction has succesfully created in the bank " + name);
        }
    }

    /**
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
            throws TransactionAlreadyExistException, AccountDoesNotExistException, TransactionAttributeException, OutgoingException, IncomingException, IOException {
        if (!accountsToTransactions.containsKey(account)) {
            throw new AccountDoesNotExistException("account with the name " + account + " in the bank" + name + " does not exist!");
        } else {
            if (transaction instanceof Payment payment) {
                if (payment.getIncomingInterest() < 0 || payment.getIncomingInterest() > 1 || payment.getOutgoingInterest() < 0 || payment.getOutgoingInterest() > 1) {
                    throw new TransactionAttributeException("Transaction attribute fails!");
                } else {
                    payment.setOutgoingInterest(this.OutgoingInterest);
                    payment.setIncomingInterest(this.incomingInterest);
                }
            }
            if (accountsToTransactions.get(account).contains(transaction)) {
                throw new TransactionAlreadyExistException("this transaction is already exist in the account " + account);
            }
            if (transaction instanceof Payment payment) {
                if (payment.getIncomingInterest() < 0 || payment.getIncomingInterest() > 1 || payment.getOutgoingInterest() < 0 || payment.getOutgoingInterest() > 1) {
                    throw new TransactionAttributeException("Transaction attribute fails!");
                } else {
                    payment.setOutgoingInterest(this.OutgoingInterest);
                    payment.setIncomingInterest(this.incomingInterest);
                }
            }
            List<Transaction> Lists = new ArrayList<>(accountsToTransactions.get(account));
            Lists.add(transaction);
            accountsToTransactions.put(account, Lists);
            writeAccount(account);
            System.out.println("Transaction successfully added");

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
            throws AccountDoesNotExistException, TransactionDoesNotExistException, IOException, IncomingException, OutgoingException {
        if(!accountsToTransactions.containsKey(account)){
            throw new AccountDoesNotExistException("account with the name "+account+" in the bank"+name+" does not exist!");
        }else {
            if(transaction instanceof Payment payment){
                ((Payment) transaction).setIncomingInterest(this.incomingInterest);
                ((Payment) transaction).setOutgoingInterest(this.OutgoingInterest);
            }
            if(!accountsToTransactions.get(account).contains(transaction)){
                throw new TransactionDoesNotExistException("this transaction does not exist in the account "+account);
            }else{
                accountsToTransactions.get(account).remove(transaction);
                writeAccount(account);
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
    public boolean containsTransaction(String account, Transaction transaction) throws IncomingException, OutgoingException {
        if (transaction instanceof Payment payment) {
            payment.setOutgoingInterest(this.OutgoingInterest);
            payment.setIncomingInterest(this.incomingInterest);
        }
        if (accountsToTransactions.containsKey(account)) {
            if (accountsToTransactions.get(account).contains(transaction))
                return true;
        }
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
        return accountsToTransactions.get(account);
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
    private void readAccounts() throws IOException, AccountAlreadyExistsException {
        File directory = new File(PrivateBank.this.getPath());
        File[] fileLists = Objects.requireNonNull(directory.listFiles());

        for(File file : fileLists){
            String fileName = file.getName();
            String accountName = fileName.replace(".json","");
            PrivateBank.this.createAccount(accountName);
            try{
                Reader read = new FileReader((PrivateBank.this.getPath()+"/"+fileName));
                JsonArray jsonArray = JsonParser.parseReader(read).getAsJsonArray();
                for(JsonElement jsonElement : jsonArray.getAsJsonArray()){
                    JsonObject Obj = jsonElement.getAsJsonObject();
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    gsonBuilder.registerTypeAdapter(Transaction.class, new De_Serialization());
                    Gson gson = gsonBuilder.create();

                    String string = gson.toJson(Obj.get("INSTANCE"));

                    if(Obj.get("CLASSNAME").getAsString().equals("Payment")){
                        Payment payment = gson.fromJson(string,Payment.class);
                        PrivateBank.this.addTransaction(accountName,payment);
                    }else if(Obj.get("CLASSNAME").getAsString().equals("IncomingTransfer")){
                        IncomingTransfer incomingTransfer = gson.fromJson(string,IncomingTransfer.class);
                        PrivateBank.this.addTransaction(accountName,incomingTransfer);
                    }else{
                        OutgoingTransfer outgoingTransfer = gson.fromJson(string,OutgoingTransfer.class);
                        PrivateBank.this.addTransaction(accountName,outgoingTransfer);
                    }
                }
            }catch (IOException e){
                e.printStackTrace();
            } catch (TransactionAlreadyExistException|AccountDoesNotExistException|TransactionAttributeException|IncomingException|OutgoingException e) {
                System.out.println(e);
            }
        }
    }
    private void writeAccount(String account) throws IOException{
        FileWriter file = new FileWriter(getPath()+'/'+account+".json");
        try{
            file.write('[');
            for (Transaction transaction: accountsToTransactions.get(account)){
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.registerTypeAdapter(transaction.getClass(),new De_Serialization());
                gsonBuilder.setPrettyPrinting();
                Gson gson = gsonBuilder.create();
                String json = gson.toJson(transaction);
                if(accountsToTransactions.get(account).indexOf(transaction)!=0)
                    file.write(',');
                file.write(json);
            }
            file.write(']');
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}

