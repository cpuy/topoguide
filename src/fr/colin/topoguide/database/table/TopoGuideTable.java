package fr.colin.topoguide.database.table;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import fr.colin.topoguide.model.TopoGuide;
import fr.colin.topoguide.model.TopoMinimal;

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
public class TopoGuideTable extends Table<TopoGuide> {

   public static final String TABLE = "topoguide";

   public static final String NOM = "nom";
   public static final String ACCES = "access";
   public static final String ORIENTATION = "orientation";
   public static final String NUMERO = "numero";
   public static final String REMARQUES = "remarques";
   public static final String SOMMET = "sommet";

   public TopoGuideTable(SQLiteDatabase database) {
      this.database = database;
   }
   
   protected ContentValues getInsertValues(TopoGuide topo) {
      ContentValues valeurs = new ContentValues();
      valeurs.put(NOM, topo.nom);
      valeurs.put(ACCES, topo.access);
      valeurs.put(ORIENTATION, topo.orientation);
      valeurs.put(NUMERO, topo.numero);
      valeurs.put(REMARQUES, topo.remarques);
      return valeurs;
   }

   @Override
   protected TopoGuide cursorToModel(Cursor cursor) {
      TopoGuide topo = TopoGuide.UNKNOWN_TOPOGUIDE;
      if (cursor.moveToFirst()) {
         topo = new TopoGuide();
         int i = 0;
         topo.id = cursor.getLong(i++);
         topo.nom = cursor.getString(i++);
         topo.access = cursor.getString(i++);
         topo.orientation = cursor.getString(i++);
         topo.numero = cursor.getString(i++);
         topo.remarques = cursor.getString(i++);
      }
      cursor.close();
      return topo;
   }

   public List<TopoMinimal> findAllMinimals() {
      Cursor c = database.query(TopoGuideTable.TABLE, new String[] { TopoGuideTable.ID, TopoGuideTable.NOM },
            null, null, null, null, null);
      return cursorToTopoMinimal(c);
   }
   
   public Cursor findAllMinimalsC() {
      return database.query(TopoGuideTable.TABLE, new String[] { TopoGuideTable.ID, TopoGuideTable.NOM },
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

   @Override
   protected String getTableName() {
      return TABLE;
   }

   @Override
   protected String[] getAllColumns() {
      return new String[] { ID, NOM, ACCES, ORIENTATION, NUMERO, REMARQUES, SOMMET };
   }

}
