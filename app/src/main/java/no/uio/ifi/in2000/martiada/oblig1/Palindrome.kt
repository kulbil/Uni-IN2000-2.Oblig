package no.uio.ifi.in2000.martiada.oblig1

fun isPalindrome(s: String): Boolean {
    return s.equals(s.reversed(), ignoreCase = true)
}
