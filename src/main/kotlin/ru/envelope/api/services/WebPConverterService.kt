package ru.envelope.api.services

import org.springframework.stereotype.Service
import ru.envelope.api.exceptions.ImageNotConvertedException
import java.io.File
import java.nio.file.Path
import javax.imageio.ImageIO

@Service
class WebPConverterService {
    fun convertAndSave(file: File, destination: Path) {
        val resizeParams = getResizeParams(file)
        val pb = ProcessBuilder("cwebp", "-q", "85", "-mt", resizeParams[0], resizeParams[1], resizeParams[2], file.absolutePath, "-o", destination.toString())
        pb.inheritIO()
        val process = pb.start()
        val exitCode = process.waitFor()

        if (exitCode != 0) {
            throw ImageNotConvertedException()
        }
    }

    private fun getResizeParams(file: File): Array<String> {
        val image = ImageIO.read(file)
        return if (image.width >= image.height) {
            arrayOf("-resize", "1000", "0")
        } else {
            arrayOf("-resize", "0", "1000")
        }
    }
}