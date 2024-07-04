package com.tao.calculadora

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import com.tao.calculadora.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity()
{
    //binding
    private lateinit var binding : ActivityMainBinding

    private var numeroInicial = ""
    private var numeroFinal = ""
    private var operador = ""
    private var resultado = ""
    private var formula = ""
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState : Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //NoLimitScreen
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        binding.apply {

            binding.layoutMain.children.filterIsInstance<Button>().forEach { button ->
                //buttons click listener
                button.setOnClickListener {
                    //get clicked button text
                    val textoBoton = button.text.toString()
                    when{
                        //Logica para numeros
                        textoBoton.matches(Regex("[0-9]"))->{
                            if(operador.isEmpty())
                            {
                                numeroInicial+=textoBoton
                                formula+=textoBoton
                                tvFormula.text = formula
                                tvResultado.text = numeroInicial
                            }else
                            {
                                numeroFinal+=textoBoton
                                formula+=textoBoton
                                tvFormula.text = formula
                                resultado = resolverOperacion(numeroInicial,numeroFinal,operador)
                                tvResultado.text = resultado

                            }
                        }
                        //Logica para operadores
                        textoBoton.matches(Regex("[+\\-*/]"))->{
                            if (numeroFinal.isEmpty()&& operador.isEmpty()) {
                                if (numeroInicial.isNotEmpty()) {
                                    operador = textoBoton
                                    tvResultado.text = "0"
                                    formula+=textoBoton
                                    tvFormula.text = formula
                                }
                            }else{
                                if (numeroFinal.isNotEmpty()) {
                                    numeroInicial=resultado
                                    numeroFinal=""
                                    operador = textoBoton
                                    tvResultado.text = resultado
                                    formula+=textoBoton
                                    tvFormula.text = formula
                                }else{
                                    formula= formula.removeSuffix(operador)
                                    operador = textoBoton
                                    formula+=textoBoton
                                    tvFormula.text = formula
                                }
                            }

                        }
                        //Logica para igual
                        textoBoton == "="->{
                            if (numeroFinal.isNotEmpty()&& operador.isNotEmpty())
                            {
                                formula = resultado
                                tvFormula.text = formula
                                resultado = resolverOperacion(numeroInicial,numeroFinal,operador)
                                tvResultado.text = ""
                                numeroInicial = resultado
                                resultado = ""
                                numeroFinal = ""
                                operador = ""
                            }
                        }
                        //Logica para decimales
                        textoBoton == "."->{
                            if(operador.isEmpty())
                            {
                                if (! numeroInicial.contains("."))
                                {
                                    if(numeroInicial.isEmpty())numeroInicial += "0$textoBoton"
                                    else numeroInicial += textoBoton
                                    tvResultado.text = numeroInicial
                                }
                            }else
                            {
                                if (! numeroFinal.contains("."))
                                {
                                    if(numeroFinal.isEmpty()) numeroFinal += "0$textoBoton"
                                    else numeroFinal += textoBoton
                                    tvResultado.text = numeroFinal
                                }
                            }
                        }
                        //Logica para borrar
                        textoBoton == "C"->{
                            numeroFinal = ""
                            numeroInicial = ""
                            operador = ""
                            resultado = ""
                            formula = ""
                            tvResultado.text = "0"
                            tvFormula.text = ""
                        }
                    }
                }
            }


        }
    }

    //funcion que resuelve las operaciones matematicas
    private fun resolverOperacion(numeroUno:String, numeroDos:String, operacion:String):String
    {
        val num1  = numeroUno.toDouble()
        val num2  = numeroDos.toDouble()
        return when(operacion)
        {
            "+"-> (num1+num2).toString()
            "-"-> (num1-num2).toString()
            "*"-> (num1*num2).toString()
            "/"-> (num1/num2).toString()
            else ->""
        }
    }
}