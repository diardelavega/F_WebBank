package fileLog;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileHandler {

	private final Logger logger = LoggerFactory.getLogger(FileHandler.class);
	// private final String base = System.getProperty("user.dir");
	private final String base = "C:/Users/Administrator/WebBankLog/";
	private final File monthFolder = new File(base + "/allMonthLog/");
	private final File dailyFolder = new File(base + "/allDaysLog/");

	// private final File monthFolder = new File(base +
	// "/src/fileLog/allMonthLog/");
	// private final File dailyFolder = new File(base +
	// "/src/fileLog/allDaysLog/");
	private File dailyile = new File(dailyFolder + "/" + dateLogNamer());

	public void init() throws IOException {
		/*
		 * logger.info("Canonical--------> " + dailyile.getCanonicalPath());
		 * logger.info(monthFolder.getAbsolutePath());
		 * logger.info(dailyFolder.getAbsolutePath()); logger.info(base);
		 */

		if (!monthFolder.exists()) {
			monthFolder.mkdirs();
		}
		if (!dailyFolder.exists()) {
			dailyFolder.mkdirs();
		}
		if (!dailyile.exists()) {
			dailyile.createNewFile();
			logger.info("New File Created");
		}
		filePlacementControl();
		logger.info("Daily data copied");
	}

	public void append(String log) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(dailyile,
					true));
			synchronized (bw) {
				bw.append(log + "\n");
				// bw.flush();
				bw.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
			logger.warn("BufferedWriter Synchronization exception");
		}
	}

	public void filePlacementControl() throws IOException {
		/*
		 * create this months file. Append to it the daily files data, delete
		 * the daily data files.
		 */
		File monthFile = new File(monthFolder + "/" + monthLogNamer());
		logger.info("monthFile name is : {}.", monthFile.getName());
		if (!monthFile.exists()) {
			monthFile.createNewFile();
			logger.info("New monthFile : monthFile Created");
		}
		int todayFileNr = Integer.parseInt(dateLogNamer());
		int k;
		for (File f : dailyFolder.listFiles()) {
			k = Integer.parseInt(f.getName());
			logger.info("k={}.  and f={}.", k, f);
			if (k != todayFileNr) {
				myAppend(f, monthFile);
				f.delete();
			}
		}
	}

	public void myAppend(File src, File target) throws IOException {
		/* copy file data from src to targe */
		try {
			FileWriter filew = new FileWriter(target, true);
			BufferedReader bffr = new BufferedReader(new FileReader(src));
			String line;

			filew.append("\n *****" + src.getName() + "\n");
			while ((line = bffr.readLine()) != null) {
				logger.info(line);
				filew.append(line + "\n");
			}
			filew.close();
			bffr.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public List<String> EAFileNames() {
		List<String> fileNames = new ArrayList<>();
		for (File fileEntry : monthFolder.listFiles()) {
			if (fileEntry.isFile()) {
				fileNames.add(fileEntry.getName());
				// System.out.println(fileEntry.getName());
			}
		}
		fileNames.add(dailyFolder.listFiles()[0].getName());
		return fileNames;
	}

	public String EAData(String fileName) throws IOException {
		// List<String> fileNames = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		String line;
		if (dailyFolder.listFiles()[0].getName().equals(fileName)) {
			BufferedReader bffr = new BufferedReader(new FileReader(dailyFolder
					+ "/" + fileName));
			while ((line = bffr.readLine()) != null) {
				// fileNames.add(line);
				sb.append(line);
				sb.append(System.getProperty("line.separator"));
			}
		} else {
			for (File fileEntry : monthFolder.listFiles()) {
				if (fileEntry.isFile()) {
					if (fileEntry.getName().equals(fileName)) {
						BufferedReader bffr = new BufferedReader(
								new FileReader(fileEntry));
						while ((line = bffr.readLine()) != null) {
							if (!line.isEmpty()||!line.contains("**"))
								// fileNames.add(line);
								sb.append(line);
							sb.append(System.getProperty("line.separator"));
						}
					}
				}
			}
		}// else

		return sb.toString();
	}

	public String dateLogNamer() {
		// String dailyFile = "/src/fileLog/allDaysLog/";
		// logger.info(dailyFile + new GregorianCalendar().get(Calendar.DATE) +
		// "");
		return new GregorianCalendar().get(Calendar.DATE) + "";
	}

	public String monthLogNamer() {
		// String monthFile = "/src/fileLog/allMonthLog/";
		GregorianCalendar cal = new GregorianCalendar();
		// logger.info((cal.get(Calendar.MONTH) + 1) + "_"
		// + cal.get(Calendar.YEAR));
		return (cal.get(Calendar.MONTH) + 1) + "_" + cal.get(Calendar.YEAR);
		// return null;
	}

}
