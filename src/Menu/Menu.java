package Menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import JObjects.Botao;
import JObjects.CampoDeTexto;
import JObjects.RadioButton;
import Main.Main;

/**
 * Menu
 * 
 * @apiNote Classe para gerenciar as telas usando a variavel state para definir
 *          a pagina atual, e modificando o conteudo do render para navegar
 *          entre as telas do aplicativo
 */
public class Menu {
	private Color beje;
	/**
	 * Variavel de controle de navegação
	 */
	String state = "Menu";
	private Botao sair = new Botao(550, 300, 97, 20, "Sair", Color.white, 2, 15);
	private int mx, my;
	private boolean pressionou, soltou, moveu;
	private CampoDeTexto camp = new CampoDeTexto(0, 200, 200, 15, "exemplo campo");
	private RadioButton check = new RadioButton();

	int x = 200;
	int y = 200;
	boolean aumentandoY, aumentandoX;
	int difX, difY;
	boolean arrastando;

	/**
	 * Inicia um menu basico
	 * 
	 * @apiNote objetos dessa pagina são adicionados aqui
	 */
	public Menu() {
		check.setTexto("1", 1, 200, 200);
		Main.campos.add(camp);
		Main.botoes.add(sair);
	}

	/**
	 * Reinicia a pagina menu e cria novas subpaginas
	 */
	public void resetar() {

	}

	/**
	 * Define que o mouse esta em movimento
	 */
	public void mover() {
		moveu = true;
		soltou = false;
	}

	/**
	 * Define o evento após pressionar o mouse e soltar
	 */
	public void soltar() {
		pressionou = false;
		soltou = true;
	}

	public void checarMoved() {
		if(moveu) {
			
		}
	}

	/**
	 * Define o evento de pressionar
	 */
	public void pressionar() {
		pressionou = true;
		moveu = false;
	}

	public void arrastar() {
		arrastando = true;
	}

	/**
	 * Retorna se o mouse foi pressionado
	 */
	public boolean pressionou() {
		return pressionou;
	}

	/**
	 * Retorna se o mouse foi movido
	 */
	public boolean moveu() {
		return moveu;
	}

	/**
	 * Retorna se o mouse foi solto
	 */
	public boolean soltou() {
		return soltou;
	}

	/**
	 * Configura a posição do mouse
	 */
	public void setMouse(int mx, int my) {
		if (this.mx == mx && this.my == my) {
			arrastando = false;
		}		
		difX = (mx - this.mx);
		difY = (my - this.my);
		this.mx = mx;
		this.my = my;
	}

	/**
	 * Retorna a posição horizontal do mouse
	 */
	public int getMouseX() {
		return mx;
	}

	/**
	 * Retorna a posição vertical do mouse
	 */
	public int getMouseY() {
		return my;
	}

	/**
	 * Configura as ações dos objetos dentro do menu root
	 */
	public void tick() {
		
		if (sair.clicou()) {
			System.exit(1);
		}

	}

	/**
	 * Renderiza o menu root
	 * 
	 * @apiNote dentro dessa função é possivel definir o que estará rodando
	 *          indefinidamente e o que estará rodando apenas quando estiver no root
	 * @param g  tipo grafico do java instanciado no main
	 * @param g2 tipo grafico para criação de animações avançadas instanciado no
	 *           main
	 */
	public void render(Graphics g, Graphics2D g2) {
		beje = new Color(211, 228, 255);
		g.setColor(beje);
		g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
		g.setColor(Color.black);
		if (arrastando) {
			x += difX * 3;
			y += difY * 3;
			difX=0;
			difY=0;
			arrastando=false;
		}
		g.drawOval((x), y, 50, 50);
		g.drawOval((x + 200), y, 50, 50);
		if (state.equals("Menu")) {
			g.setColor(Color.DARK_GRAY);
			g.setFont(new Font("arial", Font.BOLD, 30));
			g.drawString("Template de menu", 50, 40);
//			render do campo e render do radiobutton
//			camp.render(g);
//			check.render(g);
			sair.render(g);
		}

	}
}
