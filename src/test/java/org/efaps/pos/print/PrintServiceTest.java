/*
 * Copyright 2003 - 2023 The eFaps Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.efaps.pos.print;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

import org.efaps.pos.dto.PosDocItemDto;
import org.efaps.pos.dto.PosInvoiceDto;
import org.efaps.pos.dto.PrintPayableDto;
import org.efaps.pos.dto.ProductDto;
import org.efaps.pos.print.PrintProperties.Connection;
import org.efaps.pos.print.PrintProperties.PrinterDefinition;
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
        final var item3 = PosDocItemDto.builder()
                        .withIndex(2)
                        .withProduct(ProductDto.builder()
                                        .withDescription("Producto with a very large description")
                                        .build())
                        .withQuantity(BigDecimal.ONE)
                        .withCrossPrice(new BigDecimal("122.75"))
                        .build();

        final var dto = PrintPayableDto.builder()
                        .withPayable(PosInvoiceDto.builder()
                                        .withNumber("F001-0001458")
                                        .withDate(LocalDate.of(2023, 07, 13))
                                        .withItems(Arrays.asList(item1, item2, item3))
                                        .build())
                        .build();
        final var properties = new PrintProperties();
        final var printerDef = new PrinterDefinition();
        printerDef.setIdentifier("random");
        printerDef.setTemplate("test.txt");
        final var connection = new Connection();
        printerDef.setConnection(connection);
        properties.getPrinters().add(printerDef);
        final var printService = new PrintService(properties);

        printService.print(printerDef.getIdentifier(), dto);
    }
}
