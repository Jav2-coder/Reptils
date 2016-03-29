package net.javierjimenez.Reptils;

import java.net.URL;
import java.util.ResourceBundle;

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
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
}
