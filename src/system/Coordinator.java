package system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import javax.websocket.Session;

import comon.OCRequest;
import comon.TupleEntityFuncWS;
import entity.Employee;
import functions.CustomerFunctions;
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

	private static Map<Integer, ManagerFunctions> managers = new HashMap<>();
	private static Map<Integer, TellerFunctions> tellers = new HashMap<>();
	private static Map<Integer, DirectorFunctions> directors = new HashMap<>();

	private static Map<String, CustomerFunctions> clients = new HashMap<>();

	public void addTellerFunc(TellerFunctions telf) {
		tellers.put(telf.getEmpId(), telf);
	}

	public void addTellSession(int id, Session session) {
		tellers.get(id).setWsSession(session);
	}

	public void deleteTeller(int id) {
		tellers.remove(id);
	}

	public Session getTellerSession(int id) {
		return tellers.get(id).getWsSession();
	}

	public TellerFunctions getTellerFunc(int id) {
		return tellers.get(id);
	}

	/* MANAGER */
	public void addManagerFunc(ManagerFunctions telf) {
		managers.put(telf.getEmpId(), telf);
	}

	public void addManagerSession(int id, Session session) {
		managers.get(id).setWsSession(session);
	}

	public void deleteManager(int id) {
		managers.remove(id);
	}

	public Session getManagerSession(int id) {
		return managers.get(id).getWsSession();
	}

	public ManagerFunctions getManagerFunc(int id) {
		return managers.get(id);
	}

	/* DIRECTOR */
	public void addDirectorFunc(DirectorFunctions telf) {
		directors.put(telf.getEmpId(), telf);
	}

	public void addDirectorSession(int id, Session session) {
		directors.get(id).setWsSession(session);
	}

	public void deleteDirector(int id) {
		directors.remove(id);
	}

	public Session getDirectorSession(int id) {
		return directors.get(id).getWsSession();
	}

	public DirectorFunctions getDirectorFunc(int id) {
		return directors.get(id);
	}

	/* CLIENTS */
	public void addCustomerFunc(CustomerFunctions cf) {
		clients.put(cf.getPersonalId(), cf);
	}

	public CustomerFunctions getCustomerFunctions(String id) {
		return clients.get(id);
	}

	public void addCustomerSession(String id, Session session) {
		clients.get(id).setSession(session);
	}

	public Session getCustomerSession(String id) {
		return clients.get(id).getSession();
	}

	public void deleteCustomer(String persId) {
		clients.remove(persId);
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
