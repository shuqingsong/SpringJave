package com.sqs.helloworld;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class HelloWorld1 {
	
	private String message;
	private String message1;
	private List addressList;
	private Set addressSet;
	private Map addressMap;

	public void setMessage(String message){
		this.message  = message;
	}
		   
	public void getMessage(){
		System.out.println("Your Message : " + message);
	}
		   
	public void setMessage1(String message1){
		this.message1  = message1;
	}
	   
	public void getMessage1(){
		System.out.println("Your Message1 : " + message1);
	}

	public List getAddressList() {
		System.out.println("Your AddressList :"  + addressList);
		return addressList;
	}

	public void setAddressList(List addressList) {
		this.addressList = addressList;
	}

	public Set getAddressSet() {
		System.out.println("Your AddressSet :"  + addressSet);
		return addressSet;
	}

	public void setAddressSet(Set addressSet) {
		this.addressSet = addressSet;
	}

	public Map getAddressMap() {
		System.out.println("Your AddressMap :"  + addressMap);
		return addressMap;
	}

	public void setAddressMap(Map addressMap) {
		this.addressMap = addressMap;
	}
	 
}