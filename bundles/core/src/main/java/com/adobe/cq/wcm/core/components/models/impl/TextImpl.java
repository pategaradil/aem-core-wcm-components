/*******************************************************************************
 * Copyright 2016 Adobe Systems Incorporated
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
 ******************************************************************************/
package com.adobe.cq.wcm.core.components.models.impl;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.commons.osgi.PropertiesUtil;
import org.apache.sling.models.annotations.Model;

import com.adobe.cq.wcm.core.components.models.Text;

@Model(adaptables = SlingHttpServletRequest.class, adapters = Text.class, resourceType = TextImpl.RESOURCE_TYPE)
public class TextImpl implements Text {

    public static final String RESOURCE_TYPE = "core/wcm/components/text";

    @Inject
    private Resource resource;

    private String text;
    private boolean isRich;
    private String displayContext;

    @Override
    public String getText() {
        return text;
    }

    @Override
    public boolean isRichText() {
        return isRich;
    }

    @Override
    public String getDisplayContext() {
        return displayContext;
    }

    @PostConstruct
    private void postConstruct() {
        ValueMap properties = resource.adaptTo(ValueMap.class);
        text = PropertiesUtil.toString(properties.get("text"), null);
        isRich = PropertiesUtil.toBoolean(properties.get("textIsRich"), false);
        if (isRich) {
            displayContext = CONTEXT_HTML;
        } else {
            displayContext = CONTEXT_TEXT;
        }
    }
}
