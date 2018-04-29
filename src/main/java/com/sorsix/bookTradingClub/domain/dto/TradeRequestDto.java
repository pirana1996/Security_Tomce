package com.sorsix.bookTradingClub.domain.dto;

/**
 * Created by jordancho on 10.8.2017.
 */
public class TradeRequestDto {

    public Long giveBookId;

    public Long takenBookId;

    public TradeRequestDto(Long giveBookId, Long takenBookId) {
        this.giveBookId = giveBookId;
        this.takenBookId = takenBookId;
    }

    public TradeRequestDto() {
    }
}
