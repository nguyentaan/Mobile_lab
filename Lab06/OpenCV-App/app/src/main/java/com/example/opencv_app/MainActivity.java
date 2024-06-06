package com.example.opencv_app;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.Manifest;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import org.opencv.android.CameraActivity;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.core.Core;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

public class MainActivity extends CameraActivity {

    CameraBridgeViewBase cameraBridgeViewBase;
    CascadeClassifier cascadeClassifier;
    Mat gray, rgb, transpose_gray, transpose_rgb;
    MatOfRect rects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getPermission();

        cameraBridgeViewBase = findViewById(R.id.cameraView);
        cameraBridgeViewBase.setVisibility(CameraBridgeViewBase.VISIBLE);
        cameraBridgeViewBase.setCvCameraViewListener(new CameraBridgeViewBase.CvCameraViewListener2() {
            @Override
            public void onCameraViewStarted(int width, int height) {
                rgb = new Mat();
                gray = new Mat();
                rects = new MatOfRect();
            }

            @Override
            public void onCameraViewStopped() {
                rgb.release();
                gray.release();
                rects.release();
            }

            @Override
            public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
                rgb = inputFrame.rgba();
                gray = inputFrame.gray();

                transpose_gray = gray.t();
                transpose_rgb = rgb.t();


                Core.transpose(gray, transpose_gray);
                Core.flip(transpose_gray, transpose_gray, 1);
                Core.transpose(rgb, transpose_rgb);
                Core.flip(transpose_rgb, transpose_rgb, 1);

                cascadeClassifier.detectMultiScale(transpose_gray, rects, 1.1, 2);
                for(Rect rect: rects.toList()){
                    Mat submat = transpose_rgb.submat(rect);
                    Imgproc.blur(submat, submat, new Size(20, 20));
                    Imgproc.rectangle(transpose_rgb, rect, new Scalar(0, 255, 0), 20);


                }

                Core.transpose(transpose_rgb, rgb);
                Core.flip(rgb, rgb, 1);
                return transpose_rgb.t();

            }
        });

        if (OpenCVLoader.initDebug()) {
            cameraBridgeViewBase.enableView();


            try {
                InputStream inputStream = getResources().openRawResource(R.raw.lbpcascade_frontalface);
                File file = new File(getDir("cascade", MODE_PRIVATE), "lbpcascade_frontalface");
                FileOutputStream fileOutputStream = new FileOutputStream(file);


                byte[] data = new byte[4096];
                int read_bytes;

                while((read_bytes = inputStream.read(data)) != -1){
                    fileOutputStream.write(data, 0, read_bytes);
                }

                cascadeClassifier = new CascadeClassifier(file.getAbsolutePath());
                if(cascadeClassifier.empty()) cascadeClassifier = null;

                inputStream.close();
                fileOutputStream.close();
                file.delete();

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }



    @Override
    protected List<? extends CameraBridgeViewBase> getCameraViewList() {
        return Collections.singletonList(cameraBridgeViewBase);
    }

    void getPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 101);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 101 && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                cameraBridgeViewBase.enableView();
            } else {
                // Permission denied, handle appropriately
                getPermission();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (cameraBridgeViewBase != null) {
            cameraBridgeViewBase.disableView();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (OpenCVLoader.initDebug()) {
            cameraBridgeViewBase.enableView();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cameraBridgeViewBase != null) {
            cameraBridgeViewBase.disableView();
        }
    }
}
