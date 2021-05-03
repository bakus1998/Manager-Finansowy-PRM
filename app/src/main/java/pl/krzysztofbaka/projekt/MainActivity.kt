package pl.krzysztofbaka.projekt

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import pl.krzysztofbaka.projekt.adapter.TransactionAdapter
import pl.krzysztofbaka.projekt.chart.DataPoint
import pl.krzysztofbaka.projekt.databinding.ActivityMainBinding
import pl.krzysztofbaka.projekt.listener.DeleteDialogFragment
import pl.krzysztofbaka.projekt.listener.RecyclerItemClickListener
import pl.krzysztofbaka.projekt.model.Transaction
import java.time.LocalDate
import java.util.*


const val REQ = 1

class MainActivity : AppCompatActivity() {
    val binding by lazy{ ActivityMainBinding.inflate(layoutInflater)}
    val transactionAdapter by lazy {TransactionAdapter() }
    var podsumowanie: Double = 0.0
    var toDayDate = LocalDate.now()

    lateinit var intents: Intent
    lateinit var intents2: Intent




    override fun onCreate(savedInstanceState: Bundle?) {
        intents = Intent(this,AddActivity::class.java)
        intents2 = Intent(this,LineChartActivity::class.java)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        var d = LocalDate.now()
        d= d.minusDays(5)
        d = d.minusMonths(3)
        val sampleModel1 = Transaction("McDonald",-36.50, LocalDate.now().minusDays(10).plusMonths(1),"Jedzenie",false);
        val sampleModel4 = Transaction("KFC",-60.50, LocalDate.now().minusDays(16).plusMonths(1),"Jedzenie",false);
        val sampleModel2 = Transaction("Lotto",406.5, LocalDate.now().minusDays(13).plusMonths(1),"Zdrowie",true);
        val sampleModel3 = Transaction("Lotto",730.5, LocalDate.now().minusDays(26).plusMonths(1),"Zdrowie",true);

        Shared.transactionList.add(sampleModel1)
        Shared.transactionList.add(sampleModel2)
        Shared.transactionList.add(sampleModel3)
        Shared.transactionList.add(sampleModel4)

        Shared.transactionList.sortWith(compareBy<Transaction>{it.data.monthValue}.thenBy { it.data.dayOfMonth })

        setupTransactionList()
        binding.buttonDodaj.setOnClickListener {
            startActivity(Intent(this, AddActivity::class.java))
        }
        binding.buttonZestawienie.setOnClickListener {
            startActivity(intents2)
        }

        binding.transactionList.addOnItemTouchListener(RecyclerItemClickListener(this, binding.transactionList, object : RecyclerItemClickListener.OnItemClickListener {

            override fun onItemClick(view: View, position: Int) {
                intents.putExtra("editOrCreate", "edit")
                intents.putExtra("position",position.toString())
                startActivity(intents)
            }
            override fun onItemLongClick(view: View?, position: Int) {
                val test =
                    DeleteDialogFragment(position, transactionAdapter,binding.textViewPodsumowanie);
                val fragment = supportFragmentManager;
                test.show(fragment,"")
                setTextPodsumowanie()
            }

        }))

    }


    private fun setupTransactionList(){
        binding.transactionList.apply {
            adapter = transactionAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onResume() {
        super.onResume()
        transactionAdapter.transactions = Shared.transactionList
        setTextPodsumowanie()

    }

    fun setTextPodsumowanie(){
        podsumowanie =0.0;

        val plMonths = arrayOf("","Styczeń", "Luty", "Marzec"
                ,"Kwiecień","Maj","Czerwiec"
                ,"Lipiec","Sierpień","Wrzesień"
                ,"Październik","Listopad","Grudzień")

        var monthInt = toDayDate.monthValue.toInt()

        var counter = 0;

        for(item in Shared.transactionList){
            if(item.data.month==toDayDate.month&&counter==0)
            podsumowanie+=item.kwota
        }
        binding.textViewPodsumowanie.text = podsumowanie.toString()
        if(podsumowanie>0){
            binding.textViewPodsumowanie.setTextColor(Color.GREEN)
        }else{
            binding.textViewPodsumowanie.setTextColor(Color.RED)
        }

        binding.textViewMiesiac.setText(plMonths.get(monthInt))
        binding.textViewPodsumowanie.append(" zł")
    }


}