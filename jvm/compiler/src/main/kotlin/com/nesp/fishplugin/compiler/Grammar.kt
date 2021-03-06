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

package com.nesp.fishplugin.compiler

import com.nesp.fishplugin.core.data.Plugin2


object Grammar {

    /**
     * Checks plugin grammar
     */
    @JvmStatic
    fun checkGrammar(plugin: Plugin2, isParent: Boolean = false): GrammarCheckResult {
        if (plugin.parent != null) {
            if (plugin.parent !is String && plugin.parent !is Plugin2) {
                return GrammarCheckResult(
                    GrammarCheckResult.LEVEL_ERROR,
                    "The type of parent plugin is not supported"
                )
            }

            if (plugin.parent is Plugin2) {
                val checkParentGrammar = checkGrammar(plugin.parent as Plugin2, true)
                if (checkParentGrammar.level != GrammarCheckResult.LEVEL_NONE) {
                    return checkParentGrammar
                }
            }
        }

        val checkFields = plugin.checkFields()
        if (checkFields.isNotEmpty()) {
            return GrammarCheckResult(GrammarCheckResult.LEVEL_ERROR, checkFields)
        }

        if (plugin.name.trim().isEmpty() && !isParent) {
            return GrammarCheckResult(GrammarCheckResult.LEVEL_ERROR, "name cannot empty")
        }

        if (plugin.id.trim().isEmpty() && !isParent) {
            return GrammarCheckResult(GrammarCheckResult.LEVEL_ERROR, "id cannot empty")
        }

        if (plugin.version.trim().isEmpty()) {
            if (!isParent) {
                return GrammarCheckResult(GrammarCheckResult.LEVEL_ERROR, "version cannot empty")
            }
        } else {
            val plusCount = plugin.version.count { it == '+' }
            if (plusCount < 0 || plusCount > 1) {
                return GrammarCheckResult(
                    GrammarCheckResult.LEVEL_ERROR, """
                    wrong version format: ${plugin.version}
                    The sample: 1.0.0+1
                """.trimIndent()
                )
            }
        }

        if (plugin.runtime.trim().isEmpty() && !isParent) {
            return GrammarCheckResult(GrammarCheckResult.LEVEL_ERROR, "runtime cannot empty")
        }

        // do not check time
        // do not check tag

        if (plugin.deviceFlags !in 0x01..0x07) {
            if (!isParent || plugin.deviceFlags != -1) {
                return GrammarCheckResult(
                    GrammarCheckResult.LEVEL_ERROR,
                    "deviceFlags(${plugin.deviceFlags}) parse error"
                )
            }
        }

        if (plugin.pages.isEmpty()) {
            return GrammarCheckResult(GrammarCheckResult.LEVEL_ERROR, "pages is empty")
        }

        for (page in plugin.pages) {
            page.checkFields()
        }

        return GrammarCheckResult(GrammarCheckResult.LEVEL_NONE, "Grammar check passed")
    }

    data class GrammarCheckResult(
        var level: Int, // 0 pass, 1 warning, 2 error
        var message: String = "",
    ) {
        companion object {
            const val LEVEL_NONE = 0
            const val LEVEL_WARNING = 1
            const val LEVEL_ERROR = 2
        }
    }

    /**
     * Replace variable name to value in [variableNameValue] in [text]
     *
     * Example:
     *
     * applyVariableValue("i like {{var}}", {"var":"her"}) = "i like her"
     */
    fun applyVariableValue(text: String, variableNameValue: Map<String, String>): String {
        val variables = lookupVariables(text)
        var result = text
        val replacedText = arrayListOf<String>()
        for (variable in variables) {
            if (replacedText.contains(variable.name)) continue
            if (variableNameValue.containsKey(variable.name)) {
                variable.value = variableNameValue[variable.name]
                result = result.replace("{{${variable.name}}}", variable.value.toString())
                replacedText.add(variable.name)
            }
        }
        return result
    }

    /**
     * Lookups all variable in [text], the variable wrapped with {{ and }}
     *
     * Example:
     * variable named a
     * {{a}}
     */
    fun lookupVariables(text: String): List<Variable> {
        val result = mutableListOf<Variable>()
        val regex = getVariableRegex()
        val resultSequence = regex.findAll(text)
        for (matchResult in resultSequence) {
            result.add(Variable(matchResult.value, null, matchResult.range))
        }
        return result
    }

    /**
     * Whether it has variable in [text]
     */
    fun hasVariable(text: String): Boolean {
        return getVariableRegex().matches(text)
    }

    private fun getVariableRegex(): Regex {
        val pattern = "(?<=\\{\\{).*?(?=}})"
        return Regex(pattern)
    }

}