package com.example.examenprimerparcial;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.examenprimerparcial.config.SQLiteConexion;
import com.example.examenprimerparcial.config.Settings;

public class CreateActivity extends AppCompatActivity {

    EditText nombres, telefono, nota;
    Spinner pais;

    Button btnsalvar, btncontacto, btnTakePhoto;

    String currentPhotoPath;

    String pattern = "^[A-Za-z\\s]+$";
    static final int peticion_captura_imagen = 101;
    static final int peticion_acceso_camara = 102;

    ImageView ObjectImage;

    String imageFileName;

    String SendData;
    final String[] getPais = new String[1];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        nombres = (EditText) findViewById(R.id.txtNombre);
        pais = (Spinner) findViewById(R.id.cmbPais);
        telefono = (EditText) findViewById(R.id.txtTelefono);
        nota = (EditText) findViewById(R.id.txtNota);

        ObjectImage = (ImageView) findViewById(R.id.ImageView);

        btnsalvar = (Button) findViewById(R.id.btnSalvarPais);
        btncontacto= (Button) findViewById(R.id.btnContacto);
        btnTakePhoto= (Button) findViewById(R.id.btnPhoto);

        btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permiss();
            }
        });

        pais.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                getPais[0] = parent.getItemAtPosition(position).toString();
                SendData =getPais[0];

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        btnsalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validarCampos()) {
                    // Realiza la acción requerida cuando los campos no están vacíos

                    AddCont(SendData, imageFileName);
                }


            }
        });


        btncontacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

    }

    private void permiss() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, peticion_acceso_camara);
        } else {
            dispatchTakePictureIntent();

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == peticion_acceso_camara )
        {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED )
            {
                dispatchTakePictureIntent();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Se necesita el permiso para acceder a la camara", Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == peticion_captura_imagen){

            try {
                File foto = new File(currentPhotoPath);
                ObjectImage.setImageURI(Uri.fromFile(foto));


            } catch (Exception e){
                e.toString();
            }


        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.examenprimerparcial.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, peticion_captura_imagen);
            }
        }
    }


    private void AddCont(String ReceiveData, String photo) {

        SQLiteConexion conexion = new SQLiteConexion(this, Settings.NameDatabase, null, 1);
        SQLiteDatabase db = conexion.getWritableDatabase();

        String SendPhoto = photo + ".jpg";

        Pattern regexPattern = Pattern.compile(pattern);
        String userInput = nombres.getText().toString();

        Matcher matcher = regexPattern.matcher(userInput);

        if (matcher.matches()) {
            // El texto ingresado cumple con la expresión regular
            ContentValues valores = new ContentValues();
            valores.put(Settings.nombres, userInput);
            valores.put(Settings.pais, ReceiveData);
            valores.put(Settings.telefono, telefono.getText().toString());
            valores.put(Settings.nota, nota.getText().toString());
            valores.put(Settings.foto, SendPhoto);

            Long result = db.insert(Settings.tableContacts, Settings.id, valores);
            Toast.makeText(getApplicationContext(), "Registro Ingresado : " + result.toString(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "ERROR" , Toast.LENGTH_LONG).show();
        }




        db.close();

        CleanScreen();
    }

    private boolean validarCampos() {
        String nombre = nombres.getText().toString().trim();
        String notas = nota.getText().toString().trim();
        String numero = telefono.getText().toString().trim();



        if (nombre.isEmpty()) {
            nombres.setError("Campo obligatorio");
            return false;
        }

        if (numero.isEmpty()) {
            telefono.setError("Campo obligatorio");
            return false;
        }
        if (notas.isEmpty()) {
            nota.setError("Campo obligatorio");
            return false;
        }

        return true;
    }

    private void CleanScreen() {
        nombres.setText("");
        telefono.setText("");
        nota.setText("");
    }

}