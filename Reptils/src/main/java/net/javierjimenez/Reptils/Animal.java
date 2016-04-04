package net.javierjimenez.Reptils;

import java.net.URL;

public class Animal {

	private int codi;
	private String nom;
	private int ordre;
	private String especie;
	private String descripcio;
	private String estat;
	private String imatge;
	
	public Animal(int code, String name, int order, String species, String description, String state, String image) {
		this.codi = code;
		this.nom = name;
		this.ordre = order;
		this.especie = species;
		this.descripcio = description;
		this.estat = state;
		this.imatge = image;
	}
	
	public int getCodi() {
		return codi;
	}

	public void setCodi(int codi) {
		this.codi = codi;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getOrdre() {
		return ordre;
	}

	public void setOrdre(int ordre) {
		this.ordre = ordre;
	}

	public String getEspecie() {
		return especie;
	}

	public void setEspecie(String especie) {
		this.especie = especie;
	}

	public String getDescripcio() {
		return descripcio;
	}

	public void setDescripcio(String descripcio) {
		this.descripcio = descripcio;
	}

	public String getEstat() {
		return estat;
	}

	public void setEstat(String estat) {
		this.estat = estat;
	}

	public String getImatge() {
		return imatge;
	}

	public void setImatge(String imatge) {
		this.imatge = imatge;
	}
}
