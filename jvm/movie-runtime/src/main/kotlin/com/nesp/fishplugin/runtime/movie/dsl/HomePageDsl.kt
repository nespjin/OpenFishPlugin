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

class HomePageDsl(properties: MutableMap<String, String>) : DSL(properties) {

    override fun isAvailable(): Boolean {
        val property = getProperty(PROPERTY_NAME_SLIDE_LIST)
        return property != null && !isPropertyEmpty(property)
    }

    companion object {
        private const val PROPERTY_NAME_PREFIX_SLIDE = "slide"
        private const val PROPERTY_NAME_PREFIX_NEW_PLAY = "newPlay"
        private const val PROPERTY_NAME_PREFIX_NEW_MOVIE = "newMovie"
        private const val PROPERTY_NAME_PREFIX_NEW_SOAP = "newSoap"
        private const val PROPERTY_NAME_PREFIX_NEW_VARIETY = "newVariety"
        private const val PROPERTY_NAME_PREFIX_NEW_ANIM = "newAnim"
        private const val PROPERTY_NAME_PREFIX_NEW_COMMON = "newCommon"

        const val PROPERTY_NAME_SLIDE_LIST = PROPERTY_NAME_PREFIX_SLIDE + PROPERTY_CONTENT_LIST
        const val PROPERTY_NAME_SLIDE_STATUS = PROPERTY_NAME_PREFIX_SLIDE + PROPERTY_CONTENT_STATUS
        const val PROPERTY_NAME_SLIDE_COVER_IMAGE =
            PROPERTY_NAME_PREFIX_SLIDE + PROPERTY_CONTENT_COVER_IMAGE
        const val PROPERTY_NAME_SLIDE_DETAIL_URL =
            PROPERTY_NAME_PREFIX_SLIDE + PROPERTY_CONTENT_DETAIL_URL

        const val PROPERTY_NAME_NEW_PLAY_LIST =
            PROPERTY_NAME_PREFIX_NEW_PLAY + PROPERTY_CONTENT_LIST
        const val PROPERTY_NAME_NEW_PLAY_STATUS =
            PROPERTY_NAME_PREFIX_NEW_PLAY + PROPERTY_CONTENT_STATUS
        const val PROPERTY_NAME_NEW_PLAY_NAME =
            PROPERTY_NAME_PREFIX_NEW_PLAY + PROPERTY_CONTENT_NAME
        const val PROPERTY_NAME_NEW_PLAY_SCORE =
            PROPERTY_NAME_PREFIX_NEW_PLAY + PROPERTY_CONTENT_SCORE
        const val PROPERTY_NAME_NEW_PLAY_COVER_IMAGE =
            PROPERTY_NAME_PREFIX_NEW_PLAY + PROPERTY_CONTENT_COVER_IMAGE
        const val PROPERTY_NAME_NEW_PLAY_DETAIL_URL =
            PROPERTY_NAME_PREFIX_NEW_PLAY + PROPERTY_CONTENT_DETAIL_URL

        const val PROPERTY_NAME_NEW_MOVIE_LIST =
            PROPERTY_NAME_PREFIX_NEW_MOVIE + PROPERTY_CONTENT_LIST
        const val PROPERTY_NAME_NEW_MOVIE_STATUS =
            PROPERTY_NAME_PREFIX_NEW_MOVIE + PROPERTY_CONTENT_STATUS
        const val PROPERTY_NAME_NEW_MOVIE_NAME =
            PROPERTY_NAME_PREFIX_NEW_MOVIE + PROPERTY_CONTENT_NAME
        const val PROPERTY_NAME_NEW_MOVIE_SCORE =
            PROPERTY_NAME_PREFIX_NEW_MOVIE + PROPERTY_CONTENT_SCORE
        const val PROPERTY_NAME_NEW_MOVIE_COVER_IMAGE =
            PROPERTY_NAME_PREFIX_NEW_MOVIE + PROPERTY_CONTENT_COVER_IMAGE
        const val PROPERTY_NAME_NEW_MOVIE_DETAIL_URL =
            PROPERTY_NAME_PREFIX_NEW_MOVIE + PROPERTY_CONTENT_DETAIL_URL

        const val PROPERTY_NAME_NEW_SOAP_LIST =
            PROPERTY_NAME_PREFIX_NEW_SOAP + PROPERTY_CONTENT_LIST
        const val PROPERTY_NAME_NEW_SOAP_STATUS =
            PROPERTY_NAME_PREFIX_NEW_SOAP + PROPERTY_CONTENT_STATUS
        const val PROPERTY_NAME_NEW_SOAP_NAME =
            PROPERTY_NAME_PREFIX_NEW_SOAP + PROPERTY_CONTENT_NAME
        const val PROPERTY_NAME_NEW_SOAP_SCORE =
            PROPERTY_NAME_PREFIX_NEW_SOAP + PROPERTY_CONTENT_SCORE
        const val PROPERTY_NAME_NEW_SOAP_COVER_IMAGE =
            PROPERTY_NAME_PREFIX_NEW_SOAP + PROPERTY_CONTENT_COVER_IMAGE
        const val PROPERTY_NAME_NEW_SOAP_DETAIL_URL =
            PROPERTY_NAME_PREFIX_NEW_SOAP + PROPERTY_CONTENT_DETAIL_URL

        const val PROPERTY_NAME_NEW_VARIETY_LIST =
            PROPERTY_NAME_PREFIX_NEW_VARIETY + PROPERTY_CONTENT_LIST
        const val PROPERTY_NAME_NEW_VARIETY_STATUS =
            PROPERTY_NAME_PREFIX_NEW_VARIETY + PROPERTY_CONTENT_STATUS
        const val PROPERTY_NAME_NEW_VARIETY_NAME =
            PROPERTY_NAME_PREFIX_NEW_VARIETY + PROPERTY_CONTENT_NAME
        const val PROPERTY_NAME_NEW_VARIETY_SCORE =
            PROPERTY_NAME_PREFIX_NEW_VARIETY + PROPERTY_CONTENT_SCORE
        const val PROPERTY_NAME_NEW_VARIETY_COVER_IMAGE =
            PROPERTY_NAME_PREFIX_NEW_VARIETY + PROPERTY_CONTENT_COVER_IMAGE
        const val PROPERTY_NAME_NEW_VARIETY_DETAIL_URL =
            PROPERTY_NAME_PREFIX_NEW_VARIETY + PROPERTY_CONTENT_DETAIL_URL

        const val PROPERTY_NAME_NEW_ANIM_LIST =
            PROPERTY_NAME_PREFIX_NEW_ANIM + PROPERTY_CONTENT_LIST
        const val PROPERTY_NAME_NEW_ANIM_STATUS =
            PROPERTY_NAME_PREFIX_NEW_ANIM + PROPERTY_CONTENT_STATUS
        const val PROPERTY_NAME_NEW_ANIM_NAME =
            PROPERTY_NAME_PREFIX_NEW_ANIM + PROPERTY_CONTENT_NAME
        const val PROPERTY_NAME_NEW_ANIM_SCORE =
            PROPERTY_NAME_PREFIX_NEW_ANIM + PROPERTY_CONTENT_SCORE
        const val PROPERTY_NAME_NEW_ANIM_COVER_IMAGE =
            PROPERTY_NAME_PREFIX_NEW_ANIM + PROPERTY_CONTENT_COVER_IMAGE
        const val PROPERTY_NAME_NEW_ANIM_DETAIL_URL =
            PROPERTY_NAME_PREFIX_NEW_ANIM + PROPERTY_CONTENT_DETAIL_URL

        const val PROPERTY_NAME_NEW_COMMON_LIST =
            PROPERTY_NAME_PREFIX_NEW_COMMON + PROPERTY_CONTENT_LIST
        const val PROPERTY_NAME_NEW_COMMON_STATUS =
            PROPERTY_NAME_PREFIX_NEW_COMMON + PROPERTY_CONTENT_STATUS
        const val PROPERTY_NAME_NEW_COMMON_NAME =
            PROPERTY_NAME_PREFIX_NEW_COMMON + PROPERTY_CONTENT_NAME
        const val PROPERTY_NAME_NEW_COMMON_SCORE =
            PROPERTY_NAME_PREFIX_NEW_COMMON + PROPERTY_CONTENT_SCORE
        const val PROPERTY_NAME_NEW_COMMON_COVER_IMAGE =
            PROPERTY_NAME_PREFIX_NEW_COMMON + PROPERTY_CONTENT_COVER_IMAGE
        const val PROPERTY_NAME_NEW_COMMON_DETAIL_URL =
            PROPERTY_NAME_PREFIX_NEW_COMMON + PROPERTY_CONTENT_DETAIL_URL
    }

}