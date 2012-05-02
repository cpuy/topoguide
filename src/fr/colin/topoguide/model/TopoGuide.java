package fr.colin.topoguide.model;

import static fr.colin.topoguide.model.Depart.UNKNOWN_DEPART;
import static fr.colin.topoguide.model.Itineraire.UNKNOWN_ITINERAIRE;
import static fr.colin.topoguide.model.Sommet.UNKNOWN_SOMMET;
import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import android.os.Parcel;
import android.os.Parcelable;
import fr.colin.topoguide.model.unknown.UnknownTopoGuide;

public class TopoGuide implements Parcelable {
   
   public static final TopoGuide UNKNOWN_TOPOGUIDE = new UnknownTopoGuide();
   
   public long id;
   public String nom;
   public long numero;
   public String remarques;
   
   public Sommet sommet = UNKNOWN_SOMMET;
   public Depart depart = UNKNOWN_DEPART;
   public Itineraire itineraire = UNKNOWN_ITINERAIRE;
   public List<Itineraire> variantes = new ArrayList<Itineraire>();
   
   public List<String> imageUrls;

   public TopoGuide() {

   }
   
   public boolean isUnknown() {
      return false;
   }

   /**
    * Parcelable
    */
   private TopoGuide(Parcel in) {
      readFromParcel(in);
   }

   public static final Parcelable.Creator<TopoGuide> CREATOR = new Parcelable.Creator<TopoGuide>() {
      public TopoGuide createFromParcel(Parcel in) {
         return new TopoGuide(in);
      }

      public TopoGuide[] newArray(int size) {
         return new TopoGuide[size];
      }
   };

   public int describeContents() {
      return 0;
   }

   public void writeToParcel(Parcel dest, int flags) {
      dest.writeLong(id);
      dest.writeString(nom);
      dest.writeString(remarques);
      dest.writeLong(numero);
      dest.writeParcelable(sommet, flags);
      dest.writeParcelable(depart, flags);
      dest.writeParcelable(itineraire, flags);
      dest.writeStringList(imageUrls);
      dest.writeTypedList(variantes);
   }

   private void readFromParcel(Parcel in) {
      id = in.readLong();
      nom = in.readString();
      remarques = in.readString();
      numero = in.readLong();
      sommet = in.readParcelable(Sommet.class.getClassLoader());
      depart = in.readParcelable(Depart.class.getClassLoader());
      itineraire = in.readParcelable(Itineraire.class.getClassLoader());
      
      if (imageUrls == null) {
         imageUrls = new ArrayList<String>();
      }
      in.readStringList(imageUrls);
      
      if (variantes == null) {
         variantes = new ArrayList<Itineraire>();
      }
      in.readTypedList(variantes, Itineraire.CREATOR);
   }

   @Override
   public boolean equals(final Object obj) {
      if (obj instanceof TopoGuide) {
         final TopoGuide other = (TopoGuide) obj;
         return new EqualsBuilder()
            .append(id, other.id)
            .append(nom, other.nom)
            .append(numero, other.numero)
            .append(remarques, other.remarques)
            .append(sommet, other.sommet)
            .append(depart, other.depart)
            .append(itineraire, other.itineraire)
            .append(variantes, other.variantes)
            .isEquals();
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return new HashCodeBuilder(17, 37)
         .append(id)
         .append(nom)
         .append(numero)
         .append(remarques)
         .append(sommet)
         .append(depart)
         .append(itineraire)
         .append(variantes)
         .toHashCode();
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this, SHORT_PREFIX_STYLE)
         .append("id", id)
         .append("nom", nom)
         .append("numero", numero)
         .append("remarques", remarques)
         .append("sommet", sommet)
         .append("depart", depart)
         .append("itineraire", itineraire)
         .append("variantes", variantes)
         .toString();
   }
}
