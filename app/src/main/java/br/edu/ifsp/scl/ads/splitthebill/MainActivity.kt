package br.edu.ifsp.scl.ads.splitthebill

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.edu.ifsp.scl.ads.splitthebill.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}