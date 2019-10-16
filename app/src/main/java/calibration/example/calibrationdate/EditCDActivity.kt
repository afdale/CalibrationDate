package calibration.example.calibrationdate

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.calibrationdate.R
import kotlinx.android.synthetic.main.activity_edit_cd.*
import java.sql.Statement
import java.util.*
import kotlin.Exception
import android.app.DatePickerDialog
import android.widget.DatePicker
import java.text.SimpleDateFormat
import android.widget.RadioButton
import android.widget.RadioGroup

class EditCDActivity : AppCompatActivity() {

    lateinit var connectionClass: connectionClass
    lateinit var asal : String
    var myMC : String = "N/A"
    var myID : String = "N/A"
    var myLokasi : String? = ""
    var myHasil : String? = ""
    var myMBerlaku : String? = ""
    var myKal1 : String? = ""
    var myKal2 : String? = ""
    var myKey : Long? = null
    var clicked: String = ""

    lateinit var radio_group: RadioGroup
    lateinit var radioOK: RadioButton
    lateinit var radioNG: RadioButton

    lateinit var mcTVedit : TextView
    lateinit var idTVedit : TextView
    lateinit var lokasiTVedit : EditText
    lateinit var tahun : TextView
    lateinit var mberlaku : EditText

    var kalibrasi1 : TextView? = null
    var kalibrasi2 : TextView? = null
    val cal = Calendar.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_cd)

        radio_group = findViewById<RadioGroup>(R.id.radio_group) as RadioGroup
        radioOK = findViewById<RadioButton>(R.id.radioOK) as RadioButton
        radioNG = findViewById<RadioButton>(R.id.radioNG) as RadioButton

        connectionClass = connectionClass()
        mcTVedit = findViewById<TextView>(R.id.mcTVedit)
        idTVedit = findViewById<TextView>(R.id.idTVedit)
        lokasiTVedit = findViewById<EditText>(R.id.lokasiTVedit)
        tahun = findViewById<TextView>(R.id.tahun)
        mberlaku = findViewById<EditText>(R.id.mberlaku)

        mcTVedit.text = myMC
        idTVedit.text = myID
        lokasiTVedit.setText(myLokasi)
        mberlaku.setText(myMBerlaku)
        myKal1 = kalibrasi1.toString()
        myKal2 = kalibrasi2.toString()

        kalibrasi1 = this.kalibrasi
        kalibrasi1!!.text = "yyyy-mm-dd"

        kalibrasi2 = this.kalibrasinext
        kalibrasi2!!.text = "yyyy-mm-dd"

        radio_group.setOnCheckedChangeListener{compoundButton, b ->
            try {
                if (R.id.radioOK==b){
                    val teks : String = "Hasil Kalibrasi OK"
                    clicked = teks.toString()

                    Toast.makeText(this,"$clicked", Toast.LENGTH_LONG).show()
                }
                else if (R.id.radioNG==b){
                    val teks : String = "Hasil Kalibrasi NG"
                    clicked = teks.toString()

                    Toast.makeText(this,"$clicked", Toast.LENGTH_LONG).show()
                }
            } catch (ex:Exception){
                Toast.makeText(this,"$ex",Toast.LENGTH_LONG).show()
            }
        }

        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
        }

        val dateSetListener2 = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView2()
            }
        }
// TANGGAL KALIBRASI
        kalibrasi1!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(
                    this@EditCDActivity,
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
        })
// NEXT KALIBRASI
        kalibrasi2!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(
                    this@EditCDActivity,
                    dateSetListener2,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
        })

    }

    private fun updateDateInView() {
        val myFormat = "yyyy-MM-dd" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        kalibrasi1!!.text = sdf.format(cal.getTime())
    }

    private fun updateDateInView2() {
        val myFormat = "yyyy-MM-dd" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        kalibrasi2!!.text = sdf.format(cal.getTime())
    }

    override fun onResume() {
        super.onResume()
        mcTVedit.text = myMC
        idTVedit.text = myID
        myKal1 = kalibrasi1.toString()
        myKal2 = kalibrasi2.toString()
        lokasiTVedit.setText(myLokasi)
        mberlaku.setText(myMBerlaku)

        Toast.makeText(this, "$myKey", Toast.LENGTH_LONG).show()
    }

    fun goCancel (view: View){
        finish()
    }

    fun goUpload (view: View){

        myLokasi = lokasiTVedit.text.toString()
        myKal1 = kalibrasi1!!.text.toString()
        myKal2 = kalibrasi2!!.text.toString()
        myHasil = radio_group.toString()
        myMBerlaku = mberlaku.text.toString()


        DoUpdateDataCD(this).execute()
    }

    fun goDelete (view: View){
        DoDeleteDataCD(this).execute()
    }

    fun goMesin (view: View){
        asal = "mesin"
        DoResult (1)
    }

    fun goMP (view: View){
        asal = "mp"
        DoResult(2)
    }

    fun DoResult (code:Int){
        val launch = Intent (this, BarcodeScannerActivity::class.java)
        launch.putExtra("asal",asal)
        launch.putExtra("mc",myMC)
        launch.putExtra("id",myID)
        try {
            startActivityForResult(launch,code)
        } catch (ex:Exception){
            Toast.makeText(this,"$ex", Toast.LENGTH_LONG).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==1){
            if(resultCode== Activity.RESULT_OK){
                myMC = data!!.getStringExtra("mesin")
            }
        }
        if(requestCode==2){
            if(resultCode== Activity.RESULT_OK){
                myID = data!!.getStringExtra("id")
            }
        }
    }

    inner class DoUpdateDataCD(var activity: EditCDActivity) : AsyncTask<String,String,String>() {
        var dialog = Dialog(activity, android.R.style.Theme_Translucent_NoTitleBar)

        private var z = ""
        internal var isSuccess = false

        override fun onPreExecute() {
            val view = activity.layoutInflater.inflate(R.layout.progress,null)
            dialog.setContentView(view)
            dialog.setCancelable(false)
            dialog.show()
        }

        override fun doInBackground(vararg params: String?): String {
            try {
                val con = connectionClass.CONN()
                if (con == null) {
                    z = "Please check your internet connection"
                } else {
                    val query ="UPDATE calibrationdate" +
                            "SET Mesin='$myMC', ID='$myID', Lokasi='$myLokasi', Masaberlaku='$myMBerlaku', " +
                            "Kalibrasi1='$myKal1', Hasil='$clicked',  Kalibrasi2='$myKal2' WHERE Kunci=$myKey"
                    val stmt: Statement = con.createStatement()
                    stmt.executeUpdate(query)
                    z = "Update Successfull"
                    isSuccess = true
                }
            } catch (ex: Exception) {
                isSuccess = false
                z = "ERROR : $ex"
            }
            return z
        }

        override fun onPostExecute(s: String?) {
            dialog.dismiss()
            Toast.makeText(this@EditCDActivity, "" + z, Toast.LENGTH_LONG).show()
            if (isSuccess) {
                Toast.makeText(this@EditCDActivity, "" + z, Toast.LENGTH_LONG).show()
                this@EditCDActivity.finish()
            }
        }
    }

    inner class DoDeleteDataCD (var activity: EditCDActivity) : AsyncTask <String, String, String>(){
        var dialog = Dialog(activity, android.R.style.Theme_Translucent_NoTitleBar)

        private var z = ""
        internal var isSuccess = false

        override fun onPreExecute() {
            val view = activity.layoutInflater.inflate(R.layout.progress,null)
            dialog.setContentView(view)
            dialog.setCancelable(false)
            dialog.show()
        }

        override fun doInBackground(vararg params: String): String {
            try {
                val con = connectionClass.CONN()
                if (con == null) {
                    z = "Please check your internet connection"
                    //listMP.add(CDContainer("TEST","TEST"))
                } else {
                    val query = "DELETE FROM calibrationdate WHERE Kunci=$myKey"
                    val stmt: Statement = con.createStatement()
                    stmt.executeUpdate(query)
                    z = "Delete Successfull"
                    isSuccess = true
                }
            } catch (ex: Exception) {
                isSuccess = false
                z = "ERROR : $ex"
            }
            return z
        }

        override fun onPostExecute(s: String) {
            //setDialog(false)

            dialog.dismiss()
            Toast.makeText(this@EditCDActivity, "" + z, Toast.LENGTH_LONG).show()

            if(isSuccess)
            {
                Toast.makeText(this@EditCDActivity, "" + z, Toast.LENGTH_LONG).show()
                this@EditCDActivity.finish()
            }

        }
    }
}
