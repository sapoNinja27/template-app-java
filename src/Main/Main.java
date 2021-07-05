package Main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import Graficos.Spritesheet;
import JObjects.Botao;
import JObjects.CampoDeTexto;
import Menu.Menu;

/**
 * Classe main
 * 
 * @apiNote Classe iniciará o aplicativo com as configurações basicas
 */
public class Main extends Canvas implements Runnable, MouseMotionListener, MouseListener, KeyListener {
	private static final long serialVersionUID = 1L;
	public static JFrame frame;
	private Thread thread;
	/**
	 * Para que os botões respondam unitariamente é nescessarios adicionalos aqui
	 */
	public static List<Botao> botoes = new ArrayList<Botao>();
	/**
	 * Para que os campos de texto respondam unitariamente é nescessarios
	 * adicionalos aqui
	 */
	public static List<CampoDeTexto> campos = new ArrayList<CampoDeTexto>();
	private boolean isRunning = true;
	/**
	 * Tamanho da tela
	 */
	public static final int WIDTH = 180 * 4, HEIGHT = 90 * 4;
	/**
	 * Escala que vai ser usada na aplicação
	 */
	public static final int SCALE = 1;
	private BufferedImage image;
	public static Random rand;
	public static Menu menu;
	private Spritesheet mouse;
	private boolean caps;
	private boolean til;
	private boolean agudo;
	private boolean circ;
	private boolean shift;

	/**
	 * Inicia as propriedades do aplicativo
	 */
	public Main() {
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		setFocusTraversalKeysEnabled(false);
		requestFocus();
		mouse=new Spritesheet("/cursor.png");
		rand = new Random();
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		initFrame();
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		menu = new Menu();
	}

	/**
	 * Atualiza a imagem do mouse
	 */
	public void attMouse() {
		int bot = botoes.size();
		int camp = campos.size();
		for (int i = 0; i < botoes.size(); i++) {
			if (botoes.get(i).mouseOver()) {
				bot--;
			}
		}
		for (int j = 0; j < campos.size(); j++) {
			if (campos.get(j).mouseOver()) {
				camp--;
			}
		}
		if (bot == botoes.size()) {
			if (camp == campos.size()) {
				try {
					Toolkit toolkit = Toolkit.getDefaultToolkit();
					Image image = mouse.getSprite(0,0,32,32);
					Cursor c = toolkit.createCustomCursor(image, new Point(0, 0), "img");
					frame.setCursor(c);
				} catch (Exception e) {

				}
			} else {
				try {
					Toolkit toolkit = Toolkit.getDefaultToolkit();
					Image image = mouse.getSprite(32*2,0,32,32);
					Cursor c = toolkit.createCustomCursor(image, new Point(0, 0), "img");
					frame.setCursor(c);
				} catch (Exception e) {

				}
			}
		} else {
			try {
				Toolkit toolkit = Toolkit.getDefaultToolkit();
				Image image = mouse.getSprite(32,0,32,32);
				Cursor c = toolkit.createCustomCursor(image, new Point(0, 0), "img");
				frame.setCursor(c);
			} catch (Exception e) {

			}
		}

	}

	/**
	 * Monta a tela basica
	 */
	public void initFrame() {
		frame = new JFrame("Sistema");
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		Image imagem = null;
		try {
			imagem = ImageIO.read(getClass().getResource("/icone-jogo/icon.png"));
		} catch (IOException e) {

		}
		try {
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			Image image = toolkit.getImage(getClass().getResource("/cursorStandart.png"));
			Cursor c = toolkit.createCustomCursor(image, new Point(0, 0), "img");
			frame.setCursor(c);
		} catch (Exception e) {

		}
		frame.setIconImage(imagem);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	/**
	 * Inicia a thread
	 */
	public synchronized void start() {
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}

	/**
	 * Finaliza a thread
	 */
	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Chama o contrutor do aplicativo
	 */
	public static void main(String args[]) {
		Main app = new Main();
		app.start();
	}

	/**
	 * Chama os metodos de atualização continua
	 */
	public void tick() {
		menu.tick();
		attMouse();
	}

	/**
	 * Termina a criação da tela e renderiza
	 */
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = image.getGraphics();
		g.setColor(new Color(0, 0, 0));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		Graphics2D g2 = (Graphics2D) g;
		menu.render(g, g2);
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		g.setFont(new Font("arial", Font.BOLD, 20));
		bs.show();
	}

	/**
	 * Calcula a taxa de atualização do aplicativo
	 */
	public void run() {
		long lastTime = System.nanoTime();
		double amongOfTicks = 60.0;
		double delta = 0;
		double ns = 1000000000 / amongOfTicks;
		double timer = System.currentTimeMillis();
		while (isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1) {
				tick();
				render();
				delta--;
			}
			if (System.currentTimeMillis() - timer >= 1000) {
				timer += 1000;
			}
		}
		stop();
	}

	/**
	 * Define a tecla atual que foi pressionada no teclado
	 * 
	 * @apiNote apenas teclas basicas adicionadas
	 */
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
			shift = true;
			caps = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
			for (int j = 0; j < campos.size(); j++) {
				if (campos.get(j).clicou()) {
					campos.get(j).removeLetra();
				}
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_CAPS_LOCK) {
			caps = !caps;
		}
		if (e.getKeyCode() == 131) {
			if (shift) {
				circ = true;
			} else {
				til = true;
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_DEAD_ACUTE) {
			agudo = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_TAB) {
			int i = 0;
			for (int j = 0; j < campos.size(); j++) {
				if (campos.get(j).clicou()) {
					i = j;
				}
			}
			if (i == campos.size() - 1) {
				campos.get(i).desclicar();
				campos.get(0).clicar();
				i = 0;
			} else {
				campos.get(i).desclicar();
				campos.get(i + 1).clicar();
				i = 0;
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_0 || e.getKeyCode() == KeyEvent.VK_NUMPAD0) {
			for (int j = 0; j < campos.size(); j++) {
				if (campos.get(j).clicou()) {
					campos.get(j).addLetra("0");
				}
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_1 || e.getKeyCode() == KeyEvent.VK_NUMPAD1) {
			for (int j = 0; j < campos.size(); j++) {
				if (campos.get(j).clicou()) {
					campos.get(j).addLetra("1");
				}
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_2 || e.getKeyCode() == KeyEvent.VK_NUMPAD2) {
			for (int j = 0; j < campos.size(); j++) {
				if (campos.get(j).clicou()) {
					campos.get(j).addLetra("2");
				}
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_3 || e.getKeyCode() == KeyEvent.VK_NUMPAD3) {
			for (int j = 0; j < campos.size(); j++) {
				if (campos.get(j).clicou()) {
					campos.get(j).addLetra("3");
				}
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_4 || e.getKeyCode() == KeyEvent.VK_NUMPAD4) {
			for (int j = 0; j < campos.size(); j++) {
				if (campos.get(j).clicou()) {
					campos.get(j).addLetra("4");
				}
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_5 || e.getKeyCode() == KeyEvent.VK_NUMPAD5) {
			for (int j = 0; j < campos.size(); j++) {
				if (campos.get(j).clicou()) {
					campos.get(j).addLetra("5");
				}
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_6 || e.getKeyCode() == KeyEvent.VK_NUMPAD6) {
			for (int j = 0; j < campos.size(); j++) {
				if (campos.get(j).clicou()) {
					campos.get(j).addLetra("6");
				}
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_7 || e.getKeyCode() == KeyEvent.VK_NUMPAD7) {
			for (int j = 0; j < campos.size(); j++) {
				if (campos.get(j).clicou()) {
					campos.get(j).addLetra("7");
				}
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_8 || e.getKeyCode() == KeyEvent.VK_NUMPAD8) {
			for (int j = 0; j < campos.size(); j++) {
				if (campos.get(j).clicou()) {
					campos.get(j).addLetra("8");
				}
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_9 || e.getKeyCode() == KeyEvent.VK_NUMPAD9) {
			for (int j = 0; j < campos.size(); j++) {
				if (campos.get(j).clicou()) {
					campos.get(j).addLetra("9");
				}
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			for (int j = 0; j < campos.size(); j++) {
				if (campos.get(j).clicou()) {
					campos.get(j).addLetra(" ");
				}
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_A) {
			for (int j = 0; j < campos.size(); j++) {
				if (campos.get(j).clicou()) {
					if (caps) {
						if (circ) {
							campos.get(j).addLetra("Â");
							circ = false;
						} else if (til) {
							campos.get(j).addLetra("Ã");
							til = false;
						} else if (agudo) {
							campos.get(j).addLetra("Á");
							agudo = false;
						} else {
							campos.get(j).addLetra("A");
						}
					} else {
						if (circ) {
							campos.get(j).addLetra("â");
							circ = false;
						} else if (til) {
							campos.get(j).addLetra("ã");
							til = false;
						} else if (agudo) {
							campos.get(j).addLetra("á");
							agudo = false;
						} else {
							campos.get(j).addLetra("a");
						}
					}
				}
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_B) {
			for (int j = 0; j < campos.size(); j++) {
				if (campos.get(j).clicou()) {
					if (caps) {
						campos.get(j).addLetra("B");
					} else {
						campos.get(j).addLetra("b");
					}
				}
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_C) {
			for (int j = 0; j < campos.size(); j++) {
				if (campos.get(j).clicou()) {
					if (caps) {
						campos.get(j).addLetra("C");
					} else {
						campos.get(j).addLetra("c");
					}
				}
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_D) {
			for (int j = 0; j < campos.size(); j++) {
				if (campos.get(j).clicou()) {
					if (caps) {
						campos.get(j).addLetra("D");
					} else {
						campos.get(j).addLetra("d");
					}
				}
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_E) {
			for (int j = 0; j < campos.size(); j++) {
				if (campos.get(j).clicou()) {
					if (caps) {
						if (circ) {
							campos.get(j).addLetra("Ê");
							circ = false;
						} else if (til) {
							campos.get(j).addLetra("~E");
							til = false;
						} else if (agudo) {
							campos.get(j).addLetra("É");
							agudo = false;
						}
						{
							campos.get(j).addLetra("E");
						}
					} else {
						if (circ) {
							campos.get(j).addLetra("ê");
							circ = false;
						} else if (til) {
							campos.get(j).addLetra("~e");
							til = false;
						} else if (agudo) {
							campos.get(j).addLetra("é");
							agudo = false;
						} else {
							campos.get(j).addLetra("e");
						}
					}
				}
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_F) {
			for (int j = 0; j < campos.size(); j++) {
				if (campos.get(j).clicou()) {
					if (caps) {
						campos.get(j).addLetra("F");
					} else {
						campos.get(j).addLetra("f");
					}
				}
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_G) {
			for (int j = 0; j < campos.size(); j++) {
				if (campos.get(j).clicou()) {
					if (caps) {
						campos.get(j).addLetra("G");
					} else {
						campos.get(j).addLetra("g");
					}
				}
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_H) {
			for (int j = 0; j < campos.size(); j++) {
				if (campos.get(j).clicou()) {
					if (caps) {
						campos.get(j).addLetra("H");
					} else {
						campos.get(j).addLetra("h");
					}
				}
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_I) {
			for (int j = 0; j < campos.size(); j++) {
				if (campos.get(j).clicou()) {
					if (caps) {
						if (circ) {
							campos.get(j).addLetra("Î");
							circ = false;
						} else if (til) {
							campos.get(j).addLetra("~I");
							til = false;
						} else if (agudo) {
							campos.get(j).addLetra("Í");
							agudo = false;
						} else {
							campos.get(j).addLetra("I");
						}
					} else {
						if (circ) {
							campos.get(j).addLetra("î");
							circ = false;
						} else if (til) {
							campos.get(j).addLetra("~i");
							til = false;
						} else if (agudo) {
							campos.get(j).addLetra("í");
							agudo = false;
						} else {
							campos.get(j).addLetra("i");
						}
					}
				}
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_J) {
			for (int j = 0; j < campos.size(); j++) {
				if (campos.get(j).clicou()) {
					if (caps) {
						campos.get(j).addLetra("J");
					} else {
						campos.get(j).addLetra("j");
					}
				}
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_K) {
			for (int j = 0; j < campos.size(); j++) {
				if (campos.get(j).clicou()) {
					if (caps) {
						campos.get(j).addLetra("K");
					} else {
						campos.get(j).addLetra("k");
					}
				}
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_L) {
			for (int j = 0; j < campos.size(); j++) {
				if (campos.get(j).clicou()) {
					if (caps) {
						campos.get(j).addLetra("L");
					} else {
						campos.get(j).addLetra("l");
					}
				}
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_M) {
			for (int j = 0; j < campos.size(); j++) {
				if (campos.get(j).clicou()) {
					if (caps) {
						campos.get(j).addLetra("M");
					} else {
						campos.get(j).addLetra("m");
					}
				}
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_N) {
			for (int j = 0; j < campos.size(); j++) {
				if (campos.get(j).clicou()) {
					if (caps) {
						campos.get(j).addLetra("N");
					} else {
						campos.get(j).addLetra("n");
					}
				}
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_O) {
			for (int j = 0; j < campos.size(); j++) {
				if (campos.get(j).clicou()) {
					if (caps) {
						if (circ) {
							campos.get(j).addLetra("Ô");
							circ = false;
						} else if (til) {
							campos.get(j).addLetra("Õ");
							til = false;
						} else if (agudo) {
							campos.get(j).addLetra("Ó");
							agudo = false;
						} else {
							campos.get(j).addLetra("O");
						}
					} else {
						if (circ) {
							campos.get(j).addLetra("ô");
							circ = false;
						} else if (til) {
							campos.get(j).addLetra("õ");
							til = false;
						} else if (agudo) {
							campos.get(j).addLetra("ó");
							agudo = false;
						} else {
							campos.get(j).addLetra("o");
						}
					}
				}
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_P) {
			for (int j = 0; j < campos.size(); j++) {
				if (campos.get(j).clicou()) {
					if (caps) {
						campos.get(j).addLetra("P");
					} else {
						campos.get(j).addLetra("p");
					}
				}
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_Q) {
			for (int j = 0; j < campos.size(); j++) {
				if (campos.get(j).clicou()) {
					if (caps) {
						campos.get(j).addLetra("Q");
					} else {
						campos.get(j).addLetra("q");
					}
				}
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_R) {
			for (int j = 0; j < campos.size(); j++) {
				if (campos.get(j).clicou()) {
					if (caps) {
						campos.get(j).addLetra("R");
					} else {
						campos.get(j).addLetra("r");
					}
				}
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_S) {
			for (int j = 0; j < campos.size(); j++) {
				if (campos.get(j).clicou()) {
					if (caps) {
						campos.get(j).addLetra("S");
					} else {
						campos.get(j).addLetra("s");
					}
				}
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_T) {
			for (int j = 0; j < campos.size(); j++) {
				if (campos.get(j).clicou()) {
					if (caps) {
						campos.get(j).addLetra("T");
					} else {
						campos.get(j).addLetra("t");
					}
				}
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_U) {
			for (int j = 0; j < campos.size(); j++) {
				if (campos.get(j).clicou()) {
					if (caps) {
						if (circ) {
							campos.get(j).addLetra("Ú");
							circ = false;
						} else if (til) {
							campos.get(j).addLetra("~U");
							til = false;
						} else if (agudo) {
							campos.get(j).addLetra("Ú");
							agudo = false;
						} else {
							campos.get(j).addLetra("U");
						}
					} else {
						if (circ) {
							campos.get(j).addLetra("ú");
							circ = false;
						} else if (til) {
							campos.get(j).addLetra("~u");
							til = false;
						} else if (agudo) {
							campos.get(j).addLetra("ú");
							agudo = false;
						} else {
							campos.get(j).addLetra("u");
						}
					}
				}
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_V) {
			for (int j = 0; j < campos.size(); j++) {
				if (campos.get(j).clicou()) {
					if (caps) {
						campos.get(j).addLetra("V");
					} else {
						campos.get(j).addLetra("v");
					}
				}
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_X) {
			for (int j = 0; j < campos.size(); j++) {
				if (campos.get(j).clicou()) {
					if (caps) {
						campos.get(j).addLetra("X");
					} else {
						campos.get(j).addLetra("x");
					}
				}
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_Y) {
			for (int j = 0; j < campos.size(); j++) {
				if (campos.get(j).clicou()) {
					if (caps) {
						campos.get(j).addLetra("Y");
					} else {
						campos.get(j).addLetra("y");
					}
				}
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_W) {
			for (int j = 0; j < campos.size(); j++) {
				if (campos.get(j).clicou()) {
					if (caps) {
						campos.get(j).addLetra("W");
					} else {
						campos.get(j).addLetra("w");
					}
				}
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_Z) {
			for (int j = 0; j < campos.size(); j++) {
				if (campos.get(j).clicou()) {
					if (caps) {
						campos.get(j).addLetra("Z");
					} else {
						campos.get(j).addLetra("z");
					}
				}
			}
		}
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
			shift = false;
			caps = false;
		}
	}

	public void keyTyped(KeyEvent e) {

	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	/**
	 * Atualiza a posição do mouse quando ele se move
	 */
	public void mouseMoved(MouseEvent e) {
		if (e != null) {
			menu.setMouse(e.getX(), e.getY());
			menu.mover();
		}
	}

	public void mouseExited(MouseEvent e) {

	}

	/**
	 * Retorna quando o mouse for pressionado
	 */
	public void mousePressed(MouseEvent e) {
		menu.setMouse(e.getX(), e.getY());
		menu.pressionar();
	}

	/**
	 * Retorna quando o mouse for solto
	 */
	public void mouseReleased(MouseEvent e) {
		menu.setMouse(e.getX(), e.getY());
		menu.soltar();
	}

	public void mouseDragged(MouseEvent e) {
		menu.setMouse(e.getX(), e.getY());
		menu.arrastar();
	}
}
