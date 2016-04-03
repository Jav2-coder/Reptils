package net.javierjimenez.Reptils;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	private ComboBox<String> estatAnimal;

	private Statement animals = null;

	private Connection conn = null;

	private String family = null;
	
	private String ord = null;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/reptils", "foot", "ball");
			animals = conn.createStatement();
		} catch (SQLException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Problema de conexión con BD");
			alert.setHeaderText("ERROR: Problema de conexión");
			alert.setContentText("Error al conectar con la base de datos.\nRevise si esta operativa.");
			alert.showAndWait();

			System.exit(0);
		}
		familia.getItems().addAll("Amfibis", "Rèptils");
	}

	public void seleccionarOrdre(ActionEvent event) {

		ord = ordre.getValue().toString();
		
		System.out.println(ord);
		
		ResultSet a;
		
		try {
			a = animals.executeQuery("SELECT * FROM animals WHERE ordre = "
					+ "(SELECT codi from ordres WHERE nom = '" + ord + "')");
			
			nomAnimal.setText(a.getString("nom"));
			especieAnimal.setText(a.getString("especie"));
			descripcio.setText(a.getString("descripcio"));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void seleccionarFamilia(ActionEvent event) {

		family = familia.getValue().toString();

		ResultSet ordres;

		try {

			ordres = animals.executeQuery("SELECT * FROM ordres WHERE familia"
					+ " = (SELECT codi FROM families WHERE nom = '" + family + "')");

			while (ordres.next()) {	
				ordre.getItems().add(ordres.getString("nom"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
