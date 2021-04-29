package pl.krzysztofbaka.projekt

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import kotlinx.android.synthetic.main.activity_chart.*
import pl.krzysztofbaka.projekt.databinding.ActivityChartBinding
import pl.krzysztofbaka.projekt.model.Transaction
import java.lang.Exception
import kotlin.collections.ArrayList

class LineChartActivity : AppCompatActivity() {
    lateinit var option: Spinner
    private val binding by lazy { ActivityChartBinding.inflate(layoutInflater) }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val options = arrayOf(
            "Styczeń",
            "Luty",
            "Marzec",
            "Kwiecień",
            "Maj",
            "Czerwiec",
            "Lipiec",
            "Sierpień",
            "Wrzesień",
            "Październik",
            "Listopad",
            "Grudzień"
        )

        var context = this;
        option = binding.spinner
        option.adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, options)
        var sd =""

        option.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                var transactionListCopy = Shared.transactionList
                transactionListCopy.sortWith(compareBy<Transaction>{it.data.monthValue}.thenBy { it.data.dayOfMonth })

                binding.textViewSpinner.setText(options.get(position))
                var lista = LinkedList<DataPoint>()

                var monthToCheck = position +1
                var counter =0
                var value=0.0
                var minAndMax = ArrayList<Int>();

                for (i in 0..transactionListCopy.size-1) {
                    if (transactionListCopy.get(i).data.monthValue==monthToCheck) {
                        minAndMax.add(transactionListCopy.get(i).kwota.toInt())
                        if(counter==0) {
                            value += transactionListCopy.get(i).kwota.toInt()
                            lista.add(
                                DataPoint(
                                    transactionListCopy.get(i).data.dayOfMonth,
                                    value.toInt()
                                )
                            )
                            sd += transactionListCopy.get(i).data.dayOfMonth.toString() + " " + value.toString() + "\n"
                            counter++
                        }else{
                            if(transactionListCopy.get(i).kwota.toInt()>0){
                                value -= transactionListCopy.get(i).kwota.toInt()
                                lista.add(
                                    DataPoint(
                                        transactionListCopy.get(i).data.dayOfMonth,
                                        value.toInt()
                                    )
                                )
                            }else{
                                value -= transactionListCopy.get(i).kwota.toInt()
                                lista.add(
                                    DataPoint(
                                        transactionListCopy.get(i).data.dayOfMonth,
                                        value.toInt()
                                    )
                                )
                            }
                        }
                    }
                }

                var min =0;
                var max=0;
                try {
                    min = minAndMax.minOrNull()!!
                    max = minAndMax.maxOrNull()!!
                    binding.textView2.setText(min.toString() + " " + max.toString())
                    graph_view.setData(lista, min, max)

                }catch (e : Exception){
                    Toast.makeText(context, "Brak danych bądź brak przychodu", Toast.LENGTH_LONG).show()

                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //binding.textViewSpinner.setText("")
            }
        }


    }

    private fun generateRandomDataPoints(): List<DataPoint> {
        val random = Random()

        var lista = ArrayList<DataPoint>()
        lista.add(DataPoint(0, random.nextInt(1000) + 1))
        lista.add(DataPoint(1, random.nextInt(1000) + 1))
        lista.add(DataPoint(3, random.nextInt(1000) + 1))
        lista.add(DataPoint(4, random.nextInt(1000) + 1))

        return lista


    }
}