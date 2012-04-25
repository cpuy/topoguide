package fr.colin.topoguide.model.unknown;

import fr.colin.topoguide.model.Depart;

public class UnknownDepart extends Depart {

   @Override
   public boolean isUnknown() {
      return true;
   }
}
