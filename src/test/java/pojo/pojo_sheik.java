package pojo;
import java.util.List;
public class pojo_sheik {

    private Place place;
    private Meta meta;

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public static class Place{
        private String name;
        private Location location;
        private Contact contact;
        private List<Address> addresses;
        private List<String> languages_supported;
        private List<Facility> facilities;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        public Contact getContact() {
            return contact;
        }

        public void setContact(Contact contact) {
            this.contact = contact;
        }

        public List<Address> getAddresses() {
            return addresses;
        }

        public void setAddresses(List<Address> addresses) {
            this.addresses = addresses;
        }

        public List<String> getLanguages_supported() {
            return languages_supported;
        }

        public void setLanguages_supported(List<String> languages_supported) {
            this.languages_supported = languages_supported;
        }

        public List<Facility> getFacilities() {
            return facilities;
        }

        public void setFacilities(List<Facility> facilities) {
            this.facilities = facilities;
        }

        public static class Location{
            private Coordinates coordinates;
            private int accuracy;


            public Coordinates getCoordinates() {
                return coordinates;
            }

            public void setCoordinates(Coordinates coordinates) {
                this.coordinates = coordinates;
            }

            public int getAccuracy() {
                return accuracy;
            }

            public void setAccuracy(int accuracy) {
                this.accuracy = accuracy;
            }
            public static class Coordinates{
                private double lat;
                private double lng;

                public double getLat() {
                    return lat;
                }

                public void setLat(double lat) {
                    this.lat = lat;
                }

                public double getLng() {
                    return lng;
                }

                public void setLng(double lng) {
                    this.lng = lng;
                }
            }
        }
        public static class Contact{
            private Phone phone;
            private String email;
            private String website;

            public Phone getPhone() {
                return phone;
            }

            public void setPhone(Phone phone) {
                this.phone = phone;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getWebsite() {
                return website;
            }

            public void setWebsite(String website) {
                this.website = website;
            }
            public static class Phone{
                private String number;
                private String countryCode;

                public String getNumber() {
                    return number;
                }

                public void setNumber(String number) {
                    this.number = number;
                }

                public String getCountryCode() {
                    return countryCode;
                }

                public void setCountryCode(String countryCode) {
                    this.countryCode = countryCode;
                }
            }
        }
        public static class Address{
            private String type;
            private String doorNo;
            private String street;
            private String area;
            private String city;
            private String state;
            private String pincode;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getDoorNo() {
                return doorNo;
            }

            public void setDoorNo(String doorNo) {
                this.doorNo = doorNo;
            }

            public String getStreet() {
                return street;
            }

            public void setStreet(String street) {
                this.street = street;
            }

            public String getArea() {
                return area;
            }

            public void setArea(String area) {
                this.area = area;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public String getPincode() {
                return pincode;
            }

            public void setPincode(String pincode) {
                this.pincode = pincode;
            }
        }
        public static class Facility{
            private String name;
            private String type;
            private OpenHours openHours;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public OpenHours getOpenHours() {
                return openHours;
            }

            public void setOpenHours(OpenHours openHours) {
                this.openHours = openHours;
            }

            public static class OpenHours{
                private String weekdays;
                private String weekends;
                private String daily;
                public String getWeekdays() {
                    return weekdays;
                }

                public void setWeekdays(String weekdays) {
                    this.weekdays = weekdays;
                }

                public String getWeekends() {
                    return weekends;
                }

                public void setWeekends(String weekends) {
                    this.weekends = weekends;
                }
                public String getDaily() { return daily; }
                public void setDaily(String daily) { this.daily = daily; }

            }
        }

    }
    public static class Meta{
        private String timestamp;
        private boolean verified;
        private String source;

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public boolean isVerified() {
            return verified;
        }

        public void setVerified(boolean verified) {
            this.verified = verified;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }
    }
}
