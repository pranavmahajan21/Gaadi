package com.mw.gaadi.model;

import java.io.Serializable;

import com.parse.ParseUser;

import android.location.Address;

public class ServiceCenter implements Serializable {

	private static final long serialVersionUID = -1023259802815413813L;

	String objectID;

	String name;
	String address;
	String phoneNo;
	Address address2;

	ParseUser owner;

	public ServiceCenter(String objectID, String name, String address,
			String phoneNo, Address address2, ParseUser owner) {
		super();
		this.objectID = objectID;
		this.name = name;
		this.address = address;
		this.phoneNo = phoneNo;
		this.address2 = address2;
		this.owner = owner;
	}

	public String getObjectID() {
		return objectID;
	}

	public void setObjectID(String objectID) {
		this.objectID = objectID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public Address getAddress2() {
		return address2;
	}

	public void setAddress2(Address address2) {
		this.address2 = address2;
	}

	public ParseUser getOwner() {
		return owner;
	}

	public void setOwner(ParseUser owner) {
		this.owner = owner;
	}

}
