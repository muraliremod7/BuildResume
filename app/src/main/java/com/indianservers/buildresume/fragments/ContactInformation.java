package com.indianservers.buildresume.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Toast;

import com.indianservers.buildresume.R;
import com.github.gcacace.signaturepad.views.SignaturePad;
import com.indianservers.buildresume.ScrollableTabsActivity;
import com.indianservers.buildresume.Utility;
import com.indianservers.buildresume.model.SaveDataModel;
import com.indianservers.buildresume.realm.RealmController;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Calendar;

import io.realm.Realm;


public class ContactInformation extends Fragment implements View.OnClickListener{
    public static TextInputLayout profile,name,email,mobile,address,dateofbirth,maritialstatus,city,state,country,pincode,gender;
    public static ImageView uploadphoto,uploadsign;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1, SELECT_FILE1=2;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private Button mClearButton;
    private Button mSaveButton;
    private Realm realm;
    private SignaturePad mSignaturePad;

    private String userChoosenTask;

    public ContactInformation() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View itemView = inflater.inflate(R.layout.fragment_contact_information, container, false);
        profile = (TextInputLayout)itemView.findViewById(R.id.profile);
        name = (TextInputLayout)itemView.findViewById(R.id.username);
        email = (TextInputLayout)itemView.findViewById(R.id.email);
        mobile = (TextInputLayout)itemView.findViewById(R.id.mobile);
        address = (TextInputLayout)itemView.findViewById(R.id.address);
        dateofbirth = (TextInputLayout)itemView.findViewById(R.id.dateofbirth);
        maritialstatus = (TextInputLayout)itemView.findViewById(R.id.martialstatus);
        city = (TextInputLayout)itemView.findViewById(R.id.city);
        state = (TextInputLayout)itemView.findViewById(R.id.state);
        country = (TextInputLayout)itemView.findViewById(R.id.country);
        pincode = (TextInputLayout)itemView.findViewById(R.id.pincode);
        gender = (TextInputLayout)itemView.findViewById(R.id.gender);
        uploadphoto = (ImageView)itemView.findViewById(R.id.uploadPhoto);
        uploadphoto.setOnClickListener(this);


        uploadsign = (ImageView)itemView.findViewById(R.id.uploadSign);
        uploadsign.setOnClickListener(this);


        this.realm = RealmController.with(this).getRealm();
        String itemId = ScrollableTabsActivity.itemid;

        if(itemId == null){
            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
            String previouslyEncodedImage = sharedPrefs.getString("imagePreferance", "");
            if(previouslyEncodedImage==""){
            }else{
                uploadphoto.setImageBitmap(decodeBase64(previouslyEncodedImage));
            }
            String previouslyEncodedImag = sharedPrefs.getString("imagePreferanc", "");
            if(previouslyEncodedImag==""){
            }else{
                uploadsign.setImageBitmap(decodeBase64(previouslyEncodedImag));
            }
        }else{
            RealmController controller = new RealmController(getActivity().getApplication());
            SaveDataModel saveDataModels = realm.where(SaveDataModel.class).equalTo("id", Integer.parseInt(itemId)).findFirst();
            try{
                profile.getEditText().setText(saveDataModels.getProfilename());
                name.getEditText().setText(saveDataModels.getName());
                email.getEditText().setText(saveDataModels.getEmail());
                mobile.getEditText().setText(saveDataModels.getMobile());
                address.getEditText().setText(saveDataModels.getAddress());
                dateofbirth.getEditText().setText(saveDataModels.getDateofbirth());
                maritialstatus.getEditText().setText(saveDataModels.getMarriagestatus());
                city.getEditText().setText(saveDataModels.getCity());
                state.getEditText().setText(saveDataModels.getState());
                country.getEditText().setText(saveDataModels.getCountry());
                pincode.getEditText().setText(saveDataModels.getPincode());
                gender.getEditText().setText(saveDataModels.getGender());

                ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(saveDataModels.getPersonpic());
                Bitmap bitmap = BitmapFactory.decodeStream(arrayInputStream);
                uploadphoto.setImageBitmap(bitmap);

                ByteArrayInputStream arrayInputStreamm = new ByteArrayInputStream(saveDataModels.getSignaturepic());
                Bitmap bitmapp = BitmapFactory.decodeStream(arrayInputStreamm);
                uploadsign.setImageBitmap(bitmapp);

            }catch (NullPointerException e){

            }


        }
        dateofbirth.getEditText().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(MotionEvent.ACTION_UP == event.getAction()){
                    showDatePicker();
                }
                return false;
            }
        });
        gender.getEditText().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(MotionEvent.ACTION_UP == event.getAction()){
                    final CharSequence[] genderr = {"Male","Female"};
                    final AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                    alert.setTitle("Select Gender");
                    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
                    alert.setSingleChoiceItems(genderr,-1, new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            if(genderr[which]=="Male")
                            {
                                gender.getEditText().setText("Male");
                            }
                            else if (genderr[which]=="Female")
                            {
                                gender.getEditText().setText("Female");
                            }
                        }
                    });
                    alert.show();
                }
                return false;
            }
        });
        maritialstatus.getEditText().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(MotionEvent.ACTION_UP == event.getAction()){
                    final CharSequence[] genderr = {"Married","UnMarried"};
                    final AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                    alert.setTitle("Select Maritial Status");
                    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
                    alert.setSingleChoiceItems(genderr,-1, new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            if(genderr[which]=="Married")
                            {
                                maritialstatus.getEditText().setText("Married");
                            }
                            else if (genderr[which]=="UnMarried")
                            {
                                maritialstatus.getEditText().setText("UnMarried");
                            }
                        }
                    });
                    alert.show();
                }
                return false;
            }
        });
        return itemView;
    }
    private void showDatePicker() {
        DatepickerFragment date = new DatepickerFragment();
        /**
         * Set Up Current Date Into dialog
         */
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);
        /**
         * Set Call back to capture selected date
         */
        date.setCallBack(ondate);
        date.show(getFragmentManager(), "Date Picker");
    }

    DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            dateofbirth.getEditText().setText(String.valueOf(dayOfMonth) + "-" + String.valueOf(monthOfYear+1)
                    + "-" + String.valueOf(year));
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.uploadPhoto:
                selectImage();
                break;
            case R.id.uploadSign:
                verifyStoragePermissions(getActivity());
                selectSignature();
                break;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if(userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }


    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result= Utility.checkPermission(getContext());

                if (items[item].equals("Take Photo")) {
                    userChoosenTask ="Take Photo";
                    if(result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask ="Choose from Library";
                    if(result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }
    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE){
                onSelectFromGalleryResult(data);
            }else if(requestCode==SELECT_FILE1){
                onSelectFromGalleryResult1(data);
            }

            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        uploadphoto.setImageBitmap(thumbnail);
    }

    private void selectSignature() {
        final CharSequence[] items = { "Take Signature", "Choose from Library",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Add Signature!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result= Utility.checkPermission(getContext());

                if (items[item].equals("Take Signature")) {
                    userChoosenTask ="Take Signature";
                    if(result){
                        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        LayoutInflater inflater = getActivity().getLayoutInflater();
                        View dialogView = inflater.inflate(R.layout.signature_layout, null);
                        mClearButton = (Button) dialogView.findViewById(R.id.clear_button);
                        mSaveButton = (Button) dialogView.findViewById(R.id.save_button);

                        //signature ................
                        mSignaturePad = (SignaturePad) dialogView.findViewById(R.id.signature_pad);
                        mSignaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
                            @Override
                            public void onStartSigning() {
                            }

                            @Override
                            public void onSigned() {
                                mSaveButton.setEnabled(true);
                                mClearButton.setEnabled(true);

                            }

                            @Override
                            public void onClear() {
                                mSaveButton.setEnabled(false);
                                mClearButton.setEnabled(false);

                            }
                        });


                        mClearButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mSignaturePad.clear();
                            }
                        });

                        mSaveButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Bitmap signatureBitmap = mSignaturePad.getSignatureBitmap();
                                if (addJpgSignatureToGallery(signatureBitmap)) {
                                    Toast.makeText(getContext(), "Signature saved into the Gallery", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getContext(), "Unable to store the signature", Toast.LENGTH_SHORT).show();
                                }
                                if (addSvgSignatureToGallery(mSignaturePad.getSignatureSvg())) {
                                    Toast.makeText(getContext(), "SVG Signature saved into the Gallery", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getContext(), "Unable to store the SVG signature", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        builder.setView(dialogView);
                        builder.setTitle("Draw Signature!");

                        builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                        builder.show();
                    }

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask ="Choose from Library";
                    if(result)
                        galleryIntent1();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }



    private void galleryIntent1()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE1);
    }
    public File getAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e("SignaturePad", "Directory not created");
        }
        return file;
    }

    public void saveBitmapToJPG(Bitmap bitmap, File photo) throws IOException {
        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bitmap, 0, 0, null);
        OutputStream stream = new FileOutputStream(photo);
        newBitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        uploadsign.setImageBitmap(newBitmap);
        stream.close();
    }

    public boolean addJpgSignatureToGallery(Bitmap signature) {
        boolean result = false;
        try {
            File photo = new File(getAlbumStorageDir("SignaturePad"), String.format("Signature_%d.jpg", System.currentTimeMillis()));
            saveBitmapToJPG(signature, photo);
            scanMediaFile(photo);
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void scanMediaFile(File photo) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(photo);
        mediaScanIntent.setData(contentUri);
        getContext().sendBroadcast(mediaScanIntent);
    }

    public boolean addSvgSignatureToGallery(String signatureSvg) {
        boolean result = false;
        try {
            File svgFile = new File(getAlbumStorageDir("SignaturePad"), String.format("Signature_%d.svg", System.currentTimeMillis()));
            OutputStream stream = new FileOutputStream(svgFile);
            OutputStreamWriter writer = new OutputStreamWriter(stream);
            writer.write(signatureSvg);
            writer.close();
            stream.flush();
            stream.close();
            scanMediaFile(svgFile);
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Checks if the app has permission to write to device storage
     * <p/>
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity the activity from which permissions are checked
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }


    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString("imagePreferance", encodeTobase64(bm));
        editor.commit();
        String previouslyEncodedImage = sharedPrefs.getString("imagePreferance", "");
        Bitmap bitmap = getResizedBitmap(decodeBase64(previouslyEncodedImage), 500);
        uploadphoto.setImageBitmap(bitmap);
    }
    /**
     * reduces the size of the image
     * @param image
     * @param maxSize
     * @return
     */
    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }
    public static String encodeTobase64(Bitmap image) {
        Bitmap immage = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immage.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        Log.d("Image Log:", imageEncoded);
        return imageEncoded;
    }
    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory
                .decodeByteArray(decodedByte, 0, decodedByte.length);
}

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult1(Intent data) {

        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString("imagePreferanc", encodeTobase64(bm));
        editor.commit();
        String previouslyEncodedImage = sharedPrefs.getString("imagePreferanc", "");
        uploadsign.setImageBitmap(decodeBase64(previouslyEncodedImage));
    }

}
