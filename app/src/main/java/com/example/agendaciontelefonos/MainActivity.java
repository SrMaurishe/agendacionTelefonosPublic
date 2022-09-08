package com.example.agendaciontelefonos;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static ArrayList arrProductos = new ArrayList();
    private static int idTitular;
    EditText usuario,pass;
    Button btnLogin;
    Button btnAlta;
    static MainActivity instance = null;
    ProgressBar prBar;
    alertaMensaje alert = new alertaMensaje();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usuario = findViewById(R.id.edtUsuario);
        btnLogin = findViewById(R.id.btnLogIn);
        btnAlta = findViewById(R.id.btnNuevoTit);
        prBar = (ProgressBar) findViewById(R.id.gifEsperar);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prBar.setVisibility(View.VISIBLE);
                usuario.setEnabled(false);
                btnLogin.setEnabled(false);
                login("https://mjdeveloperweb.000webhostapp.com/php/conexionCliente.php");
            }
        });

        btnAlta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prBar.setVisibility(View.VISIBLE);
                usuario.setEnabled(false);
                btnLogin.setEnabled(false);
                agregar("https://mjdeveloperweb.000webhostapp.com/php/obtieneConsecutivo.php");
            }
        });

    }
    public static MainActivity getInstance() {
        return instance;
    }

    private void login(String url){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
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

                    prBar.setVisibility(View.GONE);
                    if(jsusuario.length()>0){
                        usuario.setEnabled(true);
                        Intent intent = new Intent(getApplicationContext(),Principal.class);
                        startActivity(intent);
                        btnLogin.setEnabled(true);
                    }else{
                        alert.mensajes(MainActivity.this,"No se encontro usuario");
                        usuario.setEnabled(true);
                        btnLogin.setEnabled(true);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,error.toString(), Toast.LENGTH_SHORT).show();
                System.err.println(error);
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> parametros = new HashMap<String, String>();
                parametros.put("usuario",usuario.getText().toString());
                return parametros;
            }
        };

        RequestQueue reqQue = Volley.newRequestQueue(this);
        reqQue.add(stringRequest);

    }

    private void agregar(String url){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    prBar.setVisibility(View.GONE);
                    System.err.println(response);
                    if(response.length()>0){
                        int titular = Integer.parseInt(response.equals("")?"0":response);
                        idTitular = titular+1;
                        usuario.setEnabled(true);
                        Intent intent = new Intent(getApplicationContext(),nuevoTitular.class);
                        startActivity(intent);
                        btnLogin.setEnabled(true);
                    }else{
                        alert.mensajes(MainActivity.this,"No se encontro usuario");
                        usuario.setEnabled(true);
                        btnLogin.setEnabled(true);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,error.toString(), Toast.LENGTH_SHORT).show();
                System.err.println(error);
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> parametros = new HashMap<String, String>();
                parametros.put("usuario",usuario.getText().toString());
                return parametros;
            }
        };

        RequestQueue reqQue = Volley.newRequestQueue(this);
        reqQue.add(stringRequest);

    }

    public static ArrayList getArrayProd(){
        return arrProductos;
    }
    public static int getIdTitular(){
        return idTitular;
    }
}