package tugas01

class IndeksNilaiMatkul {
    fun IndeksNilai(nilai: Int?): String {
        if (nilai == null) {
            return "Nilai harus diisi"
        }
        if (nilai > 100 || nilai < 0) {
            return "Nilai di luar jangkauan"
        }
        return when (nilai) {
            in 80..100 -> "A"
            in 70..79 -> "AB"
            in 60..69 -> "B"
            in 50..59 -> "BC"
            in 40..49 -> "C"
            in 30..39 -> "D"
            else -> "E"
        }
    }
}

fun main(args: Array<String>) {
    val indeks = IndeksNilaiMatkul()

    if (args.isNotEmpty()) {
        for (i in args.indices) {
            val nilai = args[i].toIntOrNull()
            val result = if (nilai != null) {
                indeks.IndeksNilai(nilai)
            } else {
                "Nilai harus diisi"
            }
            println("${i + 1} ${args[i]} $result")
        }
    } else {
        println("Masukkan nilai-nilai sebagai program argument.")
    }
}