import bank.IncomingTransfer;
import bank.OutgoingTransfer;
import bank.Transfer;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class TransferTest {
    Transfer incomingTransfer;
    Transfer outgoingTransfer;
    Transfer copyTransfer;

    @BeforeEach
    public void init(){
        incomingTransfer = new IncomingTransfer("22.02.2022",2000,"Incoming transfer to Jessica from Alexa");
        outgoingTransfer = new OutgoingTransfer("22.02.2022",2000,"Outgoing transfer from Alexa to Jessica", "Alexa","Jessica");
        copyTransfer = new Transfer(outgoingTransfer);
    }

    @Test
    public void inTransferTest(){
        assertEquals("22.02.2022",incomingTransfer.getDate());
        assertEquals(2000,incomingTransfer.getAmount());
        assertEquals("Incoming transfer to Jessica from Alexa",incomingTransfer.getDescription());
    }

    @Test
    public void outTransferTest(){
        assertEquals("22.02.2022",outgoingTransfer.getDate());
        assertEquals(2000,outgoingTransfer.getAmount());
        assertEquals("Outgoing transfer from Alexa to Jessica",outgoingTransfer.getDescription());
        assertEquals("Alexa",outgoingTransfer.getSender());
        assertEquals("Jessica",outgoingTransfer.getRecipient());
    }

    @Test
    public void calculateTest(){
        assertEquals(2000,incomingTransfer.calculate());
        assertEquals(-2000,outgoingTransfer.calculate());
    }

    @Test
    public void toStringTest(){
        String str = " Date of Transaction: 22.02.2022" +
                "\n Amount of Transaction: "+ -2000.0 +
                "\n Description: Outgoing transfer from Alexa to Jessica"+
                "\n Sender: Alexa"+
                "\n Recipient: Jessica\n";
        assertEquals(str,outgoingTransfer.toString());
    }

    @Test
    public void equalsTest(){
        assertFalse(copyTransfer.equals(incomingTransfer));
        assertTrue(copyTransfer.equals(outgoingTransfer));
    }

}
