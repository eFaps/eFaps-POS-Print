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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import io.pebbletemplates.pebble.PebbleEngine;

@Service("Print-PrintService")
public class PrintService
{
    private static final Logger LOG = LoggerFactory.getLogger(PrintService.class);

    public void print(PrintPayableDto dto) {

        final var engine = new PebbleEngine.Builder()
                        .extension(new PrintExtension())
                        .build();
        final var template = engine.getTemplate("test.txt");

        final Map<String, Object> context = new HashMap<>();
        context.put("payable", dto.getPayable());
        context.put("payableType", dto.getPayableType());
        context.put("order", dto.getOrder());
        context.put("additionalInfo", dto.getAdditionalInfo());
        context.put("amountInWords", dto.getAmountInWords());
        context.put("time", dto.getTime());

        final var writer = new StringWriter();
        try {
            template.evaluate(writer, context);
        } catch (final IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        final String output = writer.toString();
        System.out.println(output);
        LOG.info(output);
    }
}
