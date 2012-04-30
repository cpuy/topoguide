package fr.colin.topoguide.repository;

import static fr.colin.topoguide.utils.Downloader.downloadDocument;
import fr.colin.topoguide.html.SkitourPageParser;
import fr.colin.topoguide.model.TopoGuide;

public class RemoteTopoGuideRepository {
   
   private static final String SKITOUR_BASE_URL = "http://www.skitour.fr/topos/";
   
   public static RemoteTopoGuideRepository skitour() {
      return new RemoteTopoGuideRepository();
   }
   
   public TopoGuide fetchTopoById(long topoId) throws RepositoryException {
      try {
         return new SkitourPageParser(downloadDocument(skitourUrl(topoId)), topoId).parsePage();
      } catch (Throwable t) {
         throw new RepositoryException("An error occured when fetching topoguide from skitour", t);
      }
   }

   private String skitourUrl(long topoId) {
      return SKITOUR_BASE_URL + "," + topoId + ".html";
   }
}
