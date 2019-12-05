package com.duykhanh.storeapp.view.userpage.account.register;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.duykhanh.storeapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment implements RegisterContract.View,
        View.OnClickListener {
    final String TAG = this.getClass().toString();

    View view;
    EditText etRegUsername, etRegPassword, etRegRepassword;
    Button btnDoRegister;
    ProgressBar pbRegistering;

    RegisterPresenter presenter;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_register, container, false);
        initView();
        initComponent();

        btnDoRegister.setOnClickListener(this);

        return view;
    }

    @Override
    public void requestRegisterComplete() {
        etRegUsername.setText("");
        etRegPassword.setText("");
        etRegRepassword.setText("");
        Toast.makeText(getContext(), "Đăng ký thành công", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void requestRegisterFailure(Throwable throwable) {
        Log.e(TAG, "requestRegisterFailure: ", throwable);
        Toast.makeText(getContext(), "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDoRegister:
                String email = etRegUsername.getText().toString();
                String password = etRegPassword.getText().toString();
                presenter.requestRegister(email, password);
        }
    }

    private void initComponent() {
        presenter = new RegisterPresenter(this);
    }

    private void initView() {
        etRegUsername = view.findViewById(R.id.etRegEmail);
        etRegPassword = view.findViewById(R.id.etRegPassword);
        etRegRepassword = view.findViewById(R.id.etRegRePassword);
        btnDoRegister = view.findViewById(R.id.btnDoRegister);
        pbRegistering = view.findViewById(R.id.pbRegistering);
    }

    @Override
    public void showProgress() {
        pbRegistering.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        pbRegistering.setVisibility(View.GONE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}
