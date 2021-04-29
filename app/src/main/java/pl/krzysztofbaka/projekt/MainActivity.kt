package pl.krzysztofbaka.projekt

import android.R
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Bundle
import android.view.LayoutInflater
import android.view.SurfaceControl
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.klim.tcharts.TChart
import pl.krzysztofbaka.projekt.adapter.TransactionAdapter
import pl.krzysztofbaka.projekt.databinding.ActivityMainBinding
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


        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        var d = LocalDate.now()
        d= d.minusDays(5)
        d = d.minusMonths(3)
        //val sampleModel = Transaction("Wawa",-206.50, d,"Rozrywka",false);
        val sampleModel1 = Transaction("McDonald",-36.50, LocalDate.now().minusDays(5),"Jedzenie",false);
        val sampleModel2 = Transaction("Lotto",406.5, LocalDate.now().minusDays(10),"Zdrowie",true);
        val sampleModel3 = Transaction("Lotto",730.5, LocalDate.now().minusDays(22),"Zdrowie",true);


        //Shared.transactionList.add(sampleModel)
        Shared.transactionList.add(sampleModel1)
        Shared.transactionList.add(sampleModel2)
        Shared.transactionList.add(sampleModel3)

        Shared.transactionList.sortWith(compareBy<Transaction>{it.data.monthValue}.thenBy { it.data.dayOfMonth })



        setupTransactionList()
        binding.buttonDodaj.setOnClickListener {
            startActivity(Intent(this, AddActivity::class.java))
        }
        binding.buttonZestawienie.setOnClickListener {
            startActivity(intents2)
        }

        binding.transactionList.addOnItemTouchListener(RecyclerItemClickListenr(this, binding.transactionList, object : RecyclerItemClickListenr.OnItemClickListener {

            override fun onItemClick(view: View, position: Int) {
                intents.putExtra("editOrCreate", "edit")
                intents.putExtra("position",position.toString())
                startActivity(intents)

                //transactionAdapter.transactions = Shared.transactionList


            }
            override fun onItemLongClick(view: View?, position: Int) {
                val test =DeleteDialogFragment(position, transactionAdapter,binding.textViewPodsumowanie);
                val fragment = supportFragmentManager;
                test.show(fragment,"")
                setTextPodsumowanie()
                //transactionAdapter.transactions = Shared.transactionList

                //binding.buttonDodaj.setText(position.toString())
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


    private fun generateRandomDataPoints(): List<DataPoint> {
        val random = Random()
        return (0..20).map {
            DataPoint(it, random.nextInt(50) + 1)
        }
    }

}