package fr.colin.topoguide.repository;

import static fr.colin.topoguide.model.Itineraire.UNKNOWN_ITINERAIRE;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import fr.colin.topoguide.model.Itineraire;
import fr.colin.topoguide.repository.db.DatabaseAdapter;

public class ItineraireRepository extends DatabaseAdapter {

   public static final String TABLE = "itineraire";
   
   public static final String ID = "_id";
   public static final String VOIE = "voie";
   public static final String ORIENTATION = "orientation";
   public static final String DENIVELE = "denivele";
   public static final String DIFFICULTE_SKI = "difficulte_ski";
   public static final String DIFFICULTE_MONTEE = "difficulte_montee";
   public static final String DESCRIPTION = "description";
   public static final String MATERIEL = "materiel";
   public static final String EXPOSITION = "exposition";
   public static final String PENTE = "pente";
   public static final String DUREE_JOUR = "duree_jour";
   public static final String VARIANTE = "variante";
   public static final String TOPO_ID = "topoguide";
   
   private static String[] ALL_COLUMNS = new String[] { ID, VOIE, ORIENTATION, DENIVELE, DIFFICULTE_SKI, 
      DIFFICULTE_MONTEE, DESCRIPTION, MATERIEL, EXPOSITION, PENTE, DUREE_JOUR, TOPO_ID, VARIANTE };

   private static final int VARIANTE_COLUMN_ID = ALL_COLUMNS.length - 1;
   
   public ItineraireRepository(Context context) {
      super(context);
   }

   public Itineraire create(Itineraire itineraire) {
      Itineraire d = itineraire.clone();
      d.id = database.insert(TABLE, null, getInsertValues(d));
      return d;
   }

   private ContentValues getInsertValues(Itineraire itineraire) {
      ContentValues insertValues = new ContentValues();
      insertValues.put(VOIE, itineraire.voie);
      insertValues.put(ORIENTATION, itineraire.orientation);
      insertValues.put(DENIVELE, itineraire.denivele);
      insertValues.put(DIFFICULTE_SKI, itineraire.difficulteSki);
      insertValues.put(DIFFICULTE_MONTEE, itineraire.difficulteMontee);
      insertValues.put(DESCRIPTION, itineraire.description);
      insertValues.put(MATERIEL, itineraire.materiel);
      insertValues.put(EXPOSITION, itineraire.exposition);
      insertValues.put(PENTE, itineraire.pente);
      insertValues.put(DUREE_JOUR, itineraire.dureeJour);
      insertValues.put(VARIANTE, itineraire.isVariante());
      insertValues.put(TOPO_ID, itineraire.topoId);
      return insertValues;
   }

   protected void deleteAll() {
      database.delete(TABLE, null, null);
   }

   public Itineraire findPrincipalByTopoId(long topoId) {
      String whereClause = TOPO_ID + " = " + topoId + " AND " + VARIANTE + " = 0";
      Cursor cursor = database.query(TABLE, ALL_COLUMNS, whereClause, null, null, null, null);
      if (cursor.moveToFirst()) {
         return cursorRowToItineraire(cursor);
      }
      return UNKNOWN_ITINERAIRE;
   }

   public List<Itineraire> findVariantesByTopoId(long topoId) {
      String whereClause = TOPO_ID + " = " + topoId + " AND " + VARIANTE + " = 1";
      Cursor cursor = database.query(TABLE, ALL_COLUMNS, whereClause, null, null, null, null);
      return cursorToItineraires(cursor);
   }

   private List<Itineraire> cursorToItineraires(Cursor cursor) {
      ArrayList<Itineraire> itineraires = new ArrayList<Itineraire>();
      if (cursor.moveToFirst()) {
         do {
            itineraires.add(cursorRowToItineraire(cursor));
         } while (cursor.moveToNext());
      }
      cursor.close();
      return itineraires;
   }

   private Itineraire cursorRowToItineraire(Cursor cursor) {
      Itineraire itineraire;
      int variante = cursor.getInt(VARIANTE_COLUMN_ID);
      if (variante == 0) {
         itineraire = Itineraire.principal();
      } else {
         itineraire = Itineraire.variante();
      }
      
      int i = 0;
      itineraire.id = cursor.getLong(i++);;
      itineraire.voie = cursor.getString(i++);
      itineraire.orientation = cursor.getString(i++);
      itineraire.denivele = cursor.getInt(i++);
      itineraire.difficulteSki = cursor.getString(i++);
      itineraire.difficulteMontee = cursor.getString(i++);
      itineraire.description = cursor.getString(i++);
      itineraire.materiel = cursor.getString(i++);
      itineraire.exposition = cursor.getInt(i++);
      itineraire.pente = cursor.getInt(i++);
      itineraire.dureeJour = cursor.getInt(i++);
      itineraire.topoId = cursor.getLong(i++);
      return itineraire;
   }
}
