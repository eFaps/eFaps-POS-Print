/*
 * Copyright © 2003 - 2024 The eFaps Team (-)
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
 */
package org.efaps.pos.print.service;

import org.efaps.pos.print.PrintProperties.Connection;
import org.efaps.pos.print.dto.PrintDto;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class RestConnector
    implements IConnector
{

    private final Connection connection;
    private final RestTemplate restTemplate;

    public RestConnector(final RestTemplateBuilder restTemplateBuilder,
                         final Connection connection)
    {
        this.restTemplate = restTemplateBuilder.build();
        this.connection = connection;
    }

    @Override
    public void print(String content)
    {
        final var uri = UriComponentsBuilder.fromHttpUrl(connection.getBaseUrl()).path("print").build().toUri();
        final var requestEntity = RequestEntity.post(uri).accept(MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN)
                        .body(PrintDto.builder()
                                        .withContent(content)
                                        .build());
        restTemplate.exchange(requestEntity, String.class);
    }
}
