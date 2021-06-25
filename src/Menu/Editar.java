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

	CampoDeTexto nome = new CampoDeTexto(100, 75, 150, 15, "Nome");
	CampoDeTexto data = new CampoDeTexto(270, 75, 150, 15, "Data de Nascimento");
	CampoDeTexto tel = new CampoDeTexto(270, 100, 150, 15, "Telefone");
	CampoDeTexto cel = new CampoDeTexto(100, 100, 150, 15, "Celular");
	CampoDeTexto mail = new CampoDeTexto(100, 125, 150, 15, "E-mail");

	CampoDeTexto pais = new CampoDeTexto(100, 175, 150, 15, "Pais");
	CampoDeTexto estado = new CampoDeTexto(270, 175, 150, 15, "Estado");
	CampoDeTexto city = new CampoDeTexto(100, 200, 150, 15, "Cidade");
	CampoDeTexto cep = new CampoDeTexto(270, 200, 150, 15, "CEP");
	CampoDeTexto bairro = new CampoDeTexto(100, 225, 150, 15, "Bairro");
	CampoDeTexto end = new CampoDeTexto(270, 225, 150, 15, "Rua");
	CampoDeTexto num = new CampoDeTexto(100, 250, 150, 15, "Numero");
	CampoDeTexto comp = new CampoDeTexto(270, 250, 150, 15, "Complemento");

	CampoDeTexto obs = new CampoDeTexto(100, 300, 320, 45, "Observações");

	

	

	public Editar() {

		Main.campos.add(nome);
		data.setMask("Data");
		Main.campos.add(tel);
		Main.campos.add(cel);
		Main.campos.add(mail);
		Main.campos.add(pais);
		Main.campos.add(estado);
		Main.campos.add(city);
		cep.setMask("Cep");
		Main.campos.add(cep);
		Main.campos.add(bairro);
		Main.campos.add(end);
		num.setMask("Numero");
		Main.campos.add(num);
		Main.campos.add(comp);
		obs.setBox(3);
		obs.setSize(37 * 3);
		Main.campos.add(obs);
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

		nome.render(g);
		data.render(g);
		tel.render(g);
		cel.render(g);
		mail.render(g);
		obs.render(g);

		end.render(g);
		comp.render(g);
		bairro.render(g);
		estado.render(g);
		cep.render(g);
		num.render(g);
		city.render(g);
		pais.render(g);

		g.setColor(Color.DARK_GRAY);
		g.setFont(new Font("arial", Font.BOLD, 30));
		g.drawString("Editar ", 50, 40);
		adicionar.render(g);
		voltar.render(g);
	}
}
