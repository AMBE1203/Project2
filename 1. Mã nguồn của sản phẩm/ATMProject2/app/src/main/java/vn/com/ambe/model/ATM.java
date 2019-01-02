package vn.com.ambe.model;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by duong on 15/03/2017.
 */

public class ATM implements Serializable {

    private int id;
    private String tenNganHang;
    private Bitmap hinhMinhHoa;
    private String soNha;
    private String tenPhuong;
    private String tenQuan;
    private double viDo;
    private double kinhDo;


    public ATM(int id, String tenNganHang, Bitmap hinhMinhHoa, String soNha, String tenPhuong, String tenQuan, double viDo, double kinhDo) {
        this.id = id;
        this.tenNganHang = tenNganHang;
        this.hinhMinhHoa = hinhMinhHoa;
        this.soNha = soNha;
        this.tenPhuong = tenPhuong;
        this.tenQuan = tenQuan;
        this.viDo = viDo;
        this.kinhDo = kinhDo;
    }

    public ATM() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenNganHang() {
        return tenNganHang;
    }

    public void setTenNganHang(String tenNganHang) {
        this.tenNganHang = tenNganHang;
    }

    public Bitmap getHinhMinhHoa() {
        return hinhMinhHoa;
    }

    public void setHinhMinhHoa(Bitmap hinhMinhHoa) {
        this.hinhMinhHoa = hinhMinhHoa;
    }

    public String getSoNha() {
        return soNha;
    }

    public void setSoNha(String soNha) {
        this.soNha = soNha;
    }

    public String getTenPhuong() {
        return tenPhuong;
    }

    public void setTenPhuong(String tenPhuong) {
        this.tenPhuong = tenPhuong;
    }

    public String getTenQuan() {
        return tenQuan;
    }

    public void setTenQuan(String tenQuan) {
        this.tenQuan = tenQuan;
    }

    public double getViDo() {
        return viDo;
    }

    public void setViDo(double viDo) {
        this.viDo = viDo;
    }

    public double getKinhDo() {
        return kinhDo;
    }

    public void setKinhDo(double kinhDo) {
        this.kinhDo = kinhDo;
    }
}
