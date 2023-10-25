package ucr.ac.cr.boxb.ui.home;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ucr.ac.cr.boxb.R;
import ucr.ac.cr.boxb.model.Client;

public class ClientAdapter extends BaseAdapter {

    Context context;

    ArrayList<Client> listClients;

    public ClientAdapter(Context context, ArrayList<Client> listClients) {
        this.context = context;
        this.listClients = listClients;
    }

    @Override
    public int getCount() {
        return listClients.size();
    }

    @Override
    public Object getItem(int position) {
        return listClients.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if(view == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.client_adapter, null);
        }

        TextView txt_cedula_adapter = view.findViewById(R.id.txt_cedula_adapter);
        TextView txt_name_adapter = view.findViewById(R.id.txt_name_adapter);

        txt_name_adapter.setText(listClients.get(position).getName());
        txt_cedula_adapter.setText(listClients.get(position).getDocumentID());

//        Bitmap bitmap = BitmapFactory.decodeFile(Uri.decode(listClients.get(i).getFoto()));
//        imgU.setImageBitmap(bitmap);

        return view;
    }
}
