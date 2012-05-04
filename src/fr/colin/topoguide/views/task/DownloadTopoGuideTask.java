package fr.colin.topoguide.views.task;

import static fr.colin.topoguide.model.TopoGuide.UNKNOWN_TOPOGUIDE;
import fr.colin.topoguide.model.TopoGuide;
import fr.colin.topoguide.repository.RemoteTopoGuideRepository;
import fr.colin.topoguide.repository.RepositoryException;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class DownloadTopoGuideTask extends AsyncTask<Long, ProgressDialog, TopoGuide> {

   private ProgressDialog dialog;
   private final Context context;
   
   public DownloadTopoGuideTask(Context context) {
      this.context = context;
      dialog = new ProgressDialog(context);
   }
   
   @Override
   protected TopoGuide doInBackground(Long... param) {
      if (param != null && param.length > 0) {
         try {
            TopoGuide topo = RemoteTopoGuideRepository.skitour().fetchTopoById(param[0]);
            return topo;
         } catch (RepositoryException e) {
            onCancelled();
         }
      }
      return UNKNOWN_TOPOGUIDE;
   }

   @Override
   protected void onPreExecute() {
      dialog.show();
      super.onPreExecute();
   }
   
   @Override
   protected void onPostExecute(TopoGuide result) {
      super.onPostExecute(result);
      dialog.dismiss();
   }
   
   @Override
   protected void onCancelled() {
      super.onCancelled();
      Toast.makeText(context, "ERRRRRRREUR", Toast.LENGTH_LONG);
   }
}
