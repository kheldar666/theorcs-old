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
	private String username;
    private String series;
    private String tokenValue;
    private Date date;
    
    public RememberMeToken() {
    	//Public Default Constructor
    }

    public RememberMeToken(String username, String series, String tokenValue, Date date) {
        this.username = username;
        this.series = series;
        this.tokenValue = tokenValue;
        this.date = new Date(date.getTime());
    }

    @Basic
	@Column(name=RememberMeToken.PROP_USERNAME,length=30,nullable=false)
    public String getUsername() {
        return username;
    }

    @Basic
	@Column(name=RememberMeToken.PROP_SERIES,length=89,unique=true,nullable=false)
    public String getSeries() {
        return series;
    }

    @Basic
	@Column(name=RememberMeToken.PROP_TOKENVALUE,length=89,nullable=false)
    public String getTokenValue() {
        return tokenValue;
    }


	@Basic
	@Column(name=RememberMeToken.PROP_DATE, nullable=false)
    public Date getDate() {
        return date;
    }

	public void setUsername(String username) {
		this.username=username;
	}

	public void setSeries(String series) {
		this.series=series;
	}

	public void setTokenValue(String tokenValue) {
		this.tokenValue=tokenValue;
	}

	public void setDate(Date date) {
		this.date=new Date(date.getTime());
	}
    
	@Override
	public String toString() {
		return "RememberMeToken [_username=" + username + ", _series="
				+ series + ", _tokenValue=" + tokenValue + ", _date=" + date
				+ "]";
	}
}