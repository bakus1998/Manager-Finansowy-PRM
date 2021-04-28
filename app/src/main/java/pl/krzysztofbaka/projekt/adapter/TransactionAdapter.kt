package pl.krzysztofbaka.projekt.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.krzysztofbaka.projekt.databinding.ItemTransactionBinding
import pl.krzysztofbaka.projekt.model.Transaction


class TransactionItem(val binding: ItemTransactionBinding): RecyclerView.ViewHolder(binding.root){
    fun bind(transaction: Transaction){
        binding.apply {
            textViewDateAdapter.text = transaction.data.toString()
            textViewCategoryAdapter.text = transaction.kategoria
            textViewPlaceAdapter.text = transaction.miejsce

            if(transaction.czyPrzychod){
                textViewTransactionAdapter.text = "Dochód"
                textViewTransactionAdapter.setTextColor(Color.GREEN)
            }else{
                textViewTransactionAdapter.text = "Wydatek"
                textViewTransactionAdapter.setTextColor(Color.RED)

            }

            if(transaction.czyPrzychod) {
                textViewPriceAdapter.text = transaction.kwota.toString() + " zł"
                textViewPriceAdapter.setTextColor(Color.GREEN)
            }else{
                textViewPriceAdapter.text = transaction.kwota.toString()+ " zł"
                textViewPriceAdapter.setTextColor(Color.RED)
            }

        }
    }
}


class TransactionAdapter() : RecyclerView.Adapter<TransactionItem>() {
    var transactions: List<Transaction> = emptyList()
        set(value){
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionItem {
        val binding = ItemTransactionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
        )
        return TransactionItem(binding)
    }

    override fun onBindViewHolder(holder: TransactionItem, position: Int) {
        holder.bind(transactions[position])
    }

    override fun getItemCount(): Int =transactions.size
}