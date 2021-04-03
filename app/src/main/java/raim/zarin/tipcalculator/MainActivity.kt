package raim.zarin.tipcalculator

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
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
        binding.costOfServiceEditText.setOnKeyListener { view, keyCode, _ ->
            handleKeyEvent(
                view,
                keyCode
            )
        }
    }

    private fun calculateTipAmount() {
        val stringOfCost = binding.costOfServiceEditText.text.toString()
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

    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}