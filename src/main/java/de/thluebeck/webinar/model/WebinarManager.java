package de.thluebeck.webinar.model;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/** Controller-Klasse WebinarManager zum Anlegen, Suchen und Ausgeben von Webinaren
  * @author Holger Hinrichs
  * @version 0.1
 */ 
public class WebinarManager {
	
	//ArrayList zum Verwalten der Webinare, zur Verwendung mit JavaFX als ObservableList
	private ObservableList<Webinar> webinare = FXCollections.observableArrayList();

	/////// Erweiterungen fuer Praesenz 2 /////////
	//DOM-Baum zum Speichern der Webinare in einer XML-Datei
	private Document doc;
	//XSD, die zum Validieren der XML-Dateien verwendet wird
	private static final String XSD_DATEINAME = "src/main/resources/webinare.xsd";

	
	
	/** Nimmt ein Webinar-Objekt entgegen und traegt es in die interne Liste ein.
      * @param webinar Das einzutragende Webinar.
	 */ 
	public void webinarEintragen(Webinar webinar) {			
			webinare.add(webinar);
	}
	
	
	/** Nimmt ein Webinar-Objekt entgegen und loescht es aus der internen Liste.
     * @param webinar Das zu loeschende Webinar.
	 */ 
	public void webinarLoeschen(Webinar webinar) {			
			webinare.remove(webinar);
	}
	
	
	/** Speichert die Webinardaten als Textdatei.
	 * @param dateiname Name der Zieldatei 
	 * @return true, falls Speicherung erfolgreich, false sonst
	 */
	public boolean webinareSpeichern(String dateiname) {
		BufferedWriter bw = null;
		boolean ok;
		try {
			//Ausgabe oeffnen
			bw = new BufferedWriter(new FileWriter(dateiname));
			
			//durch Webinarliste iterieren
			for (Webinar webinar: webinare) {
				if (webinar != null) {
					//einzelne Werte schreiben, danach jeweils Zeilenumbruch setzen
					bw.write(String.valueOf(webinar.getNummer()));	//Zahl vor dem Schreiben in String wandeln
					bw.newLine();
					bw.write(webinar.getThema());
					bw.newLine();
					bw.write(webinar.getUrl());
					bw.newLine();
					bw.write(webinar.getKennwort()); //besser hashen!
					bw.newLine();
					bw.write(String.valueOf(webinar.getFestpreis()));
					bw.newLine();
					bw.write(String.valueOf(webinar.getAnzahlBuchungen()));
					bw.newLine();
				}
			} //for
			ok = true;			//Speicherung erfolgreich
		} catch (IOException e) {
			System.err.println("Fehler beim Speichern: " + e.getMessage());
			ok = false;
		} finally {
		    closeQuietly(bw);				//Ausgabe schließen (inkl. flush)
		}
		return ok;
	}
	
	
	/** Laedt Webinardaten aus einer Textdatei in die Liste.
	 * @param dateiname Name der Quelldatei 
	 * @return true, falls Laden erfolgreich, false sonst
	 */
	public boolean webinareLaden(String dateiname) {
		Webinar webinar;
		BufferedReader br = null;
		boolean ok;
		try {
			//Eingabe oeffnen
			br = new BufferedReader(new FileReader(dateiname));

			while (br.ready()) {		//solange Buffer nicht leer
				webinar = new Webinar();
				//einzelne Werte lesen
				webinar.setNummer(Integer.parseInt(br.readLine()));
				webinar.setThema(br.readLine());
				webinar.setUrl(br.readLine());
				webinar.setKennwort(br.readLine());
				webinar.setFestpreis(Integer.parseInt(br.readLine()));
				webinar.setAnzahlBuchungen(Integer.parseInt(br.readLine()));
				webinare.add(webinar);
			} //while
			ok = true;							//Laden erfolgreich
		} catch (IOException e) {
			System.err.println("Fehler beim Laden: " + e.getMessage());
			ok = false;
		} finally {
		    closeQuietly(br);					//Eingabe schliessen
		}
		return ok;
	}

	
	/** Ermittelt das passende Webinarobjekt zur uebergebenen Nummer.
	  * @param nummer Die Nummer des gesuchten Webinars.
	  * @return Das Webinar mit der uebergebenen Nummer. null, falls nicht existent.
	 */ 
	public Webinar webinarSuchen(int nummer) {
		//Webinarliste maximal bis zum Ende durchlaufen
		for (Webinar webinar : webinare) {
			if (webinar != null && webinar.getNummer() == nummer) {	//gefunden
				return webinar;									//Webinarobjekt zurueckgeben und Methode verlassen
			}
		} //for
		return null;	//nicht gefunden
	}
	
	
	/** Sortiert die Liste mit allen Webinaren nach einem gegebenen Kriterium.
	 * @param sortierung 1 fuer Sortierung nach Nummer, 2 fuer Sortierung nach Thema, sonst unsortiert
	 */ 
	public void webinareSortieren(int sortierung) {
		switch (sortierung) {
			case 1: //Webinarliste nach Nummer sortieren 
					webinare.sort( (webinar1, webinar2) -> webinar1.getNummer() - webinar2.getNummer());
					break;
					
			case 2: //Webinarliste nach Thema sortieren
					webinare.sort( (webinar1, webinar2) -> webinar1.getThema().compareTo(webinar2.getThema()));
					break;
			//default -> nichts tun, daher nicht erforderlich
		}
	}
	
	
	/** Loescht alle Webinare aus der Webinarliste.
	 */
	public void alleWebinareLoeschen() {
		webinare.clear();
	}
	
	
	/** Gibt die aktuelle Webinarliste zurueck.
	 * 
	 * @return Die aktuelle Webinarliste.
	 */
	public ObservableList<Webinar> getWebinare() {
		return webinare;
	}

	
	/** Hilfsmethode zum Schliessen von Streams, die von den Speichern/Laden-Methoden aufgerufen wird.
	 *  Das Schliessen kann in Ausnahmefaellen eine IOException ausloesen, die aber so unwahrscheinlich ist
	 *  (Platte voll, ...), dass sie nicht behandelt wird.
	 * @param closeable der zu schliessende Stream
	 */
	private void closeQuietly(Closeable closeable) {
	    if (closeable != null) {
	        try {
	            closeable.close();
	        } catch (IOException ex) {
	            // ignore
	        }
	    }
	}
	
	
	//////////////////////////////////////////////////////////////////
	// Ab hier Erweiterungen fuer Praesenz 2
	//////////////////////////////////////////////////////////////////
	
	/** Erzeugt einen DOM-Baum fuer die aktuelle Webinar-Liste.
	 * @return null, falls Erzeugen erfolgreich, sonst Fehlermeldung als String
	*/
	public String webinareDOMErzeugen() {
		String error = null;
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

		try {
			//neues (leeres) XML-Dokument erzeugen
			DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
			doc = builder.newDocument();
			
			//Wurzelelement "webinare" erzeugen
			Element webinareElement = doc.createElement("webinare");
			doc.appendChild(webinareElement);
			
			//Webinar-Liste durchlaufen, pro Webinar ein webinar-Element erzeugen und dem DOM-Baum hinzufuegen
			for (Webinar webinar : webinare) {
				if (webinar != null) {
					Element webinarElement = webinarElementErzeugen(webinar);
					webinareElement.appendChild(webinarElement);
				}
			}
		} catch (ParserConfigurationException e) {
			error = e.getMessage();
		}
		return error;
	}

	  
	/** Erzeugt ein neues webinar-Element im DOM anhand des uebergebenen Webinar-Objekts.
	* @param webinar Das Webinar-Objekt, in dem die Daten fuer das neue webinar-Element stehen.
	* @return Das erzeugte Element
	*/
	private Element webinarElementErzeugen(Webinar webinar) {
		//Elemente erzeugen
		Element webinarElement = doc.createElement("webinar");
		Element themaElement = doc.createElement("thema");
		Element anzahlBuchungenElement = doc.createElement("anzahlBuchungen");
		Element urlElement = doc.createElement("url");
		Element kennwortElement = doc.createElement("kennwort");
		Element festpreisElement = doc.createElement("festpreis");
		
		//Attribut nummer setzen
		webinarElement.setAttribute("nummer", webinar.getNummer() + "");
		
		//Texte setzen
		Node themaText = doc.createTextNode(webinar.getThema());
		Node anzahlBuchungenText = doc.createTextNode(webinar.getAnzahlBuchungen() + "");
		Node urlText = doc.createTextNode(webinar.getUrl());
		Node kennwortText = doc.createTextNode(webinar.getKennwort());
		Node festpreisText = doc.createTextNode(webinar.getFestpreis() + "");
		
		//Knoten verknuepfen
		themaElement.appendChild(themaText);
		anzahlBuchungenElement.appendChild(anzahlBuchungenText);
		urlElement.appendChild(urlText);
		kennwortElement.appendChild(kennwortText);
		festpreisElement.appendChild(festpreisText);
		
		webinarElement.appendChild(themaElement);
		webinarElement.appendChild(anzahlBuchungenElement);
		webinarElement.appendChild(urlElement);
		webinarElement.appendChild(kennwortElement);
		webinarElement.appendChild(festpreisElement);
		
		return webinarElement;
	}


	/** Validiert den DOM-Baum gegen das gegebene Schema.
	 * @return null, falls Erzeugen erfolgreich, sonst Fehlermeldung als String
	*/
	private String webinareDOMValidieren() {
		String error = null;
		try {
			//Validierung gegen gegebene XSD
			SchemaFactory schemaFactory = SchemaFactory.newInstance(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = schemaFactory.newSchema(new File(XSD_DATEINAME));
			Validator validator = schema.newValidator();
			validator.validate(new DOMSource(doc));		//loest SAXException aus, falls nicht valide
		} catch (SAXException | IOException e) {
			error = e.getMessage();
		}
		return error;
	}

   	
   	/** Speichert die Webinardaten als XML-Datei.
	 * @param dateiname Name der Zieldatei 
	 * @return null, falls Laden erfolgreich, sonst Fehlermeldung als String
	 */
	public String webinareSpeichernXML(String dateiname) {
		String error = null;
		
		//DOM-Baum erzeugen, ist per Instanzvariable doc zugreifbar
		error = webinareDOMErzeugen();
		if (error != null) return error;	//Abbruch
		
		//DOM-Baum validieren
		error = webinareDOMValidieren();
		if (error != null) return error;	//Abbruch
		
		try {
			//Zur Ausgabe des XML-Dokuments zunaechst den Transformer erzeugen und konfigurieren
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			//Pretty Print mit 4 Zeichen Einrueckung
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			
			//Quelle und Ziel festlegen, dann transformieren (d. h. hier: in Datei schreiben)
			DOMSource source = new DOMSource(doc);
			FileWriter writer = new FileWriter(new File(dateiname));
			StreamResult result = new StreamResult(writer);
			transformer.transform(source, result);
		} catch (TransformerException | IOException e) {
			error = e.getMessage();
		}
		
		return error;
	}

	
	/** Laedt Webinardaten aus einer XML-Datei in die Liste.
	 * @param dateiname Name der Quelldatei 
	 * @return "", falls Laden erfolgreich, sonst Fehlermeldung als String
	 */
	public String webinareLadenXML(String dateiname) {
		String error = null;

		try {
			//SAX-Parser vorbereiten
			SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();

			//Validierung soll gegen gegebene XSD erfolgen
		    SchemaFactory schemaFactory = SchemaFactory.newInstance(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI);
	        Schema schema = schemaFactory.newSchema(new File(XSD_DATEINAME));
	        saxParserFactory.setSchema(schema);
			saxParserFactory.setNamespaceAware(true);

			SAXParser saxParser = saxParserFactory.newSAXParser();

			//XML-Datei mittels WebinarContentHandler parsen, eingelesene Webinare in Liste webinare eintragen
			WebinarContentHandler contentHandler = new WebinarContentHandler(webinare);
			saxParser.parse(new FileInputStream(dateiname), contentHandler);
			//ggf. Fehlermeldungen vom Parser holen
			error = contentHandler.getError();
			
		} catch (ParserConfigurationException | SAXException | IOException e) {
			error = e.getMessage();
		}
		return error;
	}
	
	/** Filtert die Webinare im aktuellen DOM-Baum im Hinblick auf die uebergebenen Filterkriterien und fuegt die verbleibenden 
	 *  Webinare in die (zuvor geleerte) Webinar-Liste ein.
	 * @param webinarFilter Der anzuwendende Filter.
	 */
	public void webinareDOMFiltern(WebinarFilter webinarFilter) {
		try {
			//Filterung per XPath durchfuehren
			XPath xpath = XPathFactory.newInstance().newXPath();

			String expression_part1 = "//webinar[contains(thema, '" + webinarFilter.getThemaSubstring() + "')";
			String expression_part2 = (webinarFilter.getFestpreisVon() == -1) ? "" : " and festpreis >= " + webinarFilter.getFestpreisVon();
			String expression_part3 = (webinarFilter.getFestpreisBis() == -1) ? "" : " and festpreis <= " + webinarFilter.getFestpreisBis();
			String expression = expression_part1 + expression_part2 + expression_part3 + "]";
			NodeList gefilterteWebinare = (NodeList)xpath.evaluate(expression, doc, XPathConstants.NODESET);
			
			//bisherige Webinare aus List entfernen
			webinare.clear();
	
			//Liste mit gefilterten Webinaren durchlaufen, Webinar-Objekte erzeugen und diese der Webinar-Liste hinzufuegen
			for (int i = 0; i < gefilterteWebinare.getLength(); i++) {
				Webinar webinar = new Webinar();
				Element webinarElement = (Element)gefilterteWebinare.item(i);
				webinar.setNummer(Integer.parseInt(webinarElement.getAttributes().getNamedItem("nummer").getTextContent()));
				NodeList webinarChildren = webinarElement.getChildNodes();
				webinar.setThema(webinarChildren.item(0).getTextContent());
				webinar.setAnzahlBuchungen(Integer.parseInt(webinarChildren.item(1).getTextContent()));
				webinar.setUrl(webinarChildren.item(2).getTextContent());
				webinar.setKennwort(webinarChildren.item(3).getTextContent());
				webinar.setFestpreis(Integer.parseInt(webinarChildren.item(4).getTextContent()));
				webinare.add(webinar);
			}
			
		} catch (XPathExpressionException e) {
			System.err.println(e);
		}
	}
}