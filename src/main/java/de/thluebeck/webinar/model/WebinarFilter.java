package de.thluebeck.webinar.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/** Model-Klasse WebinarFilter.
 * @author Holger Hinrichs
 * @version 1.0
*/ 
public class WebinarFilter {

	protected StringProperty themaSubstring;		//XxxProperty-Klassen sind abstrakt!
	protected IntegerProperty festpreisVon;
	protected IntegerProperty festpreisBis;
	
	/** Konstruktor zum Erzeugen eines WebinarFilter-Objekts mit Thema-Substring und Preisbereich
	  * @param themaSubstring Der Substring, den die gefilterten Webinare im Thema enthalten muessen.
	  * @param festpreisVon Der Mindestpreis, den die gefilterten Webinare aufweisen muessen.
	  * @param festpreisBis Der Hoechstpreis, den die gefilterten Webinare aufweisen muessen.
	 */ 
	public WebinarFilter(String themaSubstring, int festpreisVon, int festpreisBis) {
		this.themaSubstring = new SimpleStringProperty(themaSubstring);		//SimpleXxxProperty ist volle Implementierung von XxxProperty
		this.festpreisVon = new SimpleIntegerProperty(festpreisVon);
		this.festpreisBis = new SimpleIntegerProperty(festpreisBis);
	}

	/** Gibt den Thema-Substring der Filterung zurueck.
	  * @return Der Thema-Substring.
	 */ 
	public String getThemaSubstring() {
		return themaSubstring.get();
	}
	
	/** Gibt den Thema-Substring der Filterung als Property zurueck.
	  * @return Der Thema-Substring.
	 */ 
	public StringProperty themaSubstringProperty() {
		return themaSubstring;
	}

	/** Gibt den Mindestpreis der Filterung zurueck.
	  * @return Der Mindestpreis.
	 */ 
	public int getFestpreisVon() {
		return festpreisVon.get();
	}

	/** Gibt den Mindestpreis der Filterung als Property zurueck.
	  * @return Der Mindestpreis.
	 */ 
	public IntegerProperty festpreisVonProperty() {
		return festpreisVon;
	}

	/** Gibt den Hoechstpreis der Filterung zurueck.
	  * @return Der Hoechstpreis.
	 */ 
	public int getFestpreisBis() {
		return festpreisBis.get();
	}

	/** Gibt den Hoechstpreis der Filterung als Property zurueck.
	  * @return Der Hoechstpreis.
	 */ 
	public IntegerProperty festpreisBisProperty() {
		return festpreisBis;
	}

}
