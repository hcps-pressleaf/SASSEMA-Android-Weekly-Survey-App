package com.example.surveyapp1;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

//methods in this class are static to avoid having a context instance variable which will cause data leaks
public class PostDatam extends AsyncTask<File, Void, Void> {
    private static final String TAG = "TestingTesting123";
    private static final String upLoadServerUri = "https://still.richmond.edu/fred_ios3.php"; //this is currently he test url
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8"); //for sending json type files to server

    private OkHttpClient client; //okhttp simplifies process of sending data so that you don't have to do it bitwise

    public PostDatam(Context context) { //called by wifi receiver to upload
        try {
            this.client = RestAdapter.createOkHttpClient(context); //this is necessary for https connections
        } catch (Exception e) {
            Log.e(TAG, "Error initializing OkHttpClient: " + e.getMessage(), e);
        }
    }

    @Override
    protected Void doInBackground(File... files) { //deprecated but still required by class...
        Log.d(TAG, "Doing in background");
        for (File file : files) {
            if (file.exists()) {
                Log.d(TAG, "File exists: " + file.getAbsolutePath());
                sendJsonToServer(file);
            } else {
                Log.e(TAG, "File not found: " + file.getAbsolutePath());
            }
        }
        return null;
    }

    public void sendJsonToServer(File file) { //method that sends an individual file to the server and deletes it if successful
        try {
            FileInputStream fis = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            //these are used to parse data from json from the user's storage
            StringBuilder jsonContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) { //while there is a line to read...
                jsonContent.append(line);
            }
            fis.close();
            String json = jsonContent.toString(); //the json file now in a string

            RequestBody body = new MultipartBody.Builder() //must be multipart because that is what the server expects
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file", file.getName(),
                            RequestBody.create(json, JSON))
                    .build();

            Request request = new Request.Builder() //builds a request to send data to server
                    .url(upLoadServerUri)
                    .post(body)
                    .build();

            client.newCall(request).enqueue(new okhttp3.Callback() { //async process managed by okhttp
                @Override
                public void onFailure(okhttp3.Call call, IOException e) {
                    Log.d(TAG, "OkHttp Request Failed: " + e.getMessage());
                }

                @Override
                public void onResponse(okhttp3.Call call, Response response) throws IOException {
                    if (response.isSuccessful()) { //if file is successfully sent, it will be deleted
                        Log.d(TAG, "Response from server: " + response.body().string());
                        boolean deleted = file.delete();
                        if (deleted) {
                            Log.d(TAG, "File successfully deleted: " + file.getAbsolutePath());
                        } else {
                            Log.d(TAG, "File not deleted: " + file.getAbsolutePath());
                        }
                    } else {
                        Log.d(TAG, "Server Error: " + response.code());
                    }
                }
            });

        } catch (IOException e) {
            Log.e(TAG, "Error reading file: " + e.getMessage());
        }
    }

    public static void uploadAllFilesInFolder(File directory, Context context) { //method to batch upload anything in the toBeUploaded directory
        if (!directory.isDirectory()) {
            Log.d(TAG, "The provided file is not a directory: " + directory);
            return;
        }

        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) { //for each file, call upload method
                    new PostDatam(context).sendJsonToServer(file);
                }
            }
        }
    }

    public static void saveJsonToFile(Context context, Object dataObject) { //when survey is completed, this method saves the answers to the phone, this way they will be safe if there is not a wifi connection to upload them on
        File directory = new File(context.getExternalFilesDir(null), "SASSEMA/toBeUploaded");
        Gson gson = new Gson(); //gson will serialize java stuff to a json object
        String json = gson.toJson(dataObject);
        Log.d("TestingTesting123", "here's the json: " + json);

        if (!directory.exists()) {
            if (!directory.mkdirs()) { //ensures directory is available
                Log.e(TAG, "Failed to create directory: " + directory.getAbsolutePath());
            }
        }

        String filename = getFromUserInfo(context, "deviceID") + "_weekly_" + System.currentTimeMillis() + ".log"; //file name example hkjd5wbqqk6wbdkq_weekly_1700560073922.log
        File file = new File(directory, filename); //created file in directory

        try (FileOutputStream fos = new FileOutputStream(file);
             OutputStreamWriter osw = new OutputStreamWriter(fos)) {

            osw.write(json); //tries to write json to directory
            osw.flush();
            Log.d(TAG, "JSON file saved to: " + file.getAbsolutePath());

        } catch (IOException e) {
            Log.e(TAG, "Error writing JSON to file: " + e.getMessage(), e);
        }

        WifiReceiver wifiReceiver = new WifiReceiver();
        wifiReceiver.triggerWifiReceiver(context); //triggers wifi check in an attempt to upload newly created file
    }

    public static String getFromUserInfo(Context context, String value) { //retrieves info from userinfo file such as device id and user id
        File userInfo = new File(new File(context.getExternalFilesDir(null), "SASSEMA"), "USER_INFO");
        if (userInfo.exists()) {
            StringBuilder jsonContent = new StringBuilder();
            try (FileInputStream fis = new FileInputStream(userInfo); //tries to retrieve data from file
                 BufferedReader reader = new BufferedReader(new InputStreamReader(fis))) {
                String line;
                while ((line = reader.readLine()) != null) { //while there is a line to read...
                    jsonContent.append(line);
                }
                JSONObject jsonObject = new JSONObject(jsonContent.toString());
                return jsonObject.optString(value, null); //gets key for value parameter and returns it
            } catch (IOException | JSONException e) {
                Log.e(TAG, "Error reading user info file: " + e.getMessage(), e);
            }
        } else {
            Log.d(TAG, "User info file does not exist");
        }
        return null;
    }
}