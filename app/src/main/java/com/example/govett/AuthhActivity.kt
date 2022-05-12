package com.example.govett

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.view.View
import android.widget.Toast

import androidx.appcompat.app.AlertDialog
import com.firebase.ui.auth.AuthUI.TAG
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.actionCodeSettings
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_auth.*



class AuthhActivity : AppCompatActivity() {
    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setTheme(R.style.Theme_GoVett)
        setContentView(R.layout.activity_auth)


//analytics event


            val analytics: FirebaseAnalytics = FirebaseAnalytics.getInstance(this)
            val bundle = Bundle()

            bundle.putString("message", "Integración de firebase completa")
            analytics.logEvent("InitScreen", bundle)



           setup()
        session()


        }


    fun onStar(){

    super.onStart()
    authLayout.visibility=View.VISIBLE




}// para preferences shared
    private fun session(){
        val prefs=getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val email=prefs.getString("email",null)
        val provider=prefs.getString("provider",null)
        if(email != null && provider!=null){
           authLayout.visibility= View.INVISIBLE
            showHome(email,ProviderType.valueOf(provider))

        }

    }


private fun setup() {
    title = "Autenticación"
    singUpButton.setOnClickListener {
        if (emailEdiText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()) {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                emailEdiText.text.toString(),
                passwordEditText.text.toString()
            ).addOnCompleteListener {


                if (it.isSuccessful) {
                    showHome(it.result?.user?.email ?: " ", ProviderType.BASIC)


                } else{


                    showAlerte()
                }



            }
        } else{
            if (emailEdiText.text.isEmpty() && passwordEditText.text.isEmpty())
                vacio()
        }

    }

    logInButton.setOnClickListener {



        if (emailEdiText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()) {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(
                emailEdiText.text.toString(),
                passwordEditText.text.toString()
            ).addOnCompleteListener {


                if (it.isSuccessful) {


                    showHome(it.result?.user?.email ?: " ", ProviderType.BASIC)

                } else {
                    showAlert()

                }
            }
        }
else{
    if (emailEdiText.text.isEmpty() && passwordEditText.text.isEmpty())


        vacio()
}
    }




    val GOOGLE_SIGN_IN = 100
    lateinit var googleSignInClient: GoogleSignInClient


    googleButton.setOnClickListener() {


        //configuracion de autenticación de google

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
         googleSignInClient.signOut()


        startActivityForResult(googleSignInClient.signInIntent, GOOGLE_SIGN_IN)

    }


    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GOOGLE_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)



                if (account != null) {
                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)


                    FirebaseAuth.getInstance().signInWithCredential(credential)
                        .addOnCompleteListener {

                            if (it.isSuccessful) {
                                showHome(account.email ?: " ", ProviderType.GOOGLE)

                            } else {


                            showAlert()
                            }


                        }

                }


            }catch (e: ApiException){
                showAlert()

            }


        }


    }


}

private fun showAlert(){
    val builder=AlertDialog.Builder(this)
    builder.setTitle("Error")
    builder.setMessage("Este correo no existe en la base de datos,por favor registrese")

    builder.setPositiveButton( "Aceptar",null)
    val dialog:AlertDialog=builder.create()
    dialog.show()


}
    private fun showAlerte(){
        val builder=AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("El correo ingresado ya existe")

        builder.setPositiveButton( "Aceptar",null)
        val dialog:AlertDialog=builder.create()
        dialog.show()


    }

    private fun vacio(){
        val builder=AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Campos vacios,ingrese sus datos")

        builder.setPositiveButton( "Aceptar",null)
        val dialog:AlertDialog=builder.create()
        dialog.show()


    }


private fun showHome(email:String,provider:ProviderType){
 val homeIntent =Intent (this,HomeActivity::class.java).apply {
     putExtra("email",email)
     putExtra("provider",provider.name)


 }
   startActivity(homeIntent)
}
/* parte de verificacion de enviar email
private fun verificaremail(){
    val actionCodeSettings = actionCodeSettings {
        // URL you want to redirect back to. The domain (www.example.com) for this
        // URL must be whitelisted in the Firebase Console.
        url = "https://www.example.com/finishSignUp?cartId=1234"
        // This must be true
        handleCodeInApp = true

        setAndroidPackageName(
            "com.example.govett",
            true, /* installIfNotAvailable */
            "12" /* minimumVersion */)
    }

}

 */


    }


