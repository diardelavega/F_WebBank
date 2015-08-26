package system;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import comon.OCRequest;
import entity.Employee;
import functions.ManagerFunctions;
import functions.TellerFunctions;

public class Coordinator {

	private static List<OCRequest> ocr = new ArrayList<>();
//	private static List<OCRequest> dirOcr = new ArrayList<>();
	private static List<ManagerFunctions> managers = new ArrayList<>();
	private static List<TellerFunctions> tellers = new ArrayList<>();

	
//	public void addDirOcr(OCRequest req){
//		dirOcr.add(req);
//		//TODO alert Directors
//	}
//	
//	public void deleteDirOCR(OCRequest req){
//		dirOcr.remove(req);
//	}
	
	
	public void addOCR(OCRequest req) {
		ocr.add(req);
		scatterAlertNewOCR();
	}
	public void deleteOCR(OCRequest req){
		ocr.remove(req);
	}

	public OCRequest getNextOCR() {
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
		//TODO implement method for client side manager alert {new request}
		for (int i = 0; i < managers.size(); i++) {
			managers.get(i).alert();
		}
	}

	public void addMenager(ManagerFunctions manager) {
		managers.add(manager);
	}

	public void removeMenager(ManagerFunctions manager) {
		managers.remove(manager);
	}

	
	
}
