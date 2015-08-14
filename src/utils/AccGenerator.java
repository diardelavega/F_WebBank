package utils;

import java.util.Random;

public class AccGenerator {
	// accNr varchar(15) 8*(number)+5*(letter) +2*(number)
	private StringBuilder sb;
	private Random rand = new Random();

	public String getAccountNr() {
		sb = new StringBuilder();
		p1Generate();
		p2Generate();
		p3Generate();
		//TODO check if acc already exists
		return sb.toString();
	}

	private void p1Generate() {
		int k;
		for (int i = 0; i < 4; i++) {
			k = rand.nextInt(100);
			if (k < 10) {
				sb.append("0" + k);
			} else {
				sb.append(k);
			}
		}// for
	}

	private void p2Generate() {
		for (int i = 0; i < 5; i++) {
			char c = (char) (rand.nextInt(26) + 'A');
			sb.append(c);
		}
	}

	private void p3Generate() {
		sb.append(rand.nextInt(89) + 10);
	}

}
