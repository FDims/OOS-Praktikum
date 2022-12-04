package bank;
import bank.exceptions.IncomingException;
import bank.exceptions.OutgoingException;
import com.google.gson.*;

import java.lang.reflect.Type;

public class De_Serialization implements JsonSerializer<Transaction>, JsonDeserializer<Transaction> {

    @Override
    public Transaction deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject Jobj = jsonElement.getAsJsonObject();
        if(Jobj.get("CLASSNAME").getAsString().equals("Payment")){
            try {
                return new Payment(Jobj.get("date").getAsString(),
                        Jobj.get("amount").getAsDouble(),
                        Jobj.get("description").getAsString(),
                        Jobj.get("incomingInterest").getAsDouble(),
                        Jobj.get("outgoingInterest").getAsDouble());
            } catch (IncomingException | OutgoingException e) {
                System.out.println(e);
            }
        }else if(Jobj.get("CLASSNAME").getAsString().equals("IncomingTransfer")){
            return new IncomingTransfer(Jobj.get("date").getAsString(),
                    Jobj.get("amount").getAsDouble(),
                    Jobj.get("description").getAsString(),
                    Jobj.get("sender").getAsString(),
                    Jobj.get("recipient").getAsString());
        }else{
            return new OutgoingTransfer(Jobj.get("date").getAsString(),
                    Jobj.get("amount").getAsDouble(),
                    Jobj.get("description").getAsString(),
                    Jobj.get("sender").getAsString(),
                    Jobj.get("recipient").getAsString());
        }
        return null;
    }

    @Override
    public JsonElement serialize(Transaction transaction, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject Obj = new JsonObject();
        JsonObject Instance = new JsonObject();
        Instance.addProperty("date",transaction.getDate());
        Instance.addProperty("amount",transaction.getAmount());
        Instance.addProperty("description",transaction.getDescription());
        if(transaction instanceof Transfer transfer){
            Instance.addProperty("sender",transfer.getSender());
            Instance.addProperty("recipient",transfer.getRecipient());
        }else if(transaction instanceof Payment payment){
            Instance.addProperty("incomingInterest",payment.getIncomingInterest());
            Instance.addProperty("outgoingInterest",payment.getOutgoingInterest());
        }
        Obj.addProperty("CLASSNAME",transaction.getClass().getSimpleName());
        Obj.add("INSTANCE",Instance);
        return Obj;
    }
}
