package raim.zarin.tipcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import raim.zarin.tipcalculator.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        displayTip(0.0)
        binding.calculateButton.setOnClickListener { calculateTipAmount() }
    }

    private fun calculateTipAmount() {
        val stringOfCost = binding.costOfService.text.toString()
        val cost = stringOfCost.toDoubleOrNull()
        // If the cost is null or 0, then display 0 tip and exit this function early
        if (cost == null || cost == 0.0) {
            displayTip(0.0)
            return
        }

        // Get tip percentage according to checked radio button
        val tipPercentage: Double = when (binding.tipOptions.checkedRadioButtonId) {
            R.id.option_twenty_percent -> 0.2
            R.id.option_eighteen_percent -> 0.18
            else -> 0.15
        }

        // Calculate tip, round up if switch checked
        var tipAmount = cost * tipPercentage
        if (binding.roundUpSwitch.isChecked) {
            tipAmount = kotlin.math.ceil(tipAmount)
        }

        // Display formatted tip value
        displayTip(tipAmount)
    }

    /**
     * Shows result in currency of users settings
     * @param tip amount of tip to format and show
     */
    private fun displayTip(tip: Double) {
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        binding.tipResult.text = getString(R.string.tip_amount, formattedTip)
    }

}