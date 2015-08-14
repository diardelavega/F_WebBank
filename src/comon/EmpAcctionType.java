package comon;

public enum EmpAcctionType {
	OPEN('o'), CLOSE('c'), DEPOSITE('d'), WITHDRAW('w'), TRANSFER('t');
	public char value;

	private EmpAcctionType(char c) {
		this.value = c;

	}
}
