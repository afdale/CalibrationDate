package calibration.example.calibrationdate

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.calibrationdate.R
import java.sql.ResultSet
import java.sql.Statement
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    lateinit var connectionClass: connectionClass
    lateinit var searchBar: SearchView

    private var listCD = ArrayList<CDContainer>()
    lateinit var listCDAdapter : ListCDAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        try {
            connectionClass = connectionClass()
            searchBar = findViewById<SearchView>(R.id.searchBar) as SearchView

            val cdListView = findViewById<ListView>(R.id.cdlv)
            listCDAdapter = ListCDAdapter (this, listCD)

            cdListView.adapter = listCDAdapter
            cdListView.setOnItemClickListener {parent, view, position, id ->
                val launch2 = Intent (this, EditCDActivity::class.java)
                launch2.putExtra("asal", "main")
                launch2.putExtra("mc", listCD[position].mMc)
                launch2.putExtra("id", listCD[position].mID)
                launch2.putExtra("key", listCD[position].mKey)
                launch2.putExtra("lokasi", listCD[position].mLokasi)
                launch2.putExtra("tgl_kalibrasi", listCD[position].mTgl_kalibrasi)
                launch2.putExtra("hasil", listCD[position].mHasil)
                launch2.putExtra("masaberlaku", listCD[position].mMasaberlaku)
                launch2.putExtra("next_kalibrasi", listCD[position].mNext_kalibrasi)
                startActivity(launch2)
            }


            searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    val text = newText!!.toLowerCase()
                    listCDAdapter.filter(text)
                    return false
                }
            })
        } catch (ex: Exception){
            Toast.makeText(this@MainActivity, "$ex", Toast.LENGTH_LONG).show()
        }
    }

    override fun onResume() {
        super.onResume()
        DoReadDatabase(this).execute()
        listCDAdapter.filter("")
    }

    fun goAddBtn (view: View) {
        val launch2 = Intent(this, AddCD::class.java)
        startActivity(launch2)
    }

    inner class DoReadDatabase (var activity: MainActivity) : AsyncTask<String,String,String>(){
        var dialog = Dialog(activity,android.R.style.Theme_Translucent_NoTitleBar)

        private var z = ""
        internal var isSuccess = false

        override fun onPreExecute() {
            listCD.clear()
            val view = activity.layoutInflater.inflate(R.layout.progress,null)
            dialog.setContentView(view)
            dialog.setCancelable(false)
            dialog.show()

        }

        override fun doInBackground(vararg params: String?): String {
            try{
                val con = connectionClass.CONN()
                if (con==null){
                    z = "Please check your internet connection"
                } else {
                    val query = "SELECT * FROM calibrationdate"
                    val stmt:Statement = con.createStatement()
                    val rs:ResultSet = stmt.executeQuery(query)

                    if(rs!=null){
                        while (rs.next()){
                            try {
                                listCD.add(
                                    CDContainer(
                                        rs.getLong("Kunci"),
                                        rs.getString("Mesin"),
                                        rs.getString("ID"),
                                        rs.getString("Lokasi"),
                                        rs.getString("Tgl_kalibrasi"),
                                        rs.getString("Hasil"),
                                        rs.getString("Masaberlaku"),
                                        rs.getString("Next_kalibrasi")
                                    )
                                )
                            } catch (ex:Exception){

                            }
                        }
                        z = "Read Successfull"
                        isSuccess = true
                    } else {
                        z = "No Data Found"
                        isSuccess = false
                    }

                }
            } catch (ex:Exception){

                isSuccess = false
                z = "ERROR : $ex"

            }
            return z
        }

        override fun onPostExecute(s: String?) {
            listCDAdapter.notifyDataSetChanged()
            dialog.dismiss()
            Toast.makeText(this@MainActivity,""+z,Toast.LENGTH_LONG).show()

            if (isSuccess){
                Toast.makeText(this@MainActivity,""+z,Toast.LENGTH_LONG).show()
            }
        }
    }

    inner class ListCDAdapter : BaseAdapter {
        private var listProblemAndon = ArrayList<CDContainer>()
        private var context: Context? = null
        private var filterList = ArrayList<CDContainer>()

        constructor(context: Context,filterList: ArrayList<CDContainer>): super (){
            this.context = context
            this.filterList = filterList
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view: View
            val vh : ViewHolder

            if (convertView==null){
                view = layoutInflater.inflate(R.layout.cd_adapter,parent,false)
                vh = ViewHolder(view)
                view.tag = vh
                Log.i("PC","set tag for ViewHolder, position: "+ position)
            }else {
                view = convertView
                vh = view.tag as ViewHolder
            }

            vh.mcTV.text = listProblemAndon[position].mMc
            vh.namaTV.text = listProblemAndon[position].mID

            return view
        }

        override fun getItem(position: Int): Any  {
            return listProblemAndon[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return listProblemAndon.size
        }

        fun filter (text: String?){
            val kata = text!!.toUpperCase(Locale.getDefault())
            listProblemAndon.clear()

            if(kata.length == 0){
                for (i in 0..filterList.size - 1) {
                    listProblemAndon.add(filterList[1])
                }
            } else {
                for(i in 0..filterList.size - 1){
                    Log.i("cari","cari: ${filterList[i].getMc().toString()}")
                    if(filterList[i].getMc()!!.contains(kata)){
                        listProblemAndon.add(filterList[i])
                    }
                }

            }
            notifyDataSetChanged()
        }
    }

    private class ViewHolder(view: View?){
        val mcTV : TextView
        val namaTV : TextView

        init {
            this.mcTV = view?.findViewById<TextView>(R.id.mcTV) as TextView
            this.namaTV = view.findViewById<TextView>(R.id.namaTV) as TextView
        }
    }




}
