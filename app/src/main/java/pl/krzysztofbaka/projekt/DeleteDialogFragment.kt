package pl.krzysztofbaka.projekt

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import pl.krzysztofbaka.projekt.adapter.TransactionAdapter

class DeleteDialogFragment(position:Int, adapter : TransactionAdapter, test: TextView) : DialogFragment() {
    val toRemovePosition = position
    val transactionAdapter = adapter
    val textViewPodsumowanie = test

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            builder.setMessage("Czy na pewno chcesz usunąć ten wpis ?")
                    .setPositiveButton("Usuń",
                            DialogInterface.OnClickListener { dialog, id ->
                                Shared.transactionList.removeAt(toRemovePosition)
                                transactionAdapter.transactions = Shared.transactionList
                                var podsumowanie =0.0;
                                for(item in transactionAdapter.transactions){
                                    podsumowanie+=item.kwota
                                }
                                textViewPodsumowanie.text = podsumowanie.toString()
                                if(podsumowanie>0){
                                    textViewPodsumowanie.setTextColor(Color.GREEN)
                                }else{
                                    textViewPodsumowanie.setTextColor(Color.RED)
                                }

                            })
                    .setNegativeButton("Anuluj",
                            DialogInterface.OnClickListener { dialog, id ->
                            })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}