package operations

fun isPrimeNumber(numero: Int): Boolean {
    if (numero <= 1) return false
    var i = 2
    while (i * i <= numero) {
        if (numero % i == 0) return false
        i++
    }
    return true
}