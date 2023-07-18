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
package org.efaps.pos.print.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(builder = PrintDto.Builder.class)
public class PrintDto
{

    private final String content;

    private PrintDto(Builder builder)
    {
        this.content = builder.content;
    }

    public String getContent()
    {
        return content;
    }

    public static Builder builder()
    {
        return new Builder();
    }

    public static final class Builder
    {

        private String content;

        private Builder()
        {
        }

        public Builder withContent(String content)
        {
            this.content = content;
            return this;
        }

        public PrintDto build()
        {
            return new PrintDto(this);
        }
    }
}
