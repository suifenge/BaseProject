package com.suifeng.sdk.network.log

object JsonUtil {

    /**
     * 格式化json字符串
     *
     * @param jsonStr 需要格式化的json串
     * @return 格式化后的json串
     */
    fun formatJson(jsonStr: String): String {
        if(jsonStr.isEmpty()) return ""
        val sb = StringBuilder()
        var last = '\u0000'
        var current = '\u0000'
        var indent = 0
        for (i in jsonStr.indices) {
            last = current
            current = jsonStr[i]
            when (current) {
                '{', '[' -> {
                    sb.append(current)
                    sb.append('\n')
                    indent++
                    addIndentBlank(sb, indent)
                }
                '}', ']' -> {
                    sb.append('\n')
                    indent--
                    addIndentBlank(sb, indent)
                    sb.append(current)
                }
                ',' -> {
                    sb.append(current)
                    if (last != '\\') {
                        sb.append('\n')
                        addIndentBlank(sb, indent)
                    }
                }
                else -> sb.append(current)
            }
        }
        return sb.toString()
    }

    private fun addIndentBlank(sb: StringBuilder, indent: Int) {
        for (i in 0 until indent) {
            sb.append('\t')
        }
    }

    /**
     * http 请求数据返回 json 中中文字符为 unicode 编码转汉字转码
     *
     * @param theString
     * @return 转化后的结果.
     */
    fun decodeUnicode(theString: String): String {
        val len = theString.length
        val outBuffer = StringBuffer(len)
        var aChar: Char
        var x = 0
        while (x < len) {
            aChar = theString[x++]
            if(aChar == '\\') {
                aChar = theString[x++]
                if(aChar == 'u') {
                    var value = 0
                    for (i in 0..3) {
                        aChar = theString[x++]
                        value = when (aChar) {
                            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> (value shl 4) + aChar.code - '0'.code
                            'a', 'b', 'c', 'd', 'e', 'f' -> (value shl 4) + 10 + aChar.code - 'a'.code
                            'A', 'B', 'C', 'D', 'E', 'F' -> (value shl 4) + 10 + aChar.code - 'A'.code
                            else -> throw IllegalArgumentException(
                                "Malformed   \\uxxxx   encoding."
                            )
                        }
                    }
                    outBuffer.append(value.toChar())
                } else {
                    when (aChar) {
                        't' -> aChar = '\t'
                        'r' -> aChar = '\r'
                        'n' -> aChar = '\n'
                        'f' -> aChar = '\u000c'
                    }
                    outBuffer.append(aChar)
                }
            } else {
                outBuffer.append(aChar)
            }
        }
        return outBuffer.toString()
    }
}