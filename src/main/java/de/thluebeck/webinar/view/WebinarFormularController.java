package de.thluebeck.webinar.view;

import de.thluebeck.webinar.model.Webinar;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

// ### nur fuer die ALTERNATIV zu fxml in Java programmierte Pane ###
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;


/**
 * Controller fuer das Formular zum Editieren von Webinardaten.
 *
 * @author holger
 */
public class WebinarFormularController {

    //die folgenden Annotationen dienen der Verknuepfung der Attribute mit den Controls in der fxml-Datei
    @FXML
    private TextField nummerField;
    @FXML
    private TextField themaField;
    @FXML
    private TextField urlField;
    @FXML
    private PasswordField kennwortField;
    @FXML
    private TextField festpreisField;
    @FXML
    private TextField anzahlBuchungenField;

    private Stage popupStage;
    private Webinar webinar;
    private boolean bestaetigt = false;


    /**
     * Initialisiert das Controller-Objekt. Wird automatisch aufgerufen nach dem Laden der fxml-Datei.
     */
    @FXML
    public void initialize() {
        //nichts zu tun
    }


    /**
     * ### ALTERNATIV zu fxml: in Java programmierte Pane ###
     *
     * @return die erzeugte Pane
     */
    public Pane getPane() {
        AnchorPane anchorPane = new AnchorPane();

        anchorPane.setPrefHeight(250.0);
        anchorPane.setPrefWidth(300.0);

        GridPane gridPane = new GridPane();
        gridPane.setLayoutX(38.0);
        gridPane.setLayoutY(45.0);
        AnchorPane.setLeftAnchor(gridPane, 5.0);
        AnchorPane.setRightAnchor(gridPane, 5.0);
        AnchorPane.setTopAnchor(gridPane, 20.0);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHgrow(Priority.SOMETIMES);
        col1.setMaxWidth(139.0);
        col1.setMinWidth(10.0);
        col1.setPrefWidth(109.0);

        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(Priority.SOMETIMES);
        col2.setMaxWidth(240.0);
        col2.setMinWidth(10.0);
        col2.setPrefWidth(181.0);

        gridPane.getColumnConstraints().addAll(col1, col2);

        RowConstraints row1 = new RowConstraints();
        row1.setMinHeight(10.0);
        row1.setPrefHeight(30.0);
        row1.setVgrow(Priority.SOMETIMES);

        RowConstraints row2 = new RowConstraints();
        row2.setMinHeight(10.0);
        row2.setPrefHeight(30.0);
        row2.setVgrow(Priority.SOMETIMES);

        RowConstraints row3 = new RowConstraints();
        row3.setMinHeight(10.0);
        row3.setPrefHeight(30.0);
        row3.setVgrow(Priority.SOMETIMES);

        RowConstraints row4 = new RowConstraints();
        row4.setMinHeight(10.0);
        row4.setPrefHeight(30.0);
        row4.setVgrow(Priority.SOMETIMES);

        RowConstraints row5 = new RowConstraints();
        row5.setMinHeight(10.0);
        row5.setPrefHeight(30.0);
        row5.setVgrow(Priority.SOMETIMES);

        RowConstraints row6 = new RowConstraints();
        row6.setMinHeight(10.0);
        row6.setPrefHeight(30.0);
        row6.setVgrow(Priority.SOMETIMES);

        gridPane.getRowConstraints().addAll(row1, row2, row3, row4, row5, row6);


        Label nummerLabel = new Label();
        nummerLabel.setText("Nummer");

        Label themaLabel = new Label();
        themaLabel.setText("Thema");
        GridPane.setRowIndex(themaLabel, 1);

        Label urlLabel = new Label();
        urlLabel.setText("URL");
        GridPane.setRowIndex(urlLabel, 2);

        Label kennwortLabel = new Label();
        kennwortLabel.setText("Kennwort");
        GridPane.setRowIndex(kennwortLabel, 3);

        Label festpreisLabel = new Label();
        festpreisLabel.setText("Festpreis in €");
        GridPane.setRowIndex(festpreisLabel, 4);

        Label anzahlBuchungenLabel = new Label();
        anzahlBuchungenLabel.setText("Anzahl Buchungen");
        GridPane.setRowIndex(anzahlBuchungenLabel, 5);

        gridPane.getChildren().addAll(nummerLabel, themaLabel, urlLabel, kennwortLabel,
                festpreisLabel, anzahlBuchungenLabel);

        nummerField = new TextField();
        GridPane.setColumnIndex(nummerField, 1);

        themaField = new TextField();
        GridPane.setColumnIndex(themaField, 1);
        GridPane.setRowIndex(themaField, 1);

        urlField = new TextField();
        GridPane.setColumnIndex(urlField, 1);
        GridPane.setRowIndex(urlField, 2);


        kennwortField = new PasswordField();
        GridPane.setColumnIndex(kennwortField, 1);
        GridPane.setRowIndex(kennwortField, 3);


        festpreisField = new TextField();
        GridPane.setColumnIndex(festpreisField, 1);
        GridPane.setRowIndex(festpreisField, 4);


        anzahlBuchungenField = new TextField();
        GridPane.setColumnIndex(anzahlBuchungenField, 1);
        GridPane.setRowIndex(anzahlBuchungenField, 5);

        gridPane.getChildren().addAll(nummerField, themaField, urlField, kennwortField,
                festpreisField, anzahlBuchungenField);

        ButtonBar buttonBar = new ButtonBar();
        buttonBar.setLayoutX(100.0);
        buttonBar.setLayoutY(146.0);
        buttonBar.setPrefHeight(40.0);
        buttonBar.setPrefWidth(200.0);
        AnchorPane.setBottomAnchor(buttonBar, 5.0);
        AnchorPane.setRightAnchor(buttonBar, 10.0);

        Button bestaetigenButton = new Button();
        bestaetigenButton.setText("Bestätigen");
        bestaetigenButton.setOnAction(e -> handleBestaetigen());

        Button abbrechenButton = new Button();
        abbrechenButton.setText("Abbrechen");
        abbrechenButton.setOnAction(e -> handleAbbrechen());

        buttonBar.getButtons().addAll(bestaetigenButton, abbrechenButton);

        anchorPane.getChildren().addAll(gridPane, buttonBar);

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
     * Setzt das Webinar-Objekt, das in diesem Formular bearbeitet werden soll.
     *
     * @param webinar
     */
    public void setWebinar(Webinar webinar) {
        this.webinar = webinar;

        //aktuelle Werte in Formularfelder uebernehmen (leere Felder bei neuem Webinar)
        nummerField.setText(webinar.getNummer() > 0 ? Integer.toString(webinar.getNummer()) : "");
        themaField.setText(webinar.getThema());
        urlField.setText(webinar.getUrl());
        kennwortField.setText(webinar.getKennwort());
        festpreisField.setText(webinar.getFestpreis() > 0 ? Integer.toString(webinar.getFestpreis()) : "");
        anzahlBuchungenField.setText(webinar.getAnzahlBuchungen() > 0 ? Integer.toString(webinar.getAnzahlBuchungen()) : "");
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
     * Handler fuer Clicks auf "Bestaetigen". Die Eingaben werden geprueft und - falls gueltig - in das Webinar-Objekt eingetragen.
     * Anschliessend wird das Popup geschlossen.
     */
    @FXML
    private void handleBestaetigen() {
        if (eingabenGueltig()) {
            webinar.setNummer(Integer.parseInt(nummerField.getText()));                        //String -> int
            webinar.setThema(themaField.getText());
            webinar.setUrl(urlField.getText());
            webinar.setKennwort(kennwortField.getText());
            webinar.setFestpreis(Integer.parseInt(festpreisField.getText()));
            webinar.setAnzahlBuchungen(Integer.parseInt(anzahlBuchungenField.getText()));    //String -> int

            bestaetigt = true;
            popupStage.close();        //Fenster schliessen
        }
    }

    /**
     * Handler fuer Clicks auf "Abbrechen". Popup wird ohne Datenuebernahme geschlossen.
     */
    @FXML
    private void handleAbbrechen() {
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
        if (nummerField.getText() == null || nummerField.getText().length() == 0) {
            fehlerMeldung += "Nummer fehlt!\n";
        } else {
            try {
                Integer.parseInt(nummerField.getText());
            } catch (NumberFormatException e) {
                fehlerMeldung += "Keine gültige Nummer (muss ganzzahlig sein)!\n";
            }
        }

        if (themaField.getText() == null || themaField.getText().length() == 0) {
            fehlerMeldung += "Thema fehlt!\n";
        }

        if (urlField.getText() == null || urlField.getText().length() == 0) {
            fehlerMeldung += "Ort fehlt!\n";
        }

        if (kennwortField.getText() == null || kennwortField.getText().length() == 0) {
            fehlerMeldung += "Kennwort fehlt!\n";
        }

        if (festpreisField.getText() == null || festpreisField.getText().length() == 0) {
            fehlerMeldung += "Festpreis fehlt!\n";
        } else {
            try {
                Integer.parseInt(festpreisField.getText());
            } catch (NumberFormatException e) {
                fehlerMeldung += "Kein gültiger Festpreis (muss ganzzahlig sein)!\n";
            }
        }

        if (anzahlBuchungenField.getText() == null || anzahlBuchungenField.getText().length() == 0) {
            fehlerMeldung += "Anzahl der Buchungen fehlt!\n";
        } else {
            try {
                Integer.parseInt(anzahlBuchungenField.getText());
            } catch (NumberFormatException e) {
                fehlerMeldung += "Keine gültige Anzahl der Buchungen (muss ganzzahlig sein)!\n";
            }
        }

        if (fehlerMeldung.length() == 0) {        //alles ok
            return true;
        } else {
            //Zusammengestellte Fehlermeldung in einem Hinweisfenster anzeigen
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(popupStage);
            alert.setTitle("Ungültige Einträge");
            alert.setHeaderText("Bitte korrigieren Sie die ungültigen Einträge!");
            alert.setContentText(fehlerMeldung);

            alert.showAndWait();

            return false;        //nicht ok
        }
    }

}
