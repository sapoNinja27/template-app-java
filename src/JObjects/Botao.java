package JObjects;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import Main.Main;
/**
 * Botão modificavel
 *
 */
public class Botao {
	private int x, y, w, h, aw, ah;
	private Color cor;
	private String text;
	private boolean mouseOver, mousePressed;
	private boolean mouseFora = true;
	private int borda;
	private int font;
	private boolean clicou;
	private int mx, my;
	/**
	 * Cria um botão
	 *@param x : posição horizontal 
	 *@param y : posição vertical 
	 *@param width : tamanho horizontal 
	 *@param height : tamanho vertical
	 */
	public Botao(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	/**
	 * Ajusta quão circular é o botão, o padrão é 0
	 *@param w : Valor de "rounded" horizontal
	 *@param h : Valor de "rounded" vertical
	 */
	public void setRound(int w, int h) {
		this.aw = w;
		this.ah = h;
	}
	public Botao(int x, int y, int w, int h, int aw, int ah, String text, Color cor, int borda, int font) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.aw = aw;
		this.ah = ah;
		this.text = text;
		this.cor = cor;
		this.borda = borda;
		this.font = font;
	}
	public boolean clicou() {
		return clicou;
	}

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

	public boolean mouseOver() {
		return !mouseFora;
	}

	public void off() {
		clicou = false;
		mouseFora = true;
	}

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
