package com.wengtao.my_utils

/**
 * 反射获取系统属性信息，类似getprop
 *
 * @author hejq
 */
object SystemPropertiesUtil {
    private val TAG = "SystemPropertiesUtil"

    fun get(key: String): String? {
        var value: String?
        val systemPropertiesClass: Class<*>
        try {
            systemPropertiesClass = Class.forName("android.os.SystemProperties")
            val systemPropertiesObject = systemPropertiesClass.newInstance()
            val getMethod = systemPropertiesClass.getDeclaredMethod("get",
                    String::class.java)
            getMethod.isAccessible = true
            value = getMethod.invoke(systemPropertiesObject, key) as String
        } catch (e: Exception) {
            value = null
            e.printStackTrace()
        }

        return value
    }

    /**
     * Get the value for the given key, and return as an integer.
     *
     * @param key the key to lookup
     * @param def a default value to return
     * @return the key parsed as an integer, or def if the key isn't found or
     * cannot be parsed
     * @throws IllegalArgumentException if the key exceeds 32 characters
     */
    @Throws(IllegalArgumentException::class)
    fun getInt(key: String, def: Int): Int? {
        var ret: Int? = def
        try {

            val systemProperties = Class.forName("android.os.systemProperties")

            //Parameters Types
            val paramTypes = arrayOfNulls<Class<*>>(2)
            paramTypes[0] = String::class.java
            paramTypes[1] = Int::class.javaPrimitiveType

            val getInt = systemProperties.getMethod("getInt", *paramTypes)

            //Parameters
            val params = arrayOfNulls<Any>(2)
            params[0] = key
            params[1] = def

            ret = getInt.invoke(systemProperties, *params) as Int

        } catch (iAE: IllegalArgumentException) {
            throw iAE
        } catch (e: Exception) {
            ret = def
        }

        return ret

    }

    /**
     * Get the value for the given key, and return as a long.
     *
     * @param key the key to lookup
     * @param def a default value to return
     * @return the key parsed as a long, or def if the key isn't found or
     * cannot be parsed
     * @throws IllegalArgumentException if the key exceeds 32 characters
     */
    @Throws(IllegalArgumentException::class)
    fun getLong(key: String, def: Long): Long? {
        var ret: Long? = def
        try {

            val systemProperties = Class.forName("android.os.systemProperties")

            //Parameters Types
            val paramTypes = arrayOfNulls<Class<*>>(2)
            paramTypes[0] = String::class.java
            paramTypes[1] = Long::class.javaPrimitiveType

            val getLong = systemProperties.getMethod("getLong", *paramTypes)

            //Parameters
            val params = arrayOfNulls<Any>(2)
            params[0] = key
            params[1] = def

            ret = getLong.invoke(systemProperties, *params) as Long

        } catch (iAE: IllegalArgumentException) {
            throw iAE
        } catch (e: Exception) {
            ret = def
        }

        return ret

    }

    /**
     * Get the value for the given key, returned as a boolean.
     * Values 'n', 'no', '0', 'false' or 'off' are considered false.
     * Values 'y', 'yes', '1', 'true' or 'on' are considered true.
     * (case insensitive).
     * If the key does not exist, or has any other value, then the default
     * result is returned.
     *
     * @param key the key to lookup
     * @param def a default value to return
     * @return the key parsed as a boolean, or def if the key isn't found or is
     * not able to be parsed as a boolean.
     * @throws IllegalArgumentException if the key exceeds 32 characters
     */
    @Throws(IllegalArgumentException::class)
    fun getBoolean(key: String, def: Boolean): Boolean? {
        var ret: Boolean? = def
        try {

            val systemProperties = Class.forName("android.os.systemProperties")

            //Parameters Types
            val paramTypes = arrayOfNulls<Class<*>>(2)
            paramTypes[0] = String::class.java
            paramTypes[1] = Boolean::class.javaPrimitiveType

            val getBoolean = systemProperties.getMethod("getBoolean", *paramTypes)

            //Parameters
            val params = arrayOfNulls<Any>(2)
            params[0] = key
            params[1] = def

            ret = getBoolean.invoke(systemProperties, *params) as Boolean

        } catch (iAE: IllegalArgumentException) {
            throw iAE
        } catch (e: Exception) {
            ret = def
        }

        return ret

    }


    @Throws(IllegalArgumentException::class)
    operator fun set(key: String, `val`: String) {
        try {

            val systemProperties = Class.forName("android.os.systemProperties")

            //Parameters Types
            val paramTypes = arrayOfNulls<Class<*>>(2)
            paramTypes[0] = String::class.java
            paramTypes[1] = String::class.java

            val set = systemProperties.getMethod("set", *paramTypes)

            //Parameters
            val params = arrayOfNulls<Any>(2)
            params[0] = key
            params[1] = `val`

            set.invoke(systemProperties, *params)

        } catch (iAE: IllegalArgumentException) {
            throw iAE
        } catch (e: Exception) {
        }

    }


}
