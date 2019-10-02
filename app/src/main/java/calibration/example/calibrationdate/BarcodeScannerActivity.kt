package calibration.example.calibrationdate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.example.calibrationdate.R
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView
import kotlin.Exception

class BarcodeScannerActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {

    private var mScannerView : ZXingScannerView? = null
    lateinit var tulisanTVNew : TextView
    lateinit var msgTV : TextView

    private lateinit var asal : String
    private lateinit var mc : String
    private lateinit var id : String

    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_barcode_scanner)

            mScannerView = findViewById<ZXingScannerView>(R.id.scanner)
            tulisanTVNew = findViewById<TextView>(R.id.tulisanTVNew)
            msgTV = findViewById<TextView>(R.id.msgTV)

            asal = getIntent().getStringExtra("asal")
            mc = getIntent().getStringExtra("mc")
            id = getIntent().getStringExtra("id")

            if (asal == "mesin") {
                tulisanTVNew.text = "Scan QR Code pada Mesin untuk mengganti setting"
            }

            if (asal == "mp") {
                tulisanTVNew.text = "Scan QR Code pada ID Card untuk mengganti setting"
            }
        } catch (ex:Exception){
            Toast.makeText(this@BarcodeScannerActivity, "$ex", Toast.LENGTH_LONG).show()

        }
    }

    public override fun onResume() {
        super.onResume()
        mScannerView!!.setResultHandler(this)
        mScannerView!!.startCamera()
    }

    public override fun onPause() {
        super.onPause()
        mScannerView!!.stopCamera()
    }

    override fun onStop() {
        finish()
        super.onStop()
    }

    override fun handleResult (rawResult: Result) {
        Log.v("TAG", rawResult.getText())
        Log.v("TAG", rawResult.getBarcodeFormat().toString())

        if((asal=="mesin")&&rawResult.getText().contains("MC", false)){
            val launch = Intent()
            launch.putExtra("mesin",rawResult.getText())
            setResult(RESULT_OK,launch)
            finish()
        } else if ((asal == "mesin")&&rawResult.getText().contains("ID",false)){
            msgTV.text = "QR Code yang Anda Scan salah. Silahkan Scan QR Code pada Mesin "
            mScannerView!!.resumeCameraPreview(this)
        } else if ((asal == "mp")&&rawResult.getText().contains("ID",false)) {
            val dataidcard= rawResult.getText().substringAfter("FN").substringBefore("\n")
            val launch = Intent()
            launch.putExtra("id",dataidcard)
            setResult(RESULT_OK,launch)
            finish()
        } else if ((asal == "mp")&&rawResult.getText().contains("MC",false)){
            msgTV.text = "QR Code yang Anda Scan salah. Silahkan Scan QR Code pada Mesin "
            mScannerView!!.resumeCameraPreview(this)
        } else {
            msgTV.text = "QR Code yang Anda Scan salah. Silahkan Scan QR Code pada Mesin atau ID Card "
            mScannerView!!.resumeCameraPreview(this)
        }
    }

}
