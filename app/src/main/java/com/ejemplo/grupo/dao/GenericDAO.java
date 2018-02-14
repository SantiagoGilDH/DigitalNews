package com.ejemplo.grupo.dao;

import android.content.Context;
import android.content.res.AssetManager;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by andres on 07/06/16.
 */
public class GenericDAO {

    public Object getObjectJSON(Context context, Class aClass, String fileName){

        Object object = null;
        try{

            AssetManager manager = context.getAssets();
            InputStream inputStream = manager.open(fileName);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            Gson gson = new Gson();
            object = gson.fromJson(bufferedReader, aClass);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return object;
    }
}
