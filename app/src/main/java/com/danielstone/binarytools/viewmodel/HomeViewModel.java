package com.danielstone.binarytools.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

/**
 * Created by danielstone on 28/12/2017.
 */

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> baseTwo = new MutableLiveData<>();
    private final MutableLiveData<String> baseEight = new MutableLiveData<>();
    private final MutableLiveData<String> baseTen = new MutableLiveData<>();
    private final MutableLiveData<String> baseSixteen = new MutableLiveData<>();
    private final MutableLiveData<String> baseN1 = new MutableLiveData<>();
    private final MutableLiveData<String> baseN2 = new MutableLiveData<>();

    private int n1Base = 30;
    private int n2Base = 12;

    public void setN1Base(int base) {
        n1Base = base;
    }

    public void setN2Base(int base) {
        n2Base = base;
    }

    public boolean setNewValue(String s, int radix) {
        try {
            Long l = Long.parseLong(s, radix);
//            if (radix != 2) baseTwo.setValue(Long.toString(l, 2));
            baseTwo.setValue(Long.toString(l, 2));
            baseEight.setValue(Long.toString(l, 8));
            baseTen.setValue(Long.toString(l, 10));
            baseSixteen.setValue(Long.toString(l, 16).toUpperCase());
            baseN1.setValue(Long.toString(l, n1Base).toUpperCase());
            baseN2.setValue(Long.toString(l, n2Base).toUpperCase());
            return true;

        } catch (Exception e) {
            return false;
        }

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

    public int getN1Base() {
        return n1Base;
    }

    public int getN2Base() {
        return n2Base;
    }
}
