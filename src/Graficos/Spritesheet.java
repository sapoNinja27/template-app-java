package Graficos;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Uma classe para gerenciar imagens
 * 
 * @apiNote definir uma spritesheet no menu oferecendo o path da imagem, ent�o a
 *          imagem sera acessivel para recorte de forma simples
 */
public class Spritesheet {
	private BufferedImage spritesheet;

	/**
	 * Armazena a imagem
	 * 
	 * @param path : caminho da imagem
	 *
	 */
	public Spritesheet(String path) {
		try {
			spritesheet = ImageIO.read(getClass().getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Retorna um recorte da imagem
	 * 
	 * @param x      : tamanho horizontal do recorte
	 * @param y      : tamanho vertical do recorte
	 * @param width  : posi��o horizontal do recorte
	 * @param height : posi��o vertical do recorte
	 */
	public BufferedImage getSprite(int x, int y, int width, int height) {
		return spritesheet.getSubimage(x, y, width, height);
	}
}
