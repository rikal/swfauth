package com.swiffshot.media.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.swiffshot.media.s3.S3Wrapper;

@RestController
public class MediaService
{
    @Autowired
    private S3Wrapper s3Wrapper;

    @RequestMapping(value = "/uploadPic", method = RequestMethod.POST)
    public List<PutObjectResult> uploadPic( @RequestParam("file") MultipartFile[] multipartFiles)
    {
	return s3Wrapper.uploadPic(multipartFiles);
    }
    
    @RequestMapping(value = "/uploadVid", method = RequestMethod.POST)
    public List<PutObjectResult> uploadVid( @RequestParam("file") MultipartFile[] multipartFiles)
    {
	return s3Wrapper.uploadVid(multipartFiles);
    }

    @RequestMapping(value = "/{userId}/downloadPic", method = RequestMethod.GET)
    public ResponseEntity<byte[]> downloadPic(@PathVariable String userId) throws IOException
    {
	return s3Wrapper.downloadPic(userId);
    }
    
    @RequestMapping(value = "/downloadVid", method = RequestMethod.GET)
    public ResponseEntity<byte[]> downloadVid(@RequestParam String key) throws IOException
    {
	return s3Wrapper.downloadVid(key);
    }

    @RequestMapping(value = "/listPics", method = RequestMethod.GET)
    public List<S3ObjectSummary> listPics() throws IOException
    {
	return s3Wrapper.listPics();
    }
    
    @RequestMapping(value = "/listVids", method = RequestMethod.GET)
    public List<S3ObjectSummary> listVids() throws IOException
    {
	return s3Wrapper.listVids();
    }
}
