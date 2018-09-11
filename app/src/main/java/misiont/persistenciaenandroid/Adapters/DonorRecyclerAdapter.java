package misiont.persistenciaenandroid.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import misiont.persistenciaenandroid.Entidades.Donor;
import misiont.persistenciaenandroid.R;
import misiont.persistenciaenandroid.ViewHolder.DonorViewHolder;

/**
 * Created by Bernardo_NoAdmin on 25/02/2018.
 */

public class DonorRecyclerAdapter extends RecyclerView.Adapter<DonorViewHolder> {

    ArrayList<Donor> donors;
    Context context;
    DonorClickListener donorClickListener;

    public DonorRecyclerAdapter(Context context, ArrayList<Donor> donors, DonorClickListener donorClickListener){
        this.donors = donors;
        this.context = context;
        this.donorClickListener = donorClickListener;

    }

    @Override
    public DonorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_view_donor,parent,false);
        return new DonorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DonorViewHolder holder, int position) {
        holder.bind(donors.get(position),donorClickListener);
    }

    @Override
    public int getItemCount() {
        return donors.size();
    }

    public interface DonorClickListener{
        void TrashClickListener(Donor donor);
        void EditClickListener(Donor donor);
    }
}
