package com.example.sqlitedatabaseapp;

public class Schools {
	  private long id;
	  private String schoolName;
	  private String averageTotal;
	  private String sector;
	  private String suburb;
	  private String state;
	  private String latitude;
	  private String longitude;
	  private String region;
	  private String icsea;
	  private String primarySecondary;
	  private String streetAddress;
	  private String fax;
	  private String email;
	  private String phone;  
	  private String postcode;

	    
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

	public String getSectorName() {
		if(sector.equalsIgnoreCase("G")){
			return "Government";
		}else if(sector.equalsIgnoreCase("N")){
			return "Non Government";
		}else{
			return "";
		}
	}
	
	public String getPrimarySecondaryName() {
		if(sector.equalsIgnoreCase("P")){
			return "Primary";
		}else if(sector.equalsIgnoreCase("S")){
			return "Secondary";
		}else {
			return "";
		}
	}
	
	public String getSnippet() {
		StringBuffer  strBuffSnippet= new StringBuffer();
		if(null != getSectorName()){
			strBuffSnippet.append(getSectorName());
			strBuffSnippet.append("\n"+"Rank:"+getId());
		}
		if(null != getStreetAddress()){
			strBuffSnippet.append("\n"+getStreetAddress());
		}
		if(null != getSuburb()){
			strBuffSnippet.append(" "+getSuburb());
		}
		if(null != getPostcode()){
			strBuffSnippet.append(" "+getPostcode());
		}	
		if(null != getEmail()){
			strBuffSnippet.append("\n"+getEmail());
		}
		if(null != getPhone()){
			strBuffSnippet.append("\n"+"Phone:"+getPhone());
		}	
		if(null != getFax()){
			strBuffSnippet.append(" "+"Fax:"+getFax());
		}	
		System.out.println(strBuffSnippet.toString());
		//return strBuffSnippet.append(getSectorName() == null ? "" : getSectorName()+"\n"+"Rank:"+getId()+"\n"+getStreetAddress() == null ? "" : getStreetAddress()+""+getSuburb() == null ? "" : getSuburb()+""+getPostcode() == null ? "" : getPostcode()+"\n"+getEmail() == null ? "" : getEmail()+"\n"+getPhone() == null ? "" : getPhone()+"\n"+getFax() == null ? "" : getFax()).toString();
		return strBuffSnippet.toString();
		//.snippet(schoolDetail.getSectorName()+"\n"+"Rank:"+schoolDetail.getId()+"\n"+schoolDetail.getStreetAddress()+" "+schoolDetail.getSuburb()+" "+schoolDetail.getPostcode()+" "+schoolDetail.getState()+"\n"+schoolDetail.getEmail()+" "+schoolDetail.getPhone()+" "+schoolDetail.getFax())
		/*if(sector.equalsIgnoreCase("P")){
			return "Primary";
		}else if(sector.equalsIgnoreCase("S")){
			return "Secondary";
		}else {
			return "";
		}*/
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

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getIcsea() {
		return icsea;
	}

	public void setIcsea(String icsea) {
		this.icsea = icsea;
	}

	public String getPrimarySecondary() {
		return primarySecondary;
	}

	public void setPrimarySecondary(String primarySecondary) {
		this.primarySecondary = primarySecondary;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	} 
