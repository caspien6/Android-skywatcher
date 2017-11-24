package hu.bme.aut.skywatcher.model;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Link implements Serializable
{

@SerializedName("href")
@Expose
private String href;
@SerializedName("render")
@Expose
private String render;
@SerializedName("rel")
@Expose
private String rel;
private final static long serialVersionUID = 6931459625913578461L;

public String getHref() {
return href;
}

public void setHref(String href) {
this.href = href;
}

public String getRender() {
return render;
}

public void setRender(String render) {
this.render = render;
}

public String getRel() {
return rel;
}

public void setRel(String rel) {
this.rel = rel;
}

}