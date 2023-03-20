package de.thluebeck.webinar.view;

import java.io.File;

import de.thluebeck.webinar.WebinarManagerFX;
import de.thluebeck.webinar.model.Webinar;
import de.thluebeck.webinar.model.WebinarFilter;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

// ### nur fuer die ALTERNATIV zu fxml in Java programmierte Pane ###
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.geometry.Pos;


/**
 * Controller fuer das Hauptfenster der Anwendung, also die Webinar-Tabelle plus Menue.
 * @author holger
 *
 */
public class WebinarTabelleController {

	//die folgenden Annotationen dienen der Verknuepfung der Attribute mit den Controls in der fxml-Datei
    @FXML
    private TableView<Webinar> webinarTable;
    @FXML
    private TableColumn<Webinar, Integer> nummerColumn;
    @FXML
    private TableColumn<Webinar, String> themaColumn;
    @FXML
    private TableColumn<Webinar, String> urlColumn;
    @FXML
    private TableColumn<Webinar, Integer> festpreisColumn;
    @FXML
    private TableColumn<Webinar, Integer> anzahlBuchungenColumn;


    //Referenz zur Applikation
    private WebinarManagerFX mainApp;

    
    /**
     * Initialisiert das Controller-Objekt. Wird automatisch aufgerufen nach dem Laden der fxml-Datei.
     */
    @FXML
    public void initialize() {
    	//Die setCellValueFactories, die auf die Tabellenspalten gesetzt werden, werden verwendet,
    	//um festzulegen, welches Attribut eines Webinar-Objekts in der jeweiligen Spalte angezeigt werden soll.
    	//cellData->getValue() liefert das Webinar-Objekt fuer eine bestimmte Zeile der TableView.
    	//Ueber dieses Objekt koennen dann die einzelnen Attribut-Properties abgefragt werden.
    	//IntegerProperties erfordern (leider) .asObject(), vgl. https://community.oracle.com/thread/2575601
    	nummerColumn.setCellValueFactory(cellData -> cellData.getValue().nummerProperty().asObject());
        themaColumn.setCellValueFactory(cellData -> cellData.getValue().themaProperty());
        urlColumn.setCellValueFactory(cellData -> cellData.getValue().urlProperty());
        festpreisColumn.setCellValueFactory(cellData -> cellData.getValue().festpreisProperty().asObject());
        anzahlBuchungenColumn.setCellValueFactory(cellData -> cellData.getValue().anzahlBuchungenProperty().asObject());

/* ## Optionaler KeyPressed-Handler zum Editieren und Loeschen von Webinaren. Wird aktuell nicht benoetigt, da stattdessen
 * ## Accelerator Keys zu den Menueeintraegen definiert wurden. ##
        webinarTable.setOnKeyPressed(e -> {
        	//STRG-E soll ausgewaehltes Webinar loeschen
        	if (new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_ANY).match(e)) {
        		e.consume();	//Event konsumieren, da behandelt
        		handleEditieren();
        	}
        	//STRG-D soll ausgewaehltes Webinar loeschen
        	else if (new KeyCodeCombination(KeyCode.D, KeyCombination.CONTROL_ANY).match(e)) {
        		e.consume();	//Event konsumieren, da behandelt
        		handleLoeschen();
        	}
        });
*/
    }

    
    /** ### ALTERNATIV zu fxml: in Java programmierte Pane ###
     * 
     * @return die erzeugte Pane
     */   
    public Pane getPane() {
    	BorderPane borderPane = new BorderPane();

    	AnchorPane anchorPane = new AnchorPane();
    	anchorPane.setPrefHeight(300.0);
    	anchorPane.setPrefWidth(600.0);
    	
    	webinarTable = new TableView<>();
    	webinarTable.setLayoutX(141.0);
    	webinarTable.setPrefHeight(200.0);
    	webinarTable.setPrefWidth(200.0);
    	webinarTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    	AnchorPane.setBottomAnchor(webinarTable, 0.0);
    	AnchorPane.setLeftAnchor(webinarTable, 0.0);
    	AnchorPane.setRightAnchor(webinarTable, 0.0);
    	AnchorPane.setTopAnchor(webinarTable, 0.0);
    	
    	nummerColumn = new TableColumn<>();
    	nummerColumn.setMaxWidth(62.0);
    	nummerColumn.setMinWidth(62.0);
    	nummerColumn.setText("Nummer");
    	webinarTable.getColumns().add(nummerColumn);

    	themaColumn = new TableColumn<>();
    	themaColumn.setMinWidth(100.0);
    	themaColumn.setPrefWidth(200.0);
    	themaColumn.setText("Thema");
    	webinarTable.getColumns().add(themaColumn);

    	urlColumn = new TableColumn<>();
    	urlColumn.setMinWidth(100.0);
    	urlColumn.setPrefWidth(200.0);
    	urlColumn.setText("URL");
    	webinarTable.getColumns().add(urlColumn);

    	festpreisColumn = new TableColumn<>();
    	festpreisColumn.setMaxWidth(80.0);
    	festpreisColumn.setMinWidth(80.0);
    	festpreisColumn.setStyle("-fx-alignment: CENTER-RIGHT;");
    	festpreisColumn.setText("Festpreis in €");
    	webinarTable.getColumns().add(festpreisColumn);

    	anzahlBuchungenColumn = new TableColumn<>();
    	anzahlBuchungenColumn.setMaxWidth(80.0);
    	anzahlBuchungenColumn.setMinWidth(80.0);
    	anzahlBuchungenColumn.setStyle("-fx-alignment: CENTER-RIGHT;");
    	anzahlBuchungenColumn.setText("Buchungen");
    	webinarTable.getColumns().add(anzahlBuchungenColumn);

    	anchorPane.getChildren().add(webinarTable);
    	
    	borderPane.setCenter(anchorPane);
    	
    	MenuBar menuBar = new MenuBar();

        Menu dateiMenu = new Menu();
        dateiMenu.setText("Datei");
        
        MenuItem ladenMenuItem = new MenuItem();
        ladenMenuItem.setText("Webinare laden...");
        ladenMenuItem.setOnAction(e -> handleLaden());
        ladenMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.L, KeyCombination.CONTROL_DOWN));
        dateiMenu.getItems().add(ladenMenuItem);
        
        MenuItem speichernMenuItem = new MenuItem();
        speichernMenuItem.setText("Webinare speichern...");
        speichernMenuItem.setOnAction(e -> handleSpeichern());
        speichernMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
        dateiMenu.getItems().add(speichernMenuItem);
        
        MenuItem beendenMenuItem = new MenuItem();
        beendenMenuItem.setText("Beenden");
        beendenMenuItem.setOnAction(e -> handleBeenden());
        beendenMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN));
        dateiMenu.getItems().add(beendenMenuItem);
        
        menuBar.getMenus().add(dateiMenu);

        Menu bearbeitenMenu = new Menu();
        bearbeitenMenu.setText("Bearbeiten");
        
        MenuItem neuesWebinarMenuItem = new MenuItem();
        neuesWebinarMenuItem.setText("Neues Webinar...");
        neuesWebinarMenuItem.setOnAction(e -> handleNeuesWebinar());
        neuesWebinarMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));
        bearbeitenMenu.getItems().add(neuesWebinarMenuItem);
        
        MenuItem editierenMenuItem = new MenuItem();
        editierenMenuItem.setText("Selektiertes Webinar editieren...");
        editierenMenuItem.setOnAction(e -> handleEditieren());
        editierenMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN));
        bearbeitenMenu.getItems().add(editierenMenuItem);
        
        MenuItem loeschenMenuItem = new MenuItem();
        loeschenMenuItem.setText("Selektiertes Webinar löschen...");
        loeschenMenuItem.setOnAction(e -> handleLoeschen());
        loeschenMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.D, KeyCombination.CONTROL_DOWN));
        bearbeitenMenu.getItems().add(loeschenMenuItem);

        menuBar.getMenus().add(bearbeitenMenu);

        Menu extrasMenu = new Menu();
        extrasMenu.setText("Extras");

        MenuItem buchungsuebersichtMenuItem = new MenuItem();
        buchungsuebersichtMenuItem.setText("Buchungsübersicht...");
        buchungsuebersichtMenuItem.setOnAction(e -> handleBuchungsuebersicht());
        buchungsuebersichtMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.B, KeyCombination.CONTROL_DOWN));
        extrasMenu.getItems().add(buchungsuebersichtMenuItem);
        
        MenuItem ueberMenuItem = new MenuItem();
        ueberMenuItem.setText("Über...");
        ueberMenuItem.setOnAction(e -> handleUeber());
        ueberMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.I, KeyCombination.CONTROL_DOWN));
        extrasMenu.getItems().add(ueberMenuItem);
        
        menuBar.getMenus().add(extrasMenu);
        
    	borderPane.setTop(menuBar);
        BorderPane.setAlignment(menuBar, Pos.TOP_LEFT);

    	return borderPane;
    }
    
    
    /**
     * Wird vom Hauptprogramm aufgerufen, um eine Referenz an sich selbst an den Controller zu uebergeben.
     * Setzt ausserdem die im Backend hinterlegten Webinare in die Tabelle.
     * @param mainApp
     */
    public void setMainApp(WebinarManagerFX mainApp) {
        this.mainApp = mainApp;

        //ObservableList mit Webinar-Objekten in Tabelle eintragen
        webinarTable.setItems(mainApp.getWebinarManager().getWebinare());
    }
    
    
    /**
     * Handler fuer Clicks auf Laden bzw. Strg-L. Oeffnet einen FileChooser für Textdateien, laedt (falls Datei ausgewaehlt)
     * die Objekte und setzt sie in die Tabelle.
     */
    @FXML
    private void handleLaden() {
    	File gewaehlteDatei = getTextFileChooser().showOpenDialog(mainApp.getPrimaryStage());

    	if (gewaehlteDatei != null) {			//falls Datei gewaehlt
	    	webinarTable.getItems().clear();	//bisherige Daten aus Tabelle entfernen

	    	//Webinare laden und in Tabelle eintragen

	    	//Praesenz 2: Laden aus XML-Datei
	    	//boolean ok = mainApp.getWebinarManager().webinareLaden(gewaehlteDatei.getAbsolutePath());
	    	String fehlermeldung = mainApp.getWebinarManager().webinareLadenXML(gewaehlteDatei.getAbsolutePath());

	    	if (fehlermeldung == null) {		//kein Fehler
	    		webinarTable.setItems(mainApp.getWebinarManager().getWebinare());
	    	} else {
	        	Alert alert = new Alert(AlertType.ERROR);
	            alert.initOwner(mainApp.getPrimaryStage());
	            alert.setTitle("Fehler");
	            alert.setHeaderText("Fehler beim Laden der ausgewählten Datei");
	            alert.setContentText(fehlermeldung);
	            alert.showAndWait();	    		
    		}
    	}
    }

    /**
     * Handler fuer Clicks auf gefiltertes Laden. Spezifiziert die Filterkriterien, oeffnet dann einen FileChooser für Textdateien, laedt (falls Datei ausgewaehlt)
     * die Objekte, filtert diese nach den gegebenen Kriterien und setzt sie in die Tabelle.
     */
    @FXML
    private void handleGefiltertLaden() {
    	String error;
        WebinarFilter webinarFilter = mainApp.webinarFilterungZeigen();
        if (webinarFilter != null) {									//es wurde ein Filter spezifiziert
        	handleLaden();												//Webinare "normal" laden
        	error = mainApp.getWebinarManager().webinareDOMErzeugen();	//DOM-Baum erzeugen
        	if (error == null) {										//Filter auf den DOM-Baum anwenden
        		mainApp.getWebinarManager().webinareDOMFiltern(webinarFilter);
        	} else {
        		System.err.println(error);
        	}
        }
    }

    /**
     * Handler fuer Clicks auf Speichern bzw. Strg-S. Oeffnet einen FileChooser für Textdateien und speichert (falls Datei ausgewaehlt)
     * die Objekte in die Datei.
     */
    @FXML
    private void handleSpeichern() {
    	File gewaehlteDatei = getTextFileChooser().showSaveDialog(mainApp.getPrimaryStage());

    	if (gewaehlteDatei != null) { 			//falls Datei gewaehlt
	    	//Praesenz 2: Speichern in XML-Datei
	    	//boolean ok = mainApp.getWebinarManager().webinareSpeichern(gewaehlteDatei.getAbsolutePath());
    		String fehlermeldung = mainApp.getWebinarManager().webinareSpeichernXML(gewaehlteDatei.getAbsolutePath());

    		if (fehlermeldung != null) {		//Fehler
	        	Alert alert = new Alert(AlertType.ERROR);
	            alert.initOwner(mainApp.getPrimaryStage());
	            alert.setTitle("Fehler");
	            alert.setHeaderText("Fehler beim Speichern der Webinare");
	            alert.setContentText(fehlermeldung);
	            alert.showAndWait();	    		
    		}
    	}
    }
    
    
    /**
     * Hilfsmethode, die einen FileChooser für Textdateien bereitstellt. Initial ist das Arbeitsverzeichnis ausgewaehlt.
     * @return FileChooser
     */
    private FileChooser getTextFileChooser() {
    	FileChooser fileChooser = new FileChooser();

    	//Arbeitsverzeichnis als initiales Verzeichnis setzen
    	File workingDirectory = new File(System.getProperty("user.dir"));
    	fileChooser.setInitialDirectory(workingDirectory);

    	//Dateiextensionen auf .txt beschraenken
    	fileChooser.getExtensionFilters().add(
    	     //Praesenz 2: nur .xml zulaessig
    		 //new FileChooser.ExtensionFilter("Textdateien", "*.txt")
   		     new FileChooser.ExtensionFilter("XML-Dateien", "*.xml")
    	);
    	return fileChooser;
	}

    
    /**
     * Handler fuer Clicks auf Beenden bzw. Strg-Q. Beendet das Programm.
     */    
    @FXML
    private void handleBeenden() {
        Platform.exit();
        System.exit(0);
    }
    

    /**
     * Handler fuer Clicks auf "Neues Webinar..." bzw. Strg-N. Erzeugt ein neues Webinar-Objekt und zeigt es im Formular an.
     * Falls dieses mit "Bestaetigen" verlassen wird, wird das Webinar der Liste hinzugefuegt.
     */
    @FXML
    private void handleNeuesWebinar() {
    	Webinar tempWebinar = new Webinar();
        boolean bestaetigt = mainApp.webinarFormularZeigen(tempWebinar);
        if (bestaetigt) {
            mainApp.getWebinarManager().webinarEintragen(tempWebinar);
        }
    }

    /**
     * Handler fuer Clicks auf "Selektiertes Webinar editieren..." bzw. Strg-E. Zeigt das selektierte Webinar im Formular an.
     * Falls kein Webinar selektiert war, wird eine Warnung in einem Popup ausgegeben.
     */
    @FXML
    private void handleEditieren() {
    	Webinar selektiertesWebinar = webinarTable.getSelectionModel().getSelectedItem();
        if (selektiertesWebinar != null) {
            mainApp.webinarFormularZeigen(selektiertesWebinar);
            //bestaetigte Aenderungen werden ueber WebinarFormularController.handleBestaetigen() in das Objekt uebernommen
        } else {
        	//falls nichts selektiert
        	Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Keine Auswahl");
            alert.setHeaderText("Kein Webinar selektiert");
            alert.setContentText("Bitte selektieren Sie ein Webinar aus der Tabelle.");
            alert.showAndWait();
        }
    }
    
    
    /**
     * Handler fuer Clicks auf "Selektiertes Webinar loeschen..." bzw. Strg-D. Loescht das selektierte Webinar.
     * Falls kein Webinar selektiert war, wird eine Warnung in einem Popup ausgegeben.
     */
    @FXML
    private void handleLoeschen() {
    	Webinar selektiertesWebinar = webinarTable.getSelectionModel().getSelectedItem();
        if (selektiertesWebinar != null) {
        	boolean bestaetigt = mainApp.webinarLoeschenAbfrageZeigen();	//Sicherheitsabfrage
        	if (bestaetigt) {
                mainApp.getWebinarManager().webinarLoeschen(selektiertesWebinar);
        	}
    	} else {
        	//falls nichts selektiert
    		Alert alert = new Alert(AlertType.WARNING);
    		alert.initOwner(mainApp.getPrimaryStage());
    		alert.setTitle("Keine Auswahl");
    		alert.setHeaderText("Kein Webinar selektiert");
            alert.setContentText("Bitte selektieren Sie ein Webinar aus der Tabelle.");
    		alert.showAndWait();
    	}
    }

    
    /**
     * Handler fuer Clicks auf "Buchungsuebersicht..." bzw. Strg-B. Zeigt das zugehoerige Balkendiagramm an.
     */
    @FXML
    private void handleBuchungsuebersicht() {
      mainApp.webinarDiagrammZeigen();
    }
    
    /**
     * Handler fuer Clicks auf "Ueber..." bzw. Strg-I. Zeigt ein Info-Fenster zur Applikation an.
     */
    @FXML
    private void handleUeber() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.initOwner(mainApp.getPrimaryStage());
		alert.setTitle("Über WebinarManager");
		alert.setHeaderText("WebinarManager 1.0");
		alert.setContentText("Autor: Holger\nDatum: 29.04.2020");
		alert.showAndWait();
    }
    
}
