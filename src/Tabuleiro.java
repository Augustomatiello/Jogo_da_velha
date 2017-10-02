import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Tabuleiro {
	
	List<Ponto> pontosDisponiveis;
	Scanner scan = new Scanner(System.in);
	char [][] tabuleiro = new char [3][3];
	
	List<Pontos_Filhos> pontuacaoFilhos = new ArrayList <Pontos_Filhos>();
	
	int profundidadeAlphaBeta = 2;
	
	Tabuleiro(){
		for(int i = 0; i < 3;i ++){
			for(int j= 0; j <3; j++){
				tabuleiro[i][j] = '-';
			}
		}
	}
	
	public int avaliaTabuleiro(){
		int pontuacao = 0;

        for (int i = 0; i < 3; ++i) {
            int disponivel = 0;
            int X = 0;
            int O = 0;
            for (int j = 0; j < 3; ++j) {
                if (tabuleiro[i][j] == '-') {
                    disponivel++;
                } else if (tabuleiro[i][j] == 'O') {
                    O++;
                } else {
                    X++;
                }

            } 
            pontuacao+=atualizaPontuacao(X, O); 
        }

        for (int j = 0; j < 3; ++j) {
            int diaponivel = 0;
            int X = 0;
            int O = 0;
            for (int i = 0; i < 3; ++i) {
                if (tabuleiro[i][j] == '-') {
                    diaponivel++;
                } else if (tabuleiro[i][j] == 'O') {
                    O++;
                } else {
                    X++;
                } 
            }
            pontuacao+=atualizaPontuacao(X, O);
        }

        int disponivel = 0;
        int X = 0;
        int O = 0;

        for (int i = 0, j = 0; i < 3; ++i, ++j) {
            if (tabuleiro[i][j] == 'O') {
                O++;
            } else if (tabuleiro[i][j] == 'X') {
                X++;
            } else {
                disponivel++;
            }
        }

        pontuacao+=atualizaPontuacao(X, O);

        disponivel = 0;
        X = 0;
        O = 0;
        
        for (int i = 2, j = 0; i > -1; --i, ++j) {
            if (tabuleiro[i][j] == 'O') {
                O++;
            } else if (tabuleiro[i][j] == 'X') {
                X++;
            } else {
                disponivel++;
            }
        }

        pontuacao+=atualizaPontuacao(X, O);

        return pontuacao;
	}

	private int atualizaPontuacao(int x, int o) {
		
		int pontuacao;
		if (x == 3) {
            pontuacao = -100;
        } else if (x == 2 && o == 0) {
            pontuacao = -10;
        } else if (x == 1 && o == 0) {
            pontuacao = -1;
        } else if (o == 3) {
            pontuacao = 100;
        } else if (o == 2 && x == 0) {
            pontuacao = 10;
        } else if (o == 1 && x == 0) {
            pontuacao = 1;
        } else {
            pontuacao = 0;
        } 
		return pontuacao;
	}
	
	public int alphaBetaMinimax(int alpha, int beta, int profundidade, int turno){
	        
	        if(beta<=alpha){ 
	        	if(turno == 1) return Integer.MAX_VALUE; 
	        	else return Integer.MIN_VALUE; 
	        }
	        
	        if(profundidade == profundidadeAlphaBeta || fimDeJogo()) 
	        	return avaliaTabuleiro();
	        
	        List<Ponto> pontosLivres = estadosLivres();
	        
	        if(pontosLivres.isEmpty()) return 0;
	        
	        if(profundidade==0) pontuacaoFilhos.clear(); 
	        
	        int maxValue = Integer.MIN_VALUE, minValue = Integer.MAX_VALUE;
	        
	        for(int i=0;i<pontosLivres.size(); ++i){
	            Ponto point = pontosLivres.get(i);
	            
	            int currentScore = 0;
	            
	            if(turno == 1){
	                movimento(point, 'O'); 
	                currentScore = alphaBetaMinimax(alpha, beta, profundidade+1, 2);
	                maxValue = Math.max(maxValue, currentScore); 
	                
	                //Set alpha
	                alpha = Math.max(currentScore, alpha);
	                
	                if(profundidade == 0)
	                    pontuacaoFilhos.add(new Pontos_Filhos(currentScore, point));
	            }else if(turno == 2){
	                movimento(point, 'X');
	                currentScore = alphaBetaMinimax(alpha, beta, profundidade+1, 1); 
	                minValue = Math.min(minValue, currentScore);
	                
	            
	                beta = Math.min(currentScore, beta);
	            }
	           
	            tabuleiro[point.x][point.y] = '-'; 
	            
	          
	            if(currentScore == Integer.MAX_VALUE || currentScore == Integer.MIN_VALUE) break;
	        }
	        return turno == 1 ? maxValue : minValue;
	    }

	public boolean fimDeJogo() {
		return (Xvenceu() || Ovenceu() || estadosLivres().isEmpty());
	}

	public boolean Ovenceu() {
		if ((tabuleiro[0][0] == tabuleiro[1][1] && tabuleiro[0][0] == tabuleiro[2][2] && tabuleiro[0][0] == 'O') || (tabuleiro[0][2] == tabuleiro[1][1] && tabuleiro[0][2] == tabuleiro[2][0] && tabuleiro[0][2] == 'O')) {
            return true;
        }
        for (int i = 0; i < 3; ++i) {
            if (((tabuleiro[i][0] == tabuleiro[i][1] && tabuleiro[i][0] == tabuleiro[i][2] && tabuleiro[i][0] == 'O')
                    || (tabuleiro[0][i] == tabuleiro[1][i] && tabuleiro[0][i] == tabuleiro[2][i] && tabuleiro[0][i] == 'O'))) {
               
                return true;
            }
        }
        return false;
	}

	public boolean Xvenceu() {
		if ((tabuleiro[0][0] == tabuleiro[1][1] && tabuleiro[0][0] == tabuleiro[2][2] && tabuleiro[0][0] == 'X') || (tabuleiro[0][2] == tabuleiro[1][1] && tabuleiro[0][2] == tabuleiro[2][0] && tabuleiro[0][2] == 'X')) {
            return true;
        }
        for (int i = 0; i < 3; ++i) {
            if (((tabuleiro[i][0] == tabuleiro[i][1] && tabuleiro[i][0] == tabuleiro[i][2] && tabuleiro[i][0] == 'X')
                    || (tabuleiro[0][i] == tabuleiro[1][i] && tabuleiro[0][i] == tabuleiro[2][i] && tabuleiro[0][i] == 'X'))) {
                
                return true;
            }
        }
        return false;
	}

	private List<Ponto> estadosLivres() {
		pontosDisponiveis = new ArrayList<>();
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                if (tabuleiro[i][j] == '-') {
                    pontosDisponiveis.add(new Ponto(i, j));
                }
            }
        }
        return pontosDisponiveis;
	}

	public void movimento(Ponto ponto, char i) {
		tabuleiro[ponto.x][ponto.y] = i;
	} 
	
	public Ponto melhorMovimento() {
        int MAX = -100000;
        int best = -1;

        for (int i = 0; i < pontuacaoFilhos.size(); i++) {
            if (MAX < pontuacaoFilhos.get(i).pontuacao) {
                MAX = pontuacaoFilhos.get(i).pontuacao;
                best = i;
            }
        }

        return pontuacaoFilhos.get(best).ponto;
    }


    public void mostraTabuleiro() {
        System.out.println();

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                System.out.print(tabuleiro[i][j] + " ");
            }
            System.out.println();

        }
    } 
    
}
