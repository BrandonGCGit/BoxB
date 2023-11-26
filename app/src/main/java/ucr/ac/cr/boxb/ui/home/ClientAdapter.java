package ucr.ac.cr.boxb.ui.home;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Random;

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


        LinearLayout lL_background_adapter = view.findViewById(R.id.lL_background_adapter);
        TextView txt_cedula_adapter = view.findViewById(R.id.txt_cedula_adapter);
        TextView txt_name_adapter = view.findViewById(R.id.txt_name_adapter);

//        Random random = new Random();
//        int randomNumber = random.nextInt(3) + 1;

        int modulo = position % 3;

        int colorRes;
        if (modulo == 0)
        {
            colorRes = R.color.soft_green;
        }
        else if (modulo == 1)
        {
            colorRes = R.color.soft_white;
        } else {
            colorRes = R.color.green;
        }


        int backgroundColor = ContextCompat.getColor(context, colorRes);
        ColorDrawable colorDrawable = new ColorDrawable(backgroundColor);

        lL_background_adapter.setBackground(colorDrawable);
        txt_name_adapter.setText(listClients.get(position).getName());
        txt_cedula_adapter.setText(listClients.get(position).getDocumentID());

//        Bitmap bitmap = BitmapFactory.decodeFile(Uri.decode(listClients.get(i).getFoto()));
//        imgU.setImageBitmap(bitmap);

        return view;
    }
}
