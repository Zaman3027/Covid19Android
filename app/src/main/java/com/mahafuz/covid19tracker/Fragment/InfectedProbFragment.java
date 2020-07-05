package com.mahafuz.covid19tracker.Fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.mahafuz.covid19tracker.ApiInterface.RetroFitInstance;
import com.mahafuz.covid19tracker.Model.InfectedProbabilityModel;
import com.mahafuz.covid19tracker.R;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfectedProbFragment extends Fragment implements AdapterView.OnItemSelectedListener{
    RetroFitInstance retroFitInstance;
    EditText mAgeEditText;
    Button mSubmitButton;
    Dialog infoDialog;
    ImageView infoIcon, infoCloseButton;
    TextView mProbabilityTextView0;
    TextView mProbabilityTextView1;
    Spinner genderSpinner, stateSpinner;

    public InfectedProbFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        retroFitInstance = new RetroFitInstance();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_infected_prob, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAgeEditText = getView().findViewById(R.id.input_age);
        genderSpinner = getView().findViewById(R.id.gender_spinner);
        stateSpinner = getView().findViewById(R.id.state_spinner);
        mSubmitButton = (Button) getView().findViewById(R.id.submit_button);
        infoDialog = new Dialog(getContext());

        genderSpinner.setOnItemSelectedListener(this);
        genderSpinner.setPrompt("Your Gender?");
        stateSpinner.setOnItemSelectedListener(this);
        stateSpinner.setPrompt("Your State?");

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_age =  mAgeEditText.getText().toString();
                String user_gender = genderSpinner.getSelectedItem().toString().substring(0,1);
                String user_state = stateSpinner.getSelectedItem().toString();
                mAgeEditText.setInputType(InputType.TYPE_CLASS_NUMBER);

                if (!user_age.matches(""))
                    showDialog(user_age, user_gender, user_state);
                else if (user_age.matches("") )
                    Toast.makeText(getContext(), "Age field can't be empty!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showDialog(String user_age, String user_gender, String user_state) {
        infoDialog.setContentView(R.layout.probability_dialogue);
        infoIcon = (ImageView) infoDialog.findViewById(R.id.info_icon);
        infoCloseButton = (ImageView) infoDialog.findViewById(R.id.info_close_button);
        mProbabilityTextView0 = (TextView) infoDialog.findViewById(R.id.user_probability_0);
        mProbabilityTextView1 = (TextView) infoDialog.findViewById(R.id.user_probability_1);
        retroFitInstance.getApi()
        .getInfectedProbabilityModel(user_age, user_gender, user_state)
        .enqueue(new Callback<InfectedProbabilityModel>() {
            @Override
            public void onResponse(Call<InfectedProbabilityModel> call, Response<InfectedProbabilityModel> response) {
                if (response.isSuccessful()){
                    String probabiliy[] = new String[2];
                    probabiliy[0] = response.body().getProb().get0();
                    probabiliy[1] = response.body().getProb().get1();
                    mProbabilityTextView0.setText(probabiliy[0]);
                    mProbabilityTextView1.setText(probabiliy[1]);
                }
            }

            @Override
            public void onFailure(Call<InfectedProbabilityModel> call, Throwable t) {

            }
        });
        infoDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        infoDialog.show();
        infoCloseButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            infoDialog.dismiss();
        }
    });


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
