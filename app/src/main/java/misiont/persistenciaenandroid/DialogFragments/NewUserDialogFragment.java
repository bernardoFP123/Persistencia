package misiont.persistenciaenandroid.DialogFragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import misiont.persistenciaenandroid.Activities.MainActivity;
import misiont.persistenciaenandroid.DAO.DAOUser;
import misiont.persistenciaenandroid.Entidades.User;
import misiont.persistenciaenandroid.R;

/**
 * Created by Bernardo_NoAdmin on 25/02/2018.
 */

public class NewUserDialogFragment extends DialogFragment {

    private static NewUserDialogFragment instance;

    EditText editTextNombreUsuarioAD;
    EditText editTextContrasenaAD;
    EditText editTextConfirmarContrasenaAD;

    public static NewUserDialogFragment getInstance(){
        if(instance == null){
            instance = new NewUserDialogFragment();
        }
        return instance;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.alert_dialog_new_user,null);
        editTextNombreUsuarioAD = view.findViewById(R.id.editTextUserName);
        editTextContrasenaAD = view.findViewById(R.id.editTextContrasena);
        editTextConfirmarContrasenaAD = view.findViewById(R.id.editTextConfirmarContrasena);

        builder.setTitle(R.string.nuevo_usuario)
                .setView(view)
                .setPositiveButton(R.string.registrar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
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
                    String nombre = editTextNombreUsuarioAD.getText().toString();
                    String contrasena = editTextContrasenaAD.getText().toString();
                    String confirContrasena = editTextConfirmarContrasenaAD.getText().toString();
                    if(nombre.equals("") || contrasena.equals("") || confirContrasena.equals("")){
                        Toast.makeText(getActivity(),R.string.unfilled_field,Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(!contrasena.equals(confirContrasena)){
                        Toast.makeText(getActivity(),R.string.contrasenas_deben,Toast.LENGTH_SHORT).show();
                        return;
                    }
                    User u = new User();
                    u.setNombre(nombre);
                    u.setContraseña(contrasena);
                    if(DAOUser.getInstance(getActivity()).validate(u)){
                        Toast.makeText(getActivity(),R.string.usuario_ocupado,Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else{
                        DAOUser.getInstance(getActivity()).insertar(u);
                        Toast.makeText(getActivity(),R.string.usuario_insertado,Toast.LENGTH_SHORT).show();
                        wantToCloseDialog = true;
                    }

                    if(wantToCloseDialog)
                        d.dismiss();


                }
            });
        }
    }
}
