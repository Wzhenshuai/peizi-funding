package com.icaopan.marketdata.provider.tonglian;

/**
 * Created by ffff on 2016-09-22.
 */
public class Book {
    private static final long serialVersionUID = 1112L;
    private Double price;
    private Long   volume;

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getVolume() {
        return volume;
    }

    public void setVolume(Long volume) {
        this.volume = volume;
    }
}
