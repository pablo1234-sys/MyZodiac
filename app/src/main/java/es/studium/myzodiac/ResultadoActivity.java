package es.studium.myzodiac;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class ResultadoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_resultado);
        //--------------------------
        Intent recibido =getIntent();
        String fechaRecuperada = recibido.getStringExtra("fechaNacimiento");
        String[] componentesFechaRecuperada = fechaRecuperada.split("/");


        int diaFechaRecuperada= Integer.parseInt(componentesFechaRecuperada[0]);
        int mesFechaRecuperada= Integer.parseInt(componentesFechaRecuperada[1]);
        int anioFechaRecuperada= Integer.parseInt(componentesFechaRecuperada[2]);

        Calendar hoy = Calendar.getInstance();
        int diaHoy = hoy.get(Calendar.DAY_OF_MONTH);
        int mesHoy= hoy.get(Calendar.MONTH)+1; //Esto se debe a que la fecha que recuperamos usa los meses del 1 al 12, pero Calendar numera los meses del 0 (enero) al 11(diciembre). De esta forma, tenemos que sumar 1 para que ambos
        //sistemas de referencia sean iguales y poder operar con ellos.
        int anioHoy=hoy.get(Calendar.YEAR);

        int edad=anioHoy-anioFechaRecuperada;

        ImageView IVimagenSignoZodiaco = findViewById(R.id.imagenHoroscopo);

        if (mesFechaRecuperada>mesHoy || (mesFechaRecuperada==mesHoy && diaFechaRecuperada>diaHoy) ) {
            edad=edad-1;
        }

            String[] signosDelZodiaco=getResources().getStringArray(R.array.arraySignoZodiaco);
            String signoZodiaco=""; //Necesario para que el if-else no dé errores por no inicializar (aunque siempre se le vaya a dar un valor, genera ese error).
        if ( (diaFechaRecuperada>=21 && mesFechaRecuperada==3) || (diaFechaRecuperada<=19 && mesFechaRecuperada==4) ) {
            signoZodiaco=signosDelZodiaco[0];
            IVimagenSignoZodiaco.setImageResource(R.drawable.aries);
        } else if ( (diaFechaRecuperada>=20 && mesFechaRecuperada==4) || (diaFechaRecuperada<=20 && mesFechaRecuperada==5) ) {
            signoZodiaco=signosDelZodiaco[1];
            IVimagenSignoZodiaco.setImageResource(R.drawable.tauro);
        } else if ( (diaFechaRecuperada>=21 && mesFechaRecuperada==5) || (diaFechaRecuperada<=20 && mesFechaRecuperada==6) ) {
            signoZodiaco=signosDelZodiaco[2];
            IVimagenSignoZodiaco.setImageResource(R.drawable.geminis);
        } else if ( (diaFechaRecuperada>=21 && mesFechaRecuperada==6) || (diaFechaRecuperada<=22 && mesFechaRecuperada==7) ) {
            signoZodiaco=signosDelZodiaco[3];
            IVimagenSignoZodiaco.setImageResource(R.drawable.cancer);
        } else if ( (diaFechaRecuperada>=23 && mesFechaRecuperada==7) || (diaFechaRecuperada<=22 && mesFechaRecuperada==8) ) {
            signoZodiaco=signosDelZodiaco[4];
            IVimagenSignoZodiaco.setImageResource(R.drawable.leo);
        } else if ( (diaFechaRecuperada>=23 && mesFechaRecuperada==8) || (diaFechaRecuperada<=22 && mesFechaRecuperada==9) ){
            signoZodiaco=signosDelZodiaco[5];
            IVimagenSignoZodiaco.setImageResource(R.drawable.virgo);
        } else if ( (diaFechaRecuperada>=23 && mesFechaRecuperada==9) || (diaFechaRecuperada<=22 && mesFechaRecuperada==10) ){
            signoZodiaco=signosDelZodiaco[6];
            IVimagenSignoZodiaco.setImageResource(R.drawable.libra);
        } else if ( (diaFechaRecuperada>=23 && mesFechaRecuperada==10) || (diaFechaRecuperada<=21 && mesFechaRecuperada==11) ){
            signoZodiaco=signosDelZodiaco[7];
            IVimagenSignoZodiaco.setImageResource(R.drawable.escorpio);
        } else if ( (diaFechaRecuperada>=22 && mesFechaRecuperada==11) || (diaFechaRecuperada<=21 && mesFechaRecuperada==12) ){
            signoZodiaco=signosDelZodiaco[8];
            IVimagenSignoZodiaco.setImageResource(R.drawable.sagitario);
        } else if ( (diaFechaRecuperada>=22 && mesFechaRecuperada==12) || (diaFechaRecuperada<=19 && mesFechaRecuperada==1) ) {
            signoZodiaco=signosDelZodiaco[9];
            IVimagenSignoZodiaco.setImageResource(R.drawable.capricornio);
        } else if ( (diaFechaRecuperada>=20 && mesFechaRecuperada==1) || (diaFechaRecuperada<=18 && mesFechaRecuperada==2) ){
            signoZodiaco=signosDelZodiaco[10];
            IVimagenSignoZodiaco.setImageResource(R.drawable.acuario);
        } else if ( (diaFechaRecuperada>=19 && mesFechaRecuperada==2) || (diaFechaRecuperada<=20 && mesFechaRecuperada==3) ) {
            signoZodiaco=signosDelZodiaco[11];
            IVimagenSignoZodiaco.setImageResource(R.drawable.piscis);
        }
        TextView TVsignoZodiaco = findViewById(R.id.textoHoroscopo);
        TVsignoZodiaco.setText(signoZodiaco);
        TextView TVedad=findViewById(R.id.textoEdad);
        TVedad.setText(getString(R.string.texto_Edad,edad));




        Button botonVolver = findViewById(R.id.botonVolver);
        botonVolver.setOnClickListener(vueltaMainActivity -> {
            finish();
                });
        //-------------------------
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}