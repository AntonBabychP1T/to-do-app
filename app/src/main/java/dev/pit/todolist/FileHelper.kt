package dev.pit.todolist

import android.content.Context
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

private const val FILENAME = "listinfo.dat"

class FileHelper {

    fun writeData(item : ArrayList<Task>, context: Context) {
        val fos : FileOutputStream = context.openFileOutput(FILENAME, Context.MODE_PRIVATE)

        ObjectOutputStream(fos).apply {
            writeObject(item)
            close()
        }
    }


    fun readData(context: Context): ArrayList<Task> {
        return try {
            val fis: FileInputStream = context.openFileInput(FILENAME)
            val ois = ObjectInputStream(fis)
            ois.readObject() as ArrayList<Task>
        } catch (e: FileNotFoundException) {
            ArrayList()
        } catch (e: Exception) {
            context.deleteFile(FILENAME)
            ArrayList()
        }
    }
}