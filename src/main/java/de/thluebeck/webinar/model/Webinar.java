package de.thluebeck.webinar.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/** Model-Klasse Webinar, jedes Objekt repraesentiert ein Webinar, eine spezielle Form der Schulung.
 * @author Holger Hinrichs
 * @version 1.0
*/ 
public class Webinar extends Schulung {

	protected StringProperty url;
	protected StringProperty kennwort;
	protected IntegerProperty festpreis;

	/** Einfacher Konstruktor ohne Parameter, Werte muessen per set...() geschrieben werden
	 */
	public Webinar() {
		this(0, "", "", "", 0);   //mit 0 und "" initialisieren
	}
	
	/** Konstruktor zum Erzeugen eines Webinar-Objekts mit seinen Eigenschaften
	  * @param nummer Die Nummer des Webinars.
	  * @param thema Das Thema des Webinars.
	  * @param url Die URL, wo das Webinar stattfindet
	  * @param kennwort Das Kennwort fuer den Zugang zum Webinar.
	  * @param festpreis Der Festpreis des Webinars
	 */ 
	public Webinar(int nummer, String thema, String url, String kennwort, int festpreis) {
		//Konstruktor der Oberklasse aufrufen
		super(nummer, thema);
		this.url = new SimpleStringProperty(url);
		this.kennwort = new SimpleStringProperty(kennwort);
		this.festpreis = new SimpleIntegerProperty(festpreis);
	}
	
	/** Setzt die URL des Webinars auf den uebergebenen Wert.
	  * @param url Die neue URL des Webinars.
	 */ 
	public void setUrl(String url) {
		this.url.set(url);
	}

	/** Gibt die URL des Webinars zurueck.
	  * @return die URL des Webinars.
	 */ 
	public String getUrl() {
		return url.get();
	}

	/** Gibt die URL des Webinars als Property zurueck.
	  * @return die URL des Webinars.
	 */ 
	public StringProperty urlProperty() {
		return url;
	}

	/** Setzt das Kennwort auf den uebergebenen Wert.
	  * @param kennwort Das neue Kennwort.
	 */ 
	public void setKennwort(String kennwort) {
		this.kennwort.set(kennwort);
	}
	
	/** Gibt das Kennwort zurueck.
	  * @return Das Kennwort.
	 */ 
	public String getKennwort() {
		return kennwort.get();
	}
	
	/** Gibt das Kennwort als Property zurueck.
	  * @return Das Kennwort.
	 */ 
	public StringProperty kennwortProperty() {
		return kennwort;
	}

	/** Setzt den Festpreis auf den uebergebenen Wert.
	  * @param festpreis Der Festpreis.
	 */ 
	public void setFestpreis(int festpreis) {
		this.festpreis.set(festpreis);
	}

	/** Gibt den Festpreis zurueck.
	  * @return Der Festpreis.
	 */ 
	public int getFestpreis() {
		return festpreis.get();
	}
	
	/** Gibt den Festpreis als Property zurueck.
	  * @return Der Festpreis.
	 */ 
	public IntegerProperty festpreisProperty() {
		return festpreis;
	}

	/** Gibt den Preis p. P. des Webinars zurueck, also den Festpreis.
	  * @return Der Preis p. P. des Webinars.
	 */ 
	@Override
	public int getPreisProPerson() {
		// Kosten = Festpreis
		return festpreis.get();
	}

	/** Gibt eine Textrepraesentation des Objekts zurueck.
	  * @return Die Textrepraesentation des Objekts.
	 */ 
	@Override
	public String toString() {
		return "Webinar: Nummer: " + nummer + ", Thema: " + thema
				+ ", URL: " + url + ", Kennwort: " + kennwort
				+ ", Festpreis: " + festpreis + ", Anzahl Buchungen: " + anzahlBuchungen;
	}
}
