package fr.colin.topoguide.repository;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import fr.colin.topoguide.database.DatabaseAdapter;
import fr.colin.topoguide.database.table.DepartTable;
import fr.colin.topoguide.database.table.ItineraireTable;
import fr.colin.topoguide.database.table.SommetTable;
import fr.colin.topoguide.database.table.TopoGuideTable;
import fr.colin.topoguide.model.Itineraire;
import fr.colin.topoguide.model.TopoGuide;
import fr.colin.topoguide.model.TopoMinimal;

public class LocalTopoGuideRepository extends DatabaseAdapter {

   private final TopoGuideTable topoGuideTable;
   private final SommetTable sommetTable;
   private final DepartTable departTable;
   private final ItineraireTable itineraireTable;

   public static LocalTopoGuideRepository fromContext(Context context) {
      return new LocalTopoGuideRepository(context, new TopoGuideTable(), new SommetTable(),
            new DepartTable(), new ItineraireTable());
   }

   protected LocalTopoGuideRepository(Context context, TopoGuideTable topoGuideRepository,
         SommetTable sommetRepository, DepartTable departRepository, ItineraireTable itineraireRepository) {
      super(context);
      this.topoGuideTable = topoGuideRepository;
      this.sommetTable = sommetRepository;
      this.departTable = departRepository;
      this.itineraireTable = itineraireRepository;
   }

   public void open() {
      super.open();
      topoGuideTable.setDatabase(database);
      sommetTable.setDatabase(database);
      departTable.setDatabase(database);
      itineraireTable.setDatabase(database);
   }

   public TopoGuide create(TopoGuide topo) {
      createSommetAndDepartFirst(topo);
      topo = thenCreateTopo(topo);
      finallyCreateItineraireAndVariantes(topo);
      return topo;
   }

   private TopoGuide thenCreateTopo(TopoGuide topo) {
      topo = topoGuideTable.create(topo);
      return topo;
   }

   private void finallyCreateItineraireAndVariantes(TopoGuide topo) {
      topo.itineraire.topoId = topo.id;
      topo.itineraire = itineraireTable.create(topo.itineraire);
      
      List<Itineraire> variantes = new ArrayList<Itineraire>();
      for (Itineraire variante : topo.variantes) {
         variante.topoId = topo.id;
         variantes.add(itineraireTable.create(variante));
      }
      topo.variantes = variantes;
   }

   private void createSommetAndDepartFirst(TopoGuide topo) {
      topo.sommet = sommetTable.create(topo.sommet);
      topo.depart = departTable.create(topo.depart);
   }

   public List<TopoMinimal> findAllMinimals() {
      return null;
   }

   public TopoGuide findTopoById(long id) {
      TopoGuide topo = topoGuideTable.findById(id);
      if (!topo.isUnknown()) {
         topo.sommet = sommetTable.findById(topo.sommet.id);
         topo.depart = departTable.findById(topo.depart.id);
         topo.itineraire = itineraireTable.findPrincipalByTopoId(topo.id);
         topo.variantes = itineraireTable.findVariantesByTopoId(topo.id);
      }
      return topo;
   }

   protected void empty() {
      itineraireTable.deleteAll();
      topoGuideTable.deleteAll();
      sommetTable.deleteAll();
      departTable.deleteAll();
   }
}
