package fr.colin.topoguide.model;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcel;
import android.os.Parcelable;

public class Sommet implements Parcelable {

   public static final String TABLE_SOMMET = "sommet";
   public static String ID = "id";
   public static String NOM = "nom";
   public static String MASSIF = "massif";
   public static String SECTEUR = "secteur";
   public static String ALTITUDE = "altitude";

   public long id;
   public String nom;
   public String massif;
   public String secteur;
   public int altitude;

   public Sommet() {
 
   }
   
   /**
    * DB
    */
   public Sommet save(SQLiteDatabase db) {
      ContentValues valeurs = new ContentValues();
      valeurs.put(NOM, nom);
      valeurs.put(MASSIF, massif);
      valeurs.put(SECTEUR, secteur);
      valeurs.put(ALTITUDE, altitude);
      this.id = db.insert(TABLE_SOMMET, null, valeurs);
      return this;
   }

   /**
    * Parcelable
    */
   private Sommet(Parcel in) {
      readFromParcel(in);
   }

   public static final Parcelable.Creator<Sommet> CREATOR = new Parcelable.Creator<Sommet>() {
      public Sommet createFromParcel(Parcel in) {
         return new Sommet(in);
      }

      public Sommet[] newArray(int size) {
         return new Sommet[size];
      }
   };

   public int describeContents() {
      return 0;
   }

   public void writeToParcel(Parcel dest, int flags) {
      dest.writeLong(id);
      dest.writeString(nom);
      dest.writeString(massif);
      dest.writeString(secteur);
      dest.writeInt(altitude);
   }

   private void readFromParcel(Parcel in) {
      id = in.readLong();
      nom = in.readString();
      massif = in.readString();
      secteur = in.readString();
      altitude = in.readInt();
   }
}
