package com.devapp.aesencryptionjava.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
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
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.devapp.aesencryptionjava.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

public class FragmentDecryption extends Fragment {
    private AutoCompleteTextView autoCompleteTextView;
    private Button generateButton;
    private ImageView imgSuccess;
    private View viewSuccess;
    private TextView titleText;
    private TextInputLayout textInputLayout;
    private EditText plainText;
    private Handler handler = new Handler(Looper.getMainLooper());
    public FragmentDecryption(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_decryption, container, false);
    }

    @Override
    public void onResume() {
        String [] aesString = getResources().getStringArray(R.array.aes_algorithms);
        ArrayAdapter arrayAdapter = new ArrayAdapter(requireContext(),R.layout.item_drop_down,aesString);
        autoCompleteTextView.setAdapter(arrayAdapter);
        super.onResume();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mapping(view);
        autoCompleteTextView.setFocusable(false);
        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(plainText.getText().toString().trim().isEmpty()){
                    showSnackbar("Plain text must not empty");
                }
                else applyAnimation();
            }
        });

        super.onViewCreated(view, savedInstanceState);
    }

    private void applyAnimation(){
        generateButton.setEnabled(false);
        titleText.animate().alpha(0).setDuration(400);
        generateButton.animate().alpha(0).setDuration(400);
        textInputLayout.animate()
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
        },300);

        handler.postDelayed(() -> {
            imgSuccess.animate().alpha(1f).setDuration(1000);
        },800);

        handler.postDelayed(() -> {
            Navigation.findNavController(requireView()).navigate(R.id.action_menuEncryption_to_fragmentSuccess);
        },2000);
    }

    private void showSnackbar(String message){
        Snackbar snackbar = Snackbar.make(requireView(),message,Snackbar.LENGTH_LONG);
        snackbar.setAction("OKAY", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        snackbar.show();
    }

    private void mapping(View view){
        autoCompleteTextView = view.findViewById(R.id.autoCompleView);
        generateButton = view.findViewById(R.id.buttonGenerate);
        viewSuccess = view.findViewById(R.id.successHandle);
        imgSuccess = view.findViewById(R.id.imgSuccess);
        titleText = view.findViewById(R.id.tvTitle);
        textInputLayout = view.findViewById(R.id.textInputLayout);
        plainText = view.findViewById(R.id.plainText);
    }
}
