package com.almosky.almoskylaundryApp.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.almosky.almoskylaundryApp.Almosky;
import com.almosky.almoskylaundryApp.R;
import com.almosky.almoskylaundryApp.activity.AddressListActivity;
import com.almosky.almoskylaundryApp.model.Addressdto;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class AddressRecyclerViewHolders extends RecyclerView.ViewHolder {

    private TextView textTitle;
    //    UserActionCountItemBinding binding;
    public TextView addressText;
    AddressListActivity _activty;
    ConstraintLayout adrsLyt;

    public final Context ctx;
    String address, id;
    int pos;


    Addressdto.Result addressData;


    public AddressRecyclerViewHolders(View itemView, Context context, AddressListActivity activity) {
        super(itemView);
        ctx = context;
        _activty = activity;

        addressText = itemView.findViewById(R.id.textAddress);
        textTitle = itemView.findViewById(R.id.textTitle);
        adrsLyt = itemView.findViewById(R.id.lytAddress);

        textTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Almosky.getInst().setAddress(address);
                Almosky.getInst().setAddressId(addressData.getID());
                if (addressData.getOffer_Location() == 1) {
                    Almosky.getInst().setOffer(true);
                } else {
                    Almosky.getInst().setOffer(false);
                }
            }
        });

        addressText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Almosky.getInst().setAddress(address);
                Almosky.getInst().setAddressId(addressData.getID());
                if (addressData.getOffer_Location() == 1) {
                    Almosky.getInst().setOffer(true);
                } else {
                    Almosky.getInst().setOffer(false);
                }
            }
        });
        Button Edit = itemView.findViewById(R.id.btnEdit);
        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                _activty.editAddress(pos);


            }
        });
        Button Delete = itemView.findViewById(R.id.btnDelete);

        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                _activty.onDeleteAddress(id);

            }
        });

//        binding = DataBindingUtil.bind(itemView);
    }


    public void bind(final Addressdto.Result item, int position) {
        id = String.valueOf(item.getID());
        address = "";
        addressData = item;
        pos = position;

        itemView.setOnClickListener(v -> {
            _activty.onClickAddress(item.getArea());
            Almosky.getInst().setAddressId(item.getID());
        });
        addressText.setOnClickListener(v -> {
            _activty.onClickAddress(item.getArea());
            Almosky.getInst().setAddressId(item.getID());
        });
        textTitle.setOnClickListener(v -> {
            _activty.onClickAddress(item.getArea());
            Almosky.getInst().setAddressId(item.getID());
        });
        adrsLyt.setOnClickListener(v -> {
            _activty.onClickAddress(item.getArea());
            Almosky.getInst().setAddressId(item.getID());
        });


        if (null != item.getArea())
            if (!item.getArea().equalsIgnoreCase(""))
                if (address.length() > 0)
                    address = address + ", " + item.getArea();
                else
                    address = item.getArea();

        if (null != item.getStreet())
            if (!item.getStreet().equalsIgnoreCase(""))
                if (address.length() > 0)
                    address = address + ", " + item.getStreet();
                else
                    address = item.getStreet();
        if (null != item.getBlock())
            if (!item.getBlock().equalsIgnoreCase(""))
                if (address.length() > 0)
                    address = address + ", " + item.getBlock();
                else
                    address = item.getBlock();


        if (null != item.getHouse())
            if (!item.getHouse().equalsIgnoreCase(""))
                if (address.length() > 0)
                    address = address + ", " + item.getHouse();
                else
                    address = item.getHouse();

        if (null != item.getApartment())
            if (!item.getApartment().equalsIgnoreCase(""))
                if (address.length() > 0)
                    address = address + ", " + item.getApartment();
                else
                    address = item.getApartment();
        if (null != item.getFloor())
            if (!item.getFloor().equalsIgnoreCase(""))
                if (address.length() > 0)
                    address = address + ", " + item.getFloor();
                else
                    address = item.getFloor();

        addressText.setText(address);
        textTitle.setText(item.getAddressName());
        //   itm=item;
    }

}
