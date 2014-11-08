import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Cadeia {
	
	// Tabela a ser preenchida pelo algoritmo
	private String[][] tabela;

	private String cadeia;

	// Estudar possibilidade de colocar como char
	private String[] caracteres;

	public Cadeia(String cadeia) {
		this.cadeia = cadeia;
		this.caracteres = cadeia.split(" ");
	}

	public String toString() {
		return cadeia;
	}

	public String[] getCaracteres() {
		return caracteres;
	}

	public static Cadeia[] getCadeias(String caminhoArquivoCadeias) {
		Cadeia[] cadeias = null;

		try { 
			FileReader arq = new FileReader(caminhoArquivoCadeias); 
			BufferedReader lerArq = new BufferedReader(arq); 
			
			// Captura numero de cadeias e instancia vetor de cadeias
			int numeroCadeias = Integer.parseInt(lerArq.readLine());
			cadeias = new Cadeia[numeroCadeias];

			// Percorre cada linha seguinte populando array de cadeias
			String linha = lerArq.readLine();
			for (int i = 0; i<numeroCadeias; i++) {
				cadeias[i] = new Cadeia(linha);
				linha = lerArq.readLine();
			}

			arq.close(); 
		} catch (IOException e) { 
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage()); 
		}

		return cadeias;
	}
}