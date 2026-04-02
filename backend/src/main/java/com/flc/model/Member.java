package com.flc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import jakarta.persistence.*;
import java.util.*;

/**
 * JPA Entity representing a member of Furzefield Leisure Centre
 */
@Entity
@Table(name = "members")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Booking> bookings = new ArrayList<>();

    public Member(String name, String email) {
        this.name = name;
        this.email = email;
        this.bookings = new ArrayList<>();
    }

    public void addBooking(Booking booking) {
        if (!bookings.contains(booking)) {
            bookings.add(booking);
            booking.setMember(this);
        }
    }

    public void removeBooking(Booking booking) {
        if (bookings.remove(booking)) {
            booking.setMember(null);
        }
    }

    public List<Booking> getBookingsForWeekend(int weekendNumber) {
        List<Booking> weekendBookings = new ArrayList<>();
        for (Booking booking : bookings) {
            if (!booking.isCancelled() && booking.getLesson().getWeekendNumber() == weekendNumber) {
                weekendBookings.add(booking);
            }
        }
        return weekendBookings;
    }
}
