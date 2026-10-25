import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class BankAccount {
	
	public static final int DEFAULT = 10000;
	
	private int accNo;
	private String first;
	private String last;
	private int balance;
	private List<Transaction> history;
	
	/**
	* Constructor for the BankAccount object, Initializes the account with an ID, name, and starting balance, 
	and critically, creates an empty ArrayList for its personal history to prevent NullPointer errors later.
	*/
	public BankAccount(int accNo, String first, String last, int balance) {
		this.accNo = accNo;
		this.first = first;
		this.last = last;
		this.balance = balance;
		this.history = new ArrayList<>();
	}
	
	/**
	* Returns the formatted string of the account.
	* <accNo> - <firstName> <lastName> - $<balance>
	*/
	public String details() {
		return accNo + " - " + first + " " + last + " - $" + balance;
	}
	
	/**
	* Returns list of all transactions involving account. Returns null if no history
	*/
	public List<Transaction> history() {
		return history.isEmpty() ? null : new ArrayList<>(history);
	}
	
	/**
	* Processes transaction by deducting/adding amount. Returns true if successful,
	* false if insufficient funds.
	*/
	public boolean processTransaction(Transaction transaction) {
		if (transaction.getSender().getAccNo() == this.accNo) {
			if (balance < transaction.getAmount()) {
				return false;
			}
			balance -= transaction.getAmount();
		} else if (transaction.getReceiver().getAccNo() == this.accNo) {
			balance += transaction.getAmount();
		}
		history.add(transaction);
		return true;
	}
	
	/**
	* Returns list of transactions where account is sender. Returns null if none.
	*/
	public List<Transaction> outgoing() {
		List<Transaction> out = history.stream().filter(t -> t.getSender().getAccNo() == this.accNo).collect(Collectors.toList());
		return out.isEmpty() ? null : out;
	}
	
	/**
	* Returns list of transactions where account is receiver. Returns null if none.
	*/
	public List<Transaction> incoming() {
		List<Transaction> in = history.stream().filter(t -> t.getReceiver().getAccNo() == this.accNo).collect(Collectors.toList());
		return in.isEmpty() ? null : in;
	}
	
	/**
	* Renames the account with new first and last name.
	*/
	public void rename(String first, String last) {
		this.first = first;
		this.last = last;
	}
	
	public static int findMax(List<BankAccount> accounts) {
		if (accounts == null || accounts.isEmpty()) return -1;
		return accounts.stream().mapToInt(BankAccount::getBalance).max().orElse(-1);
	}
	
	public static int findMin(List<BankAccount> accounts) {
		if (accounts == null || accounts.isEmpty()) return -1;
		return accounts.stream().mapToInt(BankAccount::getBalance).min().orElse(-1);
	}
	
	public static int mean(List<BankAccount> accounts) {
		if (accounts == null || accounts.isEmpty()) return -1;
		long total = accounts.stream().mapToLong(BankAccount::getBalance).sum();
		return (int) (total / accounts.size());
	}
	
	public static int totalBalance(List<BankAccount> accounts) {
		if (accounts == null || accounts.isEmpty()) return -1;
		return accounts.stream().mapToInt(BankAccount::getBalance).sum();
	}

	// Getters
	public int getAccNo() { return accNo; }
	public String getFirst() { return first; }
	public String getLast() { return last; }
	public int getBalance() { return balance; }
	public void addHistory(Transaction t) { this.history.add(t); }
}
