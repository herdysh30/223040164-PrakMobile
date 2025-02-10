package tugas01

class PersegiPanjang(val panjang: Int, val lebar: Int) {
    fun luas(): Int {
        return panjang * lebar
    }

    fun keliling(): Int {
        return (panjang + lebar) * 2
    }
}

fun main(args: Array<String>) {
    if (args.size >= 2) {
        val panjang = args[0].toIntOrNull()
        val lebar = args[1].toIntOrNull()

        if (panjang != null && lebar != null) {
            val persegiPanjang = PersegiPanjang(panjang, lebar)
            val luas = persegiPanjang.luas()
            val keliling = persegiPanjang.keliling()

            println("Panjang: $panjang")
            println("Lebar: $lebar")
            println("Luas: $luas")
            println("Keliling: $keliling")
        } else {
            println("Masukkan nilai panjang dan lebar yang valid.")
        }
    } else {
        println("Masukkan dua program argument: panjang dan lebar.")
    }
}
