package fr.colin.topoguide.html;

import static fr.colin.topoguide.model.Itineraire.variante;
import static org.apache.commons.lang3.StringUtils.substringAfter;
import static org.apache.commons.lang3.StringUtils.substringAfterLast;
import static org.apache.commons.lang3.StringUtils.substringBefore;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import fr.colin.topoguide.model.Depart;
import fr.colin.topoguide.model.Itineraire;
import fr.colin.topoguide.model.Type;
import fr.colin.topoguide.model.enums.Orientation;
import fr.colin.topoguide.model.Sommet;
import fr.colin.topoguide.model.TopoGuide;

public class SkitourPageParser {

   public static final int ITEMS_TOPO_2_ALTITUDE = 2;
   private static final int ITEMS_TOPO2_EXPOSITION = 3;
   private static final int ITEMS_TOPO2_PENTE = 4;

   private static final int ITEMS_TOPO_1_MASSIF = 0;
   private static final int ITEMS_TOPO_1_SECTEUR = 1;
   private static final int ITEMS_TOPO1_ORIENTATION = 2;
   private static final int ITEMS_TOPO1_DENIVELE = 3;
   private static final int ITEMS_TOPO1_DIFFICULTE_MONTEE = 4;
   private static final int ITEMS_TOPO1_DIFFICULTE_SKI = 5;
   private static final int ITEMS_TOPO1_DUREE_JOUR = 7;
   private static final int ITEMS_TOPO1_TYPE = 8;

   private ArrayList<String> div_items_topo_1_lines;
   private ArrayList<String> div_items_topo_2_lines;

   private Element title;
   private Element div_itineraire;
   private Element div_topo;
   private Element div_acces;
   private Element div_remarque;
   private Element lien_depart;
   
   public SkitourPageParser(Document skitourPage) {
      splitPageIntoElements(skitourPage);
   }

   private void splitPageIntoElements(Document skitourPage) {
      title = skitourPage.select("h1").first();
      div_topo = skitourPage.getElementById("topo");
      
      Element div_items_topo_1 = div_topo.select("div.item_topo").first();
      div_items_topo_1_lines = splitItemsTopoLines(div_items_topo_1);
      
      Element div_items_topo_2 = div_items_topo_1.nextElementSibling();
      lien_depart = div_items_topo_2.select("a").get(1);
      div_items_topo_2_lines = splitItemsTopoLines(div_items_topo_2);
      
      div_acces = div_topo.select("div.acces_topo").first();
      div_itineraire = div_topo.select("div.itineraire_topo").first();
      div_remarque = div_topo.select("div.rem_topo").first();
   }

   private ArrayList<String> splitItemsTopoLines(Element divItemsTopo) {
      ArrayList<String> items = new ArrayList<String>();
      for (String line : divItemsTopo.html().split("<br />")) {
         items.add(parseItemTopoLine(line));
      }
      return items;
   }

   public TopoGuide parsePage() {
      TopoGuide topoguide = createTopoGuideWithGeneralInformations();
      topoguide.sommet = parseSommet();
      topoguide.depart = parseDepart();
      topoguide.variantes = parseVariantes();
      topoguide.itineraire = parseItineraire();
      return topoguide;
   }

   protected TopoGuide createTopoGuideWithGeneralInformations() {
      TopoGuide topoguide = new TopoGuide();
      topoguide.nom = title.ownText();
      topoguide.remarques = div_remarque.ownText();
      return topoguide;
   }

   protected Itineraire parseItineraire() {
      Itineraire itineraire = Itineraire.principal();
      itineraire.voie = substringAfter(title.ownText(), ",").trim();
      itineraire.orientation = Orientation.valueOf(div_items_topo_1_lines.get(ITEMS_TOPO1_ORIENTATION));
      itineraire.denivele = getOnlyNumbers(div_items_topo_1_lines.get(ITEMS_TOPO1_DENIVELE));
      itineraire.difficulteSki = div_items_topo_1_lines.get(ITEMS_TOPO1_DIFFICULTE_SKI);
      itineraire.description = div_itineraire.ownText();
      itineraire.difficulteMontee = div_items_topo_1_lines.get(ITEMS_TOPO1_DIFFICULTE_MONTEE);
      itineraire.materiel = div_topo.ownText().replace("[]", "").trim();
      itineraire.exposition = getOnlyNumbers(div_items_topo_2_lines.get(ITEMS_TOPO2_EXPOSITION));
      itineraire.pente = getOnlyNumbers(div_items_topo_2_lines.get(ITEMS_TOPO2_PENTE));
      itineraire.dureeJour = Integer.valueOf(div_items_topo_1_lines.get(ITEMS_TOPO1_DUREE_JOUR));
      itineraire.type = Type.fromValue(div_items_topo_1_lines.get(ITEMS_TOPO1_TYPE));
      return itineraire;
   }

   protected Sommet parseSommet() {
      Sommet sommet = new Sommet();
      sommet.nom = substringBefore(title.ownText(), ",");
      sommet.altitude = getOnlyNumbers(title.select("span").first().ownText());
      sommet.massif = div_items_topo_1_lines.get(ITEMS_TOPO_1_MASSIF);
      sommet.secteur = div_items_topo_1_lines.get(ITEMS_TOPO_1_SECTEUR);
      return sommet;
   }

   protected Depart parseDepart() {
      Depart depart = new Depart();
      depart.nom = lien_depart.ownText();
      depart.altitude = getOnlyNumbers(div_items_topo_2_lines.get(ITEMS_TOPO_2_ALTITUDE));
      depart.acces = div_acces.ownText();
      return depart;
   }

   protected List<Itineraire> parseVariantes() {
      List<Itineraire> variantes = new ArrayList<Itineraire>();
      Elements paragraphes = div_itineraire.getElementsByTag("p");
      for (Element p : paragraphes) {
         variantes.add(parseVariante(p));
      }
      return variantes;
   }

   // TODO : plus propre
   private Itineraire parseVariante(Element p) {
      Itineraire variante = variante();
      Element em = p.getElementsByTag("em").first();
      variante.voie = em.getElementsByTag("strong").first().ownText().replaceFirst("» ", "");
      String[] split = em.ownText().split(" ; ");
      variante.denivele = Integer.valueOf(split[0].replace("(D+ ", "").replace("m", ""));
      variante.difficulteSki = split[1].replaceFirst("Ski ", "");
      variante.orientation = Orientation.valueOf(split[2].replaceFirst("Orientation ", "").replace(")", ""));
      variante.description = p.ownText().replace("\"", "").trim();
      return variante;
   }
   
   protected String parseItemTopoLine(String line) {
      return substringAfterLast(line, ">").trim();
   }
   
   protected int getOnlyNumbers(String string) {
      Scanner scanner = new Scanner(string);
      scanner.useDelimiter("[^\\p{Alnum},\\.-]");
      return scanner.nextInt();
   }

   public List<String> parseImagesUrls() {
      ArrayList<String> imagesUrls = new ArrayList<String>();
      Elements images = div_itineraire.select("img");
      for (Element image : images) {
         imagesUrls.add(image.attr("abs:src"));
      }
      return imagesUrls;
   }
}
