package upeu.edu.pe.demo

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.*

class MainActivity : AppCompatActivity() {
    private lateinit var etInput: EditText
    private var currentInput = ""
    private var operator: String? = null
    private var firstNumber: Double? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calc)

        etInput = findViewById(R.id.et_input)

        // Configurar botones numéricos
        val numberButtons = listOf(
            R.id.btn_0, R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4,
            R.id.btn_5, R.id.btn_6, R.id.btn_7, R.id.btn_8, R.id.btn_9
        )
        numberButtons.forEach { id ->
            findViewById<Button>(id).setOnClickListener { onNumberClicked((it as Button).text.toString()) }
        }

        // Configurar botones de operaciones
        val operationButtons = mapOf(
            R.id.btn_add to "+",
            R.id.btn_sub to "-",
            R.id.btn_mul to "*",
            R.id.btn_div to "/",
            R.id.btn_mod to "%"
        )
        operationButtons.forEach { (id, op) ->
            findViewById<Button>(id).setOnClickListener { onOperatorClicked(op) }
        }

        // Botones especiales
        findViewById<Button>(R.id.btn_pow).setOnClickListener { onOperatorClicked("^") }
        findViewById<Button>(R.id.btn_sqrt).setOnClickListener { onSqrtClicked() }
        findViewById<Button>(R.id.btn_inverse).setOnClickListener { onInverseClicked() }
        findViewById<Button>(R.id.btn_pi).setOnClickListener { onPiClicked() }

        // Configurar botón de igual
        findViewById<Button>(R.id.btn_equal).setOnClickListener { onEqualClicked() }

        // Configurar botón de limpiar
        findViewById<Button>(R.id.btn_clear).setOnClickListener { onClearClicked() }
    }

    private fun onNumberClicked(number: String) {
        currentInput += number
        etInput.setText(etInput.text.toString() + number)
    }

    private fun onOperatorClicked(op: String) {
        if (currentInput.isNotEmpty()) {
            firstNumber = currentInput.toDoubleOrNull()
            operator = op
            currentInput = ""

            etInput.setText(etInput.text.toString() + "" + op + "")
        }
    }

    private fun onEqualClicked() {
        if (operator != null && currentInput.isNotEmpty()) {
            val secondNumber = currentInput.toDoubleOrNull()
            val result = when (operator) {
                "+" -> firstNumber?.plus(secondNumber ?: 0.0)
                "-" -> firstNumber?.minus(secondNumber ?: 0.0)
                "*" -> firstNumber?.times(secondNumber ?: 1.0)
                "/" -> if (secondNumber == 0.0) "Error" else firstNumber?.div(secondNumber ?: 1.0)
                "%" -> firstNumber?.rem(secondNumber ?: 1.0)
                "^" -> firstNumber?.pow(secondNumber ?: 1.0)
                else -> "Error"
            }
            etInput.setText(result.toString())
            currentInput = result.toString()
            operator = null
            firstNumber = null
        }
    }

    private fun onSqrtClicked() {
        if (currentInput.isNotEmpty()) {
            val number = currentInput.toDoubleOrNull()
            val result = number?.let { sqrt(it) } ?: "Error"
            etInput.setText(result.toString())
            currentInput = result.toString()
        }
    }

    private fun onInverseClicked() {
        if (currentInput.isNotEmpty()) {
            val number = currentInput.toDoubleOrNull()
            val result = if (number == 0.0) "Error" else 1 / (number ?: 1.0)
            etInput.setText(result.toString())
            currentInput = result.toString()
        }
    }

    private fun onPiClicked() {
        etInput.setText(PI.toString())
        currentInput = PI.toString()
    }

    private fun onClearClicked() {
        currentInput = ""
        firstNumber = null
        operator = null
        etInput.setText("")
    }
}