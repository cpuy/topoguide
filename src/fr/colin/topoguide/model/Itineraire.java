package fr.colin.topoguide.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcel;
import android.os.Parcelable;
import fr.colin.topoguide.model.unknown.UnknownItineraire;
import fr.colin.topoguide.repository.ItineraireRepository;

public class Itineraire implements Parcelable {

   public static final Itineraire UNKNOWN_ITINERAIRE = new UnknownItineraire();
   
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
   private Boolean isVariante;

   public Itineraire clone() {
      Itineraire itineraire;
      if (this.isVariante()) {
         itineraire = variante();
      } else {
         itineraire = principal();
      }
      
      itineraire.id = id;
      itineraire.voie = voie;
      itineraire.orientation = orientation;
      itineraire.denivele = denivele;
      itineraire.difficulteSki = difficulteSki;
      itineraire.difficulteMontee = difficulteMontee;
      itineraire.description = description;
      itineraire.materiel = materiel;
      itineraire.exposition = exposition;
      itineraire.pente = pente;
      itineraire.dureeJour = dureeJour;
      itineraire.topoId = topoId;
      return itineraire;
   }
   
   public Itineraire() {
   }

   public static Itineraire variante() {
      Itineraire itineraire = new Itineraire();
      itineraire.isVariante = true;
      return itineraire;
   }

   public static Itineraire principal() {
      Itineraire itineraire = new Itineraire();
      itineraire.isVariante = false;
      return itineraire;
   }

   public boolean isVariante() {
      return isVariante;
   }

   
   @Override
   public boolean equals(final Object obj) {
      if (obj instanceof Itineraire) {
         final Itineraire other = (Itineraire) obj;
         return new EqualsBuilder()
            .append(id, other.id)
            .append(voie, other.voie)
            .append(orientation, other.orientation)
            .append(denivele, other.denivele)
            .append(difficulteSki, other.difficulteSki)
            .append(difficulteMontee, other.difficulteMontee)
            .append(description, other.description)
            .append(materiel, other.materiel)
            .append(exposition, other.exposition)
            .append(pente, other.pente)
            .append(dureeJour, other.dureeJour)
            .append(topoId, other.topoId)
            .append(isVariante, other.isVariante)
            .isEquals();
      } else {
         return false;
      }
   }
   
   @Override
   public int hashCode() {
      return new HashCodeBuilder(17, 37)
         .append(id)
         .append(voie)
         .append(orientation)
         .append(denivele)
         .append(difficulteSki)
         .append(difficulteMontee)
         .append(description)
         .append(materiel)
         .append(exposition)
         .append(pente)
         .append(dureeJour)
         .append(topoId)
         .append(isVariante)
         .toHashCode();
   }
   
   @Override
   public String toString() {
      return new ToStringBuilder(this)
         .append("id", id)
         .append("voie", voie)
         .append("orientation", orientation)
         .append("denivele", denivele)
         .append("difficulteSki", difficulteSki)
         .append("difficulteMontee", difficulteMontee)
         .append("description", description)
         .append("materiel", materiel)
         .append("exposition", exposition)
         .append("pente", pente)
         .append("dureeJour", dureeJour)
         .append("topoId", topoId)
         .append("variante", isVariante)
         .toString();
   }
   
   /**
    * DB
    */
   public Itineraire save(SQLiteDatabase db) {
      ContentValues valeurs = new ContentValues();
      valeurs.put(ItineraireRepository.VOIE, this.voie);
      valeurs.put(ItineraireRepository.ORIENTATION, this.orientation);
      valeurs.put(ItineraireRepository.DENIVELE, this.denivele);
      valeurs.put(ItineraireRepository.DIFFICULTE_SKI, this.difficulteSki);
      valeurs.put(ItineraireRepository.DIFFICULTE_MONTEE, this.difficulteMontee);
      valeurs.put(ItineraireRepository.DESCRIPTION, this.description);
      valeurs.put(ItineraireRepository.MATERIEL, this.materiel);
      valeurs.put(ItineraireRepository.EXPOSITION, this.exposition);
      valeurs.put(ItineraireRepository.PENTE, this.pente);
      valeurs.put(ItineraireRepository.DUREE_JOUR, this.dureeJour);
      valeurs.put(ItineraireRepository.VARIANTE, this.isVariante());
      valeurs.put(ItineraireRepository.TOPO_ID, this.topoId);
      this.id = db.insert(ItineraireRepository.TABLE, null, valeurs);
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
      dest.writeByte((byte) (isVariante ? 1 : 0));
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
      isVariante = in.readByte() == 1;
   }

   public boolean isUnknown() {
      return false;
   }
}
