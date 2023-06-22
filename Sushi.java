import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Sushi {
	// clasa salveaza o matrice3d si are
	// 2 functii prin care se pot seta valorile
	// si extrage
	// se pot generaliza mai multe dimensiuni
	// daca adaugam mai multe conditii
	// putem prelucra datele printr o metoda
	// daca este util pentru problema
	public static class Matrice3d {
		int[][][] matrice;
		int nrPlatouri;
		int baniMaxim;
		int nrPrieteni;

		public Matrice3d(int nrPlatouri, int baniMaxim, int nrPrieteni) {
			this.nrPlatouri = nrPlatouri;
			this.baniMaxim = baniMaxim;
			this.nrPrieteni = nrPrieteni;
		}

		public void initializare() {
			matrice = new int[nrPlatouri][baniMaxim][nrPrieteni];
		}

		public int obtineVal(int[][][] mat, int x, int y, int z) {
			return mat[x][y][z];
		}

		public void setVal(int[][][] mat, int x, int y, int z, int val) {
			mat[x][y][z] = val;
		}
	}

	// o clasa care stocheaza un Platou definit
	// de perechea pret si sumaNotelor sale
	public static class Platou {
		int pret;
		int sumaNote;

		public Platou(int pret, int sumaNote) {
			this.pret = pret;
			this.sumaNote = sumaNote;
		}
	}

	// aceasta functie aplica knapsack simplu
	static int knapsack(int nrPlatouri, int baniMaxim, Platou[] platouri, int cerinta) {
		int[][] mat;
		// dimensiunea matricei este variabila in functie de
		// cerinta exercitiului
		// pentru cerinta 2 am dublat nr de platouri
		// adica liniile pentru a efectua de 2 ori
		// acelasi algoritm
		if (cerinta == 1) {
			mat = new int[nrPlatouri + 1][baniMaxim + 1];
		} else {
			mat = new int[2 * nrPlatouri + 2][baniMaxim + 1];
			nrPlatouri *= 2;
		}
		// knapsack laborator
		for (int i = 1; i <= nrPlatouri; i++) {
			for (int j = 1; j <= baniMaxim; j++) {
				// nu folosec obiectul i => e solutia de la pasul
				// anterior
				mat[i][j] = mat[i - 1][j];
				// daca folosim trebuie rezervat spatiul
				// trebuie sa ocup maxim j-platouri[i].pret
				if (j - platouri[i].pret >= 0) {
					int sol_aux = platouri[i].sumaNote + mat[i - 1][j - platouri[i].pret];
					mat[i][j] = max(sol_aux, mat[i - 1][j]);
				}
			}
		}
		return mat[nrPlatouri][baniMaxim];
	}

	// functie care se ocupa special de cerinta 3
	// identica cu knapsack pentru cerinta 2
	// am adauga inca un for pentru a verifica si
	// nr de platouri adaugate sa nu depaseasca limita
	static int knapsack3d(int nrPlatouri, int baniMaxim, Platou[] platouri,
			int nrPrieteni, Matrice3d matrice3d) {
		for (int i = 1; i <= 2 * nrPlatouri; i++) {
			for (int j = 1; j <= baniMaxim; j++) {
				for (int h = 1; h <= nrPrieteni; h++) {
					// nu folosec obiectul i => e solutia de la pasul
					// anterior
					matrice3d.setVal(matrice3d.matrice, i, j, h,
							matrice3d.obtineVal(matrice3d.matrice, i - 1, j, h));
					// daca folosim trebuie rezervat spatiul
					// trebuie sa ocup maxim j-platouri[i].pret
					if (j - platouri[i - 1].pret >= 0) {
						int sol_aux = platouri[i - 1].sumaNote
								+ matrice3d.obtineVal(matrice3d.matrice, i - 1, j
										- platouri[i - 1].pret, h - 1);
						matrice3d.setVal(matrice3d.matrice, i, j, h,
								max(sol_aux, matrice3d.obtineVal(matrice3d.matrice, i - 1, j, h)));
					}
				}
			}
		}
		return matrice3d.obtineVal(matrice3d.matrice, 2 * nrPlatouri, baniMaxim, nrPrieteni);
	}

	// returneaza maximul, am avut o problema cu Math.max
	private static int max(int sol_aux, int i) {
		if (sol_aux > i) {
			return sol_aux;
		} else {
			return i;
		}
	}

	// functia citeste preturile platourilor in functie de
	// numarul cerintei
	static Platou[] citirePreturi(int nrPlatouri, int cerinta, Scanner in) {
		Platou[] platouri;
		// pentru cerinta 1 le citim normal
		// in schimb pentru cerinta 2 si 3 am dublat platourile
		if (cerinta == 1) {
			platouri = new Platou[nrPlatouri + 1];
			platouri[0] = new Platou(0, 0);

			for (int i = 1; i < nrPlatouri + 1; i++) {
				Platou platou = new Platou(in.nextInt(), 0);
				platouri[i] = platou;
			}
		} else {
			platouri = new Platou[2 * nrPlatouri + 2];

			platouri[0] = new Platou(0, 0);
			platouri[1] = new Platou(0, 0);
			int contor = 2;
			// pentru cerinta 2 am initializat primele 2
			// platouri cu 0
			// pentru cerinta 3 nu este nevoie
			if (cerinta == 3) {
				contor = 0;
			}

			for (int i = 1; i < nrPlatouri + 1; i++) {
				Platou platou = new Platou(in.nextInt(), 0);
				platouri[contor] = platou;
				platouri[contor + 1] = new Platou(platou.pret, 0);
				contor += 2;
			}
		}

		in.nextLine();

		return platouri;
	}

	// functia citeste toate notele platourilor in functie de cerinta
	static Platou[] citireNote(int nrPrieteni, int nrPlatouri, Platou[] platouri,
			Scanner in, int cerinta) {
		// pentru prima cerinta se citesc in ordine
		if (cerinta == 1) {
			for (int i = 0; i < nrPrieteni; i++) {
				// citesc toata linia
				String citire = in.nextLine();
				// separ cu un split
				String[] arrOfStr = citire.split(" ");
				for (int j = 1; j <= nrPlatouri; j++) {
					platouri[j].sumaNote += Integer.parseInt(arrOfStr[j - 1]);
				}
			}
		} else {
			int contor;

			for (int i = 0; i < nrPrieteni; i++) {
				// citesc toata linia
				String citire = in.nextLine();
				// separ cu un split
				String[] arrOfStr = citire.split(" ");
				// daca e cerinta 3 nu fac sumaNote pentru
				// platou 0 si 1 egala cu 0
				if (cerinta == 3) {
					contor = 0;
				} else {
					contor = 2;
				}
				for (int j = 1; j <= nrPlatouri; j++) {
					platouri[contor].sumaNote += Integer.parseInt(arrOfStr[j - 1]);
					platouri[contor + 1].sumaNote += Integer.parseInt(arrOfStr[j - 1]);
					contor += 2;
				}
			}
		}
		return platouri;
	}

	public static void main(String[] args) throws IOException {
		Scanner in = new Scanner(new File("sushi.in"));

		int cerinta = in.nextInt();
		int nrPrieteni = in.nextInt();
		int nrPlatouri = in.nextInt();
		int bani = in.nextInt();

		int baniMaxim = nrPrieteni * bani;

		if (cerinta == 1 || cerinta == 2) {
			Platou[] platouri;
			// citesc preturile platourilor
			platouri = citirePreturi(nrPlatouri, cerinta, in);
			// citesc suma notelor platourilor
			citireNote(nrPrieteni, nrPlatouri, platouri, in, cerinta);

			FileWriter fw = new FileWriter("sushi.out");
			// apelez functia knapsack care in functie de cerinta
			// seteaza lungimea matricei si scriu rezultatul
			fw.write(Integer.toString(knapsack(nrPlatouri, baniMaxim, platouri, cerinta)) + '\n');
			fw.close();

		}
		if (cerinta == 3) {
			// citesc preturile platourilor
			Platou[] platouri = citirePreturi(nrPlatouri, cerinta, in);
			// citesc suma notelor platourilor
			citireNote(nrPrieteni, nrPlatouri, platouri, in, cerinta);
			// declar si initializez un obiect Matrice3d
			Matrice3d matrice3d = new Matrice3d(2 * nrPlatouri + 2, baniMaxim + 1,
					nrPrieteni + 1);
			matrice3d.initializare();

			FileWriter fw = new FileWriter("sushi.out");
			// apelez knapsackul special pentru cerinta 3
			fw.write(Integer.toString(knapsack3d(nrPlatouri, baniMaxim, platouri,
					nrPrieteni, matrice3d)) + '\n');
			fw.close();
		}

	}
}