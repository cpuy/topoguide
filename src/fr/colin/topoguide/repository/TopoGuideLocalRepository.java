package fr.colin.topoguide.repository;

import java.util.List;

import fr.colin.topoguide.database.DatabaseAdapter;
import fr.colin.topoguide.database.DatabaseOpenHelper;
import fr.colin.topoguide.database.table.DepartTable;
import fr.colin.topoguide.database.table.ItineraireTable;
import fr.colin.topoguide.database.table.SommetTable;
import fr.colin.topoguide.database.table.TopoGuideTable;
import fr.colin.topoguide.model.TopoGuide;
import fr.colin.topoguide.model.TopoMinimal;
import android.content.Context;

public class TopoGuideLocalRepository extends DatabaseAdapter {

   private final TopoGuideTable topoGuideRepository;
   private final SommetTable sommetRepository;
   private final DepartTable departRepository;
   private final ItineraireTable itineraireRepository;

   public static TopoGuideLocalRepository fromContext(Context context) {
      return new TopoGuideLocalRepository(context, new TopoGuideTable(database), new SommetTable(database), 
            new DepartTable(database), new ItineraireTable(database));
   }
   
   protected TopoGuideLocalRepository(Context context, TopoGuideTable topoGuideRepository, 
         SommetTable sommetRepository, DepartTable departRepository, ItineraireTable itineraireRepository) {
      super(context);
      this.topoGuideRepository = topoGuideRepository;
      this.sommetRepository = sommetRepository;
      this.departRepository = departRepository;
      this.itineraireRepository = itineraireRepository;
   }

   public void open() {
      if (!isOpen()) {
         DatabaseOpenHelper topoGuideOpenHelper = new DatabaseOpenHelper(context);
         database = topoGuideOpenHelper.getWritableDatabase();
         topoGuideRepository.setDatabase(database);
         sommetRepository.setDatabase(database);
         departRepository.setDatabase(database);
         itineraireRepository.setDatabase(database);
      }
   } 
   
   public TopoGuide create(TopoGuide topo) {
      topo.sommet = sommetRepository.create(topo.sommet);
      topo.depart = departRepository.create(topo.depart);
      topo.itineraire = itineraireRepository.create(topo.itineraire);
      return topoGuideRepository.create(topo);
   }

   public List<TopoMinimal> findAllMinimals() {
      return null;
   }

   public String findById(long id) {
      return null;
   }
   

}
