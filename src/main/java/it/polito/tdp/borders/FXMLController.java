
package it.polito.tdp.borders;

import java.net.URL;

import java.util.ResourceBundle;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;
    
    @FXML
    private ComboBox<Country>boxStato;
    
    @FXML
    private Button btnVicini;

    @FXML // fx:id="txtAnno"
    private TextField txtAnno; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    boolean doCalcolaConfini(ActionEvent event) {
    	
    	txtResult.clear();
    	Graph<Country, DefaultEdge> grafo;
    	try {
    		if (Integer.parseInt(txtAnno.getText())<=2016 && Integer.parseInt(txtAnno.getText())>=1816) {
    			grafo = model.creaGrafo(Integer.parseInt(txtAnno.getText()));
    			for (Country c : grafo.vertexSet()) {
    				txtResult.appendText(c.toString()+" : "+ Graphs.neighborListOf(grafo, c).size() +"\n");
    			}
    			txtResult.appendText("le componenti connesse sono : "+model.componenti());
    			return true;
       		}
    		else {
    			txtResult.appendText("l'anno non è disponibile");
    			return false;
    		}
    	} catch (Exception e) {
    		txtResult.setText("Errore nell'inserimento dell'anno");
    		return false;
    	}
    }
    
    @FXML
    void doTrovaVicini(ActionEvent event) {
    	
    	if (this.doCalcolaConfini(event)) {
    	txtResult.clear();
    	if (boxStato.getValue()==null) {
    		txtResult.appendText("devi selezionare uno stato");
    		return ; }
    	if (model.getGrafo().containsVertex(boxStato.getValue())) {
    		txtResult.appendText("gli stati raggiungibili sono: \n");
    		for (Country c : model.visitaAmpiezza(boxStato.getValue()))
    			txtResult.appendText(c.toString()+"\n");
    	}
    	else {
    		txtResult.appendText("lo stato non è presente nel grafo");
    	}
    }}

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
    	assert boxStato != null : "fx:id=\"boxStato\" was not injected: check your FXML file 'Scene.fxml'.";
    	assert btnVicini != null : "fx:id=\"btnVicini\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	boxStato.getItems().addAll(model.tuttiStati());
    }
}
