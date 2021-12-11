package com.example.auto_sign;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;

public class DownloadObject {
    public static void downloadObject(
            String projectId, String bucketName, String objectName, String destFilePath) throws IOException {

        // Change the directory of the credentials to get access to the Database online
        Credentials credentials = GoogleCredentials
                .fromStream(new FileInputStream("/Users/francescocenciarelli/Desktop/University/Year3/Programming3 /projc/Auto_Sign/src/main/resources/ServiceKey_GoogleCloud copy.json"));

        // This is to set the name of the project online
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials)
                .setProjectId("autosign-334513").build().getService();
        // The ID of your GCP project
        // String projectId = "your-project-id";

        // The ID of your GCS bucket
        // String bucketName = "your-unique-bucket-name";

        // The ID of your GCS object
        // String objectName = "your-object-name";

        // The path to which the file should be downloaded
        // String destFilePath = "/local/path/to/file.txt";

        // THIS IS TO DOWNLOAD THE FILE FROM THE CLOUD
        Blob blob = storage.get(BlobId.of(bucketName, objectName));
        blob.downloadTo(Paths.get(destFilePath));

        System.out.println(
                "Downloaded object "
                        + objectName
                        + " from bucket name "
                        + bucketName
                        + " to "
                        + destFilePath);
    }
}
