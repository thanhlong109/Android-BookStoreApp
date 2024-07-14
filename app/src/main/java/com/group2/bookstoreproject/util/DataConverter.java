package com.group2.bookstoreproject.util;

public class DataConverter {
    public static int IntegerSafeConvert(String value, int defaultValue,OnError onError){
        int converted = defaultValue;
        try{
            converted = Integer.parseInt(value);
        }catch (Exception e){
            if(onError!=null){
                onError.onError();
            }
            return converted;
        }
        return  converted;
    }
    public static Double DoubleSafeConvert(String value, double defaultValue,OnError onError){
        double converted = defaultValue;
        try{
            converted = Double.parseDouble(value);
        }catch (Exception e){
            if(onError!=null){
                onError.onError();
            }
            return converted;
        }
        return  converted;
    }

    public interface OnError{
        void onError();
    }


}
