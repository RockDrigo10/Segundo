package com.example.segundoauqui.rocksaucechallenge.view.mainactivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.segundoauqui.rocksaucechallenge.R;
import com.example.segundoauqui.rocksaucechallenge.model.Child;

import java.util.ArrayList;

/**
 * Created by segundoauqui on 11/3/17.
 */

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {



    private static final String TAG = "RetrofitAdapter";


    ArrayList<Child> categoriesList = new ArrayList<>();
    Context context;

    public CategoriesAdapter(ArrayList<Child> categoriesList) {
        this.categoriesList = categoriesList;

    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvText1;
        TextView tvText2;
        TextView tvText3;
        ImageView ivImage;
        ImageView ivSmall;

        public ViewHolder(View itemView) {
            super(itemView);
            ivSmall = (ImageView) itemView.findViewById(R.id.ivSmall);
            ivImage = (ImageView) itemView.findViewById(R.id.ivImage);
            tvText1 = (TextView) itemView.findViewById(R.id.tvText1);
            tvText2 = (TextView) itemView.findViewById(R.id.tvText2);
            tvText3 = (TextView) itemView.findViewById(R.id.tvText3);
        }
    }


    @Override
    public CategoriesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        context = parent.getContext();

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_helper, parent, false);


        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(CategoriesAdapter.ViewHolder holder, int position) {

        final Child retro = categoriesList.get(position);

        holder.tvText1.setText(retro.getData().getAuthor());
        holder.tvText2.setText(retro.getData().getDomain());
        holder.tvText3.setText(retro.getData().getId());

        Glide.with(holder.itemView.getContext()).load(retro.getData().getThumbnail()).into(holder.ivImage);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View view) {
                new AlertDialog.Builder(view.getContext())
                        .setTitle("Select One From The Options")
                        .setPositiveButton("Show Full Image", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                final Dialog dialogCustom = new Dialog(view.getContext());
                                dialogCustom.setContentView(R.layout.smallpix);
                                ImageView largePix = (ImageView) dialogCustom.findViewById(R.id.ivSmall);
                                Glide.with(view.getContext()).load(retro.getData().getThumbnail()).into(largePix);
                                dialogCustom.show();
                                Toast.makeText(context, "Showing Small Image", Toast.LENGTH_SHORT).show();

                            }
                        }).show();
                return false;
            }
        });


    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: " + categoriesList);
        return categoriesList.size();
    }


}
