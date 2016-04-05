package net.javierjimenez.Reptils;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import java.sql.Statement;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * 
 * @author Surrui
 *
 */
public class ReptilsController implements Initializable {

	/**
	 * Objecte ImageView que conte la imatge de l'animal que es mostra
	 * actualment.
	 */
	@FXML
	private ImageView imgAnimal;

	/**
	 * Objecte TextArea que conte la descripcio de l'animal que es mostra
	 * actualment.
	 */
	@FXML
	private TextArea descripcio;

	/**
	 * Objecte TextField que conte el nom de l'animal que es mostra actualment.
	 */
	@FXML
	private TextField nomAnimal;

	/**
	 * Objecte TextField que conte la especie de l'animal que es mostra
	 * actualment.
	 */
	@FXML
	private TextField especieAnimal;

	/**
	 * Objecte Button encarregat de guardar els canvis que s'efectuen en les
	 * dades del animal.
	 */
	@FXML
	private Button guardar;

	/**
	 * Objecte Button que permet accedir al anterior animals d'un mateix ordre
	 * en cada clic.
	 */
	@FXML
	private Button animalAnt;

	/**
	 * Objecte Button que permet accedir al següent animals d'un mateix ordre en
	 * cada clic.
	 */
	@FXML
	private Button animalSeg;

	/**
	 * Objecte ComboBox que conte els noms de les families de la base de dades
	 */
	@FXML
	private ComboBox<String> familia;

	/**
	 * Objecte ComboBox que conte els noms dels ordres de dins la base de dades
	 * segons quina familia s'ha escollit.
	 */
	@FXML
	private ComboBox<String> ordre;

	/**
	 * Objecte ComboBox que conte els estats de dins la base de dades i mostra
	 * l'estat del animal que es mostra actualment.
	 */
	@FXML
	private ComboBox<String> estatAnimal = new ComboBox<>();

	/**
	 * Objecte Statement que executará les sentencies SQL i les envia a la Base
	 * de Dades.
	 */
	private Statement beasts = null;

	/**
	 * Objecte Connection que conecta el programa a la nostre base de dades.
	 */
	private Connection conn = null;

	/**
	 * Variable String que utilitzarem per emmagatzemar la familia que hem
	 * escollit
	 */
	private String family = null;

	/**
	 * Variable String que utilitzarem per emmagatzemar l'ordre que hem escollit
	 */
	private String ord = null;

	/**
	 * Variable int que es fara servir per navegar entre els animals que hi ha
	 * en cada ordre.
	 */
	private int index;

	/**
	 * Objecte List que conte els objectes Animal que creem a partir de les
	 * dades de la base de dades.
	 */
	private List<Animal> animals = new ArrayList<Animal>();

	/**
	 * Objecte ResultSet
	 */
	private ResultSet a;

	/**
	 * Metode que inicialitza la connexio amb la Base de Dades i que emplena el
	 * ComboBox familia amb els valors obtinguts de la mateixa.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		ResultSet animalFamily;

		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/reptils", "foot", "ball");
			beasts = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

			animalFamily = beasts.executeQuery("SELECT nom FROM families");

			while (animalFamily.next()) {
				familia.getItems().add(animalFamily.getString("nom"));
			}

		} catch (SQLException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Problema de conexión con BD");
			alert.setHeaderText("ERROR: Problema de conexión");
			alert.setContentText("Error al conectar con la base de datos.\nRevise si esta operativa.");
			alert.showAndWait();

			System.exit(0);
		}

		animalAnt.setDisable(true);
		animalSeg.setDisable(true);
		guardar.setDisable(true);
	}

	/**
	 * Metode encarregat de obtindre les dades dels animals de la base de dades
	 * segons quin ordre hem escollit del ComboBox.
	 * 
	 * @param event
	 */
	public void seleccionarOrdre(ActionEvent event) {

		if (ordre.getItems() != null && !ordre.getItems().isEmpty()) {

			index = 0;

			guardar.setDisable(false);

			generarEstats();

			animals.clear();

			ord = ordre.getValue().toString();

			try {
				a = beasts.executeQuery(
						"SELECT * FROM animals WHERE ordre = " + "(SELECT codi from ordres WHERE nom = '" + ord + "')");

				while (a.next()) {
					Animal A = new Animal(a.getString("nom"), a.getString("especie"), a.getString("descripcio"),
							a.getString("estat"), a.getString("imatge"));
					animals.add(A);
				}

				if (index == 0 && index != (animals.size() - 1)) {
					animalAnt.setDisable(true);
					animalSeg.setDisable(false);
				} else if (index == 0 && index == (animals.size() - 1)) {
					animalAnt.setDisable(true);
					animalSeg.setDisable(true);
				}

				imgAnimal.setImage(new Image(animals.get(index).getImatge()));
				descripcio.setText(animals.get(index).getDescripcio());
				nomAnimal.setText(animals.get(index).getNom());
				especieAnimal.setText(animals.get(index).getEspecie());
				estatAnimal.setValue(animals.get(index).getEstat());

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Metode encarregat d'introduir les diferents opcions dins del ComboBox
	 * estatAnimal
	 */
	public void generarEstats() {

		estatAnimal.getItems().addAll("Extinta", "Extinta en estat salvatge", "En perill greu", "En perill",
				"Vulnerable", "Depèn de la conservació", "Gairebé amenaçada", "Risc mínim", "No avaluada");
	}

	/**
	 * Metode encarregat de obtindre les dades dels ordres dels animals de la
	 * base de dades segons quina familia hem escollit del ComboBox.
	 * 
	 * @param event
	 */
	public void seleccionarFamilia(ActionEvent event) {

		animalAnt.setDisable(true);
		animalSeg.setDisable(true);
		guardar.setDisable(true);

		ordre.getItems().clear();
		estatAnimal.getItems().clear();

		descripcio.setText("");
		nomAnimal.setText("");
		especieAnimal.setText("");
		estatAnimal.setValue("");
		imgAnimal.setImage(null);

		family = familia.getValue().toString();

		ResultSet ordres;

		try {

			ordres = beasts.executeQuery("SELECT * FROM ordres WHERE familia"
					+ " = (SELECT codi FROM families WHERE nom = '" + family + "')");

			while (ordres.next()) {
				ordre.getItems().add(ordres.getString("nom"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metode que recorre l'objecte List cap endevant i mostra les dades del
	 * seguent animal.
	 * 
	 * @param event
	 */
	public void segAnimal(ActionEvent event) {

		if (animals.size() > 1) {

			index++;

			imgAnimal.setImage(new Image(animals.get(index).getImatge()));
			descripcio.setText(animals.get(index).getDescripcio());
			nomAnimal.setText(animals.get(index).getNom());
			especieAnimal.setText(animals.get(index).getEspecie());
			estatAnimal.setValue(animals.get(index).getEstat());

			if (index > 0) {
				animalAnt.setDisable(false);
			}

			if (index == (animals.size() - 1)) {
				animalSeg.setDisable(true);
				animalAnt.setDisable(false);
			}
		}
	}

	/**
	 * Metode que recorre l'objecte List cap enrere i mostra les dades del
	 * animal anterior.
	 * 
	 * @param event
	 */
	public void antAnimal(ActionEvent event) {

		if (index != 0) {

			index--;

			imgAnimal.setImage(new Image(animals.get(index).getImatge()));
			descripcio.setText(animals.get(index).getDescripcio());
			nomAnimal.setText(animals.get(index).getNom());
			especieAnimal.setText(animals.get(index).getEspecie());
			estatAnimal.setValue(animals.get(index).getEstat());

			if (index != 0) {
				animalSeg.setDisable(false);
			}

			if (index == 0) {
				animalSeg.setDisable(false);
				animalAnt.setDisable(true);
			}
		}
	}

	/**
	 * Metode encarregat d'actualitzar les dades en l'objecte List i en la Base
	 * de Dades, pero nomes en el cas de que es faci algun canvi.
	 * 
	 * @param event
	 */
	public void actualizarDatos(ActionEvent event) {
		boolean name, description, species, state;

		name = nomAnimal.getText().equals(animals.get(index).getNom());
		description = descripcio.getText().equals(animals.get(index).getDescripcio());
		species = especieAnimal.getText().equals(animals.get(index).getEspecie());
		state = estatAnimal.getValue().equals(animals.get(index).getEstat());

		if (!name || !description || !species || !state) {

			try {

				a.absolute(index + 1);

				a.updateString("nom", nomAnimal.getText());
				a.updateString("especie", especieAnimal.getText());
				a.updateString("descripcio", descripcio.getText());
				a.updateString("estat", estatAnimal.getValue());

				a.updateRow();

				animals.get(index).setNom(nomAnimal.getText());
				animals.get(index).setEspecie(especieAnimal.getText());
				animals.get(index).setDescripcio(descripcio.getText());
				animals.get(index).setEstat(estatAnimal.getValue());

				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Actualización de la BD");
				alert.setHeaderText("Success!");
				alert.setContentText("Los datos se han actualizado correctamente");
				alert.showAndWait();

			} catch (SQLException e) {

				e.printStackTrace();
			}

		} else {

			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("WARNING: Actualización BD");
			alert.setHeaderText("Warning!");
			alert.setContentText("No hay cambios en los datos\npara generar la actualización.");
			alert.showAndWait();

		}
	}
}
