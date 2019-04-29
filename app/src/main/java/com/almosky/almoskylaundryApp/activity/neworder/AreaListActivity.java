package com.almosky.almoskylaundryApp.activity.neworder;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.almosky.almoskylaundryApp.R;
import com.almosky.almoskylaundryApp.adapter.AreasListAdapter;
import com.almosky.almoskylaundryApp.common.BaseActivity;
import com.almosky.almoskylaundryApp.model.areadto;
import com.almosky.almoskylaundryApp.utils.api.ApiCalls;
import com.almosky.almoskylaundryApp.utils.constants.ApiConstants;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.leo.simplearcloader.SimpleArcDialog;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class AreaListActivity extends BaseActivity {

    private RecyclerView listAreas;
    ArrayList<String> areaArray = new ArrayList<>();
    //(Arrays.asList("Academic City", "Al Barari", "Barsha 1", "Barsha 2", "Barsha 3", "Barsha south"));
    private TextInputEditText edtSearch;
    private ImageButton btnClear;
    private ImageButton btnPlus;
    private AreasListAdapter mAdapter;
    private boolean isRemove;
    SimpleArcDialog dialog;
    ApiCalls apiCalls;
    private TextView areaNotFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_areas);

        listAreas = findViewById(R.id.listAreas);
        edtSearch = findViewById(R.id.edtSearch);
        btnClear = findViewById(R.id.btnClear);
        btnPlus = findViewById(R.id.btnAdd);
        areaNotFound = findViewById(R.id.areaNotFound);
        dialog = new SimpleArcDialog(this);
        apiCalls = new ApiCalls();
        getAreaList();
        mAdapter = new AreasListAdapter(AreaListActivity.this, areaArray);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        listAreas.setLayoutManager(mLayoutManager);
        listAreas.setItemAnimator(new DefaultItemAnimator());
        listAreas.setAdapter(mAdapter);
        addTextListener();
        listeners();
        areaNotFound.setOnClickListener(v -> onClickedNewArea());
        //  getAreaList();
    }

    private void onClickedNewArea() {

        SweetAlertDialog alert = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
        alert.setTitle("Are you sure to add this area ?");
        alert.setConfirmButton("OK", sweetAlertDialog -> {
            RequestParams params = new RequestParams();
            params.put("area", areaNotFound.getText().toString().toUpperCase());
            String url = ApiConstants.areaAddNew;
            apiCalls.callApiPost(AreaListActivity.this, params, dialog, url, 12);
            alert.dismiss();
        });
        alert.setCancelButton("CANCEL", sweetAlertDialog -> {
            alert.dismiss();
        });
        alert.show();

    }

    private void getAreaList() {

        RequestParams params = new RequestParams();

        String url = ApiConstants.areaListsUrl;
        apiCalls.callApiPost(AreaListActivity.this, params, dialog, url, 11);
    }

    @Override
    public void getResponse(String response, int requestId) {
        // super.getResponse(response, requestId);
        if (requestId == 11) {
            try {
                Gson gson = new Gson();
                areadto areaData = gson.fromJson(response, areadto.class);

                for (int i = 0; i < areaData.getResult().size(); i++) {

                    areaArray.add(areaData.getResult().get(i).getAreaName());
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (requestId == 12) {
            try {
                SweetAlertDialog dialog = new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE);
                dialog.setConfirmButton("OK", sweetAlertDialog -> {
                    Intent intent = new Intent();
                    intent.putExtra("selectedArea", areaNotFound.getText().toString().toUpperCase());
                    setResult(RESULT_OK, intent);
                    finish();
                });
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void listeners() {
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isRemove = true;
                edtSearch.setText("");
            }
        });
    }

    private void addTextListener() {
        edtSearch.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence query, int start, int before, int count) {
                if (!isRemove)
                    if (edtSearch.getText().toString().isEmpty()) {
                        btnClear.performClick();
                        return;
                    }
                isRemove = false;
                if (query.length() > 0) {
                    btnClear.setVisibility(View.VISIBLE);
                    btnPlus.setVisibility(View.VISIBLE);
                } else {
                    btnClear.setVisibility(View.GONE);
                    btnPlus.setVisibility(View.GONE);
                }
                query = query.toString().toLowerCase().trim();
                List<String> filteredListjsonArray = new ArrayList<>();
                if (null != areaArray) {
                    for (int j = 0; j < areaArray.size(); j++) {
                        String itemObj = areaArray.get(j);
                        if (null != itemObj) {
                            if (itemObj.toLowerCase().contains(query)) {
                                filteredListjsonArray.add(itemObj);
                            }
                        }
                    }
                }
                if (filteredListjsonArray.size() > 0) {
                    listAreas.setVisibility(View.VISIBLE);
                } else {
                    btnPlus.setOnClickListener(v -> onClickedNewArea());
                    listAreas.setVisibility(View.GONE);
                    areaNotFound.setVisibility(View.VISIBLE);
                    areaNotFound.setText(edtSearch.getText().toString());
                }
                mAdapter = new AreasListAdapter(AreaListActivity.this, filteredListjsonArray);
                listAreas.setAdapter(mAdapter);
            }
        });
    }

    public void setSelectedItem(String selectedArea) {
        Intent intent = new Intent();
        intent.putExtra("selectedArea", selectedArea);
        setResult(RESULT_OK, intent);
        finish();
    }
}