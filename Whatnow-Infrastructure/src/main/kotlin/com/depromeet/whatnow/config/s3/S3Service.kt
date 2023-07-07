package com.depromeet.whatnow.config.s3

import com.amazonaws.HttpMethod
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.Headers
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.DeleteObjectRequest
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest
import com.depromeet.whatnow.helper.SpringEnvironmentHelper
import org.springframework.stereotype.Service
import java.util.*

@Service
class S3Service(
    val amazonS3: AmazonS3,
    s3Properties: S3Properties,
    val springEnvironmentHelper: SpringEnvironmentHelper,
) {
    val s3Secret: S3Properties.S3Secret = s3Properties.s3

    fun getPresignedUrlForPromise(promiseId: Long, fileExtension: ImageFileExtension): ImageUrlDto {
        val imageKey = UUID.randomUUID().toString()
        var fileName = getForPromiseFimeName(imageKey, promiseId, fileExtension)
        val generatePresignedUrlRequest =
            getGeneratePreSignedUrlRequest(s3Secret.bucket, fileName, fileExtension.uploadExtension)

        val generatePresignedUrl = amazonS3.generatePresignedUrl(generatePresignedUrlRequest)
        return ImageUrlDto(generatePresignedUrl.toString(), imageKey)
    }

    fun getPresignedUrlForUser(userId: Long, fileExtension: ImageFileExtension): ImageUrlDto {
        val imageKey = UUID.randomUUID().toString()
        var fileName = getForUserFimeName(imageKey, userId, fileExtension)
        val generatePresignedUrlRequest =
            getGeneratePreSignedUrlRequest(s3Secret.bucket, fileName, fileExtension.uploadExtension)

        val generatePresignedUrl = amazonS3.generatePresignedUrl(generatePresignedUrlRequest)
        return ImageUrlDto(generatePresignedUrl.toString(), imageKey)
    }

    fun deleteForPromise(promiseId: Long, imageKey: String, fileExtension: ImageFileExtension) {
        var fileName = getForPromiseFimeName(imageKey, promiseId, fileExtension)
        amazonS3.deleteObject(DeleteObjectRequest(s3Secret.bucket, fileName))
    }

    fun deleteForUser(userId: Long, imageKey: String, fileExtension: ImageFileExtension) {
        var fileName = getForUserFimeName(imageKey, userId, fileExtension)
        amazonS3.deleteObject(DeleteObjectRequest(s3Secret.bucket, fileName))
    }

    private fun getForPromiseFimeName(uuid: String, promiseId: Long, fileExtension: ImageFileExtension): String {
        return springEnvironmentHelper.activeProfile + "/promise/" + promiseId + "/" + uuid + "." + fileExtension.uploadExtension
    }

    private fun getForUserFimeName(uuid: String, userId: Long, fileExtension: ImageFileExtension): String {
        return springEnvironmentHelper.activeProfile + "/user/" + userId + "/" + uuid + "." + fileExtension.uploadExtension
    }

    private fun getGeneratePreSignedUrlRequest(
        bucket: String,
        fileName: String,
        fileExtension: String,
    ): GeneratePresignedUrlRequest {
        val generatePresignedUrlRequest = GeneratePresignedUrlRequest(bucket, fileName, HttpMethod.PUT)
            .withKey(fileName)
            .withContentType("image/$fileExtension")
            .withExpiration(getPreSignedUrlExpiration())
        generatePresignedUrlRequest.addRequestParameter(
            Headers.S3_CANNED_ACL,
            CannedAccessControlList.PublicRead.toString(),
        )
        return generatePresignedUrlRequest
    }

    private fun getPreSignedUrlExpiration(): Date {
        val expiration = Date()
        var expTimeMillis = expiration.time
        expTimeMillis += 1000 * 60 * 30
        expiration.time = expTimeMillis
        return expiration
    }
}
