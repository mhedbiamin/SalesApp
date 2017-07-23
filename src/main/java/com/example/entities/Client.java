package com.example.entities;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@SuppressWarnings("serial")
@Entity
public class Client implements Serializable {
@Id @GeneratedValue
	private Long code;
	private String nom;
	@OneToMany(mappedBy="client")
	private Collection<Compte> comptes;
	public Long getCode() {
		return code;
	}
	public Collection<Compte> getComptes() {
		return comptes;
	}
	public void setComptes(Collection<Compte> comptes) {
		this.comptes = comptes;
	}
	public void setCode(Long code) {
		this.code = code;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public Client() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Client( String nom) {
		super();
		
		this.nom = nom;
	}
	
}

