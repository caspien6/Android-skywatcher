package hu.bme.aut.skywatcher.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Collection implements Serializable
{

@SerializedName("href")
@Expose
private String href;
@SerializedName("items")
@Expose
private List<Item> items = null;
@SerializedName("metadata")
@Expose
private Metadata metadata;
@SerializedName("version")
@Expose
private String version;
private final static long serialVersionUID = 3477844863224020706L;

public String getHref() {
return href;
}

public void setHref(String href) {
this.href = href;
}

public List<Item> getItems() {
return items;
}

public void setItems(List<Item> items) {
this.items = items;
}

public Metadata getMetadata() {
return metadata;
}

public void setMetadata(Metadata metadata) {
this.metadata = metadata;
}

public String getVersion() {
return version;
}

public void setVersion(String version) {
this.version = version;
}

}