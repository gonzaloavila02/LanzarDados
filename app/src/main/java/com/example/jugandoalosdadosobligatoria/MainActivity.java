package com.example.jugandoalosdadosobligatoria;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    String[] arrayPar = {"Seleccione una opción", "PAR", "IMPAR"};
    String[] array7 = {"Seleccione una opción","Menor que 7", "Mayor que 7"};

    TextView saldo,num1,num2;
    Button botonPar, boton7, boton_lanzar;
    Spinner spinner;
    EditText apuesta;
    Context context = this;
    int duration = Toast.LENGTH_LONG;

    //Este ArrayAdapter es el adaptador que necesitamos para poder crear el spinner pasandole el array que hemos creado.
    ArrayAdapter<String> adaptadorSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        saldo = findViewById(R.id.saldo);
        botonPar = findViewById(R.id.boton1);
        boton7 = findViewById(R.id.boton2);
        boton_lanzar = findViewById(R.id.boton_lanzar);
        spinner = findViewById(R.id.spinner);
        apuesta = findViewById(R.id.apuesta);
        //num1 = findViewById(R.id.num1);
        //num2 = findViewById(R.id.num2);

        saldo.setText("100");

        botonPar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParImparOptions();
            }
        });

        boton7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MayorMenor7();
            }
        });

        boton_lanzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(comprobarJugada()){
                    jugada(apuesta.getText().toString(), spinner.getSelectedItem().toString());
                }else{
                    Toast toast2;

                    String mensajeError = "Debes rellenar los campos";
                    toast2 = Toast.makeText(context,mensajeError,duration);

                    toast2.show();
                }
            }
        });
    }

    public void ParImparOptions(){
        adaptadorSpinner = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, arrayPar);
        spinner.setAdapter(adaptadorSpinner);
    }

    public void MayorMenor7(){
        adaptadorSpinner = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, array7);
        spinner.setAdapter(adaptadorSpinner);
    }
    public boolean comprobarJugada(){
        if (spinner.getSelectedItem() == null || apuesta.getText().toString().equalsIgnoreCase("")){
            return false;
        }else{
            return true;

        }


    }
    public void jugada (String valorApuesta, String valorSpinner){
        boolean check= true;
        int cantidadApostada = Integer.parseInt(valorApuesta);
        int cantidadTotal = Integer.parseInt(saldo.getText().toString());

        if(cantidadTotal <= 0 ){
            //ALERT DIALOG (COMO CREARLO)
            AlertDialog.Builder mensajeDespedida = new AlertDialog.Builder(this);
            mensajeDespedida.setMessage("Te has quedado sin dinero");
            mensajeDespedida.setPositiveButton("Terminar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            AlertDialog despedida = mensajeDespedida.create();
            despedida.show();

        }else{
            int num1 = (int)(Math.random()*(6-1+1)+1);
            int num2 = (int)(Math.random()*(6-1+1)+1);

            switch (valorSpinner){
                case "PAR":
                    check = comprobarPar(num1,num2);
                    break;
                case "IMPAR":
                    check = comprobarImpar(num1,num2);
                    break;
                case "Mayor que 7":
                    check = comprobarMayor(num1,num2);
                    break;
                case "Menor que 7":
                    check = comprobarMenor(num1,num2);
                    break;
            }
            //Con la funcion mostrarToast mostramos el layout en el que se muestran los dados
            mostrarDados(num1, num2);
            if (check == true){
                victoria(cantidadTotal,cantidadApostada);
            }else{
                derrota(cantidadTotal,cantidadApostada);
            }
        }
    }


    public boolean comprobarPar(int num1, int num2){
        int resultado = num1 + num2;

        if (resultado % 2 == 0 ) {
            return true;
        }else {
            return false;
        }
    }
    public boolean comprobarImpar(int num1, int num2){
        int resultado = num1 + num2;
        if (resultado % 2 != 0 ) {
            return true;
        }else{
            return false;
        }
    }

    public boolean comprobarMenor (int num1,int num2){
        int resultado = num1+ num2;

        if (resultado ==  7){
            return true;
        }
        if (resultado < 7 ){
            return true;
        }else{
            return false;
        }
    }
    public boolean comprobarMayor(int num1, int num2){
        int resultado = num1+ num2;

        if (resultado == 7){
            return true;
        }

        if (resultado > 7 ) {
            return true;
        }else{
            return false;
        }

    }
    public void mostrarDados(int num1, int num2){
        Toast toast= new Toast(context);
        // Creamos inflater en el que pasaremos el layout que queremos mostrar en el toast
        LayoutInflater inflater = getLayoutInflater();
        // Genero vista pasandole el layout que vamos a mostrar en el toast
        View view = inflater.inflate(R.layout.mostrardados,null);

        TextView cuadro1 = view.findViewById(R.id.num1);
        TextView cuadro2 = view.findViewById(R.id.num2);

        cuadro1.setText(String.valueOf(num1));
        cuadro2.setText(String.valueOf(num2));

        toast.setView(view);
        toast.show();


    }

    public void victoria(int canTotal,int numApuesta){
        int result = canTotal + numApuesta;
        String resultado = String.valueOf(result);

        saldo.setText(resultado);
        Toast toast= new Toast(context);
        // Creamos inflater en el que pasaremos el layout que queremos mostrar en el toast
        LayoutInflater inflater = getLayoutInflater();
        // Genero vista pasandole el layout que vamos a mostrar en el toast
        View view = inflater.inflate(R.layout.victoria,null);

        toast.setView(view);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();


    }
    public void derrota(int canTotal,int numApuesta){
        int result = canTotal - numApuesta;
        String resultado = String.valueOf(result);

        saldo.setText(resultado);
        Toast toast= new Toast(context);
        // Creamos inflater en el que pasaremos el layout que queremos mostrar en el toast
        LayoutInflater inflater = getLayoutInflater();
        // Genero vista pasandole el layout que vamos a mostrar en el toast
        View view = inflater.inflate(R.layout.derrota,null);

        toast.setView(view);
        toast.show();
    }




}