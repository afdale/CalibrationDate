package calibration.example.calibrationdate;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;

public class connectionClass {
    String IP ="10.14.119.6";
    String PORT ="3306";
    String DB ="testing";
    String USER ="test";
    String PASSWORD ="test";

    //mysql
    String classs = "com.mysql.jdbc.Driver";
    String url = "jdbc:mysql://"+IP+":"+PORT+"/"+DB+"?user="+USER+"&password="+PASSWORD;
    //String url = "jdbc:mysql://192.168.56.1/android";
    //String un = "ride";
    //String password = "ride";


    //sqlserver
    /*String classs ="net.sourceforge.jtds.jdbcDriver";
    String url = "jdbc:jtds:sqlserver://"+IP+":"+PORT+";"+"databaseName="+DB+";user="+USER+";password="+PASSWORD+";";
    */

    @SuppressLint("NewAPI")
    public Connection CONN() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;
        String ConnURL = null;
        try {
            Class.forName(classs);
            conn = DriverManager.getConnection(url);


            conn = DriverManager.getConnection(ConnURL);

        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
        }
        return conn;
    }
}

