package com.danielstone.binarytools.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

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

    public boolean setNewValue(String input, int radix) {
        try {
            input = input.toUpperCase();
            int dots = 0;
            for (int i = 0; i < input.length(); i++) {
                if (input.charAt(i) == '.') {
                    dots ++;
                }
            }

            if (dots > 1) throw new Exception();

            String[] parts = input.split("\\.");
            if (parts.length == 0 || parts.length > 2) throw new Exception();

            BigInteger integerResult = null;
            if (parts.length >= 1) {
                String integerPart = null;
                integerPart = parts[0];
                // Convert from base radix to base 10.
                BigInteger base = BigInteger.valueOf(radix);
                integerResult = new BigInteger("0");
                for (int i = 0; i < integerPart.length(); i++) {
                    char c = integerPart.charAt(i); // the
                    int u = (int) c; // integer representation of the character in ascii
                    if ((u >= '0' && u <= '9') || (u >= 'A' && u <= 'Z')) { // if the character is a number or alphabetical letter
                        if ((u >= '0' && u <= '9')) u = u - '0';
                        else u = (u - 'A') + 10; // change the integer representation to the correct value
                        if (u >= radix) throw new Exception();
                        BigInteger digit = new BigInteger(String.valueOf(u));
                        BigInteger value = digit.multiply(new BigInteger(String.valueOf(base.pow(integerPart.length() - (i + 1)))));
                        integerResult = integerResult.add(value);
                    } else {
                        throw new Exception();
                    }
                }
            }
            BigDecimal fractionResult = null;
            if (parts.length == 2) {
                String fractionPart = parts[1];
                BigDecimal base = BigDecimal.valueOf(radix);
                fractionResult = new BigDecimal("0");
                for (int i = 0; i < fractionPart.length(); i++) {
                    char c = fractionPart.charAt(i); // the
                    int u = (int) c; // integer representation of the character in ascii
                    if ((u >= '0' && u <= '9') || (u >= 'A' && u <= 'Z')) { // if the character is a number or alphabetical letter
                        if ((u >= '0' && u <= '9')) u = u - '0';
                        else u = (u - 'A') + 10; // change the integer representation to the correct value
                        if (u >= radix) throw new Exception();
                        BigDecimal digit = new BigDecimal(String.valueOf(u));
                        BigDecimal value = digit.multiply(new BigDecimal(String.valueOf(base.pow(- i - 1, MathContext.DECIMAL64))));
                        fractionResult = fractionResult.add(value);
                    } else {
                        throw new Exception();
                    }
                }
            }

            if (integerResult != null && fractionResult == null) {
                baseTwo.setValue(convertIntegerToBase(integerResult, 2));
                baseEight.setValue(convertIntegerToBase(integerResult, 8));
                baseTen.setValue(integerResult.toString());
                baseSixteen.setValue(convertIntegerToBase(integerResult, 16));
                if (n1Base.getValue() == null) n1Base.setValue(24);
                if (n2Base.getValue() == null) n2Base.setValue(36);
                baseN1.setValue(convertIntegerToBase(integerResult, n1Base.getValue()));
                baseN2.setValue(convertIntegerToBase(integerResult, n2Base.getValue()));
            }
            if (integerResult != null && fractionResult != null) {
                baseTwo.setValue(convertIntegerToBase(integerResult, 2) + "." + convertFractionToBase(fractionResult, 2));
                baseEight.setValue(convertIntegerToBase(integerResult, 8) + "." + convertFractionToBase(fractionResult, 8));
                baseTen.setValue(integerResult.toString() + "." + convertFractionToBase(fractionResult, 10));
                baseSixteen.setValue(convertIntegerToBase(integerResult, 16) + "." + convertFractionToBase(fractionResult, 16));
                if (n1Base.getValue() == null) n1Base.setValue(24);
                if (n2Base.getValue() == null) n2Base.setValue(36);
                baseN1.setValue(convertIntegerToBase(integerResult, n1Base.getValue()) + "." + convertFractionToBase(fractionResult, n1Base.getValue()));
                baseN2.setValue(convertIntegerToBase(integerResult, n2Base.getValue()) + "." + convertFractionToBase(fractionResult, n2Base.getValue()));
            }

            return true;

        } catch (Throwable e) {
            e.printStackTrace();
            clearStrings();
            return false;
        }

    }

    private String convertFractionToBase(BigDecimal fraction, int radix) {
        String result = "";
        BigDecimal base = new BigDecimal(radix);
        for (int i = 0; i < 128; i++) {
            fraction = fraction.multiply(base);
            BigInteger x = fraction.toBigInteger();
            char c = (char) ('0' + x.intValue());
            if (x.compareTo(BigInteger.valueOf(9)) == 1) {
                c = (char) ('A' + (x.intValue() - 10));
            }
            result = result + c;
            fraction = fraction.subtract(BigDecimal.valueOf(x.intValue()));
            if (fraction.compareTo(BigDecimal.ZERO) == 0) break;
        }
        return result;
    }

    private String convertIntegerToBase(BigInteger integer, int radix) throws Throwable {
        String result = "";
        while (integer.intValue() != 0) {
            BigInteger[] division = integer.divideAndRemainder(BigInteger.valueOf(radix));
            int remainder = division[1].intValue();
            char c = (char) ('0' + remainder); // get the character using the remainder
            if (remainder > 9) { // if the character
                int u = remainder - 10; // find the index of the alphabetical letter
                c = (char) ('A' + u); // update the character
            }
            result = c + result; // append to beginning of result
            integer = division[0]; // use the new decimal value.
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
