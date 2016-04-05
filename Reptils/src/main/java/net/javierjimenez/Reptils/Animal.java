package net.javierjimenez.Reptils;

/**
 * 
 * @author Surrui
 *
 */
public class Animal {

	/**
	 * Variable String que conte el nom del Animal
	 */
	private String nom;
	
	/**
	 * Variable String que conte la especie del Animal
	 */
	private String especie;
	
	/**
	 * Variable String que conte la descripcio del Animal
	 */
	private String descripcio;
	
	/**
	 * Variable String que conte l'estat del Animal
	 */
	private String estat;
	
	/**
	 * Variable String que conté la url de la imatge del Animal
	 */
	private String imatge;
	
	/**
	 * Constructor principal del objecte Animal
	 * 
	 * @param name variable String
	 * @param species variable String
	 * @param description variable String
	 * @param state variable String
	 * @param image variable String
	 */
	public Animal(String name, String species, String description, String state, String image) {
		
		this.nom = name;
		this.especie = species;
		this.descripcio = description;
		this.estat = state;
		this.imatge = image.replace("://", ":");
	}
	
	/**
	 * Metode que retorna el nom de l'objecte Animal.
	 * 
	 * @return
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Metode que modifica la variable nom de l'objecte Animal
	 * 
	 * @param nom Variable String
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * Metode que retorna la especie de l'objecte Animal.
	 * 
	 * @return
	 */
	public String getEspecie() {
		return especie;
	}

	/**
	 * Metode que modifica la variable especie de l'objecte Animal
	 * 
	 * @param especie Variable String
	 */
	public void setEspecie(String especie) {
		this.especie = especie;
	}

	/**
	 * Metode que retorna la descripcio de l'objecte Animal.
	 * 
	 * @return
	 */
	public String getDescripcio() {
		return descripcio;
	}

	/**
	 * Metode que modifica la variable descripcio de l'objecte Animal
	 *  
	 * @param descripcio Variable String
	 */
	public void setDescripcio(String descripcio) {
		this.descripcio = descripcio;
	}

	/**
	 * Metode que retorna el estat de l'objecte Animal.
	 * 
	 * @return
	 */
	public String getEstat() {
		return estat;
	}

	/**
	 * Metode que modifica la variable estat de l'objecte Animal
	 * 
	 * @param estat Variable String
	 */
	public void setEstat(String estat) {
		this.estat = estat;
	}

	/**
	 * Metode que retorna l'enllaç de la imatge de l'objecte Animal.
	 * 
	 * @return
	 */
	public String getImatge() {
		return imatge;
	}

	/**
	 * Metode que modifica la variable imatge de l'objecte Animal
	 * 
	 * @param imatge Variable String
	 */
	public void setImatge(String imatge) {
		this.imatge = imatge;
	}
}
