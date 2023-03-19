package de.thluebeck.webinar.view;

import de.thluebeck.webinar.model.WebinarFilter;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


/**
 * Controller fuer das Formular zum Filtern von Webinaren.
 * @author holger
 *
 */
public class WebinarFilterungController {

	//die folgenden Annotationen dienen der Verknuepfung der Attribute mit den Controls in der fxml-Datei
    @FXML
    private TextField themaSubstringField;
    @FXML
    private TextField festpreisVonField;
    @FXML
    private TextField festpreisBisField;

    private Stage popupStage;
    private WebinarFilter webinarFilter;

    
    /**
     * Initialisiert das Controller-Objekt. Wird automatisch aufgerufen nach dem Laden der fxml-Datei.
     */
    @FXML
    public void initialize() {
    	//nichts zu tun
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
     * Liefert den spezifizierten Webinar-Filter zurueck.
     * 
     * @return Der spezifizierte Webinar-Filter (null, falls abgebrochen wurde)
     */
    public WebinarFilter getWebinarFilter() {
        return webinarFilter;
    }

    
    /**
     * Handler fuer Clicks auf "Filtern". Die Eingaben werden geprueft und - falls gueltig - in das WebinarFilter-Objekt eingetragen.
     * Anschliessend wird das Popup geschlossen.
     */
    @FXML
    private void handleFiltern() {
        if (eingabenGueltig()) {
        	int festpreisVon, festpreisBis;
        	
        	//falls festpreisVon und/oder festpreisBis nicht eingegeben, Filterwert auf -1 setzen (wird dann im Model als Kriterium ignoriert)
            if (festpreisVonField.getText() != null && festpreisVonField.getText().length() > 0) {
            	festpreisVon = Integer.parseInt(festpreisVonField.getText());
            } else {
            	festpreisVon = -1;
            }
            
            if (festpreisBisField.getText() != null && festpreisBisField.getText().length() > 0) {
            	festpreisBis = Integer.parseInt(festpreisBisField.getText());
            } else {
            	festpreisBis = -1;
            }
            
            //Filterkriterien in Filterobjekt eintragen (leeren Thema-Substring als "" uebergeben)
        	webinarFilter = new WebinarFilter(themaSubstringField.getText() == null ? "" : themaSubstringField.getText(), 
        										festpreisVon, festpreisBis);

            popupStage.close();		//Fenster schliessen
        }
    }

    
    /**
     * Handler fuer Clicks auf "Abbrechen". Popup wird ohne Datenuebernahme geschlossen.
     */
    @FXML
    private void handleAbbrechen() {
    	webinarFilter = null;
        popupStage.close();
    }

    
    /**
     * Prueft die Eintraege in den Formularfeldern auf Gueltigkeit.
     * 
     * @return true falls gueltig, false sonst.
     */
    private boolean eingabenGueltig() {
        String fehlerMeldung = "";

        //Felder nacheinander pruefen und Auffaelligkeiten im String fehlermeldung sammeln

        //bei themaSubstringField sind beliebige Eintraege erlaubt, daher keine Pruefung

        if (festpreisVonField.getText() != null && festpreisVonField.getText().length() > 0) {
            try {
                Integer.parseInt(festpreisVonField.getText());
            } catch (NumberFormatException e) {
            	fehlerMeldung += "Kein gültiger Mindestpreis (muss ganzzahlig sein)!\n"; 
            }
        }
        
        if (festpreisBisField.getText() != null && festpreisBisField.getText().length() > 0) {
            try {
                Integer.parseInt(festpreisBisField.getText());
            } catch (NumberFormatException e) {
            	fehlerMeldung += "Kein gültiger Hoechstpreis (muss ganzzahlig sein)!\n"; 
            }
        }
        
        if (fehlerMeldung.length() == 0) {		//alles ok
            return true;
        } else {
            //Zusammengestellte Fehlermeldung in einem Hinweisfenster anzeigen
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(popupStage);
            alert.setTitle("Ungültige Einträge");
            alert.setHeaderText("Bitte korrigieren Sie die ungültigen Einträge!");
            alert.setContentText(fehlerMeldung);
            
            alert.showAndWait();
            
            return false;		//nicht ok
        }
    }

}
