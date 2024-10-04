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

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import io.pebbletemplates.pebble.error.PebbleException;
import io.pebbletemplates.pebble.extension.Filter;
import io.pebbletemplates.pebble.template.EvaluationContext;
import io.pebbletemplates.pebble.template.PebbleTemplate;

public class ColumnFilter implements Filter
{

    @Override
    public List<String> getArgumentNames()
    {
        return Arrays.asList("width", "align");
    }

    @Override
    public Object apply(final Object input,
                        final Map<String, Object> args,
                        final PebbleTemplate self,
                        final EvaluationContext context,
                        final int lineNumber)
        throws PebbleException
    {
        var result = input;
        if (result != null) {
            final int with = ((Long) args.get("width")).intValue();
            var str = String.valueOf(input);
            str = StringUtils.left(str, with);
            final String align = (String) args.get("align");
            if (align != null && (align.equals("right") || align.equals("r"))) {
                str = StringUtils.leftPad(str, with);
            } else {
                str = StringUtils.rightPad(str, with);
            }
            result = str;
        }
        return result;
    }

}
