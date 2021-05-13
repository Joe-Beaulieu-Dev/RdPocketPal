package com.octrobi.rdpocketpal.suite

import com.octrobi.rdpocketpal.util.CalculationUtilTest
import com.octrobi.rdpocketpal.util.ConversionUtilTest
import com.octrobi.rdpocketpal.util.MetricEquationUtilTest
import com.octrobi.rdpocketpal.util.NumberUtilTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    CalculationUtilTest::class,
    ConversionUtilTest::class,
    MetricEquationUtilTest::class,
    NumberUtilTest::class
)
class UtilSuite