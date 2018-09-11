package misiont.persistenciaenandroid.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import misiont.persistenciaenandroid.Adapters.DonorRecyclerAdapter;
import misiont.persistenciaenandroid.DAO.DAODonor;
import misiont.persistenciaenandroid.DAO.DAOUser;
import misiont.persistenciaenandroid.DialogFragments.ChangePasswordDialogFragment;
import misiont.persistenciaenandroid.DialogFragments.EditDonorDialogFragment;
import misiont.persistenciaenandroid.DialogFragments.NewDonorDialogFragment;
import misiont.persistenciaenandroid.Entidades.Donor;
import misiont.persistenciaenandroid.Entidades.User;
import misiont.persistenciaenandroid.R;
import misiont.persistenciaenandroid.UpdateAdapter;

import static android.R.attr.id;


public class DonorsActivity extends AppCompatActivity implements DonorRecyclerAdapter.DonorClickListener, UpdateAdapter {

    public static final String USER_ID = "user_id";
    User usuario;
    FloatingActionButton fabAddDonor;
    EditText editTextDonorId;
    ImageButton buttonBuscar;
    ImageButton buttonCancelar;
    ArrayList<Donor> donors;
    RecyclerView recyclerView;
    DonorRecyclerAdapter adapter;
    RecyclerView.LayoutManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donors);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        usuario = DAOUser.getInstance(DonorsActivity.this).buscar(getIntent().getExtras().getInt(USER_ID));

        editTextDonorId = (EditText)findViewById(R.id.editTextUserId);
        fabAddDonor = (FloatingActionButton)findViewById(R.id.fabAddDonor);
        buttonBuscar = (ImageButton)findViewById(R.id.buttonBuscar);
        buttonCancelar = (ImageButton)findViewById(R.id.buttonEliminar);
        fabAddDonor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialogForNewDonor();
            }
        });
        recyclerView = (RecyclerView)findViewById(R.id.recyclerViewDonors);

        donors = DAODonor.getInstance(DonorsActivity.this).listar();
        adapter = new DonorRecyclerAdapter(DonorsActivity.this,donors,this);

        manager = new LinearLayoutManager(DonorsActivity.this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);

        buttonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextDonorId.setText("");
                updateAdapter();
            }
        });
        buttonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchIdLike();
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menuButtonCambiarContra:
                showAlertForModifyPassword();
                break;
            case R.id.menuButtonEliminarCuenta:
                showAlertForDeleteAccount();
                break;
            case R.id.menuButtonCerrarSesion:
                onBackPressed();
                break;
        }
        return true;
    }

    private void showAlertDialogForNewDonor() {
        NewDonorDialogFragment.getInstance(this).show(getFragmentManager(),"newDonor");


    }

    private void showAlertForDeletingDonor(final Donor donor){
        AlertDialog.Builder builder = new AlertDialog.Builder(DonorsActivity.this);
        builder.setTitle(R.string.confirmacion)
                .setMessage(R.string.pregunta_borrar)
                .setPositiveButton(getString(R.string.borrar), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DAODonor.getInstance(DonorsActivity.this).eliminar(donor.getId());
                        updateAdapter();
                    }
                })
                .setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();


    }

    private void showAlertForEditingDonor(Donor donor){
        EditDonorDialogFragment.getInstance(donor,this).show(getFragmentManager(),"editDonor");
    }

    private void showAlertForModifyPassword(){
        ChangePasswordDialogFragment.getInstance(usuario).show(getFragmentManager(),"changePassword");
    }
    private void showAlertForDeleteAccount(){
        AlertDialog.Builder builder = new AlertDialog.Builder(DonorsActivity.this);
        builder.setTitle(R.string.eliminar_cuenta)
                .setMessage(R.string.confir_eliminar_cuenta)
                .setPositiveButton(R.string.eliminar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DAOUser.getInstance(getApplicationContext()).eliminar(usuario.getId());
                        Toast.makeText(getApplicationContext(),getString(R.string.cuenta_eliminada),Toast.LENGTH_LONG).show();
                        onBackPressed();

                    }
                })
                .setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
        ;
        builder.show();
    }


    public void updateAdapter(){
        donors = DAODonor.getInstance(DonorsActivity.this).listar();
        adapter = new DonorRecyclerAdapter(DonorsActivity.this,donors,this);
        recyclerView.setAdapter(adapter);

    }

    public void searchIdLike(){

        if(!editTextDonorId.getText().toString().equals("")){
            int id = Integer.parseInt(editTextDonorId.getText().toString());
            donors = DAODonor.getInstance(DonorsActivity.this).buscarPatron(id);
            adapter = new DonorRecyclerAdapter(DonorsActivity.this,donors,this);
            recyclerView.setAdapter(adapter);
        }
        else{
            Toast.makeText(DonorsActivity.this,R.string.escribir_id,Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void TrashClickListener(Donor donor) {
        showAlertForDeletingDonor(donor);
    }

    @Override
    public void EditClickListener(Donor donor) {
        showAlertForEditingDonor(donor);
    }



}
