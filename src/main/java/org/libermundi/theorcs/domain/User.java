package org.libermundi.theorcs.domain;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.libermundi.theorcs.domain.base.Account;
import org.libermundi.theorcs.domain.base.Gender;
import org.libermundi.theorcs.domain.base.Labelable;
import org.libermundi.theorcs.repositories.listeners.PasswordListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.google.common.collect.Sets;


/**
 * User Model Object. 
 * Serializable for Hibernate
 */
@Entity(name="User")
@Table(name="tbl_users")
@EntityListeners({PasswordListener.class})
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class User extends UidUserStatefulEntity implements UserDetails, Account, Labelable {
	public final static String PROP_AVATAR="avatar";
	public final static String PROP_DESCRIPTION="description";
	public final static String PROP_THUMBNAIL="thumbnail";
	public final static String PROP_CELLULARNUMBER="cellularNumber";
	public final static String PROP_PHONENUMBER="phoneNumber";
	public final static String PROP_ADDRESS="address";
	public final static String PROP_EMAIL="email";
	public final static String PROP_FIRSTNAME="firstName";
	public final static String PROP_LASTNAME="lastName";
	public final static String PROP_GENDER="gender";
	public final static String PROP_SALUTATION="salutation";
	public final static String PROP_NICKNAME="nickName";
	public final static String PROP_USERNAME="username";
	public final static String PROP_PASSWORD = "password";
	public final static String PROP_ACCOUNT_NON_EXPIRED = "accountNonExpired";
	public final static String PROP_CREDENTIALS_NON_EXPIRED = "credentialsNonExpired";
	public final static String PROP_ACCOUNT_NON_LOCKED = "accountNonLocked";
	public final static String PROP_ACTIVATION_KEY = "activationKey";
	
	private static final long serialVersionUID = 984592116709403247L;

	private Gender gender=Gender.BOTH;
	private Salutation salutation=Salutation.NONE;
	private String firstName;
	private String lastName;
	private String nickName;
	private String username;
	private String password;
	private String email;
	private String avatar;
	private String description;
	private String thumbnail;
	private String phoneNumber;
	private String cellularNumber;
	private String activationKey;
	private Set<Authority> authorities = Sets.newHashSet();
	private Address address;
	private boolean accountNonExpired;
	private boolean credentialsNonExpired;
	private boolean accountNonLocked;

	@Override
	@Basic
	@Column(name=User.PROP_USERNAME,length=30,unique=true,nullable=false)
	public String getUsername() {
		return username;
	}

	@Override
	@Basic
	@Column(name=User.PROP_PASSWORD,length=60,nullable=false)
	@XmlTransient
	public String getPassword() {
		return password;
	}

	@Basic
	@Column(name=User.PROP_AVATAR,length=255,nullable=true)
	public String getAvatar() {
		return avatar;
	}

	@Basic
	@Column(name=User.PROP_THUMBNAIL,length=255,nullable=true)
	public String getThumbnail() {
		return thumbnail;
	}

	@Basic
	@Column(name=User.PROP_CELLULARNUMBER,length=25,nullable=true)
	public String getCellularNumber() {
		return cellularNumber;
	}

	@Basic
	@Column(name=User.PROP_PHONENUMBER,length=25,nullable=true)
	public String getPhoneNumber() {
		return phoneNumber;
	}

	@Embedded
	public Address getAddress() {
		return address;
	}

	@Override
	@Basic
	@Column(name=User.PROP_EMAIL,length=255,unique=true,nullable=false)
	public String getEmail() {
		return email;
	}

	@Basic
	@Column(name=User.PROP_FIRSTNAME,length=30,nullable=true)
	public String getFirstName() {
		return firstName;
	}

	@Basic
	@Column(name=User.PROP_LASTNAME,length=50,nullable=true)
	public String getLastName() {
		return lastName;
	}

	@Enumerated(EnumType.STRING)
	@Column(name=User.PROP_GENDER,length=7,nullable=false)
	public Gender getGender() {
		return gender;
	}
	
	@Enumerated(EnumType.STRING)
	@Column(name=User.PROP_SALUTATION,length=4,nullable=false)
	public Salutation getSalutation() {
		return salutation;
	}
	
	@Basic
	@Column(name=User.PROP_NICKNAME,length=20,unique=true,nullable=true)
	public String getNickName() {
		return nickName;
	}

	@Lob
	@Column(name=User.PROP_DESCRIPTION,nullable=true)
	public String getDescription() {
		return description;
	}	
	
	@Transient
	public boolean isEnabled() {
		// Comes from Spring Security... Redundant with isActive.
		return isActive();
	}

    @ManyToMany(
    		cascade={CascadeType.PERSIST,CascadeType.REFRESH},
    		fetch=FetchType.LAZY
    )    
    @JoinTable(name="tbl_user2authorities",joinColumns={@JoinColumn(name="userId")},inverseJoinColumns={@JoinColumn(name="authorityId")})
    @LazyCollection(LazyCollectionOption.FALSE)
	@Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@XmlTransient
	public Set<Authority> getRoles() {
		return authorities;
	}
	
	
	/* (non-Javadoc)
	 * @see org.springframework.security.userdetails.UserDetails#getAuthorities()
	 */
	@Override
	@Transient
	@XmlTransient
	public Collection<GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> auth = Sets.newHashSet();
		for (GrantedAuthority grantedAuthority : authorities) {
			auth.add(new SimpleGrantedAuthority(grantedAuthority.getAuthority())); // This way we make sure we compare always SimpleGrantedAuthority in equals methods.
		}
		return auth;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.userdetails.UserDetails#isAccountNonExpired()
	 */
	@Override
	@Basic
	@Column(name=User.PROP_ACCOUNT_NON_EXPIRED)
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.security.userdetails.UserDetails#isAccountNonLocked()
	 */
	@Override
	@Basic
	@Column(name=User.PROP_ACCOUNT_NON_LOCKED)
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.security.userdetails.UserDetails#isCredentialsNonExpired()
	 */
	@Override
	@Basic
	@Column(name=User.PROP_CREDENTIALS_NON_EXPIRED)
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	@Basic
    @Column(name = User.PROP_ACTIVATION_KEY,length=36)
	public String getActivationKey() {
		return activationKey;
	}
	
	@Transient
	public String getFullName() {
		return getFirstName() + " " + getLastName();
	}
	
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}	
	
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}	
	
	public void setCellularNumber(String cellularNumber) {
		this.cellularNumber = cellularNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public void setAddress(Address address) {
		this.address = address;
	}
	
	public void setEmail(String email) {
		this.email = email.toLowerCase();
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}
	
	public void setSalutation(Salutation salutation) {
		this.salutation = salutation;
	}
	
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public void setEnabled(boolean enabled) {
		// Comes from Spring Security... Redundant with isActive.
		setActive(enabled);
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setRoles(Set<Authority> authorities) {
		this.authorities = authorities;
	}
	
	public void setActivationKey(String key){
		this.activationKey = key;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}
	
	public String resetActivationKey(){
		activationKey = UUID.randomUUID().toString();
		return activationKey;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "[User [Id="+ getId()+ "][Uid="+ getUid()+ "][FullName="+ getFullName()+ "][Email="+ getEmail()+ "]]";
	}

	/* (non-Javadoc)
	 * @see org.libermundi.theorcs.core.model.base.Labelable#printLabel()
	 */
	@Override
	public String printLabel() {
		return getFullName();
	}

}
