package pl.krzysztofbaka.projekt.model

import java.time.LocalDate

data class Transaction(
        var miejsce: String,
        var kwota: Double,
        var data: LocalDate,
        var kategoria: String,
        var czyPrzychod: Boolean
)
