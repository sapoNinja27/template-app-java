package Entidades;

public class Ficha {

	private Integer id;
	private Usuario usuario;
	private Personagem personagem;
	private Jogo jogo;

	public Ficha(Usuario usuario, Personagem personagem, Jogo jogo) {
		this.usuario = usuario;
		this.personagem = personagem;
		this.jogo=jogo;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Personagem getPersonagem() {
		return personagem;
	}
	public void setPersonagem(Personagem personagem) {
		this.personagem = personagem;
	}
	public Jogo getJogo() {
		return jogo;
	}
	public void setJogo(Jogo jogo) {
		this.jogo = jogo;
	}

	@Override
	public String toString() {
		return "Ficha [usuario=" + usuario + ", personagem=" + personagem + ", jogo=" + jogo + "]";
	}
	
	
}
