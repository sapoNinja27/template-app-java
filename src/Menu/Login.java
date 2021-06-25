package Menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

import JObjects.Botao;
import JObjects.CampoDeTexto;
import JObjects.CheckBox;
import Main.Main;

public class Login {
	Botao logar = new Botao(280, 230, 60, 20, "Entrar", Color.white, 2, 15);
	Botao sair = new Botao(280 - 60 + 150, 230, 60, 20, "Sair", Color.white, 2, 15);
	CheckBox box = new CheckBox(280, 210);
	CampoDeTexto login = new CampoDeTexto(280, 165, 150, 15, "Usuario");
	CampoDeTexto senha = new CampoDeTexto(280, 190, 150, 15, "Senha");
	boolean confirmar;

	public Login() {
		Main.botoes.add(logar);
		Main.botoes.add(sair);
		login.setSize(15);
		senha.setSize(17);
		senha.setSenha();
		Main.campos.add(login);
		Main.campos.add(senha);
		carregarLogin();
	}

	public boolean checkLogin(String login, String senhaC) {
		char[] trans = senhaC.toCharArray();
		String senha="";
		for (int i = 0; i < trans.length; i++) {
			trans[i] = (char) (trans[i] - 10);
		}
		for (int i = 0; i < trans.length; i++) {
			senha = senha + trans[i];
		}
		if((login.contentEquals("Administrador")&& senha.equals("Administrador"))||
				(login.contentEquals("Adm")&& senha.equals("Adm123"))||
				(login.contentEquals("JP")&& senha.equals("senhafacil"))||
				(login.contentEquals("Administrator")&& senha.equals("Que3B1eng4ElT0r0"))||
				//pq essa senha é que benga heitor? e a de baixo é promiscuo?
				(login.contentEquals("Root")&& senha.equals("pr0m1uscu0"))
			){
				return true;
			}
		return false;
	}

	public void carregarLogin() {
		File file = new File("ultimoLogin.txt");
		if (file.exists()) {
			try {
				String singleLine = null;
				BufferedReader reader = new BufferedReader(new FileReader("ultimoLogin.txt"));
				try {
					while ((singleLine = reader.readLine()) != null) {
						String[] trans = singleLine.split(":");
						char[] val = trans[1].toCharArray();
						trans[1] = "";
						for (int i = 0; i < val.length; i++) {
							val[i] -= 10;
							trans[1] += val[i];
						}
						login.set(trans[0]);
						senha.set(trans[1]);
					}
				} catch (IOException e) {

				}
			} catch (FileNotFoundException e) {

			}
		}
	}

	public void tick() {
		if (sair.clicou()) {
			System.exit(1);
		}
		if (logar.clicou()) {
			if (checkLogin(login.getTexto(), senha.getTexto())) {
				Main.menu.state = "Menu";
				Main.botoes.remove(logar);
				Main.botoes.remove(sair);
				Main.campos.remove(login);
				Main.campos.remove(senha);
			}
			if (box.checked()) {
				BufferedWriter write = null;
				try {
					write = new BufferedWriter(new FileWriter("ultimoLogin.txt"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					write.write(login.getTexto() + ":" + senha.getTexto());
				} catch (IOException e) {

				}
				try {
					write.flush();
					write.close();
				} catch (IOException e) {

				}
			}
		}

	}

	public void confirmar() {
		confirmar = true;
	}

	public void render(Graphics g) {
		tick();
		int width = 300;
		int height = 150;
		int borda = 4;
		g.setColor(Color.black);
		g.fillRect(Main.WIDTH / 2 - width / 2 - borda, Main.HEIGHT / 2 - height / 2 - borda, width + borda * 2,
				height + borda * 2);
		g.setColor(Color.white);
		g.fillRect(Main.WIDTH / 2 - width / 2, Main.HEIGHT / 2 - height / 2, width, height);
		g.setColor(Color.black);
		g.setFont(new Font("arial", Font.BOLD, 15));
		g.drawString("Login", Main.WIDTH / 2 - 28, 140);
		g.setFont(new Font("arial", Font.BOLD, 10));
		g.drawString("Lembrar de mim", 295, 219);
		box.render(g);
		login.render(g);
		senha.render(g);
		logar.render(g);
		sair.render(g);

	}
}
