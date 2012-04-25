package fr.colin.topoguide.model.unknown;

import fr.colin.topoguide.model.Itineraire;

public class UnknownItineraire extends Itineraire {

   @Override
   public boolean isUnknown() {
      return true;
   }
}
