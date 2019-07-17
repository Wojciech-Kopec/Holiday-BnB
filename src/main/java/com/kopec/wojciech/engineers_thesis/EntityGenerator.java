package com.kopec.wojciech.engineers_thesis;

import com.google.common.collect.Lists;
import com.kopec.wojciech.engineers_thesis.model.*;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.*;

public class EntityGenerator {
    private static final EntityGenerator instance = new EntityGenerator();

    private final Random random = new Random();
    private int currentNo = 0;

    //Generators
    private final UserGenerator userGenerator = new UserGenerator();
    private final AccommodationGenerator accommodationGenerator = new AccommodationGenerator();
    private final BookingGenerator bookingGenerator = new BookingGenerator();

    //Entities
    private List<User> owners = userGenerator.generate(5);
    private List<User> clients = userGenerator.generate(7);
    private List<Accommodation> accommodations;
    private List<Booking> bookings;


    private EntityGenerator() {
    }

    public static EntityGenerator getInstance() {
        return instance;
    }

    public List<User> getOwners() {
        return owners;
    }

    public List<User> getClients() {
        return clients;
    }

    public List<Accommodation> getAccommodations() {
        //Lazy initialization - User cannot be set before persist
        if (accommodations == null)
            accommodations = accommodationGenerator.generate();
        return accommodations;
    }

    public List<Booking> getBookings() {
        //Lazy initialization - User/Accommodation cannot be set before persist
        if (bookings == null)
            bookings = bookingGenerator.generate(25);
        return bookings;
    }

    public void setOwners(List<User> owners) {
        this.owners = owners;
    }

    public void setClients(List<User> clients) {
        this.clients = clients;
    }

    public void setAccommodations(List<Accommodation> accommodations) {
        this.accommodations = accommodations;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    private class UserGenerator {

        private User.UserBuilder defaultUserBuild() {
            return User.builder()
                    .phoneNumber(generatePhoneNumber())
                    .password("password");
        }

        private String generatePhoneNumber() {
            Random rand = random;
            int num1 = (rand.nextInt(7) + 1) * 100 + (rand.nextInt(8) * 10) + rand.nextInt(8);
            int num2 = rand.nextInt(743);
            int num3 = rand.nextInt(10000);

            DecimalFormat df = new DecimalFormat("000"); // 3 zeros
            return df.format(num1) + df.format(num2) + df.format(num3);
        }

        private User buildUser(String firstName, String lastName, String username, String email) {
            return defaultUserBuild()
                    .firstName(firstName)
                    .lastName(lastName)
                    .username(username)
                    .email(email)
                    .build();
        }

        private List<User> generate(int count) {
            List<User> users = new ArrayList<>();
            String[] firstNames = firstNames();
            String[] lastNames = lastNames();

            for (int i = 0; i < count; i++) {
                String firstName = firstNames[random.nextInt(firstNames.length)];
                String lastName = lastNames[random.nextInt(lastNames.length)];
                int randomInt = random.nextInt(100);
                String username = firstName + lastName + randomInt;
                String email = (firstName + "." + lastName + randomInt + "@mail.com").toLowerCase();

                users.add(buildUser(firstName, lastName, username, email));
            }
            return users;
        }

        private String[] lastNames() {
            return new String[]{"Smith", "Patton", "Johnson", "Brown", "Wilson", "Miller", "Jones", "Davis",
                    "Pryce", "Morgan", "Jenkins", "Anderson", "Taylor", "Cooper"};
        }

        private String[] firstNames() {
            return new String[]{"John", "Josh", "Robert", "Jacob", "Andrew", "Nathan", "Paul", "George"};
        }
    }

    private class AccommodationGenerator {

        private Accommodation.AccommodationBuilder defaultAccommodationBuild() {
            Accommodation.AccommodationType[] types = Accommodation.AccommodationType.values();
            int index = currentNo > types.length - 1 ? currentNo % types.length : currentNo;
            currentNo++;
            return Accommodation.builder()
                    .accommodationType(types[index])
                    .maxGuests(random.nextInt(7) + 2) //range(2,8)
                    .pricePerNight(((random.nextInt(36) + 1) * 10) + 40) //range(50, 400, step: 10)
                    .amenities(getRandomAmenities());
        }

        private List<Amenity> getRandomAmenities() {
            int size = random.nextInt(4) + 2; //range(2,5)
            List<Amenity> randomAmenities = new ArrayList<>();
            List<Amenity> generated = generateAmenities();
            for (int i = 0; i < size; i++) {
                int index = random.nextInt(generated.size());
                randomAmenities.add(generated.get(index));
                generated.remove(index);
            }
            return randomAmenities;
        }

        private List<Amenity> generateAmenities() {
            return Lists.newArrayList(
                    buildAmenity(Amenity.AmenityType.BACKYARD, "Spacious yard just behind the property"),
                    buildAmenity(Amenity.AmenityType.TV, "Big TV set with cable in property"),
                    buildAmenity(Amenity.AmenityType.OTHER, "Roof of property is fully accessible for guests"),
                    buildAmenity(Amenity.AmenityType.WIFI, "Fast internet connection"),
                    buildAmenity(Amenity.AmenityType.KITCHEN, "Fully functional kitchen"),
                    buildAmenity(Amenity.AmenityType.AC, "Air-conditioning included"),
                    buildAmenity(Amenity.AmenityType.PARKING, "Parking for vehicles"),
                    buildAmenity(Amenity.AmenityType.POOL, "Outdoor pool available"),
                    buildAmenity(Amenity.AmenityType.TERRACE, "Spacious terrace/balcony"),
                    buildAmenity(Amenity.AmenityType.SAUNA, "Sauna room accessible"),
                    buildAmenity(Amenity.AmenityType.OTHER, "Mini-bar fully equipped"),
                    buildAmenity(Amenity.AmenityType.OTHER, "SPA facility in premises")
            );
        }

        private Amenity buildAmenity(Amenity.AmenityType type, String description) {
            return Amenity.builder().type(type).description(description).build();
        }

        private Accommodation buildAccommodation(User user, Localization localization, String name, String
                description) {
            return defaultAccommodationBuild()
                    .name(name)
                    .description(description)
                    .user(user)
                    .localization(localization)
                    .build();
        }

        //Count hardcoded at 10
        private List<Accommodation> generate() {
            List<Accommodation> accommodations = new ArrayList<>();

            //Owner no.1
            accommodations.add(
                    buildAccommodation(owners.get(0),
                            buildLocalization("Poland", "Warszawa", "Rondo Daszyńskiego 16c/8"),
                            "MKI Apartments Warsaw", "Situated in Warsaw, 7 km from Frideric Chopin's Monument and 7 " +
                                    "km from Royal " +
                                    "Łazienki " +
                                    "Park, MKI Apartments - Wynalazek 2 features air-conditioned accommodation with a" +
                                    " balcony and free WiFi. The property features city views and is 7 km from " +
                                    "Ujazdowski Park and 8 km from Złote Tarasy Shopping Centre.\n" +
                                    "\n" +
                                    "The apartment has 1 bedroom, a flat-screen TV with satellite channels, an " +
                                    "equipped kitchen with a dishwasher and a microwave, a washing machine, and 1 " +
                                    "bathroom with a shower.\n" +
                                    "\n" +
                                    "Languages spoken at the 24-hour front desk include English, Spanish, Polish and " +
                                    "Russian.\n" +
                                    "\n" +
                                    "A bicycle rental service is available at the apartment.\n" +
                                    "\n" +
                                    "The nearest airport is Warsaw Frederic Chopin, 2.5 km from MKI Apartments - " +
                                    "Wynalazek 2, and the property offers a paid airport shuttle service.\n" +
                                    "\n" +
                                    "Mokotów is a great choice for travellers interested in convenient public " +
                                    "transport, parks and shopping.\n" +
                                    "\n" +
                                    "We speak your language!\n" +
                                    "\n" +
                                    "MKI Apartments - Wynalazek 2 has been welcoming Booking.com guests since 21 Apr " +
                                    "2019.")
            );
            accommodations.add(
                    buildAccommodation(owners.get(0),
                            buildLocalization("Poland", "Warszawa", "Mazowiecka 81"),
                            "Apartament Airport Suite", "Within 6 km of Frideric Chopin's Monument and 7 km of Royal " +
                                    "Łazienki Park, " +
                                    "Apartament Airport Obrzeżna 3 offers free WiFi and a garden. Providing private " +
                                    "parking, the apartment is 7 km from Ujazdowski Park.\n" +
                                    "\n" +
                                    "Offering a patio with city views, this apartment also has a cable flat-screen " +
                                    "TV, a well-equipped kitchen with a dishwasher, a microwave and a fridge, as well" +
                                    " as 1 bathroom with a bath and a hair dryer.\n" +
                                    "\n" +
                                    "Złote Tarasy Shopping Centre is 7 km from Apartament Airport Obrzeżna 3, while " +
                                    "Lazienki Palace is 8 km away. The nearest airport is Warsaw Frederic Chopin " +
                                    "Airport, 3.3 km from the accommodation.\n" +
                                    "\n" +
                                    "Mokotów is a great choice for travellers interested in convenient public " +
                                    "transport, parks and shopping.\n" +
                                    "\n" +
                                    "Couples particularly like the location — they rated it 9.5 for a two-person trip" +
                                    ".\n" +
                                    "\n" +
                                    "This apartment is also rated for the best value in Warsaw! Guests are getting " +
                                    "more for their money when compared to other properties in this city.\n" +
                                    "\n" +
                                    "We speak your language!")
            );
            accommodations.add(
                    buildAccommodation(owners.get(0),
                            buildLocalization("Poland", "Kraków", "Podbrzezie 6a/8"),
                            "Lukas Guest Rooms", "Located in Kraków, near Cloth Hall, St. Mary's Basilica and Lost " +
                                    "Souls Alley, " +
                                    "Lukas Guest Rooms features free WiFi.\n" +
                                    "\n" +
                                    "The accommodation comes with a flat-screen TV and a private bathroom with shower" +
                                    " and a hair dryer, while the kitchen features a dishwasher, a microwave and a " +
                                    "fridge.\n" +
                                    "\n" +
                                    "St. Florian's Gate is 1.9 km from the apartment, while National Museum of Krakow" +
                                    " is 2.1 km away. The nearest airport is John Paul II International Kraków–Balice" +
                                    " Airport, 15 km from Lukas Guest Rooms.\n" +
                                    "\n" +
                                    "Old Town is a great choice for travellers interested in food, restaurants and " +
                                    "history.\n" +
                                    "\n" +
                                    "This is our guests' favourite part of Kraków, according to independent reviews" +
                                    ".\n" +
                                    "\n" +
                                    "This property also has one of the best-rated locations in Kraków! Guests are " +
                                    "happier about it compared to other properties in the area.\n" +
                                    "\n" +
                                    "Couples particularly like the location — they rated it 9.7 for a two-person trip" +
                                    ".\n" +
                                    "\n" +
                                    "This apartment is also rated for the best value in Kraków! Guests are getting " +
                                    "more for their money when compared to other properties in this city.\n" +
                                    "\n" +
                                    "We speak your language!")
            );

            //Owner no.2
            accommodations.add(
                    buildAccommodation(owners.get(1),
                            buildLocalization("Poland", "Kraków", "Mariacka 12/3"),
                            "Shishkin Art Hostel", "Set in Kraków and within 1 km of Wawel Royal Castle, Shishkin Art" +
                                    " Hostel " +
                                    "features a shared lounge, non-smoking rooms, and free WiFi. The property is " +
                                    "around 2 km from Cloth Hall, 2.6 km from National Museum of Krakow and 2.8 km " +
                                    "from Main Market Square. The accommodation offers a shared kitchen, and " +
                                    "organising tours for guests.\n" +
                                    "\n" +
                                    "The area is popular for hiking, and bike hire is available at the hostel.\n" +
                                    "\n" +
                                    "Popular points of interest near Shishkin Art Hostel include St. Mary's Basilica," +
                                    " Lost Souls Alley and St. Florian's Gate. The nearest airport is John Paul II " +
                                    "International Kraków–Balice, 25 km from the accommodation, and the property " +
                                    "offers a paid airport shuttle service.\n" +
                                    "\n" +
                                    "Old Town is a great choice for travellers interested in food, restaurants and " +
                                    "history.\n" +
                                    "\n" +
                                    "This is our guests' favourite part of Kraków, according to independent reviews" +
                                    ".\n" +
                                    "\n" +
                                    "Couples particularly like the location — they rated it 8.4 for a two-person trip" +
                                    ".\n" +
                                    "\n" +
                                    "We speak your language!")
            );
            accommodations.add(
                    buildAccommodation(owners.get(1),
                            buildLocalization("Poland", "Wrocław", "Reymonta 40f/10"),
                            "Ibis Styles Wroclaw Centrum", "Ibis Styles Wrocław Centrum is conveniently set in the " +
                                    "heart of Wrocław, only " +
                                    "200 m from Wrocław Główny Railway Station. Guests are welcome to enjoy free WiFi" +
                                    " throughout the hotel.\n" +
                                    "\n" +
                                    "Each air-conditioned room here will provide you with a 32-inch flat-screen TV " +
                                    "with satellite channels, soundproof panoramic windows and a bathroom with a " +
                                    "shower. The hotel offers large, special \"Sweet Beds by Ibis\" beds.\n" +
                                    "\n" +
                                    "At Ibis Styles Wroclaw Centrum you will find a 24-hour front desk, a restaurant " +
                                    "and a bar. The Czary Mary restaurant specialises in international cuisine and " +
                                    "offers a wide selection of wine from around the world. The hotel offers a large " +
                                    "and modern business and conference facilities with a total space of 1150-square " +
                                    "metres. Shuttle services are available at a surcharge.\n" +
                                    "\n" +
                                    "Ibis Styles Wrocław Centrum is located 1 km from the Market Square, Capitol " +
                                    "Musical Theatre, Opera Wroclaw, Wroclaw water park, as well as shopping centers " +
                                    "like Arcada and Renoma. The International Wrocław Copernicus Airport is 12.5 km " +
                                    "away from the hotel.\n" +
                                    "\n" +
                                    "Krzyki is a great choice for travellers interested in zoos, sightseeing and " +
                                    "monuments.")
            );

            //Owner no.3
            accommodations.add(
                    buildAccommodation(owners.get(2),
                            buildLocalization("Poland", "Wrocław", "Oławska 43"),
                            "Hotel Sofia", "Just opposite Wrocław Główny Railway Station, Hotel Sofia is located 1.5 " +
                                    "km from" +
                                    " the Main Market Square. This 3-star hotel features free Wi-Fi, stylish, " +
                                    "air-conditioned rooms and business facilities.\n" +
                                    "\n" +
                                    "A buffet breakfast is served every morning in the dining room. The Lobby Bar is " +
                                    "open 24 hours a day and offers hot beverages and alcohol drinks.\n" +
                                    "\n" +
                                    "All rooms at Hotel Sofia have a working space and a flat-screen TV. Each comes " +
                                    "with a modern bathroom with a shower. The corner superior rooms feature " +
                                    "impressive panoramic views.\n" +
                                    "\n" +
                                    "Front desk staff is available 24 hours a day and can arrange shuttle and " +
                                    "concierge services.\n" +
                                    "\n" +
                                    "Hotel Sofia is located within a 20-minute drive from the Copernicus Airport " +
                                    "Wrocław.\n" +
                                    "\n" +
                                    "Stare Miasto is a great choice for travellers interested in restaurants, " +
                                    "atmosphere and culture.\n" +
                                    "\n" +
                                    "This is our guests' favourite part of Wrocław, according to independent reviews.")
            );
            accommodations.add(
                    buildAccommodation(owners.get(2),
                            buildLocalization("Spain", "Barcelona", "La Rambla 101"),
                            "Residencia Universitaria Tagaste", "Free Wi-Fi and rooms with private bathrooms feature " +
                                    "at Residencia Universitaria " +
                                    "Tagaste, a student residence set off Las Ramblas and 5 minutes’ walk from Liceu " +
                                    "Metro Station.\n" +
                                    "\n" +
                                    "Residencia Universitaria Tagaste has simply decorated rooms with air " +
                                    "conditioning and heating. All rooms have a desk.\n" +
                                    "\n" +
                                    "Sights including the MACBA Art Museum and La Boquería Market are less than 10 " +
                                    "minutes’ walk from the Sant Augustí. Barcelona’s gothic quarter and Port Vell " +
                                    "Marina are less than 1 km away.\n" +
                                    "\n" +
                                    "The residence is open to the university community, including students, " +
                                    "professors, researchers and administrative staff all year round.\n" +
                                    "\n" +
                                    "Ciutat Vella is a great choice for travellers interested in old town exploring, " +
                                    "food and sightseeing.\n" +
                                    "\n" +
                                    "This is our guests' favourite part of Barcelona, according to independent " +
                                    "reviews.")
            );

            //Owner no.4
            accommodations.add(
                    buildAccommodation(owners.get(3),
                            buildLocalization("Italy", "Rome", "Via Tubertina 66"),
                            "San Lorenzo Hostel", "Attractively situated in the San Lorenzo district of Rome, san " +
                                    "lorenzo hostel is" +
                                    " situated 600 m from Porta Maggiore, 2.1 km from Sapienza University of Rome and" +
                                    " 2.5 km from Domus Aurea.\n" +
                                    "\n" +
                                    "Featuring a shared bathroom with a bidet and free toiletries, rooms at the guest" +
                                    " house also boast free WiFi. The rooms will provide guests with a desk and a " +
                                    "kettle.\n" +
                                    "\n" +
                                    "The nearest airport is Rome Ciampino Airport, 14 km from san lorenzo hostel.\n" +
                                    "\n" +
                                    "San Lorenzo is a great choice for travellers interested in ancient landmarks, " +
                                    "monuments and old town exploring.")
            );
            accommodations.add(
                    buildAccommodation(owners.get(3),
                            buildLocalization("Spain", "Barcelona", "Carrer de la Rietera 12c"),
                            "Travelodge Barcelona Poblenou", "Just 600 m from Bogatell Beach, in Barcelona’s 22@ " +
                                    "District, the Travelodge " +
                                    "Barcelona Poblenou offers air-conditioned rooms and a café-bar. Llacuna Metro " +
                                    "Station is a 5-minute walk from the hotel.\n" +
                                    "\n" +
                                    "Rooms have modern décor, with parquet floors and colourful finishes. Each one " +
                                    "features a flat-screen TV and a private bathroom with bath or shower. Wi-Fi is " +
                                    "available for an extra charge in rooms, and is free in the rest of the hotel.\n" +
                                    "\n" +
                                    "Other facilities include vending machines. Public parking is available at an " +
                                    "extra cost.\n" +
                                    "\n" +
                                    "The streets of Poblenou are full of restaurants, bars and shops. Central " +
                                    "Barcelona can be reached in 10 minutes by metro and Barcelona’s Port Olímpic, " +
                                    "with its vibrant night life, is just a 20-minute walk away.\n" +
                                    "\n" +
                                    "Sant Martí is a great choice for travellers interested in beaches, monuments and" +
                                    " city walks.\n" +
                                    "\n" +
                                    "This is our guests' favourite part of Barcelona, according to independent " +
                                    "reviews.\n" +
                                    "\n" +
                                    "Couples particularly like the location — they rated it 8.1 for a two-person trip.")
            );

            //Owner no.5
            accommodations.add(
                    buildAccommodation(owners.get(4),
                            buildLocalization("Italy", "Bologna", "Via Donato Creti 50"),
                            "Strada Maggiore Apartment", "Strada Maggiore Apartment in Bologna features accommodation" +
                                    " with free WiFi, 1.4 " +
                                    "km from La Macchina del Tempo, 1.4 km from Santo Stefano Church and 1.5 km from " +
                                    "Via Zamboni.\n" +
                                    "\n" +
                                    "The apartment has 2 bedrooms, a flat-screen TV, an equipped kitchen with a " +
                                    "dishwasher and a microwave, a washing machine, and 1 bathroom with a bidet.\n" +
                                    "\n" +
                                    "Popular points of interest near the apartment include Santa Maria della Vita, " +
                                    "Piazza Maggiore and Quadrilatero Bologna. The nearest airport is Bologna " +
                                    "Guglielmo Marconi, 11 km from Strada Maggiore Apartment, and the property offers" +
                                    " a paid airport shuttle service.\n" +
                                    "\n" +
                                    "This is our guests' favourite part of Bologna, according to independent reviews" +
                                    ".\n" +
                                    "\n" +
                                    "This apartment is also rated for the best value in Bologna! Guests are get")
            );

            return accommodations;
        }

        private Localization buildLocalization(String country, String city, String address) {
            return Localization.builder()
                    .country(country)
                    .city(city)
                    .address(address)
                    .build();
        }

    }

    private class BookingGenerator {

        private Booking.BookingBuilder defaultBookingBuild() {
            LocalDate startDate = LocalDate.of(2022, 1, 1)
                    .plusDays(random.nextInt(350) + 1);
            LocalDate finishDate = startDate.plusDays(random.nextInt(14) + 1);

            Booking.BookingStatus[] statuses = Booking.BookingStatus.values();
            int index = currentNo++ > statuses.length - 1 ? currentNo % statuses.length : currentNo;

            return Booking.builder()
                    .startDate(startDate)
                    .finishDate(finishDate)
                    .status(statuses[index]);
        }

        private List<Booking> generate(int count) {
            List<Booking> bookings = new ArrayList<>();

            for (int i = 0; i < count; i++) {
                int clientId = i > clients.size() - 1 ? i % clients.size() : i;
                int accommodationId = i > accommodations.size() - 1 ? i % accommodations.size() : i;


                bookings.add(defaultBookingBuild()
                        .user(clients.get(clientId))
                        .accommodation(accommodations.get(accommodationId))
                        .guestsCount(random.nextInt(accommodations.get(accommodationId).getMaxGuests() - 1) + 2)
                        .build()
                );
            }
            return bookings;
        }
    }


}

