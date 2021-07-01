package JObjects;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import Main.Main;

/**
 * Campo De Texto
 * 
 * @apiNote : campo de texto customizavel com algumas mascaras, tambem é
 *          possivel modificar o tamanho e usar como uma caixa de texto, aceita
 *          senhas e texto modelo
 * @apiNote Configurações do objeto assim como sua inclusão na lista do main são
 *          feitas no init() da pagina
 */
public class CampoDeTexto {
	private int x, y, w, h;
	private String text;
	private boolean template = true;
	private String newText = "";
	private String mask = "vazio", textoSenha = "";
	private boolean mouseOver, clicou, clicouDentro;
	private int frames = 0, frames2 = 0;
	private int mx, my;
	private boolean senha;
	private boolean box;
	private boolean estatico = false;
	private String[] textBox;
	private int maxSize = 10;

	/**
	 * Cria o campo de texto
	 * 
	 * @param x        : posição horizontal
	 * @param y        : posição vertical
	 * @param width    : tamanho horizontal
	 * @param height   : tamanho vertical
	 * @param template : modelo que vai aparecer antes do usuario preencher
	 */
	public CampoDeTexto(int x, int y, int w, int h, String text) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.text = text;
	}

	/**
	 * Adiciona um texto diretamente
	 * 
	 * @param text : texto do campo
	 */
	public void setText(String text) {
		template = false;
		if (senha) {
			for (int i = text.length(); i > 0; i--) {
				textoSenha += "o";
			}
		}
		newText = text;

	}

	/**
	 * Liga ou desliga o campo
	 * 
	 * @param est : true ou false
	 */
	public void estatico(boolean est) {
		this.estatico = est;
	}

	/**
	 * Retorna o texto atual, não funciona com o modelo
	 */
	public String getTexto() {
		String newText1 = "";
		if (senha) {
			char[] trans = newText.toCharArray();
			for (int i = 0; i < trans.length; i++) {
				trans[i] = (char) (trans[i] + 10);
			}
			for (int i = 0; i < trans.length; i++) {
				newText1 = newText1 + trans[i];
			}
			return newText1;
		}
		return newText;
	}

	/**
	 * Ativar senha vai fazer o campo criptografar o que for digitado e esconder
	 * seus valores
	 */
	public void setSenha() {
		senha = true;
	}

	/**
	 * Configura o tamanho do campo(apenas para text box)
	 * 
	 * @param newSize : um valor multiplicado por
	 */
	public void setSize(int newSize) {
		maxSize = newSize;
	}

	/**
	 * Retorna se o mouse esta em cima do campo atual
	 */
	public boolean mouseOver() {
		return mouseOver;
	}

	/**
	 * Função backspace
	 */
	public void removeLetra() {
		newText = newText.substring(0, newText.length() - 1);
		textoSenha = textoSenha.substring(0, textoSenha.length() - 1);
	}

	/**
	 * Função para escrever recebendo uma letra Existem alguns parametros de
	 * formatação
	 */
	public void addLetra(String letra) {
		if (newText.length() < maxSize) {
			if (mask.equals("Numero")) {
				maxSize = 10;
				letra = letra.replaceAll("[^\\d]", "");
				newText = newText + letra;
			}

			if (mask.equals("Data")) {
				maxSize = 10;
				letra = letra.replaceAll("[^\\d]", "");
				newText = newText + letra;
				if (newText.length() == 2) {
					newText += "/";
				}
				if (newText.length() == 5) {
					newText += "/";
				}
			}
			if (mask.equals("vazio")) {
				newText = newText + letra;
				if (senha) {
					textoSenha = textoSenha + "o";
				}
			}
			if (mask.equals("Cep")) {
				maxSize = 9;
				letra = letra.replaceAll("[^\\d]", "");
				newText = newText + letra;

				if (newText.length() == 5) {
					newText += "-";
				}

			}
			if (mask.equals("Telefone")) {
				maxSize = 14;
				letra = letra.replaceAll("[^\\d]", "");
				if (newText.length() == 0) {
					newText = "(" + letra;
				} else {
					newText = newText + letra;
				}
				if (newText.length() == 3) {
					newText += ")";
				}
				if (newText.length() < 14) {
					if (newText.length() == 8) {
						newText += "-";
					}
				} else {
					newText = newText.substring(0, 8) + newText.charAt(9) + "-" + newText.substring(10, 14);
				}

			}
		}
	}

	/**
	 * Retorna se o campo foi pressionado
	 */
	public boolean clicou() {
		return clicou;
	}

	/**
	 * Configura o tipo de valor que vai ser aceito pelo campo tipos possiveis são :
	 * "data", "numero" e "cep"
	 */
	public void setMask(String mask) {
		this.mask = mask;
	}

	/**
	 * Altera o campo de texto num formato txtbox
	 * 
	 * @param tam : quantidade de linhas
	 * @apiNote : o tamanho horizontal é padrão 320, e o padrão vertical
	 *          nescessariamente precisa ser declarado como 15*tam
	 */
	public void setBox(int tam) {
		box = true;
		textBox = new String[tam];
	}

	/**
	 * Função para apertar tab e ir para o proximo campo
	 * 
	 * @apiNote : Não funciona
	 */
	public void passar() {
		desclicar();
		for (int i = 0; i < Main.campos.size(); i++) {
			if (Main.campos.get(i) == this) {
				Main.campos.get(i + 1).clicar();
				break;
			}
		}
	}

	/**
	 * Função para pressionar o campo
	 */
	public void clicar() {
		clicou = true;
		template = false;
	}

	/**
	 * Função para deixar o campo, se não teve nada digitado o valor voltará ao
	 * modelo
	 */
	public void desclicar() {
		clicou = false;
		if (newText == "") {
			template = true;
		}
	}

	/**
	 * Função pra aparecer aquela barrinha piscando ao digitar
	 */
	public boolean eventDigitando() {
		if (newText != "") {
			return true;
		}
		return false;
	}

	/**
	 * Funções tick são chamadas indefinidamente durante a aplicação atualiza a
	 * posição do mouse em relação ao objeto
	 */
	public void tick() {
		frames++;
		if (frames >= 26) {
			frames = 0;
			frames2++;
			if (frames2 >= 2) {
				frames2 = 0;
			}
		}
		mx = Main.menu.getMouseX();
		my = Main.menu.getMouseY();
		if (Main.menu.pressionou()) {
			if (mx > x - 12 && mx < x + w && my > y - 9 && my < y + h) {
				clicouDentro = true;
			}
		}
		if (Main.menu.soltou()) {
			// soltou
			if (mx > x - 12 && mx < x + w && my > y - 9 && my < y + h) {
				// dentro
				if (clicouDentro) {
					clicouDentro = false;
					template = false;
					mouseOver = true;
					clicou = true;
				}
			} else {
				// fora
				mouseOver = false;
				if (newText == "") {
					template = true;
				} else {
					clicou = false;
				}

			}

		} else {
			// so movendo
			if (mx > x - 12 && mx < x + w && my > y - 19 && my < y + h) {
				// mouse em cima
				mouseOver = true;
			} else {
				// fora
				mouseOver = false;
			}
		}

	}

	/**
	 * Renderiza o objeto, tambem configura como ele vai aparecer
	 * 
	 * @param g : tipo grafico do java instanciado no main
	 */
	public void render(Graphics g) {
		if (!estatico) {
			tick();
		}
		int borda = 1;
		g.setColor(Color.black);
		g.fillRect(x - borda, y - borda, w + borda * 2, h + borda * 2);
		g.setColor(Color.white);
		g.fillRect(x, y, w, h);
		g.setFont(new Font("arial", Font.BOLD, 13));
		if (template) {
			g.setColor(Color.gray);
			g.drawString(text, x + 3, y + 13);
		} else {
			g.setColor(Color.black);
			if (box) {
				if (newText.length() < 37) {
					textBox[0] = newText.substring(0);
					g.drawString(textBox[0], x + 3, y + 13);
					if (frames2 == 1) {
						g.drawString(textBox[0] + "|", x + 3, y + 13);
					}
				} else if (newText.length() >= 37 && newText.length() < 74) {
					textBox[0] = newText.substring(0, 36);
					g.drawString(textBox[0], x + 3, y + 13);
					textBox[1] = newText.substring(37);
					g.drawString(textBox[1], x + 3, y + 13 + 15);
					if (frames2 == 1) {
						g.drawString(textBox[1] + "|", x + 3, y + 13 + 15);
					}
				} else {
					textBox[0] = newText.substring(0, 36);
					g.drawString(textBox[0], x + 3, y + 13);
					textBox[1] = newText.substring(37, 73);
					g.drawString(textBox[1], x + 3, y + 13 + 15);
					textBox[2] = newText.substring(74);
					g.drawString(textBox[2], x + 3, y + 13 + 30);
					if (frames2 == 1) {
						g.drawString(textBox[2] + "|", x + 3, y + 13 + 30);
					}
				}

			} else {
				if (senha) {
					g.drawString(textoSenha, x + 3, y + 13);
				} else {
					g.drawString(newText, x + 3, y + 13);
				}
				if (clicou) {
					if (frames2 == 1) {
						if (senha) {
							g.drawString(textoSenha + "|", x + 3, y + 13);
						} else {
							g.drawString(newText + "|", x + 3, y + 13);
						}
					}
				}
			}
		}

	}

}
