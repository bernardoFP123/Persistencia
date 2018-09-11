package misiont.persistenciaenandroid.DialogFragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import misiont.persistenciaenandroid.Activities.DonorsActivity;
import misiont.persistenciaenandroid.DAO.DAODonor;
import misiont.persistenciaenandroid.DAO.DAOUser;
import misiont.persistenciaenandroid.Entidades.Donor;
import misiont.persistenciaenandroid.Entidades.User;
import misiont.persistenciaenandroid.R;
import misiont.persistenciaenandroid.UpdateAdapter;

/**
 * Created by Bernardo_NoAdmin on 25/02/2018.
 */

public class EditDonorDialogFragment extends DialogFragment {

    private static EditDonorDialogFragment instance;
    private static Donor donor;
    private static UpdateAdapter updateAdapter;
    EditText editTextId;
    EditText editTextNombre;
    EditText editTextApellido;
    EditText editTextEdad ;
    EditText editTextPeso;
    EditText editTextEstatura;
    Spinner spinnerRh;
    Spinner spinnerTipoDeSangre;


    public static EditDonorDialogFragment getInstance(Donor donor1, UpdateAdapter updateAdapter1){
        if(instance == null){
            instance = new EditDonorDialogFragment();
        }
        donor = donor1;
        updateAdapter = updateAdapter1;

        return instance;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.alert_dialog_new_donor,null);
        editTextId = view.findViewById(R.id.editTextNoIdentificacion);
        editTextNombre = view.findViewById(R.id.editTextNombre);
        editTextApellido = view.findViewById(R.id.editTextApellido);
        editTextEdad = view.findViewById(R.id.editTextEdad);
        editTextPeso = view.findViewById(R.id.editTextPeso);
        editTextEstatura = view.findViewById(R.id.editTextAltura);
        spinnerRh = view.findViewById(R.id.spinnerRh);
        spinnerTipoDeSangre = view.findViewById(R.id.spinnerTipoDeSangre);

        editTextId.setEnabled(false);
        editTextId.setClickable(false);
        editTextId.setText(Integer.toString(donor.getId()));

        editTextNombre.setText(donor.getNombre());
        editTextApellido.setText(donor.getApellido());
        editTextEdad.setText(donor.getEdad());
        editTextPeso.setText(donor.getPeso());
        editTextEstatura.setText(donor.getEstatura());

        ArrayAdapter rhAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.rh,android.R.layout.simple_spinner_item);
        spinnerRh.setAdapter(rhAdapter);
        String[] arrayRh = getResources().getStringArray(R.array.rh);
        if(donor.getRh().equals(arrayRh[0])){
            spinnerRh.setSelection(0);
        }
        else if(donor.getRh().equals(arrayRh[1])){
            spinnerRh.setSelection(1);
        }
        else if(donor.getRh().equals(arrayRh[2])){
            spinnerRh.setSelection(2);
        }
        else{
            spinnerRh.setSelection(3);
        }
        ArrayAdapter sangreAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.tipos_de_sangre,android.R.layout.simple_spinner_item);
        spinnerTipoDeSangre.setAdapter(sangreAdapter);
        String[] arraySangre = getResources().getStringArray(R.array.tipos_de_sangre);
        if(donor.getTipoDeSangre().equals(arraySangre[0])) {
            spinnerTipoDeSangre.setSelection(0);
        }
        else{
            spinnerTipoDeSangre.setSelection(1);
        }

        builder.setTitle(R.string.editar_usuario)
                .setView(view)
                .setPositiveButton(R.string.actualizar_donador, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                    }
                })
                .setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                ;
        return builder.create();
    }
    @Override
    public void onResume()
    {
        super.onResume();
        final AlertDialog d = (AlertDialog)getDialog();
        if(d != null)
        {

            Button positiveButton = (Button) d.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Boolean wantToCloseDialog = false;

                    int id = Integer.parseInt(editTextId.getText().toString());
                    String nombre = editTextNombre.getText().toString();
                    String apellido = editTextApellido.getText().toString();
                    String edad = editTextEdad.getText().toString();
                    String peso = editTextPeso.getText().toString();
                    String altura = editTextEstatura.getText().toString();
                    String tipoDeSangre = spinnerTipoDeSangre.getSelectedItem().toString();
                    String rh = spinnerRh.getSelectedItem().toString();

                    if(nombre.equals("") || apellido.equals("") || edad.equals("") || peso.equals("") || altura.equals("") ||
                            tipoDeSangre.equals(getResources().getStringArray(R.array.tipos_de_sangre)[0])
                            || rh.equals(getResources().getStringArray(R.array.rh)[0])){
                        Toast.makeText(getActivity(),R.string.unfilled_field,Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Donor don = new Donor();
                    don.setId(id);
                    don.setNombre(nombre);
                    don.setApellido(apellido);
                    don.setEdad(edad);
                    don.setTipoDeSangre(tipoDeSangre);
                    don.setRh(rh);
                    don.setPeso(peso);
                    don.setEstatura(altura);



                    DAODonor.getInstance(getActivity()).actualizar(don);
                    updateAdapter.updateAdapter();
                    Toast.makeText(getActivity(),R.string.donante_actualizado,Toast.LENGTH_SHORT).show();
                    wantToCloseDialog = true;

                    if(wantToCloseDialog)
                        d.dismiss();


                }
            });
        }
    }
}
