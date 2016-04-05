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

public class ReptilsController implements Initializable {
	@FXML
	private ImageView imgAnimal;
	@FXML
	private TextArea descripcio;
	@FXML
	private TextField nomAnimal;
	@FXML
	private TextField especieAnimal;
	@FXML
	private Button guardar;
	@FXML
	private Button animalAnt;
	@FXML
	private Button animalSeg;
	@FXML
	private ComboBox<String> familia;
	@FXML
	private ComboBox<String> ordre;
	@FXML
	private ComboBox<String> estatAnimal = new ComboBox<>();

	private Statement beasts = null;

	private Connection conn = null;

	private String family = null;

	private String ord = null;

	private int index;

	private List<Animal> animals = new ArrayList<Animal>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		try {
			//conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/reptils", "foot", "ball");
			conn = DriverManager.getConnection("jdbc:mysql://172.17.0.2:3306/reptils", "foot", "ball");
			beasts = conn.createStatement();
		} catch (SQLException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Problema de conexión con BD");
			alert.setHeaderText("ERROR: Problema de conexión");
			alert.setContentText("Error al conectar con la base de datos.\nRevise si esta operativa.");
			alert.showAndWait();

			System.exit(0);
		}
		familia.getItems().addAll("Amfibis", "Rèptils");

		animalAnt.setDisable(true);
		animalSeg.setDisable(true);
	}

	public void seleccionarOrdre(ActionEvent event) {

		if (ordre.getItems() != null && !ordre.getItems().isEmpty()) {

			index = 0;
			
			generarEstats();
			
			animals.clear();

			ord = ordre.getValue().toString();

			ResultSet a;

			try {
				a = beasts.executeQuery(
						"SELECT * FROM animals WHERE ordre = " + "(SELECT codi from ordres WHERE nom = '" + ord + "')");

				while (a.next()) {
					Animal A = new Animal(a.getInt("codi"), a.getString("nom"), a.getInt("ordre"),
							a.getString("especie"), a.getString("descripcio"), a.getString("estat"),
							a.getString("imatge"));
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

	public void generarEstats(){
		estatAnimal.getItems().addAll(
				"Extinta",
				"Extinta en estat salvatge",
				"En perill greu",
				"En perill",
				"Vulnerable",
				"Depèn de la conservació",
				"Gairebé amenaçada",
				"Risc mínim",
				"No avaluada");
	}
	
	public void seleccionarFamilia(ActionEvent event) {

		animalAnt.setDisable(true);
		animalSeg.setDisable(true);
		
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
}
