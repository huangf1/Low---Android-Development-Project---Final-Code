package edu.rosehulman.huangf1.low;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by thompsar on 1/24/2017.
 */
public class GetImageTask extends AsyncTask<String, Void, Bitmap> {

    private ImageView imageView;

    public GetImageTask(ImageView image) {
        imageView = image;
    }

    @Override
    protected Bitmap doInBackground(String... urlStrings) {
        String urlString = urlStrings[0];
        InputStream in = null;
        Bitmap bitmap = null;
        try {
            in = new URL(urlString).openStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        bitmap = BitmapFactory.decodeStream(in);
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        imageView.setImageBitmap(bitmap);
    }
}
