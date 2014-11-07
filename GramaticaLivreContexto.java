import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class GramaticaLivreContexto {
	int q; // numVariaveis
	int s; // numSimbolosTerminais
	int t; // numRegrasDeSubstituicao

	String[] variaveis;
	String[] simbolosTerminais;

	Map<String,List<String>> regrasDeSubstituicao = new HashMap<String,List<String>>();
	public GramaticaLivreContexto(String caminhoArquivo) {
		carregaEspecificacao(caminhoArquivo);
	}

	private void carregaEspecificacao(String caminhoArquivo) {
		try { 
			FileReader arq = new FileReader(caminhoArquivo); 
			BufferedReader lerArq = new BufferedReader(arq); 
			
			String[] linha0 = lerArq.readLine().split(" "); // q s t
			q = Integer.parseInt(linha0[0]);
			s = Integer.parseInt(linha0[1]);
			t = Integer.parseInt(linha0[2]);

			// Popula variaveis
			variaveis = lerArq.readLine().split(" ");
			
			// Popula simbolos terminais
			simbolosTerminais = lerArq.readLine().split(" ");			
			
			/*
			 * regrasDeSubstituicao
			 * TODO
			 */

			/*while (linha != null) { 
				System.out.printf("%s\n", linha); 
				linha = lerArq.readLine(); // lê da segunda até a última linha 
			}*/
			arq.close(); 
		} catch (IOException e) { 
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage()); 
		}
	}

}