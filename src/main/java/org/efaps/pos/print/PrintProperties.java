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
package org.efaps.pos.print;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "printing")
public class PrintProperties
{

    private final List<PrinterDefinition> printers = new ArrayList<>();

    public List<PrinterDefinition> getPrinters()
    {
        return printers;
    }

    public static class PrinterDefinition
    {

        private String identifier;
        private Connection connection = new Connection();
        private String template;

        public String getTemplate()
        {
            return template;
        }

        public void setTemplate(String template)
        {
            this.template = template;
        }

        public Connection getConnection()
        {
            return connection;
        }

        public void setConnection(Connection connection)
        {
            this.connection = connection;
        }

        public String getIdentifier()
        {
            return identifier;
        }

        public void setIdentifier(String identifier)
        {
            this.identifier = identifier;
        }
    }

    public static class Connection
    {

        private String type = "REST";
        private String baseUrl;

        public String getType()
        {
            return type;
        }

        public void setType(String type)
        {
            this.type = type;
        }

        public String getBaseUrl()
        {
            return baseUrl;
        }

        public void setBaseUrl(String baseUrl)
        {
            this.baseUrl = baseUrl;
        }
    }
}
