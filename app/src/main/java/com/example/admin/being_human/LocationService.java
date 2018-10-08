package com.example.admin.being_human;

import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Location;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.common.data.DataBufferObserver;
import com.google.android.gms.location.LocationRequest;

import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import pl.charmas.android.reactivelocation2.ReactiveLocationProvider;

/**
 * Created by kulsoom on 19-Dec-17.
 */

public class LocationService extends Service implements  Consumer<Location>, Observer<List<Address>>{

    List<ActiveLocation> dl=new ArrayList<>();
    ActiveLocation deviceLocation=new ActiveLocation();
    ReactiveLocationProvider locationProvider;
    StorageManager storageManager;
    UserSessionManager sessionManager;
    HashMap<String,String> user;
    @Override
    public IBinder onBind(Intent intent) {
     //   super.onBind(intent);
        System.out.println("LocationService onBind");
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("LocationService onCreate");
        storageManager=new StorageManager(this);
        sessionManager=new UserSessionManager(this);
        user=sessionManager.getUserDetails();

        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == 0) {
            System.out.println("LocationService Permission available");
            LocationRequest request = LocationRequest.create() //standard GMS LocationRequest
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                    .setInterval(100);

            locationProvider=new ReactiveLocationProvider(this);
            Observable<Location> locationObservable = locationProvider.getUpdatedLocation(request);
            locationObservable.subscribe(this );
        }
    }




    @Override
    public void accept(Location location) throws Exception {

        System.out.println( "Location Service:  "+location.getLatitude() + location.getLongitude());;
//        //database
        storageManager.saveData(Constant.LOCATION_LATITUDE, String.valueOf(location.getLatitude()));
        storageManager.saveData(Constant.LOCATION_LONGITUDE, String.valueOf(location.getLongitude()));
        deviceLocation.setLatitude(location.getLatitude());
       deviceLocation.setLongitude(location.getLongitude());
        new updateLocation(String.valueOf(location.getLatitude())+","+String.valueOf(location.getLongitude())
                ,user.get(UserSessionManager.KEY_EMAIL)).execute();
        new CheckRequests(user.get(UserSessionManager.KEY_EMAIL),this).execute();

    }

    @Override
    public void onSubscribe(Disposable d) {
        System.out.println("onSubscribe ");

    }

    @Override
    public void onNext(List<Address> addresses) {
        System.out.println("onNext ");
//        for (Address address : addresses){
//            System.out.println("getAdminArea " +address.getAdminArea());
//            System.out.println("getFeatureName " +address.getFeatureName());
//            System.out.println("getLocality " +address.getLocality());
//            System.out.println("getPremises " +address.getPremises());
//            System.out.println("getSubAdminArea " +address.getSubAdminArea());
//            System.out.println("getThoroughfare " +address.getThoroughfare());
//            System.out.println("getUrl " +address.getUrl());
//
//        }

    }

    @Override
    public void onError(Throwable e) {
        System.out.println("onError---- ");

    }

    @Override
    public void onComplete() {
        System.out.println("onComplete ");

    }
}
