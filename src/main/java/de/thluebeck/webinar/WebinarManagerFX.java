package de.thluebeck.webinar;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import de.thluebeck.webinar.model.Webinar;
import de.thluebeck.webinar.model.WebinarFilter;
import de.thluebeck.webinar.model.WebinarManager;
import de.thluebeck.webinar.view.WebinarDiagrammController;
import de.thluebeck.webinar.view.WebinarFormularController;
import de.thluebeck.webinar.view.WebinarLoeschenAbfrageController;
import de.thluebeck.webinar.view.WebinarTabelleController;
import de.thluebeck.webinar.view.WebinarFilterungController;

/**
 * Grafische Oberflaeche für WebinarManager, mit tabellarischer Ansicht, Bearbeitungsformular und Buchungsstatistik.
 * @author holger
 *
 */
public class WebinarManagerFX extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;
	
	//Verknuepfung zum Backend (Model)
	private WebinarManager wm = new WebinarManager();
	
	
	/**
	 * Parameterloser Konstruktor
	 */
	public WebinarManagerFX() {
	}
	
	
	/**
	 * Laedt die View WebinarTabelle (inkl. Menue) und zeigt sie im Hauptfenster an.
	 * @param primaryStage Wird vom Betriebssystem bereitgestellt
	 */
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("WebinarManager");
		
		//View laden
		// ### Beginn VARIANTE 1: View aus fxml ###
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(WebinarManagerFX.class.getResource("WebinarTabelle.fxml"));
			rootLayout = (BorderPane) loader.load();
			
			//Controller (steht in fxml) holen und mit der MainApp (this) verknuepfen
			WebinarTabelleController controller = loader.getController();
		// ### Ende VARIANTE 1: View aus fxml ###

			// ### Beginn VARIANTE 2: View aus Java ###
			/*
			WebinarTabelleController controller = new WebinarTabelleController();
			rootLayout = (BorderPane) controller.getPane();
			controller.initialize();
			*/
			// ### Ende VARIANTE 2: View aus Java ###
			
			controller.setMainApp(this);

			//Szene mit Root Layout erzeugen, auf die Stage setzen und anzeigen
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.setTitle("WebinarManager");
			primaryStage.show();

		// ### Beginn catch-Block VARIANTE 1: View aus fxml ###
		} catch (IOException e) {
			e.printStackTrace();
		}
		// ### Ende catch-Block VARIANTE 1: View aus fxml ###
	}
	
	
	/**
	 * Oeffnet das Formular zum Editieren eines (ggf. neuen, d. h. leeren) Webinar-Objekts. 
	 * Gibt zurueck, ob die Bearbeitung bestaetigt oder abgebrochen wurde.
	 * 
	 * @param webinar Das zu editierende Webinar-Objekt
	 * @return true falls "Bestaetigen" geklickt wurde, sonst false.
	 */
	public boolean webinarFormularZeigen(Webinar webinar) {
        //View laden
		// ### Beginn VARIANTE 1: View aus fxml ###
	    try {
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(WebinarManagerFX.class.getResource("WebinarFormular.fxml"));
	        AnchorPane pane = (AnchorPane) loader.load();

	        //Controller (steht in fxml) holen und Webinar-Objekt uebergeben
	        WebinarFormularController controller = loader.getController();
		// ### Ende VARIANTE 1: View aus fxml ###

			// ### Beginn VARIANTE 2: View aus Java ###
	        /*
			WebinarFormularController controller = new WebinarFormularController();
			AnchorPane pane = (AnchorPane) controller.getPane();
			controller.initialize();
			*/
			// ### Ende VARIANTE 2: View aus Java ###

			//(Secondary) Stage fuer das Popup-Formular erzeugen und mit der Primary Stage verknuepfen
	        Stage popupStage = new Stage();
	        popupStage.setTitle("Bearbeite Webinardaten");
	        popupStage.initModality(Modality.WINDOW_MODAL);	//Popup muss geschlossen werden, bevor es im Hauptfenster weitergehen kann
	        popupStage.initOwner(primaryStage);
	        popupStage.setResizable(false);					//fixe Fenstergroesse
	        Scene scene = new Scene(pane);
	        popupStage.setScene(scene);

	        controller.setWebinar(webinar);
	        //ausserdem Popup Stage uebergeben, damit Controller diese z. B. schliessen oder weitere Fenster oeffnen kann
	        controller.setPopupStage(popupStage);

	        //Popup anzeigen und warten, bis der User es schliesst 
	        popupStage.showAndWait();

	        //zurueckgeben, ob bestaetigt oder abgebrochen wurde
	        return controller.istBestaetigt();
		// ### Beginn catch-Block VARIANTE 1: View aus fxml ###
	    } catch (IOException e) {
	        e.printStackTrace();
	        return false;
	    }
	    // ### Ende catch-Block VARIANTE 1: View aus fxml ###

	}
	
	
	/**
	 * Oeffnet ein Fenster mit der Buchungsuebersicht als Balkendiagramm
	 */
	public void webinarDiagrammZeigen() {
        //View laden
		// ### Beginn VARIANTE 1: View aus fxml ###
	    try {
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(WebinarManagerFX.class.getResource("WebinarDiagramm.fxml"));
	        AnchorPane pane = (AnchorPane) loader.load();
	        
			//Controller (steht in fxml) holen und Buchungsdaten (fuer Diagramm) eintragen
	        WebinarDiagrammController controller = loader.getController();
		// ### Ende VARIANTE 1: View aus fxml ###

			// ### Beginn VARIANTE 2: View aus Java ###
	        /*
			WebinarDiagrammController controller = new WebinarDiagrammController();
			AnchorPane pane = (AnchorPane) controller.getPane();
			controller.initialize();
			*/
			// ### Ende VARIANTE 2: View aus Java ###

	        //(Secondary) Stage fuer das Popup-Formular erzeugen und mit der Primary Stage verknuepfen
	        Stage popupStage = new Stage();
	        popupStage.setTitle("Buchungsübersicht");
	        popupStage.initModality(Modality.WINDOW_MODAL);	//Popup muss geschlossen werden, bevor es im Hauptfenster weitergehen kann
	        popupStage.initOwner(primaryStage);
	        Scene scene = new Scene(pane);
	        popupStage.setScene(scene);

			controller.buchungsdatenEintragen(wm.getWebinare());

	        //Popup anzeigen
	        popupStage.show();

		// ### Beginn catch-Block VARIANTE 1: View aus fxml ###
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		// ### Ende catch-Block VARIANTE 1: View aus fxml ###
	}
	
	
	/**
	 * Oeffnet die Sicherheitsabfrage zum Loeschen eines Webinars. 
	 * Gibt zurueck, ob geloescht werden soll (true) oder nicht (false).
	 * 
	 * @return true falls "Ja" geklickt wurde, sonst false.
	 */
	public boolean webinarLoeschenAbfrageZeigen() {
        //View laden
		// ### Beginn VARIANTE 1: View aus fxml ###
	    try {
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(WebinarManagerFX.class.getResource("WebinarLoeschenAbfrage.fxml"));
	        AnchorPane pane = (AnchorPane) loader.load();

	        //Controller (steht in fxml) holen und Webinar-Objekt uebergeben
	        WebinarLoeschenAbfrageController controller = loader.getController();
		// ### Ende VARIANTE 1: View aus fxml ###

			// ### Beginn VARIANTE 2: View aus Java ###
	        /*
			WebinarLoeschenAbfrageController controller = new WebinarLoeschenAbfrageController();
			AnchorPane pane = (AnchorPane) controller.getPane();
			controller.initialize();
			*/
			// ### Ende VARIANTE 2: View aus Java ###

			//(Secondary) Stage fuer das Popup-Formular erzeugen und mit der Primary Stage verknuepfen
	        Stage popupStage = new Stage();
	        popupStage.setTitle("Sicherheitsabfrage");
	        popupStage.initModality(Modality.WINDOW_MODAL);	//Popup muss geschlossen werden, bevor es im Hauptfenster weitergehen kann
	        popupStage.initOwner(primaryStage);
	        popupStage.setResizable(false);					//fixe Fenstergroesse
	        Scene scene = new Scene(pane);
	        popupStage.setScene(scene);

	        //ausserdem Popup Stage uebergeben, damit Controller diese z. B. schliessen oder weitere Fenster oeffnen kann
	        controller.setPopupStage(popupStage);

	        //Popup anzeigen und warten, bis der User es schliesst 
	        popupStage.showAndWait();

	        //zurueckgeben, ob bestaetigt oder abgebrochen wurde
	        return controller.istBestaetigt();
		// ### Beginn catch-Block VARIANTE 1: View aus fxml ###
	    } catch (IOException e) {
	        e.printStackTrace();
	        return false;
	    }
	    // ### Ende catch-Block VARIANTE 1: View aus fxml ###

	}
	
	
	/**
	 * Oeffnet das Formular zum Filtern von Webinar-Objekten. 
	 * Gibt den Filter zurueck, sofern bestaetigt.
	 * 
	 * @return der Filter, falls "Filtern" geklickt wurde, sonst null.
	 */
	public WebinarFilter webinarFilterungZeigen() {
        //View laden
	    try {
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(WebinarManagerFX.class.getResource("WebinarFilterung.fxml"));
	        AnchorPane pane = (AnchorPane) loader.load();

	        //Controller (steht in fxml) holen und Webinar-Objekt uebergeben
	        WebinarFilterungController controller = loader.getController();

			//(Secondary) Stage fuer das Popup-Formular erzeugen und mit der Primary Stage verknuepfen
	        Stage popupStage = new Stage();
	        popupStage.setTitle("Filtere Webinare");
	        popupStage.initModality(Modality.WINDOW_MODAL);	//Popup muss geschlossen werden, bevor es im Hauptfenster weitergehen kann
	        popupStage.initOwner(primaryStage);
	        popupStage.setResizable(false);					//fixe Fenstergroesse
	        Scene scene = new Scene(pane);
	        popupStage.setScene(scene);

	        //ausserdem Popup Stage uebergeben, damit Controller diese z. B. schliessen oder weitere Fenster oeffnen kann
	        controller.setPopupStage(popupStage);

	        //Popup anzeigen und warten, bis der User es schliesst 
	        popupStage.showAndWait();

	        //den spezifizierten Filter holen und zurueckgeben (null, falls abgebrochen)
	        return controller.getWebinarFilter();
	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	
	
	/**
	 * Liefert das Backend-Objekt zur Webinar-Verwaltung
	 * @return das Backend-Objekt
	 */
	public WebinarManager getWebinarManager() {
		return wm;
	}
	
	
	/**
	 * Liefert die Primary Stage (z. B. an einen Controller, damit dieser neue Fenster oeffnen kann etc.)
	 * @return die Primary Stage
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	
	/**
	 * Startet die Applikation, ruft indirekt start() auf (siehe oben)
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
}
