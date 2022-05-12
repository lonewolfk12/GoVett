package com.example.govett

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_home.*

class RegistroMascotaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_mascota)

        savedButton.setOnClickListener{
            Toast.makeText(this, "Datos Guardados", Toast.LENGTH_SHORT).show()


        }
        getButton.setOnClickListener {
            Toast.makeText(this, "Datos Recuperados", Toast.LENGTH_SHORT).show()

        }
        deleteButton.setOnClickListener {
            Toast.makeText(this, "Datos Borrados", Toast.LENGTH_SHORT).show()


        }


    }

}









