package com.auctionapp.model.auction;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuctionTest {

    @Test
    @DisplayName("Test Getters and Setters of Auction Class")
    public void testStartTimeGetterAndSetter() {
        LocalDateTime expectedStartTime = LocalDateTime.of(2023, 8, 23, 23, 0, 0);
        Auction auction = new Auction();
        auction.setStartTime(expectedStartTime);
        LocalDateTime actualStartTime = auction.getStartTime();
        assertEquals(expectedStartTime, actualStartTime);
    }

    @Test
    public void testEndTimeGetterAndSetter() {
        LocalDateTime expectedEndTime = LocalDateTime.of(2023, 8, 24, 15, 30, 0);
        Auction auction = new Auction();
        auction.setEndTime(expectedEndTime);
        LocalDateTime actualEndTime = auction.getEndTime();
        assertEquals(expectedEndTime, actualEndTime);
    }

}
