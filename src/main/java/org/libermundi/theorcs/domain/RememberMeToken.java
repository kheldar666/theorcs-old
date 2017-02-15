package org.libermundi.theorcs.domain;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.libermundi.theorcs.domain.base.NumericIdEntity;

@Entity(name="RememberMeToken")
@Table(name="tbl_token")
public class RememberMeToken extends NumericIdEntity {
	public static final String PROP_USERNAME="username";
	public static final String PROP_SERIES="series";
	public static final String PROP_TOKENVALUE="tokenValue";
	public static final String PROP_DATE="date";
	
	private static final long serialVersionUID = 4252909113245060846L;
	private String _username;
    private String _series;
    private String _tokenValue;
    private Date _date;
    
    public RememberMeToken() {
    	//Public Default Constructor
    }

    public RememberMeToken(String username, String series, String tokenValue, Date date) {
        this._username = username;
        this._series = series;
        this._tokenValue = tokenValue;
        this._date = new Date(date.getTime());
    }

    @Basic
	@Column(name=RememberMeToken.PROP_USERNAME,length=30,nullable=false)
    public String getUsername() {
        return _username;
    }

    @Basic
	@Column(name=RememberMeToken.PROP_SERIES,length=89,unique=true,nullable=false)
    public String getSeries() {
        return _series;
    }

    @Basic
	@Column(name=RememberMeToken.PROP_TOKENVALUE,length=89,nullable=false)
    public String getTokenValue() {
        return _tokenValue;
    }


	@Basic
	@Column(name=RememberMeToken.PROP_DATE, nullable=false)
    public Date getDate() {
        return _date;
    }

	public void setUsername(String username) {
		_username=username;
	}

	public void setSeries(String series) {
		_series=series;
	}

	public void setTokenValue(String tokenValue) {
		_tokenValue=tokenValue;
	}

	public void setDate(Date date) {
		_date=new Date(date.getTime());
	}
    
	@Override
	public String toString() {
		return "RememberMeToken [_username=" + _username + ", _series="
				+ _series + ", _tokenValue=" + _tokenValue + ", _date=" + _date
				+ "]";
	}
}