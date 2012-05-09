package fr.colin.topoguide.repository;

@SuppressWarnings("serial")
public class RepositoryException extends Exception {

   public RepositoryException(String detailMessage, Throwable throwable) {
      super(detailMessage, throwable);
   }
}
