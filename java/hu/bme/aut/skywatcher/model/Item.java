package hu.bme.aut.skywatcher.model;

import android.os.Parcel;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item implements Serializable {

@SerializedName("href")
@Expose
private String href;
@SerializedName("data")
@Expose
private List<Datum> data = null;
@SerializedName("links")
@Expose
private List<Link> links = null;
private final static long serialVersionUID = 6542350101523525636L;

public String getHref() {
return href;
}

public void setHref(String href) {
this.href = href;
}

public List<Datum> getData() {
return data;
}

public void setData(List<Datum> data) {
this.data = data;
}

public List<Link> getLinks() {
return links;
}

public void setLinks(List<Link> links) {
this.links = links;
}


}