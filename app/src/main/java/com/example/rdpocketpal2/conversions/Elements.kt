package com.example.rdpocketpal2.conversions

enum class Element(val atomicWeight: Double, val valence: Int) {
    Calcium(40.078, 2),
    Chlorine(35.45, 1),
    Magnesium(24.305, 2),
    // phosphorus valence can be 3 or 5
    Phosphorus(30.974, 3),
    Potassium(39.098, 1),
    Sodium(22.99, 1),
}