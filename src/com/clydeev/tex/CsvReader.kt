package com.clydeev.tex

import java.io.*
import java.util.stream.Stream

/**
 * Simple class for reading csv files
 *
 * @author Clyde M. Velasquez
 * @version 1.0
 * @since 6/6/2017
 */
public class CsvReader
    @Throws(IOException::class)
    constructor(var file: File) {

    private val lineReader: BufferedReader = BufferedReader(FileReader(this.file))
    private var line: String? = ""

    @Throws(IOException::class)
    fun lineSplit(): Array<String> = line!!.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

    @Throws(IOException::class)
    fun line(): String? = line

    @Throws(IOException::class)
    fun hasNextLine(): Boolean {
        line = lineReader.readLine()
        return line != null
    }

    fun lines(): Stream<String> = lineReader.lines()
}