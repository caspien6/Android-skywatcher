package hu.bme.aut.skywatcher.model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum implements Serializable
{

@SerializedName("description")
@Expose
private String description;
@SerializedName("title")
@Expose
private String title;
@SerializedName("nasa_id")
@Expose
private String nasaId;
@SerializedName("media_type")
@Expose
private String mediaType;
@SerializedName("center")
@Expose
private String center;
@SerializedName("location")
@Expose
private String location;
@SerializedName("date_created")
@Expose
private String dateCreated;
@SerializedName("keywords")
@Expose
private List<String> keywords = null;
private final static long serialVersionUID = -2044438415254184983L;

public String getDescription() {
return description;
}

public void setDescription(String description) {
this.description = description;
}

public String getTitle() {
return title;
}

public void setTitle(String title) {
this.title = title;
}

public String getNasaId() {
return nasaId;
}

public void setNasaId(String nasaId) {
this.nasaId = nasaId;
}

public String getMediaType() {
return mediaType;
}

public void setMediaType(String mediaType) {
this.mediaType = mediaType;
}

public String getCenter() {
return center;
}

public void setCenter(String center) {
this.center = center;
}

public String getLocation() {
return location;
}

public void setLocation(String location) {
this.location = location;
}

public String getDateCreated() {
return dateCreated;
}

public void setDateCreated(String dateCreated) {
this.dateCreated = dateCreated;
}

public List<String> getKeywords() {
return keywords;
}

public void setKeywords(List<String> keywords) {
this.keywords = keywords;
}

}