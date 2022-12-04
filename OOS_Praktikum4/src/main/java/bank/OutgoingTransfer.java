package bank;

public class OutgoingTransfer extends Transfer{
    /**
     *
     * @param date
     * @param desc
     * @param amount
     */
    public OutgoingTransfer(String date,  double amount,String desc){
        super(date,amount,desc);
    }

    /**
     *
     * @param date
     * @param amount
     * @param desc
     * @param send
     * @param rec
     */
    public OutgoingTransfer(String date, double amount, String desc, String send, String rec){
        super(date,amount,desc,send,rec);
    }

    public OutgoingTransfer(IncomingTransfer other){
        super(other);
    }

    /**
     *
     * @return
     */
    @Override
    public double calculate(){
        return -super.amount;
    }
}
