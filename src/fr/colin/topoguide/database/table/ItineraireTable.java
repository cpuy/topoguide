package fr.colin.topoguide.database.table;

import static fr.colin.topoguide.model.Itineraire.UNKNOWN_ITINERAIRE;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import fr.colin.topoguide.model.Itineraire;
import fr.colin.topoguide.model.Itineraire.Orientation;
import fr.colin.topoguide.model.Itineraire.Type;

public class ItineraireTable extends Table<Itineraire> {

   public static final String TABLE = "itineraire";

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
   public static final String TYPE = "type";
   public static final String VARIANTE = "variante";
   public static final String TOPO_ID = "topoguide";

   private static String[] ALL_COLUMNS = new String[] { ID, VOIE, ORIENTATION, DENIVELE, DIFFICULTE_SKI,
         DIFFICULTE_MONTEE, DESCRIPTION, MATERIEL, EXPOSITION, PENTE, DUREE_JOUR, TYPE, TOPO_ID, VARIANTE };

   private static final int VARIANTE_COLUMN_ID = ALL_COLUMNS.length - 1;

   private static final String FIND_PRINCIPAL_BY_TOPO_ID_WHERE_CLAUSE = TOPO_ID + " = ? AND " + VARIANTE
         + " = 0";
   private static final String FIND_VARIANTES_BY_TOPO_ID_WHERE_CLAUSE = TOPO_ID + " = ? AND " + VARIANTE
         + " = 1";

   @Override
   protected ContentValues getInsertValues(Itineraire itineraire) {
      ContentValues insertValues = new ContentValues();
      insertValues.put(VOIE, itineraire.voie);
      insertValues.put(ORIENTATION, itineraire.orientation.value());
      insertValues.put(DENIVELE, itineraire.denivele);
      insertValues.put(DIFFICULTE_SKI, itineraire.difficulteSki);
      insertValues.put(DIFFICULTE_MONTEE, itineraire.difficulteMontee);
      insertValues.put(DESCRIPTION, itineraire.description);
      insertValues.put(MATERIEL, itineraire.materiel);
      insertValues.put(EXPOSITION, itineraire.exposition);
      insertValues.put(PENTE, itineraire.pente);
      insertValues.put(DUREE_JOUR, itineraire.dureeJour);
      insertValues.put(TYPE, itineraire.type.value());
      insertValues.put(VARIANTE, itineraire.isVariante());
      insertValues.put(TOPO_ID, itineraire.topoId);
      return insertValues;
   }

   public Itineraire findPrincipalByTopoId(long topoId) {
      Cursor cursor = database.query(TABLE, ALL_COLUMNS, FIND_PRINCIPAL_BY_TOPO_ID_WHERE_CLAUSE,
            toStringArray(topoId), null, null, null);
      return cursorToModel(cursor);
   }

   public List<Itineraire> findVariantesByTopoId(long topoId) {
      Cursor cursor = database.query(TABLE, ALL_COLUMNS, FIND_VARIANTES_BY_TOPO_ID_WHERE_CLAUSE,
            toStringArray(topoId), null, null, null);
      return cursorToItineraires(cursor);
   }

   @Override
   protected Itineraire cursorToModel(Cursor cursor) {
      Itineraire itineraire = UNKNOWN_ITINERAIRE;
      if (cursor.moveToFirst()) {
         itineraire = cursorRowToItineraire(cursor);
      }
      cursor.close();
      return itineraire;
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
      itineraire.id = cursor.getLong(i++);
      ;
      itineraire.voie = cursor.getString(i++);
      itineraire.orientation = Orientation.fromValue(cursor.getString(i++));
      itineraire.denivele = cursor.getInt(i++);
      itineraire.difficulteSki = cursor.getString(i++);
      itineraire.difficulteMontee = cursor.getString(i++);
      itineraire.description = cursor.getString(i++);
      itineraire.materiel = cursor.getString(i++);
      itineraire.exposition = cursor.getInt(i++);
      itineraire.pente = cursor.getInt(i++);
      itineraire.dureeJour = cursor.getInt(i++);
      itineraire.type = Type.fromValue(cursor.getString(i++));
      itineraire.topoId = cursor.getLong(i++);
      return itineraire;
   }

   @Override
   protected String getTableName() {
      return TABLE;
   }

   @Override
   protected String[] getAllColumns() {
      return ALL_COLUMNS;
   }
}
