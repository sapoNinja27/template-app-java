package JObjects;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import Entidades.Jogo;
import Main.Main;

public class Tabela {
	private int x, y, w, h;
	private List<String> text=new ArrayList<String>();
	private List<Boolean> clicou=new ArrayList<Boolean>();
	private boolean mouseOver, clicouDentro;
	private int mx, my;
	private int selecionado;
	public Tabela(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	public String getTexto(int id) {
		return text.get(id);
	}

	public void setTexto(String text, int id) {
		this.text.add(text);
		this.clicou.add(false);
	}
	public Jogo getJogo() {
		String[] pal=getTexto(selecionado).split(" ");
		return new Jogo(pal[0],pal[1]);
	}
	public boolean mouseOver() {
		return mouseOver;
	}

	public void clicou() {
		for (int i = 0; i < clicou.size(); i++) {
			if(clicou.get(i)) {
				Main.menu.busca.id= i;
				selecionado=i;
			}
		}
	}

	public void tick() {
		clicou();
		mx = Main.menu.getMouseX();
		my = Main.menu.getMouseY();
		for (int i = 0; i < clicou.size(); i++) {
			if (Main.menu.pressionou()) {
				if (mx > x - 12 && mx < x + w && my > y - 9+(i*20) && my < y+(i*20) + h) {
					clicouDentro = true;
				}
			}
			if (Main.menu.soltou()) {
				// soltou
				if (mx > x - 12 && mx < x + w && my > y+(i*20) - 9 && my < y +(i*20)+ h) {
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

	public void render(Graphics g) {
		tick();
		int borda = 1;
		int maximo=7;
		if(text.size()<7) {
			maximo=text.size();
		}
		for (int i = 0; i < maximo; i++) {
			g.setColor(Color.black);
			g.fillRect(x - borda, y - borda+(i * 20), w + borda * 2, h + borda * 2);
			g.setColor(Color.white);
			if(clicou.get(i)) {
				g.setColor(Color.gray);
			}
			g.fillRect(x, y+(i * 20), w, h);
			g.setColor(Color.black);
			g.setFont(new Font("arial", Font.BOLD, 13));
			g.drawString(text.get(i), x + 3, y + 13 + (i * 20));
		}

	}

}
