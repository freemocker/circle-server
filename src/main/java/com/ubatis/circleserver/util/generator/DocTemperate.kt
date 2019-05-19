package com.ubatis.circleserver.util.generator

fun getDocContent(tableName: String, tableComment: String, fieldList: List<GenFieldBean>) :String{

fun writeFieldsInfo(require: Boolean = true) :String{
    var ret = StringBuilder()
    fieldList.forEach {
        ret.append("""
            |	${it.name}	|  ${it.type}	| ${it.comment} | ${if(require) "是否  |" else ""}

        """.trimIndent())
    }
return ret.toString()
}

fun writeFieldsJson() :String{
    var ret = StringBuilder()
    for ((index, value) in fieldList.withIndex()) {
        ret.append("""  "${fieldList[index].name}": ${fieldList[index].type}""")
                .append("""${if(index === (fieldList.size - 1)) "" else ","}""")
                .append("""${if(fieldList[index].comment.isNullOrEmpty()) "" else " // ${fieldList[index].comment}"} """)
                .append("""${if(index === (fieldList.size - 1)) "" else "\n"}""")
    }
    return ret.toString()
}


    var DOC = """
# 表名
$tableName

# 表注释
$tableComment

# 表设计说明

|   字段名    |    类型   |   注释   |
| :--------: | :--------:|:--------|
${writeFieldsInfo(false)}

# 参数说明模板

```
|   参数名    |    类型   |   注释   |      必需|
| :--------: | :--------:|:--------|:--------:|
${writeFieldsInfo()}
```

# 返回json模板

```
{
${writeFieldsJson()}
}
```
    """
    return DOC
}