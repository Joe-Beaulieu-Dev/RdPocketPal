package com.example.rdpocketpal2.conversions

interface Element {
    val atomicWeight: Double
    val valence: Int
}

class Calcium : Element {
    override val atomicWeight = 40.078
    override val valence = 2
}

class Chlorine : Element {
    override val atomicWeight = 35.45
    override val valence = 1
}

class Magnesium : Element {
    override val atomicWeight = 24.305
    override val valence = 2
}

class Phosphorus : Element {
    override val atomicWeight = 30.974
    // can be 3 or 5
    override val valence = 3
}

class Potassium : Element {
    override val atomicWeight = 39.098
    override val valence = 1
}

class Sodium : Element {
    override val atomicWeight = 22.99
    override val valence = 1
}