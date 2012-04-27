package fr.colin.topoguide.repository;

import static fr.colin.topoguide.model.Sommet.UNKNOWN_SOMMET;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import fr.colin.topoguide.model.Sommet;

public class SommetRepository {

   public static final String TABLE_SOMMET = "sommet";
   public static String ID = "_id";
   public static String NOM = "nom";
   public static String MASSIF = "massif";
   public static String SECTEUR = "secteur";
   public static String ALTITUDE = "altitude";

   private static String[] ALL_COLUMNS = new String[] { ID, NOM, MASSIF, SECTEUR, ALTITUDE };

   private static final String FIND_SAME_WHERE_CLAUSE = NOM + " = ? AND " + MASSIF + " = ? AND " + SECTEUR
         + " = ? AND " + ALTITUDE + " = ?";

   private final SQLiteDatabase database;

   public SommetRepository(SQLiteDatabase database) {
      this.database = database;
   }

   public Sommet create(Sommet sommet) {
      ContentValues valeurs = new ContentValues();
      valeurs.put(NOM, sommet.nom);
      valeurs.put(MASSIF, sommet.massif);
      valeurs.put(SECTEUR, sommet.secteur);
      valeurs.put(ALTITUDE, sommet.altitude);

      Sommet s = sommet.clone();
      s.id = database.insert(TABLE_SOMMET, null, valeurs);
      return s;
   }

   protected void deleteAll() {
      database.delete(TABLE_SOMMET, null, null);
   }

   public Sommet findById(long sommetId) {
      Cursor c = database.query(TABLE_SOMMET, ALL_COLUMNS, ID + " = " + sommetId, null, null, null, null);
      return cursorToSommet(c);
   }

   public Sommet findSame(Sommet sommet) {
      Cursor cursor = database.query(TABLE_SOMMET, ALL_COLUMNS, FIND_SAME_WHERE_CLAUSE, new String[] {
            sommet.nom, sommet.massif, sommet.secteur, String.valueOf(sommet.altitude) }, null, null, null);
      return cursorToSommet(cursor);
   }

   private Sommet cursorToSommet(Cursor cursor) {
      Sommet sommet = UNKNOWN_SOMMET;
      if (cursor.moveToFirst()) {
         int i = 0;
         sommet = new Sommet();
         sommet.id = cursor.getLong(i++);
         sommet.nom = cursor.getString(i++);
         sommet.massif = cursor.getString(i++);
         sommet.secteur = cursor.getString(i++);
         sommet.altitude = cursor.getInt(i++);
      }
      cursor.close();
      return sommet;
   }
}
