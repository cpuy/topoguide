package fr.colin.topoguide.database.table;

import static fr.colin.topoguide.model.TopoGuide.UNKNOWN_TOPOGUIDE;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import fr.colin.topoguide.model.TopoGuide;
import fr.colin.topoguide.model.TopoMinimal;

public class TopoGuideTable extends Table<TopoGuide> {

   public static final String TABLE_NAME = "topoguide";

   public static final String NOM = "nom";
   public static final String NUMERO = "numero";
   public static final String REMARQUES = "remarques";
   public static final String SOMMET = "sommet";
   public static final String DEPART = "depart";

   private static final String[] ALL_COLUMNS = { ID, NOM, NUMERO, REMARQUES, SOMMET, DEPART };

   protected ContentValues getInsertValues(TopoGuide topo) {
      ContentValues valeurs = new ContentValues();
      valeurs.put(NOM, topo.nom);
      valeurs.put(NUMERO, topo.numero);
      valeurs.put(REMARQUES, topo.remarques);
      valeurs.put(SOMMET, topo.sommet.id);
      valeurs.put(DEPART, topo.depart.id);
      return valeurs;
   }

   @Override
   protected TopoGuide cursorToModel(Cursor cursor) {
      TopoGuide topo = UNKNOWN_TOPOGUIDE;
      if (cursor.moveToFirst()) {
         topo = new TopoGuide();
         int i = 0;
         topo.id = cursor.getLong(i++);
         topo.nom = cursor.getString(i++);
         topo.numero = cursor.getLong(i++);
         topo.remarques = cursor.getString(i++);
         topo.sommet.id = cursor.getLong(i++);
         topo.depart.id = cursor.getLong(i++);
      }
      cursor.close();
      return topo;
   }

   // *********************************************************************/
   // *********************************************************************/
   /**
    * TODO TODO
    */
   public List<TopoMinimal> findAllMinimals() {
      Cursor c = database.query(TABLE_NAME, new String[] { ID, NOM },
            null, null, null, null, null);
      return cursorToTopoMinimal(c);
   }
   
   /**
    * TODO TODO
    */
   public Cursor findAllMinimalsC() {
      return database.query(TABLE_NAME, new String[] { ID, NOM },
            null, null, null, null, null);
   }
   // *********************************************************************/
   // *********************************************************************/

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

   @Override
   protected String getTableName() {
      return TABLE_NAME;
   }

   @Override
   protected String[] getAllColumns() {
      return ALL_COLUMNS;
   }

}
