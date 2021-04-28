package pl.krzysztofbaka.projekt

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import pl.krzysztofbaka.projekt.ChartActivity
import java.util.*
import kotlinx.android.synthetic.main.activity_chart.*
import pl.krzysztofbaka.projekt.databinding.ActivityAddBinding
import pl.krzysztofbaka.projekt.databinding.ActivityChartBinding
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

        option = binding.spinner
        option.adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, options)

        option.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                binding.textViewSpinner.setText(options.get(position))
                graph_view.setData(generateRandomDataPoints())

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //binding.textViewSpinner.setText("")
            }
        }


    }

    private fun generateRandomDataPoints(): List<DataPoint> {
        val random = Random()

        var lista = ArrayList<DataPoint>()
        lista.add(DataPoint(0, 20))
        lista.add(DataPoint(1, -50))
        lista.add(DataPoint(3, random.nextInt(1000) + 1))
        lista.add(DataPoint(4, random.nextInt(1000) + 1))

        return lista


    }
}