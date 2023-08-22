package com.auctionapp.model.auction;

import com.auctionapp.model.product.EProductCategory;
import com.auctionapp.model.product.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuctionTest {

    @Test
    @DisplayName("Test Getters and Setters of Auction Class")
    public void testGettersAndSetters() {
        Auction auction = new Auction();

        auction.setId(1L);
        assertEquals(1L, auction.getId());


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            auction.setStartTime(dateFormat.parse("2023-08-15"));
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        String actual = dateFormat.format(auction.getStartTime());
        assertEquals("2023-08-15", actual);

        try {
            auction.setEndTime(dateFormat.parse("2023-09-15"));
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        assertEquals("2023-09-15", dateFormat.format(auction.getEndTime()));

        auction.setType(EAuctionType.RESERVE);
        assertEquals(EAuctionType.RESERVE, auction.getType());

    }
}
