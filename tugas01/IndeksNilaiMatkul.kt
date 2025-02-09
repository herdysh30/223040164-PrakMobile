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

fun main() {
    val indeks = IndeksNilaiMatkul()

    val nilai1: Int? = 77
    println("1. Nilai: $nilai1 -> ${indeks.IndeksNilai(nilai1)}")

    val nilai2: Int? = 110
    println("2. Nilai: $nilai2 -> ${indeks.IndeksNilai(nilai2)}")

    val nilai3: Int? = null
    println("3. Nilai: $nilai3 -> ${indeks.IndeksNilai(nilai3)}")

    val nilai4: Int? = 25
    println("4. Nilai: $nilai4 -> ${indeks.IndeksNilai(nilai4)}")
}
