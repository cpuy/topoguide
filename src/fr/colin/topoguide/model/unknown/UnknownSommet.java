package fr.colin.topoguide.model.unknown;

import fr.colin.topoguide.model.Sommet;


public class UnknownSommet extends Sommet {

   @Override
   public boolean isUnknown() {
      return true;
   }
}
