package me.pkhope.gank.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by pkhope on 16/11/24.
 */

public class Gank implements Serializable {

    public boolean error;
    public List<GankItem> results;

    public void setError(boolean error){
        this.error = error;
    }

    public boolean getError(){
        return error;
    }

    public void setResults(List<GankItem> results){
        this.results = results;
    }

    public List<GankItem> getResults(){
        return results;
    }
}
