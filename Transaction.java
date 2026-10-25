import java.util.List;

public class Transaction {
	
	private int id;
	private BankAccount sender;
	private BankAccount receiver;
	private int amount;
	private String prevHash;
	private String hash;
	
//Saves the ID, sender, receiver, and amount. It immediately calls generateHash()
	public Transaction(int id, BankAccount sender, BankAccount receiver, int amount, String prevHash) {
		this.id = id;
		this.sender = sender;
		this.receiver = receiver;
		this.amount = amount;
		this.prevHash = prevHash;
		this.hash = generateHash(id, sender.getAccNo(), receiver.getAccNo(), amount, prevHash);
	}
	
	public String get() {
		return id + ": " + sender.getAccNo() + " -> " + receiver.getAccNo() + " | $" + amount + " | " + hash;
	}
	/**A static auditor used by the recover method. 
	It loops through a provided list of transactions and recalculates every single hash to ensure the blockchain sequence perfectly links together.
	**/
	public static boolean verify(List<Transaction> transactions) {
		if (transactions == null) return false;
		if (transactions.isEmpty()) return true;
		
		String currentPrevHash = null; 
		
		for (Transaction t : transactions) {
			
			if (currentPrevHash == null) {
				if (t.getPrevHash() != null) return false;
			} else {
				if (!currentPrevHash.equals(t.getPrevHash())) return false;
			}
			
			
			String expectedHash = generateHash(t.getId(), t.getSender().getAccNo(), t.getReceiver().getAccNo(), t.getAmount(), t.getPrevHash());
			if (!expectedHash.equals(t.getHash())) {
				return false;
			}
			currentPrevHash = t.getHash();
		}
		return true;
	}
	//Takes the transaction details and the previous hash, concatenates them into one giant text string, and runs Java's .hashCode()
	public static String generateHash(int id, int senderAccNo, int receiverAccNo, int amount, String prevHash) {
		String combined = id + "" + senderAccNo + "" + receiverAccNo + "" + amount + "" + prevHash;
		return combined.hashCode() + "";
	}

	public int getId() { return id; }
	public BankAccount getSender() { return sender; }
	public BankAccount getReceiver() { return receiver; }
	public int getAmount() { return amount; }
	public String getHash() { return hash; }
	public String getPrevHash() { return prevHash; }
}
