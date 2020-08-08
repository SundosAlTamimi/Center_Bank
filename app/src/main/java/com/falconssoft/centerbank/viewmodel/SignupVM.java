package com.falconssoft.centerbank.viewmodel;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.falconssoft.centerbank.BR;

public class SignupVM extends BaseObservable {

    private String gender;
    private String inactive;
    private String indate;

    private String birthDate;
    private String nationalID;
    private String firstName;
    private String secondName;
    private String thirdName;
    private String fourthName;
    private String username;
    private String address;
    private String email;
    private String password;
    private String searchPhone;
    private  int isRemember;
    private  int isNowActive;
    private String nationality;
    private String confirmPassword;

    @Bindable
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        notifyPropertyChanged(BR.confirmPassword);
    }

    @Bindable
    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
        notifyPropertyChanged(BR.nationality);
    }

    public int getIsNowActive() {
        return isNowActive;
    }

    public void setIsNowActive(int isNowActive) {
        this.isNowActive = isNowActive;
    }

    public int getIsRemember() {
        return isRemember;
    }

    public void setIsRemember(int isRemember) {
        this.isRemember = isRemember;
    }

    @Bindable
    public String getSearchPhone() {
        return searchPhone;
    }

    public void setSearchPhone(String searchPhone) {
        this.searchPhone = searchPhone;
        notifyPropertyChanged(BR.searchPhone);
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getInactive() {
        return inactive;
    }

    public void setInactive(String inactive) {
        this.inactive = inactive;
    }

    public String getIndate() {
        return indate;
    }

    public void setIndate(String indate) {
        this.indate = indate;
    }

    @Bindable
    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
        notifyPropertyChanged(BR.birthDate);
    }

    @Bindable
    public String getNationalID() {
        return nationalID;
    }

    public void setNationalID(String nationalID) {
        this.nationalID = nationalID;
        notifyPropertyChanged(BR.nationalID);
    }

    @Bindable
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        notifyPropertyChanged(BR.firstName);
    }

    @Bindable
    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
        notifyPropertyChanged(BR.secondName);
    }

    @Bindable
    public String getThirdName() {
        return thirdName;
    }

    public void setThirdName(String thirdName) {
        this.thirdName = thirdName;
        notifyPropertyChanged(BR.thirdName);
    }

    @Bindable
    public String getFourthName() {
        return fourthName;
    }

    public void setFourthName(String fourthName) {
        this.fourthName = fourthName;
        notifyPropertyChanged(BR.fourthName);
    }

    @Bindable
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        notifyPropertyChanged(BR.username);
    }

    @Bindable
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        notifyPropertyChanged(BR.address);
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }
}
