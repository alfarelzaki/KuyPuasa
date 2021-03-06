package com.moslemdev.kuypuasa;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

/**
 * Created by kamran on 8/28/16.
 */

public class PermissionHelper {

    private static final int PERMISSION_REQUEST_CODE = 0;

    public static void askForAllPermissions(Activity activity) {
        try {
            String[] permissions = getRequiredPermissions(activity);
            ArrayList<String> permissionsToBeAsked = new ArrayList<>();

            if (permissions != null) {
                for (String permission : permissions) {
                    if (!isPermissionGranted(activity, permission) && isDangerous(activity, permission)) {
                        permissionsToBeAsked.add(permission);
                    }
                }
            }

            if (permissionsToBeAsked.size() > 0) {
                askForPermission(activity, permissionsToBeAsked.toArray(new String[]{}));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static String[] getRequiredPermissions(Context context) throws PackageManager.NameNotFoundException {
        return context.getPackageManager()
                .getPackageInfo(context.getPackageName(), PackageManager.GET_PERMISSIONS)
                .requestedPermissions;
    }

    public static boolean isPermissionGranted(Context context, String permission) {
        return Build.VERSION.SDK_INT <= 22
                || ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean areAllPermissionsGranted(Context context) {
        boolean areGranted = true;

        try {
            String[] permissions = getRequiredPermissions(context);

            for (String permission : permissions) {
                areGranted = areGranted
                        && (!isDangerous(context, permission) || isPermissionGranted(context, permission));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();

            return false;
        }

        return areGranted;
    }

    public static void askForPermission(Activity activity, String permissions) {
        askForPermission(activity, new String[]{permissions});
    }

    public static void askForPermission(Activity activity, String... permissions) {
        if (Build.VERSION.SDK_INT >= 22) {
            ActivityCompat.requestPermissions(activity, permissions, PERMISSION_REQUEST_CODE);
        }
    }

    private static boolean isDangerous(Context context, String permission) {
        boolean isDangerous = false;

        try {
            isDangerous = context.getPackageManager().getPermissionInfo(permission, 0).protectionLevel
                    == PermissionInfo.PROTECTION_DANGEROUS;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return isDangerous;
    }

    public static boolean isAnyPermissionDeniedByUser(Activity activity) {
        boolean isChecked = false;

        if (Build.VERSION.SDK_INT >= 22) {
            return false;
        }

        try {
            String[] requiredPermissions = getRequiredPermissions(activity);

            for (String requiredPermission : requiredPermissions) {
                if (isDangerous(activity, requiredPermission)) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        isChecked = isChecked || activity.shouldShowRequestPermissionRationale(requiredPermission);
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return isChecked;
    }

    public static void showPermissionSettings(Activity activity, int code) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setData(Uri.parse("package:" + activity.getPackageName()));

        activity.startActivityForResult(intent, code);
    }
}

