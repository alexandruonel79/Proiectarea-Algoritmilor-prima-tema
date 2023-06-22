
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.util.StringTokenizer;

public class Feribot {
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

		public long nextLong() {
			return Long.parseLong(next());
		}
	}

	public static void main(String[] args) throws IOException {

		MyScanner in;
		in = new MyScanner(new FileReader("feribot.in"));

		int nrMasini = in.nextInt();
		long nrFeribot = in.nextLong();

		long[] masini = new long[nrMasini];
		long maxim = -1; // greutatea nu poate fi negativa
		long suma = 0;
		for (int i = 0; i < nrMasini; i++) {
			masini[i] = in.nextLong();
			suma += masini[i];
			// selectez masina cu greutate maxima
			if (maxim < masini[i]) {
				maxim = masini[i];
			}
		}
		// cautarea binara incepe de la cea mai grea masina
		long left = maxim;
		long right = suma;
		long solutie = Long.MAX_VALUE;

		while (left <= right) {
			long mid = (left + right) / 2;

			long nrFeribLocal = 1;
			long valFeribot = 0;
			long maximFeribLocal = 0;
			long checksum = 0;
			// parcurg toate masinile si testez cum se incadreaza
			// avand noua limita setata prin mid
			// incape maxim greutatea mid pe un feribot
			for (int j = 0; j < nrMasini; j++) {
				// vedem daca ne incadram adaugand noua masina
				if (valFeribot + masini[j] <= mid) {
					valFeribot += masini[j];
					checksum += masini[j];
				} else {
					j--;
					// salvam noua greutate maxima avand mid ca etalon
					if (valFeribot > maximFeribLocal) {
						maximFeribLocal = valFeribot;
					}

					valFeribot = 0;
					nrFeribLocal++;
					// limita e prea mica, o crestem
					if (nrFeribLocal > nrFeribot) {
						left = mid + 1;
						break;
					}
				}
			}
			if (nrFeribLocal <= nrFeribot) {
				right = mid - 1;
			}

			if (nrFeribLocal == nrFeribot) {
				left++;
			}

			// verific daca am o solutie mai buna decat cea curenta
			// verific corectitudinea ei prin checksum
			// adica verifica daca sunt toate masinile adaugate
			if (solutie > maximFeribLocal && checksum == suma) {
				solutie = maximFeribLocal;
			}

		}
		try (var printer = new PrintStream("feribot.out")) {
			printer.println(solutie);
		}
	}
}
