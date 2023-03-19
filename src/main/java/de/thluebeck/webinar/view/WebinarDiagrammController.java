package de.thluebeck.webinar.view;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import de.thluebeck.webinar.model.Webinar;

// ### nur fuer die ALTERNATIV zu fxml in Java programmierte Pane ###
import javafx.geometry.Side;
import javafx.scene.chart.NumberAxis;


/**
 * Controller fuer die Buchungsuebersicht.
 * @author holger
 */
public class WebinarDiagrammController {

    @FXML
    private BarChart<String, Integer> barChart;

    @FXML
    private CategoryAxis xAxis;

    private ObservableList<String> webinarNummern = FXCollections.observableArrayList();

    
    /**
     * Initialisiert das Controller-Objekt. Wird automatisch aufgerufen nach dem Laden der fxml-Datei.
     */
    @FXML
    public void initialize() {
    	barChart.setLegendVisible(false);	//Legende ausblenden
    }


    /** ### ALTERNATIV zu fxml: in Java programmierte Pane ###
     * 
     * @return die erzeugte Pane
     */
    public Pane getPane() {
    	AnchorPane anchorPane = new AnchorPane();

    	anchorPane.setPrefHeight(450.0);
    	anchorPane.setPrefWidth(620.0);
    	
    	xAxis = new CategoryAxis();
    	xAxis.setSide(Side.BOTTOM);
    	xAxis.setLabel("Webinar-Nummer");

    	NumberAxis yAxis = new NumberAxis();
    	yAxis.setSide(Side.LEFT);
    	yAxis.setUpperBound(50.0);
    	yAxis.setLabel("Anzahl Buchungen");

    	barChart = new BarChart(xAxis, yAxis);
    	barChart.setLayoutX(106.0);
    	barChart.setLayoutY(25.0);
    	barChart.setPrefHeight(450.0);
    	barChart.setPrefWidth(620.0);
    	barChart.setTitle("Anzahl Buchungen pro Webinar");
    	AnchorPane.setBottomAnchor(barChart, 0.0);
    	AnchorPane.setLeftAnchor(barChart, 0.0);
    	AnchorPane.setRightAnchor(barChart, 0.0);
    	AnchorPane.setTopAnchor(barChart, 0.0);

    	
    	anchorPane.getChildren().add(barChart);

    	return anchorPane;
    }

    
    /**
     * Traegt die Webinarnummern und die Buchungsdaten in das Diagramm ein.
     * 
     * @param webinare
     */
    public void buchungsdatenEintragen(List<Webinar> webinare) {
    	
    	//neue Reihe von Wertepaaren (jeweils Webinarnummer als String und zugehoerige Buchungsanzahl)
        XYChart.Series<String, Integer> series = new XYChart.Series<>();

        //Webinar-Nummern in Liste eintragen (Liste wird unten als Kategorien auf die x-Achse gesetzt.
        //Ausserdem Wertepaar (Webinarnummer als String und zugehoerige Buchungsanzahl) der Datenreihe hinzufuegen
    	for (Webinar webinar : webinare) {
    		webinarNummern.add(webinar.getNummer() + "");	//+ "" zwecks Konvertierung zu String
            series.getData().add(new XYChart.Data<>(webinar.getNummer() + "", webinar.getAnzahlBuchungen()));
    	}
        
        //Gesammelte Webinarnummern als Kategorien auf die x-Achse setzen
        xAxis.setCategories(webinarNummern);
        
        //Datenreihe dem Balkendiagramm hinzufuegen
        barChart.getData().add(series);
    }
    
}