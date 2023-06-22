/*
 * Acest schelet citește datele de intrare și scrie răspunsul generat de voi,
 * astfel că e suficient să completați cele două metode.
 *
 * Scheletul este doar un punct de plecare, îl puteți modifica oricum doriți.
 *
 * Dacă păstrați scheletul, nu uitați să redenumiți clasa și fișierul.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Nostory {
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

	public static void main(final String[] args) throws IOException {
		var in = new MyScanner(new FileReader("nostory.in"));

		int cerinta = in.nextInt();

		int n = in.nextInt();
		// am citit elementele in acelasi array
		if (cerinta == 1) {
			long[] valori = new long[2 * n];

			for (int i = 0; i < 2 * n; i++) {
				valori[i] = in.nextLong();
			}
			// am sortat descrescator elementele
			Arrays.sort(valori);
			long rasp = 0;
			// am luat primele n elemente
			// acestea sunt maximele din sir
			for (int i = 2 * n - 1; i > n - 1; i--) {
				rasp += valori[i];
			}

			try (var printer = new PrintStream("nostory.out")) {
				printer.println(rasp);
			}
		} else {
			if (cerinta == 2) {
				final int limConst = in.nextInt();

				long[] v1 = new long[n];
				long[] v2 = new long[n];
				// aux contine v1 reunit cu v2
				long[] aux = new long[2 * n];

				for (int i = 0; i < n; i++) {
					v1[i] = in.nextLong();
					aux[i] = v1[i];
				}

				for (int i = 0; i < n; i++) {
					v2[i] = in.nextLong();
					aux[i + n] = v2[i];
				}
				// am determinat elementele maxime din sirul format
				// din v1 reunit cu v2
				Arrays.sort(aux);
				// am salvat maximele dintre cele 2 siruri initiale
				long[] before = new long[n];

				for (int i = 0; i < n; i++) {
					before[i] = Math.max(v1[i], v2[i]);
				}

				// am sortat descrescator sirul
				Arrays.sort(before);

				long rezultat = 0;
				int limita = limConst;
				// primele elemente n-limita raman neschimbate
				for (int i = n - 1; i > n - 1 - (n - limita); i--) {
					rezultat += before[i];
				}

				// contorul fiecarui vector
				int cntBefore = n;
				int cntAux = 2 * n;
				// element cu care interschimb valorile
				long swapper = -1;
				int smolCnt = 0;
				for (int j = 0; j < n; j++) {
					// cat timp mai am interschimbari
					if (limita > 0) {
						// daca valoarea din aux e mai mare decat cea
						// din before, o preiau
						if (aux[cntAux - 1] > before[cntBefore - 1]) {
							swapper = aux[cntAux - 1];
							cntAux--;
							// inlocuim cu valorile mici din before
							before[smolCnt] = swapper;
							smolCnt++;
							limita--;
						} else {
							cntAux--;
							cntBefore--;
						}
					}
				}
				// adaugam la suma restul de elemente
				for (int i = 0; i < limConst; i++) {
					rezultat += before[i];
				}
				try (var printer = new PrintStream("nostory.out")) {
					printer.println(rezultat);
				}
			}
		}
	}
}
