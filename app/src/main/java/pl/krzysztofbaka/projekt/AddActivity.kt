package pl.krzysztofbaka.projekt

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import pl.krzysztofbaka.projekt.databinding.ActivityAddBinding
import pl.krzysztofbaka.projekt.model.Transaction
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class AddActivity : AppCompatActivity() {

    lateinit var option: Spinner
    lateinit var optionTransaction: Spinner
    lateinit var resultOptionTransaction: String
    private val binding by lazy { ActivityAddBinding.inflate(layoutInflater) }
    lateinit var editOrCreate: String
    var position1: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        editOrCreate = intent.getStringExtra("editOrCreate").toString()

        if (editOrCreate == "edit") {
            position1 = intent.getStringExtra("position").toString().toInt()
            option = binding.spinner1
            optionTransaction = binding.spinnerRodzaj

            binding.textViewDodajWpis.setText("Edytuj wpis")
            binding.editTextTextPersonName.setText(Shared.transactionList.get(position1).miejsce)
            binding.editTextTextPrice.setText(
                Shared.transactionList.get(position1).kwota.toString().replace("-", "")
            )
            binding.buttonDodajWpis.setText("Edytuj")

            var kategoria = Shared.transactionList.get(position1).kategoria

            val options = arrayOf("Jedzenie", "Zdrowie", "Opłaty", "Rozrywka")
            option.adapter =
                ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, options)
            option.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    binding.textViewWybranaOpcja.setText(options.get(position))
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    binding.textViewWybranaOpcja.setText(Shared.transactionList.get(position1).kategoria)
                }
            }

            var whichIndexKategoria: Int;
            whichIndexKategoria = 0

            for (i in 0..options.size - 1) {
                if (options.get(i) == kategoria) {
                    whichIndexKategoria = i
                }
            }
            option.setSelection(whichIndexKategoria)


            var calendar: Calendar;
            var pick: DatePickerDialog;
            binding.floatingActionButton.setOnClickListener {
                calendar = Calendar.getInstance();
                val day = calendar.get(Calendar.DAY_OF_MONTH);
                val month = calendar.get(Calendar.MONTH);
                val year = calendar.get(Calendar.YEAR);

                pick = DatePickerDialog(
                    this,
                    DatePickerDialog.OnDateSetListener { view, year, mOfYear, dayOfMonth ->
                        var monthOfYear = mOfYear + 1;
                        var m: String;
                        var d: String;
                        m = monthOfYear.toString()
                        d = dayOfMonth.toString()
                        if (monthOfYear < 10) {
                            m = "0" + monthOfYear;
                        }
                        if (dayOfMonth < 10) {
                            d = "0" + dayOfMonth;
                        }
                        binding.editTextDate.setText("$year-$m-$d")
                    },
                    year,
                    month,
                    day
                )
                pick.show()
            }

            val local: LocalDate;
            local = Shared.transactionList.get(position1).data
            var m: String;
            var d: String;
            m = local.monthValue.toString()
            d = local.dayOfMonth.toString()
            if (local.monthValue < 10) {
                m = "0" + local.monthValue;
            }
            if (local.dayOfMonth < 10) {
                d = "0" + local.dayOfMonth;
            }
            binding.editTextDate.setText("${local.year}-$m-$d")


            val optionsTransaction = arrayOf("Dochód", "Wydatek")
            optionTransaction.adapter =
                ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, optionsTransaction)

            var typ: String

            if (Shared.transactionList.get(position1).czyPrzychod) {
                typ = "Dochód"
            } else {
                typ = "Wydatek"
            }

            optionTransaction.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    resultOptionTransaction = optionsTransaction.get(position);
                    binding.textViewTransation.setText(optionsTransaction.get(position))
                }


                override fun onNothingSelected(parent: AdapterView<*>?) {
                }


            }
            if (typ == "Dochód") {
                optionTransaction.setSelection(0)
            } else {
                optionTransaction.setSelection(1)
            }

        } else {
            option = binding.spinner1
            optionTransaction = binding.spinnerRodzaj

            val options = arrayOf("Jedzenie", "Zdrowie", "Opłaty", "Rozrywka")

            option.adapter =
                ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, options)

            option.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    binding.textViewWybranaOpcja.setText(options.get(position))
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    binding.textViewWybranaOpcja.setText("")
                }
            }

            var calendar: Calendar;
            var pick: DatePickerDialog;
            binding.floatingActionButton.setOnClickListener {
                calendar = Calendar.getInstance();
                val day = calendar.get(Calendar.DAY_OF_MONTH);
                val month = calendar.get(Calendar.MONTH);
                val year = calendar.get(Calendar.YEAR);

                pick = DatePickerDialog(
                    this,
                    DatePickerDialog.OnDateSetListener { view, year, mOfYear, dayOfMonth ->
                        var monthOfYear = mOfYear + 1;
                        var m: String;
                        var d: String;
                        m = monthOfYear.toString()
                        d = dayOfMonth.toString()
                        if (monthOfYear < 10) {
                            m = "0" + monthOfYear;
                        }
                        if (dayOfMonth < 10) {
                            d = "0" + dayOfMonth;
                        }
                        binding.editTextDate.setText("$year-$m-$d")
                    },
                    year,
                    month,
                    day
                )
                pick.show()
            }

            val local: LocalDate;
            local = LocalDate.now();
            var m: String;
            var d: String;
            m = local.monthValue.toString()
            d = local.dayOfMonth.toString()
            if (local.monthValue < 10) {
                m = "0" + local.monthValue;
            }
            if (local.dayOfMonth < 10) {
                d = "0" + local.dayOfMonth;
            }
            binding.editTextDate.setText("${local.year}-$m-$d")

            val optionsTransaction = arrayOf("Dochód", "Wydatek")

            optionTransaction.adapter =
                ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, optionsTransaction)

            optionTransaction.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    resultOptionTransaction = optionsTransaction.get(position);
                    binding.textViewTransation.setText(optionsTransaction.get(position))
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    resultOptionTransaction = ""
                    binding.textViewTransation.setText("")
                }
            }
        }
    }

    fun onSave(view: View) {
        val miejsce = binding.editTextTextPersonName.text.toString()
        val kwota = binding.editTextTextPrice.text.toString().toDouble()
        val data = binding.editTextDate.text.toString()
        val kategoria = binding.textViewWybranaOpcja.text.toString()
        val czyPrzychod = resultOptionTransaction.equals("Dochód")

        var toToast = "";
        if (miejsce == "") {
            toToast = "Podaj miejsce! "
        }

        if (kwota == null || kwota <= 0) {
            if (toToast == "") {
                toToast += "Podaj poprawną kwotę!"
            } else {
                toToast += "\nPodaj poprawną kwotę!"
            }
        }


        var dateLocal: LocalDate;
        dateLocal = LocalDate.now()
        if (data != "") {
            dateLocal =
                LocalDate.parse(binding.editTextDate.text.toString(), DateTimeFormatter.ISO_DATE)
        } else {
            if (toToast == "") {
                toToast += "Podaj poprawną datę!"
            } else {
                toToast += "\nPodaj poprawną datę!"
            }
        }


        if (toToast != "") {
            Toast.makeText(this, toToast, Toast.LENGTH_LONG).show()
            return
        }

        var kwotaKoncowa: Double;

        if (czyPrzychod) {
            kwotaKoncowa = kwota
        } else {
            kwotaKoncowa = -kwota;
        }

        if (editOrCreate == "edit") {
            Shared.transactionList.get(position1).miejsce = miejsce
            Shared.transactionList.get(position1).kwota = kwotaKoncowa
            Shared.transactionList.get(position1).data = dateLocal
            Shared.transactionList.get(position1).kategoria = kategoria
            Shared.transactionList.get(position1).czyPrzychod = czyPrzychod

            Shared.transactionList.sortWith(compareBy<Transaction>{it.data.monthValue}.thenBy { it.data.dayOfMonth })
            finish()
        } else {
            val objectTransaction = Transaction(miejsce, kwotaKoncowa, dateLocal, kategoria, czyPrzychod)
            Shared.transactionList.add(objectTransaction)
            Shared.transactionList.sortWith(compareBy<Transaction>{it.data.monthValue}.thenBy { it.data.dayOfMonth })
            finish()
        }
    }

}