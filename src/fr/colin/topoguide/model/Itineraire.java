package fr.colin.topoguide.model;

import static fr.colin.topoguide.model.enums.Orientation.INCONNUE;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import android.os.Parcel;
import android.os.Parcelable;
import fr.colin.topoguide.model.enums.Orientation;
import fr.colin.topoguide.model.unknown.UnknownItineraire;

public class Itineraire implements Parcelable {

   public static final Itineraire UNKNOWN_ITINERAIRE = new UnknownItineraire();
   
   public long id;
   public String voie;
   public Orientation orientation = INCONNUE;
   public int denivele;
   public String difficulteSki;
   public String description;
   public Type type = Type.INCONNU;
   public String difficulteMontee;
   public String materiel;
   public int exposition;
   public int pente;
   public int dureeJour;
   public long topoId;
   private boolean isVariante;

   protected Itineraire() {
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

   
   public boolean isUnknown() {
      return false;
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
            .append(type, other.type)
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
         .append(type)
         .append(topoId)
         .append(isVariante)
         .toHashCode();
   }
   
   @Override
   public String toString() {
      return new ToStringBuilder(this)
         .append("id", id)
         .append("voie", voie)
         .append("orientation", orientation.value())
         .append("denivele", denivele)
         .append("difficulteSki", difficulteSki)
         .append("difficulteMontee", difficulteMontee)
         .append("description", description)
         .append("materiel", materiel)
         .append("exposition", exposition)
         .append("pente", pente)
         .append("dureeJour", dureeJour)
         .append("type", type.value())
         .append("topoId", topoId)
         .append("variante", isVariante)
         .toString();
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
      dest.writeInt(orientation.ordinal());
      dest.writeInt(type.ordinal());
   }

   private void readFromParcel(Parcel in) {
      id = in.readLong();
      voie = in.readString();
      denivele = in.readInt();
      difficulteSki = in.readString();
      difficulteMontee = in.readString();
      description = in.readString();
      materiel = in.readString();
      exposition = in.readInt();
      pente = in.readInt();
      dureeJour = in.readInt();
      topoId = in.readLong();
      isVariante = in.readByte() == 1;
      orientation = Orientation.values()[in.readInt()];
      type = Type.values()[in.readInt()];
   }
}
