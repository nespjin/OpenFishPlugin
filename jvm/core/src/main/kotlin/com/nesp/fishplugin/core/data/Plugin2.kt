package com.nesp.fishplugin.core.data

import com.nesp.fishplugin.core.Environment
import com.nesp.fishplugin.core.PluginUtil
import org.json.JSONObject

/**
 * Fish Plugin
 */
class Plugin2 constructor(private val store: JSONObject = JSONObject()) {

    @JvmOverloads
    fun setParent(value: Any?, deviceType: Int? = null) {
        store.put(PluginUtil.getFieldNameWithDeviceType(FIELD_NAME_PARENT, deviceType), value)
    }

    @JvmOverloads
    fun getParent(deviceType: Int? = null): Any? {
        return store.opt(PluginUtil.getFieldNameWithDeviceType(FIELD_NAME_PARENT, deviceType))
    }

    var name: String
        set(value) {
            store.put(FIELD_NAME_NAME, value)
        }
        get() {
            return store.optString(FIELD_NAME_NAME)
        }

    var id: String
        set(value) {
            store.put(FIELD_NAME_ID, value)
        }
        get() {
            return store.optString(FIELD_NAME_ID)
        }

    var author: String
        set(value) {
            store.put(FIELD_NAME_AUTHOR, value)
        }
        get() {
            return store.optString(FIELD_NAME_AUTHOR)
        }

    var version: String
        set(value) {
            store.put(FIELD_NAME_VERSION, value)
        }
        get() {
            return store.optString(FIELD_NAME_VERSION)
        }

    var runtime: String
        set(value) {
            store.put(FIELD_NAME_RUNTIME, value)
        }
        get() {
            return store.optString(FIELD_NAME_RUNTIME)
        }

    var time: String
        set(value) {
            store.put(FIELD_NAME_TIME, value)
        }
        get() {
            return store.optString(FIELD_NAME_TIME)
        }

    var tags: List<String>?
        set(value) {
            store.put(FIELD_NAME_TAGS, value)
        }
        get() {
            val originList =
                store.optJSONArray(FIELD_NAME_TAGS)?.toList() ?: return null
            if (originList.isEmpty()) return emptyList()
            val ret = mutableListOf<String>()
            for (item in originList) {
                if (item !is String) {
                    throw IllegalStateException("The type ${item::class.java.simpleName} is not supported")
                }
                ret.add(item)
            }
            return ret
        }

    var deviceFlags: Int
        set(value) {
            store.put(FIELD_NAME_DEVICE_FLAGS, value)
        }
        get() {
            return store.optInt(FIELD_NAME_DEVICE_FLAGS, -1)
        }

    var type: Int
        set(value) {
            store.put(FIELD_NAME_TYPE, value)
        }
        get() {
            return store.optInt(FIELD_NAME_TYPE, -1)
        }

    var introduction: String
        set(value) {
            store.put(FIELD_NAME_INTRODUCTION, value)
        }
        get() {
            return store.optString(FIELD_NAME_INTRODUCTION)
        }

    var ref: Map<String, Any>?
        set(value) {
            store.put(FIELD_NAME_REF, value)
        }
        get() {
            return store.optJSONObject(FIELD_NAME_REF)?.toMap()
        }

    var pages: List<Page2>
        set(value) {
            store.put(FIELD_NAME_PAGES, value)
        }
        get() {
            val originList = store.optJSONArray(FIELD_NAME_PAGES)?.toList()
            if (originList.isNullOrEmpty()) return emptyList()
            val ret = mutableListOf<Page2>()
            for (item in originList) {
                if (item !is Page2) {
                    throw IllegalStateException("The type ${item::class.java.simpleName} is not supported")
                }
                ret.add(item)
            }
            return ret
        }

    var extensions: Any?
        set(value) {
            store.put(FIELD_NAME_EXTENSIONS, value)
        }
        get() {
            return store.opt(FIELD_NAME_EXTENSIONS)
        }

    /**
     * Whether to support mobile phone
     */
    fun isSupportMobilePhone(): Boolean {
        return (deviceFlags and Plugin.DEVICE_FLAG_PHONE) == Plugin.DEVICE_FLAG_PHONE
    }

    /**
     * Whether to support table
     */
    fun isSupportTable(): Boolean {
        return (deviceFlags and Plugin.DEVICE_FLAG_TABLE) == Plugin.DEVICE_FLAG_TABLE
    }

    /**
     * Whether to support desktop
     */
    fun isSupportDesktop(): Boolean {
        return (deviceFlags and Plugin.DEVICE_FLAG_DESKTOP) == Plugin.DEVICE_FLAG_DESKTOP
    }

    /**
     * Find variable which named [variableName] from [ref]
     */
    fun findRefVariable(variableName: String): Any? {
        if (ref.isNullOrEmpty()) return null
        if (variableName.trim().isEmpty()) return null
        if (!ref!!.containsKey(variableName)) return null
        val deviceType = Environment.shared.getDeviceType()
        if (ref!!.containsKey("$variableName-$deviceType")) {
            return ref!!["$variableName-$deviceType"]
        }
        return ref!![variableName]
    }

    fun getFieldValue(fieldName: String): Any? {
        return when (fieldName) {
            FIELD_NAME_NAME -> this.name
            FIELD_NAME_ID -> this.id
            FIELD_NAME_AUTHOR -> this.author
            FIELD_NAME_VERSION -> this.version
            FIELD_NAME_RUNTIME -> this.runtime
            FIELD_NAME_TIME -> this.time
            FIELD_NAME_TAGS -> this.tags
            FIELD_NAME_DEVICE_FLAGS -> this.deviceFlags
            FIELD_NAME_TYPE -> this.type
            FIELD_NAME_INTRODUCTION -> this.introduction
            FIELD_NAME_REF -> this.ref
            FIELD_NAME_PAGES -> this.pages
            FIELD_NAME_EXTENSIONS -> this.extensions
            else -> ""
        }
    }

    fun setFieldValue(fieldName: String, fieldValue: Any?) {
        when (fieldName) {
            FIELD_NAME_NAME -> {
                if (fieldValue is String) this.name = fieldValue
            }
            FIELD_NAME_ID -> {
                if (fieldValue is String) this.id = fieldValue
            }
            FIELD_NAME_AUTHOR -> {
                if (fieldValue is String) this.author = fieldValue
            }
            FIELD_NAME_VERSION -> {
                if (fieldValue is String) this.version = fieldValue
            }
            FIELD_NAME_RUNTIME -> {
                if (fieldValue is String) this.runtime = fieldValue
            }
            FIELD_NAME_TIME -> {
                if (fieldValue is String) this.time = fieldValue
            }
            FIELD_NAME_TAGS -> {
                if (fieldValue is Collection<*>) {
                    val arrayList = arrayListOf<String>()
                    @Suppress("UNCHECKED_CAST")
                    arrayList.addAll(fieldValue as Collection<String>)
                    this.tags = arrayList
                }
            }
            FIELD_NAME_DEVICE_FLAGS -> {
                if (fieldValue is Int) this.deviceFlags = fieldValue
            }
            FIELD_NAME_TYPE -> {
                if (fieldValue is Int) this.type = fieldValue
            }
            FIELD_NAME_INTRODUCTION -> {
                if (fieldValue is String) this.introduction = fieldValue
            }
            FIELD_NAME_REF -> {
                if (fieldValue == null || fieldValue is Map<*, *>) {
                    try {
                        this.ref = fieldValue as Map<String, Any>?
                    } catch (ignored: Exception) {
                    }
                }
            }
            FIELD_NAME_PAGES -> {
                if (fieldValue is Collection<*>) {
                    try {
                        val arrayList = arrayListOf<Page2>()
                        arrayList.addAll(fieldValue as Collection<Page2>)
                        this.pages = arrayList
                    } catch (ignored: Exception) {
                    }
                }
            }
            FIELD_NAME_EXTENSIONS -> this.extensions = fieldValue
            else -> {}
        }
    }

    fun findPageById(id: String): Page2? {
        return pages.firstOrNull { it.id == id }
    }

    fun findPage(predicate: (Page2) -> Boolean): Page2? {
        return pages.firstOrNull(predicate)
    }

    companion object {

        const val DEVICE_FLAG_PHONE = 1 shl 0
        const val DEVICE_FLAG_TABLE = 1 shl 1
        const val DEVICE_FLAG_DESKTOP = 1 shl 2

        ///////////////////////////////////////////////////////////////////////////
        // FIELD NAME
        ///////////////////////////////////////////////////////////////////////////
        /**
         * The parent plugin.
         * The current plugin is called a child plugin relative to the parent plugin,
         * If a field exists in both the parent plugin and the child plugin, use the field in the child plugin
         * The types:
         * 1. Plugin: Map<String,Any?> or entity
         * 2. Local path, C:/xx/xx/xx/SamplePlugin.json
         * 3. Http path, https://xxx/xxx/SamplePlugin.json
         */
        const val FIELD_NAME_PARENT = "parent"

        /**
         * plugin name
         */
        const val FIELD_NAME_NAME = "name"

        /**
         * The plugin id
         */
        const val FIELD_NAME_ID = "id"

        /**
         * The author
         */
        const val FIELD_NAME_AUTHOR = "author"

        /**
         * Version: version name + version code
         * for example:
         * 1.0.0+1
         */
        const val FIELD_NAME_VERSION = "version"

        /**
         * Supported runtime, double closed interval
         * Format: minimum version supported - highest version supported
         */
        const val FIELD_NAME_RUNTIME = "runtime"

        /**
         * creation time, update time
         * For example: 2021-11-30 22:00:11,2022-01-01 08:00:11
         */
        const val FIELD_NAME_TIME = "time"

        /**
         * The tags of plugin
         */
        const val FIELD_NAME_TAGS = "tags"

        /**
         *  Supported device types (mobile phone, tablet, computer) 8bit
         *
         *  bit7 bit6 bit5 bit4 bit3 bit2 bit1 bit0
         *  0    0    0    0    0    0    0    0
         *
         *  bit0: Whether to support mobile phone, 0 not support 1 support
         *  bit1: Whether to support tablet, 0 not support 1 support
         *  bit2: Whether to support desktop, 0 not support 1 support
         *
         *  For example: the value 00000001 supports mobile phones only,
         *  the value 00000011 supports mobile phones and tablets
         */
        const val FIELD_NAME_DEVICE_FLAGS = "deviceFlags"

        /**
         * Type, see the fields of starts with 'TYPE_'
         */
        const val FIELD_NAME_TYPE = "type"

        /**
         * Plugin introduction
         */
        const val FIELD_NAME_INTRODUCTION = "introduction"

        /**
         * Custom references can be customized fields, objects, js code
         */
        const val FIELD_NAME_REF = "ref"

        /**
         * page collection
         */
        const val FIELD_NAME_PAGES = "pages"

        /**
         * Extend Object
         */
        const val FIELD_NAME_EXTENSIONS = "extensions" /* Map<String,Any?> or entity */

        ///////////////////////////////////////////////////////////////////////////
        // Http
        ///////////////////////////////////////////////////////////////////////////
        const val HTTP_REQ_TYPE_PREFIX_GET = "get:"
        const val HTTP_REQ_TYPE_PREFIX_POST = "post:"

        @JvmStatic
        fun isGetReq(url: String): Boolean {
            return url.startsWith(HTTP_REQ_TYPE_PREFIX_GET)
        }

        @JvmStatic
        fun isPostReq(url: String): Boolean {
            return url.startsWith(HTTP_REQ_TYPE_PREFIX_POST)
        }

        @JvmStatic
        fun removeReqPrefix(url: String): String {
            if (isGetReq(url)) {
                return url.substring(HTTP_REQ_TYPE_PREFIX_GET.length)
            } else if (isPostReq(url)) {
                return url.substring(HTTP_REQ_TYPE_PREFIX_POST.length)
            }
            return url
        }

        ///////////////////////////////////////////////////////////////////////////
        // Plugin Type
        ///////////////////////////////////////////////////////////////////////////
        const val TYPE_MOVIE = 0
    }
}