package fr.colin.topoguide.database.table;

import static fr.colin.topoguide.model.Sommet.UNKNOWN_SOMMET;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import fr.colin.topoguide.model.Sommet;

public class SommetTable extends Table<Sommet> {

   public static final String TABLE_SOMMET = "sommet";
   public static String NOM = "nom";
   public static String MASSIF = "massif";
   public static String SECTEUR = "secteur";
   public static String ALTITUDE = "altitude";

   private static String[] ALL_COLUMNS = new String[] { ID, NOM, MASSIF, SECTEUR, ALTITUDE };

   private static final String FIND_SAME_WHERE_CLAUSE = NOM + " = ? AND " + MASSIF + " = ? AND " + SECTEUR
         + " = ? AND " + ALTITUDE + " = ?";

   public SommetTable(SQLiteDatabase database) {
      this.database = database;
   }

   public Sommet findSame(Sommet sommet) {
      Cursor cursor = database.query(TABLE_SOMMET, ALL_COLUMNS, FIND_SAME_WHERE_CLAUSE, new String[] {
            sommet.nom, sommet.massif, sommet.secteur, String.valueOf(sommet.altitude) }, null, null, null);
      return cursorToModel(cursor);
   }

   protected Sommet cursorToModel(Cursor cursor) {
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

   @Override
   protected String getTableName() {
      return TABLE_SOMMET;
   }

   @Override
   protected ContentValues getInsertValues(Sommet sommet) {
      ContentValues valeurs = new ContentValues();
      valeurs.put(NOM, sommet.nom);
      valeurs.put(MASSIF, sommet.massif);
      valeurs.put(SECTEUR, sommet.secteur);
      valeurs.put(ALTITUDE, sommet.altitude);
      return valeurs;
   }

   @Override
   protected String[] getAllColumns() {
      return ALL_COLUMNS;
   }
}
