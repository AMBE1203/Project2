package vn.com.ambe.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import vn.com.ambe.adapter.AdapterAtm;
import vn.com.ambe.atmproject2.MainActivity;
import vn.com.ambe.atmproject2.R;
import vn.com.ambe.controller.CtlAtm;
import vn.com.ambe.model.ATM;

/**
 * Created by duong on 15/03/2017.
 */

public class SearchFragment extends Fragment {

    private View view;

    private AutoCompleteTextView autoQuan;
    private Spinner sprNganHang;
    private Button btnTimKiem;
    private ListView listAtm;

    private String[] arrQuan;
    private String[] arrNganHang;

    private ArrayAdapter<String> adapterQuan;
    private ArrayAdapter<String> adapterNganHang;

    private ATM atmSelected;

    private ArrayList<ATM> arrAtm;
    private AdapterAtm adapterAtm;

    private int index;
    private CtlAtm ctlAtm;

    private FragmentManager fr;


    public SearchFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_search,container,false);
        ((MainActivity)getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.search));

        addControls();
        addEvents();
        return view;

    }

    private void addEvents() {

        btnTimKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyTimKiem();
            }
        });



        listAtm.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                atmSelected=arrAtm.get(position);
                xuLyChonDS();
            }
        });
        sprNganHang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                index=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }


    private void xuLyTimKiem() {

        String tenQuan=autoQuan.getText().toString();
        String tenNganHang=sprNganHang.getItemAtPosition(index).toString();
        ctlAtm=new CtlAtm();
        if (!tenQuan.equals("")){
            arrAtm=new ArrayList<ATM>();
            arrAtm=ctlAtm.getListAtmTheoQuan(MainActivity.database,tenQuan,tenNganHang);
            adapterAtm=new AdapterAtm(
                    getActivity(),
                    R.layout.layout_custom_atm,
                    arrAtm
            );

            listAtm.setAdapter(adapterAtm);
            adapterAtm.notifyDataSetChanged();
        } else {
            Toast.makeText(getActivity(), getResources().getString(R.string.loi_chua_nhap), Toast.LENGTH_SHORT).show();
        }

    }

    private void addControls() {

        autoQuan= (AutoCompleteTextView) view.findViewById(R.id.auto_quan);
        sprNganHang= (Spinner) view.findViewById(R.id.spr_ngan_hang);
        btnTimKiem= (Button) view.findViewById(R.id.btn_tim_kiem);
        listAtm= (ListView) view.findViewById(R.id.list_atm);



        arrQuan=getResources().getStringArray(R.array.arrQuan);
        adapterQuan=new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                arrQuan
        );
        autoQuan.setAdapter(adapterQuan);

        arrNganHang=getResources().getStringArray(R.array.arrNganHang);
        adapterNganHang=new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                arrNganHang
        );
        adapterNganHang.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sprNganHang.setAdapter(adapterNganHang);


    }

    private void xuLyChonDS() {

       Bundle bundle = new Bundle();
       bundle.putSerializable("ATM", atmSelected);

       HomeFragment homeFragment = new HomeFragment();
       fr = getFragmentManager();
       homeFragment.setArguments(bundle);
       fr.beginTransaction().replace(R.id.content, homeFragment).addToBackStack(getResources().getString(R.string.search)).commit();

   }

}
