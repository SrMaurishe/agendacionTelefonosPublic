package com.example.agendaciontelefonos;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;

public class alertaMensaje {
    public void mensajes(Context cont, String Mensaje){

        AlertDialog.Builder builder1 = new AlertDialog.Builder(cont);
        builder1.setMessage(Mensaje);
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "Aceptar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();

    }





}
