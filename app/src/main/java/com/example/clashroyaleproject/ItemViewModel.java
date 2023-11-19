package com.example.clashroyaleproject;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;


public class ItemViewModel extends ViewModel {


    private ArrayList<Sprite> ssTemp = new ArrayList<>();
    private ArrayList<Integer> ssaTemp = new ArrayList<>();

    private final MutableLiveData<ArrayList<Sprite>> _ss = new MutableLiveData<>();

    //only expose this
    LiveData<ArrayList<Sprite>> ss = _ss;
    private final MutableLiveData<ArrayList<Integer>> _ssa = new MutableLiveData<>();
    LiveData<ArrayList<Integer>> ssa = _ssa;


   private final MutableLiveData<Integer> igaDisplay = new MutableLiveData<>();
   
   private final MutableLiveData<Integer> arena = new MutableLiveData<>();

    private final MutableLiveData<Double> goldToGem = new MutableLiveData<>();

    private final MutableLiveData<Double> gemToCurrency = new MutableLiveData<>();

    private final MutableLiveData<String> currency = new MutableLiveData<>();
    private MutableLiveData<Integer> displayedCost = new MutableLiveData<>();

    private MutableLiveData<Integer> currencyPos = new MutableLiveData<>();

    private final MutableLiveData<Double> baseGoldValue  = new MutableLiveData<>();
    
    private final MutableLiveData<Integer> ewcValue = new MutableLiveData<>();
    

    private final MutableLiveData<String> type = new MutableLiveData<>();

    private double goldToShopToken;



    public void setVars(ArrayList<Sprite> arr1, ArrayList<Integer> arr2, double goldToGemDefault, final double goldToShopToken, double gemToCurrencyDefault, int numOfArenas) {
        baseGoldValue.setValue(0.0);
        ewcValue.setValue(0);
        _ss.setValue(arr1);
        _ssa.setValue(arr2);
        type.setValue("ewc");
        currency.setValue("USD");
        currencyPos.setValue(0);
        arena.setValue(numOfArenas+1);
        changeType();
        displayedCost.setValue(0);
        igaDisplay.setValue(0);
        goldToGem.setValue(goldToGemDefault);
        gemToCurrency.setValue(gemToCurrencyDefault);
        this.goldToShopToken = goldToShopToken;

    }


    public int setType(){
        int d = R.drawable.gold;
        switch (type.getValue()){
            case "gold":
                return R.drawable.gold;
            case "gem":
                return R.drawable.gem;
            case "st":
                return R.drawable.shop_token;
            case "currency":
                switch (currency.getValue()){
                    case "USD":
                        return R.drawable.baseline_usd_24;
                    case "Rupees":
                        return R.drawable.baseline_currency_rupee_24;
                    case "Yen":
                        return R.drawable.baseline_currency_yen_24;
                }
            case "ewc":
                return R.drawable.wc_elite;
        }

        return d;



    }

    public int changeType()  {
        int d = R.drawable.gold;
        String t = type.getValue();

        if (t.equals("ewc")){
            type.setValue("gold");
        }
        switch (t) {
            case "gold":
                type.setValue("gem");
                d = R.drawable.gem;
                break;
            case "gem":
                type.setValue("st");
                d = R.drawable.shop_token;
                break;
            case "st":
                type.setValue("currency");
                switch (currency.getValue()) {
                    case "USD":
                        d = R.drawable.baseline_usd_24;
                        break;
                    case "Rupees":
                        d = R.drawable.baseline_currency_rupee_24;
                        break;
                    case "Yen":
                        d = R.drawable.baseline_currency_yen_24;
                        break;
                }
                break;
            case "currency":
                type.setValue("ewc");
                d = R.drawable.wc_elite;
                break;
        }
        setDisplayedResult();
        //be careful of this
        return d;
    }
    public MutableLiveData<Integer> getDisplayedCost(){
        if (displayedCost == null){
             displayedCost = new MutableLiveData<>();
        }
        return displayedCost;
    }


    public MutableLiveData<Double> getGoldToGem(){
        return goldToGem;
    }

    public MutableLiveData<Double> getGemToCurrency(){
        return gemToCurrency;
    }



    public void setDisplayedResult(){

        double currentBaseGoldValue = baseGoldValue.getValue();

        switch (type.getValue()){
            case "gem":
                currentBaseGoldValue *= goldToGem.getValue();
                break;
            case "st":
                currentBaseGoldValue *= goldToShopToken;
                break;
            case "currency":
                currentBaseGoldValue *= goldToGem.getValue() * gemToCurrency.getValue();
                break;
            case "ewc":
                currentBaseGoldValue = ewcValue.getValue();
                break;
        }


        displayedCost.setValue((int)Math.round(currentBaseGoldValue));
    }

    public void addSprite(Sprite s, int amt){


        ssTemp = _ss.getValue();
        ssaTemp = _ssa.getValue();

        boolean found = false;

        for (Sprite sprite : ssTemp) {
            if (s == sprite) {
                int i = ssTemp.indexOf(sprite);
                ssaTemp.set(i, ssaTemp.get(i) + amt);
                found = true;
                break;
            }


        }
        if (!found){
            ssTemp.add(s);
            ssaTemp.add(amt);
        }




        //observers need to observe these changes
        _ss.setValue(ssTemp);
        _ssa.setValue(ssaTemp);

        recalculateValues();
        setDisplayedResult();

    }

    public void clearAll(){

        ssTemp = _ss.getValue();
        ssaTemp = _ssa.getValue();

        ssTemp.clear();
        ssaTemp.clear();

        _ss.setValue(ssTemp);
        _ssa.setValue(ssaTemp);

        baseGoldValue.setValue(0.0);
        setDisplayedResult();

    }

    public void deleteSprite(Sprite s){

        ssTemp = _ss.getValue();
        ssaTemp = _ssa.getValue();

        int index = ssTemp.indexOf(s);

        ssTemp.remove(index);
        ssaTemp.remove(index);

        _ss.setValue(ssTemp);
        _ssa.setValue(ssaTemp);

        recalculateValues();
        setDisplayedResult();


    }


    public LiveData<ArrayList<Sprite>> getSS() {
        ss = _ss;
        return ss;
    }

    public void setSSA(ArrayList<Integer> ssa) {
        _ssa.setValue(ssa);
    }

    public LiveData<ArrayList<Integer>> getSSA() {
        ssa = _ssa;
        return ssa;
    }

    public MutableLiveData<Integer> getCurrencyPos() {
        return currencyPos;
    }

    public MutableLiveData<Integer> getIgaDisplay(){
        return igaDisplay;
    }

    public MutableLiveData<Integer> getArena(){
        return arena;
    }

    public MutableLiveData<String> getCurrency(){
        return currency;
    }

    public void recalculateValues(){
        ewcValue.setValue(0);
        baseGoldValue.setValue(0.0);

        for (int i = 0; i < _ss.getValue().size(); i++){
            baseGoldValue.setValue(baseGoldValue.getValue() + _ss.getValue().get(i).goldValue * _ssa.getValue().get(i));
        }

        for (int i = 0; i < _ss.getValue().size(); i++){
            ewcValue.setValue(ewcValue.getValue() + _ss.getValue().get(i).ewcValue * _ssa.getValue().get(i));
        }
    }



}
