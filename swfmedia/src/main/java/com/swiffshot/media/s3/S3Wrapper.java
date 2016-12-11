package com.swiffshot.media.s3;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class S3Wrapper
{

    @Autowired
    private AmazonS3Client amazonS3Client;

    // @Value("${cloud.aws.s3.bucket}")
    private String vidbucket = "swiffshotvids";

    private String picbucket = "swiffshotpics";

    @SuppressWarnings("unused")
    private PutObjectResult uploadVid(String filePath, String uploadKey)
	    throws FileNotFoundException
    {
	return uploadVid(new FileInputStream(filePath), uploadKey);
    }

    @SuppressWarnings("unused")
    private PutObjectResult uploadPic(String filePath, String uploadKey)
	    throws FileNotFoundException
    {
	return uploadPic(new FileInputStream(filePath), uploadKey);
    }

    private PutObjectResult uploadVid(InputStream inputStream, String uploadKey)
    {
	PutObjectRequest putObjectRequest = new PutObjectRequest(vidbucket,
		uploadKey, inputStream, new ObjectMetadata());

	putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);

	PutObjectResult putObjectResult = amazonS3Client
		.putObject(putObjectRequest);

	IOUtils.closeQuietly(inputStream);

	return putObjectResult;
    }

    private PutObjectResult uploadPic(InputStream inputStream, String uploadKey)
    {
	PutObjectRequest putObjectRequest = new PutObjectRequest(picbucket,
		uploadKey, inputStream, new ObjectMetadata());

	putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);

	PutObjectResult putObjectResult = amazonS3Client
		.putObject(putObjectRequest);

	IOUtils.closeQuietly(inputStream);

	return putObjectResult;
    }

    public List<PutObjectResult> uploadPic(MultipartFile[] multipartFiles)
    {
	List<PutObjectResult> putObjectResults = new ArrayList<>();

	Arrays.stream(multipartFiles)
		.filter(multipartFile -> !StringUtils.isEmpty(multipartFile
			.getOriginalFilename()))
		.forEach(
			multipartFile ->
			    {
				try
				{
				    putObjectResults.add(uploadPic( multipartFile.getInputStream(), multipartFile.getOriginalFilename()));
				} catch (IOException e)
				{
				    e.printStackTrace();
				}
			    });

	return putObjectResults;
    }

    public List<PutObjectResult> uploadVid(MultipartFile[] multipartFiles)
    {
	List<PutObjectResult> putObjectResults = new ArrayList<>();

	Arrays.stream(multipartFiles)
		.filter(multipartFile -> !StringUtils.isEmpty(multipartFile
			.getOriginalFilename()))
		.forEach(
			multipartFile ->
			    {
				try
				{
				    putObjectResults.add(uploadVid(multipartFile.getInputStream(), multipartFile.getOriginalFilename()));
				} catch (IOException e)
				{
				    e.printStackTrace();
				}
			    });

	return putObjectResults;
    }

    public ResponseEntity<byte[]> downloadPic(String key) throws IOException
    {
	GetObjectRequest getObjectRequest = new GetObjectRequest(picbucket, key);

	S3Object s3Object = amazonS3Client.getObject(getObjectRequest);

	S3ObjectInputStream objectInputStream = s3Object.getObjectContent();

	byte[] bytes = IOUtils.toByteArray(objectInputStream);

	String fileName = URLEncoder.encode(key, "UTF-8").replaceAll("\\+","%20");

	HttpHeaders httpHeaders = new HttpHeaders();
	httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	httpHeaders.setContentLength(bytes.length);
	httpHeaders.setContentDispositionFormData("attachment", fileName);

	return new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);
    }

    public ResponseEntity<byte[]> downloadVid(String key) throws IOException
    {
	GetObjectRequest getObjectRequest = new GetObjectRequest(vidbucket, key);

	S3Object s3Object = amazonS3Client.getObject(getObjectRequest);

	S3ObjectInputStream objectInputStream = s3Object.getObjectContent();

	byte[] bytes = IOUtils.toByteArray(objectInputStream);

	String fileName = URLEncoder.encode(key, "UTF-8").replaceAll("\\+","%20");

	HttpHeaders httpHeaders = new HttpHeaders();
	httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	httpHeaders.setContentLength(bytes.length);
	httpHeaders.setContentDispositionFormData("attachment", fileName);

	return new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);
    }

    public List<S3ObjectSummary> listVids()
    {
	ObjectListing objectListing = amazonS3Client.listObjects(new ListObjectsRequest().withBucketName(vidbucket));

	List<S3ObjectSummary> s3ObjectSummaries = objectListing.getObjectSummaries();

	return s3ObjectSummaries;
    }

    public List<S3ObjectSummary> listPics()
    {
	ObjectListing objectListing = amazonS3Client.listObjects(new ListObjectsRequest().withBucketName(picbucket));

	List<S3ObjectSummary> s3ObjectSummaries = objectListing.getObjectSummaries();

	return s3ObjectSummaries;
    }
}