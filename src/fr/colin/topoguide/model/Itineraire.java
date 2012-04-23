package fr.colin.topoguide.model;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcel;
import android.os.Parcelable;

public class Itineraire implements Parcelable {

   public static final String TABLE_ITINERAIRE = "itineraire";
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

   public long id;
   public String voie;
   public String orientation;
   public int denivele;
   public String difficulteSki;
   public String difficulteMontee;
   public String description;
   public String materiel;
   public int exposition;
   public int pente;
   public int dureeJour;
   public long topoId;
   private Boolean variante;

   public Itineraire() {
   }

   public static Itineraire variante() {
      Itineraire itineraire = new Itineraire();
      itineraire.variante = true;
      return itineraire;
   }

   public static Itineraire principal() {
      Itineraire itineraire = new Itineraire();
      itineraire.variante = false;
      return itineraire;
   }

   public boolean isVariante() {
      return variante;
   }

   /**
    * DB
    */
   public Itineraire save(SQLiteDatabase db) {
      ContentValues valeurs = new ContentValues();
      valeurs.put(VOIE, this.voie);
      valeurs.put(ORIENTATION, this.orientation);
      valeurs.put(DENIVELE, this.denivele);
      valeurs.put(DIFFICULTE_SKI, this.difficulteSki);
      valeurs.put(DIFFICULTE_MONTEE, this.difficulteMontee);
      valeurs.put(DESCRIPTION, this.description);
      valeurs.put(MATERIEL, this.materiel);
      valeurs.put(EXPOSITION, this.exposition);
      valeurs.put(PENTE, this.pente);
      valeurs.put(DUREE_JOUR, this.dureeJour);
      valeurs.put(VARIANTE, this.isVariante());
      valeurs.put(TOPO_ID, this.topoId);
      this.id = db.insert(TABLE_ITINERAIRE, null, valeurs);
      return this;
   }

   /**
    * Parcelable
    */
   private Itineraire(Parcel in) {
      readFromParcel(in);
   }

   public static final Parcelable.Creator<Itineraire> CREATOR = new Parcelable.Creator<Itineraire>() {
      public Itineraire createFromParcel(Parcel in) {
         return new Itineraire(in);
      }

      public Itineraire[] newArray(int size) {
         return new Itineraire[size];
      }
   };

   public int describeContents() {
      return 0;
   }

   public void writeToParcel(Parcel dest, int flags) {
      dest.writeLong(id);
      dest.writeString(voie);
      dest.writeString(orientation);
      dest.writeInt(denivele);
      dest.writeString(difficulteSki);
      dest.writeString(difficulteMontee);
      dest.writeString(description);
      dest.writeString(materiel);
      dest.writeInt(exposition);
      dest.writeInt(pente);
      dest.writeInt(dureeJour);
      dest.writeLong(topoId);
      dest.writeByte((byte) (variante ? 1 : 0));
   }

   private void readFromParcel(Parcel in) {
      id = in.readLong();
      voie = in.readString();
      orientation = in.readString();
      denivele = in.readInt();
      difficulteSki = in.readString();
      difficulteMontee = in.readString();
      description = in.readString();
      materiel = in.readString();
      exposition = in.readInt();
      pente = in.readInt();
      dureeJour = in.readInt();
      topoId = in.readInt();
      variante = in.readByte() == 1;
   }
}
