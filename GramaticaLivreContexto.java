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

					//System.out.println(novaListaDeRegras);
				}

				linha = lerArq.readLine(); // lê da segunda até a última linha 
			}
			arq.close(); 
		} catch (IOException e) { 
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage()); 
		}
	}

	public void processaCadeias(Cadeia[] cadeias) {
		// TODO - Criar dois arquivos um para aceitar outro para ir ja preenchendo tabela
		for (Cadeia cadeia : cadeias) {
			if(cadeia.toString().equals("&") && vazioComoRegra()) {
				// Aceita

				continue;
			}

			//Tabela nxn, onde n = tamCadeia
			HashMap<Chave, ArrayList<String>> tabela = new HashMap<Chave, ArrayList<String>>();

			this.preencheTabela(tabela, cadeia);
		}
	}


	private void preencheTabela(HashMap<Chave, ArrayList<String>> tabela, Cadeia cadeia)
	{
		String[] caracteres = cadeia.getCaracteres();
		int tamCadeia = caracteres.length;

		//Preenche diagonal principal
		for(int i = 1; i <= tamCadeia; i++)
		{
			//Percorre regras
			for(String key : regrasDeSubstituicao.keySet())
			{
				if(regrasDeSubstituicao.get(key).contains(caracteres[i-1]))
				{
					ArrayList<String> lista = tabela.get(new Chave(i,i));
					if(lista == null)
						lista = new ArrayList<String>();
					lista.add(key);
				}
			}
		}

		this.processaRegrasABC(tabela, tamCadeia);
		
	}

	private void processaRegrasABC(HashMap<Chave, ArrayList<String>> tabela, int tamCadeia)
	{


		for(int l = 2; l < tamCadeia - l + 1; l++) //l = cumprimento da subcadeia
		{
			for(int i = 1; i < tamCadeia - l + 1; i++ ) //i = posicao inicial da subcadeia
			{
				int j = i + l - 1; //j = posicao final da subcadeia
				for(int k = i; k <= j-1; k++) //k = posicao de divisao
				{
					//Cada regra A -> BC
					for(String key : regrasDeSubstituicao.keySet())
					{
						for(String regra : regrasDeSubstituicao.get(key))
						{
							String[] variaveis = regra.split(" ");
							if(variaveis.length == 2)
							{	
								String A = variaveis[0];
								String B = variaveis[1];
								//System.out.println(key + "->" + A + B);

								ArrayList<String> tabelaIK = tabela.get(new Chave(i,k));
								ArrayList<String> tabelaKmais1J = tabela.get(new Chave(k+1, j));
								if(tabelaIK == null || tabelaKmais1J == null) continue;

								//Se tabela(i,k) contem B e tabela(k+1, j) contem C, ponha A em tabela(i,j)
								if(tabelaIK.contains(A) && tabelaKmais1J.contains(B))
								{
									ArrayList<String> tabelaIJ = tabela.get(new Chave(i,j));
									if(tabelaIJ == null)
										tabelaIJ = new ArrayList<String>();
									tabelaIJ.add(A);
								}
									 
							}
						}
					}		
				}
			}
		}
	}

	public boolean vazioComoRegra() {
		for (String key : regrasDeSubstituicao.keySet()) {
			if (regrasDeSubstituicao.get(key).contains("&"))
				return true;
		}
		return false;
	}
}

//Define um conjunto de chaves x e y
class Chave
{
	private final int x;
	private final int y;

	Chave(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	public boolean equals(Object o)
	{
		if(! (o instanceof Chave)) return false;

		Chave ch = (Chave) o;
		return ch.x == this.x && ch.y == this.y;
	}

	public int hashCode()
	{
		return x*x+y*y; 
	}

}