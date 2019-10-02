package calibration.example.calibrationdate

import java.util.*

class CDContainer {

    var mMc : String? = null
    var mID : String? = null
    var mKey : Long? = null
    var mLokasi : String? = null
    var mTgl_kalibrasi : String? = null
    var mHasil : String? = null
    var mMasaberlaku : String? = null
    var mNext_kalibrasi : String? = null

    constructor(key:Long?, mc: String?, id:String?, lokasi:String?, tgl_kalibrasi: String?, hasil: String?, masaberlaku: String?, next_kalibrasi: String?){
        this.mMc = mc
        this.mID = id
        this.mKey = key
        this.mLokasi = lokasi
        this.mTgl_kalibrasi = tgl_kalibrasi
        this.mHasil = hasil
        this.mMasaberlaku = masaberlaku
        this.mNext_kalibrasi = next_kalibrasi
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
        return mTgl_kalibrasi
    }

    fun  setTgl_kalibrasi (tgl_kalibrasi: String?){
        mTgl_kalibrasi = tgl_kalibrasi
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
        return mNext_kalibrasi
    }

    fun  setNext_kalibrasi (next_kalibrasi: String?){
        mNext_kalibrasi = next_kalibrasi
    }
}