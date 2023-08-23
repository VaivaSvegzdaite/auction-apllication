package com.auctionapp.model.auction;

import com.auctionapp.model.product.ProductDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AuctionDTOTest {
    @Test
    @DisplayName("Test Getters and Setters of ProductDTO Class")
    public void testGettersAndSetters() {
        AuctionDTO auctionDTO = new AuctionDTO();

        auctionDTO.setId(1L);
        assertEquals(1L, auctionDTO.getId());

        auctionDTO.setType(EAuctionType.RESERVE);
        assertEquals(EAuctionType.RESERVE, auctionDTO.getType());

        auctionDTO.setUserId(3L);
        assertEquals(3L, auctionDTO.getUserId());

        List<Long> list = Arrays.asList(1L, 2L, 3L);
        auctionDTO.setBidId(list);
        assertEquals(list, auctionDTO.getBidId());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            auctionDTO.setStartTime(dateFormat.parse("2023-08-15"));
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        String actual = dateFormat.format(auctionDTO.getStartTime());
        assertEquals("2023-08-15", actual);

        try {
            auctionDTO.setEndTime(dateFormat.parse("2023-09-15"));
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        assertEquals("2023-09-15", dateFormat.format(auctionDTO.getEndTime()));

        auctionDTO.setStartingPrice(15);
        assertEquals(15, auctionDTO.getStartingPrice());
    }
}
