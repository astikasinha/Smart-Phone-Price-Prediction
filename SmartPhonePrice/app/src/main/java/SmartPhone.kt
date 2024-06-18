data class SmartPhone(
    val Brand: String,
    val Model: String,
    val RAM: Double,
    val Storage: Double,
    val Color: String,
    val Free: String
)

data class PredictionResponse(
    val predicted_price: Double
)
