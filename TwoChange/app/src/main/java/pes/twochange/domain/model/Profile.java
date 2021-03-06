package pes.twochange.domain.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Profile {

    private String username;
    private String uid;
    private String name;
    private String surname;
    private String phoneNumber;
    private Address address;
    private float rate;
    private int numRates;
    private boolean notifications;


    public Profile() {
    }

    public Profile(String username, String uid, String name, String surname, String phoneNumber, Address address) {
        this.username = username;
        this.uid = uid;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.rate = -1; //user no rated
        this.numRates = 0; //0 users rate this user
        this.notifications = true;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public float getRate() {
        return this.rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public int getNumRates () {
        return this.numRates;
    }

    public void setNumRate(int numRates) {
        this.numRates = numRates;
    }

    public void incNumRates () {
        ++this.numRates;
    }

    public boolean getNotifications(){return this.notifications;}

    public void setNotifications(boolean notifications) {this.notifications = notifications;}

    public static class Address {

        private String address;
        private String zipCode;
        private String town;
        private String state;
        private String country;

        public Address() {
        }

        public Address(String address, String zipCode, String town, String state, String country) {
            this.address = address;
            this.zipCode = zipCode;
            this.town = town;
            this.state = state;
            this.country = country;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getZipCode() {
            return zipCode;
        }

        public void setZipCode(String zipCode) {
            this.zipCode = zipCode;
        }

        public String getTown() {
            return town;
        }

        public void setTown(String town) {
            this.town = town;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        @Override
        public String toString() {
            return address + ", " + zipCode + ", " +
                    town + ", " +  state + ", " + country;
        }
    }


    @Override
    public String toString() {
        String addressString = (address == null) ? "null" : address.toString();
        return "Profile{" +
                "username='" + username + '\'' +
                ", uid='" + uid + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + addressString + '\'' +
                '}';
    }

    public String fullName() {
        return name + " " + surname;
    }
}

// prueba2change@gmail.com
// PES_2Change
