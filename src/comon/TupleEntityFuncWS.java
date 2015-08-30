package comon;

import functions.EmployeeFunctions;

public class TupleEntityFuncWS {

	private EmployeeFunctions ef;
	private String wsSession;

	public TupleEntityFuncWS(EmployeeFunctions ef, String wsSession) {
		super();
		this.ef = ef;
		this.wsSession = wsSession;
	}

	public EmployeeFunctions getEf() {
		return ef;
	}

	public void setEf(EmployeeFunctions ef) {
		this.ef = ef;
	}

	public String getWsSession() {
		return wsSession;
	}

	public void setWsSession(String wsSession) {
		this.wsSession = wsSession;
	}

}
