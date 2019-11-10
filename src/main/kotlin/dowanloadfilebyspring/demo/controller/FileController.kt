package dowanloadfilebyspring.demo.controller

import dowanloadfilebyspring.demo.servis.ServisUpload
import org.springframework.http.MediaType
import org.springframework.http.codec.multipart.FilePart
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import java.awt.PageAttributes

@RestController
class FileController(val servisUpload:ServisUpload) {
    @PostMapping( "/upload")
    public fun requestBodyFlux(@RequestPart("file") filePart: FilePart): Mono<String>
    {
        return servisUpload.save(filePart)
    }
}