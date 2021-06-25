package Entidades;
public class Personagem {
	
	private Integer id;
	private String nome;
	private Integer nivel;
	private String destricao;

	public Personagem(String nome, Integer nivel, String destricao) {
		this.nome = nome;
		this.nivel = nivel;
		this.destricao = destricao;
	}
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Integer getNivel() {
		return nivel;
	}
	public void setNivel(Integer nivel) {
		this.nivel = nivel;
	}
	public String getDestricao() {
		return destricao;
	}
	public void setDestricao(String destricao) {
		this.destricao = destricao;
	}
	@Override
	public String toString() {
		return "Personagem [nome=" + nome + ", nivel=" + nivel + ", destricao=" + destricao + "]";
	}
	
	
}