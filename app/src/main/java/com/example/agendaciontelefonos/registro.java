package com.example.agendaciontelefonos;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class registro extends AppCompatActivity {
    Button btnRegistraAlta;
    EditText edtUsuario,edtIdUsuario,telAlta;
    alertaMensaje alert;
    static registro instance = null;
    private static AlertDialog.Builder aDialogBuilder;
    private static AlertDialog aDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        edtUsuario = findViewById(R.id.edtUsuaioAlta);
        telAlta = findViewById(R.id.edtTelefono);
        edtIdUsuario = findViewById(R.id.edtIdUsuario);
        btnRegistraAlta = findViewById(R.id.btnRegistrar);
        edtIdUsuario.setText(Principal.getInstance().getIdUsuaio());
        btnRegistraAlta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrar("https://mjdeveloperweb.000webhostapp.com/php/conexiones_cliente.php");
            }
        });
    }

    private void registrar(String url){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String bandUsuario = response;
                System.err.println(bandUsuario);
                if(bandUsuario.trim().length()>0){
                    showAlertDialogReg(registro.this,bandUsuario,bandUsuario);
                }else{
                    alert.mensajes(registro.this,"Usuario No Registrado");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(registro.this,error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> parametros = new HashMap<String, String>();
                parametros.put("usuario",edtUsuario.getText().toString());
                parametros.put("telefono",telAlta.getText().toString());
                parametros.put("idUsuario",edtIdUsuario.getText().toString());
                return parametros;
            }
        };

        RequestQueue reqQue = Volley.newRequestQueue(this);
        reqQue.add(stringRequest);

    }
    public static registro getInstance() {
        return instance;
    }

    public void showAlertDialogReg(Context activity, final String titel, final String text) {
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

    public void finishActiviti(){
        finish();
    }
}