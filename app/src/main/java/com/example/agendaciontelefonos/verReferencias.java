package com.example.agendaciontelefonos;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class verReferencias extends AppCompatActivity {
    String edtIdUsuarioReg;
    TableLayout tl;
    TableRow newTR;
    ArrayList arrProductos;
    alertaMensaje alert = new alertaMensaje();
    static verReferencias instance = null;
    private static AlertDialog.Builder aDialogBuilder;
    private static AlertDialog aDialog;
    EditText edtNomCont,edtTelfCont;
    Button btnCall;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_referencias);
        tl = findViewById(R.id.tblProductosRef);
        edtNomCont = findViewById(R.id.edtNombreContact);
        edtTelfCont = findViewById(R.id.edtTelefonoContact);
        edtIdUsuarioReg = Principal.getInstance().getIdUsuaio();
        btnCall = findViewById(R.id.btnCall);
        obtieneRegistro("https://mjdeveloperweb.000webhostapp.com/php/obtieneReferencias.php");

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!edtTelfCont.getText().toString().equals("")){
                    Intent i = new Intent(Intent.ACTION_DIAL);
                    i.setData(Uri.parse("tel:"+edtTelfCont.getText().toString().trim()));
                    startActivity(i);
                }else{
                    alert.mensajes(verReferencias.this,"No haz seleccionado un contacto");
                }

            }
        });

    }

    private void obtieneRegistro(String url){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String bandUsuario = response;
                System.err.println(bandUsuario);
                if(bandUsuario.trim().length()>0){
                    try {
                        arrProductos = new ArrayList();
                        JSONArray jsusuario = new JSONArray(response);
                        System.err.println(jsusuario);
                        int js = jsusuario.length();
                        int i = 0;
                        while(i<js){
                            arrProductos.add(jsusuario.get(i));
                            i++;
                        }
                        if(!arrProductos.isEmpty()){
                            agregaProudctos(arrProductos);
                        }else{
                            showAlertDialog(verReferencias.this,"Referencias","No se encontro referencias");
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }else{
                    showAlertDialog(verReferencias.this,"Referencias","No se encontro referencias");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(verReferencias.this,error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> parametros = new HashMap<String, String>();
                parametros.put("idUsuario",edtIdUsuarioReg);
                return parametros;
            }
        };

        RequestQueue reqQue = Volley.newRequestQueue(this);
        reqQue.add(stringRequest);

    }

    public void agregaProudctos(ArrayList arrProd) throws JSONException {
        JSONArray arrRReg;
        for(int i=0;i<arrProd.size();i++){
            arrRReg = (JSONArray) arrProd.get(i);
            System.err.println("Este es el arreglo arrRReg: "+arrRReg);
            newTR = new TableRow(this);
            TextView newTxtName = new TextView(this);
            TextView telefono = new TextView(this);
            //nombre usuario
            newTxtName.setText(arrRReg.get(0).toString());
            newTxtName.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            newTxtName.setBackgroundColor(i%2==0? Color.argb(93,0,141,205):Color.argb(78,3,169,244));
            newTxtName.setTextColor(Color.BLACK);
            newTxtName.setWidth(600);
            newTxtName.setTextSize(20);
            newTxtName.setId(i);
            //telefono
            telefono.setText(arrRReg.get(1).toString());
            telefono.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            telefono.setBackgroundColor(i%2==0? Color.argb(93,0,141,205):Color.argb(78,3,169,244));
            telefono.setTextColor(Color.BLACK);
            telefono.setWidth(500);
            telefono.setTextSize(20);
            telefono.setId(i);

            newTR.addView(newTxtName);
            newTR.addView(telefono);

            newTR.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    TableRow t = (TableRow) v;
                    TextView firstTextViewNom = (TextView) t.getChildAt(0);
                    TextView firstTextViewTelf= (TextView) t.getChildAt(1);
                    String nomber = firstTextViewNom.getText().toString();
                    String telefono = firstTextViewTelf.getText().toString();
                    edtNomCont.setText(nomber);
                    edtTelfCont.setText(telefono);
                }
            });


            tl.addView(newTR);
        }
    }

    public static verReferencias getInstance() {
        return instance;
    }

    public void finishActiviti(){
        finish();
    }

    public void showAlertDialog(Context activity, final String titel, final String text) {
        aDialogBuilder = new AlertDialog.Builder(activity);
        aDialogBuilder.setMessage(text);
        aDialogBuilder.setTitle(titel);
        aDialogBuilder.setCancelable(false);
        aDialogBuilder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();//w  ww.j a v  a  2  s  .  c  o m
                finishActiviti();
            }
        });
        aDialog = aDialogBuilder.create();
        aDialog.show();
    }
    public void onClickLlamada(View v) {

    }
}