package fr.colin.topoguide.model;

/**
 * TODO abstract et extends parcelable
 */
public interface Model {

   public void setId(long id);
   public Model clone();
}
