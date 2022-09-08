package com.example.agendaciontelefonos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class Principal extends AppCompatActivity {
    private static String idUsuario;
    private static String nombreUsuario;
    private static String telefonoUsuario;
    TableLayout tl;
    TableRow newTR;
    ArrayList arrProductos = new ArrayList();
    TextView edtNomsUser,edtTelefono;
    static Principal instance = null;
    Button btnAgregaef,btnVerReferencia,btnCallTit,btnActualiza;
    ProgressBar prBar;
    alertaMensaje alert = new alertaMensaje();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        tl = findViewById(R.id.tblProductosRef);
        edtNomsUser = findViewById(R.id.edtNombreUser);
        edtTelefono = findViewById(R.id.edtTelefonoUser);
        btnAgregaef = findViewById(R.id.btnAgregarReferencia);
        btnVerReferencia = findViewById(R.id.btnVerReferencia);
        btnActualiza = findViewById(R.id.btnActualizaPrinc);
        arrProductos = MainActivity.getInstance().getArrayProd();
        btnCallTit = findViewById(R.id.btnCallTit);
        prBar = (ProgressBar) findViewById(R.id.progressBarPrin);
        try {
            agregaProudctos(arrProductos);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        btnAgregaef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prBar.setVisibility(View.VISIBLE);
                Intent intent = new Intent(getApplicationContext(),registro.class);
                prBar.setVisibility(View.GONE);
                startActivity(intent);
            }
        });

        btnVerReferencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prBar.setVisibility(View.VISIBLE);
                Intent intent = new Intent(getApplicationContext(),verReferencias.class);
                prBar.setVisibility(View.GONE);
                startActivity(intent);
            }
        });

        btnCallTit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!edtTelefono.getText().toString().equals("")){
                    Intent i = new Intent(Intent.ACTION_DIAL);
                    i.setData(Uri.parse("tel:"+edtTelefono.getText().toString().trim()));
                    startActivity(i);
                }else{
                    alert.mensajes(Principal.this,"No haz seleccionado un contacto");
                }
            }
        });

        btnActualiza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),actualizaTitular.class);
                startActivity(intent);
            }
        });
    }

    public static Principal getInstance() {
        return instance;
    }

    public void agregaProudctos(ArrayList arrProd) throws JSONException {
        JSONArray arrRReg = new JSONArray();
        for(int i=0;i<arrProd.size();i++){
            arrRReg = (JSONArray) arrProd.get(i);
            System.err.println(arrRReg.get(1).toString());
            newTR = new TableRow(this);
            TextView newTxtName = new TextView(this);
            TextView telefono = new TextView(this);
            TextView idusuario = new TextView(this);
            //id usuario
            idusuario.setText(arrRReg.get(0).toString());
            idusuario.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            idusuario.setBackgroundColor(i%2==0? Color.argb(93,0,141,205):Color.argb(78,3,169,244));
            idusuario.setTextColor(Color.BLACK);
            idusuario.setWidth(100);
            idusuario.setTextSize(20);
            idusuario.setId(i);
            //nombre usuario
            newTxtName.setText(arrRReg.get(1).toString());
            newTxtName.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            newTxtName.setBackgroundColor(i%2==0? Color.argb(93,0,141,205):Color.argb(78,3,169,244));
            newTxtName.setTextColor(Color.BLACK);
            newTxtName.setWidth(600);
            newTxtName.setTextSize(20);
            newTxtName.setId(i);
            //telefono
            telefono.setText(arrRReg.get(2).toString());
            telefono.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            telefono.setBackgroundColor(i%2==0? Color.argb(93,0,141,205):Color.argb(78,3,169,244));
            telefono.setTextColor(Color.BLACK);
            telefono.setWidth(500);
            telefono.setTextSize(20);
            telefono.setId(i);

            newTR.addView(idusuario);
            newTR.addView(newTxtName);
            newTR.addView(telefono);

            newTR.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    TableRow t = (TableRow) v;
                    TextView firstTextViewIdUsuario = (TextView) t.getChildAt(0);
                    TextView firstTextViewNom = (TextView) t.getChildAt(1);
                    TextView firstTextViewTelf= (TextView) t.getChildAt(2);
                    idUsuario = firstTextViewIdUsuario.getText().toString();
                    nombreUsuario = firstTextViewNom.getText().toString();
                    telefonoUsuario = firstTextViewTelf.getText().toString();
                    edtNomsUser.setText(nombreUsuario);
                    edtTelefono.setText(telefonoUsuario);
                }
            });

            tl.addView(newTR);
        }
    }

    public static  String getIdUsuaio(){
        return idUsuario;
    }
    public static  String getNombreUsuarioPrin(){
        return nombreUsuario;
    }
    public static  String getTelefonoUsuarioPrin(){
        return telefonoUsuario;
    }
    public void ocultaVar(){prBar.setVisibility(View.GONE);}
}