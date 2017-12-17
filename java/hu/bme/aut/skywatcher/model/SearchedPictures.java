package hu.bme.aut.skywatcher.model;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchedPictures implements Serializable
{

    private int randomColorIndex ;
    private String searchedName="";

@SerializedName("collection")
@Expose
private Collection collection;
private final static long serialVersionUID = -4177313238908887203L;

public String getSearchedName(){  return searchedName;}
public void setSearchedName(String t){ searchedName = t;}

public Collection getCollection() {
return collection;
}

public void setCollection(Collection collection) {
this.collection = collection;
}

    public int getRandomColorIndex() {
        return randomColorIndex;
    }

    public void setRandomColorIndex(int randomColorIndex) {
        this.randomColorIndex = randomColorIndex;
    }

}