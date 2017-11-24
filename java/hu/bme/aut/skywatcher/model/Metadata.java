package hu.bme.aut.skywatcher.model;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Metadata implements Serializable
{

@SerializedName("total_hits")
@Expose
private Integer totalHits;
private final static long serialVersionUID = 4626588740796135902L;

public Integer getTotalHits() {
return totalHits;
}

public void setTotalHits(Integer totalHits) {
this.totalHits = totalHits;
}

}