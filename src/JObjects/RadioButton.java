package JObjects;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import Main.Main;

/**
 * Botão de radio
 * 
 * @apiNote Recebe uma lista de textos e os mostra na tela com um botão para selecionar, apenas um pode ser selecionado por vez
 * @apiNote Configurações do objeto são feitas no init() da pagina
 */
public class RadioButton {
	private List<Integer> x = new ArrayList<Integer>(), y = new ArrayList<Integer>();
	private List<String> text = new ArrayList<String>();
	private List<Integer> indexes = new ArrayList<Integer>();
	private List<Boolean> clicou = new ArrayList<Boolean>();
	private boolean mouseOver, clicouDentro;
	private int mx, my;
	private int selecionado;

	/**
	 * Cria um objeto vazio
	 */
	public RadioButton() {

	}

	/**
	 * Retorna o texto da posição desejada
	 * 
	 * @param index : index da lista (numero tem que ser maior que 0)
	 * @apiNote : o ordenamento dos valores respeita a ordenação comum, então o
	 *          primeiro valor vai ser o index 1, e não o 0
	 */
	public String getTexto(int index) {
		return text.get(index - 1);
	}

	/**
	 * Retorna o texto selecionado
	 */
	public String getTexto() {
		return text.get(selecionado);
	}

	/**
	 * Retorna o id da posição indicada
	 */
	public Integer getSelecionado() {
		return indexes.get(selecionado);
	}

	/**
	 * Adiciona um valor de texto
	 * 
	 * @param text texto 
	 * @param id   recebe um identificador para o objeto recebido
	 * @param x    recebe a posição x do objeto
	 * @param y    recebe a posição y do objeto
	 */
	public void setTexto(String text, int id, int x, int y) {
		this.text.add(text);
		this.indexes.add(id);
		this.x.add(x);
		this.y.add(y);
		if (clicou.size() == 0) {
			this.clicou.add(true);
		} else {
			this.clicou.add(false);
		}
	}

	/**
	 * Retorna se o mouse esta em cima de um dos objetos
	 */
	public boolean mouseOver() {
		return mouseOver;
	}

	/**
	 * Configura qual foi selecionado
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
				if (mx > x.get(i) - 20 && mx < x.get(i) + 50 && my > y.get(i) - 15 + (i * 20)
						&& my < y.get(i) + (i * 20) + 10) {
					clicouDentro = true;
				}
			}
			if (Main.menu.soltou()) {
				// soltou
				if (mx > x.get(i) - 20 && mx < x.get(i) + 50 && my > y.get(i) - 15 + (i * 20)
						&& my < y.get(i) + (i * 20) + 10) {
					// dentro
					if (clicouDentro) {
						clicouDentro = false;
						clicou.remove(i);
						clicou.add(i, true);
						for (int c = 0; c < clicou.size(); c++) {
							if (c != i) {
								clicou.remove(c);
								clicou.add(c, false);
							}
						}
					}
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
		int maximo = 6;
		int tam = 7;
		if (text.size() < 6) {
			maximo = text.size();
		}
		for (int i = 0; i < maximo; i++) {
			Graphics2D g2 = (Graphics2D) g;
			g.setColor(Color.black);
			g.fillRoundRect(x.get(i) - borda, y.get(i) - borda + (i * 20) + 2, tam + borda * 2, tam + borda * 2, 10,
					50);
			g.setColor(new Color(150, 150, 180));
			g.fillRoundRect(x.get(i), y.get(i) + 2 + (i * 20), tam, tam, 10, 50);

			if (mouseOver) {
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
			} else {
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f));
			}
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
			if (clicou.get(i)) {
				g.setColor(Color.DARK_GRAY);
				g.fillRoundRect(x.get(i), y.get(i) + 2 + (i * 20), tam, tam, 10, 50);
			}

			g.setColor(Color.black);
			g.setFont(new Font("arial", Font.BOLD, 13));
			g.drawString(text.get(i), x.get(i) + 12, y.get(i) + 11 + (i * 20));
		}

	}

}
