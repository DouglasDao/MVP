package com.mvp.app.common.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.Time;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.library.ExceptionTracker;
import com.library.Log;
import com.mvp.app.R;
import com.mvp.app.common.ProjectApplication;
import com.mvp.app.view.activity.BaseActivity;
import com.mvp.app.view.activity.SplashActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import okhttp3.MediaType;
import okhttp3.RequestBody;


public class CodeSnippet {

    //Time AM or PM
    private String PM = "PM";
    private String AM = "AM";
    private String TAG = getClass().getSimpleName();
    private Context mContext;

    public CodeSnippet() {
    }

    public CodeSnippet(Context mContext) {
        this.mContext = mContext;
    }


    /*public ByteArrayBody getCompressedImage(String path) {

        Bitmap imageBitmap = getBitmap(path);

        if (imageBitmap != null) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ByteArrayBody bab;
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            byte[] data = bos.toByteArray();
            bab = new ByteArrayBody(data, "" + System.currentTimeMillis() + "displayPicture.jpg");
            return bab;
        }
        return null;
    }

    public ByteArrayBody getCompressedImage(File file) {
        Bitmap imageBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        if (imageBitmap != null) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ByteArrayBody bab;
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            byte[] data = bos.toByteArray();
            bab = new ByteArrayBody(data, "" + System.currentTimeMillis() + " displayPicture.jpg");
            return bab;
        }
        return null;
    }*/

    public boolean hasNetworkConnection() {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) { // connected to the internet
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) { // connected to wifi
                if (activeNetwork.isConnectedOrConnecting()) {
                    return true;
                }
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) { // connected to the mobile provider's data plan
                if (activeNetwork.isConnectedOrConnecting()) {
                    return true;
                }
            }
        }
        return false;
    }

    public void showNetworkMessage() {
        if (mContext != null) {
            if (((BaseActivity) mContext).mParentView != null) {
                Snackbar snackbar = Snackbar.make(((BaseActivity) mContext).mParentView, "No Network found!", Snackbar.LENGTH_LONG);
                snackbar.setActionTextColor(Color.RED);
                snackbar.setAction("Settings", view -> showNetworkSettings());
                snackbar.show();
            }
        }
    }

    public File getFileFromBitmap(Context mContext, Bitmap bitmap) throws IOException {
        File f = new File(mContext.getCacheDir(), "" + System.currentTimeMillis() + "_img.png");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap = compressBitmap(bitmap, 300);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
        byte[] bitmapdata = bos.toByteArray();
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(bitmapdata);
        fos.flush();
        fos.close();
        return f;
    }

    public Bitmap compressBitmap(Bitmap mBitmap, int size) {
        int maxSize = size;
        int width = mBitmap.getWidth();
        int height = mBitmap.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 0) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        mBitmap = Bitmap.createScaledBitmap(mBitmap, width, height, true);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        mBitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
        return mBitmap;
    }

    public RequestBody requestBodyStringConversion(String details) {
        return RequestBody.create(MediaType.parse("text/plain"), details);
    }

    public RequestBody requestBodyConversion(File file) {
        return RequestBody.create(MediaType.parse("image/*"), file);
    }


    /**
     * Check which type of connection the device is connected to
     */
    public String whichNetworkConnection() {

        ConnectivityManager cm = ProjectApplication.getInstance().getConnectivityManager();

        NetworkInfo wifiNetwork = cm.getActiveNetworkInfo();
        if (wifiNetwork != null && wifiNetwork.isConnectedOrConnecting()) {
            return "Wi-Fi";
        }

        NetworkInfo mobileNetwork = cm.getActiveNetworkInfo();
        if (mobileNetwork != null && mobileNetwork.isConnectedOrConnecting()) {
            return "Mobile";
        }

        return null;
    }

    public String getNetworkType() {
        ConnectivityManager cm = ProjectApplication.getInstance().getConnectivityManager();
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork && activeNetwork.isConnectedOrConnecting()) {
            return activeNetwork.getTypeName();
        }
        return null;
    }

    public boolean isTodayLieInBetween(String str1, String str2) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            String todayStr = formatter.format(Calendar.getInstance().getTime());
            Date todayDate = formatter.parse(todayStr);
            Date date1 = formatter.parse(str1);
            Date date2 = formatter.parse(str2);

            return date1.compareTo(todayDate) <= 0 && date2.compareTo(todayDate) >= 0;
        } catch (Exception e) {
            ExceptionTracker.track(e);
        }
        return false;
    }

    public Calendar getCalendarTime(String time) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(formatter.parse(time));
            return calendar;
        } catch (Exception e) {
            ExceptionTracker.track(e);
        }
        return null;
    }

    public String getCalendarTime(Calendar time) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
            return formatter.format(time);
        } catch (Exception e) {
            ExceptionTracker.track(e);
        }
        return null;
    }

    public Calendar getCalendarForYear(String time) {
        try {

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy", Locale.getDefault());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(formatter.parse(time));
            return calendar;
        } catch (Exception e) {
            ExceptionTracker.track(e);
        }
        return null;
    }

    public Calendar getCalendarToStandard(String time) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(formatter.parse(time));
            return calendar;
        } catch (Exception e) {
            ExceptionTracker.track(e);
        }
        return null;
    }

    public void onLogout(Activity activity) {
        SharedPref.getInstance().clearSharedPreferences(mContext);
        Intent intent = new Intent(activity, SplashActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(intent);
        activity.finish();
        activity.overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
    }

    public Calendar getCalendarWithTimeOnly(String time) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("hh:mm aa", Locale.getDefault());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(formatter.parse(time));
            return calendar;
        } catch (Exception e) {
            ExceptionTracker.track(e);
            SimpleDateFormat formatter = new SimpleDateFormat("hh : mm aa", Locale.getDefault());
            Calendar calendar = Calendar.getInstance();
            try {
                calendar.setTime(formatter.parse(time));
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
            return calendar;
        }
    }

    public String getCalendarUTCFormat(String time) {
        try {
            SimpleDateFormat customFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault());
            return new SimpleDateFormat("dd MMM yyyy, HH:mm aaa", Locale.getDefault()).format(customFormat.parse(time));
        } catch (Exception e) {
            ExceptionTracker.track(e);
        }
        return null;
    }

    public String getMMMDDYYYY(String dateStr) {
        String localFormatStr = null;
        SimpleDateFormat UTCFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
        SimpleDateFormat subLocal = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
        subLocal.setTimeZone(TimeZone.getTimeZone("GMT"));
        try {
            Log.d("SubLocalTime", "" + subLocal.format(UTCFormat.parse(dateStr)));
            localFormatStr = subLocal.format(UTCFormat.parse(dateStr));
        } catch (Exception e) {
            ExceptionTracker.track(e);
        }
        return localFormatStr;
    }

    public String getDayOfMonthMonthAndYear(Calendar calendar) {
        //TODO mention
        String dateString = "";
        dateString = calendar.get(Calendar.DAY_OF_MONTH) + " " + monthNameFromInt(calendar.get(Calendar.MONTH)) + ", " + calendar.get(Calendar.YEAR);
        return dateString;
    }

    public String getDayOfMonthMonthAndYearStd(Calendar calendar) {
        String dateString = "";
        int month = calendar.get(Calendar.MONTH) + 1;
        DecimalFormat formatter = new DecimalFormat("00");
        dateString = formatter.format(calendar.get(Calendar.DAY_OF_MONTH)) + "-" + formatter.format(month) + "-" + calendar.get(Calendar.YEAR);
        return dateString;
    }

    private String monthNameFromInt(int monthInt) {
        String month = "";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (monthInt >= 0 && monthInt <= 11) {
            month = months[monthInt];
        }
        return month.substring(0, 4);
    }

    public String getPastTimerString(Calendar calendar) {
        long time = System.currentTimeMillis() - calendar.getTimeInMillis();
        long mins = time / 60000;
        if (mins > 59L) {
            long hours = mins / 60;
            if (hours > 24) {
                long days = hours / 24;
                if (days > 1) {
                    return days + " days";
                } else {
                    return days + " day";
                }
            } else {
                return hours + " hours ago";
            }
        } else {
            return "less than a minute";
        }
    }

    public Date getTimeFromMilisecond(long timeStamp) {
        return new Date((long) timeStamp * 1000);
    }

    public String getOrdinaryTime(Time time) {
        if (time.hour > 12) {
            return formatTime(time.hour - 12) + ":" + formatTime(time.minute)
                    + " " + getAMorPM(time);
        }

        return (time.hour == 0 ? String.valueOf(12) : formatTime(time.hour)) + ":"
                + formatTime(time.minute) + " " + getAMorPM(time);
    }

    public String getOrdinaryDate(Calendar calendar) {

        int month = calendar.get(Calendar.MONTH) + 1;
        DecimalFormat formatter = new DecimalFormat("00");
        Log.d(TAG, "getOrdinaryDate : " + formatter.format(month));
        return formatter.format(calendar.get(Calendar.DAY_OF_MONTH)) + "/" + formatter.format(month) + "/" + calendar.get(Calendar.YEAR);
    }

    public String getOrdinaryTime(Calendar calendar) {

        int min = calendar.get(Calendar.MINUTE);
        //Log.d(TAG,"hours :"+calendar.get(Calendar.HOUR_OF_DAY));
        String meridian = "AM";
        if (calendar.get(Calendar.HOUR_OF_DAY) > 11) {
            meridian = "PM";
        }
        DecimalFormat formatter = new DecimalFormat("00");
        return formatter.format(calendar.get(Calendar.HOUR)) + ":" + formatter.format(min) + " " + meridian;
    }

    public String getOrdinaryDateWithFipe(Time date) {

        return date.monthDay + " | " + date.month + " | " + date.year;
    }

    private String formatTime(int time) {
        if (String.valueOf(time).length() < 2)
            return "0" + time;
        else
            return String.valueOf(time);
    }

    private String getAMorPM(Time time) {
        if (time.hour > 11) {
            return PM;
        } else
            return AM;
    }


    private void showGooglePlayDialog(final Context context, final OnGooglePlayServiceListener googlePlayServiceListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Get Google Play Service");
        builder.setMessage("This app won't run without Google Play Services, which are missing from your phone");
        builder.setPositiveButton("Get Google Play Service",
                (dialog, which) -> {
                    googlePlayServiceListener.onInstallingService();
                    context.startActivity(new Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://play.google.com/store/apps/details?"
                                    + "id=com.google.android.gms")));
                    dialog.dismiss();
                });
        builder.setNegativeButton("Cancel", (dialog, which) -> googlePlayServiceListener.onCancelServiceInstallation());
        builder.setCancelable(false);
        AlertDialog alert = builder.create();
        alert.show();
    }

    private Intent getSettingsIntent(String settings) {
        return new Intent(settings);
    }

    private void startActivityBySettings(Context context, String settings) {
        context.startActivity(getSettingsIntent(settings));
    }

    private void startActivityBySettings(Context context, Intent intent) {
        context.startActivity(intent);
    }

    public void showGpsSettings(Context context) {
        startActivityBySettings(context, Settings.ACTION_LOCATION_SOURCE_SETTINGS);
    }

    public void showNetworkSettings() {
        Intent chooserIntent = Intent.createChooser(getSettingsIntent(Settings.ACTION_DATA_ROAMING_SETTINGS),
                "Complete action using");
        List<Intent> networkIntents = new ArrayList<>();
        networkIntents.add(getSettingsIntent(Settings.ACTION_WIFI_SETTINGS));
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, networkIntents.toArray(new Parcelable[]{}));
        startActivityBySettings(mContext, chooserIntent);
    }

    public boolean isSpecifiedDelay(long exisingTime, long specifiedDelay) {
        return specifiedDelay >= (Calendar.getInstance().getTimeInMillis() - exisingTime);
    }

    public void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (null != activity.getCurrentFocus())
            imm.hideSoftInputFromWindow(activity.getCurrentFocus()
                    .getApplicationWindowToken(), 0);
    }

    public void showKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (null != activity.getCurrentFocus())
            imm.showSoftInputFromInputMethod(activity.getCurrentFocus()
                    .getApplicationWindowToken(), 0);
    }

    public boolean isNull(Object object) {
        return null == object || object.toString().compareTo("null") == 0;
    }

    public final boolean isValidEmail(CharSequence target) {
        return target != null && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public boolean isValidMobile(String phone) {
        return !(phone == null || phone.length() < 6 || phone.length() > 13) && android.util.Patterns.PHONE.matcher(phone).matches();
    }

    public boolean isAboveMarshmallow() {
        int currentapiVersion = Build.VERSION.SDK_INT;
        // Do something for marshmallow and above versions
// do something for phones running an SDK before marshmallow
        return currentapiVersion >= Build.VERSION_CODES.M;
    }

    public boolean isAboveLollipop() {
        int currentapiVersion = Build.VERSION.SDK_INT;
        // Do something for marshmallow and above versions
// do something for phones running an SDK before marshmallow
        return currentapiVersion >= Build.VERSION_CODES.LOLLIPOP;
    }

    /**
     * Fetch the drawable object for the given resource id.
     *
     * @param resourceId to which the value is to be fetched.
     * @return drawable object for the given resource id.
     */

    public Drawable getDrawable(int resourceId) {
        return ResourcesCompat.getDrawable(mContext.getResources(), resourceId, null);
    }

    /**
     * Returns the current date.
     *
     * @return Current date
     */

    public Date getCurrentDate() {
        return new Date(System.currentTimeMillis());
    }

    public String getCurrentDateInFormat(String format) {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String todayDate = formatter.format(date);
        return todayDate;
    }

    public String changeDateFormat(String currentFormat, String requiredFormat, String dateString) {
        String result = "";
        if (dateString == null || dateString.isEmpty()) {
            return result;
        }
        SimpleDateFormat formatterOld = new SimpleDateFormat(currentFormat, Locale.getDefault());
        SimpleDateFormat formatterNew = new SimpleDateFormat(requiredFormat, Locale.getDefault());
        Date date = null;
        try {
            date = formatterOld.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date != null) {
            result = formatterNew.format(date);
        }
        return result;
    }


    public boolean checkDates(String startDate, String endDate, String dateFormat) {
        SimpleDateFormat dfDate = new SimpleDateFormat(dateFormat);
        boolean b = false;
        try {
            if (dfDate.parse(startDate).before(dfDate.parse(endDate))) {
                b = true;//If start date is before end date
            } else if (dfDate.parse(startDate).equals(dfDate.parse(endDate))) {
                b = true;//If two dates are equal
            } else {
                b = false; //If start date is after the end date
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return b;
    }


    public boolean isExpired(String valid_date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date strDate = null;
        try {
            strDate = sdf.parse(valid_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (new Date().after(strDate)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Fetch the string value from a xml file returns the value.
     *
     * @param resId to which the value has to be fetched.
     * @return String value of the given resource id.
     */

    public String getString(int resId) {
        return mContext.getResources().getString(resId);
    }

    /**
     * Fetch the color value from a xml file returns the value.
     *
     * @param colorId to which the value has to be fetched.
     * @return Integer value of the given resource id.
     */

    public int getColor(int colorId) {
        return ContextCompat.getColor(mContext, colorId);
    }


    public RecyclerView.ItemAnimator setItemAnimator() {
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setChangeDuration(1000);
        itemAnimator.setRemoveDuration(200);
        return itemAnimator;
    }

    public boolean isEmpty(String text) {
        return TextUtils.isEmpty(text);
    }

    public void loadImage(String url, ImageView target) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                //.placeholder(R.drawable.placeholder)
                .priority(Priority.HIGH);

        Glide.with(ProjectApplication.getInstance())
                .load(url)
                .apply(options)
                .into(target);
    }

    public void loadImage(Uri uri, ImageView target) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                //.placeholder(R.drawable.placeholder)
                .priority(Priority.HIGH);

        Glide.with(ProjectApplication.getInstance())
                .load(uri)
                .apply(options)
                .into(target);
    }


    public void loadImageCircle(String url, ImageView target, int placeholderDrawable) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(placeholderDrawable)
                .apply(RequestOptions.circleCropTransform())
                .priority(Priority.HIGH);

        Glide.with(ProjectApplication.getInstance())
                .load(url)
                .apply(options)
                .into(target);
    }

    public void loadImageCircle(Uri uri, ImageView target, int placeholderDrawable) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(placeholderDrawable)
                .apply(RequestOptions.circleCropTransform())
                .priority(Priority.HIGH);

        Glide.with(ProjectApplication.getInstance())
                .load(uri)
                .apply(options)
                .into(target);
    }

    private interface OnGooglePlayServiceListener {
        void onInstallingService();

        void onCancelServiceInstallation();
    }


}