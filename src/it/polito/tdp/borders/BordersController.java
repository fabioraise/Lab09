/**
 * Skeleton for 'Borders.fxml' Controller Class
 */

package it.polito.tdp.borders;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class BordersController {

	Model model;

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;
	
	@FXML
    private ComboBox<Country> countriesComboBox;
	
	@FXML
    private Button trovaViciniButton;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="txtAnno"
	private TextField txtAnno; // Value injected by FXMLLoader

	@FXML // fx:id="txtResult"
	private TextArea txtResult; // Value injected by FXMLLoader

	@FXML
	void doCalcolaConfini(ActionEvent event) {
		txtResult.clear();
		int year = 0;
		try {
			year = Integer.parseInt(txtAnno.getText());
			
			model.generaGrafo(year);
			
			String dati = model.stampaDati();
			
			txtResult.appendText(dati);
			
			txtResult.appendText("\nNumero di componenti connesse: "+model.getNumeroComponentiConnesse());
		} catch (NumberFormatException e) {
			txtResult.setText("Devi inserire un anno compreso tra il 1816 e 2016!");
		}
		
		populateComboBox();
	}
	
	 @FXML
	 void doTrovaVicini(ActionEvent event) {
		 txtResult.clear();
		 
		 Country selected = countriesComboBox.getValue();
		 List<Country> vicini = model.trovaVicini(selected);
		 
		 for(Country c : vicini)
			 txtResult.appendText(""+c.toString()+"\n");
	 }


	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Borders.fxml'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Borders.fxml'.";
		assert countriesComboBox != null : "fx:id=\"countriesComboBox\" was not injected: check your FXML file 'Borders.fxml'.";
        assert trovaViciniButton != null : "fx:id=\"trovaViciniButton\" was not injected: check your FXML file 'Borders.fxml'.";
        
       
	}

	public void setModel(Model model) {
		this.model = model;
	}
	
	private void populateComboBox() {
		List<Country> countries = model.getCountriesList();
		
		Collections.sort(countries);
		
		countriesComboBox.getItems().addAll(countries);
	}
}
