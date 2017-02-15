package org.libermundi.theorcs.domain;

import java.io.Serializable;
import java.util.Locale;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

@Embeddable
@XmlRootElement(name="address")
public final class Address implements Serializable {
	static final String PROD_ADDRESS1 = "address1";
	static final String PROD_ADDRESS2 = "address2";
	static final String PROD_CITY = "city";
	static final String PROD_COUNTRY_ISO = "countryISO";
	static final String PROD_STATE = "state";
	static final String PROD_ZIPCODE = "zipcode";	

	private static final long serialVersionUID = 9164655218925887943L;
	
	private String _address1;

	private String _address2;

	private String _city;

	private String _zipcode;

	private String _state;

	private String _countryIso;

	/**
	 * Getter 
	 * @return address1
	 */
	@Basic
	@Column(name=Address.PROD_ADDRESS1,length=255,nullable=true)
	public String getAddress1() {
		return _address1;
	}

	/**
	 * @return address2
	 */
	@Basic
	@Column(name=Address.PROD_ADDRESS2,length=255,nullable=true)
	public String getAddress2() {
		return _address2;
	}

	/**
	 * @return city
	 */
	@Basic
	@Column(name=Address.PROD_CITY,length=50,nullable=true)
	public String getCity() {
		return _city;
	}

	/**
	 * @return country
	 */
	@Basic
	@Column(name=Address.PROD_COUNTRY_ISO,length=2,nullable=true)
	public String getCountryISO() {
		return _countryIso;
	}

	@Transient
	public String getCountry(Locale locale) {
		Locale loc = new Locale(locale.getLanguage(), getCountryISO());
		return loc.getDisplayCountry();
	}

	
	/**
	 * @return state
	 */
	@Basic
	@Column(name=Address.PROD_STATE,length=50,nullable=true)
	public String getState() {
		return _state;
	}

	/**
	 * @return zipcode
	 */
	@Basic
	@Column(name=Address.PROD_ZIPCODE,length=10,nullable=true)
	public String getZipcode() {
		return _zipcode;
	}
	
	/**
	 * @param address1
	 */
	public void setAddress1(String address1) {
		this._address1 = address1;
	}

	/**
	 * @param address2
	 */
	public void setAddress2(String address2) {
		this._address2 = address2;
	}

	/**
	 * @param city
	 */
	public void setCity(String city) {
		this._city = city;
	}

	/**
	 * @param country
	 */
	public void setCountryISO(String iso3) {
		this._countryIso = iso3;
	}

	/**
	 * @param state
	 */
	public void setState(String state) {
		this._state = state;
	}

	/**
	 * @param zipcode
	 */
	public void setZipcode(String zipcode) {
		this._zipcode = zipcode;
	}
	
	@Override
	public String toString() {
		return "[Adress [address1="+ getAddress1()+ "][address2="+ getAddress2()+ "][City="+ getCity()+ "]]";
	}	
}
