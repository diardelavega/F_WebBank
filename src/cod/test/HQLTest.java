package cod.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import db.DBHandler;

public class HQLTest {
	private Session s = DBHandler.getSessionFactory().openSession();

	public void query(java.sql.Date sq1, java.sql.Date sq2) {
		System.out.println(sq1);
		System.out.println(sq2);
		Query q = s
				.createQuery(
						"SELECT day(trDate),"
								+ "sum(case when acction = 'WITHDRAW' then amount end )as withdraw, "
								+ "sum(case when acction = 'DEPOSITE' then amount end )as deposite "
								+ "FROM Transaction "
								// +"WHERE trData BETWEEN '2015-06-16' AND '2015-06-23' "
								+ "WHERE trData BETWEEN :fromDate AND :toDate  "
								+ "group by day(trDate) ")
				.setParameter("fromDate", sq1).setParameter("toDate", sq2);
		List<Object[]> ol = q.list();
		System.out.println(ol.size());

		for (Object[] row : ol) {
			System.out.println(row[0]);
			System.out.println(row[1]);
			System.out.println(row[2]);
		}
	}

	public String format(GregorianCalendar calendar) {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		// fmt.setCalendar(calendar);
		String dateFormatted = fmt.format(calendar.getTime());
		return dateFormatted;
	}

	public void upSession() {
		if (!s.isConnected() || !s.isOpen()) {
			s = DBHandler.getSessionFactory().openSession();
		}
	}

	public void closeSession() {
		try {
			s.flush();
			s.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
