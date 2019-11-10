package dowanloadfilebyspring.demo.servis

import org.springframework.core.io.buffer.DataBufferUtils
import org.springframework.http.codec.multipart.FilePart
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.nio.channels.AsynchronousFileChannel
import java.nio.file.Files


@Service
class ServisUpload {
    val base:Path = Paths.get("/upload")
    //в функцию передаётся имя,а выводится абсолютный путь.
    private fun getPath(name:String)= base.resolve(name)
    fun save(filePart: FilePart):Mono<String>{
        val path = Files.createFile(Paths.get("upload", filePart.filename()))
        val channel = AsynchronousFileChannel.open(path, StandardOpenOption.WRITE)
        //databuffer специализированный класс,который создаёт ,закрывает,обслуживает буффер,в которому подключаются каналы
        DataBufferUtils.write(filePart.content(), channel, 0)
                .doOnComplete { println("finish") }
                .subscribe()
                return Mono.just("create")
    }

}