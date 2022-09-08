package com.example.agendaciontelefonos;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import java.util.HashMap;
import java.util.Map;

public class nuevoTitular extends AppCompatActivity {
    EditText idTitNew,nomTitNew,telTitNew;
    ProgressBar prBarinsert;
    Button btnInserta;
    private static AlertDialog.Builder aDialogBuilder;
    private static AlertDialog aDialog;
    alertaMensaje alert = new alertaMensaje();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_titular);
        idTitNew = findViewById(R.id.edtIdUsuarioInserta);
        nomTitNew = findViewById(R.id.edtNombreInserta);
        telTitNew = findViewById(R.id.edtTelefonoInserta);
        prBarinsert = (ProgressBar) findViewById(R.id.loadInserta);
        prBarinsert.setVisibility(View.GONE);
        btnInserta = findViewById(R.id.btnInsertaTitular);
        idTitNew.setText(String.valueOf(MainActivity.getInstance().getIdTitular()));
        
        btnInserta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!nomTitNew.getText().equals("")&&!telTitNew.equals("")){
                    registrar("https://mjdeveloperweb.000webhostapp.com/php/agregaTitular.php");
                }
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
                    showAlertDialogReg(nuevoTitular.this,bandUsuario,bandUsuario);
                }else{
                    alert.mensajes(nuevoTitular.this,"Usuario No Registrado");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(nuevoTitular.this,error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> parametros = new HashMap<String, String>();
                parametros.put("usuario",nomTitNew.getText().toString());
                parametros.put("telefono",telTitNew.getText().toString());
                parametros.put("idUsuario",idTitNew.getText().toString());
                return parametros;
            }
        };

        RequestQueue reqQue = Volley.newRequestQueue(this);
        reqQue.add(stringRequest);

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