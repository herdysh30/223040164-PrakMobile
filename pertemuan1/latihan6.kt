package pertemuan1

fun main() {
    val greet = { name: String -> greeting(name) }
    greet("Kotlin")
}

fun greet(name: String) {
    println("Hello $name!")
}