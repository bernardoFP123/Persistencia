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

import misiont.persistenciaenandroid.Activities.DonorsActivity;
import misiont.persistenciaenandroid.DAO.DAOUser;
import misiont.persistenciaenandroid.Entidades.User;
import misiont.persistenciaenandroid.R;

/**
 * Created by Bernardo_NoAdmin on 25/02/2018.
 */

public class ChangePasswordDialogFragment extends DialogFragment {



    private static ChangePasswordDialogFragment instance;
    private static User usuario;
    EditText editTextOldPass;
    EditText editTextNewPass;
    EditText editTextConfirNewPass;
    public static ChangePasswordDialogFragment getInstance(User usuario1){
        if(instance == null){
            instance = new ChangePasswordDialogFragment();
        }
        usuario = usuario1;
        return instance;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.alert_dialog_modify_password,null);
        editTextOldPass = view.findViewById(R.id.editTextOldPassword);
        editTextNewPass = view.findViewById(R.id.editTextNuevaContrasena);
        editTextConfirNewPass = view.findViewById(R.id.editTextConfirmarNuevaContrasena);

        builder.setTitle(R.string.modificar_password)
                .setView(view)
                .setPositiveButton(R.string.modificar, new DialogInterface.OnClickListener() {
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

                    String oldPass = editTextOldPass.getText().toString();
                    String newPass = editTextNewPass.getText().toString();
                    String confirNewPass = editTextConfirNewPass.getText().toString();
                    if(oldPass.equals("") || newPass.equals("") || confirNewPass.equals("")){
                        Toast.makeText(getActivity().getApplicationContext(),R.string.unfilled_field,Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(!newPass.equals(confirNewPass)){
                        Toast.makeText(getActivity().getApplicationContext(),R.string.nuevas_contrasenas_deben,Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(!oldPass.equals(usuario.getContraseña())){
                        Toast.makeText(getActivity().getApplicationContext(),R.string.contrasena_invalida,Toast.LENGTH_SHORT).show();
                    }
                    usuario.setContraseña(newPass);
                    DAOUser.getInstance(getActivity().getApplicationContext()).actualizar(usuario);

                    Toast.makeText(getActivity().getApplicationContext(),R.string.usuario_modificado,Toast.LENGTH_SHORT).show();
                    wantToCloseDialog = true;
                    if(wantToCloseDialog)
                        d.dismiss();


                }
            });
        }
    }
}
