package com.superbschools.mobile;

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
	  private String website;
	  private String malefemale;
	  private String singlecoed;
	  private int rankPrimary;
	  private int rankSecondary;
	  private String reading;
	  private String grampunc;
	  private String numeracy;
	  private String writing;
	  private String spelling;
	  	    
	  public long getId() {
	    return id;
	  }

	  public void setId(long id) {
	    this.id = id;
	  }


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
		if(primarySecondary.equalsIgnoreCase("P")){
			return "Primary";
		}else if(primarySecondary.equalsIgnoreCase("S")){
			return "Secondary";
		}else {
			return "";
		}
	}
	
	public String getSnippet() {
		StringBuffer  strBuffSnippet= new StringBuffer();
		if(null != getPrimarySecondary()){
			strBuffSnippet.append(getPrimarySecondaryName());
		}
		if(null != getSectorName()){
			strBuffSnippet.append(","+getSectorName());
		}
		if(0 != getRankPrimary()){
			strBuffSnippet.append("\n"+"Rank Primary:"+getRankPrimary());
		}
		if(0 != getRankSecondary()){
			strBuffSnippet.append("\n"+"Rank Secondary:"+getRankSecondary());
		}
		if(null != getStreetAddress()){
			strBuffSnippet.append("\n"+getStreetAddress());
		}
		if(null != getSuburb()){
			strBuffSnippet.append(" "+getSuburb());
		}
		if(null != getState()){
			strBuffSnippet.append(" "+getState());
		}		
		if(null != getPostcode()){
			strBuffSnippet.append(" "+getPostcode());
		}	
		if(!("NA".equalsIgnoreCase(getEmail()))){
			strBuffSnippet.append("\n"+getEmail());
		}
		if(!("NA".equalsIgnoreCase(getPhone()))){
			strBuffSnippet.append("\n"+"Phone:"+getPhone());
		}	
		if(!("NA".equalsIgnoreCase(getFax()))){
			strBuffSnippet.append(" "+"Fax:"+getFax());
		}	
		if(!("NA".equalsIgnoreCase(getWebsite()))){
			strBuffSnippet.append("\n"+"WebSite:"+getWebsite());
		}
		return strBuffSnippet.toString();

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

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getMalefemale() {
		return malefemale;
	}

	public void setMalefemale(String malefemale) {
		this.malefemale = malefemale;
	}

	public String getSinglecoed() {
		return singlecoed;
	}

	public void setSinglecoed(String singlecoed) {
		this.singlecoed = singlecoed;
	}

/*	public String getRankPrimary() {
		return rankPrimary;
	}

	public void setRankPrimary(String rankPrimary) {
		this.rankPrimary = rankPrimary;
	}

	public String getRankSecondary() {
		return rankSecondary;
	}

	public void setRankSecondary(String rankSecondary) {
		this.rankSecondary = rankSecondary;
	}*/

	public String getReading() {
		return reading;
	}

	public void setReading(String reading) {
		this.reading = reading;
	}

	public String getGrampunc() {
		return grampunc;
	}

	public void setGrampunc(String grampunc) {
		this.grampunc = grampunc;
	}

	public String getNumeracy() {
		return numeracy;
	}

	public void setNumeracy(String numeracy) {
		this.numeracy = numeracy;
	}

	public String getWriting() {
		return writing;
	}

	public void setWriting(String writing) {
		this.writing = writing;
	}

	public String getSpelling() {
		return spelling;
	}

	public void setSpelling(String spelling) {
		this.spelling = spelling;
	}

	public int getRankPrimary() {
		return rankPrimary;
	}

	public void setRankPrimary(int rankPrimary) {
		this.rankPrimary = rankPrimary;
	}

	public int getRankSecondary() {
		return rankSecondary;
	}

	public void setRankSecondary(int rankSecondary) {
		this.rankSecondary = rankSecondary;
	}
	} 
