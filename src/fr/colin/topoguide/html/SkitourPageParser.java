package fr.colin.topoguide.html;

import static fr.colin.topoguide.model.Itineraire.variante;

import java.util.ArrayList;
import java.util.Scanner;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import fr.colin.topoguide.model.Depart;
import fr.colin.topoguide.model.Itineraire;
import fr.colin.topoguide.model.Sommet;
import fr.colin.topoguide.model.TopoGuide;

public class SkitourPageParser {
   private static final String BASE_URL = "http://www.skitour.fr/topos/";
   
   private final Document skitourPage;
   private TopoGuide topoguide = new TopoGuide();

   public SkitourPageParser(Document skitourPage) {
      this.skitourPage = skitourPage;
   }

   public TopoGuide parsePage() {
      parseGeneralInformations();
      parseItineraire(skitourPage.select("div.itineraire_topo").first());
      parseItemsTopo();
      parseMateriel();
      return topoguide;
   }

   private void parseMateriel() {
      if (topoguide.itineraire == null) topoguide.itineraire = new Itineraire();
      topoguide.itineraire.materiel = skitourPage.getElementById("topo").ownText().replace("[]", "").trim();
   }

   private void parseItemsTopo() {
      Elements itemsTopo = skitourPage.select("div.item_topo");
      parseItemsTopo1(itemsTopo.first());
      parseItemsTopo2(itemsTopo.first().nextElementSibling());
   }

   private void parseItemsTopo2(Element nextElementSibling) {
      if (topoguide.depart == null) topoguide.depart = new Depart();
      topoguide.depart.nom = nextElementSibling.select("a").get(1).ownText();
      String[] split = nextElementSibling.html().split("<br />");
      int i = 1;
      topoguide.depart.altitude = getOnlyNumbers(parseItemTopo1(split[++i]));
      if (topoguide.itineraire == null) topoguide.itineraire = new Itineraire();
      topoguide.itineraire.exposition = Integer.valueOf(parseItemTopo1(split[++i]));
      topoguide.itineraire.pente = getOnlyNumbers(parseItemTopo1(split[++i]));
   }

   private void parseItemsTopo1(Element itemTopo1) {
      String[] split = itemTopo1.html().split("<br />");
      int i = 0;
      
      Sommet sommet = new Sommet();
      sommet.massif = parseItemTopo1(split[i++]);
      sommet.secteur = parseItemTopo1(split[i++]);
      sommet.nom = "todo"; // TODO
      topoguide.sommet = sommet;
      topoguide.orientation = parseItemTopo1(split[i++]);
      
      if (topoguide.itineraire == null) topoguide.itineraire = new Itineraire(); 
      topoguide.itineraire.denivele = getOnlyNumbers(parseItemTopo1(split[i++]));
      topoguide.itineraire.difficulteMontee = parseItemTopo1(split[i++]);
      topoguide.itineraire.difficulteSki = parseItemTopo1(split[i++]);
      i++;
      topoguide.itineraire.dureeJour = Integer.valueOf((parseItemTopo1(split[i++])));
      topoguide.type = parseItemTopo1(split[i++]);
   }
   
   private int getOnlyNumbers(String string) {
      Scanner scanner = new Scanner(string);
      scanner.useDelimiter("[^\\p{Alnum},\\.-]");
      return scanner.nextInt();
   }

   private String parseItemTopo1(String line) {
      return line.substring(line.lastIndexOf(">") + 1, line.length()).trim();
   }

   private void parseGeneralInformations() {
      topoguide.nom = skitourPage.title();
      if (topoguide.depart == null) topoguide.depart = new Depart();
      topoguide.depart.acces = skitourPage.select("div.acces_topo").first().ownText();
      topoguide.remarques = skitourPage.select("div.rem_topo").first().ownText();
   }

   private void parseItineraire(Element divItineraire) {
      Itineraire itineraire = new Itineraire();
      itineraire.description = divItineraire.ownText();
      topoguide.imageUrls = new ArrayList<String>();
      Elements images = divItineraire.select("img");
      for (Element image : images) {
         topoguide.imageUrls.add(BASE_URL + image.attr("src"));
      }
      topoguide.itineraire = itineraire;
      
      topoguide.variantes = new ArrayList<Itineraire>();
      Elements p = divItineraire.getElementsByTag("p");
      for (Element element : p) {
         topoguide.variantes.add(parseVariante(element));
      }
   }

   private Itineraire parseVariante(Element element) {
      Itineraire variante = variante();
      Element em = element.getElementsByTag("em").get(0);
      variante.voie = em.getElementsByTag("strong").get(0).ownText().replaceFirst("» ", "");
      String[] split = em.ownText().split(" ; ");
      variante.denivele = Integer.valueOf(split[0].replace("(D+ ", "").replace("m", ""));
      variante.difficulteSki = split[1].replaceFirst("Ski ", "");
      variante.orientation = split[2].replaceFirst("Orientation ", "").replace(")", "");
      variante.description = element.ownText().replace("\"", "").trim();
      // TODO
      return variante;
   }
}
