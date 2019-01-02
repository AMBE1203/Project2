package vn.com.ambe.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.BoolRes;
import android.support.annotation.Nullable;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import vn.com.ambe.atmproject2.MainActivity;
import vn.com.ambe.atmproject2.R;

/**
 * Created by duong on 15/03/2017.
 */

public class SettingFragment extends Fragment {

    private SparseBooleanArray t;
    private View view;
    private CheckBox chkChonTatCa;
    private ListView listNganHang;
    private Spinner sprKieuDuong;
    private Button btnOk;
    private String mode="";
    private Boolean checked=false;
    private ArrayList<Integer> list=new ArrayList<>();

    private String[] arrNganHang;
    private ArrayAdapter<String> adapterNganHang;
    private ArrayAdapter<String> adapterKieuDuong;

    private ArrayList<String> selectedItems=new ArrayList<String>();

    private String[] arrKieuDuong;
    private int kieuDuong;

    public static String pre="pre";
    private int kt;

    public SettingFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_setting,container,false);
        ((MainActivity)getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.setting));
        addControls();
 //       getMyFile();
        addEvents();
        return view;
    }

    public void getMyFile() {
        String sdCard= Environment.getExternalStorageDirectory().getAbsolutePath()+"/myfile.txt";
        try {
            File file=new File(sdCard);
            FileInputStream fileInputStream=new FileInputStream(file);
            BufferedReader bf=new BufferedReader(new InputStreamReader(fileInputStream));
            StringBuilder sb=new StringBuilder();
            String line=bf.readLine();
            while (line!= null){
                sb.append(line);
                line=bf.readLine();
            }
            bf.close();
            fileInputStream.close();
            String x=sb.toString();
            ArrayList<String> list=new ArrayList<String>();
            list.addAll(Arrays.asList(x.split(",")));
            for (int i=0;i<list.size();++i){
                if (list.get(i).equals("all")){
                    chkChonTatCa.setChecked(true);
                    for (int j=0;j<adapterNganHang.getCount();j++){
                        listNganHang.setItemChecked(i,true);
                    }
                }
                if (list.get(i).equals("0")){
                    sprKieuDuong.setSelection(0);
                }
                if (list.get(i).equals("1")){
                    sprKieuDuong.setSelection(1);
                }
            }
            int count = this.listNganHang.getAdapter().getCount();

            for (int i = 0; i < count; i++) {
                String currentItem = (String) this.listNganHang.getAdapter()
                        .getItem(i);
                if (this.list.contains(currentItem)) {
                    this.listNganHang.setItemChecked(i, true);
                }

            }
            Toast.makeText(getActivity(), sb.toString(), Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void addEvents() {


        listNganHang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listNganHang.isItemChecked(position)==false){
                    chkChonTatCa.setChecked(false);
                }
            }
        });

        chkChonTatCa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                for (int i=0;i<adapterNganHang.getCount();++i){
                    listNganHang.setItemChecked(i,isChecked);

                }

            }
        });

        sprKieuDuong.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mode=arrKieuDuong[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyOK();
            }
        });



    }

    private void xuLyOK() {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle(getResources().getString(R.string.thong_bao));
        builder.setMessage(getResources().getString(R.string.luu_thay_doi));
        builder.setNegativeButton(R.string.dong_y, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                checked=true;


                Toast.makeText(getActivity(), getResources().getString(R.string.thanh_cong), Toast.LENGTH_SHORT).show();
                String sdCard= Environment.getExternalStorageDirectory().getAbsolutePath()+"/myfile.txt";
                try {
                    OutputStreamWriter writer=
                            new OutputStreamWriter(
                                    new FileOutputStream(sdCard));
                    if (chkChonTatCa.isChecked()){
                        writer.write("all");
                        writer.write(",");
                    } else {
                        writer.write(getSavedItems());
                        writer.write(",");
                    }
                    int x=sprKieuDuong.getSelectedItemPosition();
                    if (x==0){
                        writer.write("0");
                    } else if (x==1){
                        writer.write("1");
                    }

                    writer.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        builder.setPositiveButton(R.string.thoat, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.create().show();
    }

    private void addControls() {
        chkChonTatCa= (CheckBox) view.findViewById(R.id.chk_chon_tat);
        listNganHang= (ListView) view.findViewById(R.id.list_ngan_hang);
        sprKieuDuong= (Spinner) view.findViewById(R.id.spr_kieu_duong);
        btnOk= (Button) view.findViewById(R.id.btn_ok);
        arrNganHang=getResources().getStringArray(R.array.arrNganHang);
        adapterNganHang=new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_multiple_choice,
                arrNganHang
        );
        listNganHang.setAdapter(adapterNganHang);
        listNganHang.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);

        arrKieuDuong=getResources().getStringArray(R.array.arrKieuDuong);
        adapterKieuDuong=new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                arrKieuDuong
        );
        adapterKieuDuong.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sprKieuDuong.setAdapter(adapterKieuDuong);


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onPause() {
        super.onPause();
        saving();
    }

    private void saving() {
        SharedPreferences preferences=getActivity().getSharedPreferences(pre, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        boolean checkAll=chkChonTatCa.isChecked();
        String nganHang=getSavedItems();
        int index=sprKieuDuong.getSelectedItemPosition();
        if (checked==true){
            editor.putBoolean("check",checkAll);
            editor.putInt("index",index);
            editor.putString("nganhang",nganHang);

        }

        editor.commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        restore();
    }

    private void restore() {
        SharedPreferences preferences=getActivity().getSharedPreferences(pre, Context.MODE_PRIVATE);
        boolean checkAll=preferences.getBoolean("check",true);
        int index=preferences.getInt("index",-1);

        String savedItems = preferences
                .getString("nganhang", "");

        this.selectedItems.addAll(Arrays.asList(savedItems.split(",")));
        int count = this.listNganHang.getAdapter().getCount();

        for (int i = 0; i < count; i++) {
            String currentItem = (String) this.listNganHang.getAdapter()
                    .getItem(i);
            if (this.selectedItems.contains(currentItem)) {
                this.listNganHang.setItemChecked(i, true);
            }

        }
        sprKieuDuong.setSelection(index);
        kieuDuong=index;
        chkChonTatCa.setChecked(checkAll);


        }

    private String getSavedItems() {
        String savedItems = "";

        int count = this.listNganHang.getAdapter().getCount();

        for (int i = 0; i < count; i++) {

            if (this.listNganHang.isItemChecked(i)) {
                if (savedItems.length() > 0) {
                    savedItems += "," + this.listNganHang.getItemAtPosition(i);
                } else {
                    savedItems += this.listNganHang.getItemAtPosition(i);
                }
            }

        }
        return savedItems;
    }
}
