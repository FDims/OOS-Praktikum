package bank;

public class IncomingTransfer extends Transfer{

    /**
     *
     * @param date
     * @param desc
     * @param amount
     */
     public IncomingTransfer(String date,  double amount,String desc){
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
     public IncomingTransfer(String date, double amount, String desc, String send, String rec){
         super(date,amount,desc,send,rec);
     }

     public IncomingTransfer(IncomingTransfer other){
         super(other);
     }

    /**
     *
     * @return
     */
     @Override
    public double calculate(){
         return amount;
    }
}
