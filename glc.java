public class glc {
	public static void main(String[] args) {
		// caminho do arquivo para especificacao da GLC
		GramaticaLivreContexto gramatica = new GramaticaLivreContexto(args[0]);
		
		// caminho do arquivo para as cadeias a serem avaliadas 
		Cadeia[] cadeias = Cadeia.getCadeias(args[1]);

		gramatica.processaCadeias(cadeias);
	}
}