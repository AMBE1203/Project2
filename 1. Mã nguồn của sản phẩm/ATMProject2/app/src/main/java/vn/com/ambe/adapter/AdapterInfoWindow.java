package vn.com.ambe.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import vn.com.ambe.atmproject2.R;
import vn.com.ambe.model.ATM;

/**
 * Created by duong on 08/04/2017.
 */

public class AdapterInfoWindow implements GoogleMap.InfoWindowAdapter {
    private Activity context;
    private ATM atm;

    public AdapterInfoWindow(Activity context,ATM atm){
        this.context=context;
        this.atm=atm;
    }
    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {

        LayoutInflater inflater=this.context.getLayoutInflater();
        View view=inflater.inflate(R.layout.layout_custom_atm,null);

        ImageView imgHinh= (ImageView) view.findViewById(R.id.img_hinh);
        TextView txtTenNganHang= (TextView) view.findViewById(R.id.txt_ten_ngan_hang);
        TextView txtDiaChi= (TextView) view.findViewById(R.id.txt_dia_chi);


        imgHinh.setImageBitmap(atm.getHinhMinhHoa());
        txtTenNganHang.setText(atm.getTenNganHang());

        String diaChi=atm.getSoNha()+", "+atm.getTenPhuong()+", "+atm.getTenQuan();
        txtDiaChi.setText(diaChi);
        return view;

    }
}
