package dowanloadfilebyspring.demo.controller

import dowanloadfilebyspring.demo.servis.ServisUpload
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.codec.multipart.FilePart
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import java.awt.PageAttributes
import org.synchronoss.cloud.nio.multipart.MultipartUtils.getHeaders
import org.springframework.http.ZeroCopyHttpOutputMessage
import java.io.File
import java.io.IOException
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.GetMapping
import java.nio.file.Paths


@RestController
class FileController(val servisUpload:ServisUpload) {
    @PostMapping( "/upload")
    public fun requestBodyFlux(@RequestPart("file") filePart: FilePart): Mono<String>
    {
        return servisUpload.save(filePart)
    }
//    @GetMapping("/download/{name:.+}")
//    public fun downloadByWriteWith(response: ServerHttpResponse,@PathVariable name:String):Mono<Void>{
//        val zeroCopyResponse = response as ZeroCopyHttpOutputMessage
//        response.getHeaders().set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=$name")
//        response.getHeaders().contentType = MediaType.APPLICATION_OCTET_STREAM
//       var file=servisUpload.download(name)
//        return zeroCopyResponse.writeWith(file, 0, file.length());
//    }
@GetMapping("/download/{fileName:.+}")
@Throws(IOException::class)
fun downloadByWriteWith(@PathVariable fileName: String, response: ServerHttpResponse): Mono<Void> {
    val zeroCopyResponse = response as ZeroCopyHttpOutputMessage
    response.getHeaders().set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=uploads/$fileName")
    val file = Paths.get("upload/$fileName").toFile()
    response.getHeaders().contentType = MediaType.parseMediaType("application/octet-stream")
    return zeroCopyResponse.writeWith(file, 0, file.length())
}
}