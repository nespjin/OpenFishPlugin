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

package com.nesp.fishplugin.runtime.movie.dsl

import com.nesp.fishplugin.core.data.DSL

class MovieCategoryPageDsl(properties: MutableMap<String, String>) : DSL(properties) {

    override fun isAvailable(): Boolean {
        val property = getProperty(PROPERTY_NAME_MOVIE_LIST)
        return property != null && !isPropertyEmpty(property)
    }

    companion object {

        private const val PROPERTY_NAME_PREFIX_CATEGORY_GROUP = "categoryGroup"
        private const val PROPERTY_NAME_PREFIX_CATEGORY = "category"

        private const val PROPERTY_NAME_PREFIX_MOVIE = "movie"
        private const val PROPERTY_NAME_PREFIX_NEXT_PAGE = "nextPage"

        const val PROPERTY_NAME_CATEGORY_GROUP_LIST =
            PROPERTY_NAME_PREFIX_CATEGORY_GROUP + PROPERTY_CONTENT_LIST
        const val PROPERTY_NAME_CATEGORY_LIST =
            PROPERTY_NAME_PREFIX_CATEGORY + PROPERTY_CONTENT_LIST
        const val PROPERTY_NAME_CATEGORY_TITLE =
            PROPERTY_NAME_PREFIX_CATEGORY + "Title"
        const val PROPERTY_NAME_CATEGORY_URL = PROPERTY_NAME_PREFIX_CATEGORY + PROPERTY_CONTENT_URL

        const val PROPERTY_NAME_MOVIE_LIST =
            PROPERTY_NAME_PREFIX_MOVIE + PROPERTY_CONTENT_LIST
        const val PROPERTY_NAME_MOVIE_STATUS =
            PROPERTY_NAME_PREFIX_MOVIE + PROPERTY_CONTENT_STATUS
        const val PROPERTY_NAME_MOVIE_NAME =
            PROPERTY_NAME_PREFIX_MOVIE + PROPERTY_CONTENT_NAME
        const val PROPERTY_NAME_MOVIE_SCORE =
            PROPERTY_NAME_PREFIX_MOVIE + PROPERTY_CONTENT_SCORE
        const val PROPERTY_NAME_MOVIE_COVER_IMAGE =
            PROPERTY_NAME_PREFIX_MOVIE + PROPERTY_CONTENT_COVER_IMAGE
        const val PROPERTY_NAME_MOVIE_DETAIL_URL =
            PROPERTY_NAME_PREFIX_MOVIE + PROPERTY_CONTENT_DETAIL_URL
        const val PROPERTY_NAME_MOVIE_DETAIL = "detail"

        const val PROPERTY_NAME_MOVIE_NEXT_PAGE_URL =
            PROPERTY_NAME_PREFIX_NEXT_PAGE + PROPERTY_CONTENT_URL


    }

}