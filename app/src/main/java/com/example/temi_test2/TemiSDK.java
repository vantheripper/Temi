package com.example.temi_test2;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.CheckResult;
import androidx.appcompat.app.AlertDialog;

import com.robotemi.sdk.BatteryData;
import com.robotemi.sdk.MediaObject;
import com.robotemi.sdk.NlpResult;
import com.robotemi.sdk.Robot;
import com.robotemi.sdk.TtsRequest;
import com.robotemi.sdk.UserInfo;
import com.robotemi.sdk.activitystream.ActivityStreamObject;
import com.robotemi.sdk.activitystream.ActivityStreamPublishMessage;
import com.robotemi.sdk.constants.SdkConstants;
import com.robotemi.sdk.exception.OnSdkExceptionListener;
import com.robotemi.sdk.exception.SdkException;
import com.robotemi.sdk.face.ContactModel;
import com.robotemi.sdk.face.OnFaceRecognizedListener;
import com.robotemi.sdk.listeners.OnBatteryStatusChangedListener;
import com.robotemi.sdk.listeners.OnBeWithMeStatusChangedListener;
import com.robotemi.sdk.listeners.OnConstraintBeWithStatusChangedListener;
import com.robotemi.sdk.listeners.OnDetectionDataChangedListener;
import com.robotemi.sdk.listeners.OnDetectionStateChangedListener;
import com.robotemi.sdk.listeners.OnGoToLocationStatusChangedListener;
import com.robotemi.sdk.listeners.OnLocationsUpdatedListener;
import com.robotemi.sdk.listeners.OnRobotLiftedListener;
import com.robotemi.sdk.listeners.OnRobotReadyListener;
import com.robotemi.sdk.listeners.OnTelepresenceEventChangedListener;
import com.robotemi.sdk.listeners.OnUserInteractionChangedListener;
import com.robotemi.sdk.map.MapDataModel;
import com.robotemi.sdk.model.CallEventModel;
import com.robotemi.sdk.model.DetectionData;
import com.robotemi.sdk.navigation.listener.OnCurrentPositionChangedListener;
import com.robotemi.sdk.navigation.listener.OnDistanceToLocationChangedListener;
import com.robotemi.sdk.navigation.model.Position;
import com.robotemi.sdk.navigation.model.SafetyLevel;
import com.robotemi.sdk.navigation.model.SpeedLevel;
import com.robotemi.sdk.permission.OnRequestPermissionResultListener;
import com.robotemi.sdk.permission.Permission;
import com.robotemi.sdk.sequence.OnSequencePlayStatusChangedListener;
import com.robotemi.sdk.sequence.SequenceModel;
//import com.temievent.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class TemiSDK extends Exception implements
        Robot.NlpListener,
        OnRobotReadyListener,
        Robot.ConversationViewAttachesListener,
        Robot.WakeupWordListener,
        Robot.ActivityStreamPublishListener,
        Robot.TtsListener,
        OnBeWithMeStatusChangedListener,
        OnGoToLocationStatusChangedListener,
        OnLocationsUpdatedListener,
        OnConstraintBeWithStatusChangedListener,
        OnDetectionStateChangedListener,
        Robot.AsrListener,
        OnTelepresenceEventChangedListener,
        OnRequestPermissionResultListener,
        OnDistanceToLocationChangedListener,
        OnCurrentPositionChangedListener,
        OnSequencePlayStatusChangedListener,
        OnRobotLiftedListener,
        OnDetectionDataChangedListener,
        OnUserInteractionChangedListener,
        OnFaceRecognizedListener,
        OnSdkExceptionListener
{
    static Context context;
    static Robot robot;
    private List<String> savedLocations;
    private Boolean looptravler = false;
    String TAG = "TemiSDK";
    public static final String ACTION_HOME_WELCOME = "home.welcome", ACTION_HOME_DANCE = "home.dance", ACTION_HOME_SLEEP = "home.sleep";
    public static final String HOME_BASE_LOCATION = "home base";
    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final int REQUEST_CODE_NORMAL = 0;
    private static final int REQUEST_CODE_FACE_START = 1;
    private static final int REQUEST_CODE_FACE_STOP = 2;
    private static final int REQUEST_CODE_MAP = 3;
    private static final int REQUEST_CODE_SEQUENCE_FETCH_ALL = 4;
    private static final int REQUEST_CODE_SEQUENCE_PLAY = 5;
    private static final int REQUEST_CODE_START_DETECTION_WITH_DISTANCE = 6;


    public TemiSDK(Context context) {
        this.context = context;
        robot = Robot.getInstance(); // get an instance of the robot in order to begin using its features.
        robot.addOnRobotReadyListener(this);
        robot.addNlpListener(this);
        robot.addOnBeWithMeStatusChangedListener(this);
        robot.addOnGoToLocationStatusChangedListener(this);
        robot.addConversationViewAttachesListenerListener(this);
        robot.addWakeupWordListener(this);
        robot.addTtsListener(this);
        robot.addOnLocationsUpdatedListener(this);
        robot.addOnConstraintBeWithStatusChangedListener(this);
        robot.addOnDetectionStateChangedListener(this);
        robot.addAsrListener(this);
        robot.addOnDistanceToLocationChangedListener(this);
        robot.addOnCurrentPositionChangedListener(this);
        robot.addOnSequencePlayStatusChangedListener(this);
        robot.addOnRobotLiftedListener(this);
        robot.addOnDetectionDataChangedListener(this);
        robot.addOnUserInteractionChangedListener(this);
        robot.hideTopBar();

    }
    @Override
    public void onRobotReady(boolean isReady) {
        if (isReady) {
//            try {
//                ActivityInfo activityInfo = context.getPackageManager().getActivityInfo(getComponentName(), PackageManager.GET_META_DATA);
//                robot.onStart(activityInfo);
//            } catch (PackageManager.NameNotFoundException e) {
//                throw new RuntimeException(e);
//            }
            robot.hideTopBar();
            disableWakeup();
        }

    }
    public void requestMapPermission(){
        if (requestPermissionIfNeeded(Permission.MAP, REQUEST_CODE_MAP)) {

        }
    }

    public MapDataModel getMapdata(){
        MapDataModel mapData = robot.getMapData();
        return mapData;
    }
    /**
     * Have the robot speak while displaying what is being said.
     */
    public void speak(String txt) {
        TtsRequest ttsRequest = TtsRequest.create(txt, true);
        robot.speak(ttsRequest);
    }
    public void speak(String txt,Boolean show) {
        TtsRequest ttsRequest = TtsRequest.create(txt, show);
        robot.speak(ttsRequest);
    }

    /**
     * context is an example of saving locations.
     */
    public void saveLocation(String location) {
        boolean result = robot.saveLocation(location);
        if (result) {
            robot.speak(TtsRequest.create("I've successfully saved the " + location + " location.", true));
        } else {
            robot.speak(TtsRequest.create("Saved the " + location + " location failed.", true));
        }
    }

    /**
     * goTo checks that the location sent is saved then goes to that location.
     */
    public void goTo(String locationname) {
        for (String location : robot.getLocations()) {
            if (location.equals(locationname)) {
                robot.goTo(locationname);
            }
        }
    }
    public void goTo(String locationname,Boolean hidebillboard) {
        for (String location : robot.getLocations()) {
            if (location.equals(locationname)) {
                robot.goTo(locationname);
                if(hidebillboard){
                    hideNavBillboard();
                }else {
                    showNavBillboard();
                }

            }
        }
    }

    /**
     * stopMovement() is used whenever you want the robot to stop any movement
     * it is currently doing.
     */
    public void stopMovement() {
        robot.stopMovement();
        robot.speak(TtsRequest.create("And so I have stopped", true));
    }

    /**
     * Simple follow me example.
     */
    public void followMe() {
        robot.beWithMe();
    }

    /**
     * Manually navigate the robot with skidJoy, tiltAngle, turnBy and tiltBy.
     * skidJoy moves the robot exactly forward for about a second. It controls both
     * the linear and angular velocity. Float numbers must be between -1.0 and 1.0
     */
    public void skidJoy() {
        long t = System.currentTimeMillis();
        long end = t + 80; //van edit here default at 1000
        while (System.currentTimeMillis() < end) {
            robot.skidJoy(1F, 0F);
        }
    }

    /**
     * tiltAngle controls temi's head by specifying which angle you want
     * to tilt to and at which speed.
     */
    public void tiltAngle(Integer degree) {
        robot.tiltAngle(degree);
    }

    /**
     * turnBy allows for turning the robot around in place. You can specify
     * the amount of degrees to turn by and at which speed.
     */
    public void turnBy(Integer degree) {
        robot.turnBy(degree);
    }

    /**
     * tiltBy is used to tilt temi's head from its current position.
     */
    public void tiltBy(Integer degree) {
        robot.tiltBy(degree);
    }

    /**
     * getBatteryData can be used to return the current battery status.
     */
    public String getBatteryData() {
        BatteryData batteryData = robot.getBatteryData();
        if (batteryData == null) {
            printLog("getBatteryData()", "batteryData is null");
            return "batteryData is null";
        }
        if (batteryData.isCharging()) {
            TtsRequest ttsRequest = TtsRequest.create(batteryData.getBatteryPercentage() + " percent battery and charging.", true);
            robot.speak(ttsRequest);
        } else {
            TtsRequest ttsRequest = TtsRequest.create(batteryData.getBatteryPercentage() + " percent battery and not charging.", true);
            robot.speak(ttsRequest);
        }
        return String.valueOf(batteryData.getBatteryPercentage());
    }

    /**
     * Display the saved locations in a dialog
     */
    public void savedLocationsDialog(final Context context) {
        final List<String> locations = robot.getLocations();
        final TemiSDKLocationAdapter mAdapter = new TemiSDKLocationAdapter(context, android.R.layout.simple_selectable_list_item, locations);
        AlertDialog.Builder versionsDialog = new AlertDialog.Builder(context);
        versionsDialog.setTitle("Saved Locations: (Click to delete the location)");
        versionsDialog.setPositiveButton("OK", null);
        versionsDialog.setAdapter(mAdapter, null);
        AlertDialog dialog = versionsDialog.create();
        dialog.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {
                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(context);
                builder.setMessage("Delete location \"" + mAdapter.getItem(position) + "\" ?");
                builder.setPositiveButton("No thanks", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String location = mAdapter.getItem(position);
                        if (location == null) {
                            return;
                        }
                        boolean result = robot.deleteLocation(location);
                        if (result) {
                            locations.remove(position);
                            robot.speak(TtsRequest.create(location + "delete successfully!", false));
                            mAdapter.notifyDataSetChanged();
                        } else {
                            robot.speak(TtsRequest.create(location + "delete failed!", false));
                        }
                    }
                });
                Dialog deleteDialog = builder.create();
                deleteDialog.show();
            }
        });
        dialog.show();
    }

    /**
     * When adding the Nlp Listener to your project you need to implement context method
     * which will listen for specific intents and allow you to respond accordingly.
     * <p>
     * See AndroidManifest.xml for reference on adding each intent.
     */
    @Override
    public void onNlpCompleted(NlpResult nlpResult) {
        //do something with nlp result. Base the action specified in the AndroidManifest.xml
        Toast.makeText(context, nlpResult.action, Toast.LENGTH_SHORT).show();
        switch (nlpResult.action) {
            case ACTION_HOME_WELCOME:
                robot.tiltAngle(23);
                break;

            case ACTION_HOME_DANCE:
                long t = System.currentTimeMillis();
                long end = t + 5000;
                while (System.currentTimeMillis() < end) {
                    robot.skidJoy(0F, 1F);
                }
                break;

            case ACTION_HOME_SLEEP:
                robot.goTo(HOME_BASE_LOCATION);
                break;
        }
    }

    /**
     * callOwner is an example of how to use telepresence to call an individual.
     */
    public void callOwner() {
        if (robot.getAdminInfo() == null) {
            printLog("callOwner()", "adminInfo is null.");
            return;
        }
        robot.startTelepresence(robot.getAdminInfo().getName(), robot.getAdminInfo().getUserId());
    }

    /**
     * publishToActivityStream takes an image stored in the resources folder
     * and uploads it to the mobile application under the Activities tab.
     */
    public void publishToActivityStream() {
        ActivityStreamObject activityStreamObject;
        if (robot != null) {
            final String fileName = "puppy.png";
            Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher_background);
            File puppiesFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath(), fileName);
            FileOutputStream fileOutputStream;
            try {
                fileOutputStream = new FileOutputStream(puppiesFile);
                bm.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                fileOutputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            activityStreamObject = ActivityStreamObject.builder()
                    .activityType(ActivityStreamObject.ActivityType.PHOTO)
                    .title("Puppy")
                    .media(MediaObject.create(MediaObject.MimeType.IMAGE, puppiesFile))
                    .build();

            robot.shareActivityObject(activityStreamObject);
            robot.speak(TtsRequest.create("Uploading Image", false));
        }
    }

    public void hideTopBar() {
        robot.hideTopBar();
    }

    public void showTopBar() {
        robot.showTopBar();
    }

    @Override
    public void onWakeupWord(@NotNull String wakeupWord, int direction) {
        // Do anything on wakeup. Follow, go to location, or even try creating dance moves.
        printLog("onWakeupWord", wakeupWord + ", " + direction);
    }

    @Override
    public void onTtsStatusChanged(@NotNull TtsRequest ttsRequest) {
        // Do whatever you like upon the status changing. after the robot finishes speaking
    }

    @Override
    public void onBeWithMeStatusChanged(String status) {
        //  When status changes to "lock" the robot recognizes the user and begin to follow.
        switch (status) {
            case OnBeWithMeStatusChangedListener.ABORT:
                // do something i.e. speak
                robot.speak(TtsRequest.create("Abort", false));
                break;

            case OnBeWithMeStatusChangedListener.CALCULATING:
                robot.speak(TtsRequest.create("Calculating", false));
                break;

            case OnBeWithMeStatusChangedListener.LOCK:
                robot.speak(TtsRequest.create("Lock", false));
                break;

            case OnBeWithMeStatusChangedListener.SEARCH:
                robot.speak(TtsRequest.create("search", false));
                break;

            case OnBeWithMeStatusChangedListener.START:
                robot.speak(TtsRequest.create("Start", false));
                break;

            case OnBeWithMeStatusChangedListener.TRACK:
                robot.speak(TtsRequest.create("Track", false));
                break;
        }
    }

    @Override
    public void onGoToLocationStatusChanged(@NotNull String location, String status, int descriptionId, @NotNull String description) {
        printLog("GoToStatusChanged", "status=" + status + ", descriptionId=" + descriptionId + ", description=" + description);
        robot.speak(TtsRequest.create(description, false));
        switch (status) {
            case OnGoToLocationStatusChangedListener.START:
                robot.speak(TtsRequest.create("Starting", false));
                break;

            case OnGoToLocationStatusChangedListener.CALCULATING:
                robot.speak(TtsRequest.create("Calculating", false));
                break;

            case OnGoToLocationStatusChangedListener.GOING:
                robot.speak(TtsRequest.create("Going", false));
                break;

            case OnGoToLocationStatusChangedListener.COMPLETE:
                robot.speak(TtsRequest.create("Completed", false));
                break;

            case OnGoToLocationStatusChangedListener.ABORT:
                robot.speak(TtsRequest.create("Cancelled", false));
                break;
        }
    }

    @Override
    public void onPublish(@NotNull ActivityStreamPublishMessage message) {
        //After the activity stream finished publishing (photo or otherwise).
        //Do what you want based on the message returned.
        robot.speak(TtsRequest.create("Uploaded.", false));
    }

    @Override
    public void onLocationsUpdated(@NotNull List<String> locations) {
        //Saving or deleting a location will update the list.
        Toast.makeText(context, "Locations updated :\n" + locations, Toast.LENGTH_LONG).show();
    }

    public void disableWakeup() {
        robot.toggleWakeup(true);
    }

    public void enableWakeup() {
        robot.toggleWakeup(false);
    }

    public void toggleNavBillboard() {
        if (requestPermissionIfNeeded(Permission.SETTINGS, REQUEST_CODE_NORMAL)) {
            return;
        }
        robot.toggleNavigationBillboard(!robot.isNavigationBillboardDisabled());
    }
    public void showNavBillboard() {
        if (requestPermissionIfNeeded(Permission.SETTINGS, REQUEST_CODE_NORMAL)) {
            return;
        }
        robot.toggleNavigationBillboard(false);
    }
    public void hideNavBillboard() {
        if (requestPermissionIfNeeded(Permission.SETTINGS, REQUEST_CODE_NORMAL)) {
            return;
        }
        robot.toggleNavigationBillboard(true);
    }

    @Override
    public void onConstraintBeWithStatusChanged(boolean isConstraint) {
        printLog("onConstraintBeWith", "status = " + isConstraint);
    }

    @Override
    public void onDetectionStateChanged(int state) {
        printLog("onDetectionStateChanged: state = " + state);
        if (state == OnDetectionStateChangedListener.DETECTED) {
            robot.constraintBeWith();
        } else if (state == OnDetectionStateChangedListener.IDLE) {
            robot.stopMovement();
        }
    }

    /**
     * If you want to cover the voice flow in Launcher OS,
     * please add following meta-data to AndroidManifest.xml.
     * <pre>
     * <meta-data
     *     android:name="com.robotemi.sdk.metadata.KIOSK"
     *     android:value="true" />
     *
     * <meta-data
     *     android:name="com.robotemi.sdk.metadata.OVERRIDE_NLU"
     *     android:value="true" />
     * <pre>
     * And also need to select context App as the Kiosk Mode App in Settings > Kiosk Mode.
     *
     * @param asrResult The result of the ASR after waking up temi.
     */
    @Override
    public void onAsrResult(String asrResult) {
        printLog("onAsrResult", "asrResult = " + asrResult);
        try {
            Bundle metadata = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA)
                    .metaData;
            if (metadata == null) return;
            if (!robot.isSelectedKioskApp()) return;
            if (!metadata.getBoolean(SdkConstants.METADATA_OVERRIDE_NLU)) return;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return;
        }

        if (asrResult.equalsIgnoreCase("Hello")) {
            robot.askQuestion("Hello, I'm temi, what can I do for you?");
        } else if (asrResult.equalsIgnoreCase("Play music")) {
            robot.speak(TtsRequest.create("Okay, please enjoy.", false));
            robot.finishConversation();
            playMusic();
        } else if (asrResult.equalsIgnoreCase("Play movie")) {
            robot.speak(TtsRequest.create("Okay, please enjoy.", false));
            robot.finishConversation();
            playMovie();
        } else if (asrResult.toLowerCase().contains("follow me")) {
            robot.finishConversation();
            robot.beWithMe();
        } else if (asrResult.toLowerCase().contains("go to home base")) {
            robot.finishConversation();
            robot.goTo("home base");
        } else {
            robot.askQuestion("Sorry I can't understand you, could you please ask something else?");
        }
    }

    private void playMovie() {
        // Play movie...
        printLog("onAsrResult", "Play movie...");
    }

    public void playMusic() {
        // Play music...
        printLog("onAsrResult", "Play music...");
    }

    public void privacyModeOn() {
        robot.setPrivacyMode(true);
        Toast.makeText(context, robot.getPrivacyMode() + "", Toast.LENGTH_SHORT).show();
    }

    public void privacyModeOff() {
        robot.setPrivacyMode(false);
        Toast.makeText(context, robot.getPrivacyMode() + "", Toast.LENGTH_SHORT).show();
    }

    public void getPrivacyModeState() {
        Toast.makeText(context, robot.getPrivacyMode() + "", Toast.LENGTH_SHORT).show();
    }

    public void isHardButtonsEnabled() {
        Toast.makeText(context, robot.isHardButtonsDisabled() + "", Toast.LENGTH_SHORT).show();
    }

    public void disableHardButtons( ) {
        robot.setHardButtonsDisabled(true);
        Toast.makeText(context, robot.isHardButtonsDisabled() + "", Toast.LENGTH_SHORT).show();
    }

    public void enableHardButtons( ) {
        robot.setHardButtonsDisabled(false);
        Toast.makeText(context, robot.isHardButtonsDisabled() + "", Toast.LENGTH_SHORT).show();
    }

    public void getOSVersion( ) {
        String osVersion = String.format("LauncherOs: %s, RoboxVersion: %s", robot.getLauncherVersion(), robot.getRoboxVersion());
        Toast.makeText(context, osVersion, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onTelepresenceEventChanged(@NotNull CallEventModel callEventModel) {
        printLog("onTelepresenceEvent", callEventModel.toString());
        if (callEventModel.getType() == CallEventModel.TYPE_INCOMING) {
            Toast.makeText(context, "Incoming call", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Outgoing call", Toast.LENGTH_LONG).show();
        }
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onRequestPermissionResult(@NotNull Permission permission, int grantResult, int requestCode) {
        String log = String.format("Permission: %s, grantResult: %d", permission.getValue(), grantResult);
        Toast.makeText(context, log, Toast.LENGTH_SHORT).show();
        printLog("onRequestPermission", log);
        if (grantResult == Permission.DENIED) {
            return;
        }
        switch (permission) {
            case FACE_RECOGNITION:
                if (requestCode == REQUEST_CODE_FACE_START) {
                    robot.startFaceRecognition();
                } else if (requestCode == REQUEST_CODE_FACE_STOP) {
                    robot.stopFaceRecognition();
                }
                break;
            case SEQUENCE:
                if (requestCode == REQUEST_CODE_SEQUENCE_FETCH_ALL) {
                    getAllSequences();
                } else if (requestCode == REQUEST_CODE_SEQUENCE_PLAY) {
                    playFirstSequence();
                }
                break;
            case MAP:
                if (requestCode == REQUEST_CODE_MAP) {
                    getMap();
                }
                break;
            case SETTINGS:
                if (requestCode == REQUEST_CODE_START_DETECTION_WITH_DISTANCE) {
                    startDetectionWishDistance("REQUEST_CODE_START_DETECTION_WITH_DISTANCE");
                }
                break;
        }
    }

    public void requestFace() {
        if (robot.checkSelfPermission(Permission.FACE_RECOGNITION) == Permission.GRANTED) {
            Toast.makeText(context, "You already had FACE_RECOGNITION permission.", Toast.LENGTH_SHORT).show();
            return;
        }
        List<Permission> permissions = new ArrayList<>();
        permissions.add(Permission.FACE_RECOGNITION);
        robot.requestPermissions(permissions, REQUEST_CODE_NORMAL);
    }

    public void requestMap() {
        if (robot.checkSelfPermission(Permission.MAP) == Permission.GRANTED) {
            Toast.makeText(context, "You already had MAP permission.", Toast.LENGTH_SHORT).show();
            return;
        }
        List<Permission> permissions = new ArrayList<>();
        permissions.add(Permission.MAP);
        robot.requestPermissions(permissions, REQUEST_CODE_NORMAL);
    }

    public void requestSettings( ) {
        if (robot.checkSelfPermission(Permission.SETTINGS) == Permission.GRANTED) {
            Toast.makeText(context, "You already had SETTINGS permission.", Toast.LENGTH_SHORT).show();
            return;
        }
        List<Permission> permissions = new ArrayList<>();
        permissions.add(Permission.SETTINGS);
        robot.requestPermissions(permissions, REQUEST_CODE_NORMAL);
    }

    public void requestSequence( ) {
        if (robot.checkSelfPermission(Permission.SEQUENCE) == Permission.GRANTED) {
            Toast.makeText(context, "You already had SEQUENCE permission.", Toast.LENGTH_SHORT).show();
            return;
        }
        List<Permission> permissions = new ArrayList<>();
        permissions.add(Permission.SEQUENCE);
        robot.requestPermissions(permissions, REQUEST_CODE_NORMAL);
    }

    public void requestAll( ) {
        List<Permission> permissions = new ArrayList<>();
        for (Permission permission : Permission.values()) {
            if (robot.checkSelfPermission(permission) == Permission.GRANTED) {
                Toast.makeText(context, String.format("You already had '%s' permission.", permission.toString()), Toast.LENGTH_SHORT).show();
                continue;
            }
            permissions.add(permission);
        }
        robot.requestPermissions(permissions, REQUEST_CODE_NORMAL);
    }

    public void startFaceRecognition( ) {
        if (requestPermissionIfNeeded(Permission.FACE_RECOGNITION, REQUEST_CODE_FACE_START)) {
            return;
        }
        robot.startFaceRecognition();
    }

    public void stopFaceRecognition( ) {
        robot.stopFaceRecognition();
    }

    public void setGoToSpeed( ) {
        if (requestPermissionIfNeeded(Permission.SETTINGS, REQUEST_CODE_NORMAL)) {
            return;
        }
        List<String> speedLevels = new ArrayList<>();
        speedLevels.add(SpeedLevel.HIGH.getValue());
        speedLevels.add(SpeedLevel.MEDIUM.getValue());
        speedLevels.add(SpeedLevel.SLOW.getValue());
        final TemiSDKLocationAdapter adapter = new TemiSDKLocationAdapter(context, android.R.layout.simple_selectable_list_item, speedLevels);
        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle("Select Go To Speed Level")
                .setAdapter(adapter, null)
                .create();
        dialog.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                robot.setGoToSpeed(SpeedLevel.valueToEnum(Objects.requireNonNull(adapter.getItem(position))));
                Toast.makeText(context, adapter.getItem(position), Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void setGoToSafety( ) {
        if (requestPermissionIfNeeded(Permission.SETTINGS, REQUEST_CODE_NORMAL)) {
            return;
        }
        List<String> safetyLevel = new ArrayList<>();
        safetyLevel.add(SafetyLevel.HIGH.getValue());
        safetyLevel.add(SafetyLevel.MEDIUM.getValue());
        final TemiSDKLocationAdapter adapter = new TemiSDKLocationAdapter(context, android.R.layout.simple_selectable_list_item, safetyLevel);
        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle("Select Go To Safety Level")
                .setAdapter(adapter, null)
                .create();
        dialog.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                robot.setNavigationSafety(SafetyLevel.valueToEnum(Objects.requireNonNull(adapter.getItem(position))));
                Toast.makeText(context, adapter.getItem(position), Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void toggleTopBadge( ) {
        if (requestPermissionIfNeeded(Permission.SETTINGS, REQUEST_CODE_NORMAL)) {
            return;
        }
        robot.setTopBadgeEnabled(!robot.isTopBadgeEnabled());
    }

    public void toggleDetectionMode( ) {
        if (requestPermissionIfNeeded(Permission.SETTINGS, REQUEST_CODE_NORMAL)) {
            return;
        }
        robot.setDetectionModeOn(!robot.isDetectionModeOn());
    }

    public void toggleAutoReturn( ) {
        if (requestPermissionIfNeeded(Permission.SETTINGS, REQUEST_CODE_NORMAL)) {
            return;
        }
        robot.setAutoReturnOn(!robot.isAutoReturnOn());
    }

    public void toggleTrackUser( ) {
        if (requestPermissionIfNeeded(Permission.SETTINGS, REQUEST_CODE_NORMAL)) {
            return;
        }
        robot.setTrackUserOn(!robot.isTrackUserOn());
    }

    public void getVolume( ) {
        if (requestPermissionIfNeeded(Permission.SETTINGS, REQUEST_CODE_NORMAL))
            Toast.makeText(context, robot.getVolume() + "", Toast.LENGTH_SHORT).show();
    }

    public void setVolume( ) {
        if (requestPermissionIfNeeded(Permission.SETTINGS, REQUEST_CODE_NORMAL)) {
            return;
        }
        List<String> volumeList = new ArrayList<>(Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"));
        final TemiSDKLocationAdapter adapter = new TemiSDKLocationAdapter(context, android.R.layout.simple_selectable_list_item, volumeList);
        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle("Set Volume")
                .setAdapter(adapter, null)
                .create();
        dialog.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                robot.setVolume(Integer.parseInt(Objects.requireNonNull(adapter.getItem(position))));
                Toast.makeText(context, adapter.getItem(position), Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void requestToBeKioskApp( ) {
        if (robot.isSelectedKioskApp()) {
            Toast.makeText(context, context.getString(R.string.app_name) + " was the selected Kiosk App.", Toast.LENGTH_SHORT).show();
            return;
        }
        robot.requestToBeKioskApp();
    }

    @SuppressLint("DefaultLocale")
    public void startDetectionModeWithDistance( ) {
        if (requestPermissionIfNeeded(Permission.SETTINGS, REQUEST_CODE_START_DETECTION_WITH_DISTANCE)) {
            return;
        }
    }

    public void stopMoving(){
        robot.stopMovement();
    }

    private void startDetectionWishDistance(String distanceStr) {
        if (distanceStr.isEmpty()) distanceStr = "0";
        try {
            float distance = Float.parseFloat(distanceStr);
            robot.setDetectionModeOn(true, distance);
            printLog("Start detection mode with distance: " + distance);
        } catch (Exception e) {
            printLog("startDetectionModeWithDistance", e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDistanceToLocationChanged(@NotNull Map<String, Float> distances) {
        for (String location : distances.keySet()) {
            printLog("onDistanceToLocation", "location:" + location + ", distance:" + distances.get(location));
        }
    }

    @Override
    public void onCurrentPositionChanged(@NotNull Position position) {
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("onCurrentPositionChanged");
        broadcastIntent.putExtra("CPX",position.getX());
        broadcastIntent.putExtra("CPY",position.getY());
        broadcastIntent.putExtra("CPYaw",position.getYaw());
        broadcastIntent.putExtra("CPTiltAngle",position.getTiltAngle());
        context.sendBroadcast(broadcastIntent);
        printLog("onCurrentPosition", position.toString());
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onSequencePlayStatusChanged(int status) {
        printLog(String.format("onSequencePlayStatus status:%d", status));
        if (status == OnSequencePlayStatusChangedListener.ERROR
                || status == OnSequencePlayStatusChangedListener.IDLE) {
            robot.showTopBar();
        }
    }

    @Override
    public void onRobotLifted(boolean isLifted, @NotNull String reason) {
        printLog("onRobotLifted: isLifted: " + isLifted + ", reason: " + reason);
    }

    @CheckResult
    private boolean requestPermissionIfNeeded(Permission permission, int requestCode) {
        if (robot.checkSelfPermission(permission) == Permission.GRANTED) {
            return false;
        }
        robot.requestPermissions(Collections.singletonList(permission), requestCode);
        return true;
    }

    @Override
    public void onDetectionDataChanged(@NotNull DetectionData detectionData) {
        printLog("onDetectionDataChanged", detectionData.toString());
    }

    @Override
    public void onUserInteraction(boolean isInteracting) {
        printLog("onUserInteraction", "isInteracting:" + isInteracting);
    }

    public void getAllSequences() {
        if (requestPermissionIfNeeded(Permission.SEQUENCE, REQUEST_CODE_SEQUENCE_FETCH_ALL)) {
            return;
        }
        getAllSequences();
    }

    private volatile List<SequenceModel> allSequences;

//    private void getAllSequences() {
//        new Thread(() -> {
//            allSequences = robot.getAllSequences();
//            runOnUiThread(() -> {
//                for (SequenceModel sequenceModel : allSequences) {
//                    if (sequenceModel == null) {
//                        continue;
//                    }
//                    printLog(sequenceModel.toString());
//                }
//            });
//        }).start();
//    }

    private void playFirstSequence() {
        if (requestPermissionIfNeeded(Permission.SEQUENCE, REQUEST_CODE_SEQUENCE_PLAY)) {
            return;
        }
        if (allSequences != null && !allSequences.isEmpty()) {
            robot.playSequence(allSequences.get(0).getId());
        }
    }

    public void getMap() {
        if (requestPermissionIfNeeded(Permission.MAP, REQUEST_CODE_MAP)) {
            return;
        }
    }

    @Override
    public void onFaceRecognized(@NotNull List<ContactModel> contactModelList) {
        for (ContactModel contactModel : contactModelList) {
            printLog("onFaceRecognized", contactModel.toString());
//            showFaceRecognitionImage(contactModel.getImageKey());
        }
    }

//    private void showFaceRecognitionImage(String mediaKey) {
//        if (mediaKey.isEmpty()) {
//            ivFace.setImageResource(R.drawable.app_icon);
//            return;
//        }
//        new Thread(() -> {
//            InputStream inputStream = robot.getInputStreamByMediaKey(ContentType.FACE_RECOGNITION_IMAGE, mediaKey);
//            if (inputStream == null) {
//                return;
//            }
//            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//            try {
//                inputStream.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            runOnUiThread(() -> ivFace.setImageBitmap(bitmap));
//        }).start();
//    }

    private void printLog(String msg) {
        printLog("", msg);
    }

    private void printLog(String tag, String msg) {
        Log.d(tag, msg);
    }


    public void startNlu(String Nlu) {
        robot.startDefaultNlu(Nlu);
    }

    @Override
    public void onSdkError(@NotNull SdkException sdkException) {
        printLog("onSdkError: " + sdkException.toString());
    }

    public List<UserInfo> getAllContacts() {
        List<UserInfo> allContacts = robot.getAllContact();
        for (UserInfo userInfo : allContacts) {
            printLog("UserInfo: " + userInfo.toString());
        }
        return allContacts;
    }

    public void goToPosition(Float x,Float y,Float yaw) {
        try {
            robot.goToPosition(new Position(x, y, yaw, 0));
        } catch (Exception e) {
            e.printStackTrace();
            printLog(e.getMessage());
        }
    }

    @Override
    public void onConversationAttaches(boolean b) {

    }


}

