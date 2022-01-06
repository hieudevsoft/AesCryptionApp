package com.devapp.aesencryptionjava.fragments;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.devapp.aesencryptionjava.R;
import com.devapp.aesencryptionjava.model.Result;
import com.devapp.aesencryptionjava.util.Encryption;
import com.devapp.aesencryptionjava.util.SharedPrefs;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Base64;

public class FragmentDecryption extends Fragment {
    private final String TAG = "FragmentDecryption";
    private AutoCompleteTextView autoCompleteTextView;
    private Button generateButton;
    private ImageView imgSuccess;
    private View viewSuccess;
    private TextView titleText;
    private TextInputLayout textInputLayout;
    private EditText plainText;
    private EditText edtKey;
    private EditText edtIv;
    private Handler handler = new Handler(Looper.getMainLooper());
    private SharedPrefs prefs;
    public FragmentDecryption() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        prefs = new SharedPrefs(requireContext());
        return inflater.inflate(R.layout.fragment_decryption, container, false);
    }

    @Override
    public void onResume() {
        if (autoCompleteTextView.getText().toString().contains("CBC")) {
            edtIv.setVisibility(View.VISIBLE);
        } else {
            edtIv.setVisibility(View.GONE);
        }
        String[] aesString = getResources().getStringArray(R.array.aes_algorithms);
        ArrayAdapter arrayAdapter = new ArrayAdapter(requireContext(), R.layout.item_drop_down, aesString);
        autoCompleteTextView.setAdapter(arrayAdapter);
        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().contains("CBC")) {
                    edtIv.setVisibility(View.VISIBLE);
                } else {
                    edtIv.setVisibility(View.GONE);
                }
            }
        });
        edtIv.setText(prefs.getIv());
        edtKey.setText(prefs.getKey());
        super.onResume();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mapping(view);

        autoCompleteTextView.setFocusable(false);

        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (plainText.getText().toString().trim().isEmpty() || edtKey.getText().toString().trim().isEmpty()) {
                    showSnackbar("Fields must not empty");
                } else {
                    try {
                        if (edtIv.getVisibility() == View.VISIBLE) {
                            if (edtIv.getText().toString().isEmpty()) {
                                showSnackbar("Fields must not empty");
                            } else {
                                try {
                                    Result r = Encryption.CBCDecryptionWithKey(plainText.getText().toString(), edtKey.getText().toString(), edtIv.getText().toString());
                                    applyAnimation(r);
                                } catch (Exception e) {
                                    showSnackbar(e.getMessage());
                                }
                            }
                        } else {
                            try {
                                Result r = Encryption.ECBDecryptionWithKey(plainText.getText().toString(), edtKey.getText().toString());
                                applyAnimation(r);
                            } catch (Exception e) {
                                showSnackbar(e.getMessage());
                            }
                        }
                    } catch (Exception e) {
                        showSnackbar(e.getMessage());
                    }
                }
                ;
            }
        });

        super.onViewCreated(view, savedInstanceState);
    }

    private void applyAnimation(Result result) {
        prefs.saveKey(prefs.getKey());
        prefs.saveIv(prefs.getIv());
        generateButton.setEnabled(false);
        titleText.animate().alpha(0).setDuration(400);
        generateButton.animate().alpha(0).setDuration(400);
        textInputLayout.animate()
                .alpha(0f)
                .translationXBy(1200f)
                .setDuration(400);
        edtKey.animate()
                .alpha(0f)
                .translationXBy(-1200f)
                .setDuration(400);
        edtIv.animate()
                .alpha(0f)
                .translationXBy(1200f)
                .setDuration(400);
        plainText.animate()
                .alpha(0f)
                .translationXBy(-1200f)
                .setDuration(400);

        handler.postDelayed(() -> {
            viewSuccess.animate().alpha(1).setDuration(600);
            viewSuccess.animate().rotationBy(720f).setDuration(600);
            viewSuccess.animate().scaleXBy(900f).setDuration(600);
            viewSuccess.animate().scaleYBy(900f).setDuration(600);
        }, 300);

        handler.postDelayed(() -> {
            imgSuccess.animate().alpha(1f).setDuration(1000);
        }, 800);

        handler.postDelayed(() -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("data",result);
            Navigation.findNavController(requireView()).navigate(R.id.action_menuDecryption_to_fragmentSuccess,bundle);
        }, 2000);
    }

    private void showSnackbar(String message) {
        Snackbar snackbar = Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG);
        snackbar.setAction("OKAY", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        snackbar.show();
    }

    private void mapping(View view) {
        autoCompleteTextView = view.findViewById(R.id.autoCompleView);
        generateButton = view.findViewById(R.id.buttonGenerate);
        viewSuccess = view.findViewById(R.id.successHandle);
        imgSuccess = view.findViewById(R.id.imgSuccess);
        titleText = view.findViewById(R.id.tvTitle);
        textInputLayout = view.findViewById(R.id.textInputLayout);
        plainText = view.findViewById(R.id.plainText);
        edtKey = view.findViewById(R.id.edtKey);
        edtIv = view.findViewById(R.id.edtIv);
    }
}
