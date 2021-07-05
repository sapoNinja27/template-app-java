package JObjects;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import Main.Main;

/**
 * Tabela
 * 
 * @apiNote Recebe uma lista de textos e os mostra na tela com uma tabela
 *          simples
 * @apiNote Configurações do objeto são feitas no init() da pagina
 */
public class ArvoreHabilidade {
	private int x, y, w, h;
	private List<String> text = new ArrayList<String>();
	private List<Integer> indexes = new ArrayList<Integer>();
	private List<Boolean> clicou = new ArrayList<Boolean>();
	private boolean mouseOver, clicouDentro;
	private int mx, my;
	private int selecionado;

	/**
	 * Cria uma tabela
	 * 
	 * @param x      : posição horizontal
	 * @param y      : posição vertical
	 * @param width  : tamanho horizontal
	 * @param height : tamanho vertical
	 */
	public ArvoreHabilidade(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	/**
	 * Retorna o texto da posição desejada
	 * 
	 * @param index : index da lista (numero tem que ser maior que 0)
	 * @apiNote : o ordenamento dos valores respeita a ordenação comum, então o
	 *          primeiro valor da tabela vai ser o index 1, e não o 0
	 */
	public String getTexto(int index) {
		return text.get(index - 1);
	}

	/**
	 * Retorna o id da posição indicada
	 * 
	 * @apiNote : Ideal para usar com tabelas de classes, onde o ordenamento por id
	 *          da classe vai ser diferente do ordenamento de listas da tabela
	 */
	public Integer getSelecionado() {
		return indexes.get(selecionado);
	}

	/**
	 * Adiciona um valor de texto a tabela
	 * 
	 * @param text : texto que vai aparecer na tabela
	 * @param id   : recebe um identificador para o objeto recebido
	 * @apiNote feito para adicionar manualmente os valores
	 */
	public void setHabilidade(String text, int id) {
		this.text.add(text);
		this.indexes.add(id);
		this.clicou.add(false);
	}

	/**
	 * Retorna se o mouse esta em cima da tabela
	 */
	public boolean mouseOver() {
		return mouseOver;
	}

	/**
	 * Configura qual obj da tabela foi selecionado
	 */
	public void clicou() {
		for (int i = 0; i < clicou.size(); i++) {
			if (clicou.get(i)) {
				selecionado = i;
			}
		}
	}

	/**
	 * Funções tick são chamadas indefinidamente durante a aplicação atualiza a
	 * posição do mouse em relação ao objeto
	 */
	public void tick() {
		clicou();
		mx = Main.menu.getMouseX();
		my = Main.menu.getMouseY();
		for (int i = 0; i < clicou.size(); i++) {
			if (Main.menu.pressionou()) {
				if (mx > x - 12 && mx < x + w && my > y - 9 + (i * 20) && my < y + (i * 20) + h) {
					clicouDentro = true;
				}
			}
			if (Main.menu.soltou()) {
				// soltou
				if (mx > x - 12 && mx < x + w && my > y + (i * 20) - 9 && my < y + (i * 20) + h) {
					// dentro
					if (clicouDentro) {
						clicouDentro = false;
						clicou.remove(i);
						clicou.add(i, true);
					}
				} else {
					clicou.remove(i);
					clicou.add(i, false);
				}

			}
		}

	}

	/**
	 * Renderiza o objeto
	 * 
	 * @param g : tipo grafico do java instanciado no main
	 */
	public void render(Graphics g) {
//		tick();
//		int borda = 1;
//		int maximo = 7;
//		if (text.size() < 7) {
//			maximo = text.size();
//		}
//		int dif=mx-x+10;
//		System.out.println(dif);
//		if (Main.menu.arrastando() ) {
////			x =mx;
////			y =my;
//		}
////		g.drawRect((x), y, 50, 50);
////		g.drawRect((x + 200), y, 50, 50);
//		for (int i = 0; i < text.size(); i++) {
//			if(indexes.get(i)==1) {
//				g.setColor(Color.black);
//				g.fillOval(x - borda, y - borda + (i * 20), w + borda * 2, h + borda * 2);
//				g.setColor(Color.white);
//				if (clicou.get(i)) {
//					g.setColor(Color.gray);
//				}
//				g.fillOval(x, y + (i * 20), w, h);
//				g.setColor(Color.black);
//				g.setFont(new Font("arial", Font.BOLD, 13));
//				g.drawString(text.get(i), x + 3, y + 13 + (i * 20));
//			}else if(indexes.get(i)==2) {
//				g.setColor(Color.black);
//				g.fillOval(x - borda+50, y - borda + (i * 20)-40, w + borda * 2, h + borda * 2);
//				g.setColor(Color.white);
//				if (clicou.get(i)) {
//					g.setColor(Color.gray);
//				}
//				g.fillOval(x+50, y + (i * 20)-40, w, h);
//				g.setColor(Color.black);
//				g.setFont(new Font("arial", Font.BOLD, 13));
//				g.drawString(text.get(i), x+50 + 3, y-40 + 13 + (i * 20));
//			}
//		}
		x=200-20;
		y=200;
		int tam=40;
		int topY=y;
		int botY=y;
		int redX=x-8;
		int redY=topY-9;
		
		int blueX=x+8;
		int blueY=topY-9;
		
		int yellowX=x;
		int yellowY=botY;
//		x=Main.menu.getMouseX();
//		y=Main.menu.getMouseY();		

		//sub tier 1
		for(int i=0;i<3;i++) {
			g.setColor(Color.red);
			g.fillOval(redX-tam/2-i*tam, redY-tam-i*tam, tam, tam);
			g.setColor(Color.black);
			if(i!=0) {
//				g.drawLine(redX-i*tam+tam, redY-tam/2-i*tam+tam,
//						redX-tam-i*tam+tam, redY-(int)(tam*1.5)-i*tam+tam);
			}
		}
		
		for(int i=0;i<3;i++) {
			g.setColor(Color.blue);
			g.fillOval(blueX+tam/2+i*tam, blueY -tam-i*tam, tam, tam);
			g.setColor(Color.black);
			if(i!=0) {
//				g.drawLine(blueX+tam+i*tam-tam, blueY-tam/2-i*tam+tam,
//						blueX+tam*2+i*tam-tam, blueY-(int)(tam*1.5)-i*tam+tam);
			}
		}
		
		for(int i=0;i<3;i++) {
			g.setColor(Color.yellow);
			g.fillOval(yellowX, yellowY+i*(int)(tam*1.5), tam, tam);
			g.setColor(Color.black);
			if(i!=0) {
//				g.drawLine(x+tam/2, y+tam/2+i*tam-tam,
//						x+tam/2, y+tam*2+i*tam-tam/2);
			}
		}
//		
		for(int i=0;i<3;i++) {
			g.setColor(Color.MAGENTA);
			g.fillOval(redX+(blueX-redX)/2, topY-tam*2-i*(int)(tam*1.5), tam, tam);
			g.setColor(Color.black);
			if(i!=0) {
				
			}
		}
		g.setColor(Color.orange);
		for(int i=0;i<3;i++) {
			g.fillOval(x-(int)(tam*1.5)-i*(int)(tam*1.5), y+i*(int)(tam*1.5), tam, tam);
		}
		g.setColor(Color.green);
		for(int i=0;i<3;i++) {
			g.fillOval(x+(int)(tam*1.5)+i*(int)(tam*1.5), y+i*(int)(tam*1.5), tam, tam);
		}
		//linhas
		g.setColor(Color.black);
//		//vermelho magenta
//		g.drawLine(redX, redY-tam/2,
//				x+tam/2, y-(int)(tam*1.5));
//		//vermelho-laranja
//		g.drawLine(redX, redY-tam/2,
//				x-tam, y+tam/2);
//		//azul-magenta
//		g.drawLine(blueX+tam, blueY-tam/2,
//				x+tam/2, y-(int)(tam*1.5));
//		//azul-verde
//		g.drawLine(blueX+tam, blueY-tam/2,
//				x+tam*2, y+tam/2);
//		//amarelo-laranja
//		g.drawLine(x+tam/2, y+tam/2,
//				x-tam, y+tam/2);		
//		//amarelo-verde
//		g.drawLine(x+tam/2, y+tam/2,
//				x+tam*2, y+tam/2);
//		g.setFont(new Font("arial", Font.BOLD, 13));

		g.setColor(Color.red);
		g.drawLine(200, 0,200, 400);	
		g.drawLine(0, 200,400, 200);

		g.setColor(Color.black);
		g.drawLine(0, 0,400, 400);
		g.drawLine(400, 0,0, 400);
		

		g.setColor(Color.blue);
		g.drawLine(100, 0,300, 400);
		g.drawLine(300, 0,100, 400);
		
		g.drawLine(0, 100,400, 300);
		g.drawLine(0, 300,400, 100);
		
	}

}
