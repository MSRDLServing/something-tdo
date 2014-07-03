package com.example.somethingtdo;

public class Preference {
	private String name = "" ;
    private String id;
    
    public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	private boolean checked = false ;
    public Preference() {}
    public Preference( String name, String id) {
      this.name = name ;
      this.id = id;
    }
    public Preference( String name, boolean checked ) {
      this.name = name ;
      this.checked = checked ;
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
      return name ; 
    }
    public void toggleChecked() {
      checked = !checked ;
    }
}
