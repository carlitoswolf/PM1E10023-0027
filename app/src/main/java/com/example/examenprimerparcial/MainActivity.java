package com.example.examenprimerparcial;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;


import com.example.examenprimerparcial.config.Models.Contactos;
import com.example.examenprimerparcial.config.SQLiteConexion;
import com.example.examenprimerparcial.config.Settings;

import java.util.ArrayList;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    SQLiteConexion conexion;

    Button btnShareContacts, btnAddContacts, btnDeleteContacts, btnUpdateContacts, btnSeeImage;
    ListView ListContacts;
    ArrayList<Contactos> List;
    ArrayList<String> ArrayContacts;
     static int pasar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAddContacts =  (Button) findViewById(R.id.btnAdd);

        conexion = new SQLiteConexion(this, Settings.NameDatabase, null, 1);
        ListContacts = (ListView) findViewById(R.id.listContacts);

        ObtenerTable();

        ArrayAdapter adp = new ArrayAdapter(this, android.R.layout.simple_list_item_1, ArrayContacts);
        ListContacts.setAdapter(adp);

        btnAddContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateActivity.class);
                startActivity(intent);
            }
        });


        ListContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                try {
                    onBackPressed(List.get(i).getTelefono());
//                    Otro metodo de llamada
//                    Intent intent = new Intent(Intent.ACTION_DIAL);
//                    String phoneNumber =  "" + List.get(i).getTelefono();
//                    intent.setData(Uri.parse("tel:" + phoneNumber));
//                    startActivity(intent);
                }
                catch (Exception ex){
                    ex.toString();
                }

            }
        });
    }

    public void onBackPressed(int i) {
        AlertDialog.Builder myBuild = new AlertDialog.Builder(this);
        myBuild.setTitle("Accion");
        myBuild.setMessage("Desea realizar la llamada?");
        pasar = i;
        myBuild.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent(getApplicationContext(), CallActivity.class);
//                intent.putExtra("dato",pasar.getText().toString());
                startActivity(intent);
            }
        });
        myBuild.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = myBuild.create();
        dialog.show();
    }


    private void ObtenerTable() {
        SQLiteDatabase db = conexion.getReadableDatabase();
        Contactos Contacts = null;
        List = new ArrayList<Contactos>();
        Cursor cursor = db.rawQuery(Settings.SelectTableContacts, null);

        while (cursor.moveToNext()){
            Contacts = new Contactos();
            Contacts.setId(cursor.getInt(0));
            Contacts.setNombre(cursor.getString(1));
            Contacts.setPais(cursor.getString(2));
            Contacts.setTelefono(cursor.getInt(3));
            Contacts.setNota(cursor.getString(4));
            Contacts.setFoto(cursor.getString(5));

            List.add(Contacts);
        }
        cursor.close();
        fillList();
    }

    private void fillList() {

        ArrayContacts = new ArrayList<String>();

        for (int i=0; i < List.size(); i++){
            ArrayContacts.add("("+List.get(i).getId()+") " + List.get(i).getNombre() + "\n"
                    +"Tel "+List.get(i).getTelefono()+"\n\n");
        }
    }


}