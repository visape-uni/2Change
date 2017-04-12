package pes.twochange.domain.model;

public class Profile extends Model {

    private String uid;
    private String name;
    private String surname;
    private PhoneNumber phoneNumber;
    private Address address;

    public Profile() {
    }

    public Profile(String uid, String name, String surname, PhoneNumber phoneNumber, Address address) {
        this.uid = uid;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.address = address;
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

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

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

    public static class PhoneNumber {

        private Integer suffix;
        private String number;

        public PhoneNumber() {
        }

        public PhoneNumber(Integer suffix, String number) {
            this.suffix = suffix;
            this.number = number;
        }

        public Integer getSuffix() {
            return suffix;
        }

        public void setSuffix(Integer suffix) {
            this.suffix = suffix;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        @Override
        public String toString() {
            return "(+" + suffix + ") " + number;
        }
    }

    @Override
    public String toString() {
        String phoneNumberString = (phoneNumber == null) ? "null" : phoneNumber.toString();
        String addressString = (address == null) ? "null" : address.toString();
        return "Profile{" +
                "id='" + getId() + '\'' +
                ", uid='" + uid + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phoneNumber='" + phoneNumberString + '\'' +
                ", address='" + addressString + '\'' +
                '}';
    }

    public String getFullName() {
        return name + " " + surname;
    }
}

// prueba2change@gmail.com
// PES_2Change
