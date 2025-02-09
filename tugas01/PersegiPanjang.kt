package tugas01

class PersegiPanjang(val panjang: Int, val lebar: Int) {
    fun luas(): Int {
        return panjang * lebar
    }
    fun keliling(): Int {
        return (panjang + lebar) * 2
    }
}
fun main() {
 val PersegiPanjang = PersegiPanjang(4, 2)
    val luas = PersegiPanjang.luas()
    val keliling = PersegiPanjang.keliling()
    println("Panjang : " + PersegiPanjang.panjang)
    println("Lebar : " + PersegiPanjang.lebar)
    println("Luas : " + luas)
    println("Keliling : " + keliling)
}