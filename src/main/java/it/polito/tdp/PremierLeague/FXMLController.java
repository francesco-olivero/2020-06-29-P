/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.PremierLeague;

import java.net.URL;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.PremierLeague.model.Match;
import it.polito.tdp.PremierLeague.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnConnessioneMassima"
    private Button btnConnessioneMassima; // Value injected by FXMLLoader

    @FXML // fx:id="btnCollegamento"
    private Button btnCollegamento; // Value injected by FXMLLoader

    @FXML // fx:id="txtMinuti"
    private TextField txtMinuti; // Value injected by FXMLLoader

    @FXML // fx:id="cmbMese"
    private ComboBox<Month> cmbMese; // Value injected by FXMLLoader

    @FXML // fx:id="cmbM1"
    private ComboBox<Match> cmbM1; // Value injected by FXMLLoader

    @FXML // fx:id="cmbM2"
    private ComboBox<Match> cmbM2; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doConnessioneMassima(ActionEvent event) {
    	Month month = this.cmbMese.getValue();
    	try {
    		int minuti = Integer.parseInt(this.txtMinuti.getText());
    		this.txtResult.appendText(this.model.getMax(month, minuti));
    	}catch (NumberFormatException nfe) {
    		txtResult.appendText("ERRORE: Il campo soglia deve essere numerico\n");
    		return ;
    	}
    	
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	this.txtResult.clear();
    	Month month = this.cmbMese.getValue();
    	try {
    		int minuti = Integer.parseInt(this.txtMinuti.getText());
    		this.txtResult.appendText(this.model.creaGrafo(month, minuti));
    		
    		this.cmbM1.getItems().addAll(this.model.getIdMapMatches().values());
    		this.cmbM2.getItems().addAll(this.model.getIdMapMatches().values());
        	
    	}catch (NumberFormatException nfe) {
    		txtResult.appendText("ERRORE: Il campo soglia deve essere numerico\n");
    		return ;
    	}
    }

    @FXML
    void doCollegamento(ActionEvent event) {
    	Match m1 = this.cmbM1.getValue();
    	Match m2 = this.cmbM2.getValue();
    	
    	List<Match> list = this.model.percorsoMigliore(m1, m2);
    	if (!list.isEmpty() && list!=null) {
    		this.txtResult.appendText("\n\nPercorso migliore:\n");
    		for (Match m: list) {
    			this.txtResult.appendText(m+"\n");
    		}
    		this.txtResult.appendText("Peso del percorso: "+this.model.getPesoBest());
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnConnessioneMassima != null : "fx:id=\"btnConnessioneMassima\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCollegamento != null : "fx:id=\"btnCollegamento\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMinuti != null : "fx:id=\"txtMinuti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbMese != null : "fx:id=\"cmbMese\" was not injected: check your FXML file 'Scene.fxml'.";        assert cmbM1 != null : "fx:id=\"cmbM1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbM2 != null : "fx:id=\"cmbM2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	for (int i=1; i<=12; i++) {
    		this.cmbMese.getItems().add(Month.of(i));
    	}
  
    }
    
    
}
