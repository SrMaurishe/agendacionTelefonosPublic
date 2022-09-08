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

public class actualizaTitular extends AppCompatActivity {
    Button btnActualizarTit;
    EditText idUsuarioTit,nombreTit,telefonoTit;
    ProgressBar prBarAct;
    alertaMensaje alert;
    String nombreOld,telOld;
    private static AlertDialog.Builder aDialogBuilder;
    private static AlertDialog aDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualiza_titular);
        btnActualizarTit = findViewById(R.id.btnActualizaTitular);
        idUsuarioTit = findViewById(R.id.edtIdUsuarioAct);
        nombreTit = findViewById(R.id.edtNombreAct);
        telefonoTit = findViewById(R.id.edtTelefonoAct);
        prBarAct = (ProgressBar) findViewById(R.id.loadActualiza);

        nombreOld =Principal.getInstance().getNombreUsuarioPrin();
        telOld =Principal.getInstance().getTelefonoUsuarioPrin();
        idUsuarioTit.setText(Principal.getInstance().getIdUsuaio());
        nombreTit.setText(Principal.getInstance().getNombreUsuarioPrin());
        telefonoTit.setText(Principal.getInstance().getTelefonoUsuarioPrin());
        prBarAct.setVisibility(View.GONE);
        btnActualizarTit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prBarAct.setVisibility(View.VISIBLE);
                idUsuarioTit.setEnabled(false);
                btnActualizarTit.setEnabled(false);
                update("https://mjdeveloperweb.000webhostapp.com/php/actualizaTitular.php");
            }
        });
    }

    private void update(String url){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    System.err.println("Actualiza: "+response);
                    prBarAct.setVisibility(View.GONE);
                    if(response.length()>0){
                        nombreTit.setEnabled(true);
                        showAlertDialog(actualizaTitular.this,"Actualiza",response);
                        btnActualizarTit.setEnabled(true);
                    }else{
                        alert.mensajes(actualizaTitular.this,"No se encontro usuario");
                        nombreTit.setEnabled(true);
                        btnActualizarTit.setEnabled(true);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(actualizaTitular.this,error.toString(), Toast.LENGTH_SHORT).show();
                System.err.println(error);
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> parametros = new HashMap<String, String>();
                parametros.put("idUsuario",idUsuarioTit.getText().toString());
                parametros.put("nombreUsuario",nombreOld);
                parametros.put("telefono",telOld);
                parametros.put("nombreUsuarioNew",nombreTit.getText().toString());
                parametros.put("telefonoNew",telefonoTit.getText().toString());
                return parametros;
            }
        };

        RequestQueue reqQue = Volley.newRequestQueue(this);
        reqQue.add(stringRequest);
    }
    public void showAlertDialog(Context activity, final String titel, final String text) {
        AlertDialog.Builder aDialogBuilder = new AlertDialog.Builder(activity);
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