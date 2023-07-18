package org.efaps.pos.print;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

import org.efaps.pos.dto.PosDocItemDto;
import org.efaps.pos.dto.PosInvoiceDto;
import org.efaps.pos.dto.PrintPayableDto;
import org.efaps.pos.dto.ProductDto;
import org.efaps.pos.print.service.PrintService;
import org.junit.jupiter.api.Test;

public class PrintServiceTest
{
    @Test
    public void nn() {
        final var item1 = PosDocItemDto.builder()
                        .withIndex(1)
                        .withQuantity(new BigDecimal("2"))
                        .withProduct(ProductDto.builder()
                                        .withDescription("Hamburgesa Royal")
                                        .build())
                        .withCrossPrice(new BigDecimal("17.9"))
                        .build();
        final var item2 = PosDocItemDto.builder()
                        .withIndex(2)
                        .withProduct(ProductDto.builder()
                                        .withDescription("Pizza Hawaiana")
                                        .build())
                        .withQuantity(BigDecimal.ONE)
                        .withCrossPrice(new BigDecimal("6.75"))
                        .build();

        final var dto = PrintPayableDto.builder()
                        .withPayable(PosInvoiceDto.builder()
                                        .withNumber("F001-0001458")
                                        .withDate(LocalDate.of(2023, 07, 13))
                                        .withItems(Arrays.asList(item1, item2))
                                        .build())
                        .build();

        final var printService = new PrintService();
        printService.print(dto);
    }
}
