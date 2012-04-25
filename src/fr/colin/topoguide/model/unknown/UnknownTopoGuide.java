package fr.colin.topoguide.model.unknown;

import fr.colin.topoguide.model.TopoGuide;

public class UnknownTopoGuide extends TopoGuide {
  
   @Override
   public boolean isUnknown() {
      return true;
   }
}
