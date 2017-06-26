package com.clydeev.tex

import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException
import java.util.stream.Stream

/**
 * Simple class for reading csv files
 *
 * @author Clyde M. Velasquez
 * @version 1.0
 * @since 6/6/2017
 */
class CsvReader
    @Throws(IOException::class)
    constructor(var file: File) {

    private val lineReader: BufferedReader = BufferedReader(FileReader(this.file))
    private var line: String? = ""

    /**
     * Splits the current line into an Array of Strings
     */
    @Throws(IOException::class)
    fun lineSplit(): Array<String> = line!!.split(",".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()

    /**
     * Gets the current line
     */
    @Throws(IOException::class)
    fun line(): String? = line

    /**
     * Returns true if the file has next line otherwise false
     */
    @Throws(IOException::class)
    fun hasNextLine(): Boolean {
        line = lineReader.readLine()
        return line != null
    }

    /**
     * Returns all the lines as Stream of Strings
     */
    fun lines(): Stream<String> = lineReader.lines()
}