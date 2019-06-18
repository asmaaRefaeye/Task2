package com.example.creativejsontask.adapter;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.creativejsontask.R;
import com.example.creativejsontask.model.Data;
import com.example.creativejsontask.model.ListItem;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.viewHolder> {

    private ArrayList<Data> data;
    ArrayList<ListItem> cache_items;
    private Context context;
    public String data_type;

    public DataAdapter(Context context, ArrayList<Data> data, ArrayList<ListItem> cache_items) {
        this.data = data;
        this.context=context;
        this.cache_items=cache_items;
//        this.data_type=data_type;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewtype) {
        View view = LayoutInflater.from(context).inflate(R.layout.itemview, parent, false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder viewHolder, int i)
    {
          if(data_type!=null && data_type.equals("online"))
          {
              viewHolder.name.setText(data.get(i).getName());
              viewHolder.description.setText(data.get(i).getDescription());
              viewHolder.login.setText(data.get(i).getOwner().getLogin());

              if (data.get(i).isFork())
                  viewHolder.cardView_bg.setCardBackgroundColor(Color.GREEN);
              else
                  viewHolder.cardView_bg.setCardBackgroundColor(Color.WHITE);
          }
//          else if (data_type!=null && data_type.equals("cache") )
//          {
//              viewHolder.name.setText(cache_items.get(i).getRepoName());
//              viewHolder.description.setText(cache_items.get(i).getDescription());
//              viewHolder.login.setText(cache_items.get(i).getUserName());
//
//              if (cache_items.get(i).isFork())
//                  viewHolder.cardView_bg.setCardBackgroundColor(Color.GREEN);
//              else
//                  viewHolder.cardView_bg.setCardBackgroundColor(Color.WHITE);
//          }
    }

    @Override
    public int getItemCount() {

        return data.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        TextView name, description, login;
        CardView cardView_bg;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameTextview);
            description = itemView.findViewById(R.id.descriptionTextview);
            login = itemView.findViewById(R.id.usernameTextview);
            cardView_bg= itemView.findViewById(R.id.cardView_bg);


            itemView.setOnLongClickListener(new View.OnLongClickListener()
            {
                @Override
                public boolean onLongClick(View view) {

                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                    alert.setMessage("You need to go : ");
                    alert.setNegativeButton("repository url", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i)
                        {
                            if(data_type!=null && data_type.equals("online"))
                                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(data.get(getAdapterPosition()).getHtmlUrl())));
//                            else if(data_type!=null && data_type.equals("cache"))
//                                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(cache_items.get(getAdapterPosition()).getRepo_url())));
                        }
                    });
                    alert.setPositiveButton("owner url", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i)
                        {
                            if(data_type!=null && data_type.equals("online"))
                                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(data.get(getAdapterPosition()).getOwner().getHtmlUrl())));
//                            else if(data_type!=null && data_type.equals("cache"))
//                                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(cache_items.get(getAdapterPosition()).getOwner_url())));
                        }
                    });
                    alert.show();
                    return false;
                }
            });
        }
    }
}
