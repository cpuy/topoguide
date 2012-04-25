package fr.colin.topoguide.repository;

import static fr.colin.topoguide.model.Sommet.UNKNOWN_SOMMET;
import static fr.colin.topoguide.model.TopoGuide.TABLE_TOPOGUIDE;
import static fr.colin.topoguide.model.TopoGuide.TOPOGUIDE_ID;
import static fr.colin.topoguide.model.TopoGuide.TOPOGUIDE_NOM;
import static fr.colin.topoguide.model.TopoGuide.UNKNOWN_TOPOGUIDE;
import static fr.colin.topoguide.repository.SommetRepository.TABLE_SOMMET;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import fr.colin.topoguide.model.Itineraire;
import fr.colin.topoguide.model.Sommet;
import fr.colin.topoguide.model.TopoGuide;
import fr.colin.topoguide.model.TopoMinimal;
import fr.colin.topoguide.repository.db.TopoGuideOpenHelper;

/**
 * FIXME :
 *    - pb bd ne se close jamais
 *    - instanctiation d'un nouveau repo dans chaque activity
 *    
 * TODO :
 *    - nettoyage
 *    
 * @author colin
 *
 */
public class TopoGuideRepository {

   public static final String ITINERAIRE_TOPO = "topoguide";


   private SQLiteDatabase topoguideDB;
   private TopoGuideOpenHelper topoGuideOpenHelper;
   
   public TopoGuideRepository(Context context) {
      topoGuideOpenHelper = new TopoGuideOpenHelper(context);
   }
   
   public void open() {
      topoguideDB = topoGuideOpenHelper.getWritableDatabase();
   }
   
   public void close() {
      topoguideDB.close();
   }
   
   public boolean isOpen() {
      return topoguideDB != null && topoguideDB.isOpen();
   }

   public long save(TopoGuide topo) {
      return topo.save(topoguideDB);
   }
   
   /** TODO sommet ?? */
   public boolean delete(long id) {
      return topoguideDB.delete(ItineraireRepository.TABLE, ITINERAIRE_TOPO + "=" + id, null) >0
         && topoguideDB.delete(TABLE_TOPOGUIDE, TOPOGUIDE_ID + "=" + id, null) > 0;
   }

   private static final String FIND_TOPO_BY_ID = 
         "SELECT * FROM " + TABLE_TOPOGUIDE + " t INNER JOIN " + TABLE_SOMMET + " s ON t.sommet = s._id WHERE t._id = ?";
   
   public TopoGuide findById(long id) {
      Cursor c = topoguideDB.rawQuery(FIND_TOPO_BY_ID, new String[] {String.valueOf(id)});
      
      if (c.getCount() > 0) {
         TopoGuide topo = cursorToTopoGuide(c);
         topo.variantes = findVariantesByTopoId(id);
         return topo;
      } else {
         return UNKNOWN_TOPOGUIDE;
      }
   }

   public Sommet findSommetById(long sommetId) {
      return UNKNOWN_SOMMET;
   }
   
   private List<Itineraire> findVariantesByTopoId(long id) {
      Cursor c = topoguideDB.query(ItineraireRepository.TABLE, new String[] { ItineraireRepository.DENIVELE, ItineraireRepository.DESCRIPTION, ItineraireRepository.DIFFICULTE_SKI, ItineraireRepository.ORIENTATION, ItineraireRepository.VARIANTE }, TopoGuideRepository.ITINERAIRE_TOPO
            + " = " + id, null, null, null, null);
      return cursorToItineraire(c);
   }

   private List<Itineraire> cursorToItineraire(Cursor cursor) {
      if (cursor.getCount() > 0) {
      
      Itineraire topo = Itineraire.variante();
      cursor.moveToFirst();
      int i = 0;
      topo.denivele = cursor.getInt(i++);
      topo.description = cursor.getString(i++);
      
//      topo.access = cursor.getString(i++);
//      topo.secteur = cursor.getString(i++);
//      topo.orientation = cursor.getString(i++);
//      topo.numero = cursor.getString(i++);
      cursor.close();
      return Arrays.asList(topo);
      }
      else {
         return new ArrayList<Itineraire>();
      }
   }

   private TopoGuide cursorToTopoGuide(Cursor cursor) {
      TopoGuide topo = new TopoGuide();
      cursor.moveToFirst();
      int i = 0;
      topo.id = cursor.getLong(i++);
      topo.nom = cursor.getString(i++);
      topo.access = cursor.getString(i++);
      topo.orientation = cursor.getString(i++);
      topo.numero = cursor.getString(i++);
      topo.remarques = cursor.getString(i++);
      
      Sommet sommet = new Sommet();
      sommet.id = cursor.getLong(i++);
      sommet.nom = cursor.getString(i++);
      sommet.massif = cursor.getString(i++);
      sommet.secteur = cursor.getString(i++);
      topo.sommet = sommet;
      
      cursor.close();
      return topo;
   }

   public List<TopoMinimal> findAllMinimals() {
      Cursor c = topoguideDB.query(TABLE_TOPOGUIDE, new String[] { TOPOGUIDE_ID, TOPOGUIDE_NOM },
            null, null, null, null, null);
      return cursorToTopoMinimal(c);
   }
   
   public Cursor findAllMinimalsC() {
      return topoguideDB.query(TABLE_TOPOGUIDE, new String[] { TOPOGUIDE_ID, TOPOGUIDE_NOM },
            null, null, null, null, null);
   }

   private List<TopoMinimal> cursorToTopoMinimal(Cursor c) {
      List<TopoMinimal> topos = new ArrayList<TopoMinimal>();
      if (c.getCount() > 0) {
         c.moveToFirst();
         do {
            TopoMinimal topoMinimal = new TopoMinimal();
            topoMinimal.id = c.getLong(0);
            topoMinimal.nom = c.getString(1);
            topoMinimal.massif = "toto";
            topos.add(topoMinimal);
         } while (c.moveToNext());
      }
      c.close();
      return topos;
   }
   
   public void deleteAll() {
      topoguideDB.delete(ItineraireRepository.TABLE, null, null);
      topoguideDB.delete(TABLE_TOPOGUIDE, null, null);
      topoguideDB.delete(TABLE_SOMMET, null, null);
      topoguideDB.delete(DepartRepository.TABLE, null, null);
   }
}
