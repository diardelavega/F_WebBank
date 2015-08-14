package comon;

public enum AccountType {
	BASIC_CHECKING('b'), INTEREST_BARING('i'), CERTIFICATE_OF_DEPSITE('c'), MONEY_MARKET_DEPOSITE(
			'm');
	public char value;

	private AccountType(char c) {
		this.value = c;

	}

}
