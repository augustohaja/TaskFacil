package br.edu.ifsp.taskfacil.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Task {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column (length=100)
	private String titulo;
	@Column (length=200)
	private String descricao;
	@Column (length=100)
	private String local;
	private String data;
	
	@ManyToMany
	private List<User> lista;
	
	public Task(){
		
	}

	public Task(String titulo, String descricao, String local, String data) {
		this.titulo = titulo;
		this.descricao = descricao;
		this.local = local;
		this.data = data;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
	

	public List<User> getLista() {
		return lista;
	}

	public void setLista(List<User> lista) {
		this.lista = lista;
	}
	
	public List getLista(List<User> lista){
		return this.lista;
	}
	
	public void setColaborador(User user){
		if (!this.lista.contains(user))
			this.lista.add(user);
	}

	public void removeColaborador(User user){
		if (this.lista.contains(user)){
			this.lista.remove(user);
		}
	}
	
	@Override
	public String toString() {
		return "Task [id=" + id + ", titulo=" + titulo + ", descricao=" + descricao + ", local=" + local + ", data="
				+ data + "]";
	}
}
