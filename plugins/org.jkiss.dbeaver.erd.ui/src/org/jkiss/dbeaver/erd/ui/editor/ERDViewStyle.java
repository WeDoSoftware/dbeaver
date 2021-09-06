/*
 * DBeaver - Universal Database Manager
 * Copyright (C) 2010-2021 DBeaver Corp and others
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
package org.jkiss.dbeaver.erd.ui.editor;

import org.jkiss.code.NotNull;
import org.jkiss.dbeaver.erd.model.ERDConstants;
import org.jkiss.dbeaver.erd.ui.internal.ERDUIMessages;
import org.jkiss.dbeaver.model.preferences.DBPPreferenceStore;
import org.jkiss.utils.CommonUtils;

import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * Entity attribute presentation
 */
public enum ERDViewStyle {
    ICONS(ERDUIMessages.erd_view_style_selection_item_icons, 1),
    TYPES(ERDUIMessages.erd_view_style_selection_item_data_types, 1 << 1),
    NULLABILITY(ERDUIMessages.erd_view_style_selection_item_nullability, 1 << 2),
    COMMENTS(ERDUIMessages.erd_view_style_selection_item_comments, 1 << 3),
    ENTITY_FQN(ERDUIMessages.erd_view_style_selection_item_fully_qualified_names, 1 << 4),
    ALPHABETICAL_ORDER(ERDUIMessages.erd_view_style_selection_item_alphabetical_order, 1 << 5);

    private final int value;
    private final String title;

    ERDViewStyle(@NotNull String title, int value) {
        this.value = value;
        this.title = title;
    }

    public int getValue() {
        return value;
    }

    @NotNull
    public String getTitle() {
        return title;
    }

    @NotNull
    public static ERDViewStyle[] getDefaultStyles(@NotNull DBPPreferenceStore store) {
        final String attrString = store.getString(ERDConstants.PREF_ATTR_STYLES);
        if (!CommonUtils.isEmpty(attrString)) {
            return Arrays.stream(attrString.split(","))
                .map(x -> CommonUtils.valueOf(ERDViewStyle.class, x))
                .filter(Objects::nonNull)
                .toArray(ERDViewStyle[]::new);
        }
        return new ERDViewStyle[]{ICONS};
    }

    public static void setDefaultStyles(@NotNull DBPPreferenceStore store, @NotNull ERDViewStyle[] styles) {
        final StringJoiner buffer = new StringJoiner(",");
        for (ERDViewStyle style : styles) {
            buffer.add(style.name());
        }
        store.setValue(ERDConstants.PREF_ATTR_STYLES, buffer.toString());
    }

}
