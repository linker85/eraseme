package info.androidhive.navigationdrawer.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import info.androidhive.navigationdrawer.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RegPayFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RegPayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegPayFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private EditText inputCard, inputMM, inputYYYY, inputCVV;
    private TextInputLayout inputLayoutCard, inputLayoutMM, inputLayoutYYYY, inputLayoutCVV;
    private Button btn_register_card;
    private TextView payStatus;

    private boolean isAlreadyRegistered = false;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public RegPayFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegPayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegPayFragment newInstance(String param1, String param2) {
        RegPayFragment fragment = new RegPayFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_regpay, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inputLayoutCard   = (TextInputLayout) getView().findViewById(R.id.input_layout_card);
        inputCard         = (EditText) getView().findViewById(R.id.input_card);
        inputLayoutMM     = (TextInputLayout) getView().findViewById(R.id.input_layout_mm);
        inputMM           = (EditText) getView().findViewById(R.id.input_mm);
        inputLayoutYYYY   = (TextInputLayout) getView().findViewById(R.id.input_layout_yyyy);
        inputYYYY         = (EditText) getView().findViewById(R.id.input_yyyy);
        inputLayoutCVV    = (TextInputLayout) getView().findViewById(R.id.input_layout_cvv);
        inputCVV          = (EditText) getView().findViewById(R.id.input_cvv);
        btn_register_card = (Button) getView().findViewById(R.id.btn_register_card);
        payStatus         = (TextView) getView().findViewById(R.id.id_pay_status);

        inputCard.addTextChangedListener(new RegPayFragment.MyTextWatcher(inputCard));

        if (isAlreadyRegistered) {
            payStatus.setText("Enter new card number to update payment method");
            payStatus.setVisibility(View.VISIBLE);
        } else {
            payStatus.setVisibility(View.INVISIBLE);
        }

        btn_register_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });
    }

    /**
     * Validating form
     */
    private void submitForm() {
        if (!validateCard()) {
            return;
        }
        if (!validateMonth()) {
            return;
        }
        if (!validateYear()) {
            return;
        }
        if (!validateCvv()) {
            return;
        }
        Toast.makeText(getView().getContext(), "Thank You!", Toast.LENGTH_SHORT).show();
    }

    private boolean validateCard() {
        if (inputCard.getText().toString().trim().isEmpty()) {
            inputLayoutCard.setError(getString(R.string.hint_register));
            requestFocus(inputCard);
            return false;
        } else {
            isAlreadyRegistered = true;
            payStatus.setText("Enter new card number to update payment method");
            payStatus.setVisibility(View.VISIBLE);
            inputLayoutCard.setErrorEnabled(false);
        }
        return true;
    }
    private boolean validateMonth() {
        if (inputMM.getText().toString().trim().isEmpty()) {
            inputLayoutMM.setError(getString(R.string.hint_card_month));
            requestFocus(inputMM);
            return false;
        } else {
            isAlreadyRegistered = true;
            payStatus.setText("Enter new month to update payment method");
            payStatus.setVisibility(View.VISIBLE);
            inputLayoutMM.setErrorEnabled(false);
        }
        return true;
    }
    private boolean validateYear() {
        if (inputYYYY.getText().toString().trim().isEmpty()) {
            inputLayoutYYYY.setError(getString(R.string.hint_year));
            requestFocus(inputYYYY);
            return false;
        } else {
            isAlreadyRegistered = true;
            payStatus.setText("Enter new year to update payment method");
            payStatus.setVisibility(View.VISIBLE);
            inputLayoutYYYY.setErrorEnabled(false);
        }
        return true;
    }
    private boolean validateCvv() {
        if (inputCVV.getText().toString().trim().isEmpty()) {
            inputLayoutCVV.setError(getString(R.string.hint_card_cvv));
            requestFocus(inputCVV);
            return false;
        } else {
            isAlreadyRegistered = true;
            payStatus.setText("Enter new cvv to update payment method");
            payStatus.setVisibility(View.VISIBLE);
            inputLayoutCVV.setErrorEnabled(false);
        }
        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_name:
                    validateCard();
                    break;
            }
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
