package com.nesp.fishplugin.compiler

import com.nesp.fishplugin.core.Result
import com.nesp.fishplugin.core.data.Page
import com.nesp.fishplugin.core.data.Plugin

object Compiler {

    fun compileFromDisk(path: String): CompileResult {
        // Load plugin
        val loadResult = Loader.loadPluginFromDisk(path)
        if (loadResult.code != Result.CODE_SUCCESS) {
            return CompileResult(Result.CODE_FAILED, loadResult.message)
        }
        return doCompile(loadResult.data!!)
    }

    fun compileFromUrl(url: String): CompileResult {
        // Load plugin
        val loadResult = Loader.loadPluginFromUrl(url)
        if (loadResult.code != Result.CODE_SUCCESS) {
            return CompileResult(Result.CODE_FAILED, loadResult.message)
        }
        return doCompile(loadResult.data!!)
    }

    fun compile(plugin: Plugin): CompileResult {
        // Load plugin
        val loadResult = Loader.load(plugin)
        if (loadResult.code != Result.CODE_SUCCESS) {
            return CompileResult(Result.CODE_FAILED, loadResult.message)
        }
        return doCompile(plugin)
    }

    /**
     * Compile plugin
     */
    private fun doCompile(plugin: Plugin): CompileResult {

        // Check parent
        if (plugin.parent != null && plugin.parent !is Plugin) {
            return CompileResult(Result.CODE_FAILED, "prent is not supported")
        }

        // Check plugin grammar
        val grammarCheckResult = Grammar.checkGrammar(plugin)
        if (grammarCheckResult.level == Grammar.GrammarCheckResult.LEVEL_ERROR) {
            return CompileResult(Result.CODE_FAILED, grammarCheckResult.message)
        }
        if (grammarCheckResult.level == Grammar.GrammarCheckResult.LEVEL_WARNING) {
            println("Warning: Grammar Check: " + grammarCheckResult.message)
        }

        // Lookup and apply variable
        val lookupAndApplyVariableErrorMsg = lookupAndApplyVariable(plugin)
        if (lookupAndApplyVariableErrorMsg.isNotEmpty()) {
            return CompileResult(Result.CODE_FAILED, lookupAndApplyVariableErrorMsg)
        }

        // Remove parent if compile success
        plugin.parent = null

        // Remove ref if compile success
        plugin.ref = null

        return CompileResult(Result.CODE_SUCCESS, data = plugin)
    }

    /**
     * Lookup all variable reference and replace it with value.
     * @return error if failed.
     */
    private fun lookupAndApplyVariable(plugin: Plugin): String {
        if (plugin.parent != null) {
            val lookupAndApplyVariableOfParent = lookupAndApplyVariable(plugin.parent as Plugin)
            if (lookupAndApplyVariableOfParent.isNotEmpty()) {
                return lookupAndApplyVariableOfParent
            }
        }

        // Name
        var lookupAndApplyVariableResult = lookupAndApplyVariable(plugin, Plugin.FILED_NAME_NAME)
        if (lookupAndApplyVariableResult.isNotEmpty()) {
            return lookupAndApplyVariableResult
        }

        // Version
        lookupAndApplyVariableResult = lookupAndApplyVariable(plugin, Plugin.FILED_NAME_VERSION)
        if (lookupAndApplyVariableResult.isNotEmpty()) {
            return lookupAndApplyVariableResult
        }

        // Runtime
        lookupAndApplyVariableResult = lookupAndApplyVariable(plugin, Plugin.FILED_NAME_RUNTIME)
        if (lookupAndApplyVariableResult.isNotEmpty()) {
            return lookupAndApplyVariableResult
        }

        // Time
        lookupAndApplyVariableResult = lookupAndApplyVariable(plugin, Plugin.FILED_NAME_TIME)
        if (lookupAndApplyVariableResult.isNotEmpty()) {
            return lookupAndApplyVariableResult
        }

        // Introduction
        lookupAndApplyVariableResult =
            lookupAndApplyVariable(plugin, Plugin.FILED_NAME_INTRODUCTION)
        if (lookupAndApplyVariableResult.isNotEmpty()) {
            return lookupAndApplyVariableResult
        }

        // Pages
        lookupAndApplyVariableResult =
            lookupAndApplyVariable(plugin, Plugin.FILED_NAME_PAGES)
        if (lookupAndApplyVariableResult.isNotEmpty()) {
            return lookupAndApplyVariableResult
        }

        return ""
    }

    private fun lookupAndApplyVariable(plugin: Plugin, fieldName: String): String {
        val fieldValue = plugin.getFieldValue(fieldName)

        if (fieldValue == null || (fieldValue is String && fieldValue.isEmpty())) {
//            return "Not support for field $fieldName"
            return ""
        }

        if (fieldName == Plugin.FILED_NAME_NAME
            || fieldName == Plugin.FILED_NAME_VERSION
            || fieldName == Plugin.FILED_NAME_RUNTIME
            || fieldName == Plugin.FILED_NAME_TIME
            || fieldName == Plugin.FILED_NAME_INTRODUCTION
        ) {
            if (fieldValue !is String) return ""
            val variablesOfField = Grammar.lookupVariables(fieldValue)
            if (variablesOfField.isNotEmpty()) {
                if (Variable.exitsVariable(fieldName, variablesOfField)) {
                    return "The $fieldName field contains itself"
                }

                if (plugin.ref.isNullOrEmpty()) {
                    return "Cant find any variable on $fieldName"
                }

                for (variable in variablesOfField) {
                    if (variable.name.trim().isEmpty()) {
                        return "Exits empty variable on $fieldName"
                    }

                    variable.value = plugin.findRefVariable(variable.name)
                        ?: return "Variable ${variable.name} not exits in ref"

                    if (!Variable.isPrimitiveType(variable.value!!)) {
                        return "Only support primitive type variable for field $fieldName"
                    }

                    val variableNameAndValue = hashMapOf<String, String>()
                    variableNameAndValue[variable.name] = variable.value!!.toString()
                    plugin.setFieldValue(
                        fieldName,
                        Grammar.applyVariableValue(fieldValue, variableNameAndValue)
                    )
                }
            }
        } else if (fieldName == Plugin.FILED_NAME_PAGES) {
            for (page in plugin.pages) {
                var result = lookupAndApplyVariable(plugin, page, Page.FIELD_NAME_REF_URL)
                if (result.isNotEmpty()) return result
                result = lookupAndApplyVariable(plugin, page, Page.FIELD_NAME_URL)
                if (result.isNotEmpty()) return result
                result = lookupAndApplyVariable(plugin, page, Page.FIELD_NAME_JS)
                if (result.isNotEmpty()) return result
                result = lookupAndApplyVariable(plugin, page, Page.FIELD_NAME_DSL)
                if (result.isNotEmpty()) return result
            }
        } else {
            return "Variable in $fieldName is not supported"
        }
        return ""
    }

    private fun lookupAndApplyVariable(plugin: Plugin, page: Page, fieldName: String): String {
        if (fieldName == Page.FIELD_NAME_ID) {
            return "Not support for field $fieldName"
        }

        val fieldValue = page.getFieldValue(fieldName)

        if (fieldValue == null || (fieldValue is String && fieldValue.isEmpty())) {
//            return "Not support for field $fieldName"
            return ""
        }

        if (fieldValue !is String) return ""
        val variablesOfField = Grammar.lookupVariables(fieldValue)
        if (variablesOfField.isNotEmpty()) {
            if (Variable.exitsVariable(fieldName, variablesOfField)) {
                return "The $fieldName field contains itself"
            }

            if (plugin.ref.isNullOrEmpty()) {
                return "Cant find any variable on $fieldName"
            }

            for (variable in variablesOfField) {
                if (variable.name.trim().isEmpty()) {
                    return "Exits empty variable on $fieldName"
                }

                variable.value = plugin.findRefVariable(variable.name)
                    ?: return "Variable ${variable.name} not exits in ref"

                if (fieldName == Page.FIELD_NAME_REF_URL
                    || fieldName == Page.FIELD_NAME_URL
                    || fieldName == Page.FIELD_NAME_JS
                ) {
                    if (variable.value !is String) {
                        return "Only support string variable for field $fieldName"
                    }

                    val variableNameAndValue = hashMapOf<String, String>()
                    variableNameAndValue[variable.name] = variable.value!!.toString()
                    page.setFieldValue(
                        fieldName,
                        Grammar.applyVariableValue(fieldValue, variableNameAndValue)
                    )
                } else if (fieldName == Page.FIELD_NAME_DSL) {
                    if (variable.value !is Map<*, *>) {
                        return "Only support Map variable for field $fieldName"
                    }
                    val map = variable.value as Map<*, *>?
                    if (!map.isNullOrEmpty()) page.dsl = map
                } else {
                    return "Variable in $fieldName is not supported"
                }
            }
        }
        return ""
    }

    class CompileResult(
        code: Int,
        message: String = "",
        data: Plugin? = null
    ) : Result<Plugin>(code, message, data)
}