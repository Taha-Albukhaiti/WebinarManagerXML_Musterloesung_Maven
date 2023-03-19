package de.thluebeck.webinar.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/** Abstrakte Model-Klasse Schulung.
 * @author Holger Hinrichs
 * @version 1.0
*/ 
public abstract class Schulung {

	protected IntegerProperty nummer;	//XxxProperty-Klassen sind abstrakt!
	protected StringProperty thema;
	protected IntegerProperty anzahlBuchungen;
	
	/** Einfacher Konstruktor ohne Parameter, Werte muessen per set...() geschrieben werden
	 */
	public Schulung() {
		this(0, "");		//mit 0 und "" initialisieren
	}
	
	/** Konstruktor zum Erzeugen eines Schulung-Objekts mit Nummer und Thema
	  * @param nummer Die Nummer der Schulung.
	  * @param thema Das Thema der Schulung.
	 */ 
	public Schulung(int nummer, String thema) {
		this.nummer = new SimpleIntegerProperty(nummer);		//SimpleXxxProperty ist volle Implementierung von XxxProperty
		this.thema = new SimpleStringProperty(thema);
		this.anzahlBuchungen = new SimpleIntegerProperty(0);	//zunaechst keine Buchungen
	}

	/** Setzt die Nummer der Schulung auf den uebergebenen Wert.
	  * @param nummer Die neue Nummer der Schulung.
	 */ 
	public void setNummer(int nummer) {
		this.nummer.set(nummer);
	}
	
	/** Gibt die Nummer der Schulung zurueck.
	  * @return Die Nummer der Schulung.
	 */ 
	public int getNummer() {
		return nummer.get();
	}

	/** Gibt die Nummer der Schulung als Property zurueck.
	  * @return Die Nummer der Schulung.
	 */ 
	public IntegerProperty nummerProperty() {
		return nummer;
	}

	/** Setzt das Thema der Schulung auf den uebergebenen Wert.
	  * @param thema Das neue Thema der Schulung.
	 */ 
	public void setThema(String thema) {
		this.thema.set(thema);
	}
	
	/** Gibt das Thema der Schulung zurueck.
	  * @return Das Thema der Schulung.
	 */ 
	public String getThema() {
		return thema.get();
	}
	
	/** Gibt das Thema der Schulung als Property zurueck.
	  * @return Das Thema der Schulung.
	 */ 
	public StringProperty themaProperty() {
		return thema;
	}

	/** Setzt die Anzahl der Buchungen der Schulung auf den uebergebenen Wert.
	  * @param anzahlBuchungen Die neue Anzahl der Buchungen der Schulung.
	 */ 
	public void setAnzahlBuchungen(int anzahlBuchungen) {
		this.anzahlBuchungen.set(anzahlBuchungen);
	}
	
	/** Gibt die Anzahl der Buchungen der Schulung zurueck.
	  * @return Die Anzahl der Buchungen der Schulung.
	 */ 
	public int getAnzahlBuchungen() {
		return anzahlBuchungen.get();
	}

	/** Gibt die Anzahl der Buchungen der Schulung als Property zurueck.
	  * @return Die Anzahl der Buchungen der Schulung.
	 */ 
	public IntegerProperty anzahlBuchungenProperty() {
		return anzahlBuchungen;
	}

	/** Abstrakte Methode zur Berechnung des Preises p. P. der Schulung. 
	  * Dieser berechnet sich je nach konkreter Auspraegung der Schulung unterschiedlich:
	  * @return Der Preis p. P. der Schulung.
	 */ 
	abstract public int getPreisProPerson();

}
