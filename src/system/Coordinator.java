package system;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory.Default;

import sun.security.action.GetLongAction;
import comon.OCRequest;
import comon.StaticVars;
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

	public static Logger logger = LoggerFactory.getLogger(Coordinator.class);
	private static List<OCRequest> ocr = new ArrayList<>();
	private static Map<Integer, ManagerFunctions> managers = new HashMap<>();
	private static Map<Integer, TellerFunctions> tellers = new HashMap<>();
	private static Map<Integer, DirectorFunctions> directors = new HashMap<>();
	private static Map<String, CustomerFunctions> clients = new HashMap<>();
	private static Map<String, String> accounts = new HashMap<>();

	private static int reqCounter = 0;

	public static void addTellerFunc(TellerFunctions telf) {
		tellers.put(telf.getEmpId(), telf);
	}

	public static void addTellSession(int id, Session session) {
		tellers.get(id).setWsSession(session);
	}

	public static void deleteTeller(int id) {
		tellers.remove(id);
	}

	public static Session getTellerSession(int id) {
		return tellers.get(id).getWsSession();
	}

	public static TellerFunctions getTellerFunc(int id) {
		return tellers.get(id);
	}

	/* MANAGER */
	public static void addManagerFunc(ManagerFunctions telf) {
		managers.put(telf.getEmpId(), telf);
	}

	public static void addManagerSession(int id, Session session) {
		managers.get(id).setWsSession(session);
	}

	public static void deleteManager(int id) {
		getManagerFunc(id).leaveOCR();
		managers.remove(id);
	}

	public static Session getManagerSession(int id) {
		return managers.get(id).getWsSession();
	}

	public static ManagerFunctions getManagerFunc(int id) {
		return managers.get(id);
	}

	/* DIRECTOR */
	public static void addDirectorFunc(DirectorFunctions telf) {
		directors.put(telf.getEmpId(), telf);
	}

	public static void addDirectorSession(int id, Session session) {
		directors.get(id).setWsSession(session);
	}

	public static void deleteDirector(int id) {
		directors.remove(id);
	}

	public static Session getDirectorSession(int id) {
		return directors.get(id).getWsSession();
	}

	public static DirectorFunctions getDirectorFunc(int id) {
		return directors.get(id);
	}

	/* CLIENTS */
	public static void addCustomerFunc(CustomerFunctions cf) {
		clients.put(cf.getPersonalId(), cf);
	}

	public static CustomerFunctions getCustomerFunctions(String id) {
		logger.info("SIZE : "+clients.size());
		return clients.get(id);
	}

	public static void addCustomerSession(String id, Session session) {
		clients.get(id).setSession(session);
	}

	public static Session getCustomerSession(String id) {
		return clients.get(id).getSession();
	}

	public static void deleteCustomer(String persId) {
		clients.remove(persId);
	}

	/* ACCOUNTS */
	public static void addAccount(String accNr, String persId) {
		accounts.put(accNr, persId);
	}

	public static String getPersId(String accNr) {
		return accounts.get(accNr);
	}

	public static void deleteAccount(String accNr) {
		accounts.remove(accNr);
	}

	/* OCR */
	public static void addOCR(OCRequest req) {
		ocr.add(req);
		logger.info("OCR addet to Coordinator");
		switch (req.getReqType()) {
		case StaticVars.OPEN:
		case StaticVars.PLUS_6_ACC:
			scatterAlertNewOCR(req.getReqType(), req.getClientIdsList().get(0),
					"accType_" + req.getAccType());
			break;
		case StaticVars.CLOSE:
			scatterAlertNewOCR(req.getReqType(), req.getAccFromNr(), req
					.getClientIdsList().get(0));
			break;
		case StaticVars.PLUS_1K_DEP:
		case StaticVars.PLUS_1K_TRANS:
		case StaticVars.PLUS_1K_WITH:
			scatterAlertNewOCR(req.getReqType(), req.getAccFromNr(),
					String.valueOf(req.getAmount()));
			break;
		default:
			logger.warn("Unexpected request arrived");
			break;
		}

		// -------------Test Purposes------------------------
		// req.setResponse(StaticVars.DENIE);
		// req.setLastManagerToConsiderIt(-88);
		// req.setStatusComplete();
		// req.setNote("AutoResponse");
		// try {
		// reviewedOCR(req);
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// =============================
		ppReqCounter();
		// TODO notify manager for requests nr.
	}

	public static void deleteOCR(OCRequest req) {
		// mmReqCounter();
		ocr.remove(req);
		// TODO notify manager for requests nr.
	}

	public static OCRequest getNextOCR(int manId) {
		for (int i = 0; i < ocr.size(); i++) {
			if (ocr.get(i).isPin())
				continue;
			if (ocr.get(i).getLastManagerToConsiderIt() == manId)
				continue;
			return ocr.get(i);
		}
		return null;
	}

	public static OCRequest getForceNextOCR(OCRequest req2) {
		boolean flag = false;
		int i = ocr.indexOf(req2);

		for (; i < ocr.size(); i++) {
			if (flag == false) {
				if (ocr.size() - 1 <= i) {
					i = -1;
					flag = true;
				} else {
					i++;
					if (ocr.get(i).isPin())
						continue;
					return ocr.get(i);
				}

			} else {// flag==true
				if (ocr.get(i).isPin())
					continue;
				return ocr.get(i);
			}
		}

		return null;
	}

	public static void leaveOCR(OCRequest req) {
		ocr.get(ocr.indexOf(req)).unPin();
	}

	private static void scatterAlertNewOCR(String reqType, String d2, String ed) {
		for (Integer key : managers.keySet()) {
			getManagerFunc(key).alert(reqType, d2, ed);
		}
	}

	private static void scatterAlertOCRNr() {
		for (Integer key : managers.keySet()) {
			getManagerFunc(key).updateRequestNr(reqCounter);
		}
	}

	public static void reviewedOCR(OCRequest req) throws IOException {
		if (req.isStatus()) {
			tellers.get(req.getTellerId()).alert(req);
		}
	}

	public static String ocrListSize() {
		return ocr.size() + "";
	}

	public static void printAll() {
		for (OCRequest re : ocr) {
			re.print();
		}
	}

	public static int getReqCounter() {
		return reqCounter;
	}

	public static void ppReqCounter() {
		reqCounter++;
		scatterAlertOCRNr();
		// TODO alert manager for the available reqs
	}

	public static void mmReqCounter() {
		reqCounter--;
		scatterAlertOCRNr();
		// TODO alert manager for the available reqs
	}

}
