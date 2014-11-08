import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

// Map
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

// Regex
import java.util.regex.Pattern;
import java.util.regex.Matcher;

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
			
			// Carregando regras de substituicao
			String linha = lerArq.readLine();
			while (linha != null) {
				
				// Procura com regex padrao do arquivo
				Pattern pattern = Pattern.compile("(.*)( > )(.*)");
				Matcher matcher = pattern.matcher(linha);
				matcher.matches();
				
				// Verifica se ja possuia uma regra ou mais para aquela variavel
				List<String> regras = regrasDeSubstituicao.get(matcher.group(1));
				if (regras != null) { // se ja possui apenas adiciona
					regras.add(matcher.group(3));
				} else { // se nao estava na lista cria nova lista
					List<String> novaListaDeRegras = new ArrayList<String>();
					novaListaDeRegras.add(matcher.group(3));
					regrasDeSubstituicao.put(matcher.group(1), novaListaDeRegras);
				}

				linha = lerArq.readLine(); // lê da segunda até a última linha 
			}
			arq.close(); 
		} catch (IOException e) { 
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage()); 
		}
	}

	public void processaCadeias(Cadeia[] cadeias) {
		// TODO
	}
}