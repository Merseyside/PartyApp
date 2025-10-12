import org.gradle.api.Project
import java.io.File

inline fun <reified T> Project.findTypedProperty(propertyName: String): T {

    val stringProperty = findProperty(propertyName) as? String

    return stringProperty?.let {
        when (T::class) {
            Boolean::class -> stringProperty.toBoolean()
            Int::class -> stringProperty.toInt()
            Float::class -> stringProperty
            else -> it
        }
    } as? T ?: throw Exception("Property $propertyName not found")
}

fun Project.getKeyAlias(): String = findTypedProperty("RELEASE_KEY_ALIAS")
fun Project.getSigningPassword(): String = findTypedProperty("RELEASE_KEY_PASSWORD")
fun Project.getKeystoreFile(): File = file(findTypedProperty("RELEASE_STORE_FILE"))
fun Project.getStorePassword(): String = findTypedProperty("RELEASE_STORE_PASSWORD")