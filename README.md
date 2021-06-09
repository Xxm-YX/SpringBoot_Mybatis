# SpringBoot_Mybatis
关于枚举的一些学习
1.枚举的应用
2.枚举在mybatis里面如何存入数据库或从数据库中取出
    默认的将name存到数据库中，想要更改为ordinal存进去，直接在yml文件改一下就行
    使用自定义的 比如 type  desc这些属性来存， 自定义一个MyEnumTypeHandler来
    还需要在yml文件进行自定义

3. 关于枚举在前后端传输时的问题。
    springmvc分为以下几种情况
       GET/POST表单，默认使用StringToEnumConverterFactory，只支持Enum.name
       POST JSON默认支持Enum.name、Enum.ordinal
    如何自定义序列化和反序列化的属性都有