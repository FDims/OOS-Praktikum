import bank.Payment;
import bank.exceptions.IncomingException;
import bank.exceptions.OutgoingException;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentTest {
    Payment payment1;
    Payment payment2;
    Payment payment3;
    Payment copyPayment;

    @BeforeEach
    public void init() throws IncomingException, OutgoingException {
        payment1 = new Payment("20.10.2022",1300,"Payment test1");
        payment2 = new Payment("12.12.2021",-1500,"Payment test2",0.15,0.1);
        payment3 = new Payment("12.12.2021",1500,"Payment test3",0.15,0.1);
        copyPayment=new Payment(payment2);
    }

    @Test
    public void payment1Test(){
        assertEquals("20.10.2022",payment1.getDate());
        assertEquals(1300,payment1.getAmount());
        assertEquals("Payment test1",payment1.getDescription());
    }

    @Test
    public void payment2Test(){
        assertEquals("12.12.2021",payment2.getDate());
        assertEquals(-1500,payment2.getAmount());
        assertEquals("Payment test2",payment2.getDescription());
        assertEquals(0.15,payment2.getIncomingInterest());
        assertEquals(0.1,payment2.getOutgoingInterest());

    }

    @Test
    public void copyTest(){
        assertEquals(copyPayment.getDate(),payment2.getDate());
        assertEquals(copyPayment.getAmount(),payment2.getAmount());
        assertEquals(copyPayment.getDescription(),payment2.getDescription());
        assertEquals(copyPayment.getIncomingInterest(),payment2.getIncomingInterest());
        assertEquals(copyPayment.getOutgoingInterest(),payment2.getOutgoingInterest());
    }

    @Test
    public void calculateIn(){
        double calc = payment3.getAmount()-(payment3.getIncomingInterest()* payment3.getAmount());
        assertTrue(payment3.getAmount()>0);
        assertEquals(calc,payment3.calculate());
    }

    @Test
    public void calculateOut(){
        double calc = payment2.getAmount()+(payment2.getOutgoingInterest()* payment2.getAmount());
        assertTrue(payment2.getAmount()<0);
        assertEquals(calc,payment2.calculate());
    }

    @Test
    public void toStringTest(){
        double calc = payment2.getAmount()+(payment2.getOutgoingInterest()* payment2.getAmount());
        String str = " Date of Transaction: 12.12.2021" +
                "\n Amount of Transaction: "+calc+
                "\n Description: Payment test2"+
                "\n Incoming interest: "+0.15+
                "\n Outgoing interest: "+0.1+"\n";
        assertEquals(str,payment2.toString());
    }

    @Test
    public void equalsTest(){
        assertEquals(payment2,copyPayment);
        assertFalse(payment3.equals(copyPayment));
        assertTrue(copyPayment.equals(payment2));
    }

}
