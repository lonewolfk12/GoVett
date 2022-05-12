package com.example.govett

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_auth.*
import kotlinx.android.synthetic.main.activity_home.*

enum class ProviderType{
    BASIC,
    GOOGLE,
    FACEBOOK,



}
class HomeActivity : AppCompatActivity() {
    val bnt1 = 100

    private val nom: EditText? = null
    private  var apel:EditText? = null


    val db=FirebaseFirestore.getInstance()




    override fun onCreate(savedInstanceState: Bundle?) {





        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val bundle   =intent.extras
         val email =bundle?.getString("email")
        val provider =bundle?.getString("provider")

        setup(email?:"",provider?:"")


        //datos guardados cuando salgas de la app
        val prefs=getSharedPreferences(getString(R.string.prefs_file), MODE_PRIVATE).edit()
        prefs.putString("email",email)
        prefs.putString("provider",provider)
        prefs.apply()

    }




    private fun setup(email:String ,provider:String){
        title="Inicio"
        EmailTextView.text =email
        ProviderTextView.text=provider
        logOutButton.setOnClickListener {
            Toast.makeText(this, "Sesión finalizada", Toast.LENGTH_SHORT).show();
            //borrar datos
            val prefs=getSharedPreferences(getString(R.string.prefs_file),Context.MODE_PRIVATE).edit()
            prefs.clear()
            prefs.apply()
            FirebaseAuth.getInstance().signOut()
            onBackPressed()

        }



        savedButton.setOnClickListener{




    val et_nombre: EditText;
    val et_apellido:EditText;
    val dire:EditText;
    val edad:EditText;
    val telefono:EditText;


    et_nombre = findViewById<Button>(R.id.nombreTextView) as EditText
    et_apellido=findViewById<Button>(R.id.apellidosTextView) as EditText
    dire=findViewById<Button>(R.id.direccionTextView) as EditText
    edad=findViewById<Button>(R.id.edadTextView) as EditText
    telefono=findViewById<Button>(R.id.phoneTextView) as EditText







            val nombre = et_nombre.text.toString()
    val nombre2 = et_apellido.text.toString()
    val nombre3 = dire.text.toString()
    val nombre4 = edad.text.toString()
    val nombre5 = telefono.text.toString()
    if (nombre == ""||nombre2==""||nombre3==""||nombre4==""||nombre5=="") {
        Toast.makeText(this, "Llenar los campos", Toast.LENGTH_SHORT).show()
    }else{
        Toast.makeText(this, "Datos Guardados",Toast.LENGTH_SHORT).show();
        val user = db.collection("user").document(email).set(
            hashMapOf(
                "provider" to provider,
                "nombre" to nombreTextView.text.toString(),
                "apellidos" to apellidosTextView.text.toString(),
                "direccion" to direccionTextView.text.toString(),
                "edad" to edadTextView.text.toString(),
                "telefono" to phoneTextView.text.toString(),



                ))


           val ventana2=findViewById<Button>(R.id.ventana2)
        ventana2.setOnClickListener {
            val lanzar=Intent(this,MenuActivity::class.java)
            startActivity(lanzar)

        }
    }

}




        ventana2.setOnClickListener {
            if (nombreTextView.text.isEmpty() && apellidosTextView.text.isEmpty() && phoneTextView.text.isEmpty() &&
                   edadTextView.text.isEmpty() &&direccionTextView.text.isEmpty() ){
                Toast.makeText(this, "Llenar los campos", Toast.LENGTH_SHORT).show()
            }

        }

getButton.setOnClickListener {

    Toast.makeText(this, "Datos Recuperados",Toast.LENGTH_SHORT).show();

    db.collection("user").document(email).get().addOnSuccessListener {
    nombreTextView.setText(it.get("nombre")as String)
    apellidosTextView.setText(it.get("apellidos")as String)
    direccionTextView.setText(it.get("direccion")as String)
    edadTextView.setText(it.get("edad")as String)
    phoneTextView.setText(it.get("telefono")as String)



}
   // vacio()
}

deleteButton.setOnClickListener{
    Toast.makeText(this, "Datos Borrados",Toast.LENGTH_SHORT).show();
    nombreTextView.getText().clear();
    apellidosTextView.getText().clear();
    direccionTextView.getText().clear();
    edadTextView.getText().clear();
    phoneTextView.getText().clear();

db.collection("user").document(email).delete()
   // vacio()
}

    }


    private fun vacio(){
        val builder=AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Campos vacios,ingrese sus datos")

        builder.setPositiveButton( "Aceptar",null)
        val dialog:AlertDialog=builder.create()
        dialog.show()


    }






        }