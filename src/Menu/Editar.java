package Menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import JObjects.Botao;
import JObjects.CampoDeTexto;
import Main.Main;

public class Editar {
	Botao adicionar = new Botao(550, 240, 97, 20, "Salvar", Color.white, 2, 15);
	Botao voltar = new Botao(550, 270, 97, 20, "Voltar", Color.white, 2, 15);
	
	CampoDeTexto jogo ;
	CampoDeTexto genero ;
	CampoDeTexto nome ;
	CampoDeTexto nivel ;
	CampoDeTexto descricao ;

	
	public Editar(Integer id) {
		
		jogo = new CampoDeTexto(450, 100, 75, 15, Main.fichas.get(id).getJogo().getNome());
		genero = new CampoDeTexto(450+80, 100, 75, 15,  Main.fichas.get(id).getJogo().getGenero());
		nome = new CampoDeTexto(100, 100, 150, 15,  Main.fichas.get(id).getPersonagem().getNome());
		nivel = new CampoDeTexto(100, 125, 150, 15, ""+Main.fichas.get(id).getPersonagem().getNivel());
		descricao = new CampoDeTexto(100, 250, 300, 50, Main.fichas.get(id).getPersonagem().getDestricao());
		Main.campos.add(jogo);
		Main.campos.add(genero);
		Main.campos.add(nome);
		Main.campos.add(nivel);
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
			Main.menu.state = "Menu";
			Main.menu.resetar();
			Main.botoes.remove(adicionar);
		}
	}

	public void render(Graphics g) {
		tick();

		jogo.render(g);
		genero.render(g);
		nome.render(g);
		nivel.render(g);
		descricao.render(g);

		g.setColor(Color.DARK_GRAY);
		g.setFont(new Font("arial", Font.BOLD, 30));
		g.drawString("Editar ", 50, 40);
		adicionar.render(g);
		voltar.render(g);
	}
}
