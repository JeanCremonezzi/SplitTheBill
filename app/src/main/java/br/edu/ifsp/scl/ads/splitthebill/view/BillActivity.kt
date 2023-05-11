package br.edu.ifsp.scl.ads.splitthebill.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.edu.ifsp.scl.ads.splitthebill.databinding.ActivityBillBinding

class BillActivity : AppCompatActivity() {
    private val binding: ActivityBillBinding by lazy {
        ActivityBillBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}