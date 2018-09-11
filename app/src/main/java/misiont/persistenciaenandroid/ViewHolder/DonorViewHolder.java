package misiont.persistenciaenandroid.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import misiont.persistenciaenandroid.Adapters.DonorRecyclerAdapter;
import misiont.persistenciaenandroid.Entidades.Donor;
import misiont.persistenciaenandroid.R;

/**
 * Created by Bernardo_NoAdmin on 25/02/2018.
 */

public class DonorViewHolder extends RecyclerView .ViewHolder{

    TextView textViewNombre;
    TextView textViewId;
    TextView textViewEdad;
    TextView textViewSangre;
    TextView textViewPesoEstatura;
    ImageButton imageButtonTrash;
    ImageButton imageButtonEdit;

    DonorRecyclerAdapter.DonorClickListener donorClickListener;

    Donor donor;

    public DonorViewHolder(View itemView) {
        super(itemView);
        textViewNombre = itemView.findViewById(R.id.textViewName);
        textViewId = itemView.findViewById(R.id.textViewId);
        textViewEdad = itemView.findViewById(R.id.textViewEdad);
        textViewSangre = itemView.findViewById(R.id.textViewSangre);
        textViewPesoEstatura = itemView.findViewById(R.id.textViewPesoEstatura);
        imageButtonEdit = itemView.findViewById(R.id.imageButtonEdit);
        imageButtonTrash = itemView.findViewById(R.id.imageButtonTrashCan);

        imageButtonTrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                donorClickListener.TrashClickListener(donor);
            }
        });
        imageButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                donorClickListener.EditClickListener(donor);
            }
        });

    }

    public void bind(Donor donor, DonorRecyclerAdapter.DonorClickListener donorClickListener){
        this.donor = donor;
        this.donorClickListener = donorClickListener;

        textViewNombre.setText(donor.getNombre());
        textViewId.setText("id: "+donor.getId());
        textViewEdad.setText("Edad: "+donor.getEdad() + " " + donor.getApellido());
        textViewSangre.setText("Sangre tipo " + donor.getTipoDeSangre() + " " + donor.getRh());
        textViewPesoEstatura.setText("Peso: " + donor.getPeso() + " kg " + "Estatura: " + donor.getEstatura() + "cm");
    }

}
