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
package org.efaps.pos.print.service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.efaps.pos.dto.PrintPayableDto;
import org.efaps.pos.listener.IPrintListener;
import org.efaps.pos.print.PrintProperties;
import org.efaps.pos.print.PrintProperties.PrinterDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;

import io.pebbletemplates.pebble.PebbleEngine;

@Service("Print-PrintService")
public class PrintService
    implements IPrintListener
{

    private static final Logger LOG = LoggerFactory.getLogger(PrintService.class);
    private final RestTemplateBuilder restTemplateBuilder;
    private final PrintProperties properties;

    public PrintService(final RestTemplateBuilder restTemplateBuilder,
                        final PrintProperties properties)
    {
        this.properties = properties;
        this.restTemplateBuilder = restTemplateBuilder;
    }

    @Override
    public void print(final String identifier,
                      final Object object)
    {
        final var printerDefOpt = properties.getPrinters().stream()
                        .filter(printer -> identifier.equals(printer.getIdentifier()))
                        .findFirst();
        if (printerDefOpt.isPresent()) {
            final var printerDef = printerDefOpt.get();
            LOG.debug("Using PrinterDefintion: {}", printerDef);
            print(printerDef, object);
        }
    }

    public void print(final PrinterDefinition printerDef,
                      final Object object)
    {
        final var engine = new PebbleEngine.Builder()
                        .extension(new PrintExtension())
                        .build();
        final var template = engine.getTemplate(printerDef.getTemplate());

        final Map<String, Object> context = new HashMap<>();
        if (object instanceof PrintPayableDto) {
            fillMap4Payable(context, (PrintPayableDto) object);
        } else {
            fillMap(context, object);
        }

        final var writer = new StringWriter();
        try {
            template.evaluate(writer, context);
        } catch (final IOException e) {
            LOG.error("Catched", e);
        }

        final String content = writer.toString();
        LOG.info(content);

        IConnector connector = null;
        switch (printerDef.getConnection().getType()) {
            case "REST": {
                connector = new RestConnector(restTemplateBuilder, printerDef.getConnection());
                break;
            }
            case "Dummy": {
                connector = new DummyConnector();
                break;
            }
            default:
                throw new IllegalArgumentException("Unexpected value: " + printerDef.getConnection().getType());
        }
        connector.print(content);
    }

    private void fillMap(final Map<String, Object> context,
                         final Object object)
    {
        context.put("object", object);
    }

    private void fillMap4Payable(final Map<String, Object> context,
                                 final PrintPayableDto dto)
    {
        context.put("payable", dto.getPayable());
        context.put("payableType", dto.getPayableType());
        context.put("order", dto.getOrder());
        context.put("additionalInfo", dto.getAdditionalInfo());
        context.put("amountInWords", dto.getAmountInWords());
        context.put("time", dto.getTime());
    }
}
