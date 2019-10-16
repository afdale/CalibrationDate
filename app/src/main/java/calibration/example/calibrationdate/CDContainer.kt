package calibration.example.calibrationdate

import java.util.*

class CDContainer {

    var mMc : String? = null
    var mID : String? = null
    var mKey : Long? = null
    var mLokasi : String? = null
    var mKal1 : String? = null
    var mHasil : String? = null
    var mMasaberlaku : String? = null
    var mKal2 : String? = null

    constructor(key:Long?, mc: String?, id:String?, lokasi:String?,
                kal1: String?, hasil: String?, masaberlaku: String?, kal2: String?){
        this.mMc = mc
        this.mID = id
        this.mKey = key
        this.mLokasi = lokasi
        this.mKal1 = kal1
        this.mHasil = hasil
        this.mMasaberlaku = masaberlaku
        this.mKal2 = kal2
    }

    fun getKey() : Long?{
        return mKey
    }

    fun setKey (key: Long?){
        mKey = key
    }

    fun getID () : String? {
        return mID
    }

    fun  setID (id : String?){
        mID = id
    }

    fun  getMc() : String? {
        return mMc
    }

    fun  setMc (mc: String?){
        mMc = mc
    }

    fun  getLokasi() : String? {
        return mLokasi
    }

    fun  setLokasi (lokasi: String?){
        mLokasi = lokasi
    }

    fun  getTgl_kalibrasi() : String? {
        return mKal1
    }

    fun  setTgl_kalibrasi (tgl_kalibrasi: String?){
        mKal1 = tgl_kalibrasi
    }

    fun  getHasil() : String? {
        return mHasil
    }

    fun  setHasil (hasil: String?){
        mHasil = hasil
    }

    fun  getMasaberlaku() : String? {
        return mMasaberlaku
    }

    fun  setMasaberlaku (masaberlaku: String?){
        mMasaberlaku = masaberlaku
    }

    fun  getNext_kalibrasi() : String? {
        return mKal2
    }

    fun  setNext_kalibrasi (next_kalibrasi: String?){
        mKal2 = next_kalibrasi
    }
}