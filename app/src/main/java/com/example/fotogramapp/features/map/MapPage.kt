package com.example.fotogramapp.features.map

import android.Manifest
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.fotogramapp.LocalPostRepository
import com.example.fotogramapp.LocalUserRepository
import com.example.fotogramapp.ui.components.offlineretry.OfflineRetry
import com.example.fotogramapp.ui.components.mappin.MapPin
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.Style
import com.mapbox.maps.ViewAnnotationAnchor
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.annotation.ViewAnnotation
import com.mapbox.maps.extension.compose.annotation.generated.PointAnnotationGroup
import com.mapbox.maps.extension.style.expressions.dsl.generated.literal
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.plugin.PuckBearing
import com.mapbox.maps.plugin.animation.MapAnimationOptions
import com.mapbox.maps.plugin.annotation.AnnotationConfig
import com.mapbox.maps.plugin.annotation.AnnotationSourceOptions
import com.mapbox.maps.plugin.annotation.ClusterOptions
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.locationcomponent.createDefault2DPuck
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.maps.viewannotation.geometry
import com.mapbox.maps.viewannotation.viewAnnotationOptions
import com.mapbox.maps.viewannotation.annotationAnchor

@Composable
fun MapPage(modifier: Modifier = Modifier, postLocation: Point? = null) {

    val context = LocalContext.current
    val userRepo = LocalUserRepository.current
    val postRepo = LocalPostRepository.current

    val viewModel: MapPageViewModel = viewModel(
        factory = viewModelFactory {
            initializer {
                MapPageViewModel(
                    context = context,
                    postRepo = postRepo,
                    userRepo = userRepo
                )
            }
        }
    )

    // == Permission Launcher ==
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission(),
        onResult = viewModel.onLauncherResult
    )

    // == Map View State ==
    val mapViewportState = rememberMapViewportState {
        setCameraOptions {
            zoom(15.0)
            center(viewModel.centerLocation)
            pitch(0.0)
            bearing(0.0)
        }
    }

    // == Launched Effects ==
    LaunchedEffect(Unit) {
        viewModel.loadMappedPosts()

        if(postLocation != null) {

            viewModel.setGivenLocation(postLocation)
            mapViewportState.setCameraOptions {
                center(postLocation)
            }
        } else {
            mapViewportState.transitionToFollowPuckState()
        }

        viewModel.checkPermissions()
        if (!viewModel.hasPermission) {
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }



    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (viewModel.loading) {
            //Handle Loafing
            CircularProgressIndicator()
        } else {
            //Handle Offline
            if (viewModel.offline) {
                OfflineRetry() {
                    viewModel.loadMappedPosts()
                }
            } else {

                if (viewModel.hasPermission) {
                    //With Permissions -> Show FAB to center on user location
                    FloatingActionButton(
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .zIndex(2f)
                            .align(Alignment.BottomEnd)
                            .offset(y = (-120.0).dp),
                        onClick = {
                            mapViewportState.transitionToFollowPuckState()
                        },
                        shape = CircleShape
                    ) {
                        Icon(
                            imageVector = Icons.Default.MyLocation,
                            contentDescription = "Center marker",
                            modifier = Modifier
                                .padding(20.dp)
                                .size(35.dp),
                        )
                    }
                }

                // == Map View ==
                MapboxMap(
                    modifier = Modifier
                        .fillMaxSize(),
                    mapViewportState = mapViewportState
                ) {
                    viewModel.postLocations?.forEach { location ->
                        ViewAnnotation(
                            options = viewAnnotationOptions {
                                //View annotation is placed at the specific geo coordinate
                                geometry(location.coordinate)
                                //Set anchor to the bottom of the view annotation
                                annotationAnchor {
                                    //Bottom of the view annotation is placed on the geometry
                                    anchor(ViewAnnotationAnchor.BOTTOM)

                                }
                            },


                            ) {
                            //Insert the content of the ViewAnnotation
                            MapPin(
                                image = location.image,
                                text = location.name
                            )
                        }
                    }

                    // == Annotations with Clusters ==
                    val posts = viewModel.postLocations
                    posts?.let {
                        PointAnnotationGroup(
                            annotations = posts.map {
                                PointAnnotationOptions()
                                    .withPoint(it.coordinate)
                            },
                            annotationConfig = AnnotationConfig(
                                annotationSourceOptions = AnnotationSourceOptions(
                                    clusterOptions = ClusterOptions(
                                        textColorExpression = Expression.color(MaterialTheme.colorScheme.onPrimary.toArgb()),
                                        textColor = Color.Black.toArgb(),
                                        textSize = 20.0,
                                        circleRadiusExpression = literal(25.0),
                                        colorLevels = listOf(
                                            //Clusters Colors
                                            Pair(30, MaterialTheme.colorScheme.tertiary.toArgb()),
                                            Pair(0, MaterialTheme.colorScheme.secondary.toArgb())
                                        )
                                    )
                                )
                            ),
                        ) {
                            //On cluster Click
                            interactionsState.onClusterClicked { cluster ->
                                val clusterPoint = cluster.originalFeature.geometry()
                                mapViewportState.cameraState?.let {
                                    val currentZoom = it.zoom
                                    val calculatedZoom = currentZoom + 2.0

                                    clusterPoint?.let {
                                        mapViewportState.easeTo(
                                            CameraOptions.Builder()
                                                .center(Point.fromJson(clusterPoint.toJson()))
                                                .zoom(calculatedZoom)
                                                .build(),
                                            MapAnimationOptions.mapAnimationOptions {
                                                duration(500)
                                            }
                                        )
                                    }
                                }
                                true
                            }
                        }
                    }


                    // == Map Effects ==
                    MapEffect(Unit) { mapView ->
                        mapView.mapboxMap.style?.styleLayers?.forEach {
                        }

                        //Create User Puck (if has location Permissions)
                        mapView.location.updateSettings {
                            locationPuck = createDefault2DPuck(withBearing = true)
                            puckBearingEnabled = true
                            puckBearing = PuckBearing.HEADING
                            enabled = true
                        }

                        mapView.mapboxMap.loadStyleUri(
                            Style.STANDARD
                        ) { style ->
                            style.styleLayers.forEach {
                                Log.d("maplayers", it.id)
                            }

                            //Set Layers
                            val clusterLayers = listOf(
                                "mapbox-android-pointAnnotation-cluster-circle-layer-0-1",
                                "mapbox-android-pointAnnotation-cluster-circle-layer-1-1",
                                "mapbox-android-pointAnnotation-cluster-text-layer-1"
                            )

                            clusterLayers.forEach { layerId ->
                                style.moveStyleLayer(
                                    layerId,
                                    null
                                )
                            }
                        }
                    }
                }
            }
        }

    }

}