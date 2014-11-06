public class glc {
	public static void main(String[] args) {
		// caminho do arquivo para especificacao da GLC
		GramaticaLivreContexto gramatica = new GramaticaLivreContexto(args[0]);
		
		// caminho do arquivo para as cadeias a serem avaliadas 
		Cadeias cadeias = new Cadeias(args[1]);
	}
}