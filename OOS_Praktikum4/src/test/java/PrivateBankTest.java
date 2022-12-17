import bank.*;
import bank.exceptions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PrivateBankTest {
   static PrivateBank privateBank;
    static PrivateBank copyPrivateBank;

    @BeforeAll
    public static void init() throws IncomingException, OutgoingException, TransactionAlreadyExistException, AccountAlreadyExistsException, TransactionAttributeException, IOException {
        final File folder = new File("data/JUnitTest");
        if (folder.exists()) {
            final File[] listOfFiles = folder.listFiles();
            assert listOfFiles != null;
            for (File file : listOfFiles)
                file.delete();
            Files.deleteIfExists(Path.of("data/JUnitTest"));
        }


        privateBank = new PrivateBank("JUnitTest", 0.15, 0.12, "JUnitTest");
        copyPrivateBank = new PrivateBank(privateBank);
        privateBank.createAccount("Alexa", List.of(
                new OutgoingTransfer("02.08.2022", 560, "this is Transfer", "Alexa", "Jessica"),
                new Payment("01.02.2021", 1255, "this is deposit", 0.15, 0.1),
                new Payment("01.02.2021", -1350, "this is deposit", 0.12, 0.3),
                new IncomingTransfer("02.08.2022", 520, "this is Transfer", "Antonio", "Alexa")
        ));
        privateBank.createAccount("Antonio", List.of(
                new OutgoingTransfer("02.08.2022", 520, "this is Transfer", "Antonio", "Jessica"),
                new Payment("01.02.2021", 1200, "this is deposit", 0.15, 0.1),
                new Payment("01.02.2021", -1100, "this is deposit", 0.12, 0.3),
                new IncomingTransfer("02.08.2022", 950, "this is Transfer", "Alexa", "Antonio")
        ));

    }

    @Test
    @Order(0)
    public void constructorTest() {
        assertEquals("JUnitTest", privateBank.getName());
        assertEquals(0.15, privateBank.getIncomingInterest());
        assertEquals(0.12, privateBank.getOutgoingInterest());
        assertEquals("JUnitTest", privateBank.getDirectoryName());
    }

   @Test
       @Order(1)
    public void copyConstructorTest() {
        assertEquals(privateBank.getName(), copyPrivateBank.getName());
        assertEquals(privateBank.getIncomingInterest(), copyPrivateBank.getIncomingInterest());
        assertEquals(privateBank.getOutgoingInterest(), copyPrivateBank.getOutgoingInterest());
        assertEquals(privateBank.getDirectoryName(), copyPrivateBank.getDirectoryName());
    }


    @ParameterizedTest
    @Order(2)
    @ValueSource(strings = {"Alexa", "Antonio"})
    public void createAccountThrow(String account) {
        Exception e = assertThrows(AccountAlreadyExistsException.class, () -> privateBank.createAccount(account));
        System.out.println(e.getMessage());
    }

    @ParameterizedTest
    @Order(3)
    @ValueSource(strings = {"Jessica", "Eden"})
    public void createAccount(String account) {
        assertDoesNotThrow(() -> privateBank.createAccount(account));
    }

    @ParameterizedTest
    @Order(4)
    @ValueSource(strings = {"Alexa", "Antonio","Jessica", "Eden"})
    public void createAccountListThrow(String account){
        Exception e = assertThrows(AccountAlreadyExistsException.class, () -> privateBank.createAccount(account,List.of(new Payment("01.02.2021",1200,"this is deposit",0.15,0.1))));
        System.out.println(e.getMessage());
    }

    @ParameterizedTest
    @Order(5)
    @ValueSource(strings = {"Joe","Lee"})
    public void createAccountList(String account){
       assertDoesNotThrow(() -> privateBank.createAccount(account,List.of(new Payment("01.02.2021",1200,"this is deposit",0.15,0.1))));
    }

    @Test
    @Order(6)
    public void addTransactionThrow(){
        Exception e = assertThrows(AccountDoesNotExistException.class,()->privateBank.addTransaction("Sarah",new Payment("01.02.2021",1200,"this is deposit",0.15,0.1)));
        System.out.println(e.getMessage());
        e=assertThrows(TransactionAlreadyExistException.class,()->privateBank.addTransaction("Joe",new Payment("01.02.2021",1200,"this is deposit",0.15,0.1)));
        System.out.println(e.getMessage());
    }

    @Test
    @Order(7)
    public void addTransactionTest(){
        assertDoesNotThrow(() ->privateBank.addTransaction("Joe",new Payment("01.03.2021",1500,"this is deposit2",0.15,0.1)));
    }

    @Test
    @Order(8)
    public void removeTransactionThrow(){
        Exception e = assertThrows(AccountDoesNotExistException.class,()->privateBank.removeTransaction("Sarah",new Payment("01.02.2021",1200,"this is deposit",0.15,0.1)));
        System.out.println(e.getMessage());
        e=assertThrows(TransactionDoesNotExistException.class,()->privateBank.removeTransaction("Joe",new Payment("05.05.2024",1800,"this is deposit",0.15,0.1)));
        System.out.println(e.getMessage());
    }

    @Test
    @Order(9)
    public void removeTransactionTest(){
        assertDoesNotThrow(() ->privateBank.removeTransaction("Joe",new Payment("01.02.2021",1200,"this is deposit",0.15,0.1)));
    }

    @Test
    @Order(10)
    public void containsTest() throws IncomingException, OutgoingException {
        assertTrue(privateBank.containsTransaction("Lee",new Payment("01.02.2021",1200,"this is deposit",0.15,0.1)));
        assertFalse(privateBank.containsTransaction("Max",new Payment("01.02.2021",1200,"this is deposit",0.15,0.1)));
    }

    @Test
    @Order(11)
    public void BalanceTest(){
        assertEquals(-485.25,privateBank.getAccountBalance("Alexa"));
        assertEquals(218.0,privateBank.getAccountBalance("Antonio"));
    }

    @Test
    @Order(12)
    public void getTransactionTest() throws IncomingException, OutgoingException {
        assertEquals(List.of(
                new OutgoingTransfer("02.08.2022", 560, "this is Transfer", "Alexa", "Jessica"),
                new Payment("01.02.2021", 1255, "this is deposit", 0.15, 0.12),
                new Payment("01.02.2021", -1350, "this is deposit", 0.15, 0.12),
                new IncomingTransfer("02.08.2022", 520, "this is Transfer", "Antonio", "Alexa")
        ),privateBank.getTransactions("Alexa"));
    }

    @Test
    @Order(13)
    public void getTransactionByTypeTest() throws IncomingException, OutgoingException {
        assertEquals(List.of(
                new Payment("01.02.2021", 1255, "this is deposit", 0.15, 0.12),
                new IncomingTransfer("02.08.2022", 520, "this is Transfer", "Antonio", "Alexa")
        ),privateBank.getTransactionsByType("Alexa",true));
        assertEquals(List.of(
                new OutgoingTransfer("02.08.2022", 560, "this is Transfer", "Alexa", "Jessica"),
                new Payment("01.02.2021", -1350, "this is deposit", 0.15, 0.12)

        ),privateBank.getTransactionsByType("Alexa",false));
    }

    @Test
    @Order(14)
    public void getTransactionSorted() throws IncomingException, OutgoingException {
        assertEquals(List.of(
                new Payment("01.02.2021", -1350, "this is deposit", 0.15, 0.12),
                new OutgoingTransfer("02.08.2022", 560, "this is Transfer", "Alexa", "Jessica"),
                new IncomingTransfer("02.08.2022", 520, "this is Transfer", "Antonio", "Alexa"),
                new Payment("01.02.2021", 1255, "this is deposit", 0.15, 0.12)
        ),privateBank.getTransactionsSorted("Alexa",true));
    }
}


