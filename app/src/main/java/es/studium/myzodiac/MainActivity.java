package es.studium.myzodiac;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //---
        Button botonCalcular = findViewById(R.id.botonCalcular);
        EditText campoFecha=findViewById(R.id.campoFecha);
        botonCalcular.setOnClickListener(comportamiento -> {
            String fecha=campoFecha.getText().toString().trim();

            //Aseguramos que el campo este rellenado y despues que cumpla con el formato.
            if (fecha.isEmpty()==true) {
                Toast.makeText(MainActivity.this,R.string.errorCampoVacio, Toast.LENGTH_SHORT).show();
                return;
            }
            //Tenemos que comprobar que la fecha que introduzca el cliente coincida con el formato que aceptamos
            //Por ende, tendríamos que hacer muchas comprobaciones. Pensando, le pregunté a la IA si había
            //alguna forma de abarcar mas formatos y no solo uno y me mostró el método matches y el string mostrado abajo,
            //donde el primer termino indica uno o dos digitos para el día, el segundo termino indica uno o dos digitos para el mes
            //y el tercero indica 4 digitos para el año.
            //He preferido hacerlo así, porque hay muchas formas de introducir fechas y comprobar cada caso es mas difícil que aceptar un formato genérico.
            //Aceptar años de 2 cifras no lo he añadido, porque habría que diferenciar en si esas 2 cifras son de antes del 2000 o después,
            //lo cual añadiría bastante complejidad al código, de forma que es mas fácil indicar simplemente que el cliente cumpla un formato preestablecido.
            if (fecha.matches("\\d{1,2}/\\d{1,2}/\\d{4}")==false) {
                Toast.makeText(MainActivity.this,R.string.errorFormatoFecha,Toast.LENGTH_SHORT).show();
                return;
            }

            //Ahora aseguramos que la fecha exista:
            SimpleDateFormat formatoFechas= new SimpleDateFormat("dd/MM/yyyy"); //Definimos el formato valido.
            formatoFechas.setLenient(false);
            /* Con setLenient(false) hacemos que Java no acepte fechas que no existen.
             * Por ejemplo, si ponemos 31/02/2000, no intentará corregirla automáticamente y poner 2 de marzo de 2000 (ya que febrero solo llega hasta el 28 o 29 si es bisiesto).
             * Así, cuando usemos parse(), dará un error si la fecha no es válida
             * y podremos mostrar un Toast avisando al usuario.
             */
            Date fechaFinal; //Defino fechaFinal fuera del try, porque si no, la comprobación respecto a hoy tambien tengo que hacerla dentro del try.
            try {
                fechaFinal= formatoFechas.parse(fecha);
            }catch (ParseException o) {
                Toast.makeText(MainActivity.this,R.string.errorFechaNoExistente,Toast.LENGTH_SHORT).show();
                return;
            }
            //Ahora aseguramos que la fecha que introduzcamos no sea una fecha futura, es decir, posterior al dia de hoy
            Date hoy = new Date(); //Esto crea un objeto Date con la fecha y hora exactas en la que ejecutamos el codigo.
            if (fechaFinal.after(hoy)==true) {
                Toast.makeText(MainActivity.this, R.string.errorFechaFutura,Toast.LENGTH_SHORT).show();
                return;
            }






            //Ojo, vamos a enviar fecha, porque si llegamos hasta aquí, ya ha pasado todas las comprobaciones necesarias y en ResultadoActivity para obtener ese campo hemos usado getStringExtra. Sin embargo
            //tambien podriamos mandar fechaFinal, aunque aumentaria la complejidad de recuperar la fecha y tratarla en el ResultadoActivity
            Intent intent = new Intent(MainActivity.this,ResultadoActivity.class);
            intent.putExtra("fechaNacimiento",fecha);
            startActivity(intent);

            //--------------------
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}