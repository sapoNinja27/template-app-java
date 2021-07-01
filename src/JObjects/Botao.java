package JObjects;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import Main.Main;

/**
 * Bot�o
 * 
 * @apiNote Cria um bot�o customizavel mais simples de modificar
 * @apiNote Configura��es do objeto assim como sua inclus�o na lista do main s�o
 *          feitas no init() da pagina
 */
public class Botao {
	private int x, y, w, h, aw, ah;
	private Color cor = Color.white;
	private String text;
	private boolean mouseOver, mousePressed;
	private boolean mouseFora = true;
	private int borda;
	private int font;
	private boolean clicou;
	private int mx, my;

	/**
	 * Cria um bot�o
	 * 
	 * @param x      : posi��o horizontal
	 * @param y      : posi��o vertical
	 * @param width  : tamanho horizontal
	 * @param height : tamanho vertical
	 */
	public Botao(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	/**
	 * Cria um bot�o mais elaborado
	 * 
	 * @param x      : posi��o horizontal
	 * @param y      : posi��o vertical
	 * @param width  : tamanho horizontal
	 * @param height : tamanho vertical
	 * @param text   : texto do bot�o
	 * @param cor    : cor do bot�o
	 * @param borda  : tamanho da borda
	 * @param font   : tamanho da fonte
	 */
	public Botao(int x, int y, int w, int h, String text, Color cor, int borda, int font) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.text = text;
		this.cor = cor;
		this.borda = borda;
		this.font = font;
	}

	/**
	 * Ajusta qu�o redondo � o bot�o, o padr�o � 0
	 * 
	 * @param w : Valor de "rounded" horizontal
	 * @param h : Valor de "rounded" vertical
	 */
	public void setRound(int w, int h) {
		this.aw = w;
		this.ah = h;
	}

	/**
	 * Adiciona o texto do bot�o
	 * 
	 * @param text : texto
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Adiciona a cor do bot�o, o padr�o � branco
	 * 
	 * @param cor : cor
	 */
	public void setCor(Color cor) {
		this.cor = cor;
	}

	/**
	 * Aumenta a grossura da borda
	 * 
	 * @param borda : tamanho da borda
	 */
	public void setBorda(int borda) {
		this.borda = borda;
	}

	/**
	 * Modifica o tamanho da font
	 * 
	 * @param font : tamanho da fonte
	 */
	public void setFontSize(int font) {
		this.font = font;
	}

	/**
	 * Retorna verdadeiro quando o bot�o � pressionado
	 */
	public boolean clicou() {
		return clicou;
	}

	/**
	 * Retorna verdadeiro quando o mouse esta acima do bot�o
	 */
	public boolean mouseOver() {
		return !mouseFora;
	}

	/**
	 * Redefine o bot�o (utilizado quando � nescessario trocar a pagina que ele esta
	 * instanciado)
	 */
	public void off() {
		clicou = false;
		mouseFora = true;
	}

	/**
	 * Fun��es tick s�o chamadas indefinidamente durante a aplica��o atualiza a
	 * posi��o do mouse em rela��o ao objeto
	 */
	public void tick() {
		mx = Main.menu.getMouseX();
		my = Main.menu.getMouseY();
		if (Main.menu.pressionou()) {
			clicou = false;
			if (mx > x && mx < x + w && my > y && my < y + h) {
				mousePressed = true;
				mouseFora = false;
			} else {
				mouseOver = false;
				mouseFora = true;
			}
		} else if (Main.menu.soltou()) {
			mousePressed = false;
			if (mx > x && mx < x + w && my > y && my < y + h) {
				clicou = true;
				mouseOver = true;
				mouseFora = false;
			} else {
				mouseOver = false;
				clicou = false;
				mouseFora = true;
			}
		} else {
			mousePressed = false;
			clicou = false;
			if (mx > x && mx < x + w && my > y && my < y + h) {
				mouseOver = true;
				mouseFora = false;
			} else {
				mouseOver = false;
				mouseFora = true;
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
		Graphics2D g2 = (Graphics2D) g;
		g.setColor(Color.black);
		g.fillRoundRect(x - borda, y - borda, w + borda * 2, h + borda * 2, aw, ah);
		g.setColor(cor);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
		g.fillRoundRect(x - borda, y - borda, w + borda * 2, h + borda * 2, aw, ah);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
		g.fillRoundRect(x, y, w, h, aw, ah);
		g.setColor(Color.black);
		if (mousePressed) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
		} else if (mouseOver) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
		} else {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f));
		}
		g.fillRoundRect(x, y, w, h, aw, ah);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
		g.setColor(Color.black);
		g.setFont(new Font("arial", Font.BOLD, font));
		g.drawString(text, (x + w / 2 - text.length() * font / 4), y + h / 2 + 5);

	}

}
