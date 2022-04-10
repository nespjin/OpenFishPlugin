/*
 * Copyright (c) 2022.  NESP Technology.
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

package com.nesp.fishplugin.core.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public final class LinkedHashMapExtensions {

    private LinkedHashMapExtensions() {
    }

    @SuppressWarnings("unchecked")
    public static <K, V> Map.Entry<K, V> eldest(final LinkedHashMap<K, V> $this) {
        if ($this == null) return null;
        try {
            return (Map.Entry<K, V>) LinkedHashMap.class.getDeclaredField("head").get($this);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return null;
        }
    }

}