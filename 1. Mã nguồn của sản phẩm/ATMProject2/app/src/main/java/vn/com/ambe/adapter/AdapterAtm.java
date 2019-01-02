package vn.com.ambe.adapter;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import vn.com.ambe.atmproject2.R;
import vn.com.ambe.model.ATM;

/**
 * Created by duong on 15/03/2017.
 */

public class AdapterAtm extends ArrayAdapter<ATM>{

    private Activity context;
    private int resource;
    private List<ATM> objects;

    public AdapterAtm(@NonNull Activity context, @LayoutRes int resource, @NonNull List<ATM> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater=this.context.getLayoutInflater();
        convertView=inflater.inflate(this.resource,null);

        ImageView imgHinh= (ImageView) convertView.findViewById(R.id.img_hinh);
        TextView txtTenNganHang= (TextView) convertView.findViewById(R.id.txt_ten_ngan_hang);
        TextView txtDiaChi= (TextView) convertView.findViewById(R.id.txt_dia_chi);

        ATM atm=this.objects.get(position);

        imgHinh.setImageBitmap(atm.getHinhMinhHoa());
        txtTenNganHang.setText(atm.getTenNganHang());

        String diaChi=atm.getSoNha()+", "+atm.getTenPhuong()+", "+atm.getTenQuan();
        txtDiaChi.setText(diaChi);
        return convertView;
    }
}
