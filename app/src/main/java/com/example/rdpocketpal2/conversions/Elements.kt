package com.example.rdpocketpal2.conversions

enum class Element(val atomicWeight: Double, val valence: Int) {
    CALCIUM(40.078, 2),
    CHLORINE(35.45, 1),
    MAGNESIUM(24.305, 2),
    // phosphorus valence can be 3 or 5
    PHOSPHORUS(30.974, 3),
    POTASSIUM(39.098, 1),
    SODIUM(22.99, 1),
}