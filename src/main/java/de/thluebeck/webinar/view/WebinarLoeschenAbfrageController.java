package de.thluebeck.webinar.view;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

//### nur fuer die ALTERNATIV zu fxml in Java programmierte Pane ###
import javafx.scene.control.Button;
import javafx.scene.control.Label;


/**
 * Controller fuer die Sicherheitsabfrage zum Loeschen eines Webinars.
 * @author holger
 *
 */
public class WebinarLoeschenAbfrageController {

    private Stage popupStage;
    private boolean bestaetigt = false;

    
    /**
     * Initialisiert das Controller-Objekt. Wird automatisch aufgerufen nach dem Laden der fxml-Datei.
     */
    @FXML
    public void initialize() {
    	//nichts zu tun
    }

    
    /** ### ALTERNATIV zu fxml: in Java programmierte Pane ###
     * 
     * @return die erzeugte Pane
     */
    public Pane getPane() {
    	AnchorPane anchorPane = new AnchorPane();

    	anchorPane.setPrefHeight(100.0);
    	anchorPane.setPrefWidth(300.0);

    	Label frageLabel = new Label();
    	frageLabel.setText("Selektiertes Webinar wirklich löschen?");
    	frageLabel.setLayoutX(32.0);
    	frageLabel.setLayoutY(14.0);
    	frageLabel.setPrefHeight(17.0);
    	frageLabel.setPrefWidth(203.0);
    	AnchorPane.setLeftAnchor(frageLabel, 49.0);
    	AnchorPane.setTopAnchor(frageLabel, 20.0);

    	Button jaButton = new Button();
    	jaButton.setText("Ja");
    	jaButton.setOnAction(e -> handleJa());
    	jaButton.setLayoutX(56.0);
    	jaButton.setLayoutY(50.0);
    	jaButton.setPrefHeight(25.0);
    	jaButton.setPrefWidth(80.0);
    	AnchorPane.setBottomAnchor(jaButton, 20.0);
    	AnchorPane.setLeftAnchor(jaButton, 60.0);
    	
    	Button neinButton = new Button();
    	neinButton.setText("Nein");
    	neinButton.setOnAction(e -> handleNein());
    	neinButton.setLayoutX(144.0);
    	neinButton.setLayoutY(50.0);
    	neinButton.setPrefHeight(25.0);
    	neinButton.setPrefWidth(80.0);
    	AnchorPane.setBottomAnchor(neinButton, 20.0);
    	AnchorPane.setRightAnchor(neinButton, 60.0);
    	
    	anchorPane.getChildren().addAll(frageLabel, jaButton, neinButton);

    	return anchorPane;
    }

    
    /**
     * Setzt die (secondary) Stage fuer dieses Popup.
     * 
     * @param popupStage
     */
    public void setPopupStage(Stage popupStage) {
        this.popupStage = popupStage;
    }

    
    /**
     * Liefert zurueck, ob das Formular mit "Bestaetigen" oder "Abbrechen" geschlossen wurde.
     * 
     * @return true, falls bestaetigt, false sonst.
     */
    public boolean istBestaetigt() {
        return bestaetigt;
    }

    
    /**
     * Handler fuer Clicks auf "Ja". Instanzvariable bestaetigt wird auf true gesetzt.
     * Anschliessend wird das Popup geschlossen.
     */
    @FXML
    private void handleJa() {
        bestaetigt = true;
        popupStage.close();		//Fenster schliessen
    }

    
    /**
     * Handler fuer Clicks auf "Nein". Instanzvariable bestaetigt wird auf false gesetzt.
     * Anschliessend wird das Popup geschlossen.
     */
    @FXML
    private void handleNein() {
        bestaetigt = false;
        popupStage.close();		//Fenster schliessen
    }

}
