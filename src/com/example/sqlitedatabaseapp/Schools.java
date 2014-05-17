package com.example.sqlitedatabaseapp;

public class Schools {
	  private long id;
	  private String schoolName;
	  private String averageTotal;
	  private String sector;
	  private String suburb;
	  private String state;
	  
	  public long getId() {
	    return id;
	  }

	  public void setId(long id) {
	    this.id = id;
	  }

	

	  // Will be used by the ArrayAdapter in the ListView
	  @Override
	  public String toString() {
	    return  id +"  " + schoolName +"\n"+ suburb +"  " + state;
	  }

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getAverageTotal() {
		return averageTotal;
	}

	public void setAverageTotal(String averageTotal) {
		this.averageTotal = averageTotal;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public String getSuburb() {
		return suburb;
	}

	public void setSuburb(String suburb) {
		this.suburb = suburb;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	} 
