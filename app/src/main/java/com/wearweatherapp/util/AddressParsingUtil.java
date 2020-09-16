package com.wearweatherapp.util;

import android.util.Log;

import com.wearweatherapp.ui.settings.SettingsActivity;

public class AddressParsingUtil {

    /*
     input : 대한민국 서울특별시 강남구 개포동...
     output : 서울특별시 강남구
     */
    public static String getSigunguFromFullAddress(String address) {
        int space_cnt=0,s_ind=0,e_ind=0;
        for(int i = 0; i < address.length(); i++){
            if(address.charAt(i) == ' '){
                if(space_cnt==0)
                    s_ind= i;
                if(space_cnt==2)
                    e_ind=i;
                space_cnt++;
            }
            if(space_cnt==3)
                break;
        }
        address = address.substring(s_ind+1,e_ind);

        return address;
    }

    /*
     input : 서울특별시 강남구 개포동...
     output : 서울특별시 강남구
     */
    public static String getSigunguFromVWorldAddress(String address) {
        int first=-1, second=-1;
        first = address.indexOf(' ');
        if(first!=-1){
            second=address.indexOf(' ',first+1);
        }

        address = address.substring(0,second);

        return address;
    }
}
