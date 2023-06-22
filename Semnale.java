import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.util.StringTokenizer;

public class Semnale {
	/**
	 * A class for buffering read operations, inspired from here:
	 * https://pastebin.com/XGUjEyMN.
	 */
	private static class MyScanner {
		private BufferedReader br;
		private StringTokenizer st;

		public MyScanner(Reader reader) {
			br = new BufferedReader(reader);
		}

		public String next() {
			while (st == null || !st.hasMoreElements()) {
				try {
					st = new StringTokenizer(br.readLine());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return st.nextToken();
		}

		public int nextInt() {
			return Integer.parseInt(next());
		}
	}

	public static final int MOD = 1000000007;

	public static void main(String[] args) throws IOException {
		MyScanner in;
		in = new MyScanner(new FileReader("semnale.in"));

		final int cerinta = in.nextInt();
		int nrZero = in.nextInt();
		int nrUnu = in.nextInt();

		long[][] mat = new long[nrZero + 1][nrUnu + 1];
		// prima linie va fi 1 deoarece cu niciun zero
		// poti face maxim 1
		for (int i = 0; i <= nrUnu; i++) {
			mat[0][i] = 1;
		}
		// prima coloana va fi 1 deoarece cu 0 de unu
		// poti face maxim 1
		for (int i = 0; i <= nrZero; i++) {
			mat[i][0] = 1;
		}
		// daca nu am niciun zero si niciun unu
		// returnez 0
		mat[0][0] = 0;
		// linia 2 va fi egala cu 1
		// [2,2] va fi suprascris ulterior
		for (int i = 1; i <= nrUnu; i++) {
			mat[1][i] = 1;
		}

		if (cerinta == 1) {
			if (nrZero == 1) {
				try (var printer = new PrintStream("semnale.out")) {
					printer.println("0");
				}
				return;
			}
			// recurenta este dedusa prin observatie
			// pentru egalitatea i,j este i+1
			// pentru j>i este j
			// altfel este suma celui de deasupra
			// impreuna cu cel de deasupra mai in stanga cu o
			// pozitie
			for (int i = 1; i <= nrZero; i++) {
				for (int j = 1; j <= nrUnu; j++) {
					if (i == j) {
						mat[i][j] = i + 1;
					} else {
						if (j > i) {
							mat[i][j] = j;
						} else {
							// am aplicat proprietatea din laborator
							mat[i][j] = (mat[i - 1][j] % MOD + mat[i - 1][j - 1] % MOD) % MOD;
						}
					}
				}
			}
			try (var printer = new PrintStream("semnale.out")) {
				printer.println(mat[nrZero][nrUnu] % MOD);
			}
		} else {
			for (int i = 1; i <= nrZero; i++) {
				// save salveaza mijlocul de la care incepe
				// simetria matricei
				// prima aparitie este la pozitia 2
				int save = 2;
				for (int j = 1; j <= nrUnu; j++) {
					// pana la 3 adaug elementul anterior plus
					// elementul de deasupra
					if (j < 3) {
						mat[i][j] = (mat[i - 1][j] % MOD + mat[i][j - 1] % MOD) % MOD;
						continue;
					}
					// de la 3 si pana unde intervine simetria
					// adica pana la "mijloc" este aceiasi formula
					// ca la cerinta 1 dar mai scadem cu pe cel de
					// deasupra si mai in spate cu 3 pozitii
					if (j >= 3 && j <= i + 1) {
						if (j - 3 >= 0) {
							// aplicam proprietatea din laborator pentru diferenta
							mat[i][j] = ((mat[i - 1][j] % MOD + mat[i][j - 1] % MOD) % MOD
									- mat[i - 1][j - 3] % MOD
									+ MOD) % MOD;
						} else {
							mat[i][j] = (mat[i - 1][j] % MOD + mat[i][j - 1] % MOD) % MOD;
						}
						save = j;
						continue;
					}
					if (j > i + 1) {
						// ne folosim de simetra matricei observata
						// completam cu valorile deja calculate
						if (2 * save - j >= 0) {
							mat[i][j] = mat[i][2 * save - j];
						} else {
							mat[i][j] = 0;
						}
					}

				}
			}

			try (var printer = new PrintStream("semnale.out")) {
				printer.println(mat[nrZero][nrUnu] % MOD);
			}
		}
	}
}
