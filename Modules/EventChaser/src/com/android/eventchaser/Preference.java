package com.android.eventchaser;

import org.json.JSONException;
import org.json.JSONObject;

public class Preference {
	
	private static final String JSON_ID = "id";
    private static final String JSON_NAME = "name";
    private static final String JSON_CHECKED = "checked";

	private String name = "" ;
    private String id;
	private boolean checked = false ;

	
	  public Preference(JSONObject json) throws JSONException {
	        id = json.getString(JSON_ID);
	        if (json.has(JSON_NAME)) {
	            name = json.getString(JSON_NAME);
	        } if (json.has(JSON_CHECKED)) {
	        	checked = json.getBoolean(JSON_CHECKED);
	        }
	    }
    
    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_ID, id);
        json.put(JSON_NAME, name);
        json.put(JSON_CHECKED, checked);
        return json;
    }

    
    public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
    public Preference() {}
    public Preference( String name, String id) {
      this.name = name ;
      this.id = id;
    }
    public Preference( String name, boolean checked ) {
      this.name = name ;
      this.checked = checked ;
    }
    
    public Preference( String name, String id, boolean isChecked) {
        this.name = name ;
        this.id = id;
        this.checked = isChecked;
      }
    
    public String getName() {
      return name;
    }
    public void setName(String name) {
      this.name = name;
    }
    public boolean isChecked() {
      return checked;
    }
    public void setChecked(boolean checked) {
      this.checked = checked;
    }
    public String toString() {
      return "name:" + name + " id:" + id + " isChecked:" + checked; 
    }
    public void toggleChecked() {
      checked = !checked ;
    }
}
