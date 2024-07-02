package com.group2.bookstoreproject.ui.common.map;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.group2.bookstoreproject.R;
import com.group2.bookstoreproject.base.BaseFragment;
import com.group2.bookstoreproject.databinding.FragmentMapBinding;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.geojson.Point;
import com.mapbox.maps.MapboxMap;
import com.mapbox.maps.Style;
import com.mapbox.maps.plugin.Plugin;
import com.mapbox.maps.plugin.locationcomponent.LocationComponentPlugin;

import java.util.List;


public class MapFragment extends BaseFragment<FragmentMapBinding, MapViewModel> {
    private PermissionsManager permissionsManager;
    private MapboxMap mapboxMap;
    private Point originPoint; // Vị trí cố định
    private Point destinationPoint; // Vị trí đã chọn

    private PermissionsListener permissionsListener = new PermissionsListener() {
        @Override
        public void onExplanationNeeded(@NonNull List<String> list) {
            // Giải thích với người dùng tại sao cần quyền này, nếu cần.
        }

        @Override
        public void onPermissionResult(boolean granted) {
            if (granted) {
                // Kích hoạt LocationComponent khi quyền được cấp
                //enableLocationComponent();
            } else {
                // Người dùng từ chối quyền
                // Xử lý trường hợp người dùng từ chối quyền ở đây
            }
        }
    };

    @NonNull
    @Override
    protected FragmentMapBinding inflateBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, boolean attachToParent) {
        return FragmentMapBinding.inflate(inflater, container, attachToParent);
    }

    @NonNull
    @Override
    protected Class<MapViewModel> getViewModelClass() {
        return MapViewModel.class;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.mapView.getMapboxMap().loadStyle(Style.MAPBOX_STREETS, style -> {
            mapboxMap = binding.mapView.getMapboxMap();
            //setupMapClickListener();
        });

        if(PermissionsManager.areLocationPermissionsGranted(getContext())){
            //enableLocationComponent();
        } else {
            permissionsManager = new  PermissionsManager(permissionsListener);
            permissionsManager.requestLocationPermissions(getActivity());
        }
    }

//    private void enableLocationComponent() {
//        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            return;
//        }
//
//        LocationComponentPlugin locationComponent = binding.mapView.getPlugin(Plugin.MAPBOX_LOCATION_COMPONENT_PLUGIN_ID);
//        LocationComponentActivationOptions locationComponentActivationOptions = LocationComponentActivationOptions.builder(getContext(), mapboxMap.getStyle())
//                .useDefaultLocationEngine(true)
//                .locationComponentOptions(LocationComponentOptions.builder(getContext())
//                        .trackingGesturesManagement(true)
//                        .accuracyColor(Color.RED)
//                        .build())
//                .build();
//        locationComponent.activateLocationComponent(locationComponentActivationOptions);
//        locationComponent.setLocationComponentEnabled(true);
//        locationComponent.setCameraMode(CameraMode.TRACKING);
//        locationComponent.setRenderMode(RenderMode.COMPASS);
//    }


//    private void setupMapClickListener() {
//        mapboxMap. (point -> {
//            if (destinationPoint == null) {
//                destinationPoint = Point.fromLngLat(point.getLongitude(), point.getLatitude());
//                addMarker(destinationPoint);
//                calculateRoute();
//            } else {
//                destinationPoint = null;
//                mapboxMap.getStyle(style -> style.removeLayer("route-layer"));
//            }
//            return true;
//        });
//    }

    @Override
    public void onStart() {
        super.onStart();
        binding.mapView.onStart();
    }


    @Override
    public void onStop() {
        super.onStop();
        binding.mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        binding.mapView.onLowMemory();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding.mapView.onDestroy();
    }

}