package com.clydeev.tex

import java.io.*
import java.nio.file.Files
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

/**
 *
 * @author Clyde M. Velasquez
 * @version 1.0
 * @since 6/6/2017
 */
object TexUtil {
    private val CURRENT = File("")

    @Throws(IOException::class)
    @JvmStatic fun main(args: Array<String>) {
        if (args.isEmpty() || args.size > 1) {
            message()
        } else if (args.size == 1) {
            // Check args if it contains tex files
            val texFile = File(args[0])
            if (texFile.exists()) {
                if (!texFile.isFile && !texFile.absolutePath.trim { it <= ' ' }.endsWith(".tex")) {
                    printlnErr("ERROR: Error processing " + texFile.absolutePath)
                    printlnErr("Make sure it's a TEX file!")
                    return
                }
            } else {
                printlnErr("ERROR: File does not exist!")
                return
            }

            // Create a back-up
            val backUp = File(texFile.parentFile,
                    texFile.name.substring(0, texFile.name.lastIndexOf(".")) +
                            "_backup_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMM_ddhhmmss")) + ".tex")
            try {
                println("Creating a back-up...")
                Files.copy(texFile.toPath(), backUp.toPath())
                println("Done creating back-up file.")
            } catch (e: IOException) {
                printlnErr("ERROR: Error in creating back-up")
                // e.printStackTrace(); // For debugging purposes
                throw e
            }

            val sb = StringBuilder()
            if (texFile.exists()) {
                try {
                    println("Reading file contents...")
                    BufferedReader(FileReader(texFile)).use { bReader ->
                        bReader.lines().forEachOrdered { line -> sb.append(line).append("\n") }
                    }
                    println("Done reading file contents.")
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

            var fileContents = sb.toString()
            try {
                println("Processing " + texFile.name + "...")
                BufferedWriter(FileWriter(texFile)).use { writer ->
                    val keys = dictionary().keys
                    for (key in keys) {
                        fileContents = fileContents.replace(key.toRegex(), dictionary()[key].toString())
                    }

                    writer.write(fileContents)
                    writer.close()
                }
                println("Done replacing special characters.")
            } catch (e: IOException) {
                printlnErr(e.message)
            }
        }
    }

    private fun message() {
        println("USAGE: java -jar TexUtil.jar file1.tex")
        println("Handles only one TEX file at a time.")
        println("special_characters.csv must be the same directory as TexUtil.jar")
    }

    // Read the csv file and add to a HashMap collection
    @Throws(IOException::class)
    private fun dictionary(): HashMap<String, String> {
        val dictionary = HashMap<String, String>()

        try {
            val specialCharactersCSV = File(CURRENT.toString() + "special_characters.csv")
            val reader = CsvReader(specialCharactersCSV)

            while (reader.hasNextLine()) {
                val value = reader.lineSplit()
                dictionary.put(value[0], value[1])
            }
        } catch (e: IOException) {
            throw IOException("Cannot find special_characters.csv")
        }

        return dictionary
    }

    private fun printlnErr(message: Any?) = System.err.println(message)
}