

public class Main {
	public static void main(String[] args) { 
        Tabuleiro b = new Tabuleiro();
       

        b.mostraTabuleiro();

        System.out.println("Voce joga primeiro (linha0-2 coluna0-2): ");
     
        

        while (!b.fimDeJogo()) {
            System.out.println("Sua vez: ");
            Ponto userMove = new Ponto(b.scan.nextInt(), b.scan.nextInt());

            b.movimento(userMove, 'X'); 
            b.mostraTabuleiro();
            if (b.fimDeJogo()) break;
            
            b.alphaBetaMinimax(Integer.MIN_VALUE, Integer.MAX_VALUE, 0, 1);
            for (Pontos_Filhos pas : b.pontuacaoFilhos) 
                System.out.println("Point: " + pas.ponto + " Score: " + pas.pontuacao);
            
            b.movimento(b.melhorMovimento(), 'O');
            b.mostraTabuleiro();
        }
        if (b.Ovenceu()) {
            System.out.println("Você perdeu!");
        } else if (b.Xvenceu()) {
            System.out.println("Você venceu");
        } else {
            System.out.println("Velha!");
        }
    }
}
