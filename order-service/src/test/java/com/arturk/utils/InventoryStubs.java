package com.arturk.utils;

import lombok.experimental.UtilityClass;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@UtilityClass
public class InventoryStubs {

    public void productIsInStock(String skuCode, Integer quantity) {
        stubFor(
                get(
                        urlEqualTo(String.format("/%s/availability?quantity=%s", skuCode, quantity))
                )
                        .willReturn(aResponse()
                                .withStatus(200)
                                .withHeader("Content-Type", "application/json")
                                .withBody("true")));
    }

    public void productIsOutOfStock(String skuCode, Integer quantity) {
        stubFor(
                get(
                        urlEqualTo(String.format("/%s/availability?quantity=%s", skuCode, quantity))
                )
                        .willReturn(aResponse()
                                .withStatus(200)
                                .withHeader("Content-Type", "application/json")
                                .withBody("false")));
    }
}
