package fr.colin.topoguide.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import android.os.Parcel;
import android.os.Parcelable;
import fr.colin.topoguide.model.unknown.UnknownItineraire;

public class Itineraire implements Parcelable {

   public static final Itineraire UNKNOWN_ITINERAIRE = new UnknownItineraire();
   
   public long id;
   public String voie;
   public Orientation orientation;
   public int denivele;
   public String difficulteSki;
   public String description;
   public Type type;
   public String difficulteMontee;
   public String materiel;
   public int exposition;
   public int pente;
   public int dureeJour;
   public long topoId;
   private Boolean isVariante;

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
      dest.writeParcelable(orientation, flags);
      dest.writeInt(denivele);
      dest.writeString(difficulteSki);
      dest.writeString(difficulteMontee);
      dest.writeString(description);
      dest.writeString(materiel);
      dest.writeInt(exposition);
      dest.writeInt(pente);
      dest.writeInt(dureeJour);
      dest.writeParcelable(type, flags);
      dest.writeLong(topoId);
      dest.writeByte((byte) (isVariante ? 1 : 0));
   }

   private void readFromParcel(Parcel in) {
      id = in.readLong();
      voie = in.readString();
      orientation = in.readParcelable(Orientation.class.getClassLoader());
      denivele = in.readInt();
      difficulteSki = in.readString();
      difficulteMontee = in.readString();
      description = in.readString();
      materiel = in.readString();
      exposition = in.readInt();
      pente = in.readInt();
      dureeJour = in.readInt();
      type = in.readParcelable(Type.class.getClassLoader());
      topoId = in.readInt();
      isVariante = in.readByte() == 1;
   }
   
   public enum Type implements Parcelable {
      ALLER_RETOUR("Aller/Retour"), BOUCLE("Boucle"), TRAVERSEE("Traversée"), INCONNU("Non renseigné");
      
      private final String value;

      private Type(String value) {
         this.value = value;
      }
      
      public String value() {
         return value;
      }
      
      public static Type fromValue(String value) {
         for (Type type : Type.values()) {
            if (type.value().equalsIgnoreCase(value)) {
               return type;
            }
         }
         return INCONNU;
      }
      
      @Override
      public int describeContents() {
          return 0;
      }

      @Override
      public void writeToParcel(final Parcel dest, final int flags) {
          dest.writeInt(ordinal());
      }

      public static final Creator<Type> CREATOR = new Creator<Type>() {
          @Override
          public Type createFromParcel(final Parcel source) {
              return Type.values()[source.readInt()];
          }

          @Override
          public Type[] newArray(final int size) {
              return new Type[size];
          }
      };
   }
   
   public enum Orientation implements Parcelable {
      N("Nord"), NE("Nord-Est"), E("Est"), SE("Sud-Est"), S("Sud"), SW("Sud-Ouest"), W("Ouest"), 
      NW("Nord-Ouest"), T("Toutes"), INCONNUE("Non renseignée");
      
      private final String value;

      private Orientation(String value) {
         this.value = value;
      }
      
      public String value() {
         return value;
      }
      
      public static Orientation fromValue(String value) {
         for (Orientation orientation : Orientation.values()) {
            if (orientation.value().equalsIgnoreCase(value)) {
               return orientation;
            }
         }
         return INCONNUE;
      }
      
      @Override
      public int describeContents() {
          return 0;
      }

      @Override
      public void writeToParcel(final Parcel dest, final int flags) {
          dest.writeInt(ordinal());
      }

      public static final Creator<Orientation> CREATOR = new Creator<Orientation>() {
          @Override
          public Orientation createFromParcel(final Parcel source) {
              return Orientation.values()[source.readInt()];
          }

          @Override
          public Orientation[] newArray(final int size) {
              return new Orientation[size];
          }
      };
   }
}
