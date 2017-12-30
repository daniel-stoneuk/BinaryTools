package com.danielstone.binarytools.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.math.BigInteger;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> baseTwo = new MutableLiveData<>();
    private final MutableLiveData<String> baseEight = new MutableLiveData<>();
    private final MutableLiveData<String> baseTen = new MutableLiveData<>();
    private final MutableLiveData<String> baseSixteen = new MutableLiveData<>();
    private final MutableLiveData<String> baseN1 = new MutableLiveData<>();
    private final MutableLiveData<String> baseN2 = new MutableLiveData<>();

    private MutableLiveData<Integer> n1Base = new MutableLiveData<Integer>();
    private MutableLiveData<Integer> n2Base = new MutableLiveData<Integer>();

    public void setN1Base(int base) {
        n1Base.setValue(base);
    }

    public void setN2Base(int base) {
        n2Base.setValue(base);
    }

    public boolean setNewValue(String s, int radix) {
        try {
            s = s.toUpperCase();
            // Convert from base radix to base 10.
            BigInteger base = BigInteger.valueOf(radix);
            BigInteger result = new BigInteger("0");
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i); // the
                int u = (int) c; // integer representation of the character in ascii
                if ((u >= '0' && u <= '9') || (u >= 'A' && u <= 'Z')) { // if the character is a number or alphabetical letter
                    if ((u >= '0' && u <= '9')) u = u - '0';
                    else u = (u - 'A') + 10; // change the integer representation to the correct value
                    if (u >= radix) throw new Exception();
                    BigInteger digit = new BigInteger(String.valueOf(u));
                    BigInteger value = digit.multiply(new BigInteger(String.valueOf(base.pow(s.length() - (i + 1)))));
                    result = result.add(value);
                } else {
                    throw new Exception();
                }
            }

            baseTwo.setValue(convertToBase(result, 2));
            baseEight.setValue(convertToBase(result, 8));
            baseTen.setValue(convertToBase(result, 10));
            baseSixteen.setValue(convertToBase(result, 16));
            if (n1Base.getValue() == null) n1Base.setValue(24);
            if (n2Base.getValue() == null) n2Base.setValue(36);
            baseN1.setValue(convertToBase(result, n1Base.getValue()));
            baseN2.setValue(convertToBase(result, n2Base.getValue()));

            return true;

        } catch (Throwable e) {
            clearStrings();
            return false;
        }

    }

    private String convertToBase(BigInteger decimal, int radix) throws Throwable {
        String result = "";
        while (decimal.intValue() != 0) {
            BigInteger[] division = decimal.divideAndRemainder(BigInteger.valueOf(radix));
            int remainder = division[1].intValue();
            char c = (char) ('0' + remainder); // get the character using the remainder
            if (remainder > 9) { // if the character
                int u = remainder - 10; // find the index of the alphabetical letter
                c = (char) ('A' + u); // update the character
            }
            result = c + result; // append to beginning of result
            decimal = division[0]; // use the new decimal value.
        }
        return result;
    }

    public void clearStrings() {
        baseTwo.setValue("");
        baseEight.setValue("");
        baseTen.setValue("");
        baseSixteen.setValue("");
        baseN1.setValue("");
        baseN2.setValue("");
    }

    public LiveData<String> getBaseTwo() {
        return baseTwo;
    }

    public LiveData<String> getBaseEight() {
        return baseEight;
    }

    public LiveData<String> getBaseTen() {
        return baseTen;
    }

    public LiveData<String> getBaseSixteen() {
        return baseSixteen;
    }

    public LiveData<String> getBaseN1() {
        return baseN1;
    }

    public LiveData<String> getBaseN2() {
        return baseN2;
    }

    public MutableLiveData<Integer> getN1Base() {
        return n1Base;
    }

    public MutableLiveData<Integer> getN2Base() {
        return n2Base;
    }
}
