package com.oliviermarteaux.localshared.firebase.firestore.utils

import com.oliviermarteaux.localshared.firebase.authentication.domain.model.User
import com.oliviermarteaux.localshared.firebase.firestore.domain.model.Address
import com.oliviermarteaux.localshared.firebase.firestore.domain.model.Comment
import com.oliviermarteaux.localshared.firebase.firestore.domain.model.Post
import java.util.Date

fun generateSamplePosts(): List<Post> {
    val user1 = User("u1", "Alice", "Smith", "alice.smith@example.com")
    val user2 = User("u2", "Bob", "Johnson", "bob.johnson@example.com")

    return listOf(
        Post(
            title = "Christmas Village Opening",
            description = "Come enjoy the festive Christmas village with lights, food, and gifts!",
            photoUrl = "https://picsum.photos/800/600?random=1",
            timestamp = System.currentTimeMillis(),
            author = user1,
            comments = listOf(
                Comment(user2, "Can't wait!", System.currentTimeMillis())
            ),
            date = Date(2025 - 1900, 11, 5), // Dec 5, 2025
            address = Address("Main Street", "Downtown", "Paris", "75001", "France")
        ),
        Post(
            title = "City Marathon 2025",
            description = "Annual city marathon open to all runners.",
            photoUrl = "https://picsum.photos/800/600?random=1",
            timestamp = System.currentTimeMillis(),
            author = user2,
            comments = emptyList(),
            date = Date(2025 - 1900, 4, 12), // May 12, 2025
            address = Address("Park Avenue", "East Side", "New York", "10010", "USA")
        ),
        Post(
            title = "Grand Mall Opening",
            description = "Join us for the grand opening of Central Mall with special discounts.",
            photoUrl = "https://picsum.photos/800/600?random=1",
            timestamp = System.currentTimeMillis(),
            author = user1,
            comments = listOf(
                Comment(user2, "Excited to shop!", System.currentTimeMillis())
            ),
            date = Date(2025 - 1900, 6, 20),
            address = Address("Market Street", "Uptown", "London", "E1 6AN", "UK")
        ),
        Post(
            title = "Food Festival",
            description = "Taste the best street food in the city.",
            photoUrl = "https://picsum.photos/800/600?random=1",
            timestamp = System.currentTimeMillis(),
            author = user2,
            comments = emptyList(),
            date = Date(2025 - 1900, 7, 15),
            address = Address("Riverside Blvd", "Old Town", "Berlin", "10115", "Germany")
        ),
        Post(
            title = "Summer Concert Series",
            description = "Enjoy live music at the city park every weekend.",
            photoUrl = "https://picsum.photos/800/600?random=1",
            timestamp = System.currentTimeMillis(),
            author = user1,
            comments = emptyList(),
            date = Date(2025 - 1900, 6, 1),
            address = Address("Central Park", "Midtown", "Toronto", "M5V", "Canada")
        ),
        Post(
            title = "Winter Ice Skating Rink",
            description = "Open-air ice skating rink in downtown area.",
            photoUrl = "https://picsum.photos/800/600?random=1",
            timestamp = System.currentTimeMillis(),
            author = user2,
            comments = emptyList(),
            date = Date(2025 - 1900, 11, 20),
            address = Address("Lakeview Street", "Center", "Stockholm", "111 21", "Sweden")
        ),
        Post(
            title = "Local Art Exhibition",
            description = "Showcasing local artists' work from around the city.",
            photoUrl = "https://picsum.photos/800/600?random=1",
            timestamp = System.currentTimeMillis(),
            author = user1,
            comments = listOf(
                Comment(user2, "Amazing pieces!", System.currentTimeMillis())
            ),
            date = Date(2025 - 1900, 3, 18),
            address = Address("Art Street", "Cultural District", "Rome", "00186", "Italy")
        ),
        Post(
            title = "City Parade",
            description = "Annual parade celebrating city culture.",
            photoUrl = "https://picsum.photos/800/600?random=1",
            timestamp = System.currentTimeMillis(),
            author = user2,
            comments = emptyList(),
            date = Date(2025 - 1900, 8, 10),
            address = Address("Main Boulevard", "City Center", "Madrid", "28013", "Spain")
        ),
        Post(
            title = "Jazz Night",
            description = "Evening of live jazz music at the riverfront.",
            photoUrl = "https://picsum.photos/800/600?random=1",
            timestamp = System.currentTimeMillis(),
            author = user1,
            comments = listOf(
                Comment(user2, "Love jazz!", System.currentTimeMillis())
            ),
            date = Date(2025 - 1900, 5, 25),
            address = Address("River Street", "Old Quarter", "Lisbon", "1100-148", "Portugal")
        ),
        Post(
            title = "Technology Expo",
            description = "Exhibition of the latest gadgets and innovations.",
            photoUrl = "https://picsum.photos/800/600?random=1",
            timestamp = System.currentTimeMillis(),
            author = user2,
            comments = emptyList(),
            date = Date(2025 - 1900, 9, 2),
            address = Address("Tech Park", "Innovation District", "San Francisco", "94103", "USA")
        ),
        Post(
            title = "Farmers Market",
            description = "Fresh local produce every Saturday morning.",
            photoUrl = "https://picsum.photos/800/600?random=1",
            timestamp = System.currentTimeMillis(),
            author = user1,
            comments = emptyList(),
            date = Date(2025 - 1900, 2, 12),
            address = Address("Market Lane", "Downtown", "Amsterdam", "1012", "Netherlands")
        ),
        Post(
            title = "Halloween Festival",
            description = "Spooky activities for the whole family.",
            photoUrl = "https://picsum.photos/800/600?random=1",
            timestamp = System.currentTimeMillis(),
            author = user2,
            comments = emptyList(),
            date = Date(2025 - 1900, 9, 31),
            address = Address("Pumpkin Street", "Old Town", "Dublin", "D01", "Ireland")
        ),
        Post(
            title = "Book Fair",
            description = "Discover new authors and rare books.",
            photoUrl = "https://picsum.photos/800/600?random=1",
            timestamp = System.currentTimeMillis(),
            author = user1,
            comments = listOf(
                Comment(user2, "Can't wait!", System.currentTimeMillis())
            ),
            date = Date(2025 - 1900, 6, 10),
            address = Address("Library Road", "Central", "Edinburgh", "EH1", "UK")
        ),
        Post(
            title = "Science Festival",
            description = "Interactive science exhibitions for all ages.",
            photoUrl = "https://picsum.photos/800/600?random=1",
            timestamp = System.currentTimeMillis(),
            author = user2,
            comments = emptyList(),
            date = Date(2025 - 1900, 4, 5),
            address = Address("Innovation Street", "Tech District", "Copenhagen", "1050", "Denmark")
        ),
        Post(
            title = "Summer Food Truck Rally",
            description = "Try delicious street food from around the city.",
            photoUrl = "https://picsum.photos/800/600?random=1",
            timestamp = System.currentTimeMillis(),
            author = user1,
            comments = emptyList(),
            date = Date(2025 - 1900, 7, 8),
            address = Address("Riverfront", "Downtown", "Melbourne", "3000", "Australia")
        ),
        Post(
            title = "City Film Festival",
            description = "Screenings of independent films across multiple theaters.",
            photoUrl = "https://picsum.photos/800/600?random=1",
            timestamp = System.currentTimeMillis(),
            author = user2,
            comments = emptyList(),
            date = Date(2025 - 1900, 5, 18),
            address = Address(
                "Cinema Street",
                "Entertainment District",
                "Los Angeles",
                "90028",
                "USA"
            )
        ),
        Post(
            title = "Yoga in the Park",
            description = "Morning yoga sessions for everyone.",
            photoUrl = "https://picsum.photos/800/600?random=1",
            timestamp = System.currentTimeMillis(),
            author = user1,
            comments = emptyList(),
            date = Date(2025 - 1900, 6, 22),
            address = Address("Green Park", "Midtown", "London", "SW1A", "UK")
        ),
        Post(
            title = "City Carnival",
            description = "Annual carnival with rides, games, and food.",
            photoUrl = "https://picsum.photos/800/600?random=1",
            timestamp = System.currentTimeMillis(),
            author = user2,
            comments = emptyList(),
            date = Date(2025 - 1900, 8, 14),
            address = Address("Fun Street", "Downtown", "Rio de Janeiro", "20010", "Brazil")
        ),
        Post(
            title = "Open Air Theater",
            description = "Enjoy plays under the stars.",
            photoUrl = "https://picsum.photos/800/600?random=1",
            timestamp = System.currentTimeMillis(),
            author = user1,
            comments = listOf(
                Comment(user2, "Lovely idea!", System.currentTimeMillis())
            ),
            date = Date(2025 - 1900, 7, 30),
            address = Address("Theater Avenue", "Cultural District", "Paris", "75002", "France")
        ),
        Post(
            title = "Winter Light Festival",
            description = "City streets illuminated with spectacular light installations.",
            photoUrl = "https://picsum.photos/800/600?random=1",
            timestamp = System.currentTimeMillis(),
            author = user2,
            comments = emptyList(),
            date = Date(2025 - 1900, 11, 10),
            address = Address("Lantern Street", "Old Town", "Tokyo", "100-0001", "Japan")
        )
    )
}