package Main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
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

import JObjects.Botao;
import JObjects.CampoDeTexto;
import Menu.Menu;

public class Main extends Canvas implements Runnable, MouseMotionListener, MouseListener, KeyListener {
	public static int TILE_SIZE = 64;
	private static final long serialVersionUID = 1L;
	public static JFrame frame;
	private Thread thread;
	public static List<Botao> botoes = new ArrayList<Botao>();
	public static List<CampoDeTexto> campos = new ArrayList<CampoDeTexto>();
	private boolean isRunning = true;
	public static final int WIDTH = 180 * 4;
	public static final int HEIGHT = 90 * 4;
	public static final int SCALE = 1;
	private BufferedImage image;
	public static Random rand;
	public static Menu menu;
	private boolean caps;

	public Main() {
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		requestFocus();
		rand = new Random();
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		initFrame();
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		menu = new Menu();
	}

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
					Image image = toolkit.getImage(getClass().getResource("/cursorStandart.png"));
					Cursor c = toolkit.createCustomCursor(image, new Point(0, 0), "img");
					frame.setCursor(c);
				} catch (Exception e) {

				}
			} else {
				try {
					Toolkit toolkit = Toolkit.getDefaultToolkit();
					Image image = toolkit.getImage(getClass().getResource("/cursorBar.png"));
					Cursor c = toolkit.createCustomCursor(image, new Point(0, 0), "img");
					frame.setCursor(c);
				} catch (Exception e) {

				}
			}
		} else {
			try {
				Toolkit toolkit = Toolkit.getDefaultToolkit();
				Image image = toolkit.getImage(getClass().getResource("/cursorHand.png"));
				Cursor c = toolkit.createCustomCursor(image, new Point(0, 0), "img");
				frame.setCursor(c);
			} catch (Exception e) {

			}
		}

	}

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
//		frame.setAlwaysOnTop(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public synchronized void start() {
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}

	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		Main game = new Main();
		game.start();
	}

	public void tick() {
		menu.tick();
		attMouse();
	}

	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = image.getGraphics();
		g.setColor(new Color(0, 0, 0));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		menu.render(g);
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		g.setFont(new Font("arial", Font.BOLD, 20));
		bs.show();
	}

	public void run() {
		long lastTime = System.nanoTime();
		double amongOfTicks = 60.0;
		double delta = 0;
		double ns = 1000000000 / amongOfTicks;
		int frames = 0;
		double timer = System.currentTimeMillis();
		while (isRunning) {

			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1) {
				tick();
				render();
				frames++;
				delta--;
			}
			if (System.currentTimeMillis() - timer >= 1000) {
				System.out.println("FPS:" + frames);
				frames = 0;
				timer += 1000;
			}
		}

		stop();
	}

	public void keyPressed(KeyEvent e) {
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
		if (e.getKeyCode() == KeyEvent.VK_TAB) {
			int i = 99;
			for (int j = 0; j < campos.size(); j++) {
				if (campos.get(j).clicou()) {
					i = j;
				}
			}
			System.out.println(i);
			if (i == campos.size() - 1) {
				campos.get(i).desclicar();
				campos.get(0).clicar();
				i = 99;
			} else {
				campos.get(i).desclicar();
				campos.get(i + 1).clicar();
				i = 99;
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_0) {
			for (int j = 0; j < campos.size(); j++) {
				if (campos.get(j).clicou()) {
					campos.get(j).addLetra("0");
				}
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_1) {
			for (int j = 0; j < campos.size(); j++) {
				if (campos.get(j).clicou()) {
					campos.get(j).addLetra("1");
				}
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_2) {
			for (int j = 0; j < campos.size(); j++) {
				if (campos.get(j).clicou()) {
					campos.get(j).addLetra("2");
				}
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_3) {
			for (int j = 0; j < campos.size(); j++) {
				if (campos.get(j).clicou()) {
					campos.get(j).addLetra("3");
				}
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_4) {
			for (int j = 0; j < campos.size(); j++) {
				if (campos.get(j).clicou()) {
					campos.get(j).addLetra("4");
				}
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_5) {
			for (int j = 0; j < campos.size(); j++) {
				if (campos.get(j).clicou()) {
					campos.get(j).addLetra("5");
				}
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_6) {
			for (int j = 0; j < campos.size(); j++) {
				if (campos.get(j).clicou()) {
					campos.get(j).addLetra("6");
				}
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_7) {
			for (int j = 0; j < campos.size(); j++) {
				if (campos.get(j).clicou()) {
					campos.get(j).addLetra("7");
				}
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_8) {
			for (int j = 0; j < campos.size(); j++) {
				if (campos.get(j).clicou()) {
					campos.get(j).addLetra("8");
				}
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_9) {
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
						campos.get(j).addLetra("A");
					} else {
						campos.get(j).addLetra("a");
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
						campos.get(j).addLetra("E");
					} else {
						campos.get(j).addLetra("e");
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
						campos.get(j).addLetra("I");
					} else {
						campos.get(j).addLetra("i");
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
						campos.get(j).addLetra("O");
					} else {
						campos.get(j).addLetra("o");
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
						campos.get(j).addLetra("U");
					} else {
						campos.get(j).addLetra("u");
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

	}

	public void keyTyped(KeyEvent e) {

	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseMoved(MouseEvent e) {
		menu.setMouse(e.getX(), e.getY());
		menu.mover();
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		menu.pressionar();
	}

	public void mouseReleased(MouseEvent e) {
		menu.setMouse(e.getX(), e.getY());
		menu.soltar();
	}

	public void mouseDragged(MouseEvent e) {

	}
}
