package fr.colin.topoguide.repository;

import static fr.colin.topoguide.model.Sommet.UNKNOWN_SOMMET;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import fr.colin.topoguide.model.Sommet;
import fr.colin.topoguide.repository.db.DatabaseAdapter;

public class SommetRepository extends DatabaseAdapter {

   public static final String TABLE_SOMMET = "sommet";
   public static String ID = "_id";
   public static String NOM = "nom";
   public static String MASSIF = "massif";
   public static String SECTEUR = "secteur";
   public static String ALTITUDE = "altitude";
   
   private static String[] ALL_COLUMNS = new String[] { ID, NOM, MASSIF, SECTEUR, ALTITUDE };

   public SommetRepository(Context context) {
      super(context);
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
      if (c.getCount() > 0) {
         return cursorToSommet(c);
      } else {
         return UNKNOWN_SOMMET;
      }
   }

   private Sommet cursorToSommet(Cursor cursor) {
      Sommet sommet = new Sommet();
      int i = 0;
      cursor.moveToFirst();
      sommet.id = cursor.getLong(i++);
      sommet.nom = cursor.getString(i++);
      sommet.massif = cursor.getString(i++);
      sommet.secteur = cursor.getString(i++);
      sommet.altitude = cursor.getInt(i++);
      cursor.close();
      return sommet;
   }
}
