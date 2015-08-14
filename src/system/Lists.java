package system;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import comon.OCRequest;
import entity.Employee;
import functions.ManagerFunctions;

public class Lists {

	private static List<OCRequest> ocr = new ArrayList<>();
	private static List<ManagerFunctions> managers = new ArrayList<>();

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
