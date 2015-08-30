package system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import comon.OCRequest;
import comon.TupleEntityFuncWS;
import entity.Employee;
import functions.DirectorFunctions;
import functions.EmployeeFunctions;
import functions.ManagerFunctions;
import functions.TellerFunctions;

/**
 * @author Administrator
 *
 *         IN this class are contained the lists of all the actors loged in into
 *         the system. at first from the log in form the user (teller, manager,
 *         director, client) ("xFuction") is added in the list; at a second
 *         stage to that xFunction is added the web socket session id. in case
 *         of log out the hole tuple(function, ws-session) is deleted
 * 
 */
public class Coordinator {

	private static List<OCRequest> ocr = new ArrayList<>();

	private static Map<Integer, TupleEntityFuncWS> managers = new HashMap<>();
	private static Map<Integer, TupleEntityFuncWS> tellers = new HashMap<>();
	private static Map<Integer, TupleEntityFuncWS> directors = new HashMap<>();

	// private static List<TupleEntityFuncWS> clients = new ArrayList<>();

	public void addTellerFunc(TellerFunctions telf) {
		TupleEntityFuncWS tef = new TupleEntityFuncWS(telf, null);
		tellers.put(telf.getEmpId(), tef);
	}

	public void addTellSession(int id, String session) {
		tellers.get(id).setWsSession(session);
	}

	public void deleteTeller(int id) {
		tellers.remove(id);
	}

	public String getTellerSession(int id) {
		return tellers.get(id).getWsSession();
	}

	public TellerFunctions getTellerFunc(int id) {
		return (TellerFunctions) tellers.get(id).getEf();
	}

	/* MANAGER */
	public void addManagerFunc(ManagerFunctions telf) {
		TupleEntityFuncWS tef = new TupleEntityFuncWS(telf, null);
		managers.put(telf.getEmpId(), tef);
	}

	public void addManagerSession(int id, String session) {
		managers.get(id).setWsSession(session);
	}

	public void deleteManager(int id) {
		managers.remove(id);
	}

	public String getManagerSession(int id) {
		return managers.get(id).getWsSession();
	}

	public ManagerFunctions getManagerFunc(int id) {
		return (ManagerFunctions) managers.get(id).getEf();
	}

	/* DIRECTOR */
	public void addDirectorFunc(DirectorFunctions telf) {
		TupleEntityFuncWS tef = new TupleEntityFuncWS(telf, null);
		directors.put(telf.getEmpId(), tef);
	}

	public void addDirectorSession(int id, String session) {
		directors.get(id).setWsSession(session);
	}

	public void deleteDirector(int id) {
		directors.remove(id);
	}

	public String getDirectorSession(int id) {
		return directors.get(id).getWsSession();
	}

	public DirectorFunctions getDirectorFunc(int id) {
		return (DirectorFunctions) directors.get(id).getEf();
	}

	/* OCR */
	public void addOCR(OCRequest req) {
		ocr.add(req);
		scatterAlertNewOCR();
	}

	public void deleteOCR(OCRequest req) {
		ocr.remove(req);
	}

	public OCRequest getNextOCR(int manId) {
		for (int i = 0; i < ocr.size(); i++) {
			if (ocr.get(i).isPin())
				continue;
			if (ocr.get(i).lastManCons == manId)
				continue;
			ocr.get(i).pin();
			return ocr.get(i);
		}
		return null;
	}

	public OCRequest getForceNextOCR() {
		for (int i = 0; i < ocr.size(); i++) {
			if (ocr.get(i).isPin())
				continue;
			ocr.get(i).pin();
			return ocr.get(i);
		}
		return null;
	}

	public void leaveOCR(OCRequest req) {
		ocr.get(ocr.indexOf(req)).unPin();
	}

	private void scatterAlertNewOCR() {
		for (Integer key : managers.keySet()) {
			getManagerFunc(key).alert();
		}
	}
}
