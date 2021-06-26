package Menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import JObjects.Botao;
import Main.Main;

public class Menu {
	private Login login = new Login();
	public Buscar busca = new Buscar();
	public Editar editar ;
	private Cadastro cad = new Cadastro();
	private Color beje;
	String state = "Login";
	private Botao cadastrar = new Botao(50, 70, 97, 20, "Cadastrar", Color.white, 2, 15);
	private Botao buscar = new Botao(170, 70, 97, 20, "Buscar", Color.white, 2, 15);
	private Botao sair = new Botao(550, 300, 97, 20, "Sair", Color.white, 2, 15);
	private int mx, my;
	private boolean pressionou, soltou, moveu;

	public Menu() {
		Main.botoes.add(cadastrar);
		Main.botoes.add(buscar);
		Main.botoes.add(sair);
	}

	public void resetar() {
		login = new Login();
		busca = new Buscar();
		cad = new Cadastro();
	}

	public void mover() {
		moveu = true;
		soltou = false;
	}

	public void soltar() {
		pressionou = false;
		soltou = true;
	}

	public void pressionar() {
		pressionou = true;
		moveu = false;
	}

	public boolean pressionou() {
		return pressionou;
	}

	public boolean moveu() {
		return moveu;
	}

	public boolean soltou() {
		return soltou;
	}

	public void setMouse(int mx, int my) {
		this.mx = mx;
		this.my = my;
	}

	public int getMouseX() {
		return mx;
	}

	public int getMouseY() {
		return my;
	}

	public void tick() {
		if (sair.clicou()) {
			System.exit(1);
		}
		if (cadastrar.clicou()) {
			state = "Cadastrar";
			cadastrar.off();
		}
		if (buscar.clicou()) {
			state = "Buscar";
			buscar.off();
		}

	}

	public void render(Graphics g) {
		beje = new Color(211, 228, 255);
		g.setColor(beje);
		g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
		Graphics2D g2 = (Graphics2D) g;
		if (state.equals("Login")) {
			login.render(g);
		} else {
			sair.render(g);
		}
		if (state.equals("Cadastrar")) {
			cad.render(g);
		}
		if (state.equals("Buscar")) {
			busca.render(g);
		}
		if (state.equals("Editar")) {
			editar.render(g);
		}
		if (state.equals("Menu")) {
			g.setColor(Color.DARK_GRAY);
			g.setFont(new Font("arial", Font.BOLD, 30));
			g.drawString("Sistema com nome legal ", 50, 40);
			cadastrar.render(g);
			buscar.render(g);
		}

	}
}
