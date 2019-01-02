package vn.com.ambe.controller;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;

import vn.com.ambe.model.ATM;

/**
 * Created by duong on 15/03/2017.
 */

public class CtlAtm {

    public CtlAtm() {
    }

    public ArrayList<ATM> getListAtm(SQLiteDatabase database){
        ArrayList<ATM> list=new ArrayList<ATM>();

        String sql="select * from ATM";
        Cursor cursor=database.rawQuery(sql,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            ATM atm=new ATM();
            atm.setId(cursor.getInt(0));
            atm.setTenNganHang(cursor.getString(1));
            byte[] bytes=cursor.getBlob(2);
            Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
            atm.setHinhMinhHoa(bitmap);
            atm.setSoNha(cursor.getString(3));
            atm.setTenPhuong(cursor.getString(4));
            atm.setTenQuan(cursor.getString(5));
            atm.setViDo(cursor.getDouble(6));
            atm.setKinhDo(cursor.getDouble(7));

            list.add(atm);
            cursor.moveToNext();
        }

        cursor.close();

        return list;

    }


    public String getAdress(SQLiteDatabase database,double viDo,double kinhDo){
        String diaChi="";

        String sql="select tenNganHang,soNha,tenPhuong,tenQuan from ATM where viDo = '"+viDo +"' and kinhdo = '"+ kinhDo+"'";
        Cursor cursor=database.rawQuery(sql,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            String tenNganHang=cursor.getString(0);
            String soNha=cursor.getString(1);
            String tenPhuong=cursor.getString(2);
            String tenQuan=cursor.getString(3);

            diaChi="ATM "+tenNganHang +", Số " +soNha+", "+tenPhuong+", "+tenQuan+", Hà Nội, Việt Nam.";
            cursor.moveToNext();
        }

        cursor.close();

        return diaChi;
    }


    public ArrayList<ATM> getListAtmTheoQuan(SQLiteDatabase database,String tenQuan,String tenNganHang) {
        ArrayList<ATM> list = new ArrayList<ATM>();

        String sql = "select * from ATM where tenNganHang = '" +tenNganHang +"' and tenQuan = '"+ tenQuan+"'";
        Cursor cursor = database.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ATM atm = new ATM();
            atm.setId(cursor.getInt(0));
            atm.setTenNganHang(cursor.getString(1));
            byte[] bytes = cursor.getBlob(2);
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            atm.setHinhMinhHoa(bitmap);
            atm.setSoNha(cursor.getString(3));
            atm.setTenPhuong(cursor.getString(4));
            atm.setTenQuan(cursor.getString(5));
            atm.setViDo(cursor.getDouble(6));
            atm.setKinhDo(cursor.getDouble(7));

            list.add(atm);
            cursor.moveToNext();
        }


        cursor.close();

        return list;
    }

    public ArrayList<ATM> getListAtmTheoNganHang(SQLiteDatabase database,ArrayList<String> tenNganHang){
        ArrayList<ATM> list=new ArrayList<ATM>();
        for (String str : tenNganHang){
            String sql="select * from ATM where tenNganHang='"+str+"'";
            Cursor cursor = database.rawQuery(sql, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                ATM atm = new ATM();
                atm.setId(cursor.getInt(0));
                atm.setTenNganHang(cursor.getString(1));
                byte[] bytes = cursor.getBlob(2);
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                atm.setHinhMinhHoa(bitmap);
                atm.setSoNha(cursor.getString(3));
                atm.setTenPhuong(cursor.getString(4));
                atm.setTenQuan(cursor.getString(5));
                atm.setViDo(cursor.getDouble(6));
                atm.setKinhDo(cursor.getDouble(7));
                list.add(atm);
                cursor.moveToNext();
            }

            cursor.close();

        }
        return list;
    }
}
