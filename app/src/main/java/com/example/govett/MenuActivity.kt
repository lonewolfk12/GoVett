package com.example.govett

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity : AppCompatActivity() {
  lateinit var toogle:ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
         val drawerToggle:DrawerLayout=findViewById(R.id.drawerLayout)
        val navView:NavigationView=findViewById(R.id.nav_view)

        toogle= ActionBarDrawerToggle(this,drawerLayout,R.string.navigation_drawer_open,R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toogle)
        toogle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navView.setNavigationItemSelectedListener {
            when(it.itemId){




                R.id.nav_compartir->{
                    val sendIntent=Intent().apply {
                        action=Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT,"Dale like a la página principal https://www.facebook.com/kevin.escalona.5")
                        type="text/plain"
                    }
                    val shareIntent=Intent.createChooser(sendIntent,null)
                    startActivity(shareIntent)
                }
                R.id.nav_logout->{
                    finish()
                }
                R.id.nav_map->{
                    val lanzar=Intent(this,Maps::class.java)
                    startActivity(lanzar)
                }

                R.id.nav_edit->{
                    val lanzar2=Intent(this,HomeActivity::class.java)
                    startActivity(lanzar2)

                }
                R.id.nav_reg->{
                    val lanzar3=Intent(this,RegistroMascotaActivity::class.java)
                    startActivity(lanzar3)

                }

                R.id.nav_coment->{
                    val lanzar3=Intent(this,ComentActivity::class.java)
                    startActivity(lanzar3)

                }


                R.id.nav_home->Toast.makeText(applicationContext,"En desarrollo",Toast.LENGTH_SHORT).show()
                R.id.nav_message->Toast.makeText(applicationContext,"En desarrollo",Toast.LENGTH_SHORT).show()
                R.id.nav_config->Toast.makeText(applicationContext,"En desarrollo ",Toast.LENGTH_SHORT).show()


            }
        true
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toogle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }



    private fun setupRatingBarWithChanges() {
        val ratingBarChanges: RatingBar = findViewById(R.id.ratingbar_changes)

        setRatingText(numStars = ratingBarChanges.numStars)

        ratingBarChanges.setOnRatingBarChangeListener { ratingBar, rating, _ ->
            setRatingText(rating, ratingBar.numStars)
        }
    }

    private fun setRatingText(rating: Float = 0f, numStars: Int) {
        rating_text.text = formatRating(rating, numStars)
    }

    private fun formatRating(rating: Float, numStars: Int) = "$rating/${numStars}"

}