package de.thluebeck.webinar.model;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import javafx.collections.ObservableList;

public class WebinarContentHandler extends DefaultHandler {

    private ObservableList<Webinar> webinare;
    private Webinar webinar;
    private String error;

    //Schalter fuer Kindelemente von webinar
    private boolean inThema = false;
    private boolean inAnzahlBuchungen = false;
    private boolean inUrl = false;
    private boolean inKennwort = false;
    private boolean inFestpreis = false;


    /**
     * Konstruktor, der eine Referenz auf eine (ggf. leere) Webinar-Liste entgegennimmt und speichert.
     *
     * @param webinare Die Webinar-Liste
     */
    public WebinarContentHandler(ObservableList<Webinar> webinare) {
        this.webinare = webinare;                //Referenz auf Liste aus WebinarManager
    }


    /**
     * Je nach gelesenem Start-Tag wird ein neues Webinar-Objekt angelegt
     * bzw. der Schalter einer bestimmten Eigenschaft auf true gesetzt.
     *
     * @param namespaceURI Siehe https://docs.oracle.com/javase/7/docs/api/org/xml/sax/helpers/DefaultHandler.html
     * @param localName    Siehe https://docs.oracle.com/javase/7/docs/api/org/xml/sax/helpers/DefaultHandler.html
     * @param qName        Siehe https://docs.oracle.com/javase/7/docs/api/org/xml/sax/helpers/DefaultHandler.html
     * @param attr         Siehe https://docs.oracle.com/javase/7/docs/api/org/xml/sax/helpers/DefaultHandler.html
     */
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attr) throws SAXException {
        if (localName.equals("webinar")) {
            webinar = new Webinar();                                    //neues Webinar-Objekt
            try {
                webinar.setNummer(Integer.parseInt(attr.getValue(0)));  //mit zugehoeriger Nummer
            } catch (NumberFormatException e) {
                //nichts tun
            }
        } else if (localName.equals("thema")) {
            inThema = true;
        } else if (localName.equals("anzahlBuchungen")) {
            inAnzahlBuchungen = true;
        } else if (localName.equals("url")) {
            inUrl = true;
        } else if (localName.equals("kennwort")) {
            inKennwort = true;
        } else if (localName.equals("festpreis")) {
            inFestpreis = true;
        }
    }


    /**
     * Je nach gelesenem End-Tag wird das fertige Webinar-Objekt in die Liste eingetragen
     * bzw. der Schalter einer bestimmten Eigenschaft auf false gesetzt.
     *
     * @param namespaceURI Siehe https://docs.oracle.com/javase/7/docs/api/org/xml/sax/helpers/DefaultHandler.html
     * @param localName    Siehe https://docs.oracle.com/javase/7/docs/api/org/xml/sax/helpers/DefaultHandler.html
     * @param qName        Siehe https://docs.oracle.com/javase/7/docs/api/org/xml/sax/helpers/DefaultHandler.html
     */
    @Override
    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
        if (localName.equals("webinar")) {
            webinare.add(webinar);                    //fertiges Webinar-Objekt in Liste eintragen
        } else if (localName.equals("thema")) {
            inThema = false;
        } else if (localName.equals("anzahlBuchungen")) {
            inAnzahlBuchungen = false;
        } else if (localName.equals("url")) {
            inUrl = false;
        } else if (localName.equals("kennwort")) {
            inKennwort = false;
        } else if (localName.equals("festpreis")) {
            inFestpreis = false;
        }
    }


    /**
     * Je nach umgebendem Element wird die gelesene Zeichenkette (Elementinhalt) einer Objekteigenschaft zugewiesen.
     *
     * @param ch     Character-Array, das die Zeichenkette enthaelt
     * @param start  Start-Index
     * @param length Laenge der zu lesenden Zeichenkette
     */
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String s = new String(ch, start, length);

        if (inThema) webinar.setThema(s);
        try {
            if (inAnzahlBuchungen) webinar.setAnzahlBuchungen(Integer.parseInt(s));
        } catch (NumberFormatException e) {
            //nichts tun
        }
        if (inUrl) webinar.setUrl(s);
        if (inKennwort) webinar.setKennwort(s);
        try {
            if (inFestpreis) webinar.setFestpreis(Integer.parseInt(s));
        } catch (NumberFormatException e) {
            //nichts tun
        }
    }


    /**
     * Behandelt fatale Fehler, die beim Parsen aufgetreten sind
     *
     * @param e Zu behandelnde Ausnahme
     */
    @Override
    public void fatalError(SAXParseException e) {
        error = "Fatal error: " + e.getMessage() + " at line : "
                + e.getLineNumber() + " column " + e.getColumnNumber();
    }


    /**
     * Behandelt "normale" Fehler, die beim Parsen aufgetreten sind
     *
     * @param e Zu behandelnde Ausnahme
     */
    @Override
    public void error(SAXParseException e) {
        error = "Error: " + e.getMessage() + " at line : "
                + e.getLineNumber() + " column " + e.getColumnNumber();
    }


    /**
     * Behandelt Warnungen, die beim Parsen aufgetreten sind
     *
     * @param e Zu behandelnde Ausnahme
     */
    @Override
    public void warning(SAXParseException e) {
        error = "Warning: " + e.getMessage() + " at line : "
                + e.getLineNumber() + " column " + e.getColumnNumber();
    }


    /**
     * Gibt aktuelle Fehlermeldung zurueck (sofern vorhanden), um sie in der Oberflaeche anzeigen zu koennen.
     *
     * @return Die Fehlermeldung, ggf. null
     */
    public String getError() {
        return error;
    }
}
