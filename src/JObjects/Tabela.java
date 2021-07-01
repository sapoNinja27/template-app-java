package JObjects;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
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
public class Tabela {
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
	public Tabela(int x, int y, int w, int h) {
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
	public void setTexto(String text, int id) {
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
		tick();
		int borda = 1;
		int maximo = 7;
		if (text.size() < 7) {
			maximo = text.size();
		}
		for (int i = 0; i < maximo; i++) {
			g.setColor(Color.black);
			g.fillRect(x - borda, y - borda + (i * 20), w + borda * 2, h + borda * 2);
			g.setColor(Color.white);
			if (clicou.get(i)) {
				g.setColor(Color.gray);
			}
			g.fillRect(x, y + (i * 20), w, h);
			g.setColor(Color.black);
			g.setFont(new Font("arial", Font.BOLD, 13));
			g.drawString(text.get(i), x + 3, y + 13 + (i * 20));
		}

	}

}
