package misiont.persistenciaenandroid.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import misiont.persistenciaenandroid.DAO.DAOUser;
import misiont.persistenciaenandroid.DialogFragments.NewUserDialogFragment;
import misiont.persistenciaenandroid.Entidades.User;
import misiont.persistenciaenandroid.R;

import static android.R.attr.button;
import static android.R.id.edit;

public class MainActivity extends AppCompatActivity {

    EditText editTextuserName;
    EditText editTextContrasena;
    Button buttonLogIn;
    Button buttonNewUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextuserName = (EditText)findViewById(R.id.editTextUserName);
        editTextContrasena = (EditText)findViewById(R.id.editTextContrasena);
        buttonLogIn = (Button)findViewById(R.id.buttonIniciarSesion);
        buttonNewUser = (Button)findViewById(R.id.buttonNuevoUsuario);

        buttonNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialogForNewUser();
            }
        });

        buttonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = editTextuserName.getText().toString();
                String contrasena = editTextContrasena.getText().toString();

                User u = DAOUser.getInstance(MainActivity.this).buscar(nombre);
                if(u != null){
                    if(contrasena.equals(u.getContraseña())){
                        Intent intent = new Intent(MainActivity.this,DonorsActivity.class);
                        intent.putExtra(DonorsActivity.USER_ID,u.getId());
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(MainActivity.this,R.string.contrasena_invalida,Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(MainActivity.this,R.string.usuario_inexistente,Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void showAlertDialogForNewUser() {
        NewUserDialogFragment.getInstance().show(getFragmentManager(),"newUser");


    }
}
