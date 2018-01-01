package com.danielstone.binarytools;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.danielstone.binarytools.viewmodel.HomeViewModel;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import timber.log.Timber;


public class HomeFragment extends Fragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    private OnFragmentIL mListener;

    private HomeViewModel model;

    @BindView(R.id.input_base_two)
    TextInputLayout inputBaseTwo;
    @BindView(R.id.input_base_eight)
    TextInputLayout inputBaseEight;
    @BindView(R.id.input_base_ten)
    TextInputLayout inputBaseTen;
    @BindView(R.id.input_base_sixteen)
    TextInputLayout inputBaseSixteen;
    @BindView(R.id.input_n1)
    EditText inputN1;
    @BindView(R.id.input_base_n1)
    TextInputLayout inputBaseN1;
    @BindView(R.id.input_n2)
    EditText inputN2;
    @BindView(R.id.input_base_n2)
    TextInputLayout inputBaseN2;

    private Unbinder unbinder;

    SharedPreferences preferences;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        // no args
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // Get args
        }
        model = ViewModelProviders.of(getActivity()).get(HomeViewModel.class);

        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
    }



    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.pref_decimal_places))) {
            model.setDecimalPlaces(sharedPreferences.getInt(key, 20));
        }
    }

    class MyObserver implements Observer<String> {

        TextInputLayout textInputLayout;
        int radix;

        public MyObserver(TextInputLayout textInputLayout, int radix) {
            this.textInputLayout = textInputLayout;
            this.radix = radix;
        }

        @Override
        public void onChanged(@Nullable String s) {
            if (!textInputLayout.getEditText().isFocused()) {
                textInputLayout.getEditText().removeTextChangedListener(textWatchers[radix]);
                textInputLayout.setErrorEnabled(false);
                textInputLayout.getEditText().setText(s);
                textInputLayout.getEditText().addTextChangedListener(textWatchers[radix]);
            }
        }
    }

    class MyTextWatcher implements TextWatcher {

        TextInputLayout textInputLayout;
        int radix = 10;

        public MyTextWatcher(TextInputLayout textInputLayout, int radix) {
            this.textInputLayout = textInputLayout;
            this.radix = radix;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            Timber.i("radix: " + radix + " s: " + s);
            int radix = this.radix;
            if (!s.toString().isEmpty()) {
                if (!model.setNewValue(s.toString(), radix)) {
                    textInputLayout.setError("Invalid Input");
                } else {
                    textInputLayout.setErrorEnabled(false);
                }
            } else {
                model.clearStrings();
                textInputLayout.setErrorEnabled(false);
            }
        }
    }

    class CustomBaseWatcher implements TextWatcher {

        EditText editText;
        TextInputLayout inputLayout;
        int n;

        public CustomBaseWatcher(EditText editText, TextInputLayout inputLayout, int n) {
            this.editText = editText;
            this.inputLayout = inputLayout;
            this.n = n;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @SuppressLint("SetTextI18n")
        @Override
        public void afterTextChanged(Editable s) {
            int radix = 2;
            try {
                radix = Integer.parseInt(s.toString());
            } catch (Exception e) {
                Random random = new Random();
                radix = random.nextInt(34) + 2;
            }
            if (n == 1) {
                model.setN1Base(radix);
                model.setNewValue(inputBaseTen.getEditText().getText().toString(), 10);
            } else {
                model.setN2Base(radix);
                model.setNewValue(inputBaseTen.getEditText().getText().toString(), 10);
            }
            if (radix >= 2 && radix <= 36) inputLayout.setEnabled(true);
            else inputLayout.setEnabled(false);

        }
    }

    MyTextWatcher[] textWatchers = new MyTextWatcher[39]; // 37 & 38 reserved for n1, n2

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        unbinder = ButterKnife.bind(this, root);

        textWatchers[2] = new MyTextWatcher(inputBaseTwo, 2);
        textWatchers[8] = new MyTextWatcher(inputBaseEight, 8);
        textWatchers[10] = new MyTextWatcher(inputBaseTen, 10);
        textWatchers[16] = new MyTextWatcher(inputBaseSixteen, 16);
        textWatchers[37] = new MyTextWatcher(inputBaseN1, 37);
        textWatchers[38] = new MyTextWatcher(inputBaseN2, 38);

        inputBaseTwo.getEditText().addTextChangedListener(textWatchers[2]);
        inputBaseEight.getEditText().addTextChangedListener(textWatchers[8]);
        inputBaseTen.getEditText().addTextChangedListener(textWatchers[10]);
        inputBaseSixteen.getEditText().addTextChangedListener(textWatchers[16]);

        final CustomBaseWatcher inputN1BaseWatcher = new CustomBaseWatcher(inputN1, inputBaseN1, 1);
        inputN1.addTextChangedListener(inputN1BaseWatcher);
        inputBaseN1.getEditText().addTextChangedListener(textWatchers[37]);
        final CustomBaseWatcher inputN2BaseWatcher = new CustomBaseWatcher(inputN2, inputBaseN2, 2);
        inputN2.addTextChangedListener(inputN2BaseWatcher);
        inputBaseN2.getEditText().addTextChangedListener(textWatchers[38]);


        model.getBaseTwo().observe(this, new MyObserver(inputBaseTwo, 2));
        model.getBaseEight().observe(this, new MyObserver(inputBaseEight, 8));
        model.getBaseTen().observe(this, new MyObserver(inputBaseTen, 10));
        model.getBaseSixteen().observe(this, new MyObserver(inputBaseSixteen, 16));
        model.getBaseN1().observe(this, new MyObserver(inputBaseN1, 37));
        model.getBaseN2().observe(this, new MyObserver(inputBaseN2, 38));

        model.getN1Base().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                if (!inputN1.isFocused()) {
                    inputN1.removeTextChangedListener(inputN1BaseWatcher);
                    inputN1.setText(String.valueOf(integer));
                    inputN1.addTextChangedListener(inputN1BaseWatcher);
                }
            }
        });
        model.getN2Base().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                if (!inputN2.isFocused()) {
                    inputN2.removeTextChangedListener(inputN2BaseWatcher);
                    inputN2.setText(String.valueOf(integer));
                    inputN2.addTextChangedListener(inputN2BaseWatcher);
                }
            }
        });

        return root;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentIL) {
            mListener = (OnFragmentIL) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentIL");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        model.setDecimalPlaces(preferences.getInt(getString(R.string.pref_decimal_places), 20));
        preferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        preferences.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public interface OnFragmentIL {
    }
}
