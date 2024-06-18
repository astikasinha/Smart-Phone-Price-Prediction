import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var etBrand: EditText
    private lateinit var etModel: EditText
    private lateinit var etRAM: EditText
    private lateinit var etStorage: EditText
    private lateinit var etColor: EditText
    private lateinit var etFree: EditText
    private lateinit var btnPredict: Button
    private lateinit var tvPrediction: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etBrand = findViewById(R.id.etBrand)
        etModel = findViewById(R.id.etModel)
        etRAM = findViewById(R.id.etRAM)
        etStorage = findViewById(R.id.etStorage)
        etColor = findViewById(R.id.etColor)
        etFree = findViewById(R.id.etFree)
        btnPredict = findViewById(R.id.btnPredict)
        tvPrediction = findViewById(R.id.tvPrediction)

        btnPredict.setOnClickListener {
            val brand = etBrand.text.toString()
            val model = etModel.text.toString()
            val ram = etRAM.text.toString().toDoubleOrNull() ?: 0.0
            val storage = etStorage.text.toString().toDoubleOrNull() ?: 0.0
            val color = etColor.text.toString()
            val free = etFree.text.toString()

            val smartphone = Smartphone(brand, model, ram, storage, color, free)
            getPrediction(smartphone)
        }
    }

    private fun getPrediction(smartphone: Smartphone) {
        RetrofitInstance.api.getPrediction(smartphone).enqueue(object : Callback<PredictionResponse> {
            override fun onResponse(call: Call<PredictionResponse>, response: Response<PredictionResponse>) {
                if (response.isSuccessful) {
                    val prediction = response.body()?.predicted_price
                    tvPrediction.text = "Predicted Price: $prediction"
                    tvPrediction.visibility = View.VISIBLE
                } else {
                    Toast.makeText(this@MainActivity, "Failed to get prediction", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<PredictionResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
