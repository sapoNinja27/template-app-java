package Menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import Entidades.Ficha;
import Entidades.Jogo;
import Entidades.Personagem;
import Entidades.Usuario;
import JObjects.Botao;
import JObjects.CampoDeTexto;
import JObjects.Tabela;
import Main.Main;

public class Cadastro {
	Botao adicionar = new Botao(550, 240, 97, 20, "Adicionar", Color.white, 2, 15);
	Botao voltar = new Botao(550, 270, 97, 20, "Voltar", Color.white, 2, 15);

	CampoDeTexto jogo = new CampoDeTexto(450, 100, 75, 15, "Jogo");
	CampoDeTexto genero = new CampoDeTexto(450+80, 100, 75, 15, "Genero");
	
	CampoDeTexto nome = new CampoDeTexto(100, 100, 150, 15, "Nome");
	CampoDeTexto nivel = new CampoDeTexto(100, 125, 150, 15, "Nivel");
	CampoDeTexto descricao = new CampoDeTexto(100, 250, 300, 50, "Descricao");
	
	Tabela jogos= new Tabela(260, 100, 140, 15);
	Jogo selectedJogo = new Jogo("","");
	public Cadastro() {
		for(int i=0;i<Main.jogos.size();i++) {
			jogos.setTexto(Main.jogos.get(i).getNome()+" "+Main.jogos.get(i).getGenero(), i);
		}
		Main.campos.add(jogo);
		Main.campos.add(genero);
		Main.campos.add(nome);
		Main.campos.add(nivel);
		descricao.setBox(3);
		descricao.setSize(37*3);
		Main.campos.add(descricao);
		Main.botoes.add(adicionar);
		Main.botoes.add(voltar);
	}

	public void tick() {   
		if (voltar.clicou()) {
			Main.menu.state = "Menu";
			Main.menu.resetar();
			Main.botoes.remove(voltar);
		}
		if (adicionar.clicou()) {
			selectedJogo=Main.jogos.get(jogos.getSelecionado());
			Usuario user= new Usuario("zapelop", "senha mane");
			Main.fichas.add(new Ficha(user,new Personagem(nome.getTexto(),
					Integer.valueOf(nivel.getTexto()), descricao.getTexto()),
					selectedJogo));
			System.out.println(Main.fichas.get(0));
			Main.menu.state = "Menu";
			Main.menu.resetar();
			Main.botoes.remove(adicionar);
		}
	}

	public void render(Graphics g) {
		tick();
		jogos.render(g);
		jogo.render(g);
		genero.render(g);
		nome.render(g);
		nivel.render(g);
		descricao.render(g);
		
		g.setColor(Color.black);
		g.setFont(new Font("arial", Font.BOLD, 12));
		g.drawString("Novo Jogo ", 450, 90);
		
		g.setColor(Color.DARK_GRAY);
		g.setFont(new Font("arial", Font.BOLD, 30));
		g.drawString("Cadastrar ", 50, 40);
		adicionar.render(g);
		voltar.render(g);
	}
}
